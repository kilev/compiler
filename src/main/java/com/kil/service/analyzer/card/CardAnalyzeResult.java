package com.kil.service.analyzer.card;

import lombok.Data;

@Data
public class CardAnalyzeResult {

    private String value;
    private CardType type;
    private int lineNumber;

    @Override
    public String toString() {
        return "";
    }

    public String toTextView() {
        return "line: " + lineNumber + " value: " + value + " (" + type + ") ";
    }
}
