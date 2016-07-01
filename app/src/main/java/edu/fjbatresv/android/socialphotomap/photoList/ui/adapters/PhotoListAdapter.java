package edu.fjbatresv.android.socialphotomap.photoList.ui.adapters;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloudinary.android.Utils;

import java.util.List;
import java.util.zip.Inflater;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.fjbatresv.android.socialphotomap.R;
import edu.fjbatresv.android.socialphotomap.domain.Util;
import edu.fjbatresv.android.socialphotomap.entities.Foto;
import edu.fjbatresv.android.socialphotomap.libs.base.ImageLoader;

/**
 * Created by javie on 30/06/2016.
 */
public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.ViewHolder> {
    private Util utils;
    private List<Foto> fotoList;
    private ImageLoader imageLoader;
    private OnItemClickListener listener;

    public PhotoListAdapter(Util utils, List<Foto> fotoList, ImageLoader imageLoader, OnItemClickListener listener) {
        this.utils = utils;
        this.fotoList = fotoList;
        this.imageLoader = imageLoader;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Foto currentFoto = fotoList.get(position);
        holder.setOnItemClickListener(currentFoto, listener);
        imageLoader.load(holder.imgMain, currentFoto.getUrl());
        Log.e("IMG Avatar", utils.getavatarUrl(currentFoto.getEmail()));
        imageLoader.load(holder.imgAvatar, utils.getavatarUrl(currentFoto.getEmail()));
        holder.txtUser.setText(currentFoto.getEmail());
        double latitud = currentFoto.getLatitude();
        double longitud = currentFoto.getLongitud();
        if (latitud != 0 && longitud != 0){
            holder.txtPlace.setText(utils.getLocation(latitud, longitud));
            holder.txtPlace.setVisibility(View.VISIBLE);
        }else{
            holder.txtPlace.setVisibility(View.GONE);
        }
        if (currentFoto.isPublishedByMe()){
            holder.imgDelete.setVisibility(View.VISIBLE);
        }else{
            holder.imgDelete.setVisibility(View.GONE);
        }
    }

    public void addPhoto(Foto foto){
        fotoList.add(0, foto);
        notifyDataSetChanged();
    }

    public void removeFoto(Foto foto){
        fotoList.remove(foto);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return fotoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.imgAvatar)
        CircleImageView imgAvatar;
        @Bind(R.id.imgMain)
        ImageView imgMain;
        @Bind(R.id.imgDelete)
        ImageButton imgDelete;
        @Bind(R.id.txtPlace)
        TextView txtPlace;
        @Bind(R.id.txtUser)
        TextView txtUser;
        @Bind(R.id.imgShare)
        ImageButton imgShare;
        @Bind(R.id.layoutHeader)
        LinearLayout layoutHeader;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        //Esto le da acciones a cada objeto en la vista.
        public void setOnItemClickListener(final Foto foto, final OnItemClickListener listener){
            txtPlace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onPlaceClck(foto);
                }
            });
            imgShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onShareClick(foto, imgMain);
                }
            });
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeleteClick(foto);
                }
            });
        }
    }
}
