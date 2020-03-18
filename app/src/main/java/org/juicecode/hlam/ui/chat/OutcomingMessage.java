package org.juicecode.hlam.ui.chat;

public class OutcomingMessage {
    private String message;
    private String date;
    private Status MessageStatus;

    public Status getMessageStatus() {
        return MessageStatus;
    }

    public void setMessageStatus(Status messageStatus) {
        MessageStatus = messageStatus;
    }

    private enum Status{
        SENDING,SENT,READ
    }
    public OutcomingMessage(String text) {
        this.message = text;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
