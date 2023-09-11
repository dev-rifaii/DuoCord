package dev.rifaii.user;

import dev.rifaii.activity.Activity;
import dev.rifaii.database.RedisClient;
import redis.clients.jedis.search.IndexDefinition;
import redis.clients.jedis.search.Query;
import redis.clients.jedis.search.Schema;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserQueueDao {

    public static final String ENQUEUED_USER_KEY_PREFIX = "ENQUSR:";
    public static final String USER_MATCH_HISTORY_KEY_PREFIX = "USRMTCHHIS:";
    public static final String SEARCH_SESSION_KEY_PREFIX = "SRCHSES:";
    public static final String IDX_ENQ_USR = "IDX:ENQUSR";
    private final RedisClient redisClient = RedisClient.getInstance();

    public UserQueueDao() {
        indexFieldsToQuery();
    }

    public void insertToQueue(User user) {
        redisClient.insertJson(ENQUEUED_USER_KEY_PREFIX + user.getDiscordId(), user);
    }

    public boolean isUserInQueue(Long userDiscordId) {
        return redisClient.exists(ENQUEUED_USER_KEY_PREFIX + userDiscordId);
    }

    public List<User> getEnqueuedUsersByAgeAndActivity(Short age, Activity activity) {
        String ageRange = age < 18 ? "[-inf (18]" : "[18 +inf]";
        var query = new Query("@\\$\\.activity:%s @\\$\\.age:%s".formatted(activity.name(), ageRange));
        return redisClient.query(query, IDX_ENQ_USR, User.class);
    }

    public void upsertMatch(Long userDiscordId, Long matchDiscordId) {
        var userMatchHistory = redisClient.getJson(USER_MATCH_HISTORY_KEY_PREFIX + userDiscordId, ArrayList.class);

        if (userMatchHistory == null) {
            userMatchHistory = new ArrayList<Long>();
        }

        userMatchHistory.add(matchDiscordId);
        redisClient.insertJson(USER_MATCH_HISTORY_KEY_PREFIX + userDiscordId, userMatchHistory);
    }

    public List<Long> getMatchHistoryForUser(Long userDiscordId) {
        return redisClient.getJsonList(USER_MATCH_HISTORY_KEY_PREFIX + userDiscordId, Long.class);

    }

    public void insertSearchSession(MatchSearchDto matchSearchDto) {
        redisClient.insertJson(SEARCH_SESSION_KEY_PREFIX + matchSearchDto.getUserDiscordId(), matchSearchDto);
    }

    public void deleteSession(Long userDiscordId) {
        redisClient.deleteJson(SEARCH_SESSION_KEY_PREFIX + userDiscordId);
    }

    public Optional<MatchSearchDto> getUserSearchDto(Long userDiscordId) {
        return Optional.of(redisClient.getJson(SEARCH_SESSION_KEY_PREFIX + userDiscordId, MatchSearchDto.class));
    }

    private void indexFieldsToQuery() {
        var schema = new Schema()
                .addTextField("$.activity", 1.0)
                .addNumericField("$.age");

        var rule = new IndexDefinition(IndexDefinition.Type.JSON)
                .setPrefixes(ENQUEUED_USER_KEY_PREFIX);
        redisClient.createIndex(schema, rule, IDX_ENQ_USR);
    }
}


