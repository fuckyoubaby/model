package com.hylanda.model.common.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hylanda.model.common.domain.StandardMediaName;
import com.hylanda.model.common.service.StandardMediaNameService;

@Controller
public class StandardMediaNameController {

	@Autowired
	private StandardMediaNameService standardMediaNameService;
	
	@RequestMapping("/standardmedianame/findall")
	@ResponseBody
	@RequiresPermissions("update")
	public String findAll()
	{
		List<StandardMediaName> list = standardMediaNameService.findAll();
		return list.toString();
	}
	
}
