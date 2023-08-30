package dev.rifaii;

import dev.rifaii.database.RedisClient;
import dev.rifaii.user.User;

import static dev.rifaii.util.PropertyLoader.loadVariables;

public class Main {
    public static void main(String[] args) throws Exception {
        loadVariables();
        var redisClient = RedisClient.getInstance();
        redisClient.ping();
//        Bot.initialize();

        var user = new User()
                .setDiscordName("testName");

        redisClient.insertJson("test", user);
    }
}
