package com.example.user.helpyou;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.user.helpyou.app.AppController;
import com.example.user.helpyou.data.Problem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class problemDescriptionActivity extends AppCompatActivity {

    public static String REGISTER_URL = "https://host-iittp.000webhostapp.com/myFiles/insertProblem-db.php";
    public static final String KEY_NAME = "name";
    public static final String KEY_AREA = "area";
    public static final String KEY_PHNO = "phNo";
    public static final String KEY_TITLE = "problemTitle";
    public static final String KEY_DESCRIPTION = "problemDescription";
    public static final String KEY_STATUS = "status";
    public static final String KEY_TIMESTAMP = "timeStamp";
    EditText nameEditText;
    EditText areaEditText;
    EditText problemTitleEditText;
    EditText problemDescriptionEditText;
    Button postButton;
    Problem problem = new Problem();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_description);
        nameEditText = (EditText) findViewById(R.id.editTextName);
        areaEditText = (EditText) findViewById(R.id.areaEditText);
        problemTitleEditText = (EditText) findViewById(R.id.problemTitleEditText);
        problemDescriptionEditText = (EditText) findViewById(R.id.problemDescriptionEditText);
        postButton = (Button) findViewById(R.id.postButton);
    }

    public void insert(View view)   {
        String name = nameEditText.getText().toString().replaceAll(" ","_");
        String area = areaEditText.getText().toString().replaceAll(" ","_");
        String problemTitle = problemTitleEditText.getText().toString().replaceAll(" ","_");
        String problemDescription = problemDescriptionEditText.getText().toString().replaceAll(" ","_");
        String status = "false";
        String phNo = "22589522";
        String timeStamp;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy  HH:mm");
        String currentDateandTime = sdf.format(new Date());
        currentDateandTime = currentDateandTime.replaceAll(" ","_");
        String currentTime = Calendar.getInstance().getTime().toString();
        timeStamp = currentDateandTime;
        Toast.makeText(this,timeStamp,Toast.LENGTH_LONG).show();
        problem.setName(name);
        problem.setTimeStamp(timeStamp);
        problem.setArea(area);
        problem.setPhNo(phNo);
        problem.setProblemTitle(problemTitle);
        problem.setProbDescription(problemDescription);
        problem.setStatus(status);
        Toast.makeText(getApplicationContext(),"Posting your problem....",Toast.LENGTH_SHORT).show();
        REGISTER_URL = "https://host-iittp.000webhostapp.com/myFiles/insertProblem-db.php?name="+name+"&area="+area+
        "&problemDescription="+problemDescription+"&problemTitle="+problemTitle+"&status="+status+"&phNo="+phNo+
        "&timeStamp="+timeStamp;
        insertToDatabase();
    }

    private void insertToDatabase(){
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
                params.put(KEY_NAME,problem.getName());
                params.put(KEY_AREA,problem.getArea());
                params.put(KEY_STATUS,problem.getStatus());
                params.put(KEY_PHNO,problem.getPhNo());
                params.put(KEY_TIMESTAMP,problem.getTimeStamp());
                params.put(KEY_TITLE,problem.getProblemTitle());
                params.put(KEY_DESCRIPTION,problem.getProbDescription());
                return params;
            }
        };

        //RequestQueue requestQueue = Volley.newRequestQueue(this);
        //requestQueue.add(stringRequest);
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

}
