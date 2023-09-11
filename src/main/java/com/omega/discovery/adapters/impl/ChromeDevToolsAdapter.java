package com.omega.discovery.adapters.impl;

import java.io.File;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.omega.discovery.dto.AdapterResponse;
import com.omega.discovery.dto.Result;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.network.Network;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.omega.discovery.adapters.NetworkMediaAdapter;
import com.omega.discovery.dto.DiscoveryConfig;

@Component
public class ChromeDevToolsAdapter implements NetworkMediaAdapter{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ChromeDevToolsAdapter.class);
	
	@Override
	public AdapterResponse findMedia(String url, DiscoveryConfig config) {
		
		LOGGER.info("Running chrome developer tools for URL : {}", url);
		final Set<String> detectedLinks = new HashSet<>();
		final Result result = new Result();
		System.setProperty("webdriver.chrome.driver", config.getChromeDriverPath());
		ChromeOptions options = new ChromeOptions();
		//options.addArguments("--headless");
		options.setBinary(new File(config.getChromeExecutablePath()));
		ChromeDriver chromeDriver = new ChromeDriver(options);
		
		DevTools chromeDevTools = chromeDriver.getDevTools();
		chromeDevTools.createSession();


		chromeDevTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        //set blocked URL patterns
//        chromeDevTools.send(Network.setBlockedURLs(ImmutableList.of("*.css", "*.png")));

        //add event listener to verify that css and png are blocked
        chromeDevTools.addListener(Network.requestWillBeSent(), responseReceived -> {
			if (responseReceived.getRequest().getUrl().contains("\\.ts")) {
				System.out.println("OUT>> TS FILE");
			}
        	if (config.getMappingEntry().getMatchType().equals("contains")) {
        		if (responseReceived.getRequest().getUrl().contains(config.getMappingEntry().getRegex())) {
        			detectedLinks.add(responseReceived.getRequest().getUrl());
            	}
        	}
        });



		chromeDriver.get(url);
		if (config.getMappingEntry().getXpathClick() != null) {
			chromeDriver.findElementByXPath(config.getMappingEntry().getXpathClick()).click();
		}
		if (config.getMappingEntry().getElementById() != null) {
			chromeDriver.findElementById(config.getMappingEntry().getElementById()).click();
		}

		chromeDevTools.close();
		chromeDriver.quit();
		LOGGER.info("Status: END");
		if (detectedLinks.size() > 0) {
			result.setUrlResult(detectedLinks.stream().findFirst().get());
		}
		LOGGER.info("Result = "+result.getUrlResult());
		String resourceTitle = null; //left as a null  for now
		AdapterResponse adapterResponse = new AdapterResponse();
		adapterResponse.setUrl(result.getUrlResult());
		adapterResponse.setResourceTitle(resourceTitle);
		return adapterResponse;
	}

}
