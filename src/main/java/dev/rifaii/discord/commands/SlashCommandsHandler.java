package dev.rifaii.discord.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface SlashCommandsHandler {


    void handle(SlashCommandInteractionEvent event);

    Command supportedCommand();

}
