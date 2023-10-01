package dev.rifaii.util;

import static java.lang.Integer.parseInt;
import static java.lang.System.getenv;

public class PropertyLoader {

    public static String DISCORD_BOT_KEY;
    public static String REDIS_HOST;
    public static int REDIS_PORT;
    public static String REDIS_PASS;

    public static void loadVariables() throws Exception {
        Assert.requireNotNull(
                "Failed to load system properties",
                DISCORD_BOT_KEY = getenv("DISCORD_BOT_KEY"),
                REDIS_HOST = getenv("REDIS_HOST"),
                REDIS_PORT = parseInt(getenv("REDIS_PORT")),
                REDIS_PASS = getenv("REDIS_PASS")
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
