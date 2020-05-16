package com.kil.service.project.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kil.service.project.domain.Project;
import com.kil.service.project.domain.ProjectSettings;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;

public class FileProjectProvider implements ProjectProvider {

    private static final String DIR_SEPARATOR = File.separator;

    @Nullable
    @Override
    public Project load(@Nonnull File file) {
        try {
            return new ObjectMapper().readValue(file, Project.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(@Nonnull Project project) {
        ProjectSettings settings = project.getSetting();

        save(project, settings.getDirectory(), settings.getName());
    }

    @Override
    public void save(@Nonnull Project project, @Nonnull File directory, @Nonnull String name) {
        try {
            new ObjectMapper().writeValue(new File(directory + DIR_SEPARATOR + name), project);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
