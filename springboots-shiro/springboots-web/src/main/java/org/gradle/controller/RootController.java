package org.gradle.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class RootController {
	
	@RequestMapping({"/","/index"})
	public String  index(HttpServletRequest request){
		System.out.println(request.getSession().getId());
		return "index";
	}
}
