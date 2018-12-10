package com.example.aluno.myapplication.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aluno.myapplication.Mail;
import com.example.aluno.myapplication.Modelo.Pessoa;
import com.example.aluno.myapplication.PessoaDAO;
import com.example.aluno.myapplication.R;

/**
 * Created by Aluno on 20/08/2018.
 */

public class TelaAjuda extends Fragment {
    EditText edAjuda;
    Button btnAjuda;
    String texto = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View rootView = inflater.inflate(R.layout.ajuda, container, false);

        edAjuda=(EditText)rootView.findViewById(R.id.edAjuda);
        btnAjuda = (Button)rootView.findViewById(R.id.btnAjuda);
        btnAjuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarEmail();

            }
        });


        return rootView;
    }

    public void enviarEmail(){
        edAjuda.setText(texto);
        Toast.makeText(getContext(), "Mensagem enviada!", Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("10lifegoals", Context.MODE_PRIVATE);
        String usuario = sharedPreferences.getString("usuario", "Usuário não encontrado");
        PessoaDAO pdao = new PessoaDAO(getContext());
        Pessoa p = pdao.buscarPessoa(usuario);
        final String emailUsu = p.getEmail();


        //parte que vai no email pro usu
        final String email = emailUsu;
        final String assunto = "Agradecimento 10life goals";
        final String corpo = "Agradecemos a sua mensagem, nossa equipe trabalhará arduamente para respondê-la o mais rápido possível!";
        //fim

        //parte que vai no email pra gente
        final String email2 = "10lifegoals@gmail.com";
        final String assunto2 = "Email do Usuário";
        final String corpo2 = "O usuario" + usuario + ", com email" + emailUsu + "mandou a seguinte mensagem : " +edAjuda.getText().toString();
        //fim

        if(!isOnline()) {
            Toast.makeText(getContext(), "Não estava online para enviar e-mail!", Toast.LENGTH_SHORT).show();
            System.exit(0);
        }

        final Mail m = new Mail(email, assunto, corpo);
        new Thread(){
            @Override
            public void run() {
                m.enviarGmail();
            }
        }.start();

        final Mail m10 = new Mail(email2, assunto2, corpo2);
        new Thread(){
            @Override
            public void run() {
                m10.enviarGmail();
            }
        }.start();
    }



    public boolean isOnline() {
        try {
            ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }
        catch(Exception ex){
            Toast.makeText(getContext(), "Erro ao verificar se estava online! (" + ex.getMessage() + ")", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
