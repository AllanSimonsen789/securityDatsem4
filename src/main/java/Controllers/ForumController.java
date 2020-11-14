package Controllers;

import Database.ForumMapper;
import Database.UserMapper;
import ExtraClasses.SecureRandomString;
import ExtraClasses.VerifyRecaptcha;
import model.Post;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "ForumController")
public class ForumController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("web_csrf_token", SecureRandomString.genSecureRandomString());
        ForumMapper fm = ForumMapper.getInstance();
        ArrayList<Post> postlist = fm.fetchPosts();
        request.setAttribute("arraylen", postlist.size());
        request.setAttribute("postlist", postlist);
        request.getRequestDispatcher("/WEB-INF/forum.jsp").forward(request, response);
        /*if(request.getRequestURL().equals("/login")) {
            response.sendRedirect("index.jsp");
        } else if(request.getRequestURL().equals("/logout")) {
            HttpSession session=request.getSession(false);
            session.invalidate();
            response.sendRedirect("index.jsp");
        }*/
    }
}