package com.kil.service.card.regex;

import com.kil.common.Utils;
import com.kil.service.card.CardUtils;
import com.kil.service.card.entity.Card;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexSearchCardServiceImpl implements RegexSearchCardService {

    private static final String CARD_REGEX = "(?<=\\D|^)\\d{4}([-]|[ ]|)\\d{4}([-]|[ ]|)\\d{4}([-]|[ ]|)\\d{4}(?=[\\D]|$)";
    private static final Pattern CARD_PATTERN = Pattern.compile(CARD_REGEX);

    @Nonnull
    @Override
    public List<Card> searchCards(@Nonnull String text) {
        List<Card> results = new ArrayList<>();
        List<String> lines = Arrays.asList(text.split(Utils.getLineSeparator().toString()));

        lines.forEach(line -> {
            Matcher matcher = CARD_PATTERN.matcher(line);
            while (matcher.find()) {
                String matchedValue = matcher.group();

                Card result = new Card();
                result.setNumber(matchedValue);
                result.setType(CardUtils.getCardType(matchedValue));
                results.add(result);
            }
        });
        return results;
    }


}
