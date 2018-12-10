package com.example.aluno.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aluno.myapplication.Fragment.TelaMetas;
import com.example.aluno.myapplication.Modelo.PessoaSalvaMeta;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class TelaSalvas extends ArrayAdapter<PessoaSalvaMeta> {
    private final Context context;
    private final ArrayList<PessoaSalvaMeta> salvas;
    private TextView txtUsuCriador;
    private TextView txtDescrMeta;
    private Button btnAdicionar;
    private MetaDAO mdao;
    private SalvaDAO sdao;
    int na = 0;

    public TelaSalvas(Context context, ArrayList<PessoaSalvaMeta> metasalvas){
        super(context,R.layout.linha_metasalvas, metasalvas);
        this.context = context;
        this.salvas = metasalvas;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.linha_metasalvas,parent,false);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("10lifegoals", Context.MODE_PRIVATE);
        final String usuario = sharedPreferences.getString("usuario", "Usuário não encontrado");
        txtUsuCriador = (TextView) rowView.findViewById(R.id.txtUsuCriador);
        txtDescrMeta = (TextView) rowView.findViewById(R.id.txtDescrMeta);
        btnAdicionar = (Button)rowView.findViewById(R.id.btnAdicionar);

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog(context, usuario);
            }
        });

        mdao = new MetaDAO(getContext());
        sdao = new SalvaDAO(getContext());

        txtUsuCriador.setText(salvas.get(position).getUsuarioCriador());
        txtDescrMeta.setText(salvas.get(position).getDescrMeta());

        return rowView;

    }

    public void showInputDialog(final Context context, final String user) {
        System.out.println("ENTROU NO SHOWINPUTDIALOG");
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );//LayoutInflater inflater = this.getLayoutInflater();

        //Cria a view a ser utilizada no dialog
        View view = inflater.inflate(R.layout.adicionar_salva, null);

        //Obtém uma referencia ao Spinner
        Spinner spinner = (Spinner) view.findViewById(R.id.spPeriodo);
        final EditText desc = (EditText) view.findViewById(R.id.desc);
        desc.setText(txtDescrMeta.getText().toString());

        //Cria o Adapter
        ArrayAdapter adapter = ArrayAdapter.createFromResource(context, R.array.periodo,
                android.R.layout.simple_spinner_dropdown_item);//ou android.R.layout.simple_spinner_item

        //Atribui o adapter ao spinner
        spinner.setAdapter(adapter);

        final int[] pe = {0};
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).toString() != null) {
                    if (adapterView.getItemAtPosition(i).toString().equals("1 semana"))
                        pe[0] = 7;
                    if (adapterView.getItemAtPosition(i).toString().equals("10 dias"))
                        pe[0] = 10;
                    if (adapterView.getItemAtPosition(i).toString().equals("1 mês"))
                        pe[0] = 30;
                    if (adapterView.getItemAtPosition(i).toString().equals("2 meses"))
                        pe[0] = 60;
                    if (adapterView.getItemAtPosition(i).toString().equals("3 meses"))
                        pe[0] = 90;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        final SharedPreferences sharedPreferences = getContext().getSharedPreferences("10lifegoals", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor;
        editor = sharedPreferences.edit(); //Escrever dentro do arquivo

        final int j = getN();
        alertDialog.setView(view)
                .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        GregorianCalendar calendar = new GregorianCalendar();
                        int data = calendar.get(GregorianCalendar.DAY_OF_MONTH);
                        if (TelaMetas.qtdeAtuais() < 10) {
                            mdao.criarMeta(txtDescrMeta.getText().toString(), "nao", pe[0], "N", data, user);
                            sdao.atualizar(desc.getText().toString());

                            editor.putString("meta " + j, desc.getText().toString());
                            if (pe[0] == 7)
                                editor.putString("pe " + j, "1 semana");
                            if (pe[0] == 10)
                                editor.putString("pe " + j, "10 dias");
                            if (pe[0] == 30)
                                editor.putString("pe " + j, "1 mês");
                            if (pe[0] == 60)
                                editor.putString("pe " + j, "2 meses");
                            if (pe[0] == 90)
                                editor.putString("pe " + j, "3 meses");
                            editor.commit();

                            //lstConcluida.requestLayout();
                            //Intent i = new Intent(getActivity(), MainActivity.class);
                            //startActivity(i);
                        } else {
                            Toast.makeText(context, "Você só pode ter 10 metas por vez", Toast.LENGTH_SHORT).show();

                        }
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        //lstConcluida.requestLayout();
        editor.commit();
        alertDialog.show();

    }

    public void novaMeta(int i) {
        this.na = i;
    }

    public int getN() {
        return na;
    }
}
