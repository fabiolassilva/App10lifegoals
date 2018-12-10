package com.example.aluno.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import java.util.List;

public class OutroUsuario extends AppCompatActivity {

    private ListView listasolic;
    private ListView lstMetas;
    private List<String> metas;
    private ArrayList<String> solicita;
    private ArrayAdapter<String> adaptador;
    private ArrayAdapter<String> adaptad;
    private PessoaDAO pdao;
    ImageView image;

    String p;
    Button solicitar;
    TextView txtnome;
    TextView txtuser;
    TextView txtbio;
    TextView txtdata;
    ImageView mage;
    TextView txtSolic;
    Button btnSim;
    Button btnNao;
    private Button btnExcluir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_solicita);

        pdao = new PessoaDAO(this);
        SharedPreferences sharedPreferences =  getBaseContext().getSharedPreferences("10lifegoals", Context.MODE_PRIVATE);
        final String usuario = sharedPreferences.getString("usuario", "Usário não encontrado");

        solicita = new AmigoDAO(this).procurarSolicitacoes(usuario);

        listasolic = (ListView) findViewById(R.id.listaSolic);
        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, solicita);
        listasolic.setAdapter(adaptador);

        listasolic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                telaOutroUsuario(usuario,pdao.buscarPessoa(solicita.get(i)), new MetaDAO(getBaseContext()).procurarMetas(solicita.get(i)));
            }
        });


    }

    public void telaOutroUsuario(final String usuario, final Pessoa pessoa, ArrayList<String> metas){
        setContentView(R.layout.outro_usuario);

        lstMetas = (ListView) findViewById(R.id.listaMetasPerfil);
        txtnome = (TextView) findViewById(R.id.txtNome);
        txtuser= (TextView) findViewById(R.id.txtUsuario);
        txtbio = (TextView) findViewById(R.id.txtBio);
        txtdata = (TextView) findViewById(R.id.txtDataNasc);
        mage = (ImageView) findViewById(R.id.imageView);
        solicitar = (Button) findViewById(R.id.btnSolicitar);
        solicitar.setVisibility(View.INVISIBLE);

        txtSolic = (TextView) findViewById(R.id.txtSolic);
        txtSolic.setVisibility(View.VISIBLE);
        txtSolic.setText("Deseja aceitar a solicitação?");
        btnSim = (Button) findViewById(R.id.btnSim);
        btnSim.setVisibility(View.VISIBLE);
        btnNao = (Button) findViewById(R.id.btnNao);
        btnExcluir = (Button) findViewById(R.id.btnExcluir);
        btnNao.setVisibility(View.VISIBLE);

        btnSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSim.setVisibility(View.INVISIBLE);
                btnNao.setVisibility(View.INVISIBLE);
                txtSolic.setText("Amigos");
                btnExcluir.setVisibility(View.VISIBLE);
                new AmigoDAO(getBaseContext()).atualizar(pessoa.getUsuario(),usuario);
            }
        });

        btnNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AmigoDAO(getBaseContext()).remover(pessoa.getUsuario(),usuario);
                solicitar.setVisibility(View.VISIBLE);
                btnExcluir.setVisibility(View.INVISIBLE);
                txtSolic.setVisibility(View.INVISIBLE);
                btnSim.setVisibility(View.INVISIBLE);
                btnNao.setVisibility(View.INVISIBLE);
                solicitar.setText("Solicitar amizade");
            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                solicitar.setVisibility(View.VISIBLE);
                solicitar.setText("Solicitar amizade");
                txtSolic.setVisibility(View.INVISIBLE);
                btnExcluir.setVisibility(View.INVISIBLE);
                new AmigoDAO(getBaseContext()).remover(pessoa.getUsuario(), usuario);
            }
        });

            solicitar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnExcluir.setText("Cancelar solicitação");
                    btnExcluir.setVisibility(View.VISIBLE);
                    Toast.makeText(OutroUsuario.this, "Solicitação enviada!", Toast.LENGTH_LONG).show();
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
