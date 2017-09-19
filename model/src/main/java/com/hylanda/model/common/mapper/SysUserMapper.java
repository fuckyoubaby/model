package com.hylanda.model.common.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.hylanda.model.domain.SysUser;

@Repository("sysUserMapper")
@Mapper
public interface SysUserMapper {

	@Select("SELECT * FROM sys_user where username=#{username}")
	@ResultType(SysUser.class)
	SysUser selectByUserName(String username);
}
