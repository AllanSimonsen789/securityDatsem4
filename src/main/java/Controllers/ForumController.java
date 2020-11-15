package Controllers;

import Database.ForumMapper;
import ExtraClasses.SecureRandomString;
import model.Post;
import Exception.ForumException;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;

@WebServlet(name = "ForumController")
public class ForumController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("web_csrf_token", SecureRandomString.genSecureRandomString());
        try {
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            HttpSession session = request.getSession();
            User sessionUser = (User) session.getAttribute("username");
            int userid = (int) sessionUser.getUserID();
            LocalDateTime created = LocalDateTime.now(ZoneId.of("Europe/Copenhagen"));
            Post newPost = new Post(userid, title, content, created);

            ForumMapper fm = ForumMapper.getInstance();
            newPost = fm.createPost(newPost);
            request.setAttribute("confirmation", "The post was succesfully created with id: " + newPost.getPostID());

            //Renew session id.
            session.invalidate();
            session = request.getSession(true);
            session.setAttribute("username", sessionUser);

            //ForceReload the page
            response.sendRedirect(request.getRequestURL().toString());

        } catch (ForumException e) {
            request.setAttribute("errorMessage", "Something went wrong");
            request.getRequestDispatcher("/WEB-INF/forum.jsp").forward(request, response);
        }
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