package io.gremstudio.gauntlet.platform;

import io.gremstudio.gauntlet.ext.GauntletSettingsExt;
import io.gremstudio.gauntlet.ext.LoaderExt;
import io.gremstudio.gauntlet.ext.ModDetailsExt;
import org.gradle.api.Project;

public abstract class AbstractPlatform {
    String platformName;

    public AbstractPlatform(String platformName) {
        this.platformName = platformName;
    }

    public abstract void configurePlatform(Project project, ModDetailsExt modDetails, LoaderExt loader, GauntletSettingsExt gremSettings);

    public String getPlatformName() {
        return this.platformName;
    }

}
