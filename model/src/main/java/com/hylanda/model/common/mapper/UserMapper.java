package com.hylanda.model.common.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.hylanda.model.domain.User;

@Mapper
public interface UserMapper {

	public User findByUserName(String username);
}
