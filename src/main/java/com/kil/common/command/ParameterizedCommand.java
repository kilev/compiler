package com.kil.common.command;

@FunctionalInterface
public interface ParameterizedCommand<T> {

    void execute(T param);
}
