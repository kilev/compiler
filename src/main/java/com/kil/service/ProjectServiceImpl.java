package com.kil.service;

import com.google.common.eventbus.EventBus;
import com.kil.common.command.Command;
import com.kil.common.event.CodeChangedEvent;
import com.kil.service.project.Project;
import com.kil.service.project.ProjectCondition;
import com.kil.service.project.ProjectSettings;
import com.kil.service.project.provider.FileProjectProvider;
import com.kil.service.project.provider.ProjectProvider;
import com.kil.view.DialogUtils;

import java.io.File;
import java.util.Objects;
import java.util.Optional;

public class ProjectServiceImpl implements ProjectService {

    private final EventBus eventBus;
    private final ProjectProvider provider;

    private Project project;

    public ProjectServiceImpl(EventBus eventBus) {
        this.eventBus = eventBus;
        this.provider = new FileProjectProvider();

        newProject();
    }

    @Override
    public void saveProject() {
        if (project.getCondition().isEditable()) {
            ProjectSettings settings = project.getSetting();
            if (settings.getDirectory() == null) {
                DialogUtils.chooseDirectory().ifPresent(directory -> {
                    settings.setDirectory(directory);
                    saveProject();
                });
            } else if (settings.getName() == null) {
                DialogUtils.getProjectName().ifPresent(name -> {
                    settings.setName(name);
                    saveProject();
                });
            } else {
                project.getCondition().setEditable(false);
                provider.save(project);
            }
        }

    }

    @Override
    public void saveProjectAs(File directory, String name) {
        provider.save(project, directory, name);
    }

    @Override
    public void checkToSaveBeforeNewProject() {
        checkToSaveBeforeAction(this::newProject);
    }

    @Override
    public void checkToSaveBeforeOpenProject() {
        checkToSaveBeforeAction(() -> {
            Optional<File> projectFile = DialogUtils.chooseFile();
            projectFile.ifPresent(file -> setProject(provider.load(file)));
        });
    }

    @Override
    public void newProject() {
        project = new Project();
        project.setCondition(new ProjectCondition());
        project.setSetting(new ProjectSettings());

        eventBus.post(new CodeChangedEvent(project.getCode()));
    }

    @Override
    public void changeCode(String newCode) {
        String existCode = project.getCode();

        if (Objects.equals(existCode, newCode)) {
            return;
        }
        project.setCode(newCode);
        project.getCondition().setEditable(true);
    }

    private void checkToSaveBeforeAction(Command afterSaveCommand) {
        if (project.getCondition().isEditable()) {
            DialogUtils.askUserForSaveProject(
                    () -> {
                        saveProject();
                        afterSaveCommand.execute();
                    },
                    afterSaveCommand
            );
        } else {
            afterSaveCommand.execute();
        }
    }

    private void setProject(Project newProject) {
        project = newProject;
        eventBus.post(new CodeChangedEvent(project.getCode()));
    }
}
