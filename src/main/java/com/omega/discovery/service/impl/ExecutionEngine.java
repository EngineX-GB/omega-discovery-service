package com.omega.discovery.service.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.omega.discovery.dto.DiscoveryRequest;
import com.omega.discovery.dto.Mapper;
import com.omega.discovery.dto.MapperEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.omega.discovery.dto.DiscoveryConfig;
import com.omega.discovery.service.MappingService;

@Component
public class ExecutionEngine {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionEngine.class);
	
	private ExecutorService executorService;

	@Autowired
	private MappingService mappingService;
	
	@Value("${chromedriver.path}")
	private String chromeDriverPath;

	@Value("${chrome.executable.path}")
	private String chromeExecutablePath;

	
	@PostConstruct
	public void init() {
		executorService = Executors.newFixedThreadPool(1);
	}
	
	public void execute(final DiscoveryRequest request) {
		final MapperEntry selectedMapperEntry = getSelectedMapperEntry(mappingService.getMapper(), request.getAdapterName());
		executorService.submit(new DiscoveryExecutableTask(request, new DiscoveryConfig(chromeDriverPath, chromeExecutablePath, selectedMapperEntry)));
	}
	
	public String executeDiscoveryAndRetrieveLink(final DiscoveryRequest request) {
		final MapperEntry selectedMapperEntry = getSelectedMapperEntry(mappingService.getMapper(), request.getAdapterName());
		return new DiscoveryExecutableTask(request, 
				new DiscoveryConfig(chromeDriverPath, chromeExecutablePath, selectedMapperEntry)).retrieveLink();
	}
	
	@PreDestroy
	public void destroy() {
		executorService.shutdown();
		if (executorService.isShutdown()) {
			LOGGER.info("ExecutorService has shutdown");
		}
	}
	
	
	private MapperEntry getSelectedMapperEntry(final Mapper mapper, final String mappingName) {
		for (final MapperEntry mapperEntry : mapper.getEntries()) {
			if (mapperEntry.getName().equals(mappingName)) {
				return mapperEntry;
			}
		}
		return null;
	}
	
}
