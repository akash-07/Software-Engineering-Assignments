package com.example.user.helpyou;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by @K@sh on 8/12/2017.
 */

public class CustomList extends ArrayAdapter<String> {
    private String[] name;
    private String[] address;
    private Activity context;

    public CustomList(Activity context,String[] names,String[] addresses) {
        super(context,R.layout.problem_list_item,names);
        this.context = context;
        this.name = names;
        this.address = addresses;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.problem_list_item,null,true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.name);
        TextView textViewAddress = (TextView) listViewItem.findViewById(R.id.address);

        textViewName.setText("** "+name[position]);
        textViewAddress.setText("  "+address[position]);

        return listViewItem;
    }
}
