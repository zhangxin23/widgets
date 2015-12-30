package net.coderland.server.task.cache;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Author: zhangxin
 * Date:   15-8-19
 */
public class StockCacheOld {

    @Resource(name = "redisTemplate")
    private RedisTemplate<String, String> template;

    private JedisConnectionFactory[] connectionFactories;

    private final static Random random = new Random();
    private final static int BOUND = 86400;
    private final static int DB_MAX_NUM = 15;

    private int hashKey(String key) {
        int hash = 0;
        for(int i = 0; i < key.length(); i++) {
            hash = 31 * hash + key.charAt(i);
        }
        return hash & 0x7FFFFFFF;
    }

    public void setDb(String key) {
        template.setConnectionFactory(connectionFactories[hashKey(key) % DB_MAX_NUM]);
    }

    public boolean exists(String key) {
        return template.hasKey(key);
    }

    public void delete(String key) {
        template.delete(key);
    }

    public boolean expireAtOnce(String key) {
        return template.expire(key, 10, TimeUnit.MICROSECONDS);
    }

    public boolean expire(String key) {
        long timeout = random.nextInt(BOUND) + BOUND;
        return template.expire(key, timeout, TimeUnit.SECONDS);
    }

    public String getEmailTagCount(String key) {
        return template.opsForValue().get(key);
    }

    public void setEmailTagCount(String key, String tag) {
        template.opsForValue().set(key, tag);
        expire(key);
    }

    public Set<String> getUserTagDetail(String key, long updateTime) {
        return template.opsForZSet().rangeByScore(key, updateTime, Double.MAX_VALUE);
    }

    public boolean setUserTagDetail(String key, String value, long updateTime) {
        return template.opsForZSet().add(key, value, updateTime);
    }

    public long setUserTagDetailSet(String key, String[] valueList, long[] scoreList) {
        assert(valueList.length == scoreList.length);

        Set<ZSetOperations.TypedTuple<String>> valueTupleSet = new HashSet<ZSetOperations.TypedTuple<String>>();
        int index = 0;
        for(String item: valueList) {
            ZSetOperations.TypedTuple tuple = new DefaultTypedTuple(item, (double)scoreList[index++]);
            valueTupleSet.add(tuple);
        }

        long rtn =  template.opsForZSet().add(key, valueTupleSet);
        expire(key);
        return rtn;
    }

    public JedisConnectionFactory[] getConnectionFactories() {
        return connectionFactories;
    }

    public void setConnectionFactories(JedisConnectionFactory[] connectionFactories) {
        this.connectionFactories = connectionFactories;
    }
}
