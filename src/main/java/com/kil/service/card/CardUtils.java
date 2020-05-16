package com.kil.service.card;

import com.kil.service.card.entity.CardType;
import lombok.experimental.UtilityClass;

import java.util.Arrays;

@UtilityClass
public class CardUtils {

    public CardType getCardType(String cardNumber) {
        return Arrays.stream(CardType.values())
                .filter(cardType -> cardNumber.startsWith(cardType.getStartDigit()))
                .findFirst()
                .orElse(CardType.UNKNOWN);
    }
}
