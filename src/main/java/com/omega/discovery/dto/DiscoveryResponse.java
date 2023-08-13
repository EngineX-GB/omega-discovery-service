package com.omega.discovery.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class DiscoveryResponse {

	private String url;
	
	private LocalDateTime timestamp;
	
	private String link;
	
	private String status;
}
