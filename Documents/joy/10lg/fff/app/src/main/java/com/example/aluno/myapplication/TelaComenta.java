package com.example.aluno.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.aluno.myapplication.Modelo.Comenta;

import java.util.ArrayList;

public class TelaComenta extends ArrayAdapter<Comenta> {
    private final Context context;
    private SalvaDAO sdao;
    MetaDAO mD;
    TextView txtUserComenta;
    TextView txtDescComenta ;
    Button btnSalvar;
    Button btnComentar;
    EditText edtComent;
    ListView comentario;
    private ArrayList<Comenta> comentas;

    public TelaComenta(Context context, ArrayList<Comenta> comentas){
        super(context,R.layout.linha_comenta,comentas);
        this.context = context;
        this.comentas = comentas;
    }

    public View getView(final int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.linha_comenta,parent,false);

        txtUserComenta= (TextView) rowView.findViewById(R.id.txtUserComenta);
        txtDescComenta = (TextView) rowView.findViewById(R.id.txtDescComenta);
        txtUserComenta.setText(comentas.get(position).getUsuarioComenta());
        txtDescComenta.setText(comentas.get(position).getComent());
        return rowView;
    }
}
