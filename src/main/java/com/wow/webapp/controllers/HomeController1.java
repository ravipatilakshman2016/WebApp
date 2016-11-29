package com.wow.webapp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController1 {
	private static final Logger logger = LoggerFactory.getLogger(HomeController1.class);
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView clinics(){
		logger.debug("/index get start");
		ModelAndView mv = new ModelAndView("index");
		return mv;
	}
}

