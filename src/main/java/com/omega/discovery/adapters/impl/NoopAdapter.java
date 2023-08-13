package com.omega.discovery.adapters.impl;

import com.omega.discovery.adapters.NetworkMediaAdapter;
import com.omega.discovery.dto.AdapterResponse;
import com.omega.discovery.dto.DiscoveryConfig;

public class NoopAdapter implements NetworkMediaAdapter {

	private static final String MOCK_URL = "http://mock";
	
	private static final String MOCK_RESOURCE_TITLE = "mock-title";
	
	@Override
	public AdapterResponse findMedia(String url, DiscoveryConfig config) {
		return new AdapterResponse(MOCK_URL, MOCK_RESOURCE_TITLE);
	}

}
