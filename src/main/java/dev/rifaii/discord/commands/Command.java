package dev.rifaii.discord.commands;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Command {

    PING("ping"),
    REGISTER("register"),
    FIND("find"),
    EMBED("util");

    private final String displayableName;

    Command(String displayableName) {
        this.displayableName = displayableName;
    }

    public static boolean isDuoCordCommand(String command) {
        return Arrays.stream(Command.values())
                .map(Command::name)
                .anyMatch(dcCommand -> dcCommand.equalsIgnoreCase(command));
    }

}
