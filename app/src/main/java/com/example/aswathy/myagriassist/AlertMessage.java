package com.example.aswathy.myagriassist;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class AlertMessage extends AppCompatActivity implements View.OnClickListener {
    String h;
    Button al;
    String des;
    String cn[], fn[], cd[], fd[],cdis[],w[],ph[],Cropname[],fert[];
    String humidity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_message);
        selcrop sc=new selcrop();
        sc.execute();
        selphone sp=new selphone();
        sp.execute();
        al=(Button) findViewById( R.id.alert);
        al.setOnClickListener(this);
        selweather we=new selweather();
        we.execute();


    }

    @Override
    public void onClick(View view) {


        for( int i=0;i<w.length;i++)
        {
            if(w[i].equals("rain"))
            {
                Cropname[i]=cn[i];
                fert[i]=fn[i];
            }



          //  SmsManager smsManager = SmsManager.getDefault();
            //smsManager.sendTextMessage(ph[i], null, "Hi ", null, null);



        }

        long humi=Long.parseLong(humidity);
        if(humi>=70)
        {
            for( int i=0;i<ph.length;i++)
            {




                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(ph[i], null, "Crop Name"+Cropname.toString()+"Fertilizer"+fert.toString(), null, null);



            }

        }
        else
        {
            for( int i=0;i<ph.length;i++)
            {




                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(ph[i], null, "Crop Name"+Cropname.toString()+"Fertilizer"+fert.toString(), null, null);



            }
        }

    }

    private class selcrop extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            WebServiceCaller web = new WebServiceCaller();
            web.setSoapObject("Crop");

              web.callWebService();
            return web.getResponse();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(AlertMessage.this, ""+s, Toast.LENGTH_SHORT).show();
            try {

                JSONArray ja = new JSONArray(s);
                JSONObject obj;
                 cn = new String[ja.length()];
                 fn = new String[ja.length()];
                 cd = new String[ja.length()];
                 fd = new String[ja.length()];
                 cdis= new String[ja.length()];
                w=new String[ja.length()];


                for (int i = 0; i < ja.length(); i++) {
                    obj = ja.getJSONObject(i);


                    cn[i] = obj.getString("crop_name");
                   fn[i] = obj.getString("fert_name");
                    cd[i] = obj.getString("crop_desc");
                    fd[i] = obj.getString("fert_dscrpn");
                    cdis[i] = obj.getString("crop_diseases");
                     w[i]=obj.getString("crop_weather");


                    Log.d("crop",cn[i]);
                    Log.d("crop",fn[i]);
                    Log.d("crop",cd[i]);
                    Log.d("crop",fd[i]);
                    Log.d("crop",cdis[i]);
                    Log.d("crop",w[i]);


                }

            } catch (Exception ex) {

            }
        }
    }

    private class selphone  extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... strings) {
            WebServiceCaller web = new WebServiceCaller();
            web.setSoapObject("AlertFarmer");

            web.callWebService();
            return web.getResponse();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {

                JSONArray ja = new JSONArray(s);
                JSONObject obj;

                 ph = new String[ja.length()];


                for (int i = 0; i < ja.length(); i++) {
                    obj = ja.getJSONObject(i);



                    ph[i] = obj.getString("farmer_contact");



                    Log.d("PhonDet",ph[i]);


                    }





            } catch (Exception ex) {

            }


        }
    }

    private class selweather extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... strings) {
            WebServiceCaller web = new WebServiceCaller();
            web.setSoapObject("ViewWeather");

            web.callWebService();
            return web.getResponse();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            humidity=s;

        }
    }
}
