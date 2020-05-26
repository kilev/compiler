package com.kil.service.match;

public interface StateRequirementChecker {
    /**
     * @return true if the condition is true
     * otherwise false
     */
    boolean check(Character character);
}
