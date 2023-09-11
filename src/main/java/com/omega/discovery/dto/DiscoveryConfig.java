package com.omega.discovery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DiscoveryConfig {

	private String chromeDriverPath;

	private String chromeExecutablePath;
	
	private MapperEntry mappingEntry;
	
}
