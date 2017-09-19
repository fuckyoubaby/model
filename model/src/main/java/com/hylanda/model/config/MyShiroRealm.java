package com.hylanda.model.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.hylanda.model.common.service.UserService;
import com.hylanda.model.domain.Module;
import com.hylanda.model.domain.Role;
import com.hylanda.model.domain.User;


public class MyShiroRealm extends AuthorizingRealm {

	   /* //这里因为没有调用后台，直接默认只有一个用户("luoguohui"，"123456")
	    private static final String USER_NAME = "admin";  
	    private static final String PASSWORD = "123";  

	     
	     * 授权
	     
	    @Override
	    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) { 
	        Set<String> roleNames = new HashSet<String>();  
	        Set<String> permissions = new HashSet<String>();  
	        roleNames.add("administrator");//添加角色
	        permissions.add("newPage.jhtml");  //添加权限
	        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);  
	        info.setStringPermissions(permissions);  
	        return info;  
	    }

	     
	     * 登录验证
	     
	    @Override
	    protected AuthenticationInfo doGetAuthenticationInfo(
	            AuthenticationToken authcToken) throws AuthenticationException {
	        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
	        if(token.getUsername().equals(USER_NAME)){
	            return new SimpleAuthenticationInfo(USER_NAME,PASSWORD , getName());  //DecriptUtil.MD5(PASSWORD)
	        }else{
	            throw new AuthenticationException();  
	        }
	    }*/
	
	@Autowired
    private UserService userService;
    
    //认证.登录
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken utoken=(UsernamePasswordToken) token;//获取用户输入的token
        String username = utoken.getUsername();
        User user = userService.findUserByuserName(username);
        return new SimpleAuthenticationInfo(user, user.getPassword(),this.getClass().getName());//放入shiro.调用CredentialsMatcher检验密码
    }
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        User user=(User) principal.fromRealm(this.getClass().getName()).iterator().next();//获取session中的用户
        List<String> permissions=new ArrayList<>();
        Set<Role> roles = user.getRoles();
        if(roles.size()>0) {
            for(Role role : roles) {
                Set<Module> modules = role.getModules();
                if(modules.size()>0) {
                    for(Module module : modules) {
                        permissions.add(module.getMname());
                    }
                }
            }
        }
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        info.addStringPermissions(permissions);//将权限放入shiro中.
        return info;
    }

	
}
