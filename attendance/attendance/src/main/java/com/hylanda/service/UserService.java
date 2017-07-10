package com.hylanda.service;

import java.util.List;

import com.hylanda.entity.User;

public interface UserService {
	
	void save(User user);
	List<User> findList();

}
