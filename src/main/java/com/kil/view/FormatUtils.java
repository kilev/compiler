package com.kil.view;

import com.kil.common.Utils;
import com.kil.service.match.entity.MatchResult;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FormatUtils {

    public String toTextView(MatchResult result) {
        StringBuilder resultBuilder = new StringBuilder();
        result.getMatchInfos().forEach(matchInfo -> resultBuilder.append("line: ")
                .append(matchInfo.getLineNumber())
                .append("(indexes: ")
                .append(matchInfo.getStartIndex())
                .append(",")
                .append(matchInfo.getEndIndex())
                .append(") ")
                .append(matchInfo.getMatch())
                .append(Utils.getLineSeparator()));

        if (result.getStateTrace() != null) {
            resultBuilder.append("StateTrace")
                    .append(Utils.getLineSeparator());

            result.getStateTrace().forEach(state ->
                    resultBuilder.append(state)
                            .append(Utils.getLineSeparator()));
        }

        return resultBuilder.toString();
    }

}
