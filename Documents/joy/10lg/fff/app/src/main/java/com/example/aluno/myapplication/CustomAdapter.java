package com.example.aluno.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.example.aluno.myapplication.Modelo.Pessoa;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<Pessoa> pessoa;
    LayoutInflater inflater;

    public CustomAdapter(Context c, ArrayList<Pessoa> pessoa) {
        this.context = c;
        this.pessoa = pessoa;
    }

    public int getCount() {
        return pessoa.size();
    }

    public Object getItem(int position) {
        return pessoa.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.pesquisa_usuario, parent, false);
        }


        final int pos = position;

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, pessoa.get(pos).getUsuario(), Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

}