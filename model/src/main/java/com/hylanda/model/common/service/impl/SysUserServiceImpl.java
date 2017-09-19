package com.hylanda.model.common.service.impl;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hylanda.model.common.mapper.SysRoleMapper;
import com.hylanda.model.common.mapper.SysUserMapper;
import com.hylanda.model.common.mapper.SysUserRoleMapper;
import com.hylanda.model.common.service.SysUserService;
import com.hylanda.model.domain.SysRole;
import com.hylanda.model.domain.SysUser;
import com.hylanda.model.domain.SysUserRole;

@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService{

	@Autowired 
	private SysUserMapper sysUserMapper;
	@Autowired
	private SysRoleMapper sysRoleMapper;
	@Autowired 
	private SysUserRoleMapper sysUserRoleMapper;
	@Override
	public SysUser selectByUserName(String username) {
		
		return sysUserMapper.selectByUserName(username);
	}
	@Override
	public SysUser selectRoleInfoByUserId(String username) {
		SysUser sysUser = sysUserMapper.selectByUserName(username);
		List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectByUserId(sysUser.getId());
		Set<SysRole> set = new HashSet<SysRole>();
		for (int i = 0; i < sysUserRoles.size(); i++) {
			System.out.println(sysUserRoles.get(i).getRoleid());
			SysRole sysRole = sysRoleMapper.selectByRoleId(sysUserRoles.get(i).getRoleid());
			set.add(sysRole);
			//SysRole sysRole = sysRoleMapper.selectByRoleId(sysUserRoles.get(i).getRoleid());
			//set.add(sysRole);
		}
		sysUser.setSysRoles(set);
		return sysUser;
	}

}
