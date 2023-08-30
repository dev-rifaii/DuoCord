package dev.rifaii.user;

import dev.rifaii.activity.Activity;
import dev.rifaii.util.UserFaker;

import java.util.*;

public class UserQueueDao {

    private final List<User> enqueuedUsers = new ArrayList<>();
    private final Map<Long, Set<Long>> userMatchHistory = new HashMap<>();
    private final Map<Long, MatchSearchDto> searchSessions = new HashMap<>();

    public UserQueueDao() {
        enqueuedUsers.addAll(UserFaker.generateFakeUsers(15));
    }

    public void enqueueUser(User user) {
        enqueuedUsers.add(user);
    }

    public Optional<User> getMatchForUserByActivity(Long userId, Activity activity) {
        return enqueuedUsers.stream()
                .filter(match -> match.getActivity().name().equals(activity.name()))
                .filter(match -> userMatchHistory.get(userId) == null || !userMatchHistory.get(userId).contains(match.getDiscordId()))
                .findFirst();
    }

    public boolean isUserEnqueued(Long userDiscordId) {
        return enqueuedUsers.stream()
                .anyMatch(user -> user.getDiscordId().equals(userDiscordId));
    }

    public void addMatchToUserHistory(Long userDiscordId, Long matchDiscordId) {
        Optional.ofNullable(userMatchHistory.get(userDiscordId))
                .ifPresentOrElse(
                        existingHistory -> existingHistory.add(matchDiscordId),
                        () -> {
                            Set<Long> newHistory = new HashSet<>();
                            newHistory.add(matchDiscordId);
                            userMatchHistory.put(userDiscordId, newHistory);
                        });
    }

    public void setSearchSession(Long userDiscordId, MatchSearchDto matchSearchDto) {
        searchSessions.put(userDiscordId, matchSearchDto);
    }

    public void deleteSearchHistory(Long userDiscordId) {
        searchSessions.remove(userDiscordId);
    }

    public Optional<MatchSearchDto> getUserSearchDto(Long userDiscordId) {
        return Optional.of(searchSessions.get(userDiscordId));
    }
}
