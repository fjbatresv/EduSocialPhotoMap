package edu.fjbatresv.android.socialphotomap.photoList.ui.adapters;

import android.widget.ImageView;

import edu.fjbatresv.android.socialphotomap.entities.Foto;

/**
 * Created by javie on 30/06/2016.
 */
public interface OnItemClickListener {
    void onPlaceClck(Foto foto);
    void onShareClick(Foto foto, ImageView img);
    void onDeleteClick(Foto foto);
}
