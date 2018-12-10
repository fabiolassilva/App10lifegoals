package com.example.aluno.myapplication.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.aluno.myapplication.Fragment.TelaAjuda;
import com.example.aluno.myapplication.Fragment.TelaHome;
import com.example.aluno.myapplication.Fragment.TelaMetas;
import com.example.aluno.myapplication.TelaPerfil;
import com.example.aluno.myapplication.Fragment.TelaSair;

/**
 * Created by Aluno on 20/08/2018.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] mTabTitles;

    public MyFragmentPagerAdapter(FragmentManager fm, String[] mTabTitles) {
        super(fm);
        this.mTabTitles = mTabTitles;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new TelaHome();
            case 1:
                return new TelaMetas();
            case 2:
                return new TelaPerfil();
            case 3:
                return new TelaAjuda();
            case 4:
                return new TelaSair();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return this.mTabTitles.length;
    }

    /*@Override
    public CharSequence getPageTitle(int position){
        return this.mTabTitles[position];
    }*/
}
