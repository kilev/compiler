package com.kil.service.project.domain;

import lombok.Data;

@Data
public class Project {

    private ProjectSettings setting;
    private ProjectCondition condition;
    private String code;
}
