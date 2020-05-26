package com.kil.service.match.state;

import com.kil.service.match.StateRequirement;
import lombok.Data;

@Data
public class NextState {
    private final StateRequirement requirement;
    private final boolean appendCharacter;
    private final State state;
}
