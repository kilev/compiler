package com.kil.service.analyzer.card;

import com.kil.service.analyzer.AnalyzeUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CardSearchService {

    private static final String CARD_REGEX = "(?<=\\D|^)\\d{4}([-]|[ ]|)\\d{4}([-]|[ ]|)\\d{4}([-]|[ ]|)\\d{4}(?=[\\D]|$)";
    private static final Pattern PATTERN = Pattern.compile(CARD_REGEX);

    public List<CardAnalyzeResult> searchCards(String text) {
        List<CardAnalyzeResult> results = new ArrayList<>();
        List<String> lines = Arrays.asList(text.split(AnalyzeUtils.getLineSeparator()));

        lines.forEach(line -> {
            Matcher matcher = PATTERN.matcher(line);
            while (matcher.find()) {
                CardAnalyzeResult result = new CardAnalyzeResult();
                result.setValue(matcher.group());
                result.setType(getCardType(result.getValue()));
                result.setLineNumber(lines.indexOf(line));
                results.add(result);
            }
        });
        return results;
    }

    private CardType getCardType(String cardNumber) {
        return Arrays.stream(CardType.values())
                .filter(cardType -> cardNumber.startsWith(cardType.getStartDigit()))
                .findFirst()
                .orElse(CardType.UNKNOWN);
    }
}
