package com.kil.service.match.state;

import java.util.List;

public interface State {

    boolean isStartState();

    boolean isFinishState();

    List<NextState> getNextStates();

}
