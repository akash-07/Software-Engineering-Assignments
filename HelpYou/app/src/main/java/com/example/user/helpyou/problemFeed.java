package com.example.user.helpyou;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.user.helpyou.adapter.FeedListAdapter;
import com.example.user.helpyou.app.AppController;
import com.example.user.helpyou.data.FeedItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class problemFeed extends AppCompatActivity {

    public static String[] names;
    public static String[] addresses;

    public static final String JSON_ARRAY = "result";
    public static final String KEY_NAME = "name";
    public static final String KEY_ADDRESS = "address";
    private static final String TAG = problemFeed.class.getSimpleName();
    private ListView listView;
    private FeedListAdapter listAdapter;
    private List<FeedItem> feedItems;
    private String URL_FEED = "https://host-iittp.000webhostapp.com/myFiles/get-db.php";

    @SuppressLint("NewApi")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_feed);

        listView = (ListView)findViewById(R.id.listViewProblemFeed);
        feedItems = new ArrayList<>();
        listAdapter = new FeedListAdapter(this,feedItems);
        listView.setAdapter(listAdapter);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3b5998")));
        actionBar.setIcon(
                new ColorDrawable(ContextCompat.getColor(this,android.R.color.transparent)));

        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URL_FEED);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                parseJsonFeed(data);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        else {
            sendRequest();
        }
    }

    public void refreshList(View view) {
        sendRequest();
    }

    private void sendRequest()  {
        StringRequest stringRequest = new StringRequest(URL_FEED,
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

        AppController.getInstance().addToRequestQueue(stringRequest);
    }
    private void showJSON(String json)  {
        parseJSON pj = new parseJSON(json);
        pj.ParseJSON();
        CustomList cl = new CustomList(this,parseJSON.names,parseJSON.addresses);
        listView.setAdapter(cl);
    }

    private void parseJsonFeed(String json) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            JSONArray users = jsonObject.getJSONArray(JSON_ARRAY);
            names = new String[users.length()];
            addresses = new String[users.length()];
            for (int i = 0; i < users.length(); i++) {
                JSONObject jo = users.getJSONObject(i);
                names[i] = jo.getString(KEY_NAME);
                addresses[i] = jo.getString(KEY_ADDRESS);
                FeedItem item = new FeedItem();
                item.setId(i+1);
                item.setImge(null);
                item.setName(names[i]);
                item.setStatus(addresses[i]);
                //item.setProfilePic("http://api.androidhive.info/feed/img/nat.jpg");
                /*
                int time = (int) (System.currentTimeMillis());
                Timestamp tsTemp = new Timestamp(time);
                String ts =  tsTemp.toString();
                item.setTimeStamp(ts);
                */
                item.setUrl(null);
                feedItems.add(item);
            }
            Collections.reverse(feedItems);
            listAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
