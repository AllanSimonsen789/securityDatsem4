package model;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Date;

public class User {
    private long userID;
    private String userName;
    private String password;
    private String email;
    private String role;
    private Date creationDate;

    public User() { }// Temporary - delete later.

    //Builds user after login
    public User(long userID, String userName, String email) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
    }

    //Register constructor
    public User(String userName, String password, String email, String role, Date creationDate) {
        this.userName = userName;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt(10));
        this.email = email;
        this.role = role;
        this.creationDate = creationDate;
    }

    public long getUserID() { return userID; }

    public String getRole() { return role; }

    public Date getCreationDate() { return creationDate; }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt(10));
    }

    public boolean verifyPassword(String pw) {
        return (BCrypt.checkpw(pw, password));
    }


    //Dummy remove later.
    public boolean isValidUserCredentials(String sUserName, String sUserPassword) {
        if (sUserName.equals("test") && sUserPassword.equals("test123")) {
            return true;
        } else {
            return false;
        }
    }
}
