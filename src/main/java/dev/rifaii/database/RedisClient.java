package dev.rifaii.database;

import dev.rifaii.util.PropertyLoader;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.providers.PooledConnectionProvider;

import static dev.rifaii.util.PropertyLoader.REDIS_PASS;
import static dev.rifaii.util.PropertyLoader.REDIS_PORT;
import static java.lang.Integer.parseInt;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RedisClient {

    private final HostAndPort hostAndPortConfig = new HostAndPort(PropertyLoader.REDIS_HOST, parseInt(REDIS_PORT));
    private final JedisClientConfig clientConfig = DefaultJedisClientConfig.builder()
            .clientName("test")
            .password(REDIS_PASS)
            .build();
    private final PooledConnectionProvider provider = new PooledConnectionProvider(hostAndPortConfig, clientConfig);
    private final UnifiedJedis unifiedJedis = new UnifiedJedis(provider);

    private static RedisClient instance;

    public static synchronized RedisClient getInstance() {
        if (instance == null) {
            instance = new RedisClient();
        }
        return instance;
    }

    public <T> void insertJson(String key, T object) {
        System.out.println(object);
        unifiedJedis.jsonSet(key, object);
    }

    public <T> T getJson(String key) {
        return (T) unifiedJedis.jsonGet(key);
    }

    public void ping() {
        unifiedJedis.ping();
    }
}
