package com.kil.service.card.regex;

import com.kil.service.card.entity.Card;

import javax.annotation.Nonnull;
import java.util.List;

public interface RegexSearchCardService {

    @Nonnull
    List<Card> searchCards(@Nonnull String text);
}
