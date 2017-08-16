package com.example.user.helpyou.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.user.helpyou.R;
import com.example.user.helpyou.app.AppController;
import com.example.user.helpyou.data.FeedItem;
import com.example.user.helpyou.data.Problem;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by @K@sh on 8/14/2017.
 */

public class FeedListAdapter extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private List<Problem> problems;
    ImageLoader imageLoader = AppController.getInstance().getmImageLoader();

    public FeedListAdapter(Activity activity,List<Problem> problems)  {
        this.activity = activity;
        this.problems = problems;
    }

    @Override
    public int getCount()   {
        return problems.size();
    }

    @Override
    public Object getItem(int location) {
        return problems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)   {
        if(inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null)
            convertView = inflater.inflate(R.layout.feed_item,null);
        if(imageLoader == null)
            imageLoader = AppController.getInstance().getmImageLoader();

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView timeStamp = (TextView) convertView.findViewById(R.id.timestamp);
        TextView statusMsg = (TextView) convertView.findViewById(R.id.txtStatus);
        TextView title = (TextView) convertView.findViewById(R.id.txtTitle);
        Problem problem = problems.get(position);

        //Setting name
        name.setText(problem.getName());

        //Setting statusMsg
        if (problem.getStatus().equals("true")) {
            statusMsg.setText("Status: Accepted");
        } else {
            // status is empty, remove from view
            statusMsg.setText("Status: Not yet Recognised");
        }

        //Setting timeStamp
        timeStamp.setText(problem.getTimeStamp());

        //Settine problemTitle
        title.setText("Problem: "+ problem.getProblemTitle());

        return convertView;
    }
}
