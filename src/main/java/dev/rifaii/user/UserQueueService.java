package dev.rifaii.user;

import dev.rifaii.activity.Activity;

import java.util.Optional;

public class UserQueueService {

    private final UserQueueDao userQueueDao;

    public UserQueueService() {
        this.userQueueDao = new UserQueueDao();
    }

    public void enqueueUser(User user) {
        userQueueDao.enqueueUser(user);
    }

    public Optional<User> getMatchForUserByActivity(Long userId, Activity activity) {
        Optional<User> match = userQueueDao.getMatchForUserByActivity(userId, activity);
        match.ifPresent(foundMatch -> userQueueDao.addMatchToUserHistory(userId, foundMatch.getDiscordId()));
        return match;
    }

    public boolean isUserEnqueued(Long userDiscordId) {
        return userQueueDao.isUserEnqueued(userDiscordId);
    }

    public void setSearchSession(Long userDiscordId, MatchSearchDto matchSearchDto) {
        userQueueDao.setSearchSession(userDiscordId, matchSearchDto);
    }

    public void deleteSearchHistory(Long userDiscordId) {
        userQueueDao.deleteSearchHistory(userDiscordId);
    }

    public Optional<MatchSearchDto> getUserSearchDto(Long userDiscordId) {
        return userQueueDao.getUserSearchDto(userDiscordId);
    }

}
