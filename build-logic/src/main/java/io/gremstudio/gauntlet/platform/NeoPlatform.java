package io.gremstudio.gauntlet.platform;

import io.gremstudio.gauntlet.ext.GauntletSettingsExt;
import io.gremstudio.gauntlet.ext.LoaderExt;
import io.gremstudio.gauntlet.ext.ModDetailsExt;
import io.gremstudio.gauntlet.util.FileUtil;
import io.gremstudio.gauntlet.util.RunUtil;
import net.neoforged.moddevgradle.dsl.NeoForgeExtension;
import net.neoforged.moddevgradle.dsl.RunModel;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPluginExtension;

import java.io.File;

public class NeoPlatform extends AbstractLoaderPlatform {
    public NeoPlatform() {
        super("neoforge");
    }

    @Override
    public void configurePlatform(Project project, ModDetailsExt modDetails, LoaderExt loader, GauntletSettingsExt gremSettings) {
        linkToCommon(project, modDetails);

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

            runs.clear();

            runs.register("client", clientRun -> {
                clientRun.client();
                clientRun.getJvmArguments().addAll(RunUtil.additionalJVMArgs());
                clientRun.getGameDirectory().set(FileUtil.createFolderWithProjectFile(project, "run/client"));
                clientRun.getIdeName().set("Neoforge Client (:neoforge)");
            });

            runs.register("server", serverRun -> {
                serverRun.server();
                serverRun.getJvmArguments().addAll(RunUtil.additionalJVMArgs());
                serverRun.getGameDirectory().set(FileUtil.createFolderWithProjectFile(project, "run/server"));
                serverRun.getIdeName().set("Neoforge Server (:neoforge)");
            });

            runs.register("data", datagenRun -> {
                datagenRun.clientData();
                datagenRun.getJvmArguments().addAll(RunUtil.additionalJVMArgs());
                datagenRun.getGameDirectory().set(FileUtil.createFolderWithProjectFile(project, "run/datagen"));
                datagenRun.getIdeName().set("Neoforge Datagen (:neoforge)");
                datagenRun.getProgramArguments().addAll("--mod", modDetails.getModID().get(), "--all", "--output", project.getRootProject().file("src/generated/resources/").getAbsolutePath(), "--existing", project.getRootProject().file("src/main/resources/").getAbsolutePath());
            });

            neoExt.getMods().register(modDetails.getModID().get(), mod -> {
                mod.sourceSet(javaPlugin.getSourceSets().named("main").get());
            });
        });

        javaPlugin.getSourceSets().named("main", main -> {
            main.resources(resources -> {
                resources.srcDir(project.project(":common").file("src/main/generated"));
            });
        });
    }
}
