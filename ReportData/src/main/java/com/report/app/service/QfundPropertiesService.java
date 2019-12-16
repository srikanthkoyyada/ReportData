package com.report.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.report.app.entity.QfundProperties;
import com.report.app.repository.QfundPropertiesRepository;

@Service
public class QfundPropertiesService {

	@Autowired
	private QfundPropertiesRepository propertiesRepository;
	
	@Cacheable("qfundProperties")
	public Map<String, String> getQfundPropertiesMap(){
		return getQfundProperties();
	}
	
	
	
	@CachePut("qfundProperties")
	public Map<String, String> updateQfundPropertiesCache(){
		return getQfundProperties();
	}
	
	private Map<String, String> getQfundProperties(){
		Map<String, String> map=new HashMap<>();
		List<QfundProperties> qfundProperties=propertiesRepository.findAll();
		qfundProperties.forEach(prop ->{
			map.put(prop.getPropertyName(), prop.getPropertyValue());
		});
		return map;
	}
	
}
