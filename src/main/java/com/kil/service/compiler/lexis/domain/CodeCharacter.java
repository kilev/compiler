package com.kil.service.compiler.lexis.domain;

import lombok.Data;

@Data
public class CodeCharacter {
    private final Character character;
    private final int line;
    private final int index;
}
