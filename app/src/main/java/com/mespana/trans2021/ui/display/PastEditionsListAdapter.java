package com.mespana.trans2021.ui.display;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mespana.trans2021.R;
import com.mespana.trans2021.models.Event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class PastEditionsListAdapter extends BaseAdapter {

    private List<Event> eventList;

    public PastEditionsListAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }

    @Override
    public int getCount() {
        return eventList.size();
    }

    @Override
    public Event getItem(int position) {
        return eventList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_display_editions_list_item, parent, false);
        }
        Event e = getItem(position);
        SimpleDateFormat formater = new SimpleDateFormat("dd MMMM yyyy");
        ((TextView)convertView.findViewById(R.id.date)).setText(formater.format(e.getDate()));
        ((TextView)convertView.findViewById(R.id.place)).setText(e.getSalle());
        return convertView;
    }
}
