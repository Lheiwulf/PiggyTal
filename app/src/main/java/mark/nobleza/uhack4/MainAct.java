package mark.nobleza.uhack4;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kosalgeek.android.json.JsonConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainAct extends AppCompatActivity implements AdapterView.OnItemClickListener {

    int id;

    FragmentManager fragmentManager;

    ArrayList<myDateValidation> myDateValidations;
    ListFragment fragment = null;

    DrawerLayout drawerLayout;
    ListView itemList;

    public ActionBarDrawerToggle actionBarDrawerToggle;

    String desc,date,newDate;

    AlertDialog.Builder builder,builder2;
    AlertDialog ad,ad2;
    EditText etDesc, etDate,etReDate;
    LinearLayout linearLayout, linearLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment = new PiggyBank();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.itemFrag,fragment).commit();

        Toolbar myToolbar = (Toolbar)findViewById(R.id.toolbar);


        DateCheck();

        drawerLayout = (DrawerLayout)findViewById(R.id.mainDrawer);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainAct.this, drawerLayout, myToolbar, R.string.drawer_open, R.string.drawer_close);

        linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        linearLayout2 = new LinearLayout(getApplicationContext());
        linearLayout2.setOrientation(LinearLayout.VERTICAL);

        builder = new AlertDialog.Builder(this);
        builder.setTitle("New Budgeting");
        builder.setMessage("Enter Details");

        etDesc = new EditText(getApplicationContext());
        etDesc.setHint("Enter Description");
        etDesc.setHintTextColor(Color.GRAY);
        etDesc.setTextColor(Color.BLACK);
        linearLayout.addView(etDesc);

        etDate = new EditText(getApplicationContext());
        etDate.setHint("Enter Date");
        etDate.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
        etDate.setHintTextColor(Color.GRAY);
        etDate.setTextColor(Color.BLACK);
        linearLayout.addView(etDate);

        builder2 = new AlertDialog.Builder(this);
        builder2.setTitle("Re-Schedule Date??");
        builder2.setMessage("Enter New Date");
        etReDate = new EditText(getApplicationContext());
        etReDate.setHint("Ex: YYYY-MM-DD");
        etReDate.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
        etReDate.setHintTextColor(Color.GRAY);
        etReDate.setTextColor(Color.BLACK);
        linearLayout2.addView(etReDate);
        builder2.setView(linearLayout2);

        builder2.setPositiveButton("Submit?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://172.20.10.4/myDatabase/updateDate.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.contains("no_data")){
                            Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("changeDate",etReDate.getText().toString());
                        params.put("UbAccID", String.valueOf(id));
                        return params;
                    }
                };

                MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            }
        });
        builder2.setNegativeButton("NO!!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        ad2 = builder2.create();

        builder.setView(linearLayout);

        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                desc = etDesc.getText().toString();
                date = etDate.getText().toString();
/*
                DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy"); // Make sure user insert date into edittext in this format.
                Date dateObject;

                try{
                    dateObject = formatter.parse(date);
                    newDate = new SimpleDateFormat("dd-MM-yyyy").format(dateObject);
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
*/
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://172.20.10.4/myDatabase/insertOfPiggy.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.contains("exist")){
                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("AccID", String.valueOf(globalVar.gAccID));
                        params.put("UbAccAmount", String.valueOf(0));
                        params.put("UbDateToOpen",date);
                        params.put("description",desc);
                        params.put("isActive", String.valueOf(0));
                        return params;
                    }
                };

                MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        ad = builder.create();

        String items[] = new String[] {"My Account", "Piggy", "About", "Logout"};

        drawerLayout = (DrawerLayout)findViewById(R.id.mainDrawer);
        itemList = (ListView)findViewById(R.id.categoryList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, items);
        itemList.setAdapter(adapter);
        itemList.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


        fragment = new PiggyBank();
        switch (position){
            case 0:
                Intent i = new Intent(this, Account.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
            case 1:
                fragment = new PiggyBank();
                fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.itemFrag,fragment).commit();
                break;
            case 2:
                Toast.makeText(getApplicationContext(), "About", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Intent c = new Intent(this, LoginAct.class);
                c.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(c);
                break;
            default:
                break;
        }


        drawerLayout.closeDrawers();
    }

    public void addDesc(View view) {
        ad.show();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            finish();
        }
    }

    public void DateCheck(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://172.20.10.4/myDatabase/dateValidation.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.contains("no_data")){
                    myDateValidations = new JsonConverter<myDateValidation>().toArrayList(response, myDateValidation.class);
                    for(myDateValidation dateValidation : myDateValidations){
                        id = dateValidation.getUbAccID();
                    }
                    Toast.makeText(getApplicationContext(),"WOOOOHHH",Toast.LENGTH_SHORT).show();
                    ad2.show();
                    displayNotification();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("AccID", String.valueOf(globalVar.gAccID));
                return params;
            }
        };

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }

    NotificationCompat.Builder notification;
    private static final int uniqueID = 12345;

    protected void displayNotification() {
        Intent i = new Intent(getApplicationContext(), MainAct.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notification = new NotificationCompat.Builder(getApplicationContext());
        notification.setContentTitle("Piggy Bank Ready to Break");
        notification.setContentText("One of your PIGGY BANK is Good To GO");
        notification.setTicker("Ready!!");
        notification.setWhen(System.currentTimeMillis());
//        notification.setVibrate(new long[] {1000, 1000, 1000, 1000});
        notification.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
       notification.setSmallIcon(R.drawable.ic_stat_name);
        notification.setContentIntent(PendingIntent.getActivity(getApplicationContext(), 0, i, PendingIntent.FLAG_UPDATE_CURRENT));
        notification.setAutoCancel(true);

        NotificationManager nm = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(uniqueID,notification.build());
    }
    
}
