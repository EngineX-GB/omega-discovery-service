package com.omega.discovery.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MapperEntry {

	private String id;
	
	private String name;
	
	private String regex;
	
	private String adapter;
	
	private String matchType;

	private List<Step> steps;
	
	private boolean stream;
}
