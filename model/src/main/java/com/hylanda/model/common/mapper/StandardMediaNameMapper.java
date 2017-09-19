package com.hylanda.model.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hylanda.model.common.domain.StandardMediaName;

@Mapper
public interface StandardMediaNameMapper {

	List<StandardMediaName> findAll();
}
