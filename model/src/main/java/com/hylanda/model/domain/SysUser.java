package com.hylanda.model.domain;

import java.util.HashSet;
import java.util.Set;


public class SysUser {

	private long id;
	private String username;
	private String password;
	
	//private Set<SysRole> sysRoles = new HashSet<SysRole>();
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	/*public Set<SysRole> getSysRoles() {
		return sysRoles;
	}
	public void setSysRoles(Set<SysRole> sysRoles) {
		this.sysRoles = sysRoles;
	}*/
	
	private Set<SysRole> sysRoles = new HashSet<SysRole>();
	
	
	
	public Set<SysRole> getSysRoles() {
		return sysRoles;
	}
	public void setSysRoles(Set<SysRole> sysRoles) {
		this.sysRoles = sysRoles;
	}
	@Override
	public String toString() {
		return "SysUser [id=" + id + ", username=" + username + ", password="
				+ password + ",  getId()=" + getId()
				+ ", getUsername()=" + getUsername() + ", getPassword()="
				+ getPassword() + ",  getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
	
	
}
