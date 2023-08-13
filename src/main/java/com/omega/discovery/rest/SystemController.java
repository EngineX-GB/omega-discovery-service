package com.omega.discovery.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/discovery/system")
public class SystemController {

	@RequestMapping("/ping")
	public boolean ping() {
		return true;
	}
	
}
