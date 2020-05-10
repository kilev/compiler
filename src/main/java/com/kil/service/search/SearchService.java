package com.kil.service.search;

import com.kil.service.search.card.CardSearchResult;

import java.util.List;

public interface SearchService {

    List<CardSearchResult> searchCardsByRegex(String text);
}
