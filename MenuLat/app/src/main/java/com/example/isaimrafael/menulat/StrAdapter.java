package com.example.isaimrafael.menulat;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

/**
 * Created by Raky on 3/11/15.
 */
public class StrAdapter extends BaseAdapter {

    private Context context;
    private String[] texts = { "Unidad", "Cursando" };

    public StrAdapter(Context context) {
        this.context = context;
    }

    public int getCount() {
        return texts.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv;
        if (convertView == null) {
            tv = new TextView(context);
            tv.setLayoutParams(new GridView.LayoutParams(115, 115));
        }
        else {
            tv = (TextView) convertView;
        }

        tv.setText(texts[position]);
        return tv;
    }
}
