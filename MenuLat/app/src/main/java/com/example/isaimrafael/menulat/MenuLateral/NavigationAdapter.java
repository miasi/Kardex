package com.example.isaimrafael.menulat.MenuLateral;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.isaimrafael.menulat.MenuLateral.item_objct;
import com.example.isaimrafael.menulat.R;

import java.util.ArrayList;

/**
 * Created by IsaimRafael on 25/10/2015.
 */
public class NavigationAdapter extends BaseAdapter {
    private Activity activity;
    ArrayList<item_objct> arrayitms;

    public NavigationAdapter(Activity activity, ArrayList<item_objct> listarry){
        super();
        this.activity = activity;
        this.arrayitms = listarry;
    }

    @Override
    public Object getItem(int position){
        return arrayitms.get(position);
    }

    public int getCount(){
        return arrayitms.size();
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    public static class Fila
    {
        TextView titulo_itm;
        ImageView icono;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Fila view;
        LayoutInflater inflator = activity.getLayoutInflater();
        if (convertView==null)
        {
            view = new Fila();
            item_objct itm = arrayitms.get(position);
            convertView = inflator.inflate(R.layout.activity_itm,null);
            view.titulo_itm = (TextView)convertView.findViewById(R.id.title_item);
            view.titulo_itm.setText(itm.getTitulo());
            view.icono = (ImageView)convertView.findViewById(R.id.icon);
            view.icono.setImageResource(itm.getIcono());
            convertView.setTag(view);
        }
        else{
            view = (Fila)convertView.getTag();
        }
        return convertView;
    }
}
