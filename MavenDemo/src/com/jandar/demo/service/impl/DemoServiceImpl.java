package com.jandar.demo.service.impl;

import org.springframework.stereotype.Component;

import com.jandar.demo.domain.Demo;
import com.jandar.demo.service.IDemoService;
import com.jandar.frame.base.service.BaseServiceImpl;

@Component(value = "demoService")
public class DemoServiceImpl extends BaseServiceImpl<Demo> implements IDemoService{

	
	public DemoServiceImpl() {
		super(Demo.class);
	}
}
