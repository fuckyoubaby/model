package com.hylanda.model.common.service;

import com.hylanda.model.domain.SysUser;

public interface SysUserService {

	
	SysUser selectByUserName(String username);
	SysUser selectRoleInfoByUserId(String username);
}
