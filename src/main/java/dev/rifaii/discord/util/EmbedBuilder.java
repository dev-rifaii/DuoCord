package dev.rifaii.discord.util;

import dev.rifaii.user.User;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class EmbedBuilder {

    private static final net.dv8tion.jda.api.EmbedBuilder embedBuilder = new net.dv8tion.jda.api.EmbedBuilder();

    public static MessageEmbed buildMatchEmbed(User match) {
        embedBuilder.clear();

        embedBuilder.setTitle(match.getDiscordName())
                .setImage(match.getDiscordAvatarUrl())
                .setAuthor("DuoCord")
                .addField("Age: ", String.valueOf(match.getAge()), true)
                .addField("About the person: ", match.getAboutTheUser(), true)
                .addField("Discord username: ", match.getDiscordUsername(), true)
                .addField("Interest: ", match.getActivity().getTitleName(), true)
                .setColor(Color.RED);
        return embedBuilder.build();
    }

}
