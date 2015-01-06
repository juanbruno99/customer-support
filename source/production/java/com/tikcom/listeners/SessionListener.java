package com.tikcom.listeners;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionIdListener;
import javax.servlet.http.HttpSessionListener;

import com.tikcom.utils.session.SessionRegistry;

/**
 * @author juanm
 * 
 * Class is a listener for session events and logging of them
 * */


//with this annotation class is registered as event aware thus calling respective methods automatically by container (server)
@WebListener
public class SessionListener implements HttpSessionListener, HttpSessionIdListener {

	private SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
	
	@Override
	public void sessionIdChanged(HttpSessionEvent event, String oldSession) {
		//to replace with Log framework, for now console logging
		System.out.println(this.date() + " :Session ID" + oldSession + " changed to" + event.getSession().getId());
		SessionRegistry.updateSession(event.getSession(), oldSession);
	}

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		//to replace with Log framework, for now console logging
		System.out.println(this.date() + " :Session " + event.getSession().getId() + " created");
		SessionRegistry.addSession(event.getSession());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		//to replace with Log framework, for now console logging
		System.out.println(this.date() + " :Session " + event.getSession().getId() + " destroyed");
		SessionRegistry.removeSession(event.getSession());
	}
	
	private String date() {
		return this.formatter.format(new Date());
	}

}
