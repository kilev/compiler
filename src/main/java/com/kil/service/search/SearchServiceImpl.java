package com.kil.service.search;

import com.kil.service.search.card.CardSearchResult;
import com.kil.service.search.card.CardType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchServiceImpl implements SearchService {

    private static final String CARD_REGEX = "(?<=\\D|^)\\d{4}([-]|[ ]|)\\d{4}([-]|[ ]|)\\d{4}([-]|[ ]|)\\d{4}(?=[\\D]|$)";
    private static final Pattern CARD_PATTERN = Pattern.compile(CARD_REGEX);

    @Override
    public List<CardSearchResult> searchCardsByRegex(String text) {
        if (text == null) {
            return Collections.emptyList();
        }

        List<CardSearchResult> results = new ArrayList<>();
        List<String> lines = Arrays.asList(text.split(SearchUtils.getLineSeparator()));

        lines.forEach(line -> {
            Matcher matcher = CARD_PATTERN.matcher(line);
            while (matcher.find()) {
                String matchedValue = matcher.group();

                CardSearchResult result = new CardSearchResult();
                result.setValue(matchedValue);
                result.setType(getCardType(matchedValue));
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
