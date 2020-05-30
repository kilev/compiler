package com.kil.service.compiler.lexis.domain;

import com.kil.common.Utils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
public enum Operator {
    EQUALLY("="),

    PLUS("+"),
    MINUS("-"),

    POWER("**"),
    MULTIPLE("*"),
    DIVIDE("/"),

    OPEN_PAREN("("),
    CLOSE_PAREN(")"),

    SPACE(" "),
    NEW_LINE_SEPARATOR(Utils.getLineSeparator().toString());

    @Getter
    private final String value;

    /**
     * @return true if operator's value has only one char, otherwise false
     */
    public static boolean isSimpleOperator(Operator operator) {
        return operator.getValue().length() == 1;
    }

    public static Optional<Operator> getOperator(String value) {
        return Stream.of(Operator.values())
                .filter(operator -> operator.getValue().equals(value))
                .findAny();
    }

    public static boolean allowCharacter(Character character) {
        return Stream.of(Operator.values())
                .map(Operator::getValue)
                .anyMatch(operatorValue -> operatorValue.contains(character.toString()));
    }

    public static boolean startsWith(String value) {
        return Stream.of(Operator.values())
                .map(Operator::getValue)
                .anyMatch(operatorValue -> operatorValue.startsWith(value));
    }

}
