package com.kil.service.card.state;

import com.google.common.collect.Lists;
import com.kil.common.Utils;
import com.kil.service.card.CardUtils;
import com.kil.service.card.entity.Card;
import com.kil.service.card.entity.StateSearchCardResult;
import lombok.Data;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StateCardSearcherImpl implements StateCardSearcher {

    private final List<Character> textCharacters;

    private CardSearchState currentState;
    private List<CardSearchState> stateTrace = new ArrayList<>();
    private List<String> searchedCardsNumbers = new ArrayList<>();
    private StringBuilder cardNumberBuilder = new StringBuilder();

    public StateCardSearcherImpl(String text) {
        String preparedText = prepareText(text);
        textCharacters = Lists.charactersOf(preparedText);
    }

    @Nonnull
    @Override
    public StateSearchCardResult searchCards() {
        setTracedState(CardSearchState.START_STATE);
        textCharacters.forEach(this::processChar);
        return getResult(searchedCardsNumbers, stateTrace);
    }

    private void processChar(Character currentCharacter) {
        NewStateInfo newStateInfo = getNewStateInfo(currentState, currentCharacter);
        if (newStateInfo.isAppendCharacter()) {
            cardNumberBuilder.append(currentCharacter);
        }

        CardSearchState oldState = currentState;
        CardSearchState newState = newStateInfo.getNewState();

        if (newState == CardSearchState.FINISH_STATE) {
            searchedCardsNumbers.add(cardNumberBuilder.toString());
            cardNumberBuilder = new StringBuilder();

            setTracedState(newState);
            setTracedState(CardSearchState.START_STATE);
            processChar(currentCharacter);

        } else if (oldState != newState && newState == CardSearchState.START_STATE) {
            cardNumberBuilder = new StringBuilder();
            setTracedState(newState);
            processChar(currentCharacter);

        } else if (newState == CardSearchState.START_STATE) {
            cardNumberBuilder = new StringBuilder();
            setTracedState(newState);

        } else {
            setTracedState(newState);
        }
    }

    @Nonnull
    private NewStateInfo getNewStateInfo(CardSearchState currentState, Character character) {
        return currentState.getNextStates().stream()
                .filter(nextState -> nextState.getRequirement().check(character))
                .map(this::newStateInfo)
                .findAny()
                .orElse(createStartState());
    }

    @Nonnull
    private StateSearchCardResult getResult(
            @Nonnull List<String> cardNumbers,
            @Nonnull List<CardSearchState> stateTrace
    ) {
        StateSearchCardResult result = new StateSearchCardResult();

        List<Card> cards = cardNumbers.stream()
                .map(cardNumber -> {
                    Card card = new Card();
                    card.setNumber(cardNumber);
                    card.setType(CardUtils.getCardType(cardNumber));
                    return card;
                })
                .collect(Collectors.toList());

        result.setCards(cards);
        result.setStateTrace(stateTrace);
        return result;
    }

    @Nonnull
    private String prepareText(@Nonnull String sourceText) {
        Character lineSeparator = Utils.getLineSeparator();
        StringBuilder builder = new StringBuilder(sourceText);
        builder.insert(0, lineSeparator);
        if (!lineSeparator.equals(sourceText.charAt(sourceText.length() - 1))) {
            builder.append(lineSeparator);
        }
        return builder.toString();
    }

    private void setTracedState(CardSearchState newState) {
        currentState = newState;
        stateTrace.add(newState);
    }

    private NewStateInfo createStartState() {
        NewStateInfo newStateInfo = new NewStateInfo();
        newStateInfo.setNewState(CardSearchState.START_STATE);
        newStateInfo.setAppendCharacter(false);
        return newStateInfo;
    }

    private NewStateInfo newStateInfo(CardSearchState.NextState nextState) {
        NewStateInfo newStateInfo = new NewStateInfo();
        newStateInfo.setNewState(nextState.getState());
        newStateInfo.setAppendCharacter(nextState.isAppendCharacter());
        return newStateInfo;
    }

    @Data
    private static class NewStateInfo {
        private CardSearchState newState;
        private boolean appendCharacter;
    }
}