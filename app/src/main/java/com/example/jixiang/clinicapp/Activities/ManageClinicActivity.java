package com.example.jixiang.clinicapp.Activities;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jixiang.clinicapp.Models.RouteItem;
import com.example.jixiang.clinicapp.R;
import com.example.jixiang.clinicapp.utils.ValueAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ManageClinicActivity extends ListActivity {

    private ProgressDialog pDialog;

    EditText inputSearch;

    ListAdapter adapter;

    ValueAdapter v_adapter;

    List<RouteItem> result;

    List<RouteItem> filteredResult;

    public static final String TAG_CLINICID = "id";

    private LinearLayout myLInearLayout;

    Button valueB;
    Button manageB;

    String url;
    String clinic_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_clinic);

        ListView lv = this.getListView();
        inputSearch = (EditText) findViewById(R.id.inputSearch);

        AdapterView.OnItemClickListener oc = setOnItemListeners();
        lv.setOnItemClickListener(oc);

        valueB = new Button(ManageClinicActivity.this);

        manageB = (Button) findViewById(R.id.manage_add);
        manageB.setOnClickListener(buttonListener(2));

        String server_url = getResources().getString(R.string.serverUrl);
        url = server_url + getResources().getString(R.string.serverReadPhpFileName);
        new ReadClinicData().execute(url);
    }



    private AdapterView.OnItemClickListener setOnItemListeners() {

        AdapterView.OnItemClickListener oc = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView temp =  (TextView)view.findViewById(R.id.list_id);
                String clinicId = temp.getText().toString();
                Intent in = new Intent(getApplicationContext(),
                        SpecificClinicActivity.class);
                in.putExtra(TAG_CLINICID, clinicId);
                startActivityForResult(in, 100);
            }
        };
        return oc;
    }

    private TextWatcher setAddTextChangedListeners() {

        TextWatcher tw = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Log.i("Text", s.toString());
                StringBuilder sbText = new StringBuilder(s);
                String text = sbText.toString();

                filteredResult = new ArrayList<RouteItem>();
                Iterator<RouteItem> iter = result.iterator();
                while (iter.hasNext()) {
                    RouteItem i = iter.next();
                    if (i.getEstate().contains(text.toLowerCase()) ||
                            i.getEstate().contains(text.toUpperCase())) {
                        filteredResult.add(i);
                    }
                }
                if (text == "") {
                    adapter = new SimpleAdapter(
                            ManageClinicActivity.this, result,
                            R.layout.clinic_list, new String[]{"address1",
                            "aviva_code", "estate"},
                            new int[]{R.id.address1, R.id.aviva_code, R.id.estate});
                    setListAdapter(adapter);
                } else {
                    adapter = new SimpleAdapter(
                            ManageClinicActivity.this, filteredResult,
                            R.layout.clinic_list, new String[]{"address1",
                            "aviva_code", "estate"},
                            new int[]{R.id.address1, R.id.aviva_code, R.id.estate});
                    setListAdapter(adapter);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        return tw;

    }

    private View.OnClickListener buttonListener(int type){

        View.OnClickListener ocl = null;
        if (type == 1) {
             ocl = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new ReadClinicData().execute(url);
                    myLInearLayout.removeView(valueB);
                }
            };
        }else{
            ocl = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent in = new Intent(getApplicationContext(),
                            AddClinicActivity.class);
                    startActivityForResult(in, 100);
                }
            };

        }
        return ocl;
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

    class ReadClinicData extends AsyncTask<String, String, List<RouteItem>> {

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected List<RouteItem> doInBackground(String... params) {
            return RouteItem.jread(params[0]);
        }

        /**
         * Runs on the UI thread before {@link #doInBackground}.
         *
         * @see #onPostExecute
         * @see #doInBackground
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ManageClinicActivity.this);
            pDialog.setMessage("Loading Data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p/>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param result The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(List<RouteItem> result) {
            super.onPostExecute(result);
            pDialog.dismiss();

            if (result.size() == 0) {
                Toast.makeText(getApplicationContext(), "Internet is not working or server is not available", Toast.LENGTH_LONG).show();
                myLInearLayout =(LinearLayout) findViewById(R.id.linearLayout1);
                valueB.setText("Reload the data");
                valueB.setId(View.generateViewId());
                myLInearLayout.addView(valueB);
                View.OnClickListener oc = buttonListener(1);
                valueB.setOnClickListener(oc);

            } else {

                TextWatcher tw = setAddTextChangedListeners();
                inputSearch.addTextChangedListener(tw);

                ManageClinicActivity.this.result = result;
                Log.i("fet2", result.get(0).get("address1"));
                adapter = new SimpleAdapter(
                        ManageClinicActivity.this, result,
                        R.layout.clinic_list, new String[]{"id","address1",
                        "aviva_code", "estate"},
                        new int[]{R.id.list_id,R.id.address1, R.id.aviva_code, R.id.estate});
                setListAdapter(adapter);
            }
        }

    }
}
