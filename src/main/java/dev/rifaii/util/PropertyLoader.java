package dev.rifaii.util;

import io.github.cdimascio.dotenv.Dotenv;

public class PropertyLoader {

    public static String DISCORD_BOT_KEY;
    public static String REDIS_HOST;
    public static int REDIS_PORT;
    public static String REDIS_PASS;

    public static void loadVariables() throws Exception {
        Dotenv dotenv = Dotenv.configure().load();
        Assert.requireNotNull(
                "Failed to load system properties",
                DISCORD_BOT_KEY = dotenv.get("DISCORD_BOT_KEY"),
                REDIS_HOST = dotenv.get("REDIS_HOST"),
                REDIS_PORT = Integer.parseInt(dotenv.get("REDIS_PORT")),
                REDIS_PASS = dotenv.get("REDIS_PASS")
        );
    }

    public static void setVariablesForTesting(
            String discordBotKey,
            int redisPort,
            String redisPass
    ) {
        DISCORD_BOT_KEY = discordBotKey;
        REDIS_HOST = "localhost";
        REDIS_PORT = redisPort;
        REDIS_PASS = redisPass;
    }
}
