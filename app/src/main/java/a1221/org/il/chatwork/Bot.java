package a1221.org.il.chatwork;

import java.util.Calendar;

/**
 * Created by nadav on 1/4/2017.
 * Package: a1221.org.il.chatwork
 */

public class Bot {

    private int _ID;

    private String header;

    private Calendar lastDate;

    private String textPreview;

    private byte[] image;

    public Bot(int _ID, String header, String textPreview, Calendar lastDate, byte[] image) {
        this._ID = _ID;
        this.header = header;
        this.image = image;
        this.lastDate = lastDate;
        this.textPreview = textPreview;
    }

    public int get_ID() {
        return _ID;
    }

    public void set_ID(int _ID) {
        this._ID = _ID;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Calendar getLastDate() {
        return lastDate;
    }

    public void setLastDate(Calendar lastDate) {
        this.lastDate = lastDate;
    }

    public String getTextPreview() {
        return textPreview;
    }

    public void setTextPreview(String textPreview) {
        this.textPreview = textPreview;
    }
}
