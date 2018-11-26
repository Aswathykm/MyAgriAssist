package com.example.aswathy.myagriassist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static android.widget.Toast.LENGTH_SHORT;

public class LocationDetails extends AppCompatActivity implements WeatherResult {
    Button bt_submit;
    String cityname = null;
    String address = "";
    String min, max;
    Button bt_back;
    double longitude,temp,maxTemp,minTemp,humidity,pressure,windspeed,winddegree;
    String description;
    double latitude;
    TextView city,add,tempe,minT,maxT,hum,pre,speed,degree,lat,lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_details);
        bt_submit=(Button)findViewById(R.id.bt_submit) ;
        bt_back=(Button)findViewById(R.id.bt_back);
        city=(TextView) findViewById(R.id.etcity);
        add=(TextView) findViewById(R.id.etadd);
        lon=(TextView) findViewById(R.id.etlon);
        lat=(TextView) findViewById(R.id.etlat);
        tempe=(TextView) findViewById(R.id.ettemp);
        minT=(TextView) findViewById(R.id.etmin);
        maxT=(TextView) findViewById(R.id.etmax);
        pre=(TextView) findViewById(R.id.etpre);
        hum=(TextView) findViewById(R.id.ethum);
        speed=(TextView) findViewById(R.id.etspeed);
        degree=(TextView) findViewById(R.id.etdegree);
        //weathr=(EditText) findViewById(R.id.etweather);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i=new Intent(LocationDetails.this,FarmerHome.class);
               startActivity(i);
                SharedPreferences sh = getSharedPreferences("USER", MODE_PRIVATE);

                sh.getString("city", cityname);
                sh.getString("address", address);
                sh.getString("latitude", String.valueOf(latitude));
                sh.getString("longitude", String.valueOf(longitude));
                SharedPreferences wet=getSharedPreferences("Weather Deatils",MODE_PRIVATE);

                wet.getString("temp",temp+"");
                wet.getString("minTemp",minTemp+"");
                wet.getString("maxTemp",maxTemp+"");
                wet.getString("humidity",humidity+"");
                wet.getString("pressure",pressure+"");
                wet.getString("windspeed",windspeed+"");
                wet.getString("winddegree",winddegree+"");

                insweather wth=new insweather();
                wth.execute(cityname+"",description+"",humidity+"",pressure+"",temp+"");
                Toast.makeText(LocationDetails.this, "webbbbbb", Toast.LENGTH_SHORT).show();


            }
        });



        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GpsTracker gps = new GpsTracker(LocationDetails.this);
                if (gps.canGetLocation()) {
                    longitude = gps.getLongitude();
                    latitude = gps.getLatitude();


                    // to get city name


                    Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
                    List<Address> adresses;
                    try {
                        adresses = gcd.getFromLocation(latitude, longitude, 1);
                        if (adresses.size() > 0) {
                            System.out.println(adresses.get(0).getLocality());
                            address = adresses.get(0).getAddressLine(0);
                            Toast.makeText(LocationDetails.this, address, LENGTH_SHORT).show();
                            cityname = adresses.get(0).getLocality();
                        }

                    } catch (IOException e) {


                        Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude) + "\nCity:" + cityname, LENGTH_SHORT).show();
                    }
                } else {

                    gps.showSettingsAlert();
                }


                String sh_name = "USER";
                SharedPreferences sh = getSharedPreferences(sh_name, MODE_PRIVATE);
                SharedPreferences.Editor editor = sh.edit();
                editor.putString("city", cityname);
                editor.putString("address", address);
                editor.putString("latitude", String.valueOf(latitude));
                editor.putString("longitude", String.valueOf(longitude));
                editor.commit();

                 city.setText(cityname);
                 add.setText(address);
                 lat.setText(String.valueOf(latitude));
                 lon.setText(String.valueOf(longitude));

                String url = "http://api.openweathermap.org/data/2.5/weather?q=" +cityname+ "&appid=09a25ccabeeb8cb6fe1b575d5bcf84b4";
                Log.d("api weather",url);
                WeatherApiCallerTask task = new WeatherApiCallerTask(url, LocationDetails.this);
                task.execute();

            }
        });
    }

    @Override
    public void onWeatherResult(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject jsonObj = jsonObject.getJSONObject("main");
             temp = jsonObj.getDouble("temp");
            maxTemp = jsonObj.getDouble("temp_max");
            minTemp = jsonObj.getDouble("temp_min");

            humidity=jsonObj.getDouble("humidity");
           pressure=jsonObj.getDouble("pressure");

            Log.wtf("killer", "temp" + temp);
            Log.wtf("killer", "min" + minTemp);
            Log.wtf("killer", "max" + maxTemp);

            minT.setText("min : " + minTemp);
            tempe.setText("temp : " + temp);
            maxT.setText("max : " + maxTemp);

            Log.d("killer", "humidity" + humidity);
            Log.d("killer", "pressure" + pressure);

            hum.setText("humidity : " + humidity);
            pre.setText("pressure : " + pressure);

            JSONObject jsonObj1 = jsonObject.getJSONObject("wind");
              windspeed=jsonObj1.getDouble("speed");
              winddegree=jsonObj1.getDouble("deg");
            Log.d("killer", "wind" + windspeed);
            Log.d("killer", "deg" + winddegree);

            speed.setText("wind : " + windspeed);
            degree.setText("deg : " + winddegree);

            JSONObject jsonObj2 = jsonObject.getJSONObject("weather");
            JSONArray j=new JSONArray();


                JSONObject forecastDate = new JSONObject(result);
                JSONArray ForecastData = forecastDate.getJSONArray("weather");
                for(int i =0 ; i< ForecastData.length();i++) {
                    Toast.makeText(this, "inside", Toast.LENGTH_SHORT).show();
                    JSONObject weather = ForecastData.getJSONObject(i);
                    JSONObject Description = weather.getJSONArray("weather").getJSONObject(1);
                    description = Description.getString("main");

                 // weathr.setText("Description : " + description);
                    Log.d("killer", "Description" + description);
                }



            description=jsonObj2.getString("description");
//

            SharedPreferences wet=getSharedPreferences("Weather Deatils",MODE_PRIVATE);
            SharedPreferences.Editor editor=wet.edit();
            editor.putString("temp",temp+"");
            editor.putString("minTemp",minTemp+"");
            editor.putString("maxTemp",maxTemp+"");
            editor.putString("humidity",humidity+"");
            editor.putString("pressure",pressure+"");
            editor.putString("windspeed",windspeed+"");
            editor.putString("winddegree",winddegree+"");
            editor.putString("description",description);

            editor.commit();
           // Log.d("temp", String.valueOf(+temp));
            /*tempe.setText( temp+"");
            minT.setText( minTemp+"");
            maxT.setText(maxTemp+"");
            pre.setText( pressure+"");
            hum.setText( humidity+"");
           speed.setText( windspeed+"");
            degree.setText( winddegree+"");
           weathr.setText( description+"");

*/
            /*insweather wth=new insweather();
            wth.execute(city+"",description+"",humidity+"",pressure+"",temp+"");
            Toast.makeText(this, "webbbbb", LENGTH_SHORT).show();
*/
       } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private class insweather  extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... strings) {
            WebServiceCaller w=new WebServiceCaller();
            w.setSoapObject("InsertWeather");
            w.addProperty("wloc", strings[0]);
            w.addProperty("wcondition", strings[1]);
            w.addProperty("humidity", strings[2]);
            w.addProperty("pressure", strings[3]);

            w.addProperty("temp", strings[4]);

           w.callWebService();
            return w.getResponse();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(LocationDetails.this, ""+s, LENGTH_SHORT).show();
        }
    }
}
