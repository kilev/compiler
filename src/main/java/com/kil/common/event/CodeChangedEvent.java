package com.kil.common.event;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CodeChangedEvent {

    private final String newCode;
}
