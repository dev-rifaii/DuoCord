package dev.rifaii.discord.commands;

import dev.rifaii.activity.Activity;
import dev.rifaii.user.MatchSearchDto;
import dev.rifaii.user.User;
import dev.rifaii.user.UserQueueService;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.time.Instant;

import static dev.rifaii.discord.action.Button.buildExitButton;
import static dev.rifaii.discord.action.Button.buildSkipButton;
import static dev.rifaii.discord.commands.Command.FIND;
import static dev.rifaii.discord.commands.Option.AGE;
import static dev.rifaii.discord.commands.Option.GAME;
import static dev.rifaii.discord.util.EmbedBuilder.buildMatchEmbed;

public class FindCommandHandler implements SlashCommandsHandler {

    private final UserQueueService userQueueService;

    public FindCommandHandler(UserQueueService userQueueService) {
        this.userQueueService = userQueueService;
    }

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        Long userDiscordId = event.getUser().getIdLong();
        var matchSearchDto = inputToMatchSearchDto(event);
        var match = userQueueService.getMatchForUserByActivity(userDiscordId, matchSearchDto.getActivity());

        if (match.isPresent()) {
            userQueueService.setSearchSession(userDiscordId, matchSearchDto);
            sendMatchFoundEmbed(event, match.get());
        } else {
            sendNoMatchesFoundMessage(event);
        }
    }

    private void sendMatchFoundEmbed(SlashCommandInteractionEvent event, User match) {
        var matchEmbed = buildMatchEmbed(match);
        event.replyEmbeds(matchEmbed)
                .setActionRow(buildExitButton(), buildSkipButton())
                .setEphemeral(true)
                .queue();
    }

    private void sendNoMatchesFoundMessage(SlashCommandInteractionEvent event) {
        event.reply("No matches found, try again later")
                .setEphemeral(true)
                .queue();
    }

    @Override
    public Command supportedCommand() {
        return FIND;
    }

    private MatchSearchDto inputToMatchSearchDto(SlashCommandInteractionEvent event) {
        Short age = (short) event.getOption(AGE.getCode()).getAsInt();
        var game = Activity.valueOf(event.getOption(GAME.getCode()).getAsString());

        return new MatchSearchDto()
                .setUserDiscordId(event.getUser().getIdLong())
                .setActivity(game)
                .setTimestamp(Instant.now())
                .setAge(age);
    }
}
