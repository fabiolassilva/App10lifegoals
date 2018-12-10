package com.example.aluno.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aluno.myapplication.DAO.ComentaDAO;
import com.example.aluno.myapplication.Modelo.Comenta;
import com.example.aluno.myapplication.Modelo.Meta;
import com.example.aluno.myapplication.Modelo.PessoaSalvaMeta;

import java.util.ArrayList;

public class TelaInicio extends ArrayAdapter<Meta> {
    private final Context context;
    private final ArrayList<Meta> meta;
    private SalvaDAO sdao;
    TextView txtNomeUsu;
    TextView txtDescrMeta;
    Button btnSalvar;
    MetaDAO mD;
    Button btnComentar;
    ListView comentario;
    private ArrayList<Comenta> coments;
    ComentaDAO dao;
    String descr, user;
    ArrayAdapter adapterComenta;
    private EditText edcomentar;


    public TelaInicio(Context context, ArrayList<Meta> metas) {
        super(context, R.layout.linha_inicio, metas);
        this.context = context;
        this.meta = metas;

    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.linha_inicio2, parent, false);

        edcomentar = rowView.findViewById(R.id.comentar);
        //edtComent.setText("Fala");
        comentario = (ListView) rowView.findViewById(R.id.comentarios);
        // txtUser = (TextView) rowView.findViewById(R.id.txtUser);
        //txtComent = (TextView) rowView.findViewById(R.id.txtComent);
        btnComentar = (Button) rowView.findViewById(R.id.btnComentar);
        txtNomeUsu = (TextView) rowView.findViewById(R.id.txtNomeUsu);
        txtDescrMeta = (TextView) rowView.findViewById(R.id.txtDescMeta);
        btnSalvar = (Button) rowView.findViewById(R.id.btnSalvar);

        mD = new MetaDAO(getContext());
        txtNomeUsu.setText(meta.get(position).getNomeUser());
        txtDescrMeta.setText(meta.get(position).getDescricao());
        descr = meta.get(position).getDescricao();
        user = meta.get(position).getNomeUser();

        //Toast.makeText(context, "Eai "+position, Toast.LENGTH_SHORT).show();
        System.out.println("Eai " + position);

        coments = new ArrayList<>();
        coments = new ComentaDAO(getContext()).comentarios(meta.get(position).getNumero());
        adapterComenta = new TelaComenta(getContext(), coments);
        comentario.setAdapter(adapterComenta);

        System.out.println("Eai " + position + " " + txtNomeUsu.getText().toString() + " " + txtDescrMeta.getText().toString() + " " + meta.get(position).getNumero());

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarMeta(meta.get(position));
            }
        });

        btnComentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("xexexexex");
                System.out.println("qqqqq "+edcomentar.getText().toString());
                // edcomentar.setText("Fala");
                if (!edcomentar.getText().toString().equals(""))
                    if (edcomentar.getText().toString().length() > 0) {
                        comentar(meta.get(position).getNumero());
                    }
            }
        });
        atualizarTamanho();
        return rowView;
    }


    ///salvar meta inicio
    public void salvarMeta(Meta meta) {
        sdao = new SalvaDAO(getContext());
        sdao.abrir();


        String usuarioCriador;
        String descrMeta;
        String usuarioLogado;
        String efetiva = "N";


        SharedPreferences sharedPreferences = getContext().getSharedPreferences("10lifegoals", Context.MODE_PRIVATE);
        String usuarioLog = sharedPreferences.getString("usuario", "Usuário não encontrado");

        usuarioLogado = usuarioLog;

        usuarioCriador = meta.getNomeUser();
        descrMeta = meta.getDescricao();
        Log.e("salva", usuarioCriador + " " + descrMeta);

        PessoaSalvaMeta psm = sdao.salvar(usuarioLogado, usuarioCriador, descrMeta, efetiva);

        ArrayAdapter<PessoaSalvaMeta> adaptador = new ArrayAdapter<PessoaSalvaMeta>(getContext().getApplicationContext(), 0);
        adaptador.add(psm);
        adaptador.notifyDataSetChanged();


        SharedPreferences.Editor editor;
        editor = sharedPreferences.edit(); //Escrever dentro do arquivo

        editor.putString("usuarioLogado", usuarioLogado);
        editor.putString("usuarioCriador", usuarioCriador);
        editor.putString("descrMeta", descrMeta);
        editor.putString("efetiva", efetiva);
        editor.commit();

        Toast.makeText(getContext(), "Meta Salva", Toast.LENGTH_SHORT).show();
    }

    //salvsar meta fim

    public void comentar(int numMeta){
        //comentar
        dao = new ComentaDAO(getContext());
        dao.abrir();
        String coment;
        String usuarioComenta;
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("10lifegoals", Context.MODE_PRIVATE);
        String userComenta = sharedPreferences.getString("usuario", "usuario não encontrado");
        usuarioComenta = userComenta;
        coment = edcomentar.getText().toString();
        Comenta c = dao.comentar(coment, usuarioComenta, numMeta);

        /*txtComent.setText(coment);
        txtUser.setText(usuarioComenta);*/

        SharedPreferences.Editor editor;
        editor = sharedPreferences.edit();
        editor.putString("coment", coment);
        editor.putString("usuarioComenta", usuarioComenta);
        editor.commit();
        dao.fechar();

        edcomentar.setText("");
        adapterComenta.add(c);
        adapterComenta.notifyDataSetChanged();
        Log.e("teste", adapterComenta.getCount()+"");
        atualizarTamanho();
    }

    public void atualizarTamanho(){
        adapterComenta = new TelaComenta(getContext(), coments);
        comentario.setAdapter(adapterComenta);

        ViewGroup.LayoutParams layoutParams = comentario.getLayoutParams();
        layoutParams.height = (int) 95 * comentario.getCount();
        comentario.setLayoutParams(layoutParams);
        comentario.invalidate();
    }
}
