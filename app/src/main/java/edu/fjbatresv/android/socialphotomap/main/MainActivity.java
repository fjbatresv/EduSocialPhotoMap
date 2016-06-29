package edu.fjbatresv.android.socialphotomap.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.fjbatresv.android.socialphotomap.R;

public class MainActivity extends AppCompatActivity {

    public static String signedUser = "signedUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
