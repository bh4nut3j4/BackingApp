package bhanuteja.android.com.backingapp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import bhanuteja.android.com.backingapp.R;
import bhanuteja.android.com.backingapp.ui.models.IngredientsModel;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 6/7/17.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {
    List<IngredientsModel> list;
    Context context;

    public IngredientsAdapter(List<IngredientsModel> list,Context context){
        this.list=list;
        this.context=context;
    }

    @Override
    public IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_model,parent,false);
        return new IngredientsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(IngredientsViewHolder holder, int position) {
        holder.name.setText(list.get(position).getIngredient());
        holder.quantity.setText(list.get(position).getQuantity());
        holder.measure.setText(list.get(position).getMeasure());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class IngredientsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ingredient_name)TextView name;
        @BindView(R.id.ingredient_quantity)TextView quantity;
        @BindView(R.id.ingredient_measure)TextView measure;
        public IngredientsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
