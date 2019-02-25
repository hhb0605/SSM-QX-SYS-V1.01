package cn.qx.common.aspect;

import java.lang.reflect.Method;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import cn.qx.common.cache.CacheKey;
import cn.qx.common.cache.LRUCache;


@Aspect
@Service
public class SysCacheAspect {
    
    @Around("@annotation(cn.qx.common.annotation.RequestCache)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        if(!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        MethodSignature methodSignature = (MethodSignature)signature;
        Method method = joinPoint.getTarget().getClass().getDeclaredMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        CacheKey cacheKey = new CacheKey();
        cacheKey.setArgs(joinPoint.getArgs());
        cacheKey.setTargetClass(joinPoint.getTarget().getClass());
        cacheKey.setTargetMethod(method);
        Map<CacheKey, Object> cache = LRUCache.getCache();
        Object result = cache.get(cacheKey);
        if(result == null) {
            result = joinPoint.proceed();
            cache.put(cacheKey, result);
        }
        return result;
    }
}
