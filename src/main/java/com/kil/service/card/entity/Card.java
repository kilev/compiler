package com.kil.service.card.entity;

import lombok.Data;

@Data
public class Card {

    private String number;
    private CardType type;

    public String toTextView() {
        return number + "(" + type + ")";
    }
}
