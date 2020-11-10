package model;

import java.util.Date;

public class Reply {
    private long userID;
    private long postID;
    private long replyID; //We use this id to filter the order of the replys to a certain post. - auto increment in db.
    private String contens;
    private Date creationDate;

    public Reply(long userID, long postID, long replyID, String contens, Date creationDate) {
        this.userID = userID;
        this.postID = postID;
        this.replyID = replyID;
        this.contens = contens;
        this.creationDate = creationDate;
    }

    public long getUserID() { return userID; }

    public long getPostID() { return postID; }

    public long getReplyID() { return replyID; }

    public String getContens() { return contens; }

    public Date getCreationDate() { return creationDate; }

    public void setContens(String contens) { this.contens = contens; }
}
