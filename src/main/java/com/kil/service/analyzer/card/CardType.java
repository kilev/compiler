package com.kil.service.analyzer.card;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CardType {

    VISA("4"),
    MASTER_CARD("5"),
    WORLD("2"),
    UNKNOWN(null);

    @Getter
    private final String startDigit;
}
