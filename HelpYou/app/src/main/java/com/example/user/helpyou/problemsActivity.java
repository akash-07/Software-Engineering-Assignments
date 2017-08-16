package com.example.user.helpyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

import static android.R.id.list;

public class problemsActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String JSON_URL = "https://host-iittp.000webhostapp.com/myFiles/get-db.php";
    private static final String TAG_RESULTS = "result";
    private static final String TAG_ADDRESS = "address";
    private static final String TAG_NAME = "name";

    JSONArray problems = null;

    ArrayList<HashMap<String,String>> problemList;
    Button button;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problems);
        listView = (ListView) findViewById(R.id.listView);
        button = (Button) findViewById(R.id.getProblemsButton);
        button.setOnClickListener(this);
        problemList = new ArrayList<>();
        sendRequest();
    }

    public void sendRequest()  {
        StringRequest stringRequest = new StringRequest(JSON_URL,
            new Response.Listener<String>(){
                @Override
                public void onResponse(String response) {
                    showJSON(response);
                }
            },
            new Response.ErrorListener()  {
                @Override
                public void onErrorResponse(VolleyError error)  {
                    Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
    RequestQueue requestQueue = Volley.newRequestQueue(this);
    requestQueue.add(stringRequest);
}


    private void showJSON(String json)  {
        parseJSON pj = new parseJSON(json);
        pj.ParseJSON();
        CustomList cl = new CustomList(this,parseJSON.names,parseJSON.addresses);
        listView.setAdapter(cl);
    }

    @Override
    public void onClick(View v) {
        sendRequest();
    }


}
