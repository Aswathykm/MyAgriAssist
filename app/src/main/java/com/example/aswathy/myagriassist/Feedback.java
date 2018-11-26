package com.example.aswathy.myagriassist;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Feedback extends AppCompatActivity implements View.OnClickListener {
Button Send;
EditText name,feed;
String uid,uname,ufeed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        name=(EditText)findViewById(R.id.fname);
        feed=(EditText)findViewById(R.id.feedb);
        Send=(Button)findViewById(R.id.feedbtn);
        Send.setOnClickListener(this);
        SharedPreferences sh=getSharedPreferences("My data", MODE_PRIVATE);
        uid=sh.getString("ID","");
        Toast.makeText(Feedback.this, "User"+uid, Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onClick(View view) {

        uname=name.getText().toString();
        ufeed=feed.getText().toString();

        insfeed ifd=new insfeed();
        ifd.execute(uid,ufeed);
    }

    private class insfeed extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            WebServiceCaller web = new WebServiceCaller();
            web.setSoapObject("Feedback");
            web.addProperty("user_id", strings[0]);
            web.addProperty("feedback",strings[1]);
            web.callWebService();
            return web.getResponse();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(Feedback.this,"Thank You For Your Response"+s,Toast.LENGTH_LONG).show();
        }
    }
}
