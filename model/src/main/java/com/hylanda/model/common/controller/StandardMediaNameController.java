package com.hylanda.model.common.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hylanda.model.common.domain.StandardMediaName;
import com.hylanda.model.common.service.StandardMediaNameService;

@Controller
public class StandardMediaNameController {

	@Autowired
	private StandardMediaNameService standardMediaNameService;
	
	@RequestMapping("/standardmedianame/findall")
	@ResponseBody
	//@RequiresPermissions("update")
	public String findAll()
	{
		List<StandardMediaName> list = standardMediaNameService.findAll();
		return list.toString();
	}
	
	@RequestMapping("model/view/standard/query/{page}/{pageSize}")
	@ResponseBody
	public PageInfo<StandardMediaName> query(@PathVariable Integer page, @PathVariable Integer pageSize){
		if(page!= null && pageSize!= null){  
            PageHelper.startPage(page, pageSize);  
        }  
        List<StandardMediaName> standardMediaNames = standardMediaNameService.findAll();  
        return new PageInfo<StandardMediaName>(standardMediaNames); 
	}
	@RequestMapping("model/view/standard/insert")
	@ResponseBody
	public String insert(StandardMediaName standardMediaName)
	{
		int result = standardMediaNameService.insert(standardMediaName);
		return ""+result;
	}
	@RequestMapping("model/view/standard/update")
	@ResponseBody
	public String update(StandardMediaName standardMediaName)
	{
		int result = standardMediaNameService.update(standardMediaName);
		return ""+result;
	}
	@RequestMapping("model/view/standard/delete")
	@ResponseBody
	public String delete(Long id)
	{
		int result = standardMediaNameService.delete(id);
		return ""+result;
	}
	
}
