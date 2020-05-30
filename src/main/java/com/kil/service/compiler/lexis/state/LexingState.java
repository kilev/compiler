package com.kil.service.compiler.lexis.state;

import com.kil.service.compiler.entity.Token;
import com.kil.service.compiler.lexis.LexingUtils;
import com.kil.service.compiler.lexis.domain.CodeCharacter;
import lombok.Data;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Data
public abstract class LexingState {
    private List<Token> tokens = new ArrayList<>();
    private List<String> errors = new ArrayList<>();
    private LexingState previousState = null;
    private List<CodeCharacter> codeCharacterSequence = new ArrayList<>();

    public abstract void process(@Nonnull CodeCharacter codeCharacter);

    public abstract void handleSequence();

    public void fillFromPrevious(@Nullable LexingState previousState) {
        if (previousState == null) {
            return;
        }
        this.tokens = previousState.tokens;
        this.errors = previousState.errors;
        this.previousState = previousState;
        this.codeCharacterSequence = previousState.codeCharacterSequence;
    }

    protected void addTokenFromCharSequence() {
        String tokenValue = LexingUtils.getString(codeCharacterSequence);
        CodeCharacter firstTokenCharacter = getCodeCharacterSequence().get(0);// token must contain at least one character
        Token token = new Token(tokenValue, firstTokenCharacter.getLine(), firstTokenCharacter.getIndex());
        tokens.add(token);
    }

    protected void requestPreviousStateToHandleCharSequence() {
        if (!getCodeCharacterSequence().isEmpty()) {
            previousState.handleSequence();
        }
        codeCharacterSequence = new ArrayList<>();
    }


}
