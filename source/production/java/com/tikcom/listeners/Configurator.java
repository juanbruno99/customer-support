package com.tikcom.listeners;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import com.tikcom.filters.AuthenticationFilter;
/**
 * This "Configurator" class is a weblistener that operates on the Servlet Context
 * basically used in conjunction with Filters, in this case for Authentication
 * @see AuthenticationFilter
 * 
 * Classes here are registered programmatically, though this wouldn't allow filter chain
 * 
 * @author juanm
 * @since 1/6/2015 18:20:00
 * */

@WebListener
public class Configurator implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		
		FilterRegistration.Dynamic registration = context.addFilter("authentication", new AuthenticationFilter());
		
		registration.setAsyncSupported(true);
		registration.addMappingForUrlPatterns(null, false, "/sessions", "/tickets");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {	
	}

	
}
