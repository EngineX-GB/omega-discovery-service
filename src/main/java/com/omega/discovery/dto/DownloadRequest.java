package com.omega.discovery.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DownloadRequest {

	private String url;
	
	private String outputFileName;
	
	private Integer startFileNumber;
	
	private Integer endFileNumber;
	
	private Boolean isStream;
	
	private Boolean resumeDownload;
	
	private String taskId;
	
	private String processingPlan;
	
	private Boolean autoRestart;
	
}
