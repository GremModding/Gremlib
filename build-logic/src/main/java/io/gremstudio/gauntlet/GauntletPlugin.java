package io.gremstudio.gauntlet;

import io.gremstudio.gauntlet.ext.GauntletSettingsExt;
import io.gremstudio.gauntlet.ext.LoaderExt;
import io.gremstudio.gauntlet.ext.ModDetailsExt;
import io.gremstudio.gauntlet.util.FileUtil;
import io.gremstudio.gauntlet.util.Loaders;
import net.fabricmc.loom.api.LoomGradleExtensionAPI;
import net.fabricmc.loom.api.fabricapi.FabricApiExtension;
import net.fabricmc.loom.configuration.ide.RunConfigSettings;
import net.neoforged.moddevgradle.dsl.NeoForgeExtension;
import net.neoforged.moddevgradle.dsl.RunModel;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.*;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.attributes.Attribute;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.plugins.BasePluginExtension;
import org.gradle.api.plugins.JavaLibraryPlugin;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.provider.Provider;
import org.gradle.api.publish.PublishingExtension;
import org.gradle.api.publish.maven.MavenPublication;
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin;
import org.gradle.api.tasks.AbstractCopyTask;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.SourceTask;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.compile.JavaCompile;
import org.gradle.api.tasks.javadoc.Javadoc;
import org.gradle.external.javadoc.CoreJavadocOptions;
import org.gradle.jvm.tasks.Jar;
import org.gradle.jvm.toolchain.JavaLanguageVersion;
import org.gradle.language.jvm.tasks.ProcessResources;

import java.io.File;
import java.net.URI;
import java.util.*;

public class GauntletPlugin implements Plugin<Project> {
    public static boolean RELEASE_MODE = false;

    @Override
    public void apply(Project project) {
        if (project.getRootProject().equals(project)) {
            RELEASE_MODE = Boolean.parseBoolean(project.getProviders().environmentVariable("RELEASE_MODE").getOrElse("false"));
            project.getLogger().lifecycle((RELEASE_MODE) ? "Release Mode on" : "Release Mode off");
        } else {
            project.getPluginManager().apply(MavenPublishPlugin.class);
        }

        ObjectFactory objs = project.getObjects();
        project.getLogger().lifecycle("Gauntlet Mode");

        GauntletExt gremExt = project.getExtensions().create("gauntlet", GauntletExt.class, objs);
        GauntletExt gremRoot = project.getRootProject().getExtensions().getByType(GauntletExt.class);
        project.getPluginManager().apply(JavaLibraryPlugin.class); // Mods require java, obviously.
        gremRoot.getGremSettings().getExportJavadocJar().convention("false");
        gremRoot.getGremSettings().getQuietJavadocExport().convention("true");

        ModDetailsExt modDetails = gremRoot.getModDetails(); // Mod details are a root only thing.
        GauntletSettingsExt gremSettings = gremRoot.getGremSettings(); // So are gremsettings

        registerTasks(project);

        // Seems to be needed if I want to check if its there or not.
        project.afterEvaluate((proj) -> {
            //Todo: Can I have a better way to differentiate this? Perhaps if I could do a root project check in some regard?
            if (gremExt.getLoader().getLoader().isPresent()) { // Hack to get around the fact that I dont think the extension is null at this point
                LoaderExt loader = gremExt.getLoader();
                project.getLogger().lifecycle("Loader " + loader.getLoader().get().getName() + " found, project using loader mode.");
                commonBuild(project, modDetails, loader, gremSettings);

                if (gremExt.getLoader().getLoader().get().equals(Loaders.COMMON)) {
                    configureCommon(project, modDetails, loader, gremSettings);
                } else if (gremExt.getLoader().getLoader().get().equals(Loaders.FABRIC)) {
                    configureFabric(project, modDetails, loader);
                } else if (gremExt.getLoader().getLoader().get().equals(Loaders.NEOFORGE)) {
                    configureNeoforge(project, modDetails, loader);
                }

                project.getRepositories().maven((maven) -> {
                    maven.setName("MSRandom");
                    maven.setUrl("https://maven.msrandom.net/repository/root/");
                });

                project.getDependencies().add("annotationProcessor", "net.msrandom:multiplatform-processor:1.0.7");
                project.getDependencies().add("compileOnly", "net.msrandom:multiplatform-annotations:1.0.0");

                ((JavaCompile)project.getTasks().getByName("compileJava")).getOptions().getCompilerArgs().add("-AgenerateExpectStubs");
            }
        });
    }

    private void registerTasks(Project project) {
        /*
        project.getTasks().register("gauntletTest", GauntletTestTask.class, (action) -> {
            action.setGroup("gauntlet");
            action.setDescription("Test lmfao");
            action.getTestModDetails().set(modDetails);
            //this.testLoaderExt.set(loader)
        });
         */
    }

    // Implements stuff thats shared amongst all loaders. Essentially, multiloader-common.
    private void commonBuild(Project project, ModDetailsExt modDetails, LoaderExt loader, GauntletSettingsExt settings) {
        addCommonMavens(project);
        boolean isMultiloader = modDetails.getLoaders().get().size() > 1;

        if (isMultiloader) {
            //modDetails.modVersion+loader.getName()-${minecraftVersion}
            project.setVersion(modDetails.getModVersion().get() + "+" + loader.getLoader().get().getName().toLowerCase(Locale.ROOT) + "-" + modDetails.getMinecraftVersion().get());
        } else {
            project.setVersion(modDetails.getModVersion().get() + "+" + modDetails.getMinecraftVersion().get());
        }

        if (!RELEASE_MODE) {
            project.setVersion(project.getVersion() + "-SNAPSHOT");
        }

        project.getExtensions().getByType(BasePluginExtension.class).getArchivesName().set(modDetails.getModID().get());
        JavaPluginExtension javaPlugin = project.getExtensions().getByType(JavaPluginExtension.class);
        javaPlugin.getToolchain().getLanguageVersion().set(JavaLanguageVersion.of(settings.getJavaVersion().get()));
        javaPlugin.withSourcesJar();
        // Im iffy on if I should do this.
        if (Boolean.parseBoolean(settings.getExportJavadocJar().get())) {
            javaPlugin.withJavadocJar();
        }

        String[] elements = new String[]{"apiElements", "runtimeElements", "sourcesElements"/*, "javadocElements"*/};
        for (String element : elements) {
            project.getConfigurations().getByName(element).outgoing(configurationPublications -> {
                configurationPublications.capability(project.getGroup() + ":" + modDetails.getModID().get() + ":" + modDetails.getModVersion().get());
                configurationPublications.capability(project.getGroup() + ":" + modDetails.getModID().get() + ":" + modDetails.getModVersion().get() + "+" + modDetails.getMinecraftVersion().get());
                if (isMultiloader) {
                    configurationPublications.capability(project.getGroup() + ":" + modDetails.getModID().get() + ":" + modDetails.getModVersion().get() + "+" + project.getName() + modDetails.getMinecraftVersion().get());
                }
            });

            project.getExtensions().getByType(PublishingExtension.class).getPublications().configureEach(publication -> {
                if (publication instanceof MavenPublication) {
                    ((MavenPublication) publication).suppressPomMetadataWarningsFor(element);
                }
            });
        }

        project.getTasks().getByName("sourcesJar", task -> {
            renameLicense(project, modDetails, (Jar)task);
        });

        project.getTasks().getByName("jar", task -> {
            Jar jarTask = ((Jar) task);

            renameLicense(project, modDetails, jarTask);
            jarTask.manifest(manifest -> {
                Map<String, String> map = new HashMap<>();
                map.put("Specification-Title", modDetails.getMetadata().getModName().get());
                map.put("Specification-Vendor", modDetails.getMetadata().getAuthors().getFirst().getName());
                map.put("Specification-Version", jarTask.getArchiveVersion().get());
                map.put("Implementation-Title", modDetails.getMetadata().getModName().get());
                map.put("Implementation-Vendor", modDetails.getMetadata().getAuthors().getFirst().getName());
                map.put("Implementation-Version", jarTask.getArchiveVersion().get());
                map.put("Built-On-Minecraft", modDetails.getMinecraftVersion().get());
                manifest.attributes(map);
            });
        });

        project.getTasks().getByName("javadoc", task -> {
            Javadoc docTask = (Javadoc) task;
            if (Boolean.parseBoolean(settings.getQuietJavadocExport().get()) && docTask.getOptions() instanceof CoreJavadocOptions options) {
                options.addStringOption("Xdoclint:-missing", "-quiet");
            }
        });

        // Will be replaced with commented version below, eventually at least.
        project.getTasks().getByName("processResources", task -> {
            ProcessResources processTask = (ProcessResources) task;

            Map<String, String> shared = new HashMap<>();
            shared.put("mod_id", modDetails.getModID().get());
            shared.put("version", modDetails.getModVersion().get());
            shared.put("mod_name", modDetails.getMetadata().getModName().get());
            shared.put("minecraft_version", modDetails.getMinecraftVersion().get());

            shared.put("description", modDetails.getMetadata().getDescription().get());

            shared.put("license", modDetails.getMetadata().getLicense().get());
            shared.put("icon", modDetails.getMetadata().getIcon().get());

            Map<String, Object> jsonShared = new HashMap<>();
            shared.forEach((name, replacement) -> jsonShared.put(name, replacement.replace("\n", "\\\\n")));

            processTask.filesMatching(List.of("META-INF/neoforge.mods.toml"), file -> file.expand(shared));
            processTask.filesMatching(List.of("pack.mcmeta", "fabric.mod.json"), file -> file.expand(jsonShared));
        });

        /*project.getTasks().getByName("processResources", task -> {
            ProcessResources processTask = (ProcessResources) task;

            Map<String, Object> shared = new HashMap<>();
            shared.put("mod_id", modDetails.getModID().get());
            shared.put("version", modDetails.getModVersion().get());
            shared.put("mod_name", modDetails.getMetadata().getModName().get());

            shared.put("license", modDetails.getMetadata().getLicense().get());
            shared.put("icon", modDetails.getMetadata().getIcon().get());

            if (loader.getLoader().get().equals(Loaders.FABRIC)) {
                processTask.filesMatching("fabric.mod.json", fileCopyDetails -> {
                    fileCopyDetails.expand(shared);
                    fileCopyDetails.filter((line) -> {
                        if (line.contains("grem/fabric_mod_authors")) {
                            List<Person> authors = modDetails.getMetadata().getAuthors();
                            List<String> stringifiedAuthors = new ArrayList<>();
                            for (Person author : authors) {
                                String authName = author.getName() + ((!author.getRole().isBlank()) ? (" - " + author.getRole()) : "");

                            }

                            return "";
                        }

                        return line;
                    });
                });
            }
        });*/

        setupMavenPublishingForProject(project, modDetails);
    }

    private void configureCommon(Project project, ModDetailsExt modDetails, LoaderExt loader, GauntletSettingsExt gremSettings) {
        project.getPluginManager().withPlugin("net.neoforged.moddev", (action) -> {
            NeoForgeExtension neoExt = project.getExtensions().getByType(NeoForgeExtension.class);
            neoExt.setNeoFormVersion(gremSettings.getNeoformVersion().get());

            File atFile = project.file("src/main/resources/META-INF/common.accesstransformer.cfg");
            if (atFile.exists()) {
                neoExt.accessTransformers(transformers -> {
                    transformers.from(atFile.getAbsolutePath());
                    transformers.publish(atFile);
                });
            }

            File intInjectFile = project.file("interfaces.json");
            if (intInjectFile.exists()) {
                neoExt.interfaceInjectionData(intInj -> {
                    intInj.from(intInjectFile.getAbsolutePath());
                    intInj.publish(intInjectFile);
                });
            }
        });


        project.getDependencies().add("compileOnly", "net.fabricmc:sponge-mixin:" + gremSettings.getFabricMixinVersion().get() + "+mixin." + gremSettings.getMixinVersion().get());
        project.getDependencies().add("compileOnly", "io.github.llamalad7:mixinextras-common:" + gremSettings.getMixinExtrasVersion().get());
        project.getDependencies().add("annotationProcessor", "io.github.llamalad7:mixinextras-common:" + gremSettings.getMixinExtrasVersion().get());

        Configuration commonJava = project.getConfigurations().create("commonJava", configuration -> {
            configuration.setCanBeResolved(false);
            configuration.setCanBeConsumed(true);
        });

        Configuration commonResources = project.getConfigurations().create("commonResources", configuration -> {
            configuration.setCanBeResolved(false);
            configuration.setCanBeConsumed(true);
        });

        SourceSetContainer sourceSets = project.getExtensions().getByType(SourceSetContainer.class);

        project.getArtifacts().add(commonJava.getName(), sourceSets.getByName("main").getJava().getSourceDirectories().getSingleFile());
        project.getArtifacts().add(commonResources.getName(), sourceSets.getByName("main").getResources().getSourceDirectories().getSingleFile());
    }

    private void sharedLoadersConfig(Project project, ModDetailsExt modDetails) {
        Configuration commonJava = project.getConfigurations().create("commonJava", configuration -> {
            configuration.setCanBeResolved(true);
        });

        Configuration commonResources = project.getConfigurations().create("commonResources", configuration -> {
            configuration.setCanBeResolved(true);
        });

        Dependency dep = project.getDependencies().create(project.project(":common"));

        ((ProjectDependency)dep).capabilities(cap -> cap.requireCapability(project.getGroup() + ":" + modDetails.getModID().get()));
        Attribute<String> loaderAttribute = Attribute.of("io.github.mcgradleconventions.loader", String.class);
        ((ProjectDependency)dep).attributes(attributes -> attributes.attribute(loaderAttribute, "common"));
        project.getConfigurations().getByName("compileOnly", compOnly -> {
            compOnly.getDependencies().add(dep);

        });

        project.getDependencies().add("commonJava", project.project(":common"));
        project.getDependencies().add("commonResources", project.project(":common"));



        TaskContainer tasks = project.getTasks();
        tasks.getByName("processResources", task -> {
            task.dependsOn(commonResources);
            ((AbstractCopyTask)task).from(commonResources);
        });

        tasks.getByName("compileJava", task -> {
            task.dependsOn(commonJava);
            ((SourceTask)task).source(commonJava);
        });

        tasks.getByName("javadoc", task -> {
            task.dependsOn(commonJava);
            ((SourceTask)task).source(commonJava);
        });

        tasks.getByName("sourcesJar", task -> {
            task.dependsOn(commonJava);
            ((AbstractCopyTask)task).from(commonJava);
            task.dependsOn(commonResources);
            ((AbstractCopyTask)task).from(commonResources);
        });
    }

    private void setupMavenPublishingForProject(Project project, ModDetailsExt modDetails) {
        String relMode = (RELEASE_MODE) ? "Snapshots" : "Release";

        PublishingExtension publish = project.getExtensions().getByType(PublishingExtension.class);

        publish.getPublications().register("mavenJava", MavenPublication.class, maven -> {
            maven.setArtifactId(modDetails.getModID().get());
            maven.from(project.getComponents().getByName("java"));
        });

        publish.getRepositories().maven(maven -> {
            maven.setUrl("https://mvn.devos.one/" + relMode.toLowerCase(Locale.ROOT));
            maven.setName("devOS");
            maven.getCredentials().setUsername(project.getProviders().environmentVariable("DEVOS_USERNAME").orElse(project.findProperty("devOSUsername").toString()).get());
            maven.getCredentials().setPassword(project.getProviders().environmentVariable("DEVOS_PASSWORD").orElse(project.findProperty("devOSPassword").toString()).get());
        });
    }

    private void configureFabric(Project project, ModDetailsExt modDetails, LoaderExt loader) {
        sharedLoadersConfig(project, modDetails);

        project.getPluginManager().withPlugin("net.fabricmc.fabric-loom", (action) -> {
            project.getDependencies().add("minecraft", "com.mojang:minecraft:" + modDetails.getMinecraftVersion().get());
            project.getDependencies().add("implementation", "net.fabricmc:fabric-loader:" + loader.getLoaderVersion().get());

            LoomGradleExtensionAPI gradleExtension = project.getExtensions().getByType(LoomGradleExtensionAPI.class);

            File awFile = project.project(":common").file("src/main/resources/" + modDetails.getModID().get() + ".classtweaker");
            if (awFile.exists()) {
                project.getLogger().lifecycle("TESTME");
                gradleExtension.getAccessWidenerPath().set(awFile);
                gradleExtension.getEnableTransitiveAccessWideners().set(true);
            }

            RunConfigSettings clientRun = gradleExtension.getRunConfigs().getByName("client");
            clientRun.client();
            clientRun.getDisplayName().set("Fabric Client");
            clientRun.getGenerateRunConfig().set(true);
            clientRun.getRunDirectory().set(FileUtil.createFolderWithProjectFile(project, "run/client"));

            RunConfigSettings serverRun = gradleExtension.getRunConfigs().getByName("server");
            serverRun.server();
            serverRun.getDisplayName().set("Fabric Server");
            serverRun.getGenerateRunConfig().set(true);
            serverRun.getRunDirectory().set(FileUtil.createFolderWithProjectFile(project, "run/server"));

            FabricApiExtension fapiExtension = project.getExtensions().getByType(FabricApiExtension.class);
            fapiExtension.configureDataGeneration((dataGen) -> {
                dataGen.getClient().set(true);
                dataGen.getOutputDirectory().set(FileUtil.createFolderWithProjectFile(project.project(":common"), "src/main/generated"));
            });

            gradleExtension.getRunConfigs().getByName("datagen").getDisplayName().set("Fabric Datagen");
        });
    }

    private void configureNeoforge(Project project, ModDetailsExt modDetails, LoaderExt loader) {
        sharedLoadersConfig(project, modDetails);

        JavaPluginExtension javaPlugin = project.getExtensions().getByType(JavaPluginExtension.class);

        project.getPluginManager().withPlugin("net.neoforged.moddev", (action) -> {
            NeoForgeExtension neoExt = project.getExtensions().getByType(NeoForgeExtension.class);

            neoExt.setVersion(loader.getLoaderVersion().get());
            File atFile = project.project(":common").file("src/main/resources/META-INF/common.accesstransformer.cfg");
            if (atFile.exists()) {
                neoExt.accessTransformers(transformers -> {
                    transformers.from(atFile.getAbsolutePath());
                    transformers.publish(atFile);
                });
            }

            File intInjectFile = project.project(":common").file("interfaces.json");
            if (intInjectFile.exists()) {
                neoExt.interfaceInjectionData(intInj -> {
                    intInj.from(intInjectFile.getAbsolutePath());
                    intInj.publish(intInjectFile);
                });
            }

            NamedDomainObjectContainer<RunModel> runs = neoExt.getRuns();
            runs.configureEach(run -> {
                run.systemProperty("neoforge.enabledGameTestNamespaces", modDetails.getModID().get());
            });

            runs.register("client", clientRun -> {
                clientRun.client();
                clientRun.getGameDirectory().set(FileUtil.createFolderWithProjectFile(project, "run/client"));
                clientRun.getIdeName().set("Neoforge Client");
            });

            runs.register("server", serverRun -> {
                serverRun.server();
                serverRun.getGameDirectory().set(FileUtil.createFolderWithProjectFile(project, "run/server"));
                serverRun.getIdeName().set("Neoforge Server");
            });

            runs.register("data", datagenRun -> {
                datagenRun.clientData();
                datagenRun.getGameDirectory().set(FileUtil.createFolderWithProjectFile(project, "run/datagen"));
                datagenRun.getIdeName().set("Neoforge Datagen");
                datagenRun.getProgramArguments().addAll("--mod", modDetails.getModID().get(), "--all", "--output", project.getRootProject().file("src/generated/resources/").getAbsolutePath(), "--existing", project.getRootProject().file("src/main/resources/").getAbsolutePath());
            });

            neoExt.getMods().register(modDetails.getModID().get(), mod -> {
                mod.sourceSet(javaPlugin.getSourceSets().getByName("main"));
            });
        });

        javaPlugin.getSourceSets().getByName("main").resources(resources -> {
            resources.srcDir(project.project(":common").file("src/main/generated"));
        });
    }

    private void renameLicense(Project project, ModDetailsExt modDetails, Jar task) {
        task.from(project.getRootProject().file("LICENSE"), file -> {
            file.rename(name -> name + "_" + modDetails.getMetadata().getModName());
        });
    }

    private void addCommonMavens(Project project) {
        RepositoryHandler repos = project.getRepositories();
        repos.mavenLocal(); // Like half of the development Im doing rn is relying on local you will need local.
        repos.mavenCentral(); // Neoforge has requirements for this I think.

        // A bit unsure how to declare exclusive content for this one. It shouldn't be a big issue but still.
        repos.maven(repo -> {
            repo.setName("FabricMC");
            repo.setUrl(URI.create("https://maven.fabricmc.net"));
        });
        // BlameJared maven is probably not needed but who knows
        repos.maven(repo -> {
            repo.setName("BlameJared");
            repo.setUrl(URI.create("https://maven.blamejared.com"));
        });
        // You probably have it in settings at least, but still.
        repos.maven(repo -> {
            repo.setName("DevOS Snapshots");
            repo.setUrl(URI.create("https://mvn.devos.one/snapshots/"));
        });
    }

    // Rough outline of the extention and stuff.
    /* build.gradle.kts:
        gauntlet {
            loaders = [NEOFORGE, FABRIC] // Probably no support for Lexforge unless I really wanna make something in <1.20
            minecraftVersion = 26.1.2 // Not gonna do multi-version, I feel thats a bit too much for the mods made.
            modID = "gremlib"
            metadata { // Yes this is based on the Cloche metadata. I like what I was seeing to be fair.
                modName = "Gremlib"
                version = 0.1.0 // The loader and mc versions would be appended on.
                description = "The library used for various mods."
                license = "MIT"
                author = person.name("Siuol")
                contributor.add(person.name("Siuol"))
            }
        }
     */
    /* common/build.gradle.kts:
        gauntlet {
            common {
                classTweaker = "${modID}.classtweaker"
                mixin = "${modID}.mixin.json"
            }
        }
     */
    /* fabric/build.gradle.kts
        gauntlet {
            fabric {
                mixin = "${modID}.fabric.mixin.json" // So my idea is that you can define a classtweaker and mixin json for your loader
            }
        }
     */
    /*
        gauntlet {
            neoforge {
                mixin = "${modID}.neoforge.mixin.json"
            }
        }
     */
}
