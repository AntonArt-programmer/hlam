package org.juicecode.telehlam.database.messages;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "messages")
public class Message {
    public static final int MESSAGE_INCOMING = 0;
    public static final int MESSAGE_OUTGOING = 1;

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int type;
    private String text;
    private long timestamp;
    private long authorId;
    private long receiverId;
    private boolean hasRead;

    public String getAuthorLogin() {
        return authorLogin;
    }

    public void setAuthorLogin(String authorLogin) {
        this.authorLogin = authorLogin;
    }

    private String authorLogin;
    @TypeConverters(Status.class)
    private Status status;

    public Message() {

    }

    @Ignore
    public Message(int type, String text, long authorId, long receiverId,boolean hasRead, String authorLogin) {
        this.type = type;
        this.authorId = authorId;
        this.receiverId = receiverId;
        this.text = text;
        this.timestamp = (new Date().getTime());
        this.status = Status.NONE;
        this.hasRead = hasRead;
        this.authorLogin = authorLogin;
    }

    @Ignore
    public Message(int type, String text, long authorId, long receiverId, long timestamp ,boolean hasRead,  String authorLogin) {
        this.type = type;
        this.authorId = authorId;
        this.receiverId = receiverId;
        this.text = text;
        this.timestamp = timestamp;
        this.status = Status.NONE;
        this.hasRead = hasRead;
        this.authorLogin = authorLogin;
    }

    public boolean isHasRead() {
        return hasRead;
    }

    public void setHasRead(boolean hasRead) {
        this.hasRead = hasRead;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(long receiverId) {
        this.receiverId = receiverId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        if (status == Status.READ && status == Status.SENT) {
            // TODO(all): create custom error class
            throw new Error("Cannot change status");
        }
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
//coming soon
    public enum Status {
        NONE(0), SENT(1), READ(2), ERROR(-1);

        private int status;

        Status(int status) {
            this.status = status;
        }

        @TypeConverter
        public static int toInteger(Status status) {
            return status.status;
        }

        @TypeConverter
        public static Status toStatus(int code) {
            switch (code) {
                case 0:
                    return NONE;
                case 1:
                    return SENT;
                case 2:
                    return READ;
                case -1:
                    return ERROR;
                default:
                    throw new RuntimeException("Unknown Message Type code");
            }
        }
    }

}
