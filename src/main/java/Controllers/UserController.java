package Controllers;

import Database.UserMapper;
import Exception.AuthenticationException;
import Exception.MySQLDuplicateEntryException;
import Exception.RegistrationException;
import ExtraClasses.RegistrationHelper;
import ExtraClasses.SecureRandomString;
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
@WebServlet(name = "UserController")
public class UserController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (SecureRandomString.validateSecureString(request.getParameter("web_token"))) {
            request.setAttribute("csrf_success_error", "Tokens are equal");
            request.setAttribute("web_csrf_token", SecureRandomString.genSecureRandomString());
        } else {
            request.setAttribute("csrf_success_error", "Tokens are NOT equal");
            request.setAttribute("web_csrf_token", SecureRandomString.genSecureRandomString());
        }

        switch (request.getRequestURI()) {
            case "/login":
                //Lav switch - login, logout, register på cmd.
                UserMapper us = UserMapper.getInstance();
                try {
                    String inputEmail = request.getParameter("email");
                    request.setAttribute("email", inputEmail);
                    if (inputEmail.length() == 0 && !RegistrationHelper.checkEmail(inputEmail)) {
                        throw new RegistrationException("Email is not valid");
                    }
                    if (!VerifyRecaptcha.verify(request.getParameter("g-recaptcha-response"))) {
                        throw new RegistrationException("Recaptha not done");
                    }
                    User user = us.Login(inputEmail, request.getParameter("password"));
                    if (user != null) {
                        //Login sucsessfull. Username and hashed psw are correct.
                        SessionHelper.rotateSessionIDWithUser(request.getSession(), request, user);
                        response.sendRedirect("/profile");
                        //request.getRequestDispatcher("/WEB-INF/profilePage.jsp").forward(request, response);
                    } else {
                        throw new AuthenticationException("SoMeThInG WeNt WrOnG :(");
                    }
                } catch (AuthenticationException e) {
                    request.setAttribute("errorMessage", "Invalid login and password. Try again");
                    request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                } catch (RegistrationException e) {
                    request.setAttribute("errorMessage", e.getMessage());
                    request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                }
                break;
            case "/register":
                try {
                    //Get form data
                    String userName = request.getParameter("loginname").trim();
                    //We trim to ensure that we won't have 2 usernames in the DB like; "Tobias", " Tobias".
                    String email = request.getParameter("email");
                    request.setAttribute("loginname", request.getParameter("loginname"));
                    request.setAttribute("email", request.getParameter("email"));
                    String password = request.getParameter("password");
                    String passwordR = request.getParameter("password2");
                    String gRecaptchaResponse = request
                            .getParameter("g-recaptcha-response");
                    //Check re-capcha & Password strength & Email
                    if (password == null || passwordR == null || !password.equals(passwordR)) {
                        throw new RegistrationException("The passwords do not match");
                    }
                    if (!VerifyRecaptcha.verify(gRecaptchaResponse)) {
                        throw new RegistrationException("Recpathca not done correctly");
                    }
                    if (RegistrationHelper.checkPassword(password)
                            && RegistrationHelper.checkEmail(email)) {
                        //Create user in DB
                        UserMapper um = UserMapper.getInstance();
                        User registeredUser = um.Register(userName, password, email);
                        //auto login for the user, give session id.
                        SessionHelper.rotateSessionIDWithUser(request.getSession(), request, registeredUser);
                        //Return attributes
                        request.setAttribute("user", registeredUser);
                        request.setAttribute("created", "The user was created successfully!");
                        request.getRequestDispatcher("/WEB-INF/profilePage.jsp").forward(request, response);
                    } else {
                        throw new RegistrationException("Something went wrong");
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
            String newPasswordR = request.getParameter("newPassword2");
            request.setAttribute("loginname", request.getParameter("loginname"));
            request.setAttribute("email", request.getParameter("loginname"));
            String gRecaptchaResponse = request
                    .getParameter("g-recaptcha-response");
            //Check for fields that have been filled out correctly
            if (newUsername.length() == 0 && newPassword.length() == 0 && newEmail.length() == 0) {
                throw new RegistrationException("Nothing changed");
            }
            boolean emailCheck = true;
            boolean passwordCheck = true;
            if (newEmail != "") {
                emailCheck = RegistrationHelper.checkEmail(newEmail);
            }
            if (newPassword == null || newPasswordR == null || !newPassword.equals(newPasswordR)) {
                throw new RegistrationException("The passwords do not match");
            }
            //Check re-capcha & Password strength & Email
            if (!VerifyRecaptcha.verify(gRecaptchaResponse)) {
                throw new RegistrationException("Captacha nOT DonE");
            }
            if (emailCheck && passwordCheck) {
                //Get logged-in/createdUser from session.
                UserMapper um = UserMapper.getInstance();
                HttpSession session = request.getSession();
                User sessionUser = (User) session.getAttribute("username");
                //Edit user in DB
                User editedUser = um.Edit(sessionUser.getUserID(), newUsername, newPassword, newEmail);
                //We need to add the old ID to the new session user.
                editedUser.setUserID(sessionUser.getUserID());
                if (editedUser.getEmail() == null) {
                    editedUser.setEmail(sessionUser.getEmail());
                }
                if (editedUser.getUserName() == null) {
                    editedUser.setUserName(sessionUser.getUserName());
                }
                //Reset user in session, replace with the edited user
                SessionHelper.rotateSessionIDWithUser(session, request, editedUser);

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
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Something went wrong.");
            request.getRequestDispatcher("/WEB-INF/edit.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("web_csrf_token", SecureRandomString.genSecureRandomString());
        //Update the session id, but keep the same user.
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("username");

        switch (request.getRequestURI()) {
            case "/login":
                SessionHelper.rotateSessionIDWithoutUser(session, request);
                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                break;
            case "/logout":
                session = request.getSession(false);
                session.invalidate();
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                break;
            case "/register":
                SessionHelper.rotateSessionIDWithoutUser(session, request);
                request.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
                break;
            case "/edit":
                if (sessionUser != null) {
                    SessionHelper.rotateSessionIDWithUser(session, request, sessionUser);
                    request.getRequestDispatcher("/WEB-INF/edit.jsp").forward(request, response);
                } else {
                    response.setStatus(403);
                    request.getRequestDispatcher("/error.jsp").forward(request, response);
                }
                break;
        }
    }
}
