package com.report.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DataPageViewController {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(DataPageViewController.class);

	@RequestMapping("/")
	public String home() {
		 LOGGER.debug("This is a debug message");
		 LOGGER.info("This is an info message");
		 LOGGER.warn("This is a warn message");
		 LOGGER.error("This is an error message");
		return "index";
	}

}
