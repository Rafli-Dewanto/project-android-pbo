package com.sugiartha.juniorandroid.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sugiartha.juniorandroid.R;
import com.sugiartha.juniorandroid.model.Peserta;

import java.util.List;

public class Adapter extends BaseAdapter {
    private final Activity activity;
    private LayoutInflater inflater;
    private final List<Peserta> items;

    public Adapter(Activity activity, List<Peserta> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int location) {
        return items.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row, null);
        }

        TextView idTextView = convertView.findViewById(R.id.id);
        TextView nameTextView = convertView.findViewById(R.id.name);
        TextView addressTextView = convertView.findViewById(R.id.address);

        Peserta data = items.get(position);

        // Convert the ID to a string before setting it as the text of the idTextView
        String idString = String.valueOf(data.getId());

        idTextView.setText(idString);
        nameTextView.setText(data.getName());
        addressTextView.setText(data.getAddress());

        return convertView;
    }
}
