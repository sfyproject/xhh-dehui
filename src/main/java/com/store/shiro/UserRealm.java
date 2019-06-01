package com.store.shiro;

import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.store.model.Admin;
import com.store.service.AdminService;
import com.store.service.SysMenuService;
import com.store.utils.ShiroUtils;
import com.store.utils.Utils;




//授权领域
public class UserRealm extends AuthorizingRealm {
	@Autowired
	private AdminService adminService;
    @Autowired
    private SysMenuService sysMenuService;

	@Override
	//授权  调用SecurityUtils.getSubject().isPermitted(String str)方法时会调用doGetAuthorizationInfo方法
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		String adminId = ShiroUtils.getUserId();
		
		
		 // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
        Set<String> permsList = sysMenuService.getPermsListByAdminId(adminId);//获取用户权限
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(permsList);
		return info;
	}

	@Override 
	//认证 .调用currUser.login(token)方法时会调用doGetAuthenticationInfo方法
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String adminUsername = (String) token.getPrincipal();
		String adminPassword = new String((char[]) token.getCredentials());
		// 查询用户信息
		Admin admin = adminService.loadAdminByUsername(adminUsername);
		// 账号不存在
		if (admin == null) {
			throw new UnknownAccountException("账号或密码不正确");
		}
		// 密码错误
		String password=Utils.decryptByBase64(admin.getAdminPassword(), "utf-8");
		if (!adminPassword.equals(password)) {
			throw new IncorrectCredentialsException("账号或密码不正确");
		}
		Session session = SecurityUtils.getSubject().getSession();
		session.setAttribute("login", admin);
        //第三个字段是realm，即当前realm的名称。
		AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(admin, adminPassword, getName());
		return authcInfo;
	}

}
