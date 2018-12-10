package com.example.aluno.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aluno.myapplication.DAO.AmigoDAO;
import com.example.aluno.myapplication.Modelo.Pessoa;
import com.example.aluno.myapplication.adapters.MyFragmentPagerAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private int[] tabIcons = {
            R.drawable.casa,
            R.drawable.metas,
            R.drawable.perfil,
            R.drawable.ajuda,
            R.drawable.saida
    };

    ListView list;
    ListView lstMetas;
    SearchView searchView;
   ListView lista;

    String usuario;
    //CustomAdapter adapter;
    private ArrayAdapter<String> adapter;
    ArrayList<String> pessoa;
    ArrayAdapter adaptador;

    Button solicitar;
    TextView txtnome;
    TextView txtuser;
    TextView txtbio;
    TextView txtdata;
    ImageView mage;
    private TextView txtSolic;
    private Button btnSim;
    private Button btnNao;
    private Button btnExcluir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences =  getBaseContext().getSharedPreferences("10lifegoals", Context.MODE_PRIVATE);
        usuario = sharedPreferences.getString("usuario", "Usário não encontrado");

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        mViewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), getResources().getStringArray(R.array.titles_tab)));
        mTabLayout.setupWithViewPager(mViewPager);

        mTabLayout.getTabAt(0).setIcon(tabIcons[0]);
        mTabLayout.getTabAt(1).setIcon(tabIcons[1]);
        mTabLayout.getTabAt(2).setIcon(tabIcons[2]);
        mTabLayout.getTabAt(3).setIcon(tabIcons[3]);
        mTabLayout.getTabAt(4).setIcon(tabIcons[4]);
    }

    private void pesquisarUsuario(String pesquisa) {
        final PessoaDAO dao = new PessoaDAO(this);
        final MetaDAO mdao = new MetaDAO(this);
        pessoa = dao.pesquisar(pesquisa, usuario);
        if (pessoa.isEmpty()) {
            Toast.makeText(getBaseContext(), "Usuário não encontrado", Toast.LENGTH_SHORT).show();
        } else {
            setContentView(R.layout.pesquisa_usuario);
            list = (ListView) findViewById(R.id.listPesquisa);
            adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, pessoa);
            //adapter = new CustomAdapter(this, pessoa);
            list.setAdapter(adapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    System.out.println(pessoa.get(i));
                    telaOutroUsuario(usuario, dao.buscarPessoa(pessoa.get(i)), mdao.procurarMetas(pessoa.get(i)));
                }
            });

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem lupa = menu.findItem(R.id.action_search);
        searchView = (SearchView) lupa.getActionView();
        searchView.setQueryHint("Pesquisar");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("entrei aqui");
                pesquisarUsuario(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                System.out.println("entrei no change");
                return false;
            }
        });

        MenuItem estrela = menu.findItem(R.id.action_star);

        return true;
    }

    public void telaEditaPerfil(){
        setContentView(R.layout.editar_perfil);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        System.out.println("sasasassasas");
        switch (id){
            case R.id.action_star:
                showInputDialog(MainActivity.this);
        }
        return true;

    }

    public void showInputDialog(Context context) {
        final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        ArrayAdapter adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mostrarTop());
        adapter3.notifyDataSetChanged();

        //Cria a view a ser utilizada no dialog
        View view = inflater.inflate(R.layout.top10, null);
        lista = view.findViewById(R.id.listaTop);
        lista.setAdapter(adapter3);
        alertDialog.setView(view).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog.show();
    }

    public ArrayList<String> mostrarTop(){

        SalvaDAO sdao = new SalvaDAO(this);
        ArrayList<String> topmetas = new ArrayList<String>();
        topmetas = sdao.top10Metas();

    return topmetas;
    }

    public void telaOutroUsuario(final String usuario, final Pessoa pessoa, ArrayList<String> metas) {
        setContentView(R.layout.outro_usuario);

        final AmigoDAO amgd = new AmigoDAO(this);


        lstMetas = (ListView) findViewById(R.id.listaMetasPerfil);
        txtnome = (TextView) findViewById(R.id.txtNome);
        txtuser = (TextView) findViewById(R.id.txtUsuario);
        txtbio = (TextView) findViewById(R.id.txtBio);
        txtdata = (TextView) findViewById(R.id.txtDataNasc);
        mage = (ImageView) findViewById(R.id.imageView);
        solicitar = (Button) findViewById(R.id.btnSolicitar);
        btnExcluir = (Button) findViewById(R.id.btnExcluir);
        txtSolic = (TextView) findViewById(R.id.txtSolic);
        btnSim = (Button) findViewById(R.id.btnSim);
        btnSim.setVisibility(View.INVISIBLE);
        btnNao = (Button) findViewById(R.id.btnNao);
        btnNao.setVisibility(View.INVISIBLE);


        //if (amgd.ehAmigo(pessoa.getNome()) == true){
           /* solicitar.setText("Solicitar amizade");
            solicitar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    amgd.adicionarAmigo(usuario, pessoa.getNome());
                    Toast.makeText(getBaseContext(), "Solicitação enviada!", Toast.LENGTH_SHORT);
                }
            });*/

        //} else {
        if (!amgd.mandouSolicitacao(usuario, pessoa.getUsuario())) {
            System.out.println("n eh solicitar");
            if (amgd.procurarAmigos(usuario, pessoa.getUsuario())) {
                System.out.println("ele eh amigo");
                txtSolic.setText(" Amigos");
                solicitar.setVisibility(View.INVISIBLE);
                btnSim.setVisibility(View.INVISIBLE);
                btnNao.setVisibility(View.INVISIBLE);
                btnExcluir.setVisibility(View.VISIBLE);
            } else {
                if (amgd.recebeuSolicitacao(pessoa.getUsuario(), usuario)) {
                    System.out.println("n eh amigo");
                    solicitar.setVisibility(View.INVISIBLE);
                    txtSolic = (TextView) findViewById(R.id.txtSolic);
                    txtSolic.setVisibility(View.VISIBLE);
                    txtSolic.setText("Deseja aceitar a solicitação?");
                    btnSim = (Button) findViewById(R.id.btnSim);
                    btnSim.setVisibility(View.VISIBLE);
                    btnNao = (Button) findViewById(R.id.btnNao);
                    btnNao.setVisibility(View.VISIBLE);

                    btnSim.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            btnSim.setVisibility(View.INVISIBLE);
                            btnNao.setVisibility(View.INVISIBLE);
                            txtSolic.setText("Amigos");
                            solicitar.setVisibility(View.INVISIBLE);
                            new AmigoDAO(getBaseContext()).atualizar(pessoa.getUsuario(), usuario);
                            btnExcluir.setVisibility(View.VISIBLE);
                        }
                    });

                    btnNao.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            new AmigoDAO(getBaseContext()).remover(pessoa.getUsuario(), usuario);
                            solicitar.setVisibility(View.VISIBLE);
                            btnExcluir.setVisibility(View.INVISIBLE);
                            txtSolic.setVisibility(View.INVISIBLE);
                            btnSim.setVisibility(View.INVISIBLE);
                            btnNao.setVisibility(View.INVISIBLE);
                            solicitar.setText("Solicitar amizade");
                            solicitar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Log.e("yan2","entrou de novo");
                                    amgd.adicionarAmigo(usuario, pessoa.getUsuario());
                                    solicitar.setVisibility(View.INVISIBLE);
                                    btnExcluir.setVisibility(View.VISIBLE);
                                    btnExcluir.setText("Cancelar solicitação");

                                    Toast.makeText(MainActivity.this, "Solicitação enviada!", Toast.LENGTH_LONG).show();//NÃO SEI PQ MAS N APARECE ESSA PORARIAAAAAAAAAA
                                    //toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
                                    //toast.show();

                                    Log.e("yan3","entrou + de novo");
                                }
                            });
                        }
                    });

                }else{
                    Log.e("yan1","entrou");
                    solicitar.setText("Solicitar amizade");
                    solicitar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.e("yan2","entrou de novo");
                            amgd.adicionarAmigo(usuario, pessoa.getUsuario());
                            solicitar.setVisibility(View.INVISIBLE);
                            btnExcluir.setVisibility(View.VISIBLE);
                            btnExcluir.setText("Cancelar solicitação");
                            txtSolic.setText("Aguardando a resposta da solicitação de amizade");

                            Toast.makeText(MainActivity.this, "Solicitação enviada!", Toast.LENGTH_LONG).show();//NÃO SEI PQ MAS N APARECE ESSA PORARIAAAAAAAAAA
                            //toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
                            //toast.show();

                            Log.e("yan3","entrou + de novo");
                        }
                    });}
            }
        } else {
            txtSolic.setText("Aguardando a resposta da solicitação de amizade");
            solicitar.setVisibility(View.INVISIBLE);
            btnSim.setVisibility(View.INVISIBLE);
            btnNao.setVisibility(View.INVISIBLE);
            btnExcluir.setVisibility(View.VISIBLE);
            btnExcluir.setText("Cancelar solicitação");
        }


        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AmigoDAO(getBaseContext()).remover(pessoa.getUsuario(), usuario);
                solicitar.setVisibility(View.VISIBLE);
                btnExcluir.setVisibility(View.INVISIBLE);
                txtSolic.setVisibility(View.INVISIBLE);
                solicitar.setText("Solicitar amizade");
                solicitar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("yan2","entrou de novo");
                        amgd.adicionarAmigo(usuario, pessoa.getUsuario());
                        solicitar.setVisibility(View.INVISIBLE);
                        btnExcluir.setVisibility(View.VISIBLE);
                        btnExcluir.setText("Cancelar solicitação");

                        Toast.makeText(MainActivity.this, "Solicitação enviada!", Toast.LENGTH_LONG).show();//NÃO SEI PQ MAS N APARECE ESSA PORARIAAAAAAAAAA
                        //toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
                        //toast.show();

                        Log.e("yan3","entrou + de novo");
                    }
                });
                Log.e("yan4]","entrou");
            }
        });

        adaptador = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, metas);
        lstMetas.setAdapter(adaptador);

        txtdata.setText(pessoa.getDataNasc());
        mage.setImageBitmap(pessoa.getImage());

        txtuser.setText(pessoa.getUsuario());
        txtnome.setText(pessoa.getNome());
        txtbio.setText(pessoa.getBio());

    }

    public void onBackPressed() {
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);

        return;
    }

}