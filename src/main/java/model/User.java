package model;

import Database.DBConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Properties;

public class User {
    private long userID;
    private String userName;
    private String password;
    private String email;
    private String role;
    private LocalDateTime creationDate;
    private String imageURL;

    private static String pepper;

    private static void setPepper(){
        try{
            InputStream prob = null;
            prob = DBConnection.class.getResourceAsStream("/pepper.properties");
            Properties pros = new Properties();
            pros.load(prob);
            pepper = pros.getProperty("pepper");
        }catch(IOException e){
            throw new RuntimeException();
        }

    }

    public User() {
    }

    //Builds user after login
    public User(long userID, String userName, String email) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
    }

    //Register constructor
    public User(String userName, String password, String email) {
        this.userName = userName;
        if(pepper == null) {setPepper();}
        this.password = BCrypt.hashpw(pepper + password, BCrypt.gensalt(12));
        this.email = email;
        this.role = "user";
        this.creationDate = LocalDateTime.now(ZoneId.of("Europe/Copenhagen"));
        this.imageURL = "https://res.cloudinary.com/dmk5yii3m/image/upload/v1605704326/Security/Users-User-Male-4-icon_cuefxg.png";
    }

    public User(String userName, String password, String email, String imageURL) {
        this.userName = userName;
        if(pepper == null) {setPepper();}
        this.password = BCrypt.hashpw(pepper + password, BCrypt.gensalt(12));
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
        if(pepper == null) {setPepper();}
        this.password = BCrypt.hashpw(pepper + password, BCrypt.gensalt(12));
    }

    public void setUserID(long userID) { this.userID = userID; }

    public void setEmail(String email) { this.email = email; }

    public void setRole(String role) { this.role = role; }

    public boolean verifyPassword(String typedPw, String sqlPW) {
        if(pepper == null){setPepper();}
        return (BCrypt.checkpw(pepper + typedPw, sqlPW));
    }

}
