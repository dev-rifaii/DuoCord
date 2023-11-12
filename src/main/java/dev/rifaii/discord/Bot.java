package dev.rifaii.discord;

import dev.rifaii.discord.action.ActionListener;
import dev.rifaii.discord.commands.SlashCommandsListener;
import dev.rifaii.user.UserQueueDaoRedis;
import dev.rifaii.user.UserQueueService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static dev.rifaii.activity.Activity.PLAYABLE_ACTIVITIES;
import static dev.rifaii.discord.commands.Command.FIND;
import static dev.rifaii.discord.commands.Command.REGISTER;
import static dev.rifaii.discord.commands.Option.*;
import static dev.rifaii.util.PropertyLoader.DISCORD_BOT_KEY;

public class Bot {

    private static final String TOKEN = DISCORD_BOT_KEY;

    private static final UserQueueService userQueueService = new UserQueueService(new UserQueueDaoRedis());

    public static void initialize() {
        JDA jda = JDABuilder.createDefault(TOKEN)
                            .setStatus(OnlineStatus.ONLINE)
                            .addEventListeners(new SlashCommandsListener(userQueueService))
                            .addEventListeners(new ActionListener(userQueueService))
                            .setEventPassthrough(true)
                            .build();

        jda.updateCommands().addCommands(
                List.of(
                        registerCommand(),
                        findCommand()
                )
        ).queue();
    }

    @NotNull
    private static SlashCommandData registerCommand() {
        return Commands.slash(REGISTER.getDisplayableName(), "Enter queue")
                       .addOption(OptionType.INTEGER, AGE.getText(), "your age", true)
                       .addOption(OptionType.STRING, TEXT.getText(), "something to say", true)
                       .addOptions(playableActivitiesOptions());
    }

    private static SlashCommandData findCommand() {
        return Commands.slash(FIND.getDisplayableName(), "find")
                       .addOption(OptionType.INTEGER, AGE.getText(), "your age", true)
                       .addOptions(playableActivitiesOptions());
    }

    private static OptionData playableActivitiesOptions() {
        return new OptionData(OptionType.STRING, GAME.getText(), "Desired game", true)
                .addChoices(
                        PLAYABLE_ACTIVITIES.stream()
                                           .map(game -> new Command.Choice(game.getTitleName(), game.name()))
                                           .toList()
                );
    }
}
