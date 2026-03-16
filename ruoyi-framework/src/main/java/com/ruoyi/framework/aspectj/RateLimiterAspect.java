package com.ruoyi.framework.aspectj;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.ruoyi.common.annotation.RateLimiter;
import com.ruoyi.common.enums.LimitType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.ip.IpUtils;

/**
 * 限流处理
 *
 * @author ruoyi
 */
@Aspect
@Component
public class RateLimiterAspect
{
    private static final Logger log = LoggerFactory.getLogger(RateLimiterAspect.class);

    // 内存存储用于限流
    private Map<String, RateLimitInfo> rateLimitMap = new ConcurrentHashMap<>();

    @Before("@annotation(rateLimiter)")
    public void doBefore(JoinPoint point, RateLimiter rateLimiter) throws Throwable
    {
        int time = rateLimiter.time();
        int count = rateLimiter.count();

        String combineKey = getCombineKey(rateLimiter, point);
        try
        {
            RateLimitInfo rateLimitInfo = rateLimitMap.get(combineKey);
            long currentTime = System.currentTimeMillis();
            
            if (rateLimitInfo == null) {
                rateLimitInfo = new RateLimitInfo(currentTime, new AtomicInteger(1));
                rateLimitMap.put(combineKey, rateLimitInfo);
            } else {
                // 检查是否在时间窗口内
                if (currentTime - rateLimitInfo.getStartTime() > time * 1000) {
                    // 时间窗口已过，重置计数
                    rateLimitInfo.setStartTime(currentTime);
                    rateLimitInfo.getCount().set(1);
                } else {
                    // 时间窗口内，增加计数
                    int currentCount = rateLimitInfo.getCount().incrementAndGet();
                    if (currentCount > count) {
                        throw new ServiceException("访问过于频繁，请稍候再试");
                    }
                    log.info("限制请求'{}',当前请求'{}',缓存key'{}'", count, currentCount, combineKey);
                }
            }
        }
        catch (ServiceException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new RuntimeException("服务器限流异常，请稍候再试");
        }
    }

    public String getCombineKey(RateLimiter rateLimiter, JoinPoint point)
    {
        StringBuffer stringBuffer = new StringBuffer(rateLimiter.key());
        if (rateLimiter.limitType() == LimitType.IP)
        {
            stringBuffer.append(IpUtils.getIpAddr()).append("-");
        }
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Class<?> targetClass = method.getDeclaringClass();
        stringBuffer.append(targetClass.getName()).append("-").append(method.getName());
        return stringBuffer.toString();
    }

    // 限流信息类
    private static class RateLimitInfo {
        private long startTime;
        private AtomicInteger count;

        public RateLimitInfo(long startTime, AtomicInteger count) {
            this.startTime = startTime;
            this.count = count;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public AtomicInteger getCount() {
            return count;
        }
    }
}
