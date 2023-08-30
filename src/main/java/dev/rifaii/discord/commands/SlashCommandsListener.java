package dev.rifaii.discord.commands;

import dev.rifaii.user.UserQueueService;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@Slf4j
public class SlashCommandsListener extends ListenerAdapter {

    private final CommandHandlerSupplier commandHandlerSupplier;

    public SlashCommandsListener(UserQueueService userQueueService) {
        this.commandHandlerSupplier = new CommandHandlerSupplier(userQueueService);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        var inputCommand = event.getName().toUpperCase();
        if (!Command.isDuoCordCommand(inputCommand)) {
            log.warn(String.format("Unknown command passed with value: %s", inputCommand));
            return;
        }

        commandHandlerSupplier.getHandlerForCommand(Command.valueOf(inputCommand))
                .handle(event);
    }
}
