package com.kil.service.card.entity;

import com.kil.service.card.state.CardSearchState;
import lombok.Data;

import java.util.List;

@Data
public class StateSearchCardResult {

    private List<Card> cards;
    private List<CardSearchState> stateTrace;
}
