package com.buhanka.kinofind;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.buhanka.kinofind.Models.GiveJob;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PredlojitProekt2 extends AppCompatActivity {

    private EditText etPhone, etName, etAbout;
    private DatabaseReference mDataBase;
    private String USER_KEY = "GiveJob";
    public static final String[] str = new String[]{"#kek", "#lol", "#orbidol", "#cheburek"};
    MultiAutoCompleteTextView atv;

    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testing);
       atv= findViewById(R.id.multiAutoCompleteTextView);
       atv.setAdapter(new ArrayAdapter<String>(PredlojitProekt2.this, android.R.layout.simple_list_item_1, str));
        atv.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        init();
    }

    private void init()
    {
        etName = findViewById(R.id.ETName2);
        etAbout = findViewById(R.id.ETAbout2);
        etPhone = findViewById(R.id.ETPhone2);

        mDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY+"/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    public void save (View view) {
        String about, name, phone, id;

        id = mDataBase.getKey();
        name = etName.getText().toString();
        about = etAbout.getText().toString();
        phone = etPhone.getText().toString();

        GiveJob newJob = new GiveJob(id, name, about, phone);

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(about) && !TextUtils.isEmpty(phone) )
        {
            mDataBase.push().setValue(newJob);
            Toast.makeText(this, "Сохранено", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Пустое поле", Toast.LENGTH_SHORT).show();
        }


        Intent intent = new Intent(PredlojitProekt2.this, PredlojitProekt3.class);
        intent.putExtra("hello", "jjoinkjn");
        //startActivity(intent);










    }

    }
