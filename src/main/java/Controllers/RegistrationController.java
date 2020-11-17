package Controllers;

import Database.UserMapper;
import Exception.MySQLDuplicateEntryException;
import Exception.RegistrationException;
import ExtraClasses.RegistrationHelper;
import ExtraClasses.SessionHelper;
import ExtraClasses.VerifyRecaptcha;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: rando
 * Date: 24/09/2020
 * Time: 12:31
 * To change this template use File | Settings | File Templates.
 */
@WebServlet(name = "RegistrationController")
public class RegistrationController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //Get form data
            String userName = request.getParameter("loginname").trim();
            //We trim to ensure that we won't have 2 usernames in the DB like; "Tobias", " Tobias".
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String gRecaptchaResponse = request
                    .getParameter("g-recaptcha-response");

            //Check re-capcha & Password strength
            if (VerifyRecaptcha.verify(gRecaptchaResponse)
                    && RegistrationHelper.checkPassword(password)
                    && RegistrationHelper.checkEmail(email)) {
                //Create user in DB
                UserMapper um = UserMapper.getInstance();
                User registeredUser = um.Register(userName, password, email);

                //auto login for the user, give session id.
                HttpSession session = request.getSession();
                session.invalidate();
                session = request.getSession(true);
                session.setAttribute("username", registeredUser);

                request.setAttribute("username", registeredUser.getUserName());
                request.setAttribute("id", registeredUser.getUserID());
                request.setAttribute("email", registeredUser.getEmail());
                request.setAttribute("created", registeredUser.getCreationDate());
                request.getRequestDispatcher("/WEB-INF/profilePage.jsp").forward(request, response);
            }
        } catch (MySQLDuplicateEntryException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        } catch (RegistrationException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.getRequestDispatcher("/register.jsp").forward(request, response); //Den her rammer /register og ikke /register.jsp
//Rotate session ID, with the same user.
//        HttpSession session = request.getSession();
//        User sessionUser = (User) session.getAttribute("username");
//        SessionHelper.rotateSessionID(request.getSession(), request, sessionUser);

        if (request.getRequestURI().equals("/register")) {
            request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        }
    }
}
