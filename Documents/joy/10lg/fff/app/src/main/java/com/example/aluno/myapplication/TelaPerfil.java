package com.example.aluno.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aluno.myapplication.Modelo.Pessoa;

/**
 * Created by Aluno on 20/08/2018.
 */

public class TelaPerfil extends Fragment {
    private Button btnEditarPerfil;
    private EditText edtUser;
    private TextView txtNome;
    private TextView txtUsuario;
    private TextView txtDataNasc;
    private TextView txtEmail;
    private TextView txtBio;
    private ArrayAdapter<String> adaptador;
    private Context context;
    private PessoaDAO pdao;
    private Pessoa pessoa;
    ImageView image;
    Button btnAmigos;
    Button btnsolic;

    @Override
    public void onAttach(Context context){
        this.context = context;
        super.onAttach(context);
    }


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState){
        final View rootView = inflater.inflate(R.layout.perfil, container, false);
        txtEmail = (TextView)rootView.findViewById(R.id.txtEmail);
        txtNome =   (TextView)rootView.findViewById(R.id.txtNome);
        txtDataNasc = (TextView)rootView.findViewById(R.id.txtDataNasc);
        image = (ImageView) rootView.findViewById(R.id.imageView);
        txtUsuario = (TextView)rootView.findViewById(R.id.txtUsuario);
        txtBio = (TextView) rootView.findViewById(R.id.txtBio);
        btnEditarPerfil = (Button) rootView.findViewById(R.id.btnEditarPerfil);
        btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), EditarPerfil.class);
                startActivity(i);
            }
        });

        btnAmigos = (Button) rootView.findViewById(R.id.btnAmigos);
        btnAmigos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ListaAmigos.class);
                startActivity(i);
            }
        });

        btnsolic = (Button) rootView.findViewById(R.id.btnSolici);
        btnsolic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), OutroUsuario.class);
                startActivity(i);
            }
        });

        SharedPreferences sharedPreferences =  getContext().getSharedPreferences("10lifegoals", Context.MODE_PRIVATE);
        String usuario = sharedPreferences.getString("usuario", "Usário não encontrado");
        String niver = sharedPreferences.getString("niver", "Data de aniversário não encontrada");
        String nome = sharedPreferences.getString("nome", "Nome não encontrado");
        String email = sharedPreferences.getString("email", "Email não encontrado");
        String bio = sharedPreferences.getString("bio", "Clique em 'Editar Perfil' e adicione uma biografia");



        pdao = new PessoaDAO(context);
        pessoa = pdao.buscarPessoa(usuario);
        if(!niver.equals("Data de aniversário não encontrada")){
            txtDataNasc.setText(niver);}
        if(pessoa.getImage()!=null)
            image.setImageBitmap(pessoa.getImage());

        txtUsuario.setText(usuario);
        txtNome.setText(nome);
        txtEmail.setText(email);
        txtBio.setText(bio);
        return rootView;
    }


}
