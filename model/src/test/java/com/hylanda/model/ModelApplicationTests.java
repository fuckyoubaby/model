package com.hylanda.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hylanda.model.common.domain.StandardMediaName;
import com.hylanda.model.common.mapper.StandardMediaNameMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ModelApplicationTests {

	@Autowired
	StandardMediaNameMapper standardMediaNameMapper;
	@Test
	public void contextLoads() {
		StandardMediaName standardMediaName = new StandardMediaName();
		standardMediaName.setArea("38");
		standardMediaName.setAreaMediaName("测试网站2");
		standardMediaName.setIsRank(0);
		standardMediaName.setMediaName("测试网站2");
		standardMediaName.setSiteLevel("2");
		standardMediaName.setSityType("2");
		standardMediaName.setUrl("ceshi.com");
		//int id = standardMediaNameMapper.insert(standardMediaName);
		Long mediaid = 18344l;
		standardMediaName.setStandardId(mediaid);
		//standardMediaNameMapper.update(standardMediaName);
		standardMediaNameMapper.delete(mediaid);
	}

}
