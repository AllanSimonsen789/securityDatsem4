package Controllers;

import Database.ForumMapper;
import Database.ReplyMapper;
import Exception.ForumException;
import ExtraClasses.SecureRandomString;
import model.Post;
import model.Reply;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

@WebServlet(name = "PostController")
public class PostController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("web_csrf_token", SecureRandomString.genSecureRandomString());
        try {
            String content = request.getParameter("content");
            int userid = Integer.parseInt(request.getParameter("userid"));
            int postid = Integer.parseInt(request.getParameter("postid"));
            System.out.println(userid + " " + postid);
            Reply newReply = new Reply(userid, postid, content);

            ReplyMapper rm = ReplyMapper.getInstance();
            newReply = rm.createReply(newReply);
            request.setAttribute("confirmation", "The reply was succesfully created with id: " + newReply.getReplyID());
            System.out.println(request.getRequestURL().toString());
            response.sendRedirect(request.getRequestURL().toString() + "?post=" + postid);
        } catch (ForumException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Something went wrong");
            request.getRequestDispatcher("/WEB-INF/post.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("web_csrf_token", SecureRandomString.genSecureRandomString());
        int postid = Integer.parseInt(request.getParameter("post"));
        ForumMapper fm = ForumMapper.getInstance();
        ReplyMapper rm = ReplyMapper.getInstance();
        request.setAttribute("post", fm.fetchPost(postid));
        request.setAttribute("replies", rm.fetchreplies(postid));

        request.getRequestDispatcher("/WEB-INF/post.jsp").forward(request, response);

    }
}