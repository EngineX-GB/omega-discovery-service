package com.omega.discovery.adapters.impl;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.omega.discovery.dto.AdapterResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.omega.discovery.adapters.NetworkMediaAdapter;
import com.omega.discovery.dto.DiscoveryConfig;

public class HtmlSourceCodeAdapter implements NetworkMediaAdapter{

	private static final Logger LOGGER = LoggerFactory.getLogger(HtmlSourceCodeAdapter.class);
	
	@Override
	public AdapterResponse findMedia(final String url, final DiscoveryConfig config) {
		AdapterResponse adapterResponse = null;
		try {
			Document document = Jsoup.connect(url).get();
			final String sourceText = document.text();
			if (config.getMappingEntry().getMatchType().equals("contains")) {
				Pattern pattern = Pattern.compile(config.getMappingEntry().getRegex());
				Matcher matcher = pattern.matcher(sourceText);
				while(matcher.find()) {
					adapterResponse = new AdapterResponse(matcher.group(0), UUID.randomUUID().toString());
				}
			}
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return adapterResponse;
	}

}
