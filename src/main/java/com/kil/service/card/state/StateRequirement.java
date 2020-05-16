package com.kil.service.card.state;

import com.kil.common.Utils;
import lombok.Getter;

@Getter
public enum StateRequirement implements StateRequirementChecker {
    LINE_START {
        @Override
        public boolean check(Character character) {
            return character.equals(Utils.getLineSeparator());
        }
    },
    DIGIT {
        @Override
        public boolean check(Character character) {
            return Character.isDigit(character);
        }
    },
    DASH {
        @Override
        public boolean check(Character character) {
            return character.equals('-');
        }
    },
    SPACE {
        @Override
        public boolean check(Character character) {
            return character.equals(' ');
        }
    },
    LINE_END {
        @Override
        public boolean check(Character character) {
            return character.equals(Utils.getLineSeparator());
        }
    }
}
