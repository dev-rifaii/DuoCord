package dev.rifaii.user;

import dev.rifaii.IntegrationTestBase;
import dev.rifaii.database.RedisClient;
import org.junit.jupiter.api.Test;

import java.util.stream.LongStream;

import static dev.rifaii.activity.Activity.OVERWATCH;
import static dev.rifaii.user.UserQueueDaoRedis.ENQUEUED_USER_KEY_PREFIX;
import static org.junit.jupiter.api.Assertions.*;

public class UserQueueDaoRedisITest extends IntegrationTestBase {

    private final RedisClient redisClient = RedisClient.getInstance();

    @Test
    public void insertToQueue() {
        var user = buildUser();
        assertFalse(userQueueDao.isUserInQueue(user.getDiscordId()));
        userQueueDao.insertToQueue(user);
        assertTrue(redisClient.exists(ENQUEUED_USER_KEY_PREFIX + user.getDiscordId()));
    }

    @Test
    public void getMatchForUserByActivity_ReturnsMatch_IfUserWithSameActivityAndAgeGroupExists() {
        short age = 1;
        LongStream.range(1, 5)
                  .mapToObj(it -> buildUser().setDiscordId(it).setAge(age).setActivity(OVERWATCH))
                  .forEach(userQueueDao::insertToQueue);
        assertEquals(5, userQueueDao.getEnqueuedUsersByAgeAndActivity(age, OVERWATCH).size());
    }

}
