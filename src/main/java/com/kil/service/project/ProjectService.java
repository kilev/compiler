package com.kil.service.project;

import java.io.File;

public interface ProjectService {

    void newProject();

    void saveProject();

    void saveProjectAs(File directory, String name);

    void checkToSave();

    void checkToSaveBeforeNewProject();

    void checkToSaveBeforeOpenProject();

    void changeCode(String newCode);
}
