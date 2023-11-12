package dev.rifaii.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev.rifaii.exception.JsonParsingException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.json.Path;
import redis.clients.jedis.providers.PooledConnectionProvider;
import redis.clients.jedis.search.*;

import java.util.Collections;
import java.util.List;

import static dev.rifaii.util.PropertyLoader.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class RedisClient {

    private final HostAndPort hostAndPortConfig = new HostAndPort(REDIS_HOST, REDIS_PORT);
    private final JedisClientConfig clientConfig = DefaultJedisClientConfig.builder()
                                                                           .clientName("test")
                                                                           .password(REDIS_PASS)
                                                                           .build();
    private final PooledConnectionProvider provider = new PooledConnectionProvider(hostAndPortConfig, clientConfig);
    private final UnifiedJedis client = new UnifiedJedis(provider);

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());
    private static RedisClient instance;

    public static synchronized RedisClient getInstance() {
        if (instance == null) {
            instance = new RedisClient();
        }
        return instance;
    }

    public static void initialize() {
        getInstance().ping();
    }

    public <T> void insertJson(String key, T object) {
        try {
            client.jsonSetWithEscape(key, object);
        } catch (ClassCastException | JedisDataException e) {
            log.error("Failed to insert JSON, exception message: %s".formatted(e.getMessage()));
            throw e;
        }
    }

    public <T> T getJson(String key, Class<T> type) {
        try {
            return objectMapper.readValue(client.jsonGetAsPlainString(key, Path.ROOT_PATH), type);
        } catch (JedisDataException e) {
            log.error("Failed to get JSON, exception message: %s".formatted(e.getMessage()));
            throw e;
        } catch (JsonProcessingException e) {
            throw new JsonParsingException();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public <T> List<T> getJsonList(String key, Class<T> type) {
        try {
            return objectMapper.readValue(client.jsonGetAsPlainString(key, Path.ROOT_PATH), constructListType(type));
        } catch (JedisDataException e) {
            log.error("Failed to get JSON, exception message: %s".formatted(e.getMessage()));
            throw e;
        } catch (JsonProcessingException e) {
            throw new JsonParsingException();
        } catch (IllegalArgumentException e) {
            return Collections.emptyList();
        }
    }

    public void deleteJson(String key) {
        client.jsonDel(key);
        log.debug("Key %s deleted".formatted(key));
    }

    public boolean exists(String key) {
        return client.exists(key);
    }

    public void createIndex(Schema schema, IndexDefinition indexDefinition, String indexId) {
        if (!indexExists(indexId)) {
            client.ftCreate(
                    indexId,
                    IndexOptions.defaultOptions().setDefinition(indexDefinition),
                    schema
            );
        }
    }

    private boolean indexExists(String indexId) {
        return client.ftList().contains(indexId);
    }

    public <T> List<T> query(Query query, String index, Class<T> clazz) {
        var searchResult = client.ftSearch(index, query);

        if (isSearchResultEmpty(searchResult)) return Collections.emptyList();

        var json = searchResultToJsonString(searchResult);

        try {
            return objectMapper.readValue(json, constructListType(clazz));
        } catch (JsonProcessingException e) {
            log.error("Failed to parse value from search result");
            throw new JsonParsingException(e.getMessage());
        }
    }

    public SearchResult ftSearch(String index, Query query) {
        return client.ftSearch(index, query);
    }

    private <T> JavaType constructListType(Class<T> type) {
        return objectMapper.getTypeFactory().constructCollectionType(List.class, type);
    }

    private boolean isSearchResultEmpty(SearchResult searchResult) {
        return searchResult.getTotalResults() == 0 || !searchResult.getDocuments()
                                                                   .get(0)
                                                                   .getProperties()
                                                                   .iterator()
                                                                   .hasNext();
    }

    private String searchResultToJsonString(SearchResult searchResult) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        searchResult.getDocuments().stream().map(Document::getProperties).forEach(property -> {
            property.forEach(entry -> stringBuilder.append(entry.getValue()));
            stringBuilder.append(",");
        });
        //Temporary hack
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    private void ping() {
        client.ping();
    }
}
