package com.kil.service.compiler.lexis;

import com.google.common.collect.Lists;
import com.kil.service.compiler.entity.LexingResult;
import com.kil.service.compiler.entity.Token;
import com.kil.service.compiler.lexis.domain.Operator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

enum LexerStateDepr {
    WORD_STATE() {
        @Override
        boolean checkCredentials(Character character) {
            return Character.isLetter(character);
        }

        @Override
        void processCharacter(Character character) {
//            if()

        }
    },
    INTEGER_STATE() {
        @Override
        boolean checkCredentials(Character character) {
            return Character.isDigit(character);
        }

        @Override
        void processCharacter(Character character) {

        }
    },
    OPERATOR_STATE() {
        @Override
        boolean checkCredentials(Character character) {
            return Stream.of(Operator.values())
                    .anyMatch(operator -> operator.getValue().contains(character.toString()));
        }

        @Override
        void processCharacter(Character character) {
            Optional<String> matchedOperator = Stream.of(Operator.values())
                    .filter(operator -> operator.getValue().contains(character.toString()))
                    .map(Operator::getValue)
                    .findAny();

        }
    };

    private static List<Token> tokens = new ArrayList<>();
    private static List<String> errors = new ArrayList<>();
    private static LexerStateDepr previusState;
    private static List<Character> charSequence = new ArrayList<>();


    abstract boolean checkCredentials(Character character);

    abstract void processCharacter(Character character);

    public void analyze(String code) {
        List<Character> codeCharacters = Lists.charactersOf(code);

        codeCharacters.forEach(character -> {
            //state for continue process char
            LexerStateDepr state = Stream.of(LexerStateDepr.values())
                    .filter(lexerStateDepr -> lexerStateDepr.checkCredentials(character))
                    .findAny()
                    .orElseThrow(() -> new IllegalArgumentException("Invalid lexing character: " + character.toString()));


        });


    }

    public LexingResult getResult() {
        LexingResult result = new LexingResult();
        result.setTokens(tokens);
        result.setErrors(errors);
        finish();
        return result;
    }

    private void finish() {
        tokens = new ArrayList<>();
        errors = new ArrayList<>();
        charSequence = new ArrayList<>();
        previusState = null;
    }


}
