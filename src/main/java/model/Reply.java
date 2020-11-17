package model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Reply {
    private long replyID; //We use this id to filter the order of the replys to a certain post. - auto increment in db.
    private long userID;
    private String username;
    private long postID;
    private String contens;
    private LocalDateTime creationDate;

    public Reply(long userID, long postID, String contens) {
        this.userID = userID;
        this.postID = postID;
        this.contens = contens;
        this.creationDate = LocalDateTime.now(ZoneId.of("Europe/Copenhagen"));
    }

    public Reply(long userID, long postID, long replyID, String contens, LocalDateTime creationDate) {
        this.userID = userID;
        this.postID = postID;
        this.replyID = replyID;
        this.contens = contens;
        this.creationDate = creationDate;
    }

    public Reply(long replyID, long userID, String username, long postID, String contens, LocalDateTime creationDate) {
        this.replyID = replyID;
        this.userID = userID;
        this.username = username;
        this.postID = postID;
        this.contens = contens;
        this.creationDate = creationDate;
    }

    public long getUserID() { return userID; }

    public long getPostID() { return postID; }

    public String getUsername() {
        return username;
    }

    public void setReplyID(long replyID) {
        this.replyID = replyID;
    }

    public long getReplyID() { return replyID; }

    public String getContens() { return contens; }

    public LocalDateTime getCreationDate() { return creationDate; }

    public void setContens(String contens) { this.contens = contens; }
}
