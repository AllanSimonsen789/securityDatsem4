package Controllers;

import Database.ForumMapper;
import ExtraClasses.SecureRandomString;
import ExtraClasses.SessionHelper;
import model.ForumPost;
import Exception.ForumException;
import model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@WebServlet(name = "ForumPostController")
public class ForumPostController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("username");
        if (!SecureRandomString.validateSecureString(request.getParameter("web_token"))) {
            session = request.getSession(false);
            session.invalidate();
            request.setAttribute("errorMessage", "Web tokens are NOT equal");
            request.setAttribute("web_csrf_token", SecureRandomString.genSecureRandomString());
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        } else {
            //Create new web_csrf_token.
            request.setAttribute("web_csrf_token", SecureRandomString.genSecureRandomString());
            //Renew session id.
            session.invalidate();
            session = request.getSession(true);
            session.setAttribute("username", sessionUser);
        }
        try {
            String title = request.getParameter("title").trim();
            String content = request.getParameter("content").trim();
            if(title.equals("") || content.equals("")){
                throw new ForumException("Please fill out the form.");
            }

            int userid = Math.toIntExact(sessionUser.getUserID());
            LocalDateTime created = LocalDateTime.now(ZoneId.of("Europe/Copenhagen"));

            ForumMapper fm = ForumMapper.getInstance();
            ForumPost forumPost = fm.createPost(new ForumPost(userid, title, content, created));
            request.setAttribute("confirmation", "The post was succesfully created with id: " + forumPost.getPostID());

            ArrayList<ForumPost> postlist = fm.fetchPosts();
            request.setAttribute("arraylen", postlist.size());
            request.setAttribute("postlist", postlist);
            request.getRequestDispatcher("/WEB-INF/forum.jsp").forward(request, response);
        } catch (ForumException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/forum.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Rotate session ID, with the same user.
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("username");

        if(sessionUser != null && SecureRandomString.validateSecureString(request.getParameter("web_token"))){
            SessionHelper.rotateSessionIDWithUser(request.getSession(), request, sessionUser);
        } else {
            //The user hasn't logged in, and can't comment or create a new forum post.
            SessionHelper.rotateSessionIDWithoutUser(session, request);
        }
        ForumMapper fm = ForumMapper.getInstance();
        ArrayList<ForumPost> postlist = fm.fetchPosts();
        request.setAttribute("arraylen", postlist.size());
        request.setAttribute("postlist", postlist);
        //Create new web_csrf_token.
        request.setAttribute("web_csrf_token", SecureRandomString.genSecureRandomString());
        request.getRequestDispatcher("/WEB-INF/forum.jsp").forward(request, response);
    }
}