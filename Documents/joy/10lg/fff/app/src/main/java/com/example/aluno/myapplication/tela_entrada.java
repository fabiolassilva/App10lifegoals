package com.example.aluno.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aluno.myapplication.Modelo.Meta;
import com.example.aluno.myapplication.Modelo.Pessoa;

import java.util.List;

public class tela_entrada extends AppCompatActivity {
    private PessoaDAO pdao;
    private MetaDAO mdao;
    private Button btnEntrar;
    private EditText edUsu;
    private EditText edSen;


    private Button btnEsqueci;
    public EditText txtEmail;
    private EditText txtUsuario;
    private Boolean login = false;
    private String usuario="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_entrada);

        pdao = new PessoaDAO(this);
        mdao = new MetaDAO(this);
        edUsu = (EditText)findViewById(R.id.edUsu);
        edSen = (EditText)findViewById(R.id.edSen);
        btnEsqueci = (Button)findViewById(R.id.btnEsqueci);
        //txtEmail = (EditText)findViewById(R.id.emailEsqueci);
        /*txtUsuario = (EditText)findViewById(R.id.usuEsqueci);
        usuario = txtUsuario.getText().toString();*/
        btnEsqueci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog(this);
            }
        });
        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = edUsu.getText().toString();
                String senha = edSen.getText().toString();
                if(!usuario.equals("")){
                    if(!senha.equals("")){

                        login = pdao.carregaLogin(usuario,senha);
                        if(login == true){
                            //Instancia pessoa logada
                            Pessoa pessoa = pdao.buscarPessoa(usuario);
                            List<Meta> metas = mdao.lerMetas(usuario);

                            Log.e("Seila", pessoa.getNome());

                            SharedPreferences sharedPreferences = getSharedPreferences("10lifegoals", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor;
                            editor = sharedPreferences.edit(); //Escrever dentro do arquivo

                            editor.putString("usuario", pessoa.getUsuario());
                            editor.putString("nome", pessoa.getNome());
                            editor.putString("senha", pessoa.getSenha());
                            editor.putString("email", pessoa.getEmail());
                            editor.putString("datanasc", pessoa.getDataNasc());
                            editor.putString("termos", pessoa.getTermos());
                            editor.putString("niver", pessoa.getDataNasc());
                            editor.putString("bio", pessoa.getBio());

                            for(int i=0;i<=9;i++){
                                if(metas.get(i).getSituacao().equals("C")){
                                    editor.putString("meta "+i,"Meta concluída");
                                    editor.putString("pe "+i,"");}
                                else{
                                    editor.putString("meta "+i, metas.get(i).getDescricao());
                                    editor.putString("pe "+i, ""+(metas.get(i).getPeriodo()));}
                            }
                            editor.commit();

                            Intent i = new Intent(tela_entrada.this, MainActivity.class);
                            startActivity(i);
                        } else
                            Toast.makeText(getApplicationContext(),"Nome de usuario ou senha incorretos. Tente Novamente!", Toast.LENGTH_SHORT).show();


                    }

                }

            }
        });
    }

    public void showInputDialog(View.OnClickListener context){
        final android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        //Cria a view a ser utilizada no dialog
        View view = inflater.inflate(R.layout.esqueci_senha,null);
        txtUsuario = (EditText) view.findViewById(R.id.usuEsqueci);

        txtEmail = (EditText) view.findViewById(R.id.emailEsqueci);

        alertDialog.setView(view).setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                enviarEmail();
            }
        });
        alertDialog.show();
    }

    public void enviarEmail(){
        usuario = txtUsuario.getText().toString();
        PessoaDAO pdao = new PessoaDAO(this);
        Pessoa p = pdao.buscarPessoa(usuario);
        final String senha = p.getSenha() ;

        //parte que vai no email
        final String email =  txtEmail.getText().toString();
        final String assunto = "Recuperação de senha";
        final String corpo = "Sua senha é " + senha + ". Caso queira mudar, vá na Tela de Perfil, no botão Editar Perfil e altere sua senha." +"\n\n\n\n"+ "Não responda a esse email!";
        //fim

        if(!isOnline()) {
            Toast.makeText(getApplicationContext(), "Não estava online para enviar e-mail!", Toast.LENGTH_SHORT).show();
            System.exit(0);
        }

        final Mail m = new Mail(email, assunto, corpo);
        new Thread(){
            @Override
            public void run() {
                m.enviarGmail();
            }
        }.start();
    }



    public boolean isOnline() {
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }
        catch(Exception ex){
            Toast.makeText(getApplicationContext(), "Erro ao verificar se estava online! (" + ex.getMessage() + ")", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
