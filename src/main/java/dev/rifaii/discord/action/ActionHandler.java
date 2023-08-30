package dev.rifaii.discord.action;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public interface ActionHandler {

    void handle(ButtonInteractionEvent event);

    Button supportedButton();

}
