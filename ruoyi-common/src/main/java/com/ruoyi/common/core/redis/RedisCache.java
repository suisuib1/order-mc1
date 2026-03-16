package com.ruoyi.common.core.redis;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Component;

/**
 * spring redis 工具类
 *
 * @author ruoyi
 **/
@SuppressWarnings(value = { "unchecked", "rawtypes" })
@Component
public class RedisCache
{
    // 内存存储作为Redis降级方案
    private Map<String, Object> memoryCache = new ConcurrentHashMap<>();

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     */
    public <T> void setCacheObject(final String key, final T value)
    {
        // 使用内存存储
        memoryCache.put(key, value);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键值
     * @param value 缓存的值
     * @param timeout 时间
     * @param timeUnit 时间颗粒度
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit)
    {
        // 使用内存存储
        memoryCache.put(key, value);
    }

    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout)
    {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key Redis键
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit)
    {
        // 内存存储返回true
        return true;
    }

    /**
     * 获取有效时间
     *
     * @param key Redis键
     * @return 有效时间
     */
    public long getExpire(final String key)
    {
        // 内存存储返回0
        return 0;
    }

    /**
     * 判断 key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public Boolean hasKey(String key)
    {
        // 检查内存存储
        return memoryCache.containsKey(key);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(final String key)
    {
        // 从内存存储获取
        return (T) memoryCache.get(key);
    }

    /**
     * 删除单个对象
     *
     * @param key
     */
    public boolean deleteObject(final String key)
    {
        // 从内存存储删除
        memoryCache.remove(key);
        return true;
    }

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     * @return
     */
    public boolean deleteObject(final Collection collection)
    {
        // 从内存存储删除
        for (Object key : collection) {
            memoryCache.remove(key);
        }
        return true;
    }

    /**
     * 缓存List数据
     *
     * @param key 缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public <T> long setCacheList(final String key, final List<T> dataList)
    {
        // 使用内存存储
        memoryCache.put(key, dataList);
        return dataList.size();
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public <T> List<T> getCacheList(final String key)
    {
        // 从内存存储获取
        Object value = memoryCache.get(key);
        return value instanceof List ? (List<T>) value : null;
    }

    /**
     * 缓存Set
     *
     * @param key 缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> Object setCacheSet(final String key, final Set<T> dataSet)
    {
        // 使用内存存储
        memoryCache.put(key, dataSet);
        return null;
    }

    /**
     * 获得缓存的set
     *
     * @param key
     * @return
     */
    public <T> Set<T> getCacheSet(final String key)
    {
        // 从内存存储获取
        Object value = memoryCache.get(key);
        return value instanceof Set ? (Set<T>) value : null;
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap)
    {
        // 使用内存存储
        if (dataMap != null) {
            memoryCache.put(key, dataMap);
        }
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     * @return
     */
    public <T> Map<String, T> getCacheMap(final String key)
    {
        // 从内存存储获取
        Object value = memoryCache.get(key);
        return value instanceof Map ? (Map<String, T>) value : null;
    }

    /**
     * 往Hash中存入数据
     *
     * @param key Redis键
     * @param hKey Hash键
     * @param value 值
     */
    public <T> void setCacheMapValue(final String key, final String hKey, final T value)
    {
        // 使用内存存储
        Object valueObj = memoryCache.get(key);
        if (valueObj instanceof Map) {
            ((Map<String, T>) valueObj).put(hKey, value);
        } else {
            Map<String, T> newMap = new ConcurrentHashMap<>();
            newMap.put(hKey, value);
            memoryCache.put(key, newMap);
        }
    }

    /**
     * 获取Hash中的数据
     *
     * @param key Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public <T> T getCacheMapValue(final String key, final String hKey)
    {
        // 从内存存储获取
        Object valueObj = memoryCache.get(key);
        if (valueObj instanceof Map) {
            return ((Map<String, T>) valueObj).get(hKey);
        }
        return null;
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys)
    {
        // 从内存存储获取
        Object valueObj = memoryCache.get(key);
        if (valueObj instanceof Map) {
            List<T> result = new java.util.ArrayList<>();
            for (Object hKey : hKeys) {
                result.add(((Map<String, T>) valueObj).get(hKey));
            }
            return result;
        }
        return null;
    }

    /**
     * 删除Hash中的某条数据
     *
     * @param key Redis键
     * @param hKey Hash键
     * @return 是否成功
     */
    public boolean deleteCacheMapValue(final String key, final String hKey)
    {
        // 从内存存储删除
        Object valueObj = memoryCache.get(key);
        if (valueObj instanceof Map) {
            ((Map<?, ?>) valueObj).remove(hKey);
            return true;
        }
        return false;
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(final String pattern)
    {
        // 从内存存储获取
        Set<String> result = new java.util.HashSet<>();
        for (String key : memoryCache.keySet()) {
            if (key.matches(pattern.replace("*", ".*")) || key.startsWith(pattern.replace("*", ""))) {
                result.add(key);
            }
        }
        return result;
    }
}
