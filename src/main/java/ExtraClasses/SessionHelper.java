package ExtraClasses;

import model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionHelper {
    public static void rotateSessionIDWithUser(HttpSession session, HttpServletRequest request, User userForSession){
        //The code below makes sure that we are given a new session id whenever we refresh - after our initial login.
        //This needs to happen. Because it will ensure that no-one can steal a session id, and then pretend to be the logged in person.
        // remove current session
        session.invalidate();
        // generate a new session
        session = request.getSession(true);
        // set loggedin user into session.
        session.setAttribute("username", userForSession);
    }

    public static void rotateSessionIDWithoutUser(HttpSession session, HttpServletRequest request){
        //The code below makes sure that we are given a new session id whenever we refresh - after our initial login.
        //This needs to happen. Because it will ensure that no-one can steal a session id, and then pretend to be the logged in person.
        // remove current session
        session.invalidate();
        session = request.getSession(true);
    }
}
