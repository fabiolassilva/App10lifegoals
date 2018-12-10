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
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.aluno.myapplication.Modelo.Pessoa;
import com.example.aluno.myapplication.adapters.MyFragmentPagerAdapter;


public class MenuActivity extends AppCompatActivity {
    //combo boxes periodo e publicar
    Spinner periodo;
    Spinner publicar;

    //TelaMetas
    EditText desc1;
    EditText desc2;
    EditText desc3;
    EditText desc4;
    EditText desc5;
    EditText desc6;
    EditText desc7;
    EditText desc8;
    EditText desc9;
    EditText desc10;
    Button btnCadastrar;

    private Button btnTermos;
    // parte do sobre
    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private TextView[] mDots;
    private SliderAdapter sliderAdapter;
    private Button mNextBtn;
    private Button mBackBtn;
    private int mCurrentPage;
    // fim da parte do sobre

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Button btnEntrada;
    private Button btnTelaCriar;
    private Button btnCriar;


    private Button btnMais;
    private Button btnVoltar;
    private Button btn;
    private Button btnSair;


    private int[] tabIcons = {
            R.drawable.casa,
            R.drawable.metas,
            R.drawable.perfil,
            R.drawable.ajuda,
            R.drawable.saida
    };

    private PessoaDAO pdao;
    private MetaDAO mdao;
    private EditText edUsuario;
    private EditText edEmail;
    private EditText edSenha;
    private EditText confirmeSenha;
    private EditText edNome;
    private CheckBox chkTermos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mdao = new MetaDAO(getBaseContext());
        SharedPreferences sharedPreferences = getSharedPreferences("10lifegoals", Context.MODE_PRIVATE);
        String usuario = sharedPreferences.getString("usuario", "");
        if (usuario == "") {
            setContentView(R.layout.principal);
            btnEntrada = (Button) findViewById(R.id.btnTelaEntrar);
            btnEntrada.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MenuActivity.this, tela_entrada.class);
                    startActivity(i);
                    MenuActivity.this.finish();
                }
            });

            btnTelaCriar = (Button) findViewById(R.id.btnTelaCriar);
            btnTelaCriar.setOnClickListener((new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    telaCriar();
                }
            }));

            btnMais = (Button) findViewById(R.id.btnMais);
            btnMais.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mais();
                }
            });


            pdao = new PessoaDAO(this);
        } else {
            Intent i = new Intent(MenuActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }


    }


    public void telaPerfil() {
        setContentView(R.layout.editar_perfil);
    }


    public void telaIn() {
        setContentView(R.layout.inicio);
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

    public void telaMetas() {
        setContentView(R.layout.metas);

        //telametas
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        //tab1
        TabHost.TabSpec spec = tabHost.newTabSpec("Atuais");
        spec.setContent(R.id.Atuais);
        spec.setIndicator("Atuais");
        tabHost.addTab(spec);

        //tab2
        spec = tabHost.newTabSpec("Concluídas");
        spec.setContent(R.id.Concluídas);
        spec.setIndicator("Concluídas");
        tabHost.addTab(spec);

        //tab2
        spec = tabHost.newTabSpec("Salvas");
        spec.setContent(R.id.Salvas);
        spec.setIndicator("Salvas");
        tabHost.addTab(spec);
    }

    public void telaCriar() {
        setContentView(R.layout.activity_tela_criar_conta);

        pdao = new PessoaDAO(this);
        pdao.abrir();

        /*List<Livro> livros = LivroDAO.lerLivros();
        ArrayAdapter<Livro> adaptador = new ArrayAdapter<Livro>(this, android.R.layout.simple_list_item_checked, livros);
        this.setListAdapter(adaptador);

        this.getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);*/
        edEmail = (EditText) findViewById(R.id.edEmail);
        edUsuario = (EditText) findViewById(R.id.edUsuario);
        edSenha = (EditText) findViewById(R.id.edSenha);
        confirmeSenha = (EditText) findViewById(R.id.edConfirme);
        edNome = (EditText) findViewById(R.id.edNome);
        chkTermos = (CheckBox) findViewById(R.id.chTermos);
        btnTermos = (Button) findViewById(R.id.btnTermos);
        btnTermos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog(this);
            }
        });
        btnCriar = (Button) findViewById(R.id.btnCriar);
        btnCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edEmail.getText().toString();
                String usuario = edUsuario.getText().toString();
                String senha = edSenha.getText().toString();
                String confirme = confirmeSenha.getText().toString();
                String nome = edNome.getText().toString();
                String termos;
                if (chkTermos.isChecked())
                    termos = "s";
                else {
                    termos = "n";
                }

                ArrayList<String> usuarios = pdao.procuraUsuario();
                if (usuarios.size() > 0) {
                    for (int j = 0; j < usuarios.size(); j++) {
                        if (!usuario.equals(usuarios.get(j))) {
                            usuario = edUsuario.getText().toString();
                            if (senha.equals(confirme)) {
                                if (validarEmail(email) == true) {
                                    if (termos.equals("s")) {
                                        if (!email.equals("")) {
                                            if (!usuario.equals("")) {
                                                if (!senha.equals("")) {

                                                    pdao = new PessoaDAO(MenuActivity.this);
                                                    Pessoa pessoa = pdao.cadastrarPessoa(usuario, nome, senha, email, null, termos, null, null);

                                                    ArrayAdapter<Pessoa> adaptador = new ArrayAdapter<Pessoa>(MenuActivity.this.getApplicationContext(), 0);
                                                    adaptador.add(pessoa);
                                                    adaptador.notifyDataSetChanged();

                                                    Intent i = new Intent(MenuActivity.this, TelaCadastrarMetas.class);
                                                    startActivity(i);
                                                }
                                            }
                                        }

                                    } else {
                                        Toast.makeText(getBaseContext(), "Você precisa aceitar os Termos de Uso para ter acesso ao sistema.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getBaseContext(), "Email inválido! Digite novamente. ", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getBaseContext(), "As senhas não estão iguais. Digite novamente.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getBaseContext(), "Esse nome de usuário já existe. Tente novamente.", Toast.LENGTH_SHORT).show();
                            usuario = edUsuario.getText().toString();
                        }
                    }
                } else {
                    usuario = edUsuario.getText().toString();
                    if (senha.equals(confirme)) {
                        if (validarEmail(email) == true) {
                            if (termos.equals("s")) {
                                if (!email.equals("")) {
                                    if (!usuario.equals("")) {
                                        if (!senha.equals("")) {

                                            pdao = new PessoaDAO(MenuActivity.this);
                                            Pessoa pessoa = pdao.cadastrarPessoa(usuario, nome, senha, email, null, termos, null, null);

                                            ArrayAdapter<Pessoa> adaptador = new ArrayAdapter<Pessoa>(MenuActivity.this.getApplicationContext(), 0);
                                            adaptador.add(pessoa);
                                            adaptador.notifyDataSetChanged();

                                            Intent i = new Intent(MenuActivity.this, TelaCadastrarMetas.class);
                                            startActivity(i);
                                        }
                                    }
                                }

                            } else {
                                Toast.makeText(getBaseContext(), "Você precisa aceitar os Termos de Uso para ter acesso ao sistema.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getBaseContext(), "Email inválido! Digite novamente. ", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getBaseContext(), "As senhas não estão iguais. Digite novamente.", Toast.LENGTH_SHORT).show();
                    }
                }


                SharedPreferences sharedPreferences = getSharedPreferences("10lifegoals", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor;
                editor = sharedPreferences.edit(); //Escrever dentro do arquivo

                editor.putString("usuario", usuario);
                editor.putString("nome", nome);
                editor.putString("senha", senha);
                editor.putString("email", email);
                editor.putString("termos", termos);
                editor.commit();

                //telaCadM();

            }
        });
    }

    public static boolean validarEmail(String email)
    {
        boolean valido = false;
        if (email != null && email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                valido = true;
            }
        }
        return valido;
    }



    public void showInputDialog(View.OnClickListener context) {
        final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        //Cria a view a ser utilizada no dialog
        View view = inflater.inflate(R.layout.termos_uso, null);

        alertDialog.setView(view).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alertDialog.show();
    }

    public void telaInicial() {
        setContentView(R.layout.activity_main);

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

    public void mais() {
        setContentView(R.layout.activity_main_sobre);

        //sobre
        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.dotsLayout);

        mNextBtn = (Button) findViewById(R.id.nextBtn);
        mBackBtn = (Button) findViewById(R.id.prevBtn);

        sliderAdapter = new SliderAdapter(this);
        mSlideViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);

        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSlideViewPager.setCurrentItem(mCurrentPage + 1);
            }
        });
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mSlideViewPager.setCurrentItem(mCurrentPage - 1);
            }
        });
        //fim sobre

        btnVoltar = (Button) findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voltar();
            }
        });
    }

    public void voltar() {
        //telaCadM();
        Intent i = new Intent(MenuActivity.this, MenuActivity.class);
        startActivity(i);
    }


    //sobre funcoes

    public void addDotsIndicator(int position) {
        mDots = new TextView[3];
        mDotLayout.removeAllViews();

        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(27);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));
            mDotLayout.addView(mDots[i]);
        }
        if (mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            mCurrentPage = position;
            if (position == 0) {
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(false);
                mBackBtn.setVisibility(View.INVISIBLE);
                mNextBtn.setText("Próximo");
                mBackBtn.setText("");

            } else if (position == mDots.length - 1) {
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.VISIBLE);
                mNextBtn.setText("Fim");
                mNextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        voltar();
                    }
                });
                btnVoltar.setEnabled(false);
                mBackBtn.setText("Anterior");

            } else {
                mNextBtn.setEnabled(true);
                mBackBtn.setEnabled(true);
                mBackBtn.setVisibility(View.VISIBLE);
                mNextBtn.setText("Próximo");
                mBackBtn.setText("Anterior");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

// fim sobre fucoes

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        return true;
    }


    /*public class SearchFiltro implements SearchView.OnQueryTextListener{

        @Override
        public boolean onQueryTextSubmit(String query) { //texto que for digitado lá
            return false; //ele trata de forma padrao
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    } */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }
}