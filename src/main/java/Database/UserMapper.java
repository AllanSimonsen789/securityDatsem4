package Database;

import Exception.AuthenticationException;
import Exception.MySQLDuplicateEntryException;
import ExtraClasses.CryptoMngr;
import model.User;


import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Properties;

import Exception.ImageException;

/**
 * Created by IntelliJ IDEA.
 * User: rando
 * Date: 24/09/2020
 * Time: 18:32
 * To change this template use File | Settings | File Templates.
 */
public class UserMapper {

    private DBConnection DBC;
    private static UserMapper instance;
    private final SecretKey KEY;
    private final SecretKey IV;


    public static UserMapper getInstance() throws IOException {
        if (instance == null) {
            instance = new UserMapper();
        }
        return instance;
    }

    private UserMapper() throws IOException {
        DBC = DBConnection.getInstance();
        InputStream prob = null;
        prob = DBConnection.class.getResourceAsStream("/crypto.properties");
        Properties pros = new Properties();
        pros.load(prob);
        //assign parameters
        KEY = new SecretKeySpec(pros.getProperty("key").getBytes(), CryptoMngr.ALGORITHM);
        IV = new SecretKeySpec(pros.getProperty("IV").getBytes(), CryptoMngr.ALGORITHM);

    }

    //Login user
    public User Login(String email, String password) throws AuthenticationException {
        User returnUser = new User();
        User sqlBuildUser = new User();
        PreparedStatement pStmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            //Create connection
            conn = DBC.getConnection();
            String encryptedEmail = Base64.getEncoder().encodeToString(CryptoMngr.encrypt(KEY.getEncoded(), IV.getEncoded(), email.getBytes()));
            //Prepared statement
            String sql;
            sql = "SELECT * " +
                    "FROM userstable " +
                    "WHERE email LIKE ? " +
                    "LIMIT 1";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, encryptedEmail);
            rs = pStmt.executeQuery();

            //Extract data from resultset
            if (rs.next()) {
                //Retrieve by column name
                long id = rs.getLong("id");
                String sqlUserN = rs.getString("userName");
                String sqlPass = rs.getString("password");
                String sqlEmail = new String(CryptoMngr.decrypt(KEY.getEncoded(), IV.getEncoded(), Base64.getDecoder().decode(rs.getString("email").getBytes())));
                String sqlRole = rs.getString("role");
                String sqlUserImg = rs.getString("imageURL");
                LocalDateTime creationtime = LocalDateTime.of(1889, 4, 20, 12, 00);
                try {
                    creationtime = rs.getTimestamp("creationDate").toLocalDateTime();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //Build the user from SQL data
                sqlBuildUser.setEmail(sqlEmail);
                sqlBuildUser.setUserID(id);
                sqlBuildUser.setUserName(sqlUserN);
                sqlBuildUser.setRole(sqlRole);
                sqlBuildUser.setCreationDate(creationtime);
                sqlBuildUser.setImageURL(sqlUserImg);

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
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
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
            sql = "INSERT INTO userstable (userName, password, email, role, creationDate, imageURL) " +
                    "VALUES (?,?,?,?,?,?)";
            pStmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pStmt.setString(1, user.getUserName());
            pStmt.setString(2, user.getPassword());
            user.setEmail(Base64.getEncoder().encodeToString(CryptoMngr.encrypt(KEY.getEncoded(), IV.getEncoded(), user.getEmail().getBytes())));
            pStmt.setString(3, user.getEmail());
            //Hardcode that only users can be created through the system.
            pStmt.setString(4, user.getRole());
            pStmt.setTimestamp(5, Timestamp.valueOf(user.getCreationDate()));
            pStmt.setString(6, user.getImageURL());
            pStmt.executeUpdate();

            //Ensure that the user was created and what ID they got from auto_increment in DB.
            rs = pStmt.getGeneratedKeys();
            if (rs != null && rs.next()) {
                returnUser.setUserID(rs.getInt(1));
                returnUser.setEmail(email);
                returnUser.setUserName(user.getUserName());
                returnUser.setRole(user.getRole());
                returnUser.setCreationDate(user.getCreationDate());
                returnUser.setImageURL(user.getImageURL());
            }
        } catch (SQLException ex) {
            throw new MySQLDuplicateEntryException("Username or Email already in use");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (rs != null) {
                    rs.close();
                }
                pStmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Finally failed to close connections");
            }
        }
        return returnUser;
    }

    //Update user
    public User Edit(long userID, String newUsername, String newPassword, String newEmail) throws Exception {
        User returnUser = new User();
        User updateUser = new User();
        ArrayList<String> newData = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        String encryptedEmail = Base64.getEncoder().encodeToString(CryptoMngr.encrypt(KEY.getEncoded(), IV.getEncoded(), newEmail.getBytes()));
        int paramIndex = 1;

        //Build SQL string dynamically
        sql.append("UPDATE userstable SET ");
        if (newUsername != "") {
            updateUser.setUserName(newUsername);
            returnUser.setUserName(newUsername);
            newData.add(updateUser.getUserName());
            if (newPassword != "" || newEmail != "") {
                sql.append("userName=?, ");
            } else {
                sql.append("userName=? ");
            }
            paramIndex++;
        }
        if (newPassword != "") {
            updateUser.setPassword(newPassword);
            returnUser.setPassword(newPassword);
            newData.add(updateUser.getPassword());
            if (newEmail != "") {
                sql.append("password=?, ");
            } else {
                sql.append("password=? ");
            }
            paramIndex++;
        }
        if (newEmail != "") {
            updateUser.setEmail(encryptedEmail);
            returnUser.setEmail(newEmail);
            newData.add(updateUser.getEmail());
            sql.append("email=? ");
            paramIndex++;
        }
        sql.append("WHERE id=?");

        //SQL prep
        PreparedStatement pStmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            //Create connection
            conn = DBC.getConnection();

            //Prepared statement
            pStmt = conn.prepareStatement(sql.toString());
            for (int i = 1; i <= paramIndex; i++) {
                if (i < paramIndex) {
                    pStmt.setString(i, newData.get(i - 1));
                } else {
                    pStmt.setLong(i, userID);
                }
            }
            //Execute query
            pStmt.executeUpdate();
        } catch (SQLException ex) {
            throw new MySQLDuplicateEntryException("SQL server error, Duplicate entry");
        } finally {
            //finally block used to close resources
            try {
                if (rs != null) {
                    rs.close();
                }
                pStmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Finally failed to close connections");
            }
        }
        return returnUser;
    }

    public void setProfilePic(long userID, String imageURL) throws ImageException {
        PreparedStatement pStmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            //Create connection
            conn = DBC.getConnection();
            //Prepared statement
            String sql;
            sql = "UPDATE userstable SET imageURL = ? WHERE ID = ?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, imageURL);
            pStmt.setLong(2, userID);
            pStmt.executeUpdate();
        } catch (SQLException e) {
            throw new ImageException();
        } finally {
            //finally block used to close resources
            try {
                pStmt.close();
                conn.close();
            } catch (SQLException | NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}