package mark.nobleza.uhack4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kosalgeek.android.json.JsonConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginAct extends AppCompatActivity {

    EditText etUser, etPass;
    String user,pass;

    ArrayList<myUser> myUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUser = (EditText)findViewById(R.id.Username);
        etPass = (EditText)findViewById(R.id.Password);



    }

    public void Login(View view) {

        final Intent i = new Intent(this, MainAct.class);

        user = etUser.getText().toString();
        pass = etPass.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://172.20.10.4/myDatabase/login.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.contains("NO_DATA")) {
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    myUsers = new JsonConverter<myUser>().toArrayList(response, myUser.class);
                    for(myUser users : myUsers){
                        globalVar.gAccID = users.getAccID();
                        globalVar.gTotalCash = users.getUbAccAmount();
                        globalVar.gUbAccIDMain = users.getUbAccID();
                        globalVar.gAtmNum = users.getAccNum();
                    }
                    Toast.makeText(getApplicationContext(), "" + globalVar.gAccID, Toast.LENGTH_SHORT).show();
                    startActivity(i);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Unable", Toast.LENGTH_SHORT).show();
                }
            }

            }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"E",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", user);
                params.put("password", pass);
                return  params;
            }
        };

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }

    public void Register(View view) {
        Intent i = new Intent(this, RegisterAct.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }


}
