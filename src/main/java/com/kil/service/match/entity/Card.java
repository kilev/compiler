package com.kil.service.match.entity;

import com.kil.service.TextViewTransformable;
import lombok.Data;

import java.util.Arrays;

@Data
public class Card implements TextViewTransformable {

    private String number;
    private CardType type;

    public static CardType getCardType(String cardNumber) {
        return Arrays.stream(CardType.values())
                .filter(cardType -> cardNumber.startsWith(cardType.getStartDigit()))
                .findFirst()
                .orElse(CardType.UNKNOWN);
    }

    @Override
    public String toTextView() {
        return number + "(" + getCardType(number) + ")";
    }

}
