package com.jandar.demo.web;

import org.springframework.beans.factory.annotation.Autowired;

import com.jandar.demo.domain.Demo;
import com.jandar.demo.service.IDemoService;
import com.jandar.frame.base.action.BaseAction;

public class DemoAction extends BaseAction<Demo> {

	@Autowired(required = true)
	private IDemoService demoService;
	
	
	@Override
	public void prepare() throws Exception {
		
	}

	
	public String list(){
		
		return LIST;
	}
	
}
