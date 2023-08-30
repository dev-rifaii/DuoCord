package dev.rifaii.discord.commands;

import lombok.Getter;

@Getter
public enum Option {

    AGE("age"),
    TEXT("text"),
    GAME("game"),
    ACTIVITY("activity");

    private final String code;

    Option(String code) {
        this.code = code;
    }

}
