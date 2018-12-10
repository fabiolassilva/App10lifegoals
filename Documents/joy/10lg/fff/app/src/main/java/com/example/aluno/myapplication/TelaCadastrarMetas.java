package com.example.aluno.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.example.aluno.myapplication.Modelo.Meta;

/**
 * Created by Aluno on 03/10/2018.
 */

public class TelaCadastrarMetas extends AppCompatActivity{
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

    //inicial
    private int[] tabIcons = {
            R.drawable.casa,
            R.drawable.metas,
            R.drawable.perfil,
            R.drawable.ajuda,
            R.drawable.saida
    };

    private TabLayout mTabLayout;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_metas);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        desc1 = (EditText) findViewById(R.id.desc1);
        desc2 = (EditText) findViewById(R.id.desc2);
        desc3 = (EditText) findViewById(R.id.desc3);
        desc4 = (EditText) findViewById(R.id.desc4);
        desc5 = (EditText) findViewById(R.id.desc5);
        desc6 = (EditText) findViewById(R.id.desc6);
        desc7 = (EditText) findViewById(R.id.desc7);
        desc8 = (EditText) findViewById(R.id.desc8);
        desc9 = (EditText) findViewById(R.id.desc9);
        desc10 = (EditText) findViewById(R.id.desc10);

        SharedPreferences sharedPreferences =  getBaseContext().getSharedPreferences("10lifegoals", Context.MODE_PRIVATE);
        final String usuario = sharedPreferences.getString("usuario", "Uusário não encontrado");

        final String[] publi = new String[10];

        publicar = (Spinner) findViewById(R.id.spPublicar1);
        publicar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString()!= null)
                    publi[0] = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final int[] pe = new int[10];
        periodo = (Spinner) findViewById(R.id.spPeriodo1);
        periodo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString()!= null) {
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

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.periodo, R.layout.support_simple_spinner_dropdown_item);
        periodo.setAdapter(adapter);
        ArrayAdapter adapter11 = ArrayAdapter.createFromResource(this, R.array.publicar, R.layout.support_simple_spinner_dropdown_item);
        publicar.setAdapter(adapter11);

        publicar = (Spinner) findViewById(R.id.spPublicar2);
        publicar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString()!= null)
                    publi[1] = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        periodo = (Spinner) findViewById(R.id.spPeriodo2);
        periodo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString()!= null) {
                    if (adapterView.getItemAtPosition(i).toString().equals("1 semana"))
                        pe[1] = 7;
                    if (adapterView.getItemAtPosition(i).toString().equals("10 dias"))
                        pe[1] = 10;
                    if (adapterView.getItemAtPosition(i).toString().equals("1 mês"))
                        pe[1] = 30;
                    if (adapterView.getItemAtPosition(i).toString().equals("2 meses"))
                        pe[1] = 60;
                    if (adapterView.getItemAtPosition(i).toString().equals("3 meses"))
                        pe[1] = 90;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.periodo, R.layout.support_simple_spinner_dropdown_item);
        periodo.setAdapter(adapter2);
        ArrayAdapter adapter22 = ArrayAdapter.createFromResource(this, R.array.publicar, R.layout.support_simple_spinner_dropdown_item);
        publicar.setAdapter(adapter22);

        publicar = (Spinner) findViewById(R.id.spPublicar3);
        publicar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString()!= null)
                    publi[2] = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        periodo = (Spinner) findViewById(R.id.spPeriodo3);
        periodo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString()!= null) {
                    if (adapterView.getItemAtPosition(i).toString().equals("1 semana"))
                        pe[2] = 7;
                    if (adapterView.getItemAtPosition(i).toString().equals("10 dias"))
                        pe[2] = 10;
                    if (adapterView.getItemAtPosition(i).toString().equals("1 mês"))
                        pe[2] = 30;
                    if (adapterView.getItemAtPosition(i).toString().equals("2 meses"))
                        pe[2] = 60;
                    if (adapterView.getItemAtPosition(i).toString().equals("3 meses"))
                        pe[2] = 90;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(this, R.array.periodo, R.layout.support_simple_spinner_dropdown_item);
        periodo.setAdapter(adapter3);
        ArrayAdapter adapter33 = ArrayAdapter.createFromResource(this, R.array.publicar, R.layout.support_simple_spinner_dropdown_item);
        publicar.setAdapter(adapter33);

        publicar = (Spinner) findViewById(R.id.spPublicar4);
        publicar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString()!= null)
                    publi[3] = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        periodo = (Spinner) findViewById(R.id.spPeriodo4);
        periodo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString()!= null) {
                    if (adapterView.getItemAtPosition(i).toString().equals("1 semana"))
                        pe[3] = 7;
                    if (adapterView.getItemAtPosition(i).toString().equals("10 dias"))
                        pe[3] = 10;
                    if (adapterView.getItemAtPosition(i).toString().equals("1 mês"))
                        pe[3] = 30;
                    if (adapterView.getItemAtPosition(i).toString().equals("2 meses"))
                        pe[3] = 60;
                    if (adapterView.getItemAtPosition(i).toString().equals("3 meses"))
                        pe[3] = 90;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter adapter4 = ArrayAdapter.createFromResource(this, R.array.periodo, R.layout.support_simple_spinner_dropdown_item);
        periodo.setAdapter(adapter4);
        ArrayAdapter adapter44 = ArrayAdapter.createFromResource(this, R.array.publicar, R.layout.support_simple_spinner_dropdown_item);
        publicar.setAdapter(adapter44);

        publicar = (Spinner) findViewById(R.id.spPublicar5);
        publicar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString()!= null)
                    publi[4] = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        periodo = (Spinner) findViewById(R.id.spPeriodo5);
        periodo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString()!= null) {
                    if (adapterView.getItemAtPosition(i).toString().equals("1 semana"))
                        pe[4] = 7;
                    if (adapterView.getItemAtPosition(i).toString().equals("10 dias"))
                        pe[4] = 10;
                    if (adapterView.getItemAtPosition(i).toString().equals("1 mês"))
                        pe[4] = 30;
                    if (adapterView.getItemAtPosition(i).toString().equals("2 meses"))
                        pe[4] = 60;
                    if (adapterView.getItemAtPosition(i).toString().equals("3 meses"))
                        pe[4] = 90;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter adapter5 = ArrayAdapter.createFromResource(this, R.array.periodo, R.layout.support_simple_spinner_dropdown_item);
        periodo.setAdapter(adapter5);
        ArrayAdapter adapter55 = ArrayAdapter.createFromResource(this, R.array.publicar, R.layout.support_simple_spinner_dropdown_item);
        publicar.setAdapter(adapter55);

        publicar = (Spinner) findViewById(R.id.spPublicar6);
        publicar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString()!= null)
                    publi[5] = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        periodo = (Spinner) findViewById(R.id.spPeriodo6);
        periodo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString()!= null) {
                    if (adapterView.getItemAtPosition(i).toString().equals("1 semana"))
                        pe[5] = 7;
                    if (adapterView.getItemAtPosition(i).toString().equals("10 dias"))
                        pe[5] = 10;
                    if (adapterView.getItemAtPosition(i).toString().equals("1 mês"))
                        pe[5] = 30;
                    if (adapterView.getItemAtPosition(i).toString().equals("2 meses"))
                        pe[5] = 60;
                    if (adapterView.getItemAtPosition(i).toString().equals("3 meses"))
                        pe[5] = 90;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter adapter6 = ArrayAdapter.createFromResource(this, R.array.periodo, R.layout.support_simple_spinner_dropdown_item);
        periodo.setAdapter(adapter6);
        ArrayAdapter adapter66 = ArrayAdapter.createFromResource(this, R.array.publicar, R.layout.support_simple_spinner_dropdown_item);
        publicar.setAdapter(adapter66);

        publicar = (Spinner) findViewById(R.id.spPublicar7);
        publicar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString()!= null)
                    publi[6] = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        periodo = (Spinner) findViewById(R.id.spPeriodo7);
        periodo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString()!= null) {
                    if (adapterView.getItemAtPosition(i).toString().equals("1 semana"))
                        pe[6] = 7;
                    if (adapterView.getItemAtPosition(i).toString().equals("10 dias"))
                        pe[6] = 10;
                    if (adapterView.getItemAtPosition(i).toString().equals("1 mês"))
                        pe[6] = 30;
                    if (adapterView.getItemAtPosition(i).toString().equals("2 meses"))
                        pe[6] = 60;
                    if (adapterView.getItemAtPosition(i).toString().equals("3 meses"))
                        pe[6] = 90;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter adapter7 = ArrayAdapter.createFromResource(this, R.array.periodo, R.layout.support_simple_spinner_dropdown_item);
        periodo.setAdapter(adapter7);
        ArrayAdapter adapter77 = ArrayAdapter.createFromResource(this, R.array.publicar, R.layout.support_simple_spinner_dropdown_item);
        publicar.setAdapter(adapter77);

        publicar = (Spinner) findViewById(R.id.spPublicar8);
        publicar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString()!= null)
                    publi[7] = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        periodo = (Spinner) findViewById(R.id.spPeriodo8);
        periodo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString()!= null) {
                    if (adapterView.getItemAtPosition(i).toString().equals("1 semana"))
                        pe[7] = 7;
                    if (adapterView.getItemAtPosition(i).toString().equals("10 dias"))
                        pe[7] = 10;
                    if (adapterView.getItemAtPosition(i).toString().equals("1 mês"))
                        pe[7] = 30;
                    if (adapterView.getItemAtPosition(i).toString().equals("2 meses"))
                        pe[7] = 60;
                    if (adapterView.getItemAtPosition(i).toString().equals("3 meses"))
                        pe[7] = 90;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter adapter8 = ArrayAdapter.createFromResource(this, R.array.periodo, R.layout.support_simple_spinner_dropdown_item);
        periodo.setAdapter(adapter8);
        ArrayAdapter adapter88 = ArrayAdapter.createFromResource(this, R.array.publicar, R.layout.support_simple_spinner_dropdown_item);
        publicar.setAdapter(adapter88);

        publicar = (Spinner) findViewById(R.id.spPublicar9);
        publicar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString()!= null)
                    publi[8] = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        periodo = (Spinner) findViewById(R.id.spPeriodo9);
        periodo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString()!= null) {
                    if (adapterView.getItemAtPosition(i).toString().equals("1 semana"))
                        pe[8] = 7;
                    if (adapterView.getItemAtPosition(i).toString().equals("10 dias"))
                        pe[8] = 10;
                    if (adapterView.getItemAtPosition(i).toString().equals("1 mês"))
                        pe[8] = 30;
                    if (adapterView.getItemAtPosition(i).toString().equals("2 meses"))
                        pe[8] = 60;
                    if (adapterView.getItemAtPosition(i).toString().equals("3 meses"))
                        pe[8] = 90;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter adapter9 = ArrayAdapter.createFromResource(this, R.array.periodo, R.layout.support_simple_spinner_dropdown_item);
        periodo.setAdapter(adapter9);
        ArrayAdapter adapter99 = ArrayAdapter.createFromResource(this, R.array.publicar, R.layout.support_simple_spinner_dropdown_item);
        publicar.setAdapter(adapter99);

        publicar = (Spinner) findViewById(R.id.spPublicar10);
        publicar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString()!= null)
                    publi[9] = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        periodo = (Spinner) findViewById(R.id.spPeriodo10);
        periodo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString()!= null) {
                    if (adapterView.getItemAtPosition(i).toString().equals("1 semana"))
                        pe[9] = 7;
                    if (adapterView.getItemAtPosition(i).toString().equals("10 dias"))
                        pe[9] = 10;
                    if (adapterView.getItemAtPosition(i).toString().equals("1 mês"))
                        pe[9] = 30;
                    if (adapterView.getItemAtPosition(i).toString().equals("2 meses"))
                        pe[9] = 60;
                    if (adapterView.getItemAtPosition(i).toString().equals("3 meses"))
                        pe[9] = 90;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter adapter10 = ArrayAdapter.createFromResource(this, R.array.periodo, R.layout.support_simple_spinner_dropdown_item);
        periodo.setAdapter(adapter10);
        ArrayAdapter adapter110 = ArrayAdapter.createFromResource(this, R.array.publicar, R.layout.support_simple_spinner_dropdown_item);
        publicar.setAdapter(adapter110);
        //fim dos comboboxes


        btnCadastrar = (Button) findViewById(R.id.btnCadastrarM);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<String> metas;
                metas = new ArrayList();

                metas.add(desc1.getText().toString());
                metas.add(desc2.getText().toString());
                metas.add(desc3.getText().toString());
                metas.add(desc4.getText().toString());
                metas.add(desc5.getText().toString());
                metas.add(desc6.getText().toString());
                metas.add(desc7.getText().toString());
                metas.add(desc8.getText().toString());
                metas.add(desc9.getText().toString());
                metas.add(desc10.getText().toString());

                MetaDAO mdao = new MetaDAO(getBaseContext());
                mdao.abrir();

                SharedPreferences sharedPreferences = getSharedPreferences("10lifegoals", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor;
                editor = sharedPreferences.edit(); //Escrever dentro do arquivo

                GregorianCalendar calendar = new GregorianCalendar();
                int data = calendar.get(GregorianCalendar.DAY_OF_MONTH);

                for(int i=0; i<pe.length; i++) {
                    Meta meta = mdao.criarMeta(metas.get(i), publi[i], pe[i], "N", data, usuario);
                    editor.putString("meta "+i, metas.get(i));
                    if(pe[i] == 7)
                        editor.putString("pe "+i, "1 semana");
                    if(pe[i] == 10)
                        editor.putString("pe "+i, "10 dias");
                    if(pe[i] == 30)
                        editor.putString("pe "+i, "1 mês");
                    if(pe[i] == 60)
                        editor.putString("pe "+i, "2 meses");
                    if(pe[i] == 90)
                        editor.putString("pe "+i, "3 meses");

                    ArrayAdapter<Meta> adaptador = new ArrayAdapter<Meta>(TelaCadastrarMetas.this.getApplicationContext(), 0);
                    adaptador.add(meta);
                    adaptador.notifyDataSetChanged();
                }


                editor.commit();

                Intent i = new Intent(TelaCadastrarMetas.this, MainActivity.class);
                startActivity(i);

            }
        });
    }
}
