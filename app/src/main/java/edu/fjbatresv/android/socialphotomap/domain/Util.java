package edu.fjbatresv.android.socialphotomap.domain;

import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by javie on 28/06/2016.
 */
public class Util {
    private Geocoder geocoder;
    private final static String gravatarUrl = "http://gravatar.com/avatar/";

    public Util(Geocoder geocoder) {
        this.geocoder = geocoder;
    }

    public String getavatarUrl(String email){
        return gravatarUrl + md5(email) + "?s=64";
    }

    private static final String md5(final String s){
        final String MD5 = "MD5";
        try{
            //Crete MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            //Create Hex String
            StringBuilder hexString = new StringBuilder();
            for(byte aMessageDigest : messageDigest){
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while(h.length() < 2){
                    h = "0" + h;
                    hexString.append(h);
                }
            }
            return hexString.toString();
        }catch(NoSuchAlgorithmException ex){

        }
        return "";
    }

    public String getLocation (double latitud, double longitud){
        String respuesta = "";
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitud, longitud, 1);
            Address address = addresses.get(0);
            for (int i = 0; i < address.getMaxAddressLineIndex(); i++){
                respuesta += address.getAddressLine(i) + ", ";
            }
        } catch (IOException e) {
            Log.e("getLocation", e.toString());
        }
        return respuesta;
    }
}
