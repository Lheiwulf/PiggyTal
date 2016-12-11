package mark.nobleza.uhack4;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import java.util.Locale;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class PiggyBank extends ListFragment {

    String TAG = this.getClass().getSimpleName();
    public static ArrayList<myPiggy> piggyItem;
    public myPiggyAdapter piggyAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://172.20.10.4/myDatabase/listOfPiggy.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.contains("no_data")) {
                    piggyItem = new JsonConverter<myPiggy>().toArrayList(response, myPiggy.class);
                    piggyAdapter = new myPiggyAdapter(getContext(), piggyItem);
                    setListAdapter(piggyAdapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "WTF", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("AccID", String.valueOf(globalVar.gAccID));
                return params;
            }
        };
        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);


    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        myPiggy selectedPiggy = piggyItem.get(position);
        Intent i = new Intent(getContext(), DataLog.class);
        globalVar.gUBAccID = selectedPiggy.getPiggyID();
        Toast.makeText(getContext(), "" + selectedPiggy.getPiggyID(), Toast.LENGTH_SHORT).show();
        startActivity(i);
    }

}
