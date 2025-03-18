package online.dongting.utils;

import cn.hutool.core.lang.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @author: ll
 * @since: 2025/1/10 HOUR:15 MINUTE:32
 * @description:
 */
@Slf4j
public class RedisLock implements IRedisLock{

    private final String lockKey;

    private final StringRedisTemplate stringRedisTemplate;

    private final static String KEY_PREFIX = "lock:";

    private final static String ID_PREFIX = UUID.randomUUID().toString(true);

    private static final DefaultRedisScript<Long> UNLOCK_SCRIPT;
    static {
        UNLOCK_SCRIPT = new DefaultRedisScript<>();
        UNLOCK_SCRIPT.setLocation(new ClassPathResource("unlock.lua"));
        UNLOCK_SCRIPT.setResultType(Long.class);
    }

    public RedisLock(String lockKey, StringRedisTemplate stringRedisTemplate) {
        this.lockKey = lockKey;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean tryLock(long timeoutSec) {

        String value = ID_PREFIX+Thread.currentThread().getId();
        Boolean absent = stringRedisTemplate.opsForValue().setIfAbsent(KEY_PREFIX + lockKey, value, timeoutSec, TimeUnit.SECONDS);
        return Boolean.TRUE.equals(absent);
    }

    @Override
    public void unlock() {
        stringRedisTemplate.execute(
                UNLOCK_SCRIPT,
                Collections.singletonList(KEY_PREFIX + lockKey),
                ID_PREFIX+Thread.currentThread().getId());

//        String value = ID_PREFIX+Thread.currentThread().getId();
//        String id = stringRedisTemplate.opsForValue().get(KEY_PREFIX + lockKey);
//        if (value.equals(id)){
//            stringRedisTemplate.delete(KEY_PREFIX + lockKey);
//        }
    }
}
