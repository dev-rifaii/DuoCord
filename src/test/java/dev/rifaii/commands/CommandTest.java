package dev.rifaii.commands;

import dev.rifaii.discord.commands.Command;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    @ParameterizedTest
    @MethodSource("isDcCommandArgs")
    void isDcCommand_ReturnsCorrectFlag_GivenCommand(String command, boolean expected) {
        boolean actual = Command.isDuoCordCommand(command);
        assertEquals(expected, actual);
    }

    public static Stream<Arguments> isDcCommandArgs() {
        return Stream.of(
                Arguments.of("goofy", false),
                Arguments.of("PING", true),
                Arguments.of("piNg", true),
                Arguments.of("Yeet", false)
        );
    }
}