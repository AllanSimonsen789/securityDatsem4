package Controllers;

import Database.UserMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import Exception.AuthenticationException;
import model.User;


@WebServlet(name = "LoginController")
public class LoginController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String cmd = request.getParameter("cmd");

        //Lav switch - login, logout, register på cmd.
        UserMapper us = new UserMapper();

        try{
            User user = us.Login(request.getParameter("loginname"), request.getParameter("password"));
            String gRecaptchaResponse = request
                    .getParameter("g-recaptcha-response");
            boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);

            if(user != null && verify) {
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

        /*System.out.println("Hellooooo WORLD!" + request.getParameter("username") + " " + request.getParameter("password"));
        if(SecureRandomString.validateSecureString(request.getParameter("web_token"))) {
            request.setAttribute("csrf_success_error", "Tokens are equal");
            request.setAttribute("web_csrf_token", SecureRandomString.genSecureRandomString());
        } else {
            request.setAttribute("csrf_success_error", "Tokens are NOT equal");
            request.setAttribute("web_csrf_token", SecureRandomString.genSecureRandomString());
        }


        if (request.getParameterMap().containsKey("username") && request.getParameterMap().containsKey("password")) {
            Connection conn = null;

            try {
                conn = new MySqlConnection().MySqlConnect();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            MySqlLogin login = new MySqlLogin(conn);
            boolean loginSuccessFull = false;

            String user = request.getParameter("username");
            String pass = request.getParameter("password");

            try {
                System.out.println(user + " " + pass);
                loginSuccessFull = login.verifyUser(user, pass);
            } catch (SQLException throwables) {
                throwables.printStackTrace();

            }

            if(loginSuccessFull) {

                // get current session
                HttpSession session = request.getSession();


                // remove current session
                session.invalidate();
                // generate a new session
                session = request.getSession(true);


                session.setAttribute("username", user);


                request.getRequestDispatcher("secret.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }

        }
    */
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //request.setAttribute("web_csrf_token", SecureRandomString.genSecureRandomString());
        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);

        if(request.getRequestURL().equals("/login")) {
            response.sendRedirect("index.jsp");
        } else if(request.getRequestURL().equals("/logout")) {
            HttpSession session=request.getSession(false);
            session.invalidate();
            response.sendRedirect("index.jsp");
        }


    }
}

