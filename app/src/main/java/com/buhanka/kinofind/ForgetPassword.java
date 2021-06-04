package com.buhanka.kinofind;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {
    EditText signT;
    ProgressBar progressbar;

    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);

        signT = findViewById(R.id.ETSignEmailF);
        progressbar = findViewById(R.id.progressbarF);


    }








    public void send(View view) {

        String email = signT.getText().toString();

        if (email.isEmpty()) {
            Toast.makeText(getApplicationContext(),"Вы не ввели Email",Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getApplicationContext(),"Не корректный email",Toast.LENGTH_SHORT).show();
            return;
        }

        progressbar.setVisibility(View.VISIBLE);

        FirebaseAuth.getInstance()
                .sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(getApplicationContext(),"Усё хорошо, теперь проверьте почту, чтобы сбросить пароль.",Toast.LENGTH_LONG).show();
                progressbar.setVisibility(View.GONE);
                startActivity(new Intent(ForgetPassword.this, MainActivity.class));
                finish();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(getApplicationContext(),"Ошибка! Вероятно вы использовали другую почту, ну или мышак перегрыз провод к интернету",Toast.LENGTH_LONG).show();
                        progressbar.setVisibility(View.GONE);
                    }
                });

                    /*
                progressbar.setVisibility(View.GONE);)
            } else {
                this.toast(task.exception?.message!!)
            }
        }*/


    }
}