package com.example.user.helpyou;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by @K@sh on 8/12/2017.
 */

public class parseJSON {

    public static String[] names;
    public static String[] addresses;

    public static final String JSON_ARRAY = "result";
    public static final String KEY_NAME = "name";
    public static final String KEY_ADDRESS = "address";

    private String json;

    public parseJSON(String json){
        this.json = json;
    }

    protected void ParseJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            JSONArray users = jsonObject.getJSONArray(JSON_ARRAY);

            names = new String[users.length()];
            addresses = new String[users.length()];

            for(int i=0;i<users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                names[i] = jo.getString(KEY_NAME);
                addresses[i] = jo.getString(KEY_ADDRESS);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
