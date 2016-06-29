package edu.fjbatresv.android.socialphotomap.domain;

import com.firebase.client.FirebaseError;

/**
 * Created by javie on 28/06/2016.
 */
public interface FirebaseActionListenerCallback {
    void onSuccess();
    void onError(FirebaseError error);
}
