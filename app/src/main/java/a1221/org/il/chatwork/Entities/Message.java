package a1221.org.il.chatwork.Entities;

import java.util.Calendar;

/**
 * Created by Daniel on 1/4/2017.
 */

public class Message {

    private int _ID;

    private String text;

    private Calendar date;

    private boolean isUserSender;

    public Message() {
    }

    public Message(int _ID, String text, Calendar date, boolean isUserSender) {
        this._ID = _ID;
        this.text = text;
        this.date = date;
        this.isUserSender = isUserSender;
    }

    public int get_ID() {
        return _ID;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public boolean isUserSender() {
        return isUserSender;
    }

    public void setUserSender(boolean userSender) {
        isUserSender = userSender;
    }
}
