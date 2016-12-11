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

import java.util.HashMap;
import java.util.Map;

public class RegisterAct extends AppCompatActivity {

    EditText cardNum, atmPin, pUser, etEmail;
    String cNum, pinATM, userP, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        cardNum = (EditText)findViewById(R.id.CardNum);
        atmPin = (EditText)findViewById(R.id.ATMPin);
        pUser = (EditText)findViewById(R.id.PUser);
        etEmail = (EditText)findViewById(R.id.Email);

    }

    public void Confirm(View view) {

        final Intent i = new Intent(this, LoginAct.class);

        cNum = cardNum.getText().toString();
        pinATM = atmPin.getText().toString();
        userP = pUser.getText().toString();
        email = etEmail.getText().toString();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://172.20.10.4/myDatabase/registration.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("Registered!")){
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), "Registration Successful" , Toast.LENGTH_SHORT).show();
                }
                else if(response.contains("Failed")){
                    Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Account Existed" , Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "DB error", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("cardNum", cNum);
                params.put("atmPIN", pinATM);
                params.put("prefUser", userP);
                params.put("email", email);
                return params;
            }
        };

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
