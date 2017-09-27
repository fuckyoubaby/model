package com.hylanda.model.common.service;

import java.util.List;

import com.hylanda.model.common.domain.StandardMediaName;

public interface StandardMediaNameService {

	List<StandardMediaName> findAll();
	
	int insert(StandardMediaName standardMediaName);
	int update(StandardMediaName standardMediaName);
	int delete(Long id);
}
