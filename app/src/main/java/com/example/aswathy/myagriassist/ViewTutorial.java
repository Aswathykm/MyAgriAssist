package com.example.aswathy.myagriassist;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewTutorial extends AppCompatActivity {
    RecyclerView rvt;
    List<Tutorial_View_Bean> tvb;
    Tutorial_View_Adapter tAdapter;
    //String Pname[]=new String[100];
    String Timg[]=new String[100];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tutorial);
        rvt= (RecyclerView) findViewById(R.id.rvtutorial);
        tvb = new ArrayList<>();


       tut t=new tut();
        t.execute();

    }

    private class tut extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {
            WebServiceCaller wc=new WebServiceCaller();
            wc.setSoapObject("ViewTutorial");
            wc.callWebService();
            return wc.getResponse();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {

                JSONArray ja = new JSONArray(s);
                JSONObject obj;
                Tutorial_View_Bean TVB;

                for (int i = 0; i < ja.length(); i++) {
                    obj = ja.getJSONObject(i);
                    TVB = new Tutorial_View_Bean();


                    Timg[i] = obj.getString("tut_file");



                    // Log.d("LOGGG",PrName[i]+":"+PrRate+":"+PrDesc);

                    TVB.setTimg(Timg[i]);

                    tvb.add(TVB);
                }
                tAdapter = new Tutorial_View_Adapter(tvb, getApplicationContext());
                rvt.setLayoutManager(new LinearLayoutManager(ViewTutorial.this));
                rvt.setAdapter(tAdapter);

            } catch (Exception ex) {

            }
        }
    }
}
