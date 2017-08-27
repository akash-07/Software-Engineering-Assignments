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
import com.example.user.helpyou.data.Problem;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by LENOVO on 17-08-2017.
 */



public class ProblemHistoryAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Problem> problems;
    ImageLoader imageLoader = AppController.getInstance().getmImageLoader();


    public ProblemHistoryAdapter(Activity activity,List<Problem> problems)  {
        this.activity = activity;
        this.problems = problems;
    }

    @Override
    public int getCount()   { return problems.size(); }

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
                convertView = inflater.inflate(R.layout.problem_item,null);
        if(imageLoader == null)
            imageLoader = AppController.getInstance().getmImageLoader();

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView timeStamp = (TextView) convertView.findViewById(R.id.timestamp);
        TextView statusMsg = (TextView) convertView
                .findViewById(R.id.txtStatusMsg);
        TextView description = (TextView) convertView.findViewById(R.id.txtDesc);
        TextView area = (TextView) convertView.findViewById(R.id.txtArea);
        /*
        NetworkImageView profilePic = (NetworkImageView) convertView;

                .findViewById(R.id.profilePic);
        FeedImageView feedImageView = (FeedImageView) convertView
                .findViewById(R.id.feedImage1);
        */
        Problem item = problems.get(position);

        name.setText(item.getName());

        //Converting timeStamp into x ago format
        /*
        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                Long.parseLong(item.getTimeStamp()),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
        timeStamp.setText(timeAgo);
        */
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        timeStamp.setText(currentDateTimeString);
        //Check for empty status message
        if (!TextUtils.isEmpty(item.getStatus())) {
            statusMsg.setText(item.getStatus());
            statusMsg.setVisibility(View.VISIBLE);
        } else {
            // status is empty, remove from view
            statusMsg.setVisibility(View.GONE);
        }

        description.setText(item.getProbDescription());
        area.setText(item.getArea());

        //Checking for null feed urlS
        /*
        if (item.getUrl() != null) {
            url.setText(Html.fromHtml("<a href=\"" + item.getUrl() + "\">"
                    + item.getUrl() + "</a> "));

            // Making url clickable
            url.setMovementMethod(LinkMovementMethod.getInstance());
            url.setVisibility(View.VISIBLE);
        } else {
            // url is null, remove from the view
            url.setVisibility(View.GONE);
        }*/
        //Setting it to null
        //url.setVisibility(View.GONE);


        //user profile pic
        //profilePic.setImageUrl(item.getProfilePic(),imageLoader);

        /*
        //Feed image
        if(item.getImge() != null)  {
            feedImageView.setImageUrl(item.getImge(),imageLoader);
            feedImageView.setVisibility(View.VISIBLE);
            feedImageView.setResponseObserver(new FeedImageView.ResponseObserver()  {
                @Override
                public void onError() {
                }

                @Override
                public void onSuccess() {
                }
            });
        } else {
            feedImageView.setVisibility(View.GONE);
        }
        */
        return convertView;
    }
}
