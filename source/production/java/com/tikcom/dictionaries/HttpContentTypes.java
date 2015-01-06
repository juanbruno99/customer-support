package com.tikcom.dictionaries;

public enum HttpContentTypes {
	
	TEXT_HTML("text/html"),
	ENCODING_UTF("UTF-8"),
	FILE("application/octet-stream");
	
	private String type;
	
	private HttpContentTypes(String type) {
		this.type = type;
	}
	
	public String getTypeValue() {
		return this.type;
	}

}
