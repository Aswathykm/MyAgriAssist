package com.example.aswathy.myagriassist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OrderProduct extends AppCompatActivity implements View.OnClickListener {
    EditText qty,price,due;
    Button order;
    String p;
    SharedPreferences sh;
    String pid,pamt,pqty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_product);
        qty=(EditText)findViewById(R.id.ordqty);
        price=(EditText)findViewById(R.id.ordprice);
        due =(EditText)findViewById(R.id.duedate);
        order=(Button)findViewById(R.id.order);
        order.setOnClickListener(this);

            }

    @Override
    public void onClick(View view) {
        String productqtty = qty.getText().toString();
        sh=getSharedPreferences("buy",MODE_PRIVATE);
        pid=sh.getString("pid","");
        pamt=sh.getString("price","");
        pqty=sh.getString("qty","");


      String productprice = price.getText().toString();

        String duedate = due.getText().toString();

        SharedPreferences sh=getSharedPreferences("My data", Context.MODE_PRIVATE);
        String buyer_id= sh.getString("ID",null);

        insorder ord= new insorder();
         ord.execute(buyer_id,pid,productqtty,productprice,duedate);
        Intent i= new Intent(OrderProduct.this,Payment.class);
        startActivity(i);


    }

    private class insorder extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... strings) {
            WebServiceCaller web = new WebServiceCaller();
            web.setSoapObject("Order");
            web.addProperty("buyer_id", strings[0]);
            web.addProperty("product_id", strings[1]);
            web.addProperty("qty", strings[2]);
            web.addProperty("price", strings[3]);
            web.addProperty("due", strings[4]);

            web.callWebService();
            return web.getResponse();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Order","Your Order Placed SuccessFully"+s);
            Toast.makeText(OrderProduct.this,"Your Order Placed SuccessFully",Toast.LENGTH_LONG).show();
        }
    }
}
