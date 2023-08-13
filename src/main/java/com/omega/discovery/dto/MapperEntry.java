package com.omega.discovery.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MapperEntry {

	private String id;
	
	private String name;
	
	private String regex;
	
	private String adapter;
	
	private String matchType;
	
	private String xpathClick;
	
	private String elementById;
	
	private boolean stream;
}
