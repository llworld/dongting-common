package online.dongting.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 缓存工具类.
 * @author ll
 */
@Slf4j
public class RedisUtil {

  private static RedisTemplate<Object, Object> redisTemplate;
  private static  final  String ERROR_LOG="redis exception:";
  public void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
    RedisUtil.redisTemplate = redisTemplate;
  }

  /**
   * 释放缓存链接
   */
  public static void unbindConnection() {
    RedisConnectionUtils
        .unbindConnection(Objects.requireNonNull(redisTemplate.getConnectionFactory()));
  }

  /**
   * 【指定缓存失效时间】
   */
  public static boolean expire(String key, long time) {
    try {
      if (time > 0) {
        redisTemplate.expire(key, time, TimeUnit.SECONDS);
      }
      return true;
    } catch (Exception e) {
      log.error(ERROR_LOG+e);
      return false;
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【根据key 获取过期时间】
   *
   * @param key 键 不能为null
   * @return 时间(秒) 返回0代表为永久有效
   */
  public static long getExpire(String key) {
    return redisTemplate.getExpire(key, TimeUnit.SECONDS);
  }

  /**
   * 【判断key是否存在】
   *
   * @param key 键
   * @return true 存在 false不存在
   */
  public static boolean isKey(String key) {
    try {
      return redisTemplate.hasKey(key);
    } catch (Exception e) {
      log.error(ERROR_LOG+e);
      return false;
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【删除缓存】
   *
   * @param key 可以传一个值 或多个
   */
  public static Integer delete(String... key) {
    if (key != null && key.length > 0) {
      try {
        if (key.length == 1) {
          if (redisTemplate.delete(key[0])) {
            return 1;
          }
        } else {
          redisTemplate.delete(CollectionUtils.arrayToList(key));
          return key.length;
        }
      } finally {
        unbindConnection();
      }
    }
    return 0;
  }


  /**
   * 【普通缓存获取】
   *
   * @param key 键
   * @return 值
   */
  public static Object get(String key) {
    try {
      return key == null ? null : redisTemplate.opsForValue().get(key);
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【普通缓存放入】
   *
   * @param key 键
   * @param value 值
   * @return true成功 false失败
   */
  public static boolean set(String key, Object value) {
    try {
      redisTemplate.opsForValue().set(key, value);
      return true;
    } catch (Exception e) {
      log.error(ERROR_LOG+e);
      return false;
    } finally {
      unbindConnection();
    }

  }

  /**
   * 【普通缓存放入并设置时间】
   *
   * @param key 键
   * @param value 值
   * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
   * @return true成功 false 失败
   */
  public static boolean set(String key, long time, Object value) {
    try {
      if (time > 0) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
      } else {
        set(key, value);
      }
      return true;
    } catch (Exception e) {
      log.error(ERROR_LOG+e);
      return false;
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【递增】
   *
   * @param key 键
   * @param delta 要增加几(大于0)
   */
  public static long incr(String key, long delta) {
    if (delta < 0) {
      throw new RuntimeException("递增因子必须大于0");
    }
    try {
      return redisTemplate.opsForValue().increment(key, delta);
    } finally {
      unbindConnection();
    }
  }


  /**
   * 【递减】
   *
   * @param key 键
   * @param delta 要减几(大于0)
   */
  public static long decr(String key, long delta) {
    if (delta < 0) {
      throw new RuntimeException("递减因子必须大于0");
    }
    try {
      return redisTemplate.opsForValue().increment(key, -delta);
    } finally {
      unbindConnection();
    }
  }


  /**
   * 【HashGet】
   *
   * @param key 键 不能为null
   * @param item 项 不能为null
   * @return 值
   */
  public static Object hashGet(String key, String item) {
    try {
      return redisTemplate.opsForHash().get(key, item);
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【获取hashKey对应的所有键值】
   *
   * @param key 键
   * @return 对应的多个键值
   */

  public static Map<Object, Object> hashGet(String key) {
    try {
      return redisTemplate.opsForHash().entries(key);
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【HashSet】
   *
   * @param key 键
   * @param map 对应多个键值
   * @return true 成功 false 失败
   */
  public static boolean hashsSet(String key, Map<String, Object> map) {
    try {
      redisTemplate.opsForHash().putAll(key, map);
      return true;
    } catch (Exception e) {
      log.error(ERROR_LOG+e);
      return false;
    } finally {
      unbindConnection();
    }
  }


  /**
   * 【HashSet 并设置时间】
   *
   * @param key 键
   * @param map 对应多个键值
   * @param time 时间(秒)
   * @return true成功 false失败
   */
  public static boolean hashsSet(String key, Map<String, Object> map, long time) {
    try {
      redisTemplate.opsForHash().putAll(key, map);
      if (time > 0) {
        expire(key, time * 60);
      }
      return true;
    } catch (Exception e) {
      log.error(ERROR_LOG+e);
      return false;
    } finally {
      unbindConnection();
    }
  }


  /**
   * 【向一张hash表中放入数据,如果不存在将创建】
   *
   * @param key 键
   * @param item 项
   * @param value 值
   * @return true 成功 false失败
   */
  public static boolean hashsSet(String key, String item, Object value) {
    try {
      redisTemplate.opsForHash().put(key, item, value);
      return true;
    } catch (Exception e) {
      log.error(ERROR_LOG+e);
      return false;
    } finally {
      unbindConnection();
    }
  }

  public static Set<Object> getKeys(String key) {
    try {
      return redisTemplate.keys(key);
    } finally {
      unbindConnection();
    }
  }

  /**
   * 获取指定变量中的hashMap值
   */
  public static List hashGetToList(String key) {
    try {
      return redisTemplate.opsForHash().values(key);
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【向一张hash表中放入数据,如果不存在将创建】
   *
   * @param key 键
   * @param item 项
   * @param value 值
   * @param time 时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
   * @return true 成功 false失败
   */
  public static boolean hashsSet(String key, String item, Object value, long time) {
    try {
      redisTemplate.opsForHash().put(key, item, value);
      if (time > 0) {
        expire(key, time);
      }
      return true;
    } catch (Exception e) {
      log.error(ERROR_LOG+e);
      return false;
    } finally {
      unbindConnection();
    }
  }


  /**
   * 【删除hash表中的值】
   *
   * @param key 键 不能为null
   * @param item 项 可以使多个 不能为null
   */
  public static void delectHash(String key, Object... item) {
    try {
      redisTemplate.opsForHash().delete(key, item);
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【判断hash表中是否有该项的值】
   *
   * @param key 键 不能为null
   * @param item 项 不能为null
   * @return true 存在 false不存在
   */
  public static boolean isHashKey(String key, String item) {
    try {
      return redisTemplate.opsForHash().hasKey(key, item);
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【hash递增 如果不存在,就会创建一个 并把新增后的值返回】
   *
   * @param key 键
   * @param item 项
   * @param by 要增加几(大于0)
   */
  public static double hashIncr(String key, String item, double by) {
    try {
      return redisTemplate.opsForHash().increment(key, item, by);
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【hash递减 并把新增后的值返回】
   *
   * @param key 键
   * @param item 项
   * @param by 要减少记(小于0)
   */
  public static double hashDecr(String key, String item, double by) {
    try {
      return redisTemplate.opsForHash().increment(key, item, -by);
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【根据key获取Set中的所有值】
   *
   * @param key 键
   */
  public static Set<Object> setGet(String key) {
    try {
      return redisTemplate.opsForSet().members(key);
    } catch (Exception e) {
      log.error(ERROR_LOG+e);
      return null;
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【根据value从一个set中查询,是否存在】
   *
   * @param key 键
   * @param value 值
   * @return true 存在 false不存在
   */
  public static boolean setIsKey(String key, Object value) {
    try {
      return redisTemplate.opsForSet().isMember(key, value);
    } catch (Exception e) {
      log.error(ERROR_LOG+e);
      return false;
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【将数据放入set缓存】
   *
   * @param key 键
   * @param values 值 可以是多个
   * @return 成功个数
   */
  public static long setSet(String key, Object... values) {
    try {
      return redisTemplate.opsForSet().add(key, values);
    } catch (Exception e) {
      log.error(ERROR_LOG+e);
      return 0;
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【将set数据放入缓存】
   *
   * @param key 键
   * @param time 时间(秒)
   * @param values 值 可以是多个
   * @return 成功个数
   */
  public static long setSetAndTime(String key, long time, Object... values) {
    try {
      Long count = redisTemplate.opsForSet().add(key, values);
      if (time > 0) {
        expire(key, time * 60);
      }
      return count;
    } catch (Exception e) {
      log.error(ERROR_LOG+e);
      return 0;
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【获取set缓存的长度】
   *
   * @param key 键
   */
  public static long setGetSetSize(String key) {
    try {
      return redisTemplate.opsForSet().size(key);
    } catch (Exception e) {
      log.error(ERROR_LOG+e);
      return 0;
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【移除值为value的】
   *
   * @param key 键
   * @param values 值 可以是多个
   * @return 移除的个数
   */
  public static long setRemove(String key, Object... values) {
    try {
      Long count = redisTemplate.opsForSet().remove(key, values);
      return count;
    } catch (Exception e) {
      log.error(ERROR_LOG+e);
      return 0;
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【获取list缓存的内容】
   *
   * @param key 键
   * @param start 开始
   * @param end 结束  0 到 -1代表所有值
   */
  public static List<Object> listGet(String key, long start, long end) {
    try {
      return redisTemplate.opsForList().range(key, start, end);
    } catch (Exception e) {
      log.error(ERROR_LOG+e);
      return null;
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【获取list缓存的长度】
   *
   * @param key 键
   */
  public static long listGetListSize(String key) {
    try {
      return redisTemplate.opsForList().size(key);
    } catch (Exception e) {
      log.error(ERROR_LOG+e);
      return 0;
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【通过索引 获取list中的值】
   *
   * @param key 键
   * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
   */
  public static Object listGetIndex(String key, long index) {
    try {
      return redisTemplate.opsForList().index(key, index);
    } catch (Exception e) {
      log.error(ERROR_LOG+e);
      return null;
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【将list放入缓存】
   *
   * @param key 键
   * @param value 值
   */
  public static boolean listSet(String key, Object value) {
    try {
      redisTemplate.opsForList().rightPush(key, value);
      return true;
    } catch (Exception e) {
      log.error(ERROR_LOG+e);
      return false;
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【将list放入缓存】
   *
   * @param key 键
   * @param value 值
   * @param time 时间(秒)
   */
  public static boolean listSet(String key, Object value, long time) {
    try {
      redisTemplate.opsForList().rightPush(key, value);
      if (time > 0) {
        expire(key, time * 60);
      }
      return true;
    } catch (Exception e) {
      log.error(ERROR_LOG+e);
      return false;
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【将list放入缓存】
   *
   * @param key 键
   * @param value 值
   */
  public static boolean listSet(String key, List<Object> value) {
    try {
      redisTemplate.opsForList().rightPushAll(key, value);
      return true;
    } catch (Exception e) {
      log.error(ERROR_LOG+e);
      return false;
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【将list放入缓存】
   *
   * @param key 键
   * @param value 值
   * @param time 时间(秒)
   */
  public static boolean listSet(String key, List<Object> value, long time) {
    try {
      redisTemplate.opsForList().rightPushAll(key, value);
      if (time > 0) {
        expire(key, time * 60);
      }
      return true;
    } catch (Exception e) {
      log.error(ERROR_LOG+e);
      return false;
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【根据索引修改list中的某条数据】
   *
   * @param key 键
   * @param index 索引
   * @param value 值
   */
  public static boolean listUpdateIndex(String key, long index, Object value) {
    try {
      redisTemplate.opsForList().set(key, index, value);
      return true;
    } catch (Exception e) {
      log.error(ERROR_LOG+e);
      return false;
    } finally {
      unbindConnection();
    }
  }

  /**
   * 【移除N个值为value】
   *
   * @param key 键
   * @param count 移除多少个
   * @param value 值
   * @return 移除的个数
   */
  public static long listRemove(String key, long count, Object value) {
    try {
      Long remove = redisTemplate.opsForList().remove(key, count, value);
      return remove;
    } catch (Exception e) {
      log.error(ERROR_LOG+e);
      return 0;
    } finally {
      unbindConnection();
    }
  }

  /**
   * <h4>功能：[redis的有序集合]</h4>
   * <h4></h4>
   */
  public static void zSetAdd(String key, Object object, Double score) {
    try {
      redisTemplate.opsForZSet().add(key, object, score);
    } catch (Exception e) {
      log.error(ERROR_LOG+e);
    } finally {
      unbindConnection();
    }
  }

  public static void zSetAdd(String key, Set<ZSetOperations.TypedTuple<Object>> tuples) {
    try {
      redisTemplate.opsForZSet().add(key, tuples);
    } catch (Exception e) {
      log.error(ERROR_LOG+e);
    } finally {
      unbindConnection();
    }
  }


  public static void zSetAdd(String key, String value) {
    try {
      redisTemplate.opsForZSet().add(key, value,System.currentTimeMillis());
    } catch (Exception e) {
      log.error(ERROR_LOG+e);
    } finally {
      unbindConnection();
    }
  }

  /**
   * 返回有序集合中,成员的分数值
   * @param key
   * @param value
   * @return
   */
  public static Double zSetScore(String key, String value) {
    try {
        return redisTemplate.opsForZSet().score(key, value);
    } catch (Exception e) {
      log.error(ERROR_LOG+e);
    } finally {
      unbindConnection();
    }
    return null;
  }

  /**
   * <h4>功能：[redis的有序集合]</h4>
   * <h4></h4>
   */
  public static Long zSetCount(String key, Double min, Double max) {
    try {
      Long count = redisTemplate.opsForZSet().count(key, min, max);
      return count;
    } catch (Exception e) {
      log.error(ERROR_LOG+e);
      return 0L;
    } finally {
      unbindConnection();
    }
  }

  /**
   * <h4>功能：[zset集合的数量]</h4>
   * <h4></h4>
   */
  public static Long zSetListSize(String key) {
    try {
      Long size = redisTemplate.opsForZSet().size(key);
      return size;
    } catch (Exception e) {
      log.error(ERROR_LOG+e);
      return 0L;
    } finally {
      unbindConnection();
    }
  }

  /**
   * <h4>功能：[redis的有序集合从大到小获取]</h4>
   * <h4></h4>
   */
  public static Set<Object> zSetGetList(String key, int min, int max) {
    try {
      Set<Object> list = redisTemplate.opsForZSet().reverseRange(key, min, max);
      return list;
    } catch (Exception e) {
      log.error(ERROR_LOG+e);
      return null;
    } finally {
      unbindConnection();
    }
  }

  public static boolean zSetRemove(String key, Object... values) {
    try {
      return redisTemplate.opsForZSet().remove(key, values) > 0L;
    } finally {
      unbindConnection();
    }
  }
}
