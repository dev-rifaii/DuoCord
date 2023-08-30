package dev.rifaii.discord.action;

import dev.rifaii.user.UserQueueService;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.util.Collections;

import static dev.rifaii.discord.action.Button.EXIT;

@RequiredArgsConstructor
public class ExitButtonHandler implements ActionHandler {

    private static final String SEARCH_TERMINATION_MESSAGE = "Search terminated.";

    private final UserQueueService userQueueService;

    @Override
    public void handle(ButtonInteractionEvent event) {
        userQueueService.deleteSearchHistory(event.getUser().getIdLong());
        sendSearchTerminationMessage(event);
    }

    private void sendSearchTerminationMessage(ButtonInteractionEvent event) {
        event.editMessage(SEARCH_TERMINATION_MESSAGE)
                .setComponents(Collections.emptyList())
                .setSuppressEmbeds(true)
                .queue();
    }

    @Override
    public Button supportedButton() {
        return EXIT;
    }
}
