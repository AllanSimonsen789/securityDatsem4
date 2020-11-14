package model;

import java.time.LocalDateTime;

public class Post {
    private long postID;
    private long userID;
    private String username;
    private String postTitle;
    private String contens;
    private LocalDateTime creationDate;

    public Post(long postID, long userID, String username, String postTitle, String contens, LocalDateTime creationDate) {
        this.postID = postID;
        this.username = username;
        this.userID = userID;
        this.postTitle = postTitle;
        this.contens = contens;
        this.creationDate = creationDate;
    }

    public long getPostID() {
        return postID;
    }

    public long getUserID() {
        return userID;
    }

    public String getUsername(){
        return username;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getContens() {
        return contens;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
}
