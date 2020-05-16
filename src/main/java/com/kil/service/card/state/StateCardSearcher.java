package com.kil.service.card.state;

import com.kil.service.card.entity.StateSearchCardResult;

import javax.annotation.Nonnull;

public interface StateCardSearcher {

    @Nonnull
    StateSearchCardResult searchCards();
}
