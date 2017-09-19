package com.hylanda.model.common.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.hylanda.model.domain.SysUserRole;

@Repository("sysUserRoleMapper")
@Mapper
public interface SysUserRoleMapper {

	@Select("SELECT * FROM sys_role_user where Sys_User_id=#{userid}")
	 @Results({
	        @Result(property = "id",  column = "id"),
	        @Result(property = "userid", column = "Sys_User_id"),
	        @Result(property = "roleid",  column = "Sys_Role_id")
	    })
	List<SysUserRole> selectByUserId(long userid);
	
	//List<SysUserRole> findRoleByUserId(long userid);
}
