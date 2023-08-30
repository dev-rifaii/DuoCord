package dev.rifaii.util;

import static java.lang.System.getenv;

public class PropertyLoader {

    public static String DISCORD_BOT_KEY;
    public static String REDIS_HOST;
    public static String REDIS_PORT;
    public static String REDIS_PASS;

    public static void loadVariables() throws Exception {
        Assert.requireNotNull(
                "Failed to load system properties",
                DISCORD_BOT_KEY = getenv("DISCORD_BOT_KEY"),
                REDIS_HOST = getenv("REDIS_HOST"),
                REDIS_PORT = getenv("REDIS_PORT"),
                REDIS_PASS = getenv("REDIS_PASS")
        );
    }
}
