package com.hylanda.model.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hylanda.model.common.domain.StandardMediaName;
import com.hylanda.model.common.mapper.StandardMediaNameMapper;
import com.hylanda.model.common.service.StandardMediaNameService;

@Service
public class StandardMediaNameServiceImpl implements StandardMediaNameService{

	@Autowired
	private StandardMediaNameMapper standardMediaNameMapper;
	@Override
	public List<StandardMediaName> findAll() {
		// TODO Auto-generated method stub
		return standardMediaNameMapper.findAll();
	}

}
