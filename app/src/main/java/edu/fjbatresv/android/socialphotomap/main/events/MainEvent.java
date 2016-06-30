package edu.fjbatresv.android.socialphotomap.main.events;

/**
 * Created by javie on 29/06/2016.
 */
public class MainEvent {
    private int type;
    private String error;
    public static final int  UPLOAD_INIT = 0;
    public static final int  UPLOAD_COMPLETE = 1;
    public static final int  UPLOAD_ERROR = 2;

    public MainEvent() {
    }

    public MainEvent(int type, String error) {
        this.type = type;
        this.error = error;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
