package Database;

import Exception.AuthenticationException;
import Exception.MySQLDuplicateEntryException;
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

    //Login user
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
            sql = "SELECT * FROM securityExam.posttabelwuser";
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
                LocalDateTime posttime = LocalDateTime.now();

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
}