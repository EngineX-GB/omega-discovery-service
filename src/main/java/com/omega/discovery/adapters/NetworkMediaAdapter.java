package com.omega.discovery.adapters;

import com.omega.discovery.dto.AdapterResponse;
import com.omega.discovery.dto.DiscoveryConfig;

public interface NetworkMediaAdapter {

	AdapterResponse findMedia (final String url, final DiscoveryConfig config);
	
}
