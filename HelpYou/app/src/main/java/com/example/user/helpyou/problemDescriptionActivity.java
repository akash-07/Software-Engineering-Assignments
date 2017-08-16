package com.example.user.helpyou;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.helpyou.app.AppController;

import java.util.HashMap;
import java.util.Map;

public class problemDescriptionActivity extends AppCompatActivity {

    public static String REGISTER_URL = "https://host-iittp.000webhostapp.com/myFiles/insert1-db.php";
    public static final String KEY_NAME = "name";
    public static final String KEY_ADDRESS = "address";
    TextView responseTextView;
    EditText nameEditText;
    EditText addEditText;
    Button postButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_description);
        nameEditText = (EditText) findViewById(R.id.editTextName);
        addEditText = (EditText) findViewById(R.id.addressEditText);
        postButton = (Button) findViewById(R.id.postButton);
    }

    public void insert(View view)   {
        String name = nameEditText.getText().toString();
        String address = addEditText.getText().toString();
        Toast.makeText(getApplicationContext(),"Posting your problem....",Toast.LENGTH_SHORT).show();
        REGISTER_URL = "https://host-iittp.000webhostapp.com/myFiles/insert1-db.php?name="+name+"&address="+address;
        insertToDatabase(name,address);
    }

    private void insertToDatabase(final String name,final String address){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,REGISTER_URL,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error)  {
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams()    {
                Map<String,String> params = new HashMap<String,String>();
                params.put(KEY_NAME,name);
                params.put(KEY_ADDRESS,address);
                return params;
            }
        };

        //RequestQueue requestQueue = Volley.newRequestQueue(this);
        //requestQueue.add(stringRequest);
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

}
