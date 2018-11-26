package com.example.aswathy.myagriassist;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewProduct extends AppCompatActivity {

    RecyclerView rv;
    List<Product_View_Bean> pvb;
    Product_View_Adapter pAdapter;
    String Pname[]=new String[100];
    String Pimg[]=new String[100];
    String Pdesc[]=new String[100];
    String Pqty[]=new String[100];
    String Pexp[]=new String[100];
    String Pman[]=new String[100];
    String Prate[]=new String[100];
    String Pid[]=new String[100];

    //SharedPreferences sh,shp,shc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);
        rv = (RecyclerView) findViewById(R.id.rvpdt);
        pvb = new ArrayList<>();


        pdt p=new pdt();
        p.execute();

    }

    private class pdt extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            WebServiceCaller wc=new WebServiceCaller();
            wc.setSoapObject("ViewProducts");
            wc.callWebService();
            return wc.getResponse();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {

                JSONArray ja = new JSONArray(s);
                JSONObject obj;
                Product_View_Bean PVB;

                for (int i = 0; i < ja.length(); i++) {
                    obj = ja.getJSONObject(i);
                    PVB = new Product_View_Bean();

                    Pname[i] = obj.getString("product_name");
                    Prate[i] = obj.getString("product_price");
                    Pdesc[i] = obj.getString("product_description");
                    Pid[i] = obj.getString("product_id");
                    Pimg[i] = obj.getString("product_image");
                    Pexp[i]=obj.getString("product_exp");
                    Pman[i]=obj.getString("product_man");
                    Pqty[i]=obj.getString("product_qty");



                    // Log.d("LOGGG",PrName[i]+":"+PrRate+":"+PrDesc);

                    PVB.setPname(Pname[i]);
                    PVB.setPrate(Prate[i]);
                    PVB.setPdesc(Pdesc[i]);
                    PVB.setPid(Pid[i]);
                    PVB.setPimg(Pimg[i]);
                    PVB.setPqty(Pqty[i]);
                    PVB.setEdate(Pexp[i]);
                    PVB.setMdate(Pman[i]);

                    pvb.add(PVB);
                }
                pAdapter = new Product_View_Adapter(pvb, getApplicationContext());
                rv.setLayoutManager(new LinearLayoutManager(ViewProduct.this));
                rv.setAdapter(pAdapter);

            } catch (Exception ex) {

            }
        }
    }

}

