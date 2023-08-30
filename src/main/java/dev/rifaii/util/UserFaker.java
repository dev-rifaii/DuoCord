package dev.rifaii.util;

import dev.rifaii.activity.Activity;
import dev.rifaii.user.User;

import java.time.Instant;
import java.util.List;
import java.util.stream.IntStream;

public class UserFaker {

    public static List<User> generateFakeUsers(int count) {
        return IntStream.range(0, count)
                .mapToObj(UserFaker::buildUser)
                .toList();
    }

    private static User buildUser(int number) {
        return new User()
                .setAge((short) ((Math.random() * 10) + 1))
                .setAboutTheUser("Fake number " + number)
                .setActivity(Activity.OVERWATCH)
                .setDiscordHandle("FakeHandle" + number)
                .setDiscordId((long) number)
                .setDiscordUsername("usernumber" + number)
                .setEntryTime(Instant.now())
                .setDiscordAvatarUrl("https://i.pravatar.cc/300?u=" + Math.random() * 10)
                .setDiscordName("Name" + number);
    }
}
