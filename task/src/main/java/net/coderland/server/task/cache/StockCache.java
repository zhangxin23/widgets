package net.coderland.server.task.cache;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * Author: zhangxin
 * Date:   15-12-30
 */
public class StockCache {

    @Resource(name = "redisTemplate")
    private RedisTemplate<String, String> template;

    private JedisConnectionFactory[] connectionFactories;

    private final static String key = "stock";

    public boolean empty() {
        return template.opsForList().size(key) == 0;
    }

    public long size() {
        return template.opsForList().size(key);
    }

    public String getKey() {
        return template.opsForList().leftPop(key);
    }

    public void setKey(String value) {
        template.opsForList().leftPush(key, value);
    }

    public JedisConnectionFactory[] getConnectionFactories() {
        return connectionFactories;
    }

    public void setConnectionFactories(JedisConnectionFactory[] connectionFactories) {
        this.connectionFactories = connectionFactories;
    }
}
