package com.example.aswathy.myagriassist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Home extends AppCompatActivity implements View.OnClickListener {
    LinearLayout mkt,fc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mkt=(LinearLayout)findViewById(R.id.mkt);
        mkt.setOnClickListener(this);
        fc=(LinearLayout)findViewById(R.id.fb);
        fc.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view==mkt){
            Intent j=new Intent(Home.this,ViewProduct.class);
            startActivity(j);
        }
        if (view==fc){
            Intent c=new Intent(Home.this,CorF.class);
            startActivity(c);
        }
    }
}
