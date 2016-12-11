package mark.nobleza.uhack4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kosalgeek.android.json.JsonConverter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DataLog extends AppCompatActivity {

    ArrayList<myDataLog> myDataLogs = null;
    ArrayAdapter<myDataLog> myDataLogArrayAdapter;
    ListView myListView;

    TextView tvTotal;
    EditText etAdd;
    double additional;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_log);

        myListView = (ListView)findViewById(R.id.myLV);
        tvTotal = (TextView)findViewById(R.id.Total);
        etAdd = (EditText)findViewById(R.id.etAdditional);

        newData();

    }


    public void newData(){
//        Toast.makeText(getApplicationContext(),"" + ID, Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://172.20.10.4/myDatabase/piggyTransaction.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.contains("no_data")){
                    myDataLogs = new JsonConverter<myDataLog>().toArrayList(response, myDataLog.class);
                    myDataLogArrayAdapter = new myDataLogAdapter(getApplicationContext(),myDataLogs);
                    myListView.setAdapter(myDataLogArrayAdapter);
                    total();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("UbAccID", String.valueOf(globalVar.gUBAccID));
                params.put("UbAccAmount", String.valueOf(additional));
                return params;
            }
        };

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    public void total(){
        double total = 0;

        for (myDataLog mydata : myDataLogs){
            total = total + mydata.getAccAmount();

        }tvTotal.setText("Total: P " + String.format("%.4f",total));
    }

    public void Add(View view) {
        additional = Double.parseDouble(etAdd.getText().toString());


        if (additional > globalVar.gTotalCash) {
            Toast.makeText(getApplicationContext(), "Cannot add greater than your balance!", Toast.LENGTH_SHORT).show();
        }
        else {
            updateTotal();
            Toast.makeText(getApplicationContext()," "+ additional+ " " + globalVar.gUBAccID,Toast.LENGTH_SHORT ).show();
          StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://172.20.10.4/myDatabase/insertTxnPiggy.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (!response.contains("failed")) {
                        Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("UbAccID", String.valueOf(globalVar.gUBAccID));
                    params.put("UbAccAmount", String.valueOf(additional));
                    params.put("Date", getDateTime());
                    return params;
                }
            };
            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

        }
        newData();
        Toast.makeText(getApplicationContext(),"Added Successfully",Toast.LENGTH_SHORT).show();
        etAdd.getText().clear();
    }

    private String getDateTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void updateTotal(){
        globalVar.gTotalCash = globalVar.gTotalCash - additional;

        Toast.makeText(getApplicationContext(), "" + globalVar.gTotalCash, Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://172.20.10.4/myDatabase/updateMainTotal.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.contains("no_data")) {
                    Toast.makeText(getApplicationContext(), "Total amount Updated", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("totalAmount", String.valueOf(globalVar.gTotalCash));
                params.put("UbAccID", String.valueOf(globalVar.gUbAccIDMain));
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent();
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        super.onBackPressed();
    }
}
