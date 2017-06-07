package bhanuteja.android.com.backingapp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import bhanuteja.android.com.backingapp.R;
import bhanuteja.android.com.backingapp.ui.models.StepsModel;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 6/6/17.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {
    List<StepsModel> stepsModelList;
    Context context;

    public StepsAdapter(List<StepsModel> list, Context context) {
        this.stepsModelList=list;
        this.context=context;
    }

    @Override
    public StepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.steps_recycler_model,parent,false);
        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepsViewHolder holder, int position) {
        holder.shortdescription.setText(stepsModelList.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        return stepsModelList.size();
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.step_short_description)TextView shortdescription;
        public StepsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
