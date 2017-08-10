package com.example.user.helpyou;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

public class problemDescriptionActivity extends AppCompatActivity {

    TextView responseTextView = (TextView) findViewById(R.id.responseTextView);
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
        insertToDatabase(name,address);
    }

    private void insertToDatabase(String name, String address) {
        class SendPostRequest extends AsyncTask<String, Void, String> {
            protected void onPreExecute() {
            }

            protected String doInBackground(String... arg0) {
                try {
                    URL url = new URL("https://...");
                    JSONObject postDataParams = new JSONObject();
                    postDataParams.put("name", "abc");
                    postDataParams.put("address", "Rosen street/210, London");
                    Log.e("params", postDataParams.toString());

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(15000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(getPostDataString(postDataParams));
                    writer.flush();
                    writer.close();
                    os.close();

                    int responseCode = conn.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuffer sb = new StringBuffer("");
                        String line = "";

                        while ((line = in.readLine()) != null) {
                            sb.append(line);
                            break;
                        }
                        in.close();
                        return sb.toString();
                    } else {
                        return new String("false: " + responseCode);
                    }
                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }
            }

            @Override
            protected void onPostExecute(String result) {
                //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
                responseTextView.setText(result);
            }
        }
    }

    public String getPostDataString(JSONObject params) throws Exception {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext())    {
            String key = itr.next();
            Object value = params.get(key);

            if(first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key,"UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(),"UTF-8"));
        }
        return result.toString();
    }
}
