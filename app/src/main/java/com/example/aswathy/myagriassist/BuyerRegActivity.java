package com.example.aswathy.myagriassist;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

public class BuyerRegActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener, AdapterView.OnItemSelectedListener {
    EditText fname, lname, dob, contact, email, username, password, hname;
    Button reg, cancel, dofb;
    RadioGroup gender;
    RadioButton male, female;
    Spinner place, dist, state, quali;
    int mYear, mMonth, mDay;
    String gd;
    String places[] = null;
    String place_id[] = null;
    String dists[] = null;
    String dist_id[] = null;

    String states[] = null;
    String qualification[] = null;
    String state_id[] = null;
    String quali_id[] = null;
    String stat,dis,pla,qualif;

    ArrayAdapter adapter = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_reg);
        fname = (EditText) findViewById(R.id.etfname);
        lname = (EditText) findViewById(R.id.etlname);
        dob = (EditText) findViewById(R.id.etdate);
        gender = (RadioGroup) findViewById(R.id.rgender);
        contact = (EditText) findViewById(R.id.etphone);
        email = (EditText) findViewById(R.id.etemail);
        username = (EditText) findViewById(R.id.etuname);
        password = (EditText) findViewById(R.id.etpass);
        hname = (EditText) findViewById(R.id.ethn);
        place = (Spinner) findViewById(R.id.spplace);

        dist = (Spinner) findViewById(R.id.spdist);
        state = (Spinner) findViewById(R.id.spstate);
        quali = (Spinner) findViewById(R.id.spquali);
        reg = (Button) findViewById(R.id.btnreg);
        cancel = (Button) findViewById(R.id.btncancelb);
        dofb = (Button) findViewById(R.id.btndate);
        male = (RadioButton) findViewById(R.id.rbmale);
        female = (RadioButton) findViewById(R.id.rbfemale);

        reg.setOnClickListener(this);
        cancel.setOnClickListener(this);
        dofb.setOnClickListener(this);

        gender.setOnCheckedChangeListener(this);
        male.setOnCheckedChangeListener(this);
        female.setOnCheckedChangeListener(this);

        dist.setOnItemSelectedListener(this);
        state.setOnItemSelectedListener(this);
        place.setOnItemSelectedListener(this);
        quali.setOnItemSelectedListener(this);

        selstate ss = new selstate();
        ss.execute();
        selquali sq=new selquali();
        sq.execute();



    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

        if (i == R.id.rbmale) {
            gd = male.getText().toString();

        } else {
            gd = female.getText().toString();
        }


    }

    @Override
    public void onClick(View view) {
        if (view == dofb) {

            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {

                    dob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                }
            }, mYear, mMonth, mDay);
            datePickerDialog.show();

        } else if (view.getId() == R.id.btnreg) {
            String firstname = fname.getText().toString();
            String lastname = lname.getText().toString();
            String contacts = contact.getText().toString();
            String mail = email.getText().toString();
            String uname = username.getText().toString();
            String pass = password.getText().toString();
            String dobirth = dob.getText().toString();
            String house = hname.getText().toString();

//            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
            //  Toast.makeText(FarmerRegActivity.this, "REGISTER SUCCESSFULLY", Toast.LENGTH_LONG).show();
            insertData id= new insertData();
            id.execute(firstname,lastname,gd,dobirth,house,contacts,mail,uname,pass,pla,qualif);

        } else {
            fname.setText("");
            lname.setText("");
            contact.setText("");
            email.setText("");
            username.setText("");
            hname.setText("");
            //gender.clearCheck();
            dob.setText("");

        }


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if (adapterView == state) {

            int a = state.getSelectedItemPosition();
            stat = state_id[state.getSelectedItemPosition()];
            seldist sd = new seldist();
            sd.execute(stat);

            //  Toast.makeText(this, "position:" + a, Toast.LENGTH_SHORT).show();
            stat = state.getItemAtPosition(a).toString();
            //  Toast.makeText(FarmerRegActivity.this, "text" + "item" + stat, Toast.LENGTH_LONG).show();
        }

        if (adapterView == dist) {
            dis = dist_id[dist.getSelectedItemPosition()];
            // Toast.makeText(this, "" + dis, Toast.LENGTH_LONG).show();

            selplace sp = new selplace();
            sp.execute(dis);
//            pla = place_id[place.getSelectedItemPosition()];
//            pla = place.getSelectedItem().toString();

        }
        if (adapterView == place) {
            pla = place_id[place.getSelectedItemPosition()];
//            pla = adapterView.getItemAtPosition(i).toString();
            // Toast.makeText(FarmerRegActivity.this, "" + pla, Toast.LENGTH_SHORT).show();

        }
        if (adapterView == quali) {
            qualif = quali_id[quali.getSelectedItemPosition()];


            Toast.makeText(BuyerRegActivity.this, "text" + "item" + qualif, Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private class selplace extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            WebServiceCaller web = new WebServiceCaller();
            web.setSoapObject("place");
            web.addProperty("district_id", strings[0]);
            web.callWebService();
            return web.getResponse();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // Toast.makeText(FarmerRegActivity.this, "NO place" + s, Toast.LENGTH_LONG).show();
            JSONArray j;
            try {
                j = new JSONArray(s);
                JSONObject obj;
                places = new String[j.length()];
                place_id = new String[j.length()];
                for (int i = 0; i < j.length(); i++) {
                    obj = j.getJSONObject(i);
                    place_id[i] = obj.getString("place_id");
                    places[i] = obj.getString("place_name");
                }
                adapter = new ArrayAdapter<String>(BuyerRegActivity.this, android.R.layout.simple_spinner_item, places);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                place.setAdapter(adapter);


            } catch (Exception e) {

            }
        }
    }

    private class selstate extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            WebServiceCaller web = new WebServiceCaller();
            web.setSoapObject("state");

            web.callWebService();
            return web.getResponse();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
           // Toast.makeText(BuyerRegActivity.this, "No Value" + s, Toast.LENGTH_LONG).show();
            JSONArray j;
            try {
                j = new JSONArray(s);
                JSONObject obj;
                states = new String[j.length()];
                state_id = new String[j.length()];
                for (int i = 0; i < j.length(); i++) {
                    obj = j.getJSONObject(i);
                    states[i] = obj.getString("state_name");
                    state_id[i] = obj.getString("state_id");
                }
                adapter = new ArrayAdapter<String>(BuyerRegActivity.this, android.R.layout.simple_spinner_item, states);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                state.setAdapter(adapter);

            } catch (Exception e) {

            }
        }
    }

    private class seldist extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            WebServiceCaller web = new WebServiceCaller();
            web.setSoapObject("district");
            web.addProperty("state_id", strings[0]);
            web.callWebService();
            return web.getResponse();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // Toast.makeText(FarmerRegActivity.this, " No Dist" + s, Toast.LENGTH_LONG).show();
            JSONArray j;
            try {
                j = new JSONArray(s);
                JSONObject obj;
                dists = new String[j.length()];
                dist_id = new String[j.length()];
                for (int i = 0; i < j.length(); i++) {
                    obj = j.getJSONObject(i);
                    dists[i] = obj.getString("district_name");
                    dist_id[i] = obj.getString("district_id");
                }
                adapter = new ArrayAdapter<String>(BuyerRegActivity.this, android.R.layout.simple_spinner_item, dists);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                dist.setAdapter(adapter);

            } catch (Exception e) {

            }

        }
    }

    private class insertData extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            WebServiceCaller web = new WebServiceCaller();
            web.setSoapObject("BuyerReg");
            web.addProperty("firstname", strings[0]);
            web.addProperty("lastname", strings[1]);
            web.addProperty("gender", strings[2]);
            web.addProperty("dob", strings[3]);

            web.addProperty("housename", strings[4]);
            web.addProperty("contact", strings[5]);
            web.addProperty("email", strings[6]);
            web.addProperty("username", strings[7]);
            web.addProperty("password", strings[8]);
            web.addProperty("place", strings[9]);
            web.addProperty("quali", strings[10]);
            web.callWebService();
            return web.getResponse();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(BuyerRegActivity.this,""+s,Toast.LENGTH_LONG).show();
            if (s.equals("Sucess"))
            {
                Intent i=new Intent(BuyerRegActivity.this,LoginActivity.class);
                startActivity(i);
                Toast.makeText(BuyerRegActivity.this,"Register Successful",Toast.LENGTH_LONG).show();
            }
        }
    }
    private class selquali extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            WebServiceCaller web = new WebServiceCaller();
            web.setSoapObject("qualification");

            web.callWebService();
            return web.getResponse();
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // Toast.makeText(FarmerRegActivity.this,"No QUali"+ s, Toast.LENGTH_LONG).show();
            JSONArray j;
            try {
                j = new JSONArray(s);
                JSONObject obj;
                qualification = new String[j.length()];
                quali_id = new String[j.length()];
                for (int i = 0; i < j.length(); i++) {
                    obj = j.getJSONObject(i);
                    qualification[i] = obj.getString("qualification_type");
                    quali_id[i] = obj.getString("qualification_id");
                }
                adapter = new ArrayAdapter<String>(BuyerRegActivity.this, android.R.layout.simple_spinner_item,qualification);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                quali.setAdapter(adapter);

            } catch (Exception e) {

            }



        }

    }
}



