package com.vmzone.demo.utils;

import javax.servlet.http.HttpSession;

import com.vmzone.demo.exceptions.BadCredentialsException;
import com.vmzone.demo.exceptions.ResourceDoesntExistException;
import com.vmzone.demo.models.User;

public class SessionManager {
	
	private static final String USER = "user";

	public static boolean isUserLoggedIn(HttpSession session) {
		return session.getAttribute(USER) !=null;
	}
	
	public static boolean isAdmin(HttpSession session) {

		return ((User) session.getAttribute(USER)).isAdmin();
	}
	
	public static long getLoggedUserId(HttpSession session) throws ResourceDoesntExistException {
		if(!isUserLoggedIn(session)){
			throw new ResourceDoesntExistException("You are not logged in! You should log in first!");
		}
		return ((User) session.getAttribute(USER)).getUserId();
	}
	
	public static User getLoggedUser(HttpSession session) throws ResourceDoesntExistException {
		if(!isUserLoggedIn(session)){
			throw new ResourceDoesntExistException("You are not logged in! You should log in first!");
		}
		return ((User) session.getAttribute(USER));
	}

	public static void isAuthenticated(HttpSession session) throws BadCredentialsException {
		if(!isUserLoggedIn(session)){
			throw new BadCredentialsException("You are not logged in! You should log in first!");
		}
	}
}
