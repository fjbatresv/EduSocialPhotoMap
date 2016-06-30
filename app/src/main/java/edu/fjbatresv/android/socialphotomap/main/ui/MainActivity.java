package edu.fjbatresv.android.socialphotomap.main.ui;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.fjbatresv.android.socialphotomap.FotoListFragment;
import edu.fjbatresv.android.socialphotomap.FotoMapFragment;
import edu.fjbatresv.android.socialphotomap.R;
import edu.fjbatresv.android.socialphotomap.SocialPhotoMapApp;
import edu.fjbatresv.android.socialphotomap.login.ui.LoginActivity;
import edu.fjbatresv.android.socialphotomap.main.MainPresenter;
import edu.fjbatresv.android.socialphotomap.main.events.MainEvent;
import edu.fjbatresv.android.socialphotomap.main.ui.adapters.MainSectionsPagerAdapter;

public class MainActivity extends AppCompatActivity implements MainView,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabs)
    TabLayout tabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;

    public static String signedUser = "signedUser";
    private SocialPhotoMapApp app;
    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    MainSectionsPagerAdapter adapter;
    @Inject
    MainPresenter presenter;


    private String fotoPath;
    private Location lastKnownLocation;
    private GoogleApiClient apiClient;
    private boolean resolvingError = false;
    private static final int PERMISSIONS_REQUEST_LOCATION = 1;
    private final static int REQUEST_PICTURE = 1;
    private static final int REQUEST_RESOLVE_ERROR = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        app = (SocialPhotoMapApp) getApplication();
        setupInjection();
        setupNavigation();
        setupGoogleApiClient();
        presenter.onCreate();
    }

    @Override
    protected void onStart() {
        apiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        apiClient.disconnect();
        super.onStop();
    }

    private void setupGoogleApiClient() {
        if (apiClient == null) {
            apiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API).build();
        }
    }

    private void setupNavigation() {
        String email = sharedPreferences.getString(app.getEmailKey(), getString(R.string.app_name));
        toolbar.setTitle(email);
        setSupportActionBar(toolbar);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupInjection() {
        String[] titles = new String[]{getString(R.string.main_title_list), getString(R.string.main_title_map)};
        Fragment[] fragments = new Fragment[]{new FotoListFragment(), new FotoMapFragment()};
        app.getMainComponent(this, getSupportFragmentManager(), fragments, titles).inject(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout:
                logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        presenter.logout();
        sharedPreferences.edit().clear().commit();
        startActivity(
                new Intent(this, LoginActivity.class)
                        .addFlags(
                                Intent.FLAG_ACTIVITY_CLEAR_TOP
                                        | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        | Intent.FLAG_ACTIVITY_NEW_TASK
                        )
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case PERMISSIONS_REQUEST_LOCATION:{
                if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    setLastLocation();
                }
                return;
            }
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                }, PERMISSIONS_REQUEST_LOCATION);
            }
            return;
        }
        setLastLocation();
    }

    private void setLastLocation() {
        if (LocationServices.FusedLocationApi.getLocationAvailability(apiClient).isLocationAvailable()){
            lastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(apiClient);
            //showSnackBar(lastKnownLocation.toString());
        } else {
            showSnackBar(getString(R.string.main_error_location_not_available));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        apiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (resolvingError){
            return;
        } else if (connectionResult.hasResolution()){
            resolvingError = true;
            try {
                connectionResult.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
            } catch (IntentSender.SendIntentException e) {
                Log.e("ConnectionFailed", e.toString());
            }
        }else{
            resolvingError = true;
            GoogleApiAvailability
                    .getInstance()
                    .getErrorDialog(this, connectionResult.getErrorCode(), REQUEST_RESOLVE_ERROR)
                    .show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_RESOLVE_ERROR){
            resolvingError = false;
            if (resultCode == RESULT_OK){
                if (!apiClient.isConnecting() && !apiClient.isConnected()){
                    apiClient.connect();
                }
            }
        } else if (requestCode == REQUEST_PICTURE){
            Log.e("activityResult", "picture");
            if (resultCode == RESULT_OK){
                Log.e("activityResult", "ok");
                boolean fromCamera = (data == null || data.getData() == null);
                Log.e("activityResult", "camara: " + fromCamera);
                if (fromCamera){
                    Log.e("activityResult", "addGallery | " + fotoPath);
                    addToGallery();
                }else{
                    Log.e("activityResult", "realPathFromUri");
                    fotoPath = getRealPathFromUri(data.getData());
                }
                Log.e("activityResult", "uploadPhoto");
                presenter.uploadPhoto(lastKnownLocation, fotoPath);
            }
        }
    }

    @Override
    public void onUploadInit() {
        showSnackBar(getString(R.string.main_notice_upload_init));
    }

    @Override
    public void onUploadComplete() {
        showSnackBar(getString(R.string.main_notice_upload_complete));
    }

    @Override
    public void onUploadError(String error) {
        showSnackBar(error);
    }

    @OnClick(R.id.fab)
    public void tackePicture(){
        //Esto seria null si el dispositivo no tiene camara o algo
        Intent chooserIntent = null;
        List<Intent> intentList = new ArrayList<Intent>();
        //Elegir imagen de la galeria
        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //Tomar fotografia
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra("return-data", true);
        File foto = getFile();
        if (foto != null){
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(foto));
            if (cameraIntent.resolveActivity(getPackageManager()) != null){
                intentList = addIntentToList(intentList, cameraIntent);
            }
        }
        Log.e("tackePicture", pickIntent.resolveActivity(getPackageManager()).toString());
        if(pickIntent.resolveActivity(getPackageManager()) != null){
            Log.e("tackePicture", "pickIntent");
            intentList = addIntentToList(intentList, pickIntent);
        }
        if (intentList.size() > 0){
            chooserIntent = Intent.createChooser(intentList.remove(intentList.size() - 1)
                    , getString(R.string.main_message_picture_resource));
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toArray(new Parcelable[]{}));
        }
            startActivityForResult(chooserIntent, REQUEST_PICTURE);
    }

    private File getFile(){
        File foto = null;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        try {
            foto = File.createTempFile(imageFileName, ".jpg", storageDir);
            fotoPath = foto.getAbsolutePath();
        } catch (IOException e) {
            showSnackBar(getString(R.string.main_error_dispatch_camera));
            Log.e("getFile", e.toString());
        }
        return foto;
    }

    private List<Intent> addIntentToList(List<Intent> list, Intent intent){
        //Esto recibe las aplicaciones que pueden recibir el intent
        List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resInfo){
            String packageName = resolveInfo.activityInfo.packageName;
            Intent target = new Intent(intent);
            target.setPackage(packageName);
            list.add(target);
        }
        return list;
    }

    private void showSnackBar(String mensaje){
        Snackbar.make(viewPager, mensaje, Snackbar.LENGTH_LONG).show();
    }

    private String getRealPathFromUri(Uri data) {
        String result = null;
        Cursor cursor = getContentResolver().query(data, null, null, null, null);
        if (cursor == null){
            result = data.getPath();
        }else{
            if (data.toString().contains("mediaKey")){
                cursor.close();
                try {
                    File file = File.createTempFile("tempImg", ".jpg", getCacheDir());
                    InputStream input = getContentResolver().openInputStream(data);
                    OutputStream output = new FileOutputStream(file);
                    try{
                        byte[] buffer = new byte[ 4* 1024];
                        int read;
                        while ((read = input.read(buffer)) != -1){
                            output.write(buffer, 0, read);
                        }
                        output.flush();
                        result = file.getAbsolutePath();
                    } finally {
                        output.close();
                        input.close();
                    }
                } catch (IOException e) {
                    Log.e("realPathUri", e.toString());
                }
            } else {
                cursor.moveToFirst();
                int dataColumn = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                result = cursor.getString(dataColumn);
                cursor.close();
            }
        }
        return result;
    }

    private void addToGallery() {
        Intent mediaScan = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE );
        File file = new File(fotoPath);
        Uri contentUri = Uri.fromFile(file);
        mediaScan.setData(contentUri);
        sendBroadcast(mediaScan);
    }

}
