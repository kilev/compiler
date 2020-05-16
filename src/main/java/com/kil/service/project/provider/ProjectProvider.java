package com.kil.service.project.provider;

import com.kil.service.project.domain.Project;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;

public interface ProjectProvider {

    @Nullable
    Project load(@Nonnull File file);

    void save(@Nonnull Project project);

    void save(@Nonnull Project project, @Nonnull File directory, @Nonnull String name);
}
