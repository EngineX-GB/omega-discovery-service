package com.omega.discovery.adapters.impl;

import java.io.File;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.omega.discovery.dto.AdapterResponse;
import com.omega.discovery.dto.Result;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.network.Network;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
		//options.addArguments("--headless"); //TODO: undo this
		//options.setBinary(new File(config.getChromeExecutablePath()));
		ChromeDriver chromeDriver = new ChromeDriver(options);
		
		DevTools chromeDevTools = chromeDriver.getDevTools();
		chromeDevTools.createSession();


		chromeDevTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        //set blocked URL patterns
//        chromeDevTools.send(Network.setBlockedURLs(ImmutableList.of("*.css", "*.png")));

        //add event listener to verify that css and png are blocked
//        chromeDevTools.addListener(Network.requestWillBeSent(), responseReceived -> {
//			if (responseReceived.getRequest().getUrl().contains("\\.ts")) {
//				System.out.println("OUT>> TS FILE");
//			}
//        	if (config.getMappingEntry().getMatchType().equals("contains")) {
//        		if (responseReceived.getRequest().getUrl().contains(config.getMappingEntry().getRegex())) {
//        			detectedLinks.add(responseReceived.getRequest().getUrl());
//            	}
//        	}
//        });



		chromeDriver.get(url);
		chromeDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(chromeDriver, 40);
//		if (config.getMappingEntry().getXpathClick() != null) {
//			chromeDriver.findElementByXPath(config.getMappingEntry().getXpathClick()).click();
//		}
//		if (config.getMappingEntry().getElementById() != null) {
//			chromeDriver.findElementById(config.getMappingEntry().getElementById()).click();
//		}

		wait.until(ExpectedConditions.visibilityOf(chromeDriver.findElementByXPath("/html/body/div[4]/div/div/button")));
		if (chromeDriver.findElementByXPath("/html/body/div[4]/div/div/button") != null) {
			chromeDriver.findElementByXPath("/html/body/div[4]/div/div/button").click();
		}

		wait.until(ExpectedConditions.visibilityOf(chromeDriver.findElementByXPath("/html/body/div[4]/div/button[1]")));
		if (chromeDriver.findElementByXPath("/html/body/div[4]/div/button[1]") != null) {
			chromeDriver.findElementByXPath("/html/body/div[4]/div/button[1]").click();
		}

		if (chromeDriver.findElementById("player") != null) {
			chromeDriver.findElementById("player").click();
		}

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
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
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
