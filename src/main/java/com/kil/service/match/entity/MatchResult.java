package com.kil.service.match.entity;

import com.kil.service.match.state.State;
import lombok.Data;

import java.util.List;

@Data
public class MatchResult {

    private List<MatchInfo> matchInfos;
    private List<State> stateTrace;
}
