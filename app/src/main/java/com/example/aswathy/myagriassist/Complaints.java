package com.example.aswathy.myagriassist;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Complaints extends AppCompatActivity implements View.OnClickListener {
    Button Com;
    EditText uname,com;
    String uid,ucomp,unam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);
        uname=(EditText)findViewById(R.id.fcname);
        com=(EditText)findViewById(R.id.etf);
        Com=(Button)findViewById(R.id.comp);
        Com.setOnClickListener(this);
        SharedPreferences sh=getSharedPreferences("My data", MODE_PRIVATE);
        uid=sh.getString("ID","");
        Toast.makeText(Complaints.this, "User"+uid, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View view) {
        unam=uname.getText().toString();
        ucomp=com.getText().toString();

        insCom icm=new insCom();
        icm.execute(uid,ucomp);


    }

    private class insCom extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            WebServiceCaller web = new WebServiceCaller();
            web.setSoapObject("Complaint");
            web.addProperty("user_id", strings[0]);
            web.addProperty("complain",strings[1]);
            web.callWebService();
            return web.getResponse();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(Complaints.this,"Thank You For Your Response"+s,Toast.LENGTH_LONG).show();
        }
    }
}
