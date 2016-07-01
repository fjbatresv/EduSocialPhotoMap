package edu.fjbatresv.android.socialphotomap.photoMap.ui;


import android.Manifest;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.fjbatresv.android.socialphotomap.R;
import edu.fjbatresv.android.socialphotomap.SocialPhotoMapApp;
import edu.fjbatresv.android.socialphotomap.domain.Util;
import edu.fjbatresv.android.socialphotomap.entities.Foto;
import edu.fjbatresv.android.socialphotomap.libs.base.ImageLoader;
import edu.fjbatresv.android.socialphotomap.photoMap.PhotoMapPresenter;


/**
 * A simple {@link Fragment} subclass.
 */
public class FotoMapFragment extends Fragment implements PhotoMapView, OnMapReadyCallback, GoogleMap.InfoWindowAdapter {
    private static final int PERMISSIONS_REQUEST_LOCATION = 1;
    @Bind(R.id.container)
    FrameLayout container;

    @Inject
    Util util;
    @Inject
    ImageLoader imageLoader;
    @Inject
    PhotoMapPresenter presenter;

    private GoogleMap googleMap;
    private HashMap<Marker, Foto> markers;

    public FotoMapFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpInjection();
        markers = new HashMap<Marker, Foto>();
        presenter.onCreate();
    }

    private void setUpInjection() {
        SocialPhotoMapApp app = (SocialPhotoMapApp) getActivity().getApplication();
        app.getPhotoMapComponent(this, this).inject(this);
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
        View view = inflater.inflate(R.layout.fragment_foto_map, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void addPhoto(Foto foto) {
        LatLng location = new LatLng(foto.getLatitude(), foto.getLongitud());
        Marker marker = this.googleMap.addMarker(new MarkerOptions().position(location));
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 6));
        markers.put(marker, foto);
    }

    @Override
    public void removePhoto(Foto foto) {
        for (Map.Entry<Marker, Foto> opt: markers.entrySet()){
            Foto fotoA = opt.getValue();
            Marker markerA = opt.getKey();
            if (fotoA.getId().equals(foto.getId())){
                markerA.remove();
                markers.remove(markerA);
                break;
            }
        }
    }

    @Override
    public void onPhotosError(String error) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        presenter.subscribe();
        this.googleMap.setInfoWindowAdapter(this);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && 
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                }, PERMISSIONS_REQUEST_LOCATION);
            }
            return;
        }
        this.googleMap.setMyLocationEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case PERMISSIONS_REQUEST_LOCATION:{
                if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (this.googleMap != null){
                        this.googleMap.setMyLocationEnabled(true);
                    }
                }
                return;
            }
        }
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.info_window, null);
        Foto fotoA = markers.get(marker);
        CircleImageView imgAvatar = (CircleImageView)view.findViewById(R.id.imgAvatar);
        TextView txtUser = (TextView) view.findViewById(R.id.txtUser);
        ImageView imgMain = (ImageView) view.findViewById(R.id.imgMain);

        imageLoader.load(imgAvatar, util.getavatarUrl(fotoA.getEmail()));
        imageLoader.load(imgMain, fotoA.getUrl());
        txtUser.setText(fotoA.getEmail());

        return view;
    }
}
