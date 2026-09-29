package io.gremstudio.gauntlet.util;

import org.gradle.api.Project;

import java.io.File;

public class FileUtil {
    public static File createFolderWithProjectFile(Project project, String path) {
        File file = project.file(path);
        file.mkdirs();
        return file;
    }
}
