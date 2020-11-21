package Controllers;

import Database.ForumMapper;
import ExtraClasses.SecureRandomString;
import ExtraClasses.SessionHelper;
import model.ForumPost;
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
import java.util.ArrayList;

@WebServlet(name = "ForumPostController")
public class ForumPostController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("web_csrf_token", SecureRandomString.genSecureRandomString());
        try {
            String title = request.getParameter("title").trim();
            String content = request.getParameter("content").trim();
            if(title.equals("") || content.equals("")){
                throw new ForumException("Please fill out the form.");
            }
            HttpSession session = request.getSession();
            User sessionUser = (User) session.getAttribute("username");
            int userid = (int) sessionUser.getUserID();
            LocalDateTime created = LocalDateTime.now(ZoneId.of("Europe/Copenhagen"));
            ForumPost newForumPost = new ForumPost(userid, title, content, created);

            ForumMapper fm = ForumMapper.getInstance();
            newForumPost = fm.createPost(newForumPost);
            request.setAttribute("confirmation", "The post was succesfully created with id: " + newForumPost.getPostID());

            //Renew session id.
            session.invalidate();
            session = request.getSession(true);
            session.setAttribute("username", sessionUser);

            //ForceReload the page
            response.sendRedirect(request.getRequestURL().toString());

        } catch (ForumException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/forum.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("web_csrf_token", SecureRandomString.genSecureRandomString());
        //Rotate session ID, with the same user.
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("username");

        if(sessionUser != null){
            SessionHelper.rotateSessionIDWithUser(request.getSession(), request, sessionUser);
        } else {
            //The user hasn't logged in, and can't comment or create a new forum post.
            SessionHelper.rotateSessionIDWithoutUser(session, request);
        }
        ForumMapper fm = ForumMapper.getInstance();
        ArrayList<ForumPost> postlist = fm.fetchPosts();
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