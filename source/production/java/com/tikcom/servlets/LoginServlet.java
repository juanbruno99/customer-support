package com.tikcom.servlets;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tikcom.dictionaries.LoggingDictionary;
import com.tikcom.utils.url.ServletUrlPaths;

@WebServlet(
	name = "loginServlet",
	urlPatterns = "/login"
		)

public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = -2274968532182296118L;
	private static final Logger log = LogManager.getLogger();
	
	//serves for now as in memory rudimentary DBs
	private static final Map<String, String> userDatabse = new Hashtable<String, String>();
	
	static {
		userDatabse.put("juanm", "juanpass");
		userDatabse.put("carlosb", "carlospass");
		userDatabse.put("maurob", "mauropass");
		userDatabse.put("cristib", "cristipass");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		
		//Check for log out action and if so invalidate session and return to login (could also send to jsp)
		if(checkLogOutAction(session, req)) {
			resp.sendRedirect(ServletUrlPaths.LOGIN_PATH);
			return;
		}
		
		/**session already active for the user, if so send to tickets page (fall of the edge of method)
		 * if not send through response redirect to login jsp but setting loginFail false as it will never reach here
		 * if data is not correct - see doPost below
		*/
		if(!checkSessionStatus(session, resp)) {
		
		//else send to login jsp page (normal login process indicated by attribute set to false)
		req.setAttribute("loginFailed", false);
		req.getRequestDispatcher(ServletUrlPaths.LOGIN_JSP_PATH).forward(req, resp);
		}
	}
	
	private boolean checkLogOutAction(HttpSession session, HttpServletRequest req) {
		//if request contains logOut parameter invalidate session
		if(req.getParameter("logout")!=null) {
			session.invalidate();
			return true;
		}
			
		return false;
	}

	/**
	 * Call to jsp will head back here after form posting action in it
	 * */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)	throws IOException, ServletException {
		
		HttpSession session = req.getSession();
		
		//session already active for the user, and not first log in, send to tickets page
		if(checkSessionStatus(session, resp))
			return;
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		//check passed parameters and forward back to login page if missing or incorrect, with login fail attribute set up
		if(username == null || password == null ||  !LoginServlet.userDatabse.containsKey(username) ||
				!password.equals(LoginServlet.userDatabse.get(username))) {
				
				req.setAttribute("loginFailed", true);
				req.getRequestDispatcher(ServletUrlPaths.LOGIN_JSP_PATH).forward(req, resp);
				log.info(LoggingDictionary.FAILED_LOGIN, username);
		}
		else { //else send to tickets page and set additional security to prevent session fixation attack
				/**
				 * Attribue for session object set here
				 * */
				session.setAttribute("username", username);
				
				if(session.getId() != null)
					req.changeSessionId();
				
				resp.sendRedirect(ServletUrlPaths.TICKETS_PATH);
		}
	}
	
	//checks whether the session is still active or not
	private boolean checkSessionStatus(HttpSession session, HttpServletResponse resp) throws IOException  {
		if(session.getAttribute("username") != null) {
			resp.sendRedirect(ServletUrlPaths.TICKETS_PATH);
			return true;
		}
		
		return false;
	}
}
