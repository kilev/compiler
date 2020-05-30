package com.kil.service.compiler.lexis;

import com.google.common.collect.Lists;
import com.kil.common.Utils;
import com.kil.service.compiler.entity.LexingResult;
import com.kil.service.compiler.lexis.domain.CodeCharacter;
import com.kil.service.compiler.lexis.domain.Operator;
import com.kil.service.compiler.lexis.state.IntegerState;
import com.kil.service.compiler.lexis.state.LexingState;
import com.kil.service.compiler.lexis.state.OperatorState;
import com.kil.service.compiler.lexis.state.UnsupportedState;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class Lexer {

    public LexingResult analyze(String code) {
        LexingState previousState = null;

        for (CodeCharacter codeCharacter : getCodeCharacters(code)) {
            LexingState state = chooseState(codeCharacter);
            state.fillFromPrevious(previousState);
            state.process(codeCharacter);
            previousState = state;
        }

        if (previousState != null && !previousState.getCodeCharacterSequence().isEmpty()) {
            previousState.handleSequence();
        }

        LexingResult result = new LexingResult();
        result.setTokens(previousState == null ? new ArrayList<>() : previousState.getTokens());
        result.setErrors(previousState == null ? new ArrayList<>() : previousState.getErrors());
        return result;
    }

    private LexingState chooseState(CodeCharacter codeCharacter) {
        if (Operator.allowCharacter(codeCharacter.getCharacter())) {
            return new OperatorState();
        } else if (Character.isDigit(codeCharacter.getCharacter())) {
            return new IntegerState();
        }
        return new UnsupportedState();
    }

    private List<CodeCharacter> getCodeCharacters(String code) {
        List<CodeCharacter> codeCharacters = new ArrayList<>();

        int line = 0;
        int index = 0;
        for (Character character : Lists.charactersOf(code)) {
            CodeCharacter codeCharacter = new CodeCharacter(character, line, index);
            codeCharacters.add(codeCharacter);

            if (character.equals(Utils.getLineSeparator())) {
                line++;
                index = 0;
            } else {
                index++;
            }
        }
        return codeCharacters;
    }
}
