package dev.rifaii;

import dev.rifaii.database.RedisClient;
import dev.rifaii.discord.Bot;

import static dev.rifaii.util.PropertyLoader.loadVariables;

public class Main {
    public static void main(String[] args) throws Exception {
        loadVariables();
        RedisClient.initialize();
        Bot.initialize();
    }

}
