package com.kil.service.card.state;

import jdk.jfr.DataAmount;
import lombok.Data;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@DataAmount
public enum CardSearchState {

    Q21(),
    Q20(
            new NextState(StateRequirement.LINE_END, false, CardSearchState.Q21),
            new NextState(StateRequirement.SPACE, false, CardSearchState.Q21)),
    Q19(new NextState(StateRequirement.DIGIT, true, CardSearchState.Q20)),
    Q18(new NextState(StateRequirement.DIGIT, true, CardSearchState.Q19)),
    Q17(new NextState(StateRequirement.DIGIT, true, CardSearchState.Q18)),
    Q16(new NextState(StateRequirement.DIGIT, true, CardSearchState.Q17)),
    Q15(
            new NextState(StateRequirement.DIGIT, true, CardSearchState.Q17),
            new NextState(StateRequirement.DASH, true, CardSearchState.Q16),
            new NextState(StateRequirement.SPACE, true, CardSearchState.Q16)
    ),
    Q14(new NextState(StateRequirement.DIGIT, true, CardSearchState.Q15)),
    Q13(new NextState(StateRequirement.DIGIT, true, CardSearchState.Q14)),
    Q12(new NextState(StateRequirement.DIGIT, true, CardSearchState.Q13)),
    Q11(new NextState(StateRequirement.DIGIT, true, CardSearchState.Q12)),
    Q10(
            new NextState(StateRequirement.DIGIT, true, CardSearchState.Q12),
            new NextState(StateRequirement.DASH, true, CardSearchState.Q11),
            new NextState(StateRequirement.SPACE, true, CardSearchState.Q11)
    ),
    Q9(new NextState(StateRequirement.DIGIT, true, CardSearchState.Q10)),
    Q8(new NextState(StateRequirement.DIGIT, true, CardSearchState.Q9)),
    Q7(new NextState(StateRequirement.DIGIT, true, CardSearchState.Q8)),
    Q6(new NextState(StateRequirement.DIGIT, true, CardSearchState.Q7)),
    Q5(
            new NextState(StateRequirement.DIGIT, true, CardSearchState.Q7),
            new NextState(StateRequirement.DASH, true, CardSearchState.Q6),
            new NextState(StateRequirement.SPACE, true, CardSearchState.Q6)
    ),
    Q4(new NextState(StateRequirement.DIGIT, true, CardSearchState.Q5)),
    Q3(new NextState(StateRequirement.DIGIT, true, CardSearchState.Q4)),
    Q2(new NextState(StateRequirement.DIGIT, true, CardSearchState.Q3)),
    Q1(new NextState(StateRequirement.DIGIT, true, CardSearchState.Q2)),
    Q0(
            new NextState(StateRequirement.LINE_START, false, CardSearchState.Q1),
            new NextState(StateRequirement.SPACE, false, CardSearchState.Q1)
    );

    public static final CardSearchState START_STATE = Q0;
    public static final CardSearchState FINISH_STATE = Q21;

    @Getter
    private final List<NextState> nextStates;

    CardSearchState(NextState... nextStates) {
        assert (nextStates != null);

        this.nextStates = Arrays.asList(nextStates);
    }

    @Data
    public static class NextState {
        private final StateRequirement requirement;
        private final boolean appendCharacter;
        private final CardSearchState state;
    }
}
