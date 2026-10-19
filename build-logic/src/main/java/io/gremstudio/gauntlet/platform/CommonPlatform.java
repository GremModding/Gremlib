package io.gremstudio.gauntlet.platform;

import io.gremstudio.gauntlet.ext.GauntletSettingsExt;
import io.gremstudio.gauntlet.ext.LoaderExt;
import io.gremstudio.gauntlet.ext.ModDetailsExt;
import net.neoforged.moddevgradle.dsl.NeoForgeExtension;
import org.gradle.api.Project;

import java.io.File;

public class CommonPlatform extends AbstractPlatform {
    public CommonPlatform(String platformName) {
        super(platformName);
    }

    public void configurePlatform(Project project, ModDetailsExt modDetails, LoaderExt loader, GauntletSettingsExt gremSettings) {
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

    }
}
