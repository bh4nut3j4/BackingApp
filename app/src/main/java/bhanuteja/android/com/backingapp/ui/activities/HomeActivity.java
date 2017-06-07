package bhanuteja.android.com.backingapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import java.util.ArrayList;
import java.util.List;

import bhanuteja.android.com.backingapp.R;
import bhanuteja.android.com.backingapp.ui.adapters.HomeAdapter;
import bhanuteja.android.com.backingapp.ui.models.RecipeModel;
import bhanuteja.android.com.backingapp.utils.NetworkUtils;
import bhanuteja.android.com.backingapp.utils.Onitemtouchlistener;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {
    @BindView(R.id.homerecylerview)
    RecyclerView recyclerView;
    @BindView(R.id.homewarning)
    TextView warningtext;

    public boolean twoPane;
    LinearLayoutManager layoutManager;
    public static List<RecipeModel> list = new ArrayList<>();
    HomeAdapter adapter;
    public static final String LIST_CONSTANT = "list";
    Parcelable listState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        twoPane = getResources().getBoolean(R.bool.is_tablet);
        if (twoPane){
            layoutManager = new GridLayoutManager(this,2);
        }else {
            layoutManager = new GridLayoutManager(this,1);
        }
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HomeAdapter(list,this);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new Onitemtouchlistener(this, new Onitemtouchlistener.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                RecipeModel recipe = list.get(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable("data",recipe);
                Intent intent = new Intent(HomeActivity.this,ListActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }));

        getData();
    }

    public void getData(){
        AndroidNetworking.get(NetworkUtils.dataUrl)
                .setPriority(Priority.HIGH)
                .build()
                .getAsObjectList(RecipeModel.class,new ParsedRequestListener<List<RecipeModel>>(){
                    @Override
                    public void onResponse(List<RecipeModel> response) {
                        List<RecipeModel> model = response;
                        list=model;
                        adapter = new HomeAdapter(list,HomeActivity.this);
                        recyclerView.setAdapter(adapter);
                        layoutManager.onRestoreInstanceState(listState);
                    }
                    @Override
                    public void onError(ANError anError) {
                    }
                });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        listState  = layoutManager.onSaveInstanceState();
        outState.putParcelable(LIST_CONSTANT,listState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState!=null){
            listState = savedInstanceState.getParcelable(LIST_CONSTANT);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (listState!=null){
            layoutManager.onRestoreInstanceState(listState);
        }
    }
}
