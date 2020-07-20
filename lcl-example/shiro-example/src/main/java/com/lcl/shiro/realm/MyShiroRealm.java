package com.lcl.shiro.realm;

import com.lcl.shiro.dao.UserDao;
import com.lcl.shiro.pojo.User;
import com.lcl.shiro.util.CodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

/**
 * @ClassName: MyShiroRealm
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/8 8:49
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Slf4j
public class MyShiroRealm extends AuthorizingRealm {
    @Resource
    private UserDao userDao;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        User userInfo  = (User)principalCollection.getPrimaryPrincipal();
        userInfo.getRoles().forEach(role -> {
            simpleAuthorizationInfo.addRole(role.getRoleName());
            role.getPermissionss().forEach(permissions -> {
                simpleAuthorizationInfo.addStringPermission(permissions.getPermissionsName());
            });
        });
        return simpleAuthorizationInfo;
    }

    /**
     * 身份认证 - 之后走上面授权
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userDao.selectByUsername(token.getUsername());
        if (user == null) {
            log.info("--->>> 用户不存在，登录失败");
            //返回null -> shiro就会知道这是用户不存在的异常
            return null;
        }
        if (!user.getPassword().equals(String.valueOf(token.getPassword()))) {
            log.info("--->>> 用户名或密码错误");
            throw new IncorrectCredentialsException("用户名或者密码错误");
        }
        if (user.getIsLock() == CodeEnum.YES.getCode()) {
            log.info("--->>> 用户被锁定");
            throw new LockedAccountException("用户已被锁定");
        }
        log.info("--->>> 登录用户,{}", user);
        /**
         * 进行验证 -> 注：shiro会自动验证密码
         * 参数1：principal -> 放对象就可以在页面任意地方拿到该对象里面的值
         * 参数2：hashedCredentials -> 密码
         * 参数3：credentialsSalt -> 设置盐值
         * 参数4：realmName -> 自定义的Realm
         */
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getUsername()), super.getName());
        // 验证成功开始踢人(清除缓存和Session)
//        ShiroUtils.deleteCache(token.getUsername(), true);
        // 认证成功后更新token
//        user.setToken(ShiroUtils.getSession().getId().toString());
//        userDao.updateById(user);
        return authenticationInfo;
    }
}
