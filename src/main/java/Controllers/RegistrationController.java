package Controllers;

import Database.UserMapper;
import Exception.MySQLDuplicateEntryException;
import Exception.RegistrationException;
import ExtraClasses.RegistrationHelper;
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
            String userName = request.getParameter("loginname");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String gRecaptchaResponse = request
                    .getParameter("g-recaptcha-response");

            //Check re-capcha & Password strength
            if (VerifyRecaptcha.verify(gRecaptchaResponse)
                    && RegistrationHelper.checkPassword(password)
                    && RegistrationHelper.checkEmail(email)) {
                //Create user in DB
                UserMapper um = new UserMapper();
                User registeredUser = um.Register(userName, password, email);

                //auto login for the user, give session id.
                HttpSession session = request.getSession();
                session.invalidate();
                session = request.getSession(true);
                session.setAttribute("username", registeredUser);

                request.setAttribute("username", registeredUser.getUserName());
                request.setAttribute("id", registeredUser.getUserID());
                request.setAttribute("email", registeredUser.getEmail());
                request.setAttribute("created", "The user was created successfully!");
                request.getRequestDispatcher("/WEB-INF/welcome.jsp").forward(request, response);
            }
        } catch (MySQLDuplicateEntryException e) {
            request.setAttribute("errorMessage", "This username allready exists");
            request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        } catch (RegistrationException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.getRequestDispatcher("/register.jsp").forward(request, response); //Den her rammer /register og ikke /register.jsp

        if (request.getRequestURI().equals("/register")) {
            request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        }
    }
}
