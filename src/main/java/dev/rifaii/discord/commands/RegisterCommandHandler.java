package dev.rifaii.discord.commands;

import dev.rifaii.activity.Activity;
import dev.rifaii.user.User;
import dev.rifaii.user.UserQueueService;
import jakarta.validation.Validation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.time.Instant;

import static dev.rifaii.discord.commands.Command.REGISTER;
import static dev.rifaii.discord.commands.Option.*;
import static dev.rifaii.util.Constant.ERROR_MESSAGE_REPLY;
import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
public class RegisterCommandHandler implements SlashCommandsHandler {

    private final UserQueueService userQueueService;

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        if (userQueueService.isUserEnqueued(event.getUser().getIdLong())) {
            sendAlreadyRegisteredMessage(event);
            return;
        }

        try {
            validateAndRegisterUser(event);
        } catch (NullPointerException | IllegalArgumentException | ValidationException e) {
            logErrorAndSendMessage(e, event);
        }
    }

    private void sendAlreadyRegisteredMessage(SlashCommandInteractionEvent event) {
        event.reply("You are already in the queue").queue();
    }

    private void validateAndRegisterUser(SlashCommandInteractionEvent event) {
        var user = commandInputToUser(event);
        validateUser(user);
        userQueueService.enqueueUser(user);
        sendConfirmationMessage(event);
        log.info(format("User with ID %d has been registered", user.getDiscordId()));
    }

    private void sendConfirmationMessage(SlashCommandInteractionEvent event) {
        event.reply("You have been added to the queue")
                .setEphemeral(true)
                .queue();
    }

    private User commandInputToUser(SlashCommandInteractionEvent event) {
        final short age = (short) event.getOption(AGE.getCode()).getAsInt();
        final String text = event.getOption(TEXT.getCode()).getAsString();
        final Activity activity = Activity.valueOf(event.getOption(GAME.getCode()).getAsString());
        final String avatarUrl = event.getUser().getAvatarUrl();
        final String userHandle = event.getUser().getAsMention();
        final String username = event.getUser().getName();

        return new User()
                .setActivity(activity)
                .setAge(age)
                .setAboutTheUser(text)
                .setEntryTime(Instant.now())
                .setDiscordAvatarUrl(avatarUrl)
                .setDiscordHandle(userHandle)
                .setDiscordUsername(username)
                .setDiscordName(event.getUser().getGlobalName())
                .setDiscordId(event.getUser().getIdLong());
    }

    private void validateUser(User user) {
        validator.validate(user);
    }

    private void logErrorAndSendMessage(Exception e, SlashCommandInteractionEvent event) {
        log.error(e.getMessage());
        event.reply(ERROR_MESSAGE_REPLY).queue();
    }

    @Override
    public Command supportedCommand() {
        return REGISTER;
    }
}
