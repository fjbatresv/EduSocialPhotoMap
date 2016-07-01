package edu.fjbatresv.android.socialphotomap.photoMap.events;

import edu.fjbatresv.android.socialphotomap.entities.Foto;

/**
 * Created by javie on 1/07/2016.
 */
public class PhotoMapEvent {
    private int type;
    private Foto foto;
    private String error;

    public final static int READ_EVENT = 0;
    public final static int DELETE_EVENT = 1;

    public PhotoMapEvent() {
    }

    public PhotoMapEvent(int type, Foto foto, String error) {
        this.type = type;
        this.foto = foto;
        this.error = error;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
