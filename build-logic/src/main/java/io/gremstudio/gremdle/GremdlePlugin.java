package io.gremstudio.gremdle;

import com.google.gson.FormattingStyle;
import com.google.gson.GsonBuilder;
import io.gremstudio.gremdle.ext.GremSettingsExt;
import io.gremstudio.gremdle.ext.LoaderExt;
import io.gremstudio.gremdle.ext.ModDetailsExt;
import io.gremstudio.gremdle.metadata.Person;
import io.gremstudio.gremdle.util.Loaders;
import org.apache.tools.ant.filters.ReplaceTokens;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.plugins.BasePluginExtension;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.publish.PublishingExtension;
import org.gradle.api.publish.maven.MavenPublication;
import org.gradle.api.tasks.javadoc.Javadoc;
import org.gradle.external.javadoc.CoreJavadocOptions;
import org.gradle.jvm.tasks.Jar;
import org.gradle.jvm.toolchain.JavaLanguageVersion;
import org.gradle.language.jvm.tasks.ProcessResources;

import java.io.FilterReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GremdlePlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        ObjectFactory objs = project.getObjects();
        project.getLogger().lifecycle("Gremdle Mode");

        GremdleExt gremExt = project.getExtensions().create("gremdle", GremdleExt.class, objs);
        GremdleExt gremRoot = project.getRootProject().getExtensions().getByType(GremdleExt.class);
        gremRoot.getGremSettings().getExportJavadocJar().convention("false");
        gremRoot.getGremSettings().getQuietJavadocExport().convention("true");

        ModDetailsExt modDetails = gremRoot.getModDetails(); // Mod details are a root only thing.
        GremSettingsExt gremSettings = gremRoot.getGremSettings(); // So are gremsettings

        registerTasks(project);

        // Seems to be needed if I want to check if its there or not.
        project.afterEvaluate((proj) -> {
            //Todo: Can I have a better way to differentiate this? Perhaps if I could do a root project check in some regard?
            if (gremExt.getLoader().getLoader().isPresent()) { // Hack to get around the fact that I dont think the extension is null at this point
                LoaderExt loader = gremExt.getLoader();
                project.getLogger().lifecycle("Loader " + loader.getLoader().get().getName() + " found, project using loader mode.");
                loaderBase(project, modDetails, loader, gremSettings);

                project.getPluginManager().withPlugin("net.fabricmc.fabric-loom", (action) -> {
                    project.getDependencies().add("minecraft", "com.mojang:minecraft:" + modDetails.getMinecraftVersion().get());
                    project.getDependencies().add("implementation", "net.fabricmc:fabric-loader:" + loader.getLoaderVersion().get());
                });
            }
        });
    }

    private void registerTasks(Project project) {
        /*
        project.getTasks().register("gremdleTest", GremdleTestTask.class, (action) -> {
            action.setGroup("gremdle");
            action.setDescription("Test lmfao");
            action.getTestModDetails().set(modDetails);
            //this.testLoaderExt.set(loader)
        });
         */
    }

    // Implements stuff thats shared amongst all loaders. Yeah this is basically multiloader-common
    private static void loaderBase(Project project, ModDetailsExt modDetails, LoaderExt loader, GremSettingsExt settings) {
        addCommonMavens(project);
        boolean isMultiloader = modDetails.getLoaders().get().size() > 1;

        if (isMultiloader) {
            //modDetails.modVersion+loader.getName()-${minecraftVersion}
            project.setVersion(modDetails.getModVersion().get() + "+" + loader.getLoader().get().getName() + "-" + modDetails.getMinecraftVersion().get());
        } else {
            project.setVersion(modDetails.getModVersion().get() + "+" + modDetails.getMinecraftVersion().get());
        }

        project.getExtensions().getByType(BasePluginExtension.class).getArchivesName().set(modDetails.getModID().get());
        JavaPluginExtension javaPlugin = project.getExtensions().getByType(JavaPluginExtension.class);
        javaPlugin.getToolchain().getLanguageVersion().set(JavaLanguageVersion.of(settings.getJavaVersion().get()));
        javaPlugin.withSourcesJar();
        // Im iffy on if I should do this.
        if (Boolean.parseBoolean(settings.getExportJavadocJar().get())) {
            javaPlugin.withJavadocJar();
        }

        String[] elements = new String[]{"apiElements", "runtimeElements", "sourcesElements", "javadocElements"};
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
    }

    private static void renameLicense(Project project, ModDetailsExt modDetails, Jar task) {
        task.from(project.getRootProject().file("LICENSE"), file -> {
            file.rename(name -> name + "_" + modDetails.getMetadata().getModName());
        });
    }

    private static void addCommonMavens(Project project) {
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
        gremdle {
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
        gremdle {
            common {
                classTweaker = "${modID}.classtweaker"
                mixin = "${modID}.mixin.json"
            }
        }
     */
    /* fabric/build.gradle.kts
        gremdle {
            fabric {
                mixin = "${modID}.fabric.mixin.json" // So my idea is that you can define a classtweaker and mixin json for your loader
            }
        }
     */
    /*
        gremdle {
            neoforge {
                mixin = "${modID}.neoforge.mixin.json"
            }
        }
     */
}
