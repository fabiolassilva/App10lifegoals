package com.example.aluno.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.aluno.myapplication.Fragment.TelaMetas;
import com.example.aluno.myapplication.MetaDAO;
import com.example.aluno.myapplication.Modelo.Meta;

import java.util.ArrayList;

public class TelaConcluidas extends ArrayAdapter<Meta> {
    private final Context context;
    private ArrayList<Meta> concluidas;
    private TextView txtmetas;
    private MetaDAO mdao;
    private TelaMetas telaMetas;
    private SharedPreferences sharedPreferences  =  getContext().getSharedPreferences("10lifegoals", Context.MODE_PRIVATE);
    private String usuario = sharedPreferences.getString("usuario", "Usuário não encontrado");

    public TelaConcluidas(Context context, ArrayList<Meta> metasconcluidas){
        super(context,R.layout.linha_metaconcluidas, metasconcluidas);
        this.context = context;
        this.concluidas = metasconcluidas;
    }

    public ArrayList<Meta> getConcluidas() {
        return concluidas;
    }

    public void setConcluidas(ArrayList<Meta> concluidas) {
        this.concluidas= concluidas;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.linha_metaconcluidas,parent,false);

        txtmetas = (TextView) rowView.findViewById(R.id.txtMetas);

        txtmetas.setText(concluidas.get(position).getDescricao());

        mdao = new MetaDAO(getContext());

        return rowView;
    }
}
