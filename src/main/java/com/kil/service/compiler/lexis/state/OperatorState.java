package com.kil.service.compiler.lexis.state;

import com.kil.service.compiler.lexis.LexingUtils;
import com.kil.service.compiler.lexis.domain.CodeCharacter;
import com.kil.service.compiler.lexis.domain.Operator;

import javax.annotation.Nonnull;
import java.util.Optional;

public class OperatorState extends LexingState {

    @Override
    public void process(@Nonnull CodeCharacter codeCharacter) {
        String potentialTokenValue = LexingUtils.getString(getCodeCharacterSequence(), codeCharacter);

        if (Operator.startsWith(potentialTokenValue)) {
            getCodeCharacterSequence().add(codeCharacter);
        } else {
            requestPreviousStateToHandleCharSequence();
            if (Operator.startsWith(codeCharacter.getCharacter().toString())) {
                getCodeCharacterSequence().add(codeCharacter);
            }
        }
    }

    @Override
    public void handleSequence() {
        String tokenValue = LexingUtils.getString(getCodeCharacterSequence());

        Optional<Operator> operator = Operator.getOperator(tokenValue);
        if (operator.isPresent()) {
            addTokenFromCharSequence();
        } else {
            getErrors().add(getClass().getSimpleName() + ": found invalid token: " + tokenValue);
        }
    }
}
