package dev.rifaii.discord.commands;

import dev.rifaii.user.UserQueueService;

import java.util.List;

public class CommandHandlerSupplier {

    private final List<SlashCommandsHandler> definedHandlers;

    public CommandHandlerSupplier(UserQueueService userQueueService) {
        definedHandlers = List.of(new RegisterCommandHandler(userQueueService), new FindCommandHandler(userQueueService));
    }

    public SlashCommandsHandler getHandlerForCommand(Command command) {
        return definedHandlers.stream()
                .filter(handler -> handler.supportedCommand().equals(command))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }
}
