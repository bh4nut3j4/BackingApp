package bhanuteja.android.com.backingapp.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import bhanuteja.android.com.backingapp.R;
import bhanuteja.android.com.backingapp.ui.adapters.IngredientsAdapter;
import bhanuteja.android.com.backingapp.ui.models.IngredientsModel;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 6/7/17.
 */

public class IngredientsFragement extends Fragment{
    @BindView(R.id.ingredients_recyclerview)RecyclerView recyclerView;
    IngredientsAdapter adapter;
    List<IngredientsModel> list;

    public IngredientsFragement() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ingredients_fragment,container,false);
        ButterKnife.bind(this,rootView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new IngredientsAdapter(list,getContext());
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    public void setList(List<IngredientsModel> models){
        list=models;
    }
}
