package Controllers;

import Database.UserMapper;
import Exception.AuthenticationException;
import Exception.MySQLDuplicateEntryException;
import Exception.RegistrationException;
import ExtraClasses.RegistrationHelper;
import ExtraClasses.SecureRandomString;
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
@WebServlet(name = "UserController")
public class UserController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(SecureRandomString.validateSecureString(request.getParameter("web_token"))) {
            request.setAttribute("csrf_success_error", "Tokens are equal");
            request.setAttribute("web_csrf_token", SecureRandomString.genSecureRandomString());
        } else {
            request.setAttribute("csrf_success_error", "Tokens are NOT equal");
            request.setAttribute("web_csrf_token", SecureRandomString.genSecureRandomString());
        }

        switch (request.getRequestURI()){
            case "/login":
                //Lav switch - login, logout, register på cmd.
                UserMapper us = UserMapper.getInstance();
                try{
                    User user = us.Login(request.getParameter("loginname"), request.getParameter("password"));
                    String gRecaptchaResponse = request
                            .getParameter("g-recaptcha-response");
                    boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
                    if(user != null && verify) {
                        //Login sucsessfull. Username and hashed psw are correct.

                        // get current session
                        HttpSession session = request.getSession();
                        //The code below makes sure that we are given a new session id whenever we refresh - after our initial login.
                        //This needs to happen. Because it will ensure that no-one can steal a session id, and then pretend to be the logged in person.
                        // remove current session
                        session.invalidate();
                        session = request.getSession(true);
                        // generate a new session
                        session.setAttribute("username", user);

                        request.setAttribute("username", user.getUserName());
                        request.setAttribute("id", user.getUserID());
                        request.setAttribute("email", user.getEmail());
                        request.getRequestDispatcher("/WEB-INF/welcome.jsp").forward(request, response);
                    }else{
                        throw new AuthenticationException("SoMeThInG WeNt WrOnG :(");
                    }
                }catch (AuthenticationException e){
                    request.setAttribute("errorMessage", "Invalid login and password. Try again");
                    request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                }
                break;
            case "/register":
                try {
                    //Get form data
                    String userName = request.getParameter("loginname").trim();
                    //We trim to ensure that we won't have 2 usernames in the DB like; "Tobias", " Tobias".
                    String email = request.getParameter("email");
                    String password = request.getParameter("password");
                    String gRecaptchaResponse = request
                            .getParameter("g-recaptcha-response");
                    //Check re-capcha & Password strength & Email
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
                        //Return attributes
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
                break;
            case "/edit":
                //Put isn't viable through .jsp. Therfore we route it through the post.
                doPut(request, response);
                break;
        }


    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //Get form data
            String newUsername = request.getParameter("newUsername").trim();
            //We trim to ensure that we won't have 2 usernames in the DB like; "Tobias", " Tobias".
            String newEmail = request.getParameter("newEmail");
            String newPassword = request.getParameter("newPassword");
            String gRecaptchaResponse = request
                    .getParameter("g-recaptcha-response");
            //Check for fields that have been filled out correctly
            boolean emailCheck = true;
            boolean passwordCheck = true;
            if (newEmail != ""){
                emailCheck = RegistrationHelper.checkEmail(newEmail);
            }
            if (newPassword != ""){
                passwordCheck = RegistrationHelper.checkPassword(newPassword);
            }
            //Check re-capcha & Password strength & Email
            if (VerifyRecaptcha.verify(gRecaptchaResponse) && emailCheck && passwordCheck) {
                //Get logged-in/createdUser from session.
                UserMapper um = UserMapper.getInstance();
                HttpSession session = request.getSession();
                User sessionUser = (User) session.getAttribute("username");
                //Edit user in DB
                User editedUser = um.Edit(sessionUser.getUserID(), newUsername, newPassword, newEmail);
                //We need to add the old ID to the new session user.
                editedUser.setUserID(sessionUser.getUserID());
                if(editedUser.getEmail() == null){
                    editedUser.setEmail(sessionUser.getEmail());
                }
                if(editedUser.getUserName() == null){
                    editedUser.setUserName(sessionUser.getUserName());
                }
                //Reset user in session, replace with the edited user
                session.invalidate();
                session = request.getSession(true);
                session.setAttribute("username", editedUser);

                request.setAttribute("username", editedUser.getUserName());
                request.setAttribute("id", editedUser.getUserID());
                request.setAttribute("email", editedUser.getEmail());
                request.getRequestDispatcher("/WEB-INF/userEditedPage.jsp").forward(request, response);
            }
        } catch (MySQLDuplicateEntryException e) {
            request.setAttribute("errorMessage", "Something went wrong.");
            request.getRequestDispatcher("/WEB-INF/edit.jsp").forward(request, response);
        } catch (RegistrationException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/edit.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("web_csrf_token", SecureRandomString.genSecureRandomString());

        switch (request.getRequestURI()){
            case "/login":
                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                break;
            case "/logout":
                HttpSession session=request.getSession(false);
                session.invalidate();
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                break;
            case "/register":
                request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
                break;
            case "/edit":
                request.getRequestDispatcher("/WEB-INF/edit.jsp").forward(request, response);
                break;
        }
    }
}
