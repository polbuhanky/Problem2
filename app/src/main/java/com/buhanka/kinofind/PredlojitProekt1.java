package com.buhanka.kinofind;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class PredlojitProekt1 extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        super.onCreate(savedInstanceState);
        setContentView(R.layout.predlojit_proekt1);
    }

    public void goToPP2(View view) {
        startActivity(new Intent(PredlojitProekt1.this, PredlojitProekt2.class));
    }
}
