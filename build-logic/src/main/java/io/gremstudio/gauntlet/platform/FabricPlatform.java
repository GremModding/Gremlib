package io.gremstudio.gauntlet.platform;

import io.gremstudio.gauntlet.ext.GauntletSettingsExt;
import io.gremstudio.gauntlet.ext.LoaderExt;
import io.gremstudio.gauntlet.ext.ModDetailsExt;
import io.gremstudio.gauntlet.util.FileUtil;
import io.gremstudio.gauntlet.util.RunUtil;
import net.fabricmc.loom.LoomCompanionGradlePlugin;
import net.fabricmc.loom.api.LoomGradleExtensionAPI;
import net.fabricmc.loom.api.fabricapi.FabricApiExtension;
import org.gradle.api.Project;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;

import java.io.File;

public class FabricPlatform extends AbstractLoaderPlatform {
    public FabricPlatform() {
        super("fabric");
    }

    @Override
    public void configurePlatform(Project project, ModDetailsExt modDetails, LoaderExt loader, GauntletSettingsExt gremSettings){
        linkToCommon(project, modDetails);


        project.getPluginManager().withPlugin("net.fabricmc.fabric-loom", (action) -> {
            project.project(":common").getPluginManager().apply(LoomCompanionGradlePlugin.class);

            project.getDependencies().add("minecraft", "com.mojang:minecraft:" + modDetails.getMinecraftVersion().get());
            project.getDependencies().add("implementation", "net.fabricmc:fabric-loader:" + loader.getLoaderVersion().get());

            LoomGradleExtensionAPI gradleExtension = project.getExtensions().getByType(LoomGradleExtensionAPI.class);

            gradleExtension.mods(mod -> mod.create(modDetails.getModID().get(), settings -> {
                SourceSet projMain = project.getExtensions().getByType(SourceSetContainer.class).named("main").get();
                SourceSet commonMain = project.project(":common").getExtensions().getByType(SourceSetContainer.class).named("main").get();

                settings.sourceSet(projMain);
                settings.sourceSet(commonMain);
            }));

            File awFile = project.project(":common").file("src/main/resources/" + modDetails.getModID().get() + ".classtweaker");
            if (awFile.exists()) {
                gradleExtension.getAccessWidenerPath().set(awFile);
                gradleExtension.getEnableTransitiveAccessWideners().set(true);
            }

            gradleExtension.getRunConfigs().named("client", (config) -> {
                config.client();
                config.getDisplayName().set("Fabric Client");
                config.getGenerateRunConfig().set(true);
                config.getJvmArguments().addAll(RunUtil.additionalJVMArgs());
                config.getRunDirectory().set(FileUtil.createFolderWithProjectFile(project, "run/client"));
            });

            gradleExtension.getRunConfigs().named("server", (config) -> {
                config.server();
                config.getDisplayName().set("Fabric Server");
                config.getGenerateRunConfig().set(true);
                config.getJvmArguments().addAll(RunUtil.additionalJVMArgs());
                config.getRunDirectory().set(FileUtil.createFolderWithProjectFile(project, "run/server"));
            });

            FabricApiExtension fapiExtension = project.getExtensions().getByType(FabricApiExtension.class);
            fapiExtension.configureDataGeneration((dataGen) -> {
                dataGen.getClient().set(true);
                dataGen.getOutputDirectory().set(FileUtil.createFolderWithProjectFile(project.project(":common"), "src/main/generated"));
            });

            gradleExtension.getRunConfigs().named("datagen", (config) -> {
                config.getDisplayName().set("Fabric Datagen");
                config.getJvmArguments().addAll(RunUtil.additionalJVMArgs());
            });
        });
    }
}
