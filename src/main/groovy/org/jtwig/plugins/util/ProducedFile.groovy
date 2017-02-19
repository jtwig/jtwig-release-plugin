package org.jtwig.plugins.util

import org.gradle.api.Project
import org.jtwig.plugins.config.ReleaseExtension


public enum ProducedFile {
    JAR("%s-%s.jar"),
    POM("%s-%s.pom"),
    SOURCES_JAR("%s-%s-sources.jar"),
    JAVADOC_JAR("%s-%s-javadoc.jar");

    private final String pattern;

    ProducedFile(String pattern) {
        this.pattern = pattern;
    }

    public File file (Project project) {
        ReleaseExtension releaseExtension = ReleaseExtension.retrieve(project);
        File destinationDir = DestinationDir.destinationDir(project);
        return new File(destinationDir, fileName(project, releaseExtension.getVersion()));
    }

    private String fileName(Project project, String version) {
        String.format(pattern, project.getName(), version)
    }

    public String bintrayPath(Project project) {
        ReleaseExtension releaseExtension = ReleaseExtension.retrieve(project);
        return String.format("%s/%s/%s/%s",
                project.group.toString().replace(".", "/"),
                project.name, releaseExtension.getVersion(), fileName(project, releaseExtension.getVersion()));
    }
}