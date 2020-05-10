package com.kil.service.search.card;

import lombok.Data;

@Data
public class CardSearchResult {

    private String value;
    private CardType type;

    public String toTextView() {
        return value + "(" + type + ")";
    }
}
