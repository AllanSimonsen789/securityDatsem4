package Controllers;

import ExtraClasses.SecureRandomString;
import ExtraClasses.SessionHelper;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AdminController")
public class AdminController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("web_csrf_token", SecureRandomString.genSecureRandomString());
        //Rotate session ID, with the same user.
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("username");

        if(sessionUser != null){
            SessionHelper.rotateSessionIDWithUser(session, request, sessionUser);
            if (sessionUser.getRole().equals("admin")) {
                request.getRequestDispatcher("/WEB-INF/secretAdmin.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Admin panel access, requires admin account");
                //Auto logout.
                session = request.getSession(false);
                session.invalidate();
                request.getRequestDispatcher("/login").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", "Admin panel access, requires a login");
            request.getRequestDispatcher("/login").forward(request, response);
        }
    }
}
