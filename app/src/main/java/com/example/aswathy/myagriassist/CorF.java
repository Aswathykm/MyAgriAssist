package com.example.aswathy.myagriassist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CorF extends AppCompatActivity implements View.OnClickListener {
Button f,c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cor_f);
        f=(Button)findViewById(R.id.feed);
        f.setOnClickListener(this);
        c=(Button)findViewById(R.id.com);
        c.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==f)
        {
            Intent i=new Intent(CorF.this,Feedback.class);
            startActivity(i);
        }
        else
        {
            Intent j=new Intent(CorF.this,Complaints.class);
            startActivity(j);
        }

    }
}
