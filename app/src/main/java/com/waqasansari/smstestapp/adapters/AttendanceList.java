package com.waqasansari.smstestapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.waqasansari.smstestapp.R;

import java.util.List;

/**
 * Created by KFMWA916 on 5/8/2017.
 */

public class AttendanceList extends BaseAdapter {
    private Context context;
    private List<String> checkIns, checkOuts, dateList;

    public AttendanceList(Context context, List<String> checkIns, List<String> checkOuts, List<String> dateList) {
        this.context = context;
        this.checkIns = checkIns;
        this.checkOuts = checkOuts;
        this.dateList = dateList;
    }

    @Override
    public int getCount() {
        return checkIns.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_attendance, parent, false);
        }

        ((TextView) convertView.findViewById(R.id.txtDate)).setText(dateList.get(position));
        ((TextView) convertView.findViewById(R.id.txtCheckIn)).setText(checkIns.get(position));
        ((TextView) convertView.findViewById(R.id.txtCheckOut)).setText(checkOuts.get(position));


        return convertView;
    }
}
