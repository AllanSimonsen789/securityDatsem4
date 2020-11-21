package model;

import java.time.LocalDateTime;

public class ForumPost {
    private long postID;
    private long userID;
    private String username;
    private String role;
    private String postTitle;
    private String contens;
    private LocalDateTime creationDate;

    public ForumPost(long postID, long userID, String username,String role, String postTitle, String contens, LocalDateTime creationDate) {
        this.postID = postID;
        this.username = username;
        this.role = role;
        this.userID = userID;
        this.postTitle = postTitle;
        this.contens = contens;
        this.creationDate = creationDate;
    }

    public ForumPost(int userID, String postTitle, String contens, LocalDateTime creationDate) {
        this.userID = userID;
        this.postTitle = postTitle;
        this.contens = contens;
        this.creationDate = creationDate;
    }

    public void setPostID(long postID) {
        this.postID = postID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getPostID() {
        return postID;
    }

    public long getUserID() {
        return userID;
    }

    public String getUsername() { return username; }

    public String getPostTitle() {
        return postTitle;
    }

    public String getContens() {
        return contens;
    }

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
}
