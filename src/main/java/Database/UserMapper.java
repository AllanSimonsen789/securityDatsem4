package Database;

import Exception.AuthenticationException;
import Exception.MySQLDuplicateEntryException;
import model.Post;
import model.User;


import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;

/**
 * Created by IntelliJ IDEA.
 *   User: rando
 *   Date: 24/09/2020
 *   Time: 18:32
 *   To change this template use File | Settings | File Templates.
 */
public class UserMapper {

    private DBConnection DBC;
    private static UserMapper instance;

    public static UserMapper getInstance() throws IOException {
        if(instance == null){
            instance = new UserMapper();
        }
        return instance;
    }

    private UserMapper() throws IOException {
        DBC = DBConnection.getInstance();
    }

    //Login user
    public User Login(String username, String password) throws AuthenticationException{
        User returnUser = new User();
        User sqlBuildUser = new User();
        PreparedStatement pStmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            //Create connection
            conn = DBC.getConnection();

            //Prepared statement
            String sql;
            sql = "SELECT id, userName, password, email " +
                    "FROM userstable " +
                    "WHERE userName LIKE? " +
                    "LIMIT 1";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, username);
            rs = pStmt.executeQuery();

            //Extract data from resultset
            if (rs.next()){
                //Retrieve by column name
                long id = rs.getLong("id");
                String sqlUserN = rs.getString("userName");
                String sqlPass = rs.getString("password");
                String sqlEmail = rs.getString("email");
                LocalDateTime creationtime = LocalDateTime.of(1889,4,20,12,00);
                try {
                    creationtime = rs.getTimestamp("creationDate").toLocalDateTime();
                } catch ( Exception e) {
                    e.printStackTrace();
                }

                //Build the user from SQL data
                sqlBuildUser.setEmail(sqlEmail);
                sqlBuildUser.setUserID(id);
                sqlBuildUser.setUserName(sqlUserN);
                sqlBuildUser.setCreationDate(creationtime);

                //Validate Password
                if (!sqlBuildUser.verifyPassword(password, sqlPass)) {
                    throw new AuthenticationException("Invalid username or password");
                } else {
                    returnUser = sqlBuildUser;
                }
            } else {
                //If there isn't a rs.next(), then there wasn't found a user in the DB with the specified username.
                throw new AuthenticationException("Invalid username or password");
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
        return returnUser;
    }

    //Register user
    public User Register(String userName, String password, String email) throws MySQLDuplicateEntryException {
        User user = new User(userName, password, email);
        User returnUser = new User();
        PreparedStatement pStmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            //Create connection
            conn = DBC.getConnection();

            //Prepared statement
            String sql;
            sql = "INSERT INTO userstable (userName, password, email, role, creationDate) " +
                    "VALUES (?,?,?,?,?)";
            pStmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pStmt.setString(1, user.getUserName());
            pStmt.setString(2, user.getPassword());
            pStmt.setString(3, user.getEmail());
            //Hardcode that only users can be created through the system.
            pStmt.setString(4, user.getRole());
            pStmt.setTimestamp(5, Timestamp.valueOf(user.getCreationDate()));
            pStmt.executeUpdate();

            //Ensure that the user was created and what ID they got from auto_increment in DB.
            rs = pStmt.getGeneratedKeys();
            if(rs != null && rs.next()){
                returnUser.setUserID(rs.getInt(1));
                returnUser.setEmail(user.getEmail());
                returnUser.setUserName(user.getUserName());
                returnUser.setCreationDate(user.getCreationDate());

            }
        } catch (SQLException ex) {
            throw new MySQLDuplicateEntryException("SQL server error, Duplicate entry");
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
        return returnUser;
    }

    //update user

}