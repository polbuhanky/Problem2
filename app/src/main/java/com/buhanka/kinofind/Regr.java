package com.buhanka.kinofind;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.buhanka.kinofind.Models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Regr extends AppCompatActivity {

    Button  btnRegister;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;

    RelativeLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        btnRegister = findViewById(R.id.BTNReg);


        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterWindow();
            }
        });
    }


    private void showRegisterWindow() {
        Toast toast1 = Toast.makeText(getApplicationContext(),
                "Проверка данных", Toast.LENGTH_SHORT);
        toast1.show();



        final EditText mailText = (EditText) findViewById(R.id.ETRegEmail);
        final EditText passText = (EditText) findViewById(R.id.ETRegPass);
        final EditText nameText = (EditText) findViewById(R.id.ETRegName);
        final EditText phoneText = (EditText) findViewById(R.id.ETRegPhone);
        final CheckBox checkP = findViewById(R.id.checkBox);

        if (!checkP.isChecked()){
            Toast toast2 = Toast.makeText(getApplicationContext(),
                    "Примите соглашение", Toast.LENGTH_SHORT);
            toast2.show();
            return;
        }

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




        auth.createUserWithEmailAndPassword(mailText.getText().toString(), passText.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        User user = new User();
                        user.setEmail(mailText.getText().toString());
                        user.setName(nameText.getText().toString());
                        user.setPass(passText.getText().toString());
                        user.setPhone(phoneText.getText().toString());

                        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user);
                        Toast toast3 = Toast.makeText(getApplicationContext(),
                                "Вроде верно ;)", Toast.LENGTH_SHORT);
                        toast3.show();
                        startActivity(new Intent(Regr.this, Navigate.class));
                        finish();

                    }
                });

    }

    public void BTNToRegist(View view) {
        showRegisterWindow();
    }



}
