package com.kil.service.compiler.lexis.state;

import com.kil.service.compiler.lexis.domain.CodeCharacter;

import javax.annotation.Nonnull;

/**
 * use when all other states can't lex some character
 */
public class UnsupportedState extends LexingState {
    @Override
    public void process(@Nonnull CodeCharacter codeCharacter) {
        getErrors().add(getClass().getSimpleName() + ": found unsupported character: " + codeCharacter.getCharacter());

        requestPreviousStateToHandleCharSequence();
    }

    /**
     * this state don't provide any char sequence
     */
    @Override
    public void handleSequence() {

    }
}
