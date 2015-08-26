package com.example.jixiang.clinicapp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.jixiang.clinicapp.R;
import com.example.jixiang.clinicapp.utils.HttpHelper;

public class SpecificClinicActivity extends AppCompatActivity {

    private ProgressDialog pDialog;

    Button btn_modify;
    Button btn_delete;

    String url;
    String clinic_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_clinic);

        Intent intent = getIntent();
        clinic_id = intent.getStringExtra(ManageClinicActivity.TAG_CLINICID);

        initGUI();
        btn_modify.setOnClickListener(buttonListener(1));
        btn_delete.setOnClickListener(buttonListener(2));

        String server_url = getResources().getString(R.string.serverUrl);
        url = server_url + getResources().getString(R.string.serverDeletePhpFileName);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 100) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

    private void initGUI(){

        btn_modify = (Button) findViewById(R.id.spec_modify);
        btn_delete = (Button) findViewById(R.id.spec_delete);

    }

    private View.OnClickListener buttonListener(int type) {

        View.OnClickListener ocl = null;
        if (type == 1) {
            ocl = new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    //new CreateNewClinic().execute(url);
                    Intent in = new Intent(getApplicationContext(),
                            ModifyClinicActivity.class);
                    startActivityForResult(in, 100);
                }
            };
        }else{
            ocl = new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                     new DeleteClinic().execute(clinic_id,url);
                    Intent in = new Intent(getApplicationContext(),
                            MainActivity.class);
                    startActivityForResult(in, 100);
                }
            };
        }
        return ocl;
    }

    class DeleteClinic extends AsyncTask<String,String,String>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SpecificClinicActivity.this);
            pDialog.setMessage("Deleting the data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = HttpHelper.deleteData(params[0],params[1]);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result.equals("success")) {
                Toast.makeText(getApplicationContext(), "Data deleted successfully", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(), "Data deleted Failed", Toast.LENGTH_LONG).show();

            }

            pDialog.dismiss();

        }
    }


}
