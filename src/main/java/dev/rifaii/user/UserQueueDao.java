package dev.rifaii.user;

import dev.rifaii.activity.Activity;

import java.util.List;
import java.util.Optional;

public interface UserQueueDao {

    void insertToQueue(User user);

    boolean isUserInQueue(Long userDiscordId);

    List<User> getEnqueuedUsersByAgeAndActivity(Short age, Activity activity);

    void upsertMatch(Long userDiscordId, Long matchDiscordId);

    List<Long> getMatchHistoryForUser(Long userDiscordId);

    void insertSearchSession(MatchSearchDto matchSearchDto);

    void deleteSession(Long userDiscordId);

    Optional<MatchSearchDto> getUserSearchDto(Long userDiscordId);


}
