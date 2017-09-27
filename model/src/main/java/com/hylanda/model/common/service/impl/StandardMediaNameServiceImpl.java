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
	@Override
	public int insert(StandardMediaName standardMediaName) {
		// TODO Auto-generated method stub
		return standardMediaNameMapper.insert(standardMediaName);
	}
	@Override
	public int update(StandardMediaName standardMediaName) {
		// TODO Auto-generated method stub
		return standardMediaNameMapper.update(standardMediaName);
	}
	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		return standardMediaNameMapper.delete(id);
	}

}
