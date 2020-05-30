package com.kil.service.compiler.entity;

import lombok.Data;

@Data
public class Token {
    private final String value;
    private final int line;
    private final int index;
}
