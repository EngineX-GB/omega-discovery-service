package com.omega.discovery.util;

public class Util {

	private Util() {
		// private constructor
	}
	
	public static final String truncate(final String resourceTitle, final int truncateSize) {
		if (resourceTitle.length() > truncateSize) {
			return resourceTitle.replaceAll("[^a-zA-Z0-9\\-]", "-").substring(0, truncateSize);
		}
		return resourceTitle.replaceAll("[^a-zA-Z0-9\\-]", "-");
	}
	
	
}
