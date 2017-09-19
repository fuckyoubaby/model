package com.hylanda.model.common.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.hylanda.model.domain.SysRole;


@Repository("sysRoleMapper")
@Mapper
public interface SysRoleMapper {

	@Select("SELECT * FROM sys_role where id=#{id}")
	@ResultType(SysRole.class)
	SysRole selectByRoleId(long id);
	
	//SysRole findRoleById(long id);
}
