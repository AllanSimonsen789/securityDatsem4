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

import Exception.ImageException;
import Exception.IllegalArgumentException;

@WebServlet(name = "ProfileController")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class ProfileController extends HttpServlet {
    private final ImageMapper im = new ImageMapper();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String returnString = "";
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("username");
        if (sessionUser != null) {
            try {
                // File content
                Part filePart = request.getPart("fileContent");
                if ((filePart == null) || (filePart.getInputStream().available() == 0)) {//.available, checks how much data is accsesible in the filePart.
                    throw new IllegalArgumentException("Unknown file content specified!");
                    //Send to error page.
                }
                UserMapper um = UserMapper.getInstance();
                returnString = im.uploadProfilePic(filePart);
                um.setProfilePic(sessionUser.getUserID(), returnString);
                sessionUser.setImageURL(returnString);
            } catch (IllegalArgumentException e) {
                request.setAttribute("errorMessage", e.getMessage());
            } catch (ImageException e) {
                request.setAttribute("errorMessage", e.getMessage());
            } catch (IOException e){
                request.setAttribute("errorMessage", "Something went wrong, try again later.");
            }
            request.setAttribute("user", sessionUser);
            request.getRequestDispatcher("/WEB-INF/profilePage.jsp").forward(request, response);
        }
        //Else set request dispatcher to login. There is no user in session.
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
