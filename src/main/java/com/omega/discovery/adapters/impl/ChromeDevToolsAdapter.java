package com.omega.discovery.adapters.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.omega.discovery.dto.*;
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
		options.addArguments("--mute-audio");
		if (!config.getChromeDriverDebugMode()) {
			options.addArguments("--headless");
		}
		if (config.getChromeExecutablePath() != null && Files.exists(Paths.get(config.getChromeExecutablePath()))) {
			options.setBinary(new File(config.getChromeExecutablePath()));
		}
		ChromeDriver chromeDriver = new ChromeDriver(options);
		DevTools chromeDevTools = chromeDriver.getDevTools();
		chromeDevTools.createSession();
		chromeDevTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		chromeDriver.get(url);
		chromeDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(chromeDriver, 40);

		chromeDevTools.addListener(Network.requestWillBeSent(), responseReceived -> {
			if (config.getMappingEntry().getMatchType().equals("contains")) {
				if (responseReceived.getRequest().getUrl().contains(config.getMappingEntry().getRegex())) {
					detectedLinks.add(responseReceived.getRequest().getUrl());
				}
			}
			if (config.getMappingEntry().getMatchType().equals("pattern")) {
				final Pattern pattern = Pattern.compile(config.getMappingEntry().getRegex());
				final Matcher matcher = pattern.matcher(responseReceived.getRequest().getUrl());
				System.out.println("OUT>>>> ANALYSE LINK : " + pattern.pattern() + ", url = " + responseReceived.getRequest().getUrl());
				if (matcher.find()) {
					detectedLinks.add(responseReceived.getRequest().getUrl());
				}
			}
		});

		final List<Step> steps = config.getMappingEntry().getSteps().stream().sorted().collect(Collectors.toList());

		for (final Step step : steps) {
			if (step.getElementType() == ElementType.XPATH) {
				wait.until(ExpectedConditions.visibilityOf(chromeDriver.findElementByXPath(step.getValue())));
				if (chromeDriver.findElementByXPath(step.getValue()) != null) {
					LOGGER.info("[Step] : Element {} click on {}", step.getElementType(), step.getValue());
					chromeDriver.findElementByXPath(step.getValue()).click();
				}
			} else if (step.getElementType() == ElementType.ID) {
				wait.until(ExpectedConditions.visibilityOf(chromeDriver.findElementById(step.getValue())));
				if (chromeDriver.findElementById(step.getValue()) != null) {
					LOGGER.info("[Step] : Element {} click on {}", step.getElementType(), step.getValue());
					chromeDriver.findElementById(step.getValue()).click();
				}
			} else if (step.getElementType() == ElementType.PAUSE) {
				try {
					LOGGER.info("[Step] : Sleeping for {} ms", step.getValue());
					Thread.sleep(Long.valueOf(step.getValue()));
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			} else if (step.getElementType() == ElementType.REFRESH) {
				LOGGER.info("[Step] : refresh");
				chromeDriver.navigate().refresh();
			}
			else {
				LOGGER.info("Unknown element type : {}", step.getElementType());
			}
		}


		try {
			Thread.sleep(10000);
		} catch(InterruptedException e) {
			e.printStackTrace();
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
