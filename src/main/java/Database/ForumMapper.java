package Database;

import Exception.MySQLDuplicateEntryException;
import Exception.ForumException;
import model.Post;
import model.User;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 *   User: ALlan
 *   Date: 24/09/2020
 *   Time: 18:32
 *   To change this template use File | Settings | File Templates.
 */
public class ForumMapper {

    private DBConnection DBC;
    private static ForumMapper instance;

    public static ForumMapper getInstance() throws IOException {
        if(instance == null){
            instance = new ForumMapper();
        }
        return instance;
    }

    public ForumMapper() throws IOException {
        DBC = DBConnection.getInstance();
    }

    public ArrayList<Post> fetchPosts() {
        User sqlBuildUser = new User();
        PreparedStatement pStmt = null;
        Connection conn = null;
        ResultSet rs = null;
        ArrayList<Post> returnlist = new ArrayList<>();
        try {
            //Create connection
            conn = DBC.getConnection();

            //Prepared statement
            String sql;
            sql = "SELECT * FROM securityExam.posttabelwuser ORDER BY id DESC";
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();

            //Extract data from resultset
            while (rs.next()){
                //Retrieve by column name
                long id = rs.getLong("id");
                long userid = rs.getLong("userid");
                String username = rs.getString("username");
                String title = rs.getString("title");
                String content = rs.getString("content");
                LocalDateTime posttime = LocalDateTime.of(1889,4,20,12,00);
                try {
                    posttime = rs.getTimestamp("posttime").toLocalDateTime();
                } catch ( Exception e) {
                    e.printStackTrace();
                }
                returnlist.add(new Post(id, userid, username, title, content, posttime));
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

    public Post createPost(Post post) throws ForumException {
        Post returnPost = null;
        PreparedStatement pStmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            //Create connection
            conn = DBC.getConnection();

            //Prepared statement
            String sql;
            sql = "INSERT INTO securityExam.posttable (userid, title, content, posttime) VALUES (?,?,?,?)";
            pStmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pStmt.setLong(1, post.getUserID());
            pStmt.setString(2, post.getPostTitle());
            pStmt.setString(3, post.getContens());
            pStmt.setTimestamp(4, Timestamp.valueOf(post.getCreationDate()));
            pStmt.executeUpdate();

            //Ensure that the user was created and what ID they got from auto_increment in DB.
            rs = pStmt.getGeneratedKeys();
            if(rs != null && rs.next()){
                returnPost = post;
                returnPost.setPostID(rs.getInt(1));
            }
        } catch (SQLException ex) {
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
        return returnPost;
    }
}