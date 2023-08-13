package com.omega.discovery.service.impl;

import java.time.LocalDateTime;

import com.omega.discovery.dto.DiscoveryRequest;
import com.omega.discovery.dto.DiscoveryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.omega.discovery.service.DiscoveryService;

@Service
public class DiscoveryServiceImpl implements DiscoveryService{

	@Autowired
	private ExecutionEngine executionEngine;
	
	@Override
	public DiscoveryResponse submitDiscoveryRequest(DiscoveryRequest request) {
		executionEngine.execute(request);
		final DiscoveryResponse response = new DiscoveryResponse();
		response.setUrl(request.getUrl());
		response.setTimestamp(LocalDateTime.now());
		return response;
	}

	@Override
	public DiscoveryResponse getDiscoveryLink(DiscoveryRequest request) {
		final String discoveryLink = executionEngine.executeDiscoveryAndRetrieveLink(request);
		final DiscoveryResponse response = new DiscoveryResponse();
		response.setUrl(request.getUrl());
		response.setTimestamp(LocalDateTime.now());
		response.setLink(discoveryLink);
		if(discoveryLink == null) {
			response.setStatus("NULL_LINK_RETURNED");
		}
		return response;
		
	}

	
	
}
