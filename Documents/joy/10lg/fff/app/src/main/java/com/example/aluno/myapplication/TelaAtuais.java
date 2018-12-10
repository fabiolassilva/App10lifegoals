package com.example.aluno.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aluno.myapplication.DAO.ComentaDAO;
import com.example.aluno.myapplication.Fragment.TelaMetas;
import com.example.aluno.myapplication.Modelo.Comenta;
import com.example.aluno.myapplication.Modelo.Meta;

import java.util.ArrayList;

public class TelaAtuais extends ArrayAdapter<Meta> {
    private final Context context;
    private final ArrayList<Meta> atuais;
    private TextView[] txtperiodo;
    private TextView[] txtmetas;
    private CheckBox[] ckConcluir;
    private TextView txtnumero;
    private MetaDAO mdao;
    private TelaMetas telaMetas;
    private SharedPreferences sharedPreferences = getContext().getSharedPreferences("10lifegoals", Context.MODE_PRIVATE);

    private TelaConcluidas adapterConcluidas;

    private int numeroMeta;
    private String usuario = sharedPreferences.getString("usuario", "Usuário não encontrado");

    private ArrayList<Comenta> coments;
    ArrayAdapter adapterComenta;
    private ListView comentario;
    Button alertC;

    public TelaAtuais(Context context, ArrayList<Meta> metastuais, TelaConcluidas adapterConcluidas) {
        super(context, R.layout.linha_metasatuais, metastuais);
        this.context = context;
        this.atuais = metastuais;
        this.ckConcluir = new CheckBox[metastuais.size()];
        this.txtperiodo = new TextView[metastuais.size()];
        this.txtmetas = new TextView[metastuais.size()];
        this.adapterConcluidas = adapterConcluidas;
    }

    public View getView(final int position, View convertView, final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.linha_metasatuais, parent, false);

        txtperiodo[position] = (TextView) rowView.findViewById(R.id.txtperiodo);
        txtmetas[position] = (TextView) rowView.findViewById(R.id.txtMetas);
        ckConcluir[position] = (CheckBox) rowView.findViewById(R.id.ckConcluir);
        txtnumero = (TextView) rowView.findViewById(R.id.numerometa);

        alertC = (Button) rowView.findViewById(R.id.btnAlertC);
        alertC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertComent(mdao.procurarNum(usuario, txtmetas[position].getText().toString()),context, parent);
            }
        });

        txtmetas[position].setText(atuais.get(position).getDescricao());
        if(atuais.get(position).getPeriodo()==7) {
            txtperiodo[position].setText("1 semana");
        }
        if(atuais.get(position).getPeriodo()==10) {
            txtperiodo[position].setText("10 dias");
        }
        if(atuais.get(position).getPeriodo()==30) {
            txtperiodo[position].setText("1 mês");
        }
        if(atuais.get(position).getPeriodo()==60) {
            txtperiodo[position].setText("2 meses");
        }
        if(atuais.get(position).getPeriodo()==90) {
            txtperiodo[position].setText("3 meses");
        }
        txtnumero.setText(Integer.toString(position + 1));

        mdao = new MetaDAO(getContext());
        ckConcluir[position].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("seila - "+position, ckConcluir[position].isChecked() + " "+txtmetas[position].getText().toString());
                if (ckConcluir[position].isChecked()) {
                    final int num = mdao.procurarNum(usuario, txtmetas[position].getText().toString());

                    mdao.concluir(usuario, num);
                    mdao.procurarMetaSituacao(usuario);
                    mdao.metasAtuais(usuario);

                    SharedPreferences.Editor editor;
                    editor = sharedPreferences.edit(); //Escrever dentro do arquivo
                    editor.putString("usuario", usuario);
                    editor.putString("descricao", txtmetas[position].getText().toString());
                    editor.commit();
                    Toast.makeText(getContext(), "Meta Concluida", Toast.LENGTH_SHORT).show();

                    ArrayList<Meta> concluidas = mdao.metasConcluidas(usuario);
                    adapterConcluidas.setConcluidas(concluidas);

                    adapterConcluidas.notifyDataSetChanged();
                }
            }
        });
        return rowView;
    }

    public void alertComent(int num, final Context context, ViewGroup parent) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.alert_comenta, parent, false);

        comentario  = (ListView) rowView.findViewById(R.id.lista_alert);

        coments = new ArrayList<>();
        coments = new ComentaDAO(context).comentarios(num);
        if(!coments.isEmpty()){
            adapterComenta = new TelaComenta(context, coments);
            comentario.setAdapter(adapterComenta);}

        alertDialog.setView(rowView).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alertDialog.show();
    }
}
