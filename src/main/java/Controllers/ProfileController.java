package Controllers;

import Database.ImageMapper;
import Database.UserMapper;
import ExtraClasses.SecureRandomString;
import ExtraClasses.SessionHelper;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

import Exception.ImageException;

@WebServlet(name = "ProfileController")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class ProfileController extends HttpServlet {
    //private final ImageMapper im = new ImageMapper();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doPost in profile controller.");
        String returnString = "";
        HttpSession session = request.getSession();
        try {
            User sessionUser = (User) session.getAttribute("username");
            UserMapper um = UserMapper.getInstance();
      //      returnString = im.uploadProfilePic((List<Part>) request.getParts());
            um.setProfilePic(sessionUser.getUserID(), returnString);
            //set the session users image url to the updated url.
        } catch (ImageException e) {
            e.printStackTrace();
        }
//        request.setAttribute("imageFile", path + fileName);
        System.out.println(returnString);
        request.setAttribute("imageFile", returnString);
        request.getRequestDispatcher("/WEB-INF/profilePage.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("web_csrf_token", SecureRandomString.genSecureRandomString());
        //Rotate session ID, with the same user.
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("username");

        if (sessionUser != null) {
            SessionHelper.rotateSessionIDWithUser(session, request, sessionUser);
            request.setAttribute("user", sessionUser);
            request.getRequestDispatcher("/WEB-INF/profilePage.jsp").forward(request, response);
        } else {
            //Not logged in.
            request.setAttribute("errorMessage", "Login to see profile page");
            response.sendRedirect("/login");
        }

    }
}
