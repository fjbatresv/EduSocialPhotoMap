package edu.fjbatresv.android.socialphotomap.libs;

import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import edu.fjbatresv.android.socialphotomap.libs.base.ImageLoader;


/**
 * Created by javie on 14/06/2016.
 */
public class GlideImageLoader implements ImageLoader {
    private RequestManager glideRequestManager;

    public GlideImageLoader(RequestManager glideRequestManager) {
        this.glideRequestManager = glideRequestManager;
    }

    @Override
    public void load(ImageView imgAvatar, String s) {
        glideRequestManager
                .load(s)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .override(700, 700)
                .into(imgAvatar);
    }
}
