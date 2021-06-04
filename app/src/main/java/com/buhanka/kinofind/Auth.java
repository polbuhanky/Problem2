package com.buhanka.kinofind;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Auth extends AppCompatActivity {

    Button btnSignIn, btnRegister;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;

    RelativeLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        btnSignIn = findViewById(R.id.BTNSign);



        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");

    }


    private void showSignWindow() {
        Toast toast1 = Toast.makeText(getApplicationContext(),
                "Проверка данных", Toast.LENGTH_SHORT);
        toast1.show();



        final EditText mailText = (EditText) findViewById(R.id.ETSignEmail);
        final EditText passText = (EditText) findViewById(R.id.ETSignPass);


        if(TextUtils.isEmpty(mailText.getText().toString())) {
            Toast toast2 = Toast.makeText(getApplicationContext(),
                    "Почта не введена", Toast.LENGTH_SHORT);
            toast2.show();
            return;
        }

        if(passText.getText().toString().length() < 5) {
            Toast toast3 = Toast.makeText(getApplicationContext(),
                    "Введите пароль больше 5ти символов", Toast.LENGTH_SHORT);
            toast3.show();
            return;
        }




        auth.signInWithEmailAndPassword(mailText.getText().toString(), passText.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                final CheckBox checkP = findViewById(R.id.checkBoxAuth);

                if (checkP.isChecked()){
                    SharedPreferences autoAuth = getSharedPreferences("auto_auth", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = autoAuth.edit();
                    editor.putBoolean("isNecessary", true);
                    editor.putString("Email", mailText.getText().toString());
                    editor.putString("Password", passText.getText().toString());
                    editor.apply();
                    Toast toast2 = Toast.makeText(getApplicationContext(),
                            "Теперь вы будете входить автоматически, что бы выйти из аккаунта - нажмите на три точки в верхней панели меню", Toast.LENGTH_LONG);
                    toast2.show();

                }
                startActivity(new Intent(Auth.this, Menu.class));
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

    public void SignOn2(View view) {
        showSignWindow();
    }


    public void forget(View view) {
        startActivity(new Intent(Auth.this, ForgetPassword.class));
    }
}