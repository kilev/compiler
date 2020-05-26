package com.kil.service.match.entity;

import lombok.Data;

import java.util.Arrays;

@Data
public class Card {

    private String number;
    private CardType type;

    public static CardType getCardType(String cardNumber) {
        return Arrays.stream(CardType.values())
                .filter(cardType -> cardNumber.startsWith(cardType.getStartDigit()))
                .findFirst()
                .orElse(CardType.UNKNOWN);
    }

    public String toTextView() {
        return number + "(" + getCardType(number) + ")";
    }

}
