package io.gremstudio.gremdle;

import io.gremstudio.gremdle.ext.LoaderExt;
import io.gremstudio.gremdle.ext.ModDetailsExt;
import net.fabricmc.loom.configuration.providers.minecraft.MinecraftJarConfiguration;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.model.ObjectFactory;


public class GremdlePlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        ObjectFactory objs = project.getObjects();
        project.getLogger().lifecycle("Gremdle Mode");

        GremdleExt gremExt = project.getExtensions().create("gremdle", GremdleExt.class, objs);
        ModDetailsExt modDetails = project.getRootProject().getExtensions().getByType(GremdleExt.class).getModDetails(); // Mod details are a root only thing.

        project.getTasks().register("gremdleTest", GremdleTestTask.class, (action) -> {
            action.setGroup("gremdle");
            action.setDescription("Test lmfao");
            action.getTestModDetails().set(modDetails);
            //this.testLoaderExt.set(loader)
        });

        // Seems to be needed if I want to check if its there or not.
        project.afterEvaluate((action) -> {
            project.getLogger().lifecycle("Loader test: " + gremExt.getLoader().getName().getOrElse("NO LOADER FOUND"));
            project.getLogger().lifecycle("ModDetails test: " + modDetails.getModID().getOrElse("NO ID FOUND"));
            if (!gremExt.getLoader().getName().getOrElse("").isEmpty()) { // Hack to get around the fact that I dont think the extension is null at this point
                LoaderExt loader = gremExt.getLoader();
                project.getLogger().lifecycle("Loader " + loader.getName() + " found, project using loader mode.");
                loaderMode(project, modDetails, loader);
            }
        });
    }

    private void loaderMode(Project project, ModDetailsExt modDetails, LoaderExt loader) {
        project.getPluginManager().withPlugin("net.fabricmc.fabric-loom", (action) -> {
            project.getDependencies().add("minecraft", "com.mojang:minecraft:" + modDetails.getMinecraftVersion().get());
            project.getDependencies().add("implementation", "net.fabricmc:fabric-loader:" + loader.getLoaderVersion().get());
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
