package Database;

import Exception.ForumException;
import model.Post;
import model.Reply;
import model.User;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 *   User: ALlan
 *   Date: 24/09/2020
 *   Time: 18:32
 *   To change this template use File | Settings | File Templates.
 */
public class ReplyMapper {

    private DBConnection DBC;
    private static ReplyMapper instance;

    public static ReplyMapper getInstance() throws IOException {
        if(instance == null){
            instance = new ReplyMapper();
        }
        return instance;
    }

    public ReplyMapper() throws IOException {
        DBC = DBConnection.getInstance();
    }

    public ArrayList<Reply> fetchreplies(int id) {
        PreparedStatement pStmt = null;
        Connection conn = null;
        ResultSet rs = null;
        ArrayList<Reply> returnlist = new ArrayList<>();
        try {
            //Create connection
            conn = DBC.getConnection();

            //Prepared statement
            String sql;
            sql = "SELECT * FROM securityExam.replyuserview WHERE postid = ? ORDER BY id";
            pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, id);
            rs = pStmt.executeQuery();

            //Extract data from resultset
            while (rs.next()){
                //Retrieve by column name
                long replyid = rs.getLong("id");
                long userid = rs.getLong("userid");
                String username = rs.getString("username");
                String content = rs.getString("content");
                LocalDateTime posttime = LocalDateTime.of(1889,4,20,12,00);
                try {
                    posttime = rs.getTimestamp("replytime").toLocalDateTime();
                } catch ( Exception e) {
                    e.printStackTrace();
                }
                returnlist.add(new Reply(replyid, userid, username, new Long(id), content, posttime));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                rs.close();
                pStmt.close();
                conn.close();
            } catch (SQLException | NullPointerException e) {
                e.printStackTrace();
            }
        }

        return returnlist;
    }

    public Reply createReply(Reply reply) throws ForumException {
        Reply returnReply = null;
        PreparedStatement pStmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            //Create connection
            conn = DBC.getConnection();

            //Prepared statement
            String sql;
            sql = "INSERT INTO securityExam.replytable (content, replytime, userid, postid) VALUES (?,?,?,?)";
            pStmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pStmt.setString(1, reply.getContens());
            pStmt.setTimestamp(2, Timestamp.valueOf(reply.getCreationDate()));
            pStmt.setLong(3, reply.getUserID());
            pStmt.setLong(4, reply.getPostID());
            pStmt.executeUpdate();

            //Ensure that the user was created and what ID they got from auto_increment in DB.
            rs = pStmt.getGeneratedKeys();
            if(rs != null && rs.next()){
                returnReply = reply;
                returnReply.setReplyID(rs.getInt(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ForumException("Something went wrong");
        }finally {
            //finally block used to close resources
            try {
                if(rs != null){rs.close();}
                pStmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Finally failed to close connections");
            }
        }
        return returnReply;
    }
}