package online.dongting.utils;

/**
 * @author: ll
 * @since: 2025/1/10 HOUR:15 MINUTE:30
 * @description:
 */
public interface IRedisLock {

    /**
     * 尝试加锁
     * @param timeoutSec 超时时间
     * @return true:获取锁成功 false:获取锁失败
     */
    boolean tryLock(long timeoutSec);

    /**
     * 释放锁
     */
    void unlock();
}
