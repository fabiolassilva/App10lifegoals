package com.example.aluno.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aluno.myapplication.DAO.AmigoDAO;
import com.example.aluno.myapplication.Modelo.Pessoa;

import java.util.ArrayList;

public class ListaAmigos extends AppCompatActivity {

    ListView listaAmg;
    private ArrayList<String> solicita;
    private PessoaDAO pdao;
    private ArrayAdapter<String> adaptador;
    private ListView lstMetas;

    String p;
    Button solicitar;
    TextView txtnome;
    TextView txtuser;
    TextView txtbio;
    TextView txtdata;
    ImageView mage;
    TextView txtSolic;
    private ArrayAdapter<String> adaptad;
    private Button btnExcluir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_amigos);

        listaAmg = (ListView) findViewById(R.id.listAmigos);

        pdao = new PessoaDAO(this);
        SharedPreferences sharedPreferences = getBaseContext().getSharedPreferences("10lifegoals", Context.MODE_PRIVATE);
        final String usuario = sharedPreferences.getString("usuario", "Usuário não encontrado");

        solicita = new AmigoDAO(this).listarAmigos(usuario);

        listaAmg = (ListView) findViewById(R.id.listAmigos);
        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, solicita);
        listaAmg.setAdapter(adaptador);

        listaAmg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                telaOutroUsuario(usuario, pdao.buscarPessoa(solicita.get(i)), new MetaDAO(getBaseContext()).procurarMetas(solicita.get(i)));
            }
        });
    }

    public void telaOutroUsuario(final String usuario, final Pessoa pessoa, ArrayList<String> metas) {
        setContentView(R.layout.outro_usuario);

        lstMetas = (ListView) findViewById(R.id.listaMetasPerfil);
        txtnome = (TextView) findViewById(R.id.txtNome);
        txtuser = (TextView) findViewById(R.id.txtUsuario);
        txtbio = (TextView) findViewById(R.id.txtBio);
        txtdata = (TextView) findViewById(R.id.txtDataNasc);
        mage = (ImageView) findViewById(R.id.imageView);
        txtSolic = (TextView) findViewById(R.id.txtSolic);
        solicitar = (Button) findViewById(R.id.btnSolicitar);
        solicitar.setVisibility(View.INVISIBLE);
        btnExcluir = (Button) findViewById(R.id.btnExcluir);
        btnExcluir.setVisibility(View.VISIBLE);
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("oi","oi2");
                solicitar.setVisibility(View.VISIBLE);
                solicitar.setText("Solicitar amizade");
                txtSolic.setVisibility(View.INVISIBLE);
                btnExcluir.setVisibility(View.INVISIBLE);
                new AmigoDAO(getBaseContext()).remover(pessoa.getUsuario(), usuario);

                Log.e("oi3","oi4");

            }
        });
        txtSolic = (TextView) findViewById(R.id.txtSolic);
        txtSolic.setVisibility(View.VISIBLE);
        txtSolic.setText("Amigos");

            solicitar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnExcluir.setText("Cancelar solicitação");
                    btnExcluir.setVisibility(View.VISIBLE);
                    Toast.makeText(ListaAmigos.this, "Solicitação enviada!", Toast.LENGTH_LONG).show();
                    new AmigoDAO(getBaseContext()).adicionarAmigo(usuario, pessoa.getUsuario());
                }
            });
        
        adaptad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, metas);
        lstMetas.setAdapter(adaptad);

        txtdata.setText(pessoa.getDataNasc());
        mage.setImageBitmap(pessoa.getImage());

        txtuser.setText(pessoa.getUsuario());
        txtnome.setText(pessoa.getNome());
        txtbio.setText(pessoa.getBio());

    }
}
