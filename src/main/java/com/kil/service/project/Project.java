package com.kil.service.project;

import lombok.Data;

@Data
public class Project {

    private ProjectSettings setting;
    private ProjectCondition condition;
    private String code;
}
