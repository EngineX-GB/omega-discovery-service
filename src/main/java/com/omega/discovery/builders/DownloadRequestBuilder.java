package com.omega.discovery.builders;

import com.omega.discovery.dto.DownloadRequest;

public class DownloadRequestBuilder {

	private String url;
	
	private String outputFileName;
	
	private Integer startFileNumber;
	
	private Integer endFileNumber;
	
	private boolean isStream;
	
	private boolean resumeDownload;
	
	private String taskId;
	
	private String processingPlan;
	
	private boolean autoRestart;
	
	
	public DownloadRequestBuilder setUrl(String url) {
		this.url = url;
		return this;
	}

	public DownloadRequestBuilder setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
		return this;
	}

	public DownloadRequestBuilder setStartFileNumber(Integer startFileNumber) {
		this.startFileNumber = startFileNumber;
		return this;
	}

	public DownloadRequestBuilder setEndFileNumber(Integer endFileNumber) {
		this.endFileNumber = endFileNumber;
		return this;
	}

	public DownloadRequestBuilder setIsStream(boolean isStream) {
		this.isStream = isStream;
		return this;
	}

	public DownloadRequestBuilder setResumeDownload(boolean resumeDownload) {
		this.resumeDownload = resumeDownload;
		return this;
	}


	public DownloadRequestBuilder setProcessingPlan(String processingPlan) {
		this.processingPlan = processingPlan;
		return this;
	}

	public DownloadRequestBuilder setAutoRestart(boolean autoRestart) {
		this.autoRestart = autoRestart;
		return this;
	}

	public DownloadRequest build() {
		final DownloadRequest request = new DownloadRequest();
		request.setUrl(this.url);
		request.setIsStream(this.isStream);
		request.setOutputFileName(this.outputFileName);
		request.setProcessingPlan(this.processingPlan);
		request.setResumeDownload(this.resumeDownload);
		request.setStartFileNumber(this.startFileNumber);
		request.setEndFileNumber(this.endFileNumber);
		request.setTaskId("");
		request.setAutoRestart(this.autoRestart);
		return request;
	}

	
	
}
