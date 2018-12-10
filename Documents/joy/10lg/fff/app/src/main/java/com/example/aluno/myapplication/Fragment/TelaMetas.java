package com.example.aluno.myapplication.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.aluno.myapplication.Mail;
import com.example.aluno.myapplication.MainActivity;
import com.example.aluno.myapplication.MetaDAO;
import com.example.aluno.myapplication.Modelo.Meta;
import com.example.aluno.myapplication.Modelo.PessoaSalvaMeta;
import com.example.aluno.myapplication.PessoaDAO;
import com.example.aluno.myapplication.R;
import com.example.aluno.myapplication.SalvaDAO;
import com.example.aluno.myapplication.TelaAtuais;
import com.example.aluno.myapplication.TelaConcluidas;
import com.example.aluno.myapplication.TelaSalvas;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by Aluno on 20/08/2018.
 */

public class TelaMetas extends Fragment {
    TabHost tabHost;
    private Context context;
    private static ListView lstConcluida;
    private Button novaM;
    int na = 0;
    private ListView listaSalvas;
    public static ListView listaAtuais;
    MetaDAO mdao;

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.metas, container, false);

        //telametas
        tabHost = (TabHost) rootView.findViewById(R.id.tabHost);
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

        //parte do adicionar meta
        novaM = (Button) rootView.findViewById(R.id.novaM);



        final SharedPreferences sharedPreferences = getContext().getSharedPreferences("10lifegoals", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor;
        editor = sharedPreferences.edit(); //Escrever dentro do arquivo
        final String usuario = sharedPreferences.getString("usuario", "Usuário não encontrado");

        novaM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog(context, usuario);
            }
        });

        mdao = new MetaDAO(getContext());
        //num = mdao.procurarNum(usuario);

        TelaSalvas adapterSalvas = new TelaSalvas(this.getActivity(), mostrarSalvas());
        TelaConcluidas adapterConcluidas = new TelaConcluidas(this.getActivity(), mostrarConcluidas()); //adaptador1 = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, concluidas);
        TelaAtuais adapterAtuais = new TelaAtuais(this.getActivity(), mostrarAtuais(), adapterConcluidas);


        listaAtuais = (ListView) rootView.findViewById(R.id.listaMetasAtuais);
        listaAtuais.requestLayout();
        listaAtuais.requestLayout();
        listaAtuais.setAdapter(adapterAtuais);
        listaAtuais.requestLayout();
        adapterAtuais.notifyDataSetChanged();

        lstConcluida = (ListView) rootView.findViewById(R.id.listaConcluida);
        lstConcluida.requestLayout();
        lstConcluida.requestLayout();
        lstConcluida.setAdapter(adapterConcluidas);
        lstConcluida.requestLayout();
        adapterConcluidas.notifyDataSetChanged();

        listaSalvas = (ListView) rootView.findViewById(R.id.listaMetasSalvas);
        listaSalvas.requestLayout();
        listaSalvas.requestLayout();
        listaSalvas.setAdapter(adapterSalvas);
        listaSalvas.requestLayout();
        adapterSalvas.notifyDataSetChanged();

        return rootView;
    }

    private ArrayList<Meta> mostrarAtuais() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("10lifegoals", Context.MODE_PRIVATE);
        String usuario = sharedPreferences.getString("usuario", "Usuário não encontrado");
        MetaDAO mdao = new MetaDAO(getActivity());
        ArrayList<Meta> metatuais = new ArrayList<Meta>();
        metatuais = mdao.metasAtuais(usuario);

        return metatuais;
    }

    private ArrayList<Meta> mostrarConcluidas() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("10lifegoals", Context.MODE_PRIVATE);
        String usuario = sharedPreferences.getString("usuario", "Usuário não encontrado");
        MetaDAO mdao = new MetaDAO(getActivity());
        ArrayList<Meta> metaconcluidas = new ArrayList<Meta>();
        metaconcluidas = mdao.metasConcluidas(usuario);

        return metaconcluidas;
    }

    private ArrayList<PessoaSalvaMeta> mostrarSalvas() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("10lifegoals", Context.MODE_PRIVATE);
        String usuario = sharedPreferences.getString("usuario", "Usuário não encontrado");
        SalvaDAO sdao = new SalvaDAO(getActivity());
        ArrayList<PessoaSalvaMeta> metasalvas = new ArrayList<PessoaSalvaMeta>();
        metasalvas = sdao.metasSalvas(usuario);

        return metasalvas;
    }


    public void showInputDialog(final Context context, final String user) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = getActivity().getLayoutInflater();

        //Cria a view a ser utilizada no dialog
        View view = inflater.inflate(R.layout.nova_meta, null);

        //Obtém uma referencia ao Spinner
        Spinner spinner = (Spinner) view.findViewById(R.id.spPeriodo);
        Spinner spinner2 = (Spinner) view.findViewById(R.id.spPublicar);
        final EditText desc = (EditText) view.findViewById(R.id.desc);

        //Cria o Adapter
        ArrayAdapter adapter = ArrayAdapter.createFromResource(context, R.array.periodo,
                android.R.layout.simple_spinner_dropdown_item);//ou android.R.layout.simple_spinner_item
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(context, R.array.publicar,
                android.R.layout.simple_spinner_dropdown_item);//ou android.R.layout.simple_spinner_item

        //Atribui o adapter ao spinner
        spinner.setAdapter(adapter);
        spinner2.setAdapter(adapter2);

        final int[] pe = {0};
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).toString() != null) {
                    if (adapterView.getItemAtPosition(i).toString().equals("1 semana"))
                        pe[0] = 7;
                    if (adapterView.getItemAtPosition(i).toString().equals("10 dias"))
                        pe[0] = 10;
                    if (adapterView.getItemAtPosition(i).toString().equals("1 mês"))
                        pe[0] = 30;
                    if (adapterView.getItemAtPosition(i).toString().equals("2 meses"))
                        pe[0] = 60;
                    if (adapterView.getItemAtPosition(i).toString().equals("3 meses"))
                        pe[0] = 90;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final String[] publi = {""};
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (adapterView.getItemAtPosition(i).toString() != null)
                    publi[0] = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        final SharedPreferences sharedPreferences = getContext().getSharedPreferences("10lifegoals", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor;
        editor = sharedPreferences.edit(); //Escrever dentro do arquivo

        final int j = getN();
        alertDialog.setView(view)
                .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        GregorianCalendar calendar = new GregorianCalendar();
                        int data = calendar.get(GregorianCalendar.DAY_OF_MONTH);
                        if (listaAtuais.getCount() < 10) {
                            mdao.criarMeta(desc.getText().toString(), publi[0], pe[0], "N", data, user);

                            editor.putString("meta " + j, desc.getText().toString());
                            if (pe[0] == 7)
                                editor.putString("pe " + j, "1 semana");
                            if (pe[0] == 10)
                                editor.putString("pe " + j, "10 dias");
                            if (pe[0] == 30)
                                editor.putString("pe " + j, "1 mês");
                            if (pe[0] == 60)
                                editor.putString("pe " + j, "2 meses");
                            if (pe[0] == 90)
                                editor.putString("pe " + j, "3 meses");
                            editor.commit();
                            lstConcluida.requestLayout();

                            Intent i = new Intent(getActivity(), MainActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(context, "Você só pode ter 10 metas por vez", Toast.LENGTH_SHORT).show();

                        }
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        lstConcluida.requestLayout();
        editor.commit();
        alertDialog.show();
    }

    public void novaMeta(int i) {
        this.na = i;
    }

    public int getN() {
        return na;
    }


    //tenativa email periodo
    public void emailPeriodo() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("10lifegoals", Context.MODE_PRIVATE);
        String usuario = sharedPreferences.getString("usuario", "Usuário não encontrado");
        PessoaDAO pdao = new PessoaDAO(getActivity());
        String nome = pdao.procuraNome(usuario);
        String email = pdao.procuraEmail(usuario);

        GregorianCalendar calendar = new GregorianCalendar();
        int hj = calendar.get(GregorianCalendar.DAY_OF_MONTH);
        Meta meta = new Meta();

        int total = calendar.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        int dia = meta.getData() + meta.getPeriodo();

        //parte que vai no email
        //final String email =  txtEmail.getText().toString();
        final String assunto = "ALERTA DE PERÍODO";
        final String corpoMeio = " Olá sr.(a)" + nome + "período de sua meta " + meta.getDescricao() + " " +
                "está pela metade. Não desista, divida com alguém e procure ajuda para cumpri-la. Boa sorte!" +
                "Atenciosamente " + "quipe 10LifeGoals.";
        final String corpoFim = " Olá sr.(a)" + nome + "período de sua meta " + meta.getDescricao() + " " +
                "terminou. Não desista, cadastre novamente e procure ajuda para cumpri-la. Boa sorte!" +
                "Atenciosamente " + "quipe 10LifeGoals.";

        final Mail mm = new Mail(email, assunto, corpoMeio);
        final Mail mf = new Mail(email, assunto, corpoFim);

        if (!isOnline()) {
            Toast.makeText(getContext(), "Não estava online para enviar e-mail!", Toast.LENGTH_SHORT).show();
            System.exit(0);
        }

        if (dia > total)
            dia = dia - total;
        if (meta.getPeriodo() == 7) {
            if (hj == (dia - 3)) {
                System.out.println("etrou no if de email");
                new Thread() {
                    @Override
                    public void run() {
                        mm.enviarGmail();
                    }
                }.start();
            }
            if ((hj == dia) && meta.getSituacao() == "n") {
                System.out.println("etrou no if de email de expirou");
                new Thread() {
                    @Override
                    public void run() {
                        mf.enviarGmail();
                    }
                }.start();
            }
        }

        if (meta.getPeriodo() == 10) {
            if (hj == (dia / 2)) {
                System.out.println("etrou no if de email");
                new Thread() {
                    @Override
                    public void run() {
                        mm.enviarGmail();
                    }
                }.start();
            }
            if ((hj == dia) && meta.getSituacao() == "n") {
                System.out.println("etrou no if de email de expirou");
                new Thread() {
                    @Override
                    public void run() {
                        mf.enviarGmail();
                    }
                }.start();
            }
        }

        if (meta.getPeriodo() == 30) {
            if (hj == (dia / 2)) {
                System.out.println("etrou no if de email");
                new Thread() {
                    @Override
                    public void run() {
                        mm.enviarGmail();
                    }
                }.start();
            }
            if ((hj == dia) && meta.getSituacao() == "n") {
                System.out.println("etrou no if de email de expirou");
                new Thread() {
                    @Override
                    public void run() {
                        mf.enviarGmail();
                    }
                }.start();
            }
        }

        if (meta.getPeriodo() == 60) {
            if (hj == (dia / 2)) {
                System.out.println("etrou no if de email");
                new Thread() {
                    @Override
                    public void run() {
                        mm.enviarGmail();
                    }
                }.start();
            }
            if ((hj == dia) && meta.getSituacao() == "n") {
                System.out.println("etrou no if de email de expirou");
                new Thread() {
                    @Override
                    public void run() {
                        mf.enviarGmail();
                    }
                }.start();
            }
        }

        if (meta.getPeriodo() == 90) {
            if (hj == (dia / 2)) {
                System.out.println("etrou no if de email");
                new Thread() {
                    @Override
                    public void run() {
                        mm.enviarGmail();
                    }
                }.start();
            }
            if ((hj == dia) && meta.getSituacao() == "n") {
                System.out.println("etrou no if de email de expirou");
                new Thread() {
                    @Override
                    public void run() {
                        mf.enviarGmail();
                    }
                }.start();
            }
        }
    }

    public boolean isOnline() {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Erro ao verificar se estava online! (" + ex.getMessage() + ")", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public static int qtdeAtuais(){
        int qtde = listaAtuais.getCount();
        return qtde;
    }

}
