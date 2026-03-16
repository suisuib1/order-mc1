package com.ruoyi.framework.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * redis配置
 * 
 * @author ruoyi
 */
@SuppressWarnings("deprecation")
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport
{
    // Redis配置已禁用，使用内存存储作为替代
}

