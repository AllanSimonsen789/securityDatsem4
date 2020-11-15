package Controllers;

import ExtraClasses.SecureRandomString;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "profileController")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class profileController extends HttpServlet {
    private String path = "C:\\deleteThisFromSecurity\\";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("file");
        String fileName = "tmpRandomGeneratedFileNameNeeded.PNG"; //filePart.getSubmittedFileName();
        for (Part part : request.getParts()) {
            part.write(path + fileName);
        }
        //request.getRequestDispatcher("upload.jsp").forward(request, response);

        HttpSession session = request.getSession();
//        request.setAttribute("imageFile", path + fileName);
        request.setAttribute("imageFile", "file:///C:/deleteThisFromSecurity/"+fileName);
        request.getRequestDispatcher("/WEB-INF/profilePage.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("web_csrf_token", SecureRandomString.genSecureRandomString());
        HttpSession session = request.getSession();
        if (session.getAttribute("username") != null) {
            User sessionUser = (User) session.getAttribute("username");
            request.setAttribute("user", sessionUser);
            request.getRequestDispatcher("/WEB-INF/profilePage.jsp").forward(request, response);
        } else {
            //Not logged in.
            response.sendRedirect("/login");
        }

    }
}
