package dev.rifaii.discord.action;

import dev.rifaii.discord.util.EmbedBuilder;
import dev.rifaii.user.MatchSearchDto;
import dev.rifaii.user.User;
import dev.rifaii.user.UserQueueService;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

import java.util.Collections;
import java.util.Optional;

import static dev.rifaii.util.Constant.ERROR_MESSAGE_REPLY;

@RequiredArgsConstructor
public class SkipButtonHandler implements ActionHandler {

    public static final String NO_NEW_MATCHES_MESSAGE = "No new matches were found, try again later";

    private final UserQueueService userQueueService;

    @Override
    public void handle(ButtonInteractionEvent event) {
        Long userDiscordId = event.getUser().getIdLong();
        Optional<MatchSearchDto> existingMatchSearch = userQueueService.getUserSearchDto(userDiscordId);

        existingMatchSearch.ifPresentOrElse(matchSearchDto -> {
                    var newMatch = userQueueService.getMatchForUserByActivity(userDiscordId, matchSearchDto.getActivity());

                    if (newMatch.isPresent()) {
                        displayNewMatch(event, newMatch.get());
                    } else {
                        sendSearchTerminationMessage(event, NO_NEW_MATCHES_MESSAGE);
                    }
                },
                () -> sendSearchTerminationMessage(event, ERROR_MESSAGE_REPLY)
        );
    }

    private void displayNewMatch(ButtonInteractionEvent event, User newMatch) {
        MessageEmbed newMatchEmbed = EmbedBuilder.buildMatchEmbed(newMatch);
        event.editMessageEmbeds(newMatchEmbed).queue();
    }

    private void sendSearchTerminationMessage(ButtonInteractionEvent event, String message) {
        event.editMessage(message)
                .setComponents(Collections.emptyList())
                .setSuppressEmbeds(true)
                .queue();
    }

    @Override
    public Button supportedButton() {
        return Button.SKIP;
    }
}
