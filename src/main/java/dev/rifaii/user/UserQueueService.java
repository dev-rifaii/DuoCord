package dev.rifaii.user;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;

public class UserQueueService {

    private final UserQueueDao userQueueDao;

    private final Validator validator = buildDefaultValidatorFactory().getValidator();

    public UserQueueService(UserQueueDao userQueueDao) {
        this.userQueueDao = userQueueDao;
    }

    public void enqueueUser(User user) {
        validateUser(user);
        userQueueDao.insertToQueue(user);
    }

    public Optional<User> findMatchForUser(MatchSearchDto matchSearchDto) {
        Optional<User> bestMatch = findBestMatchFromEnqueuedUsers(matchSearchDto);
        bestMatch.ifPresent(foundMatch -> userQueueDao.upsertMatch(matchSearchDto.getUserDiscordId(), foundMatch.getDiscordId()));
        return bestMatch;
    }

    public boolean isUserEnqueued(Long userDiscordId) {
        return userQueueDao.isUserInQueue(userDiscordId);
    }

    public void setSearchSession(MatchSearchDto matchSearchDto) {
        userQueueDao.insertSearchSession(matchSearchDto);
    }

    public void deleteSearchHistory(Long userDiscordId) {
        userQueueDao.deleteSession(userDiscordId);
    }

    public Optional<MatchSearchDto> getUserSearchDto(Long userDiscordId) {
        return userQueueDao.getUserSearchDto(userDiscordId);
    }

    private void validateUser(@Valid User user) {
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

    private Optional<User> findBestMatchFromEnqueuedUsers(MatchSearchDto matchSearchDto) {
        List<User> enqueuedUsers = userQueueDao.getEnqueuedUsersByAgeAndActivity(matchSearchDto.getAge(), matchSearchDto.getActivity());
        List<Long> previousMatches = userQueueDao.getMatchHistoryForUser(matchSearchDto.getUserDiscordId());
        return enqueuedUsers.stream()
                            .filter(match -> !previousMatches.contains(match.getDiscordId()))
                            .findFirst();
    }

}
