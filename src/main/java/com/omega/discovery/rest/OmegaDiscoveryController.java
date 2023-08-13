package com.omega.discovery.rest;

import com.omega.discovery.dto.DiscoveryRequest;
import com.omega.discovery.dto.DiscoveryResponse;
import com.omega.discovery.service.DiscoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/discovery")
@RestController
public class OmegaDiscoveryController {

	@Autowired
	private DiscoveryService discoveryService;
	
	
	@PostMapping("/submit")
	public DiscoveryResponse submitDiscoveryRequest(@RequestBody DiscoveryRequest request) {
		return discoveryService.submitDiscoveryRequest(request);
	}
	
	@GetMapping("/link")
	public DiscoveryResponse getDiscoveryLink(@RequestBody DiscoveryRequest request) {
		return discoveryService.getDiscoveryLink(request);
	}

}
