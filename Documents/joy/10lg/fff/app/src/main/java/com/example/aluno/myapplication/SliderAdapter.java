package com.example.aluno.myapplication;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){

        this.context = context;

    }

    //arrays
    public int[] slide_images = {
            R.drawable.sobre_img,
            R.drawable.sobre_img,
            R.drawable.sobre_img
    };

    public String[] slide_headings = {

            "Você é a nossa maior inspiração!",
            "Viu nosso coração de frutas?",
            "Incentivo e Amor"
    };

    public String[] slide_descs = {

            "Esse site é sobre você, sobre suas metas de vida, aquelas que você não fala para ninguém. " +
                    "Agora você pode compartilhar com quem você desejar e vai poder receber ajuda e dicas de como sair do sonhos e começar a cumprí-las.",
            "Como todo mundo sabe, as frutas são ricas em nutrientes que fazem bem para os seres humanos. " +
                    "E aqui, no 10 Life Goals, representamos nossos usuários como frutas, pois queremos que eles façam bem uns aos outros.",
            "Essas duas palavras são a chave do 10 Life Goals, a interação entre nossos usuários incentivando uns aos outros a cumprirem suas metas particulares e," +
                    " acima de tudo, compartilhando amor."

    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == (RelativeLayout) o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view =  layoutInflater.inflate(R.layout.slide_layout, container,false);

        ImageView slideImageView = (ImageView)view.findViewById(R.id.slide_image);
        TextView slideHeading = (TextView)view.findViewById(R.id.slide_heading);
        TextView slideDescription = (TextView)view.findViewById(R.id.slide_desc);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_descs[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
