package com.example.aswathy.myagriassist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText username,pawd;
    Button login,reg,regf;
    String user,pass;
    SharedPreferences sh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=(EditText)findViewById(R.id.etuname);
        pawd=(EditText)findViewById(R.id.etpass);
        login=(Button)findViewById(R.id.btnlogin);
        login.setOnClickListener(this);
       // cancel=(Button)findViewById(R.id.btncancel);
        //cancel.setOnClickListener(this);
        reg=(Button)findViewById(R.id.btnbreg);
        reg.setOnClickListener(this);
        regf=(Button)findViewById(R.id.btnfreg);
        regf.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        if(view==login) {
            user = username.getText().toString();
            pass = pawd.getText().toString();

                selectData sdata = new selectData();
                sdata.execute(user, pass);




        }

       else if (view==reg)
        {
            Intent in=new Intent(this,BuyerRegActivity.class);
            startActivity(in);

        }
        if (view==regf)
        {
            Intent inn=new Intent(this,FarmerRegActivity.class);
            startActivity(inn);

        }


    }



    private class selectData extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {
            WebServiceCaller web=new WebServiceCaller();
            web.setSoapObject("login");
            web.addProperty("username",strings[0]);
            web.addProperty("password",strings[1]);
            web.callWebService();
            return web.getResponse();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(LoginActivity.this,"Login Successfull"+s, Toast.LENGTH_LONG).show();

            String data[]=s.split(":");
            String uid=data[0].trim();
            String type=data[1].trim();
            username.setText("");
            pawd.setText("");

            if (type.equals("Buyer"))
            {
                sh=getSharedPreferences("My data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sh.edit();
            editor.putString("ID",uid);
            editor.commit();

                Intent i=new Intent(LoginActivity.this,Home.class);
                startActivity(i);

            }
            else if (type.equals("Farmer"))
            {
                sh=getSharedPreferences("My data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sh.edit();
                editor.putString("ID",uid);
                editor.commit();

                Intent in=new Intent(LoginActivity.this,FarmerHome.class);
                startActivity(in);

            }
            else if (type.equals("Admin"))
            {
                sh=getSharedPreferences("My data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sh.edit();
                editor.putString("ID",uid);
                editor.commit();

                Intent inn=new Intent(LoginActivity.this,Admin.class);
                startActivity(inn);

            }



            else
            {
                Toast.makeText(LoginActivity.this,"Login Failed",Toast.LENGTH_LONG).show();
            }
//            Intent i=new Intent(LoginActivity.this,Home.class);
//            startActivity(i);
//            sh=getSharedPreferences("My data", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor=sh.edit();
//            editor.putString("ID",s);
//            editor.commit();

        }
    }




}
