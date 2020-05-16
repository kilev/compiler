package com.kil.view;

import com.kil.common.Utils;
import com.kil.service.card.entity.Card;
import com.kil.service.card.entity.StateSearchCardResult;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class FormatUtils {

    public String toTextView(StateSearchCardResult result) {
        return new StringBuilder()
                .append(toTextView(result.getCards()))
                .append(Utils.getLineSeparator())
                .append("StateTrace:")
                .append(Utils.getLineSeparator())
                .append(result.getStateTrace().stream()
                        .map(Enum::name)
                        .collect(Collectors.joining(Utils.getLineSeparator().toString()))
                ).toString();
    }

    public String toTextView(List<Card> cards) {
        return cards.stream()
                .map(Card::toTextView)
                .collect(Collectors.joining(Utils.getLineSeparator().toString()));
    }

}
