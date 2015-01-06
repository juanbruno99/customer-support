package com.tikcom.utils.session;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

/**
 * @author juanm
 * 
 * Class holds references for active obj sessions, since changes of those are event based this 
 * class is used in combination with SessionListeners
 * */
public final class SessionRegistry {
	
	private static final Map<String, HttpSession> SESSION = new Hashtable<String, HttpSession>();
	
	public static void addSession(HttpSession session) {
		SESSION.put(session.getId(), session);
	}
	
	public static void updateSession(HttpSession session, String oldSession) {
		
		synchronized(SESSION) {
			SESSION.remove(oldSession);
			SESSION.put(session.getId(), session);
		}
	}
	
	public static void removeSession(HttpSession session) {
		SESSION.remove(session.getId());
	}

	public static List<HttpSession> getAllSessions() {
		return new ArrayList<>(SESSION.values());
	}
	
	public static int getNumberOfSessions() {
		return SESSION.size();
	}
	
	//Static use of class make constructor private
	private SessionRegistry() {}
}
