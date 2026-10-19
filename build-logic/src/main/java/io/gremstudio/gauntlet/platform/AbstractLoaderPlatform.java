package io.gremstudio.gauntlet.platform;

import io.gremstudio.gauntlet.ext.ModDetailsExt;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.ProjectDependency;
import org.gradle.api.attributes.Attribute;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;

public abstract class AbstractLoaderPlatform extends AbstractPlatform {
    String loaderVersion;

    public AbstractLoaderPlatform(String platformName) {
        super(platformName);
    }

    public void linkToCommon(Project project, ModDetailsExt modDetails) {
        if (project.findProject(":common") != null) {
            SourceSet projMain = project.getExtensions().getByType(SourceSetContainer.class).named("main").get();
            SourceSet commonMain = project.project(":common").getExtensions().getByType(SourceSetContainer.class).named("main").get();
            projMain.getJava().srcDirs(commonMain.getJava().getSrcDirs());
            projMain.getResources().srcDirs(commonMain.getResources().getSrcDirs());

            Dependency dep = project.getDependencies().create(project.project(":common"));

            ((ProjectDependency) dep).capabilities(cap -> cap.requireCapability(project.getGroup() + ":" + modDetails.getModID().get()));
            Attribute<String> loaderAttribute = Attribute.of("io.github.mcgradleconventions.loader", String.class);
            ((ProjectDependency) dep).attributes(attributes -> attributes.attribute(loaderAttribute, "common"));
            project.getConfigurations().named("compileOnly", config -> config.getDependencies().add(dep));
        }
    }
}
