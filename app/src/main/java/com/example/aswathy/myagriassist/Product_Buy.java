package com.example.aswathy.myagriassist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Product_Buy extends AppCompatActivity implements View.OnClickListener {

    TextView name,desc,price,exp,man,qty;
    ImageView img;
   Button buy1,order;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__buy);
        name=(TextView)findViewById(R.id.tvname);
        desc=(TextView)findViewById(R.id.tvdesc);
        qty=(TextView)findViewById(R.id.tvqty);
        price=(TextView)findViewById(R.id.tvprice);
        exp=(TextView)findViewById(R.id.tvexp);
        man=(TextView)findViewById(R.id.tvmdate);
        img=(ImageView)findViewById(R.id.ivbuy);

        buy1=(Button) findViewById(R.id.btnbuy);
        buy1.setOnClickListener(this);

        order=(Button)findViewById(R.id.btnorder);
        order.setOnClickListener(this);

        Intent j=getIntent();

      name.setText (j.getStringExtra("name"));
       qty.setText(j.getStringExtra("qty"));
       price.setText(j.getStringExtra("price"));
         exp.setText( j.getStringExtra("exp"));
       man.setText(j.getStringExtra("man"));
       desc.setText(j.getStringExtra("desc"));
         id=j.getStringExtra("id");

        String url="http://192.168.43.244:8084/AgriAssist/Farmer/image/"+j.getStringExtra("Imagesrc");
       Log.d("UURRLL",url);
        Picasso.with(Product_Buy.this).load(url).into(img);


        SharedPreferences buy = getSharedPreferences("buy", MODE_PRIVATE);
        SharedPreferences.Editor editor = buy.edit();
        editor.putString("qty", j.getStringExtra("qty"));
        editor.putString("price",j.getStringExtra("price"));
        editor.putString("pid",id);
        editor.putString("pimg",url);

        editor.commit();


    }

    @Override
    public void onClick(View view) {

        if(view==buy1)
        {
            Intent by=new Intent(Product_Buy.this,Payment.class);

            startActivity(by);


        }
        if(view==order)
        {
            Intent ord=new Intent(Product_Buy.this,OrderProduct.class);

            startActivity(ord);
        }

    }
}
