package com.hylanda.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hylanda.dao.UserDao;
import com.hylanda.entity.User;
import com.hylanda.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	public void save(User user) {
		userDao.save(user);
	}

	public List<User> findList() {
		// TODO Auto-generated method stub
		return userDao.findAll(User.class);
	}

}
