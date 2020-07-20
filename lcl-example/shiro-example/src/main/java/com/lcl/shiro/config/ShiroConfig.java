package com.lcl.shiro.config;

import com.lcl.shiro.realm.MyShiroRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroWebFilterConfiguration;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName: ShiroConfig
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/8 8:48
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Configuration
public class ShiroConfig {
    private final String CACHE_KEY = "shiro:cache:";
    private final String SESSION_KEY = "shiro:session:";
    /**
     * 默认过期时间30分钟，即在30分钟内不进行操作则清空缓存信息，页面即会提醒重新登录
     */
    private final int EXPIRE = 1800;

    /**
     * 开启Shiro-aop注解支持：使用代理方式所以需要开启代码支持
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * MD5方法解析密码
     */
    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm realm = new MyShiroRealm();
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(3);
        realm.setCredentialsMatcher(matcher);
        return realm;
    }

    /**
     * ShiroFilterChainDefinition
     */
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        Map<String, String> filterChainMap = chainDefinition.getFilterChainMap();
        //权限配置
        //filterChainDefinitionMap.put("/stu/addStu","perms[student:aaaa]");
        // 配置不会被拦截的链接 顺序判断  相关静态资源
        filterChainMap.put("/assets/**", "anon");
        filterChainMap.put("/css/**", "anon");
        filterChainMap.put("/font/**", "anon");
        filterChainMap.put("/images/**", "anon");
        filterChainMap.put("/js/**", "anon");
        filterChainMap.put("/products/**", "anon");
        filterChainMap.put("/Widget/**", "anon");
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainMap.put("/logout", "logout");
        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        filterChainMap.put("/**", "authc");
        chainDefinition.addPathDefinition("/**", "authc");
        return chainDefinition;
    }

    /**
     * 安全管理器
     */
    @Bean
    public SessionsSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 自定义session管理
//        securityManager.setSessionManager(sessionManager());
        // 自定义Cache实现缓存管理
//        securityManager.setCacheManager(cacheManager());
        // 自定义Realm验证
        securityManager.setRealm(this.myShiroRealm());
        return securityManager;
    }

    /**
     * 配置Cache管理器：用于往Redis存储权限和角色标识  (使用的是shiro-redis开源插件)
     */
//    @Bean
//    public RedisCacheManager cacheManager() {
//        RedisCacheManager redisCacheManager = new RedisCacheManager();
//        redisCacheManager.setRedisManager(redisManager());
//        redisCacheManager.setKeyPrefix(CACHE_KEY);
//        // 配置缓存的话要求放在session里面的实体类必须有个id标识 注：这里id为用户表中的主键，否-> 报：User must has getter for field: xx
//        redisCacheManager.setPrincipalIdFieldName("id");
//        return redisCacheManager;
//    }

    /**
     * SessionID生成器
     */
//    @Bean
//    public ShiroSessionIdGenerator sessionIdGenerator(){
//        return new ShiroSessionIdGenerator();
//    }


    /**
     * 配置Session管理器
     */
//    @Bean
//    public SessionManager sessionManager() {
//        ShiroSessionManager shiroSessionManager = new ShiroSessionManager();
//        shiroSessionManager.setSessionDAO(redisSessionDAO());
//        return shiroSessionManager;
//    }

}
