package com.hylanda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hylanda.entity.User;
import com.hylanda.service.UserService;
import com.hylanda.util.GetUUID;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	@RequestMapping(value = "user/save")
	public String saveUser()
	{
		User user = new User();
		//user.setId(1);
		user.setUserName("admin");
		user.setPassword("123");
		String uuid = GetUUID.getUUID();
		user.setId(uuid);
		userService.save(user);
		
		return "index";
	}

}
