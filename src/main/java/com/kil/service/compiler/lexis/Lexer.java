package com.kil.service.compiler.lexis;

import com.kil.service.compiler.entity.LexingResult;
import com.kil.service.compiler.entity.Token;
import com.kil.service.compiler.entity.TokenType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Lexer {

    private static final String LEXER_ERROR_TEMPLATE = "Lexer error: ";
    private static final String UNSUPPORTED_CHARACTER_ERROR_TEMPLATE = LEXER_ERROR_TEMPLATE + "unsupported character: ";

    List<Token> tokens = new ArrayList<>();
    List<String> errors = new ArrayList<>();
    List<CodeCharacter> sequence = new ArrayList<>();

    public LexingResult analyze(String code) {
        List<CodeCharacter> codeCharacters = LexingUtils.getCodeCharacters(code);
        for (CodeCharacter codeCharacter : codeCharacters) {
            processCodeCharacter(codeCharacter);
        }
        resolveTokenFromCurrentSequence();

        tokens.removeIf(token -> token.getType() == TokenType.SPACE
                || token.getType() == TokenType.NEW_LINE_SEPARATOR);
        return new LexingResult(tokens, errors);
    }

    private void processCodeCharacter(CodeCharacter codeCharacter) {
        //add error if found unsupported character
        boolean supportedCharacter = Stream.of(TokenType.values())
                .anyMatch(tokenType -> tokenType.supportCharacter(codeCharacter.getCharacter()));
        if (!supportedCharacter) {
            errors.add(UNSUPPORTED_CHARACTER_ERROR_TEMPLATE + codeCharacter.getCharacter());
            resolveTokenFromCurrentSequence();
            return;
        }

        String currentSequence = LexingUtils.getString(sequence, codeCharacter);

        boolean hasEqualsStartSequence = Stream.of(TokenType.values())
                .anyMatch(tokenType -> tokenType.startsWith(currentSequence));

        if (!hasEqualsStartSequence) {
            resolveTokenFromCurrentSequence();
        }
        sequence.add(codeCharacter);
    }

    private void resolveTokenFromCurrentSequence() {
        if (sequence.isEmpty()) {
            return;
        }

        String sequenceValue = LexingUtils.getString(sequence);

        Optional<TokenType> tokenType = Stream.of(TokenType.values())
                .filter(type -> type.isTypeOf(sequenceValue))
                .findFirst();

        if (tokenType.isPresent()) {
            Token newToken = getToken(sequence, tokenType.get());
            tokens.add(newToken);
        } else {
            List<Token> newTokens = resolveDigitAndLetter(sequence);
            tokens.addAll(newTokens);
        }
        sequence.clear();
    }

    private List<Token> resolveDigitAndLetter(List<CodeCharacter> sequence) {
        return sequence.stream()
                .filter(codeCharacter -> TokenType.isDigitOrLetter(codeCharacter.getCharacter().toString()))
                .map(singleCodeCharacter -> {
                    TokenType type = null;
                    String characterValue = singleCodeCharacter.getCharacter().toString();
                    if (TokenType.DIGIT.isTypeOf(characterValue)) {
                        type = TokenType.DIGIT;
                    } else if (TokenType.LETTER.isTypeOf(characterValue)) {
                        type = TokenType.LETTER;
                    }
                    return getToken(Collections.singletonList(singleCodeCharacter), type);
                })
                .collect(Collectors.toList());
    }

    private Token getToken(List<CodeCharacter> sequence, TokenType type) {
        assert type != null && !sequence.isEmpty();

        String value = LexingUtils.getString(sequence);
        CodeCharacter firstCharacter = sequence.get(0);

        return new Token(type, value, firstCharacter.getLine(), firstCharacter.getIndex());
    }

}
