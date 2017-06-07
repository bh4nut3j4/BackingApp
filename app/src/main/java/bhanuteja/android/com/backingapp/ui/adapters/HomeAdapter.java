package bhanuteja.android.com.backingapp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import bhanuteja.android.com.backingapp.R;
import bhanuteja.android.com.backingapp.ui.models.RecipeModel;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 6/6/17.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    List<RecipeModel> list;
    Context context;
    public HomeAdapter(List<RecipeModel> list,Context context){
        this.list=list;
        this.context=context;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.homerecycler_model,parent,false);
        return new HomeViewHolder(v);
  }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        holder.name.setText(list.get(position).getRecipe_name());
        String url = list.get(position).getImage();
        if (url==null || url.equals("")){
            holder.imageView.setImageResource(R.drawable.image_placeholder);
        }else {
           Glide.with(context).load(url).placeholder(R.drawable.image_placeholder).error(R.drawable.image_placeholder).into(holder.imageView);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class HomeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipeimage)
        ImageView imageView;
        @BindView(R.id.recipename)
        TextView name;

        public HomeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
