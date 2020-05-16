package com.kil.service.project.domain;

import lombok.Data;

import java.io.File;

@Data
public class ProjectSettings {

    private File directory;
    private String name;
}
