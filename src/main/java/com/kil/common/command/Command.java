package com.kil.common.command;

@FunctionalInterface
public interface Command {

    Command EMPTY = () -> {
    };

    void execute();
}
