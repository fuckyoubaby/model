package com.hylanda.model.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 配置mybait使用的数据源,
 * @author shijinxiang
 * @date 2017年9月12日
 * @time 下午3:01:26
 */
@Configuration
public class DruidDataSourceConfiguration {

	@Bean
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource druidDataSource()
	{
		DruidDataSource druidDataSource = new DruidDataSource();
		return druidDataSource;
	}
}
