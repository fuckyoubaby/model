package com.hylanda.model.common.controller;


import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hylanda.model.ModelApplication;
import com.hylanda.model.domain.Role;
import com.hylanda.model.domain.User;

@Controller
public class UserController {

	protected  Logger Logger = LoggerFactory.getLogger(ModelApplication.class);
	
	
	
	
	@RequestMapping("/login")
    public String login() {
        return "login";
    }
    @RequestMapping("/loginUser")
    public String loginUser(String username,String password,HttpSession session) {
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(username,password);
        Subject subject = SecurityUtils.getSubject();
        
        boolean isAdmin = false;  //是否是管理员
        
        try {
            subject.login(usernamePasswordToken);   //完成登录
            User user=(User) subject.getPrincipal();
            Set<Role> roles = user.getRoles();
            Iterator<Role> it = roles.iterator();  
            while (it.hasNext()) {  
              Role role = it.next(); 
              if (role.getRname().equals("admin")) {
				isAdmin = true;
              }
            }  
          
            
            
            session.setAttribute("user", user);
            
			if (isAdmin) {
				//return new ModelAndView("admin/index");
				return "admin/index";			
			}
			//return new ModelAndView("index");
            return "user/index";
        } catch(Exception e) {
        	//return new ModelAndView("login");
            return "login";//返回登录页面
        }
        
    }
    @RequestMapping("/logOut")
    public String logOut(HttpSession session) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
//        session.removeAttribute("user");
        return "login";
    }
}
