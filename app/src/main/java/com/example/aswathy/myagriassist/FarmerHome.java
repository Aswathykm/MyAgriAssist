package com.example.aswathy.myagriassist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class FarmerHome extends AppCompatActivity implements View.OnClickListener {
       LinearLayout weatherdet;
       LinearLayout agrimkt,Tut,fee;
       Button out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_home);
         out=(Button) findViewById(R.id.out);
         out.setOnClickListener(this);
         weatherdet=(LinearLayout)findViewById(R.id.llweather);
         weatherdet.setOnClickListener(this);
         agrimkt=(LinearLayout)findViewById(R.id.Agrimkt);
         agrimkt.setOnClickListener(this);
         Tut=(LinearLayout)findViewById(R.id.AgriTut);
         Tut.setOnClickListener(this);
         fee=(LinearLayout)findViewById(R.id.Agrires);
         fee.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if(view==weatherdet) {
            Intent i = new Intent(FarmerHome.this, LocationDetails.class);
            startActivity(i);
        }
        else if (view==agrimkt){
            Intent j=new Intent(FarmerHome.this,ViewProduct.class);
            startActivity(j);
        }
        else if (view==Tut){
            Intent h=new Intent(FarmerHome.this,ViewTutorial.class);
            startActivity(h);
        }
        else if (view==fee){
            Intent f=new Intent(FarmerHome.this,CorF.class);
            startActivity(f);
        }


        else{
            Intent j=new Intent(FarmerHome.this,LoginActivity.class);
            startActivity(j);
        }

    }
}
