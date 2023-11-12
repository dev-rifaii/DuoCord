package dev.rifaii.discord.commands;

import lombok.Getter;

@Getter
public enum Option {

    AGE("age"),
    NOTE("note"),
    GAME("game"),
    ACTIVITY("activity");

    private final String text;

    Option(String text) {
        this.text = text;
    }

}
