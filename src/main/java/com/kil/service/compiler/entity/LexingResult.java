package com.kil.service.compiler.entity;

import lombok.Data;

import java.util.List;

@Data
public class LexingResult {
    private final List<Token> tokens;
    private final List<String> errors;
}
