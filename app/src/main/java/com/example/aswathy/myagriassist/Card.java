package com.example.aswathy.myagriassist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Card extends AppCompatActivity implements View.OnClickListener {
    EditText cardname,cardnum,exipry;
    TextView amt;
    Button cbill;
    String type;
    SharedPreferences sh;
    String pid,pamt,pqty;
    String uid;
    String phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        cardname=(EditText)findViewById(R.id.cdnm);
        cardnum=(EditText)findViewById(R.id.cdnmbr);
        exipry=(EditText)findViewById(R.id.cddt);
        amt=(TextView) findViewById(R.id.cdamt);
        cbill=(Button)findViewById(R.id.btnbill);
        cbill.setOnClickListener(this);

        sh=getSharedPreferences("buy",MODE_PRIVATE);
        pid=sh.getString("pid","");
        pamt=sh.getString("price","");
        pqty=sh.getString("qty","");

        amt.setText(pamt+"");
        Intent j=getIntent();
        type=j.getStringExtra("payment");
        sh=getSharedPreferences("My data", Context.MODE_PRIVATE);
        uid=sh.getString("ID","");
       // Toast.makeText(Card.this,"id"+uid+", amt:"+pamt+", type:"+type+", qmty:"+pqty,Toast.LENGTH_LONG).show();

        getnumbr gn=new getnumbr();
        gn.execute(uid);


    }

    @Override
    public void onClick(View view) {
        Toast.makeText(Card.this,"Transaction Successful",Toast.LENGTH_LONG).show();
        selectpay sp=new selectpay();
        sp.execute(type,uid,pid,pamt);

    }

    private class selectpay extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... strings) {
            WebServiceCaller wb = new WebServiceCaller();
            wb.setSoapObject("Payment");
            wb.addProperty("type", strings[0]);
            wb.addProperty("buyer_id", strings[1]);
            wb.addProperty("product_id", strings[2]);
            wb.addProperty("amount", strings[3]);
            wb.callWebService();
            return wb.getResponse();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(Card.this,""+s,Toast.LENGTH_LONG).show();
            if(s.equals("Sucess")){

                SmsManager smsManager=SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNumber,null,"Dear Customer,Your Transaction is successfull. Amount Rs. "+pamt ,null,null);
            }

        }
    }

    private class getnumbr  extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... strings) {
            WebServiceCaller wb = new WebServiceCaller();
            wb.setSoapObject("GetNum");
            wb.addProperty("uid", strings[0]);
            wb.callWebService();
            return wb.getResponse();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(Card.this, "Number"+s, Toast.LENGTH_SHORT).show();
            phoneNumber=s.trim();
        }
    }
}
