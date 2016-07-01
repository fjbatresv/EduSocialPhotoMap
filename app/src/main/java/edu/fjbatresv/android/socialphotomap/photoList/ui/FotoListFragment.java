package edu.fjbatresv.android.socialphotomap.photoList.ui;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;

import java.io.ByteArrayOutputStream;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.fjbatresv.android.socialphotomap.R;
import edu.fjbatresv.android.socialphotomap.SocialPhotoMapApp;
import edu.fjbatresv.android.socialphotomap.entities.Foto;
import edu.fjbatresv.android.socialphotomap.photoList.PhotoListPresenter;
import edu.fjbatresv.android.socialphotomap.photoList.ui.adapters.OnItemClickListener;
import edu.fjbatresv.android.socialphotomap.photoList.ui.adapters.PhotoListAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class FotoListFragment extends Fragment implements PhotoListView, OnItemClickListener {
    @Bind(R.id.recycleView)
    RecyclerView recyclerView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.container)
    FrameLayout container;

    @Inject
    PhotoListPresenter presenter;
    @Inject
    PhotoListAdapter adapter;

    public FotoListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpInjection();
        presenter.onCreate();
    }

    private void setUpInjection() {
        SocialPhotoMapApp app = (SocialPhotoMapApp) getActivity().getApplication();
        app.getPhotoListComponent(this, this, this).inject(this);
    }

    @Override
    public void onDestroy() {
        presenter.unSubscribe();
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_foto_list, container, false);
        ButterKnife.bind(this, view);
        setUpRecyclerView();
        presenter.subscribe();
        return view;
    }

    private void setUpRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void toggleList(boolean mostrar) {
        int visible = View.VISIBLE;
        if(!mostrar){
            visible = View.GONE;
        }
        recyclerView.setVisibility(visible);
    }

    @Override
    public void toggleProgress(boolean mostrar) {
        int visible = View.VISIBLE;
        if(!mostrar){
            visible = View.GONE;
        }
        progressBar.setVisibility(visible);
    }

    @Override
    public void addPhoto(Foto foto) {
        adapter.addPhoto(foto);
    }

    @Override
    public void removePhoto(Foto foto) {
        adapter.removeFoto(foto);
    }

    @Override
    public void onPhotosError(String error) {
        Snackbar.make(container, error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onPlaceClck(Foto foto) {
        Intent intent = new Intent(Intent.ACTION_VIEW)
                .setData(Uri.parse("geo:" + foto.getLatitude() + ", " + foto.getLongitud()));
        //Esto nos confirma que si hay como resolver el intent
        if (intent.resolveActivity(getActivity().getPackageManager()) != null ){
            startActivity(intent);
        }
    }

    @Override
    public void onShareClick(Foto foto, ImageView img) {
        Bitmap bitmap = ((GlideBitmapDrawable)img.getDrawable()).getBitmap();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, null, null);
        Uri uri = Uri.parse(path);
        Intent share = new Intent(Intent.ACTION_SEND)
                .setType("image/jpeg")
                .putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(share, getString(R.string.photolist_message_share)));

    }

    @Override
    public void onDeleteClick(Foto foto) {
        presenter.removePhoto(foto);
    }
}
