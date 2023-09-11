package dev.rifaii.user;

import dev.rifaii.IntegrationTestBase;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static dev.rifaii.activity.Activity.OVERWATCH;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserQueueServiceITest extends IntegrationTestBase {

    private final UserQueueService userQueueService = new UserQueueService(new UserQueueDao());

    @Test
    public void findMatchForUser_DoesNotReturnAlreadyMatchedUser() {
        var user = buildUser().setDiscordId(303L).setAge((short) 1).setActivity(OVERWATCH);
        insertMatchesForUser(user, 2);
        Optional<User> firstMatch = userQueueService.findMatchForUser(buildMatchSearchForUser(user));
        Optional<User> secondMatch = userQueueService.findMatchForUser(buildMatchSearchForUser(user));

        assertTrue(firstMatch.isPresent());
        assertTrue(secondMatch.isPresent());
        assertNotEquals(firstMatch.get().getDiscordId(), secondMatch.get().getDiscordId());
    }

    private MatchSearchDto buildMatchSearchForUser(User user) {
        return new MatchSearchDto()
                .setUserDiscordId(user.getDiscordId())
                .setAge(user.getAge())
                .setActivity(user.getActivity());
    }
}
