package com.kil.service.match;

import com.google.common.collect.Lists;
import com.kil.common.Utils;
import com.kil.service.match.entity.MatchInfo;
import com.kil.service.match.entity.MatchResult;
import com.kil.service.match.state.NextState;
import com.kil.service.match.state.State;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@NotThreadSafe
@RequiredArgsConstructor
public class StateSearcher {

    private final String text;
    private final State startState;

    private final List<State> stateTrace = new ArrayList<>();
    private final List<MatchInfo> matches = new ArrayList<>();

    private State currentState;
    private StringBuilder matchBuilder;
    private Integer startIndex;

    @Nonnull
    public MatchResult search() {
        matchBuilder = new StringBuilder();

        List<String> lines = Arrays.stream(text.split(Utils.getLineSeparator().toString()))
                .collect(Collectors.toList());
        lines.forEach(line -> processLine(line, lines.indexOf(line)));

        return getResult();
    }

    private void processLine(String line, int lineNumber) {
        setTracedState(startState);

        String preparedLine = Character.MIN_VALUE + line + Character.MIN_VALUE;

        List<Character> lineCharacters = Lists.charactersOf(preparedLine);
        for (int i = 0; i < lineCharacters.size(); i++) {
            processChar(lineCharacters.get(i), lineNumber, i - 1);
        }
    }

    private void processChar(Character currentCharacter, int lineNumber, int characterIndex) {
        NewStateInfo newStateInfo = getNewStateInfo(currentState, currentCharacter);
        if (newStateInfo.isAppendCharacter()) {
            matchBuilder.append(currentCharacter);
            if (startIndex == null) {
                startIndex = characterIndex;
            }
        }

        State oldState = currentState;
        State newState = newStateInfo.getNewState();

        if (newState.isFinishState()) {
            //если прошли все состояния
            addSearchInfo(matchBuilder.toString(), lineNumber, startIndex, characterIndex);
            matchBuilder = new StringBuilder();
            startIndex = null;

            setTracedState(newState);
            setTracedState(startState);
            processChar(currentCharacter, lineNumber, characterIndex);

        } else if (oldState != newState && newState.isStartState()) {
            //если состояние сбросилось, начинаем сначала с этого же символа
            matchBuilder = new StringBuilder();
            startIndex = null;
            setTracedState(newState);
            processChar(currentCharacter, lineNumber, characterIndex);

        } else if (newState.isStartState()) {
            //если не был переход из нулевого состояния
            matchBuilder = new StringBuilder();
            startIndex = null;
            setTracedState(newState);

        } else {
            //если был переход на след состояния
            setTracedState(newState);
        }
    }

    @Nonnull
    private NewStateInfo getNewStateInfo(State currentState, Character character) {
        return currentState.getNextStates().stream()
                .filter(nextState -> nextState.getRequirement().check(character))
                .map(this::newStateInfo)
                .findAny()
                .orElse(createStartState());
    }

    @Nonnull
    private MatchResult getResult() {
        MatchResult result = new MatchResult();
        result.setMatchInfos(matches);
        result.setStateTrace(stateTrace);
        return result;
    }

    private void setTracedState(State newState) {
        currentState = newState;
        stateTrace.add(newState);
    }

    private NewStateInfo createStartState() {
        NewStateInfo newStateInfo = new NewStateInfo();
        newStateInfo.setNewState(startState);
        newStateInfo.setAppendCharacter(false);
        return newStateInfo;
    }

    private NewStateInfo newStateInfo(NextState nextState) {
        NewStateInfo newStateInfo = new NewStateInfo();
        newStateInfo.setNewState(nextState.getState());
        newStateInfo.setAppendCharacter(nextState.isAppendCharacter());
        return newStateInfo;
    }

    private void addSearchInfo(
            String matchedValue,
            int lineNumber,
            int startIndex,
            int endIndex
    ) {
        MatchInfo matchInfo = new MatchInfo();
        matchInfo.setMatch(matchedValue);
        matchInfo.setLineNumber(lineNumber);
        matchInfo.setStartIndex(startIndex);
        matchInfo.setEndIndex(endIndex);

        matches.add(matchInfo);
    }

    @Data
    private static class NewStateInfo {
        private State newState;
        private boolean appendCharacter;
    }
}
