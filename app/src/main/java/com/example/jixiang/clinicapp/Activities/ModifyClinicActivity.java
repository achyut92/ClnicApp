package com.example.jixiang.clinicapp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jixiang.clinicapp.Models.RouteItem;
import com.example.jixiang.clinicapp.R;
import com.example.jixiang.clinicapp.utils.HttpHelper;


public class ModifyClinicActivity extends ActionBarActivity {



    private ProgressDialog pDialog;

    EditText edt_address_1;
    EditText edt_address_2;
    EditText edt_clinic;

    Button btn_submit;
    Button btn_back;

    String url;
    String clinic_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_clinic);
        Intent intent = getIntent();
        clinic_id = intent.getStringExtra(ManageClinicActivity.TAG_CLINICID);


        initGUI();

        btn_submit.setOnClickListener(buttonListener(1));
        btn_back.setOnClickListener(buttonListener(2));

        String server_url = getResources().getString(R.string.serverUrl);
        url = server_url + getResources().getString(R.string.serverModifyPhpFileName);


    }

    private void initGUI() {
        edt_address_1 = (EditText) findViewById(R.id.modify_address_1);
        edt_address_2 = (EditText) findViewById(R.id.modify_address_2);
        edt_clinic = (EditText) findViewById(R.id.modify_clinic);

        btn_submit = (Button) findViewById(R.id.modify_submit);
        btn_back = (Button) findViewById(R.id.modify_back);
    }


    private View.OnClickListener buttonListener(int type) {

        View.OnClickListener ocl = null;
        if (type == 1) {
            ocl = new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    new ModifyClinic().execute(url);

                }
            };
        }else{
            ocl = new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent in = new Intent(getApplicationContext(),
                            MainActivity.class);
                    startActivityForResult(in, 100);
                }
            };
        }
        return ocl;
    }


    class ModifyClinic extends AsyncTask<String, String, String> {

        String address_1;
        String address_2;
        String clinic;
        RouteItem item;
        String url_backend;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ModifyClinicActivity.this);
            pDialog.setMessage("Processing the data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

            url_backend = url;
            address_1 = edt_address_1.getText().toString();
            address_2 = edt_address_2.getText().toString();
            clinic = edt_clinic.getText().toString();
            item = new RouteItem(clinic_id,address_1, address_2, clinic);
        }

        @Override
        protected String doInBackground(String... params) {
            String result = HttpHelper.modifyData(item, params[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result.equals("success")) {
                Toast.makeText(getApplicationContext(), "Data Modified successfully", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(), "Data Modified Failed", Toast.LENGTH_LONG).show();

            }

            pDialog.dismiss();

        }


    }
}
