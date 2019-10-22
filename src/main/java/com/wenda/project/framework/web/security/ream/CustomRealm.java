package com.wenda.project.framework.web.security.ream;

import com.wenda.project.entity.TbUser;
import com.wenda.project.framework.constant.StatusEnum;
import com.wenda.project.framework.constant.UserTypeEnum;
import com.wenda.project.framework.web.base.dao.CommonDao;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private Environment environment;

    /**
     * 添加权限
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //权限
        Set<String> stringSet = new HashSet<>();
        info.setStringPermissions(stringSet);
        //角色
        CommonDao<TbUser> dao = new CommonDao<>(TbUser.class);
        Set<String> roleSet = new HashSet<>();
        try {
           TbUser user = dao.find(new String[]{"user_name=?","status=?"},new Object[]{userName, StatusEnum.NORMAL.getId()});
            if(user.getUserType() == UserTypeEnum.ADMIN.getId()){
                roleSet.add("admin");
            }else {
                roleSet.add("user");
            }
            info.setRoles(roleSet);

        } catch (Exception e) {
            e.printStackTrace();
            throw new AccountException("登录时发生系统错误，详情：" + e.toString());
        }

        return info;
    }

    /**
     * private UserService userService;
     * <p>
     * 获取即将需要认证的信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String userName = (String) authenticationToken.getPrincipal();
        String userPwd = new String((char[]) authenticationToken.getCredentials());

        //根据用户名从数据库获取密码
        if (StringUtils.isBlank(userName)) {
            throw new AccountException("用户名不正确");
        }
        if (StringUtils.isBlank(userPwd)) {
            throw new AccountException("密码错误");
        }
        CommonDao<TbUser> dao = new CommonDao<>(TbUser.class);
        TbUser user = null;
        try {
            user = dao.find(new String[]{"user_name=?","status=?"},new Object[]{userName, StatusEnum.NORMAL.getId()});
        } catch (Exception e) {
            e.printStackTrace();
            throw new AccountException("登录时发生系统错误，详情：" + e.toString());
        }

        if (user == null) {
            throw new UnknownAccountException("用户不存");
        }
        if (!userPwd.equals(user.getPassword())) {
            throw new AccountException("登录密码错误");
        }
        return new SimpleAuthenticationInfo(userName, userPwd, getName());
    }
}