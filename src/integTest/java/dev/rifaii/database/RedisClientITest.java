package dev.rifaii.database;

import dev.rifaii.IntegrationTestBase;
import dev.rifaii.activity.Activity;
import dev.rifaii.exception.JsonParsingException;
import dev.rifaii.user.MatchSearchDto;
import dev.rifaii.user.User;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RedisClientITest extends IntegrationTestBase {

    private static final String ITEST_KEY = "ITEST";

    private final RedisClient redisClient = RedisClient.getInstance();


    @Test
    void getJson_IsSuccessful_FetchingExistingKeyAndCorrectType() {
        var user = new User()
                .setDiscordId(123L)
                .setActivity(Activity.OVERWATCH);

        String key = ITEST_KEY + user.getDiscordId();
        redisClient.insertJson(key, user);

        var savedUser = redisClient.getJson(key, User.class);

        assertEquals(user.getDiscordId(), savedUser.getDiscordId());
        assertEquals(user.getActivity(), savedUser.getActivity());
    }

    @Test
    void getJson_ThrowsJsonParsingException_GivenInvalidType() {
        var user = new User()
                .setDiscordId(123L)
                .setActivity(Activity.OVERWATCH);

        String key = ITEST_KEY + user.getDiscordId();
        redisClient.insertJson(key, user);

        assertThrows(
                JsonParsingException.class,
                () -> redisClient.getJson(key, MatchSearchDto.class)
        );
    }

    @Test
    void getJson_ReturnsNull_GivenNonExistentKey() {
        assertNull(redisClient.getJson(ITEST_KEY + "random554321", MatchSearchDto.class));
    }

    @Test
    void getJsonList_IsSuccessful_FetchingExistingKeyAndCorrectType() {
        var ids = Set.of(1L, 2L);
        List<User> users = List.of(
                new User().setDiscordId(1L),
                new User().setDiscordId(2L)
        );

        String key = "USERS";
        redisClient.insertJson(key, users);

        List<User> savedUsers = redisClient.getJsonList(key, User.class);
        assertEquals(2, savedUsers.size());
        assertTrue(savedUsers.stream().map(User::getDiscordId).allMatch(ids::contains));
    }

}
