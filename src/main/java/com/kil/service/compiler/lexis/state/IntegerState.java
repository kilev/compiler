package com.kil.service.compiler.lexis.state;

import com.kil.service.compiler.lexis.LexingUtils;
import com.kil.service.compiler.lexis.domain.CodeCharacter;

import javax.annotation.Nonnull;

public class IntegerState extends LexingState {
    @Override
    public void process(@Nonnull CodeCharacter codeCharacter) {
        //TODO remove assert after refactoring
        assert Character.isDigit(codeCharacter.getCharacter());

        boolean onlyDigits = LexingUtils.getCharacterStream(getCodeCharacterSequence(), codeCharacter)
                .allMatch(Character::isDigit);

        if (!onlyDigits) {
            requestPreviousStateToHandleCharSequence();
        }
        getCodeCharacterSequence().add(codeCharacter);
    }

    @Override
    public void handleSequence() {
        //TODO remove assert after refactoring
        boolean containsOnlyDigits = getCodeCharacterSequence().stream()
                .map(CodeCharacter::getCharacter)
                .allMatch(Character::isDigit);
        assert containsOnlyDigits;

        addTokenFromCharSequence();
    }


}
