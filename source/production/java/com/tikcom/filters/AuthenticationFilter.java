package com.tikcom.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.tikcom.utils.url.ServletUrlPaths;

public class AuthenticationFilter implements Filter {

	private static final Logger log = (Logger) LogManager.getLogger(AuthenticationFilter.class);
			
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//Get the current session if any
		HttpSession session = ((HttpServletRequest)request).getSession();
		
		if(session != null && session.getAttribute("username") == null) {
			((HttpServletResponse)response).sendRedirect(ServletUrlPaths.LOGIN_PATH);
		} else {
			chain.doFilter(request, response);
		}
	} 

	@Override
	public void destroy() {
	}

}
