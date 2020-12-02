package Controllers;

import Database.ForumMapper;
import Database.ReplyMapper;
import Exception.ForumException;
import ExtraClasses.SecureRandomString;
import ExtraClasses.SessionHelper;
import model.Reply;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ReplyController")
public class ReplyController extends HttpServlet {
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
            String content = request.getParameter("content").trim();
            int userid = Math.toIntExact(sessionUser.getUserID());
            int postid = Integer.parseInt(request.getParameter("postid"));
            if(content.equals("")){
                throw new ForumException("Please write something in the reply.");
            }

            ReplyMapper rm = ReplyMapper.getInstance();
            Reply newReply = rm.createReply(new Reply(userid, postid, content));
            request.setAttribute("confirmation", "The reply was succesfully created with id: " + newReply.getReplyID());

            ForumMapper fm = ForumMapper.getInstance();
            request.setAttribute("post", fm.fetchPost(postid));
            request.setAttribute("replies", rm.fetchreplies(postid));

            request.getRequestDispatcher("/WEB-INF/post.jsp").forward(request, response);
        } catch (ForumException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/post.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Rotate session ID, with the same user.
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("username");

        if(sessionUser != null  && SecureRandomString.validateSecureString(request.getParameter("web_token"))){
            SessionHelper.rotateSessionIDWithUser(session, request, sessionUser);
        } else {
            //The user hasn't logged in, and can't comment or create a new forum post.
            SessionHelper.rotateSessionIDWithoutUser(session, request);
        }
        int postid = Integer.parseInt(request.getParameter("postID"));
        ForumMapper fm = ForumMapper.getInstance();
        ReplyMapper rm = ReplyMapper.getInstance();
        request.setAttribute("post", fm.fetchPost(postid));
        request.setAttribute("replies", rm.fetchreplies(postid));
        //Create new web_csrf_token.
        request.setAttribute("web_csrf_token", SecureRandomString.genSecureRandomString());
        request.getRequestDispatcher("/WEB-INF/post.jsp").forward(request, response);
    }
}