package com.example.isaimrafael.kardexv11.rellena_materias_horario;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.isaimrafael.kardexv11.R;

import java.util.List;

/**
 * Created by IsaimRafael on 19/11/2015.
 */
public class TareaArrayAdapter<Tarea> extends ArrayAdapter<Tarea> {
    public TareaArrayAdapter(Context context, List<Tarea> objects){
        super(context, 0, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listItemView = convertView;
        if (null == convertView){
            listItemView = inflater.inflate(R.layout.list_item_design, parent, false);
        }

        TextView titulo = (TextView)listItemView.findViewById(R.id.text1);
        TextView subtitulo = (TextView)listItemView.findViewById(R.id.text2);

        Tarea item = (Tarea)getItem(position);

        String cadenaBruta;
        String subCadenas [];
        String delimitador = ",";

        cadenaBruta = item.toString();
        subCadenas = cadenaBruta.split(delimitador,2);

        titulo.setText(subCadenas[0]);
        subtitulo.setText(subCadenas[1]);

        return listItemView;
    }
}
