package com.example.aswathy.myagriassist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Admin extends AppCompatActivity implements View.OnClickListener {
    Button weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        weather=(Button)findViewById(R.id.alert);
        weather.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        Intent alrt=new Intent(Admin.this,AlertMessage.class);
        startActivity(alrt);
    }
}
