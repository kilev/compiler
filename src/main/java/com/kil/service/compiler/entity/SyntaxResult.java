package com.kil.service.compiler.entity;

import lombok.Data;

import java.util.List;

@Data
public class SyntaxResult {
    private List<Token> tokens;
    private List<String> errors;
}
