package io.gremstudio.gauntlet.task;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;

// What is plugin dev even about bro
@CacheableTask
public class NukeLoomCacheTask extends DefaultTask {
    @TaskAction
    public void run() {
        File projectDir = getProject().getProjectDir();
        File loomCacheLocation = new File(projectDir + "/build/loom-cache");
        if (loomCacheLocation.exists()) {
            loomCacheLocation.delete(); // WE BRING THE BOOM
        }

        File otherLoomCacheLocation = new File(projectDir + "/.gradle/loom-cache");
        if (otherLoomCacheLocation.exists()) {
            otherLoomCacheLocation.delete(); // WE BRING THE BOOM
        }
    }
}
