package com.kil.service.compiler.lexis;

import lombok.Data;

@Data
public class CodeCharacter {
    private final Character character;
    private final int line;
    private final int index;
}
