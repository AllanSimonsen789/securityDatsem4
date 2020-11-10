package model;

import java.util.Date;

public class Post {
    private long postID;
    private long userID;
    private String postTitle;
    private String contens;
    private Date creationDate;

    public Post(long postID, long userID, String postTitle, String contens, Date creationDate) {
        this.postID = postID;
        this.userID = userID;
        this.postTitle = postTitle;
        this.contens = contens;
        this.creationDate = creationDate;
    }

    public String getPostTitle() { return postTitle; }

    public String getContens() { return contens; }

    public Date getCreationDate() { return creationDate; }
}
