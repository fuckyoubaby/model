/**
 * 
 */
package com.hylanda.system.domain.user;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author shijinxiang
 * 2017Äê7ÔÂ10ÈÕ
 */
public class Role implements GrantedAuthority{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String role;
	public Role(String role)
	{
		this.role = role;
	}
	
	public String getAuthority() {
		// TODO Auto-generated method stub
		return this.role;
	}

}
