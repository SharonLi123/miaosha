package top.safun.miaosha.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Service
public class RedisService {

    @Autowired
    RedisConfig redisConfig;

    @Autowired
    JedisPool jedisPool;

    public <T> T get(KeyPrefix prefix,String key,Class<T> clazz){
        Jedis jedis=null;
        try {
            jedis=jedisPool.getResource();
            String realKey=prefix.getPrefix()+key;
            String value=jedis.get(realKey);
            return stringToBean(value,clazz);
        }finally {
            returnToPool(jedis);
        }
    }

    public <T> boolean set(KeyPrefix prefix,String key,T value){
        Jedis jedis=null;
        try {
            jedis=jedisPool.getResource();
            String str=BeanToString(value);
            String realKey=prefix.getPrefix()+key;
            int seconds=prefix.expireSeconds();

            if(seconds<=0){
                jedis.set(realKey,str);
            }else {
                jedis.setex(realKey,seconds,str);
            }

            return true;
        }finally {
            returnToPool(jedis);
        }
    }

    public boolean exist(KeyPrefix prefix,String key){
        Jedis jedis=null;
        try {
            jedis=jedisPool.getResource();
            String realKey=prefix.getPrefix()+key;
            return jedis.exists(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    public Long decr(KeyPrefix prefix,String key){
        Jedis jedis=null;
        try {
            jedis=jedisPool.getResource();
            String realKey=prefix.getPrefix()+key;
            return jedis.decr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    public Long incr(KeyPrefix prefix,String key){
        Jedis jedis=null;
        try {
            jedis=jedisPool.getResource();
            String realKey=prefix.getPrefix()+key;
            return jedis.incr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    private <T> String BeanToString(T value){
        if (value==null){
            return null;
        }
         Class<?> clazz=value.getClass();
        if (clazz==String.class){
            return (String) value;
        }else if (clazz==int.class || clazz==Integer.class){
            return ""+value;
        }else if (clazz==long.class || clazz==Long.class){
            return ""+value;
        }else {
            return JSON.toJSONString(value);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T stringToBean(String str,Class<T> clazz){
        if (str==null || str.length()==0 || clazz==null){
            return null;
        }
        if (clazz==String.class){
            return (T) str;
        }else if (clazz==int.class || clazz==Integer.class){
            return (T)(Integer.valueOf(str));
        }else if(clazz==long.class || clazz==Long.class){
            return (T)(Long.valueOf(str));
        }else {
            return JSON.toJavaObject(JSON.parseObject(str),clazz);
        }
    }

    private void returnToPool(Jedis jedis){
        if (jedis!=null){
            jedis.close();
        }
    }


    @Bean
    public JedisPool jedisPoolFactory(){
        JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
        jedisPoolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
        jedisPoolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait()*1000);

        JedisPool jedisPool=new JedisPool(jedisPoolConfig,redisConfig.getHost(),
                redisConfig.getPort(),redisConfig.getTimeout()*1000);
        return jedisPool;
    }
}
