package com.example.aluno.myapplication.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.aluno.myapplication.MenuActivity;
import com.example.aluno.myapplication.R;

/**
 * Created by Aluno on 20/08/2018.
 */

public class TelaSair extends Fragment{
    Button btnSair;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.sair, container, false);
        btnSair = (Button) view.findViewById(R.id.btnSair);
        btnSair.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                telaSair();
            }
        }));
        return view;

    }

    public void telaSair(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("10lifegoals", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = sharedPreferences.edit(); //Escrever dentro do arquivo
        editor.remove("nome");
        editor.remove("email");
        editor.remove("usuario");
        editor.remove("senha");
        editor.remove("bio");
        editor.remove("niver");

        for(int i=0;i<=10;i++){
            editor.remove("meta "+i);
            editor.remove("pe "+i);
        }

        editor.commit();
        Intent i = new Intent(getActivity(), MenuActivity.class);
        startActivity(i);
        getActivity().finish();
    }
}
