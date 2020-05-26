package com.kil.service.match.entity;

import lombok.Data;

@Data
public class MatchInfo {

    private String match;
    private int lineNumber;
    private int startIndex;
    private int endIndex;
}
