package com.example.aswathy.myagriassist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Payment extends AppCompatActivity  {
 RadioGroup payment;
 RadioButton cash,card;
 String pay="";
 TextView tv_pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        payment=(RadioGroup)findViewById(R.id.rgpay);

        cash=(RadioButton)findViewById(R.id.rbcod);

        card=(RadioButton)findViewById(R.id.rbcd);
        tv_pay=(TextView)findViewById(R.id.tv_pay);


        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pay.equals("CASH ON DELIVERY"))
                {
                    Intent in=new Intent(Payment.this,CashOnDelivery.class);
                    in.putExtra("payment",pay);
                    startActivity(in);
                }
                else if (pay.equals("DEBIT/CREDIT CARD"))
                {
                    Intent in=new Intent(Payment.this,Card.class);
                    in.putExtra("payment",pay);
                    startActivity(in);
                }
            }
        });


        payment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rbcod) {
                    pay ="CASH ON DELIVERY";

                } else if(i==R.id.rbcd) {
                    pay= "DEBIT/CREDIT CARD";
                }
            }
        });

    }




}
