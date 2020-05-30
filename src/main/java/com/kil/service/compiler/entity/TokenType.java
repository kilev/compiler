package com.kil.service.compiler.entity;

import com.kil.common.Utils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TokenType {
    EQUALLY("="),

    PLUS("+"),
    MINUS("-"),

    POWER("**"),

    MULTIPLE("*"),
    DIVIDE("/"),

    OPEN_PAREN("("),
    CLOSE_PAREN(")"),

    SPACE(" "),
    NEW_LINE_SEPARATOR(Utils.getLineSeparator().toString()),

    DIGIT(null) {
        @Override
        public boolean isTypeOf(String value) {
            return value.length() == 1 && Character.isDigit(value.charAt(0));
        }

        @Override
        public boolean supportCharacter(Character character) {
            return Character.isDigit(character);
        }
    },
    LETTER(null) {
        @Override
        public boolean isTypeOf(String value) {
            return value.length() == 1 && Character.isLetter(value.charAt(0));
        }

        @Override
        public boolean supportCharacter(Character character) {
            return Character.isLetter(character);
        }
    };

    @Getter
    private final String value;

//    public static Optional<Terminal> getOperator(String value) {
//        return Stream.of(Terminal.values())
//                .filter(terminal -> terminal.getValue().equals(value))
//                .findAny();
//    }
//
//    public static boolean allowCharacter(Character character) {
//        return Stream.of(Terminal.values())
//                .map(Terminal::getValue)
//                .anyMatch(operatorValue -> operatorValue.contains(character.toString()));
//    }
//
//    public static boolean startsWith(String value) {
//        return Stream.of(Terminal.values())
//                .map(Terminal::getValue)
//                .anyMatch(operatorValue -> operatorValue.startsWith(value));
//    }

    public boolean isTypeOf(String value) {
        assert this.value != null;
        return this.value.equals(value);
    }

    public boolean startsWith(String value) {
        return this.value != null && this.value.startsWith(value);
    }

    public boolean supportCharacter(Character character) {
        assert value != null;
        return value.contains(character.toString());
    }

    public static boolean isDigitOrLetter(String value) {
        return DIGIT.isTypeOf(value) || LETTER.isTypeOf(value);
    }
}
