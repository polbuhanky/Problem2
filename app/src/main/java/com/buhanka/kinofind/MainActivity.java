package com.buhanka.kinofind;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    private static String mailText;
    private static String passText;


    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        auth = FirebaseAuth.getInstance();

        SharedPreferences autoAuth = getSharedPreferences("auto_auth", Context.MODE_PRIVATE);

        if (autoAuth.contains("Password") && autoAuth.contains("Email") && autoAuth.contains("isNecessary")) {
            if(autoAuth.getBoolean("isNecessary", false)){

                mailText = autoAuth.getString("Email", "");
                passText = autoAuth.getString("Password", "");


                auth.signInWithEmailAndPassword(mailText, passText).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast toast2 = Toast.makeText(getApplicationContext(),
                                "Вы вошли в аккаунт " + mailText, Toast.LENGTH_SHORT);
                        toast2.show();

                        startActivity(new Intent(MainActivity.this, Menu.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast toast10 = Toast.makeText(getApplicationContext(),
                                "Ошибка " + e.getMessage(), Toast.LENGTH_SHORT);
                        toast10.show();
                    }
                });


            }

        }

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.BarColor));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);}


    public void goToSignView(View view) {
        Intent intent = new Intent(MainActivity.this, Auth.class);
        startActivity(intent);
    }

    public void goToRegistrationView(View view) {
        Intent intent = new Intent(MainActivity.this, Regr.class);
        startActivity(intent);
    }
}


