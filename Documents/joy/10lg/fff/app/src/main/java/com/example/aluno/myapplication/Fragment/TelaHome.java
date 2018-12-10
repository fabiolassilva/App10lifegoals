package com.example.aluno.myapplication.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.aluno.myapplication.DAO.AmigoDAO;
import com.example.aluno.myapplication.MetaDAO;
import com.example.aluno.myapplication.Modelo.Meta;
import com.example.aluno.myapplication.R;
import com.example.aluno.myapplication.TelaInicio;

import java.util.ArrayList;

/**
 * Created by Aluno on 20/08/2018.
 */

public class TelaHome extends Fragment {


    private ListView listView;
    //salvar meta

    private TextView txtDescrMeta;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState){
        final View rootView = inflater.inflate(R.layout.inicio, container, false);

        listView = (ListView)rootView.findViewById(R.id.listaHome);
        ArrayAdapter adapter = new TelaInicio(this.getActivity(), adcMetas());
        listView.setAdapter(adapter);


        return rootView;
    }

    private ArrayList<Meta> adcMetas(){
        int i = 0;
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("10lifegoals", Context.MODE_PRIVATE);
        String usuario = sharedPreferences.getString("usuario", "Usuário não encontrado");
        MetaDAO mdao = new MetaDAO(getActivity());
        ArrayList<Meta> meta = new ArrayList<Meta>();
        ArrayList<String> amigos;
        amigos = new AmigoDAO(getActivity()).listarAmigos(usuario);
        for (i = 0; i < amigos.size(); i++) {
            ArrayList<Meta> m = mdao.metasHome(amigos.get(i));
            for (int j = 0; j < m.size(); j++) {
                meta.add(m.get(j));
                System.out.println("M "+ m.get(j).getNumero() + "\n");
            }
            System.out.println("estou nesse for");
        }
        //meta.add(mdao.metasHome(amigos.get(i)));

        return meta;
    }
}
