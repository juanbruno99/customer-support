package com.tikcom.utils.url;

import javax.servlet.http.HttpServletResponse;

import com.tikcom.dictionaries.HttpContentTypes;

public class ServletUtils {

	public static void setDefaultResponseContent(HttpServletResponse response) {
		//setting response content type and encoding
		response.setContentType(HttpContentTypes.TEXT_HTML.getTypeValue());
		response.setCharacterEncoding(HttpContentTypes.ENCODING_UTF.getTypeValue());
	}
	
}
