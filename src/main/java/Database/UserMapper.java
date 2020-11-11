package Database;

import model.User;
import Exception.AuthenticationException;
import Exception.MySQLDuplicateEntryException;

import java.sql.*;

public class UserMapper {

    //Login user
    public User Login(String username, String password) throws AuthenticationException{
        User returnUser = new User();
        User sqlBuildUser = new User();
        PreparedStatement pStmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            //Create connection
            DBConnection dbConn = DBConnection.getInstance();
            conn = dbConn.getConnection();

            //Prepared statement
            String sql;
            sql = "SELECT id, userName, password, email " +
                    "FROM userstable " +
                    "WHERE userName LIKE?";
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

                //Build the user from SQL data
                sqlBuildUser.setEmail(sqlEmail);
                sqlBuildUser.setUserID(id);
                sqlBuildUser.setUserName(sqlUserN);

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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return returnUser;
    }

    //Register user
    public static User Register(String userName, String password, String email) throws MySQLDuplicateEntryException {
        User user = new User(userName, password, email);
        PreparedStatement pStmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            //Create connection
            DBConnection dbConn = DBConnection.getInstance();
            conn = dbConn.getConnection();

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
            pStmt.setDate(5, new java.sql.Date(user.getCreationDate().getTime()));
            pStmt.executeUpdate();

            //Ensure that the user was created and what ID they got from auto_increment in DB.
            rs = pStmt.getGeneratedKeys();
            if(rs != null && rs.next()){
                user.setUserID(rs.getInt(1));
            }
        } catch (SQLException ex) {
            throw new MySQLDuplicateEntryException("SQL server error, Duplicate entry");
        }finally {
            //finally block used to close resources
            try {
                rs.close();
                pStmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Finally failed to close connections");
            }
        }
        return user;
    }

    //update user

}