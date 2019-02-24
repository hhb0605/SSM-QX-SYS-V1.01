package cn.qx.common.cache;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 
 * @author Satone
 * @date 2019年2月24日
 */
public class LRUCache implements Serializable{
    
    private static final long serialVersionUID = -8548928399682796788L;
    // 缓存
    private static LinkedHashMap<CacheKey, Object> cache;
    // 可重入读写锁
    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    // 期望容量
    private static final int CACHE_SIZE = 10;
    // 负载因子
    private static final float HASH_LOCAL_FACTORY = 0.75f;

    // 懒汉式式获取缓存
    public static Map<CacheKey, Object> getCache() {
        lock.readLock().lock();
        if (cache == null) {
            // 持有读锁获取写锁，必须释放读锁
            lock.readLock().unlock();
            lock.writeLock().lock();;
            if (cache == null) {
                // Map容量，因为HashMap在此关系时发生增长操作
                int capacity = (int) Math.ceil(CACHE_SIZE / HASH_LOCAL_FACTORY) + 1;
                // 匿名内部类，继承LinkedHashMap，重写removeEldestEntry
                cache = new LinkedHashMap<CacheKey, Object>(capacity, HASH_LOCAL_FACTORY, true) {

                    private static final long serialVersionUID = -5489035837306256867L;

                    // 此方法在put()与putAll()时执行
                    @Override
                    protected boolean removeEldestEntry(java.util.Map.Entry<CacheKey, Object> eldest) {
                        System.out.println("removeEldestEntry() = "+(this.size() > CACHE_SIZE));
                        return this.size() > CACHE_SIZE;
                    }
                };
            }
            // 降级锁，在释放写锁前获取读锁
            lock.readLock().lock();
            lock.writeLock().unlock();
        }
        // 解除读锁
        lock.readLock().unlock();
        return cache;
    }

    // 添加映射，返回操作是否成功
    public boolean put(CacheKey key, Object value) {
        if (cache == null) {
            throw new NullPointerException("缓存为空");
        }
        lock.writeLock().lock();
        cache.put(key, value);
        lock.writeLock().unlock();
        return true;
    }

    // 获取值，无缓存返回null
    public Object get(CacheKey key) {
        lock.readLock().lock();
        lock.readLock().unlock();
        return cache.get(key);
    }
}
