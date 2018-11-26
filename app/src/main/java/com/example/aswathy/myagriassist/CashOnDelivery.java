package com.example.aswathy.myagriassist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CashOnDelivery extends AppCompatActivity implements View.OnClickListener {
    Button bill;
    TextView amt;
    String type;

    SharedPreferences sh;
    String pid,pamt,pqty;
    String uid;
    String phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_on_delivery);

        bill=(Button)findViewById(R.id.btnbillc);
        bill.setOnClickListener(this);
        amt=(TextView) findViewById(R.id.codamt);

        sh=getSharedPreferences("buy",MODE_PRIVATE);
        pid=sh.getString("pid","");
        pamt=sh.getString("price","");
        pqty=sh.getString("qty","");

        amt.setText(pamt+"");
        Intent j=getIntent();
        type=j.getStringExtra("payment");
        sh=getSharedPreferences("My data", Context.MODE_PRIVATE);
        uid=sh.getString("ID","");
//        Toast.makeText(CashOnDelivery.this,"id"+uid+", amt:"+pamt+", type:"+type+", qmty:"+pqty,Toast.LENGTH_LONG).show();



        getnum gn=new getnum();
        gn.execute(uid);

    }

    @Override
    public void onClick(View view) {
        Toast.makeText(CashOnDelivery.this,"Transaction Successful",Toast.LENGTH_LONG).show();

        selpay sp=new selpay();
        sp.execute(type,uid,pid,pamt);

    }

    private class selpay extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            WebServiceCaller web = new WebServiceCaller();
            web.setSoapObject("Payment");
            web.addProperty("type", strings[0]);
            web.addProperty("buyer_id", strings[1]);
            web.addProperty("product_id", strings[2]);
            web.addProperty("amount", strings[3]);
            web.callWebService();
            return web.getResponse();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(CashOnDelivery.this,""+s,Toast.LENGTH_LONG).show();
            if(s.equals("Sucess")){

                SmsManager smsManager=SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNumber,null,"Dear Customer,Your Transaction is successfull. Amount Rs. "+pamt ,null,null);
            }
        }
    }

    public class getnum extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            WebServiceCaller web = new WebServiceCaller();
            web.setSoapObject("GetNum");
            web.addProperty("uid", strings[0]);
            web.callWebService();
            return web.getResponse();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(CashOnDelivery.this, "Number"+s, Toast.LENGTH_SHORT).show();
           phoneNumber=s.trim();
        }
    }
}
