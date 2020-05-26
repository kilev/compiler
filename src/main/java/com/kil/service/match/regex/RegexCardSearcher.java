package com.kil.service.match.regex;

import com.kil.common.Utils;
import com.kil.service.match.entity.MatchInfo;
import com.kil.service.match.entity.MatchResult;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexCardSearcher {

    private static final String CARD_REGEX = "(?<=\\D|^)\\d{4}([-]|[ ]|)\\d{4}([-]|[ ]|)\\d{4}([-]|[ ]|)\\d{4}(?=[\\D]|$)";
    private static final Pattern CARD_PATTERN = Pattern.compile(CARD_REGEX);

    private final String text;
    private final List<MatchInfo> matchInfos;

    public RegexCardSearcher(String text) {
        this.text = text;

        matchInfos = new ArrayList<>();
    }

    @Nonnull
    public MatchResult searchCards() {
        List<String> lines = Arrays.asList(text.split(Utils.getLineSeparator().toString()));

        lines.forEach(line -> {
            Matcher matcher = CARD_PATTERN.matcher(line);
            while (matcher.find()) {
                addSearchInfo(matcher, lines.indexOf(line));
            }
        });

        MatchResult result = new MatchResult();
        result.setMatchInfos(matchInfos);

        return result;
    }

    private void addSearchInfo(Matcher matcher, int lineNumber) {
        MatchInfo matchInfo = new MatchInfo();
        matchInfo.setMatch(matcher.group());
        matchInfo.setLineNumber(lineNumber);
        matchInfo.setStartIndex(matcher.start());
        matchInfo.setEndIndex(matcher.end());

        matchInfos.add(matchInfo);

    }


}
