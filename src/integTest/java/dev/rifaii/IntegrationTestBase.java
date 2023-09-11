package dev.rifaii;

import com.redis.testcontainers.RedisStackContainer;
import dev.rifaii.user.User;
import dev.rifaii.user.UserQueueDao;
import dev.rifaii.util.PropertyLoader;
import org.testcontainers.containers.wait.strategy.Wait;

import java.util.stream.IntStream;

import static dev.rifaii.activity.Activity.OVERWATCH;
import static org.testcontainers.utility.DockerImageName.parse;

public class IntegrationTestBase {

    private static final String REDIS_PASSWORD = "redis";
    protected UserQueueDao userQueueDao = new UserQueueDao();
    private static final RedisStackContainer redisContainer = new RedisStackContainer(parse("redis/redis-stack:latest"))
            .withCommand("redis-stack-server", "--requirepass", REDIS_PASSWORD)
            .withExposedPorts(6379)
            .waitingFor(Wait.forLogMessage(".*Ready to accept connections.*\\n", 1));

    static {
        redisContainer.start();
        PropertyLoader.setVariablesForTesting(
                "KeYMoCk212",
                redisContainer.getFirstMappedPort(),
                REDIS_PASSWORD
        );
    }

    protected User buildUser() {
        return new User()
                .setDiscordId(1234L)
                .setDiscordName("JD")
                .setDiscordUsername("JohnDoe231")
                .setAge((short) 1)
                .setActivity(OVERWATCH)
                .setDiscordAvatarUrl("jdUrl");
    }

    protected void insertMatchesForUser(User user, int amountOfMatches) {
        IntStream.range(0, amountOfMatches)
                 .mapToObj(it -> buildUser().setDiscordId(it + 500L)
                                            .setActivity(user.getActivity())
                                            .setAge(user.getAge()))
                 .forEach(userQueueDao::insertToQueue);
    }

}
