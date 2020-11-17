package model;

import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

public class User {
    private long userID;
    private String userName;
    private String password;
    private String email;
    private String role;
    private LocalDateTime creationDate;
    private String imageURL;

    public User() {
    }// Temporary - delete later.

    //Builds user after login
    public User(long userID, String userName, String email) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
    }

    //Register constructor
    public User(String userName, String password, String email) {
        this.userName = userName;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt(12));
        this.email = email;
        this.role = "user";
        this.creationDate = LocalDateTime.now(ZoneId.of("Europe/Copenhagen"));
    }

    public User(String userName, String password, String email, String imageURL) {
        this.userName = userName;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt(12));
        this.email = email;
        this.imageURL = imageURL;
        this.role = "user";
        this.creationDate = LocalDateTime.now(ZoneId.of("Europe/Copenhagen"));
    }

    public long getUserID() { return userID; }

    public String getRole() { return role; }

    public LocalDateTime getCreationDate() { return creationDate; }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImageURL() { return imageURL; }

    public void setImageURL(String imageURL) { this.imageURL = imageURL; }

    //Might not be needed.
    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public void setUserID(long userID) { this.userID = userID; }

    public void setEmail(String email) { this.email = email; }

    public void setRole(String role) { this.role = role; }

    public boolean verifyPassword(String typedPw, String sqlPW) {
        return (BCrypt.checkpw(typedPw, sqlPW));
    }

}
