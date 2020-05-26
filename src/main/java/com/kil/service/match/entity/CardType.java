package com.kil.service.match.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CardType {

    VISA("4"),
    MASTER_CARD("5"),
    WORLD("2"),
    UNKNOWN("");

    @Getter
    private final String startDigit;
}
