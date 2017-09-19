package com.hylanda.model.common.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hylanda.model.common.mapper.UserMapper;
import com.hylanda.model.common.service.UserService;
import com.hylanda.model.domain.Role;
import com.hylanda.model.domain.User;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserMapper userMapper;
	@Override
	public User findUserByuserName(String username) {
		// TODO Auto-generated method stub
		
		User user = new User();
		user.setPassword("123");
		user.setUid(1);
		user.setUsername("admin");
		
		Set<Role> roles = new HashSet<Role>();
		Role role = new Role();
		role.setRid(1);
		role.setRname("管理員");
		
		user.setRoles(roles);
		
		return userMapper.findByUserName(username); //user;//
	}

}
