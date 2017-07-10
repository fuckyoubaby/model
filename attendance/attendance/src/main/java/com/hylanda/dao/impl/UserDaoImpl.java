package com.hylanda.dao.impl;


import org.springframework.stereotype.Repository;

import com.hylanda.base.dao.impl.BaseDaoImpl;
import com.hylanda.dao.UserDao;
import com.hylanda.entity.User;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{

}
