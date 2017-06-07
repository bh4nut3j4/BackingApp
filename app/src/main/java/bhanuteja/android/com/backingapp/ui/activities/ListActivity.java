package bhanuteja.android.com.backingapp.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bhanuteja.android.com.backingapp.R;
import bhanuteja.android.com.backingapp.ui.adapters.StepsAdapter;
import bhanuteja.android.com.backingapp.ui.fragments.IngredientsFragement;
import bhanuteja.android.com.backingapp.ui.fragments.VideoFragment;
import bhanuteja.android.com.backingapp.ui.models.IngredientsModel;
import bhanuteja.android.com.backingapp.ui.models.RecipeModel;
import bhanuteja.android.com.backingapp.ui.models.StepsModel;
import bhanuteja.android.com.backingapp.utils.Onitemtouchlistener;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListActivity extends AppCompatActivity {

    @BindView(R.id.ingredientsviewtext)TextView ingredientsnumber;
    @BindView(R.id.stepsrecyclerview)RecyclerView recyclerView;
    @BindView(R.id.viewingredientcardview)CardView cardView;
    StepsAdapter adapter;
    List<StepsModel> stepList;
    List<IngredientsModel> ingredientsList;
    RecipeModel recipeModel;
    public boolean twoPane;
    public String recipename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        Bundle bundle =getIntent().getExtras();
        if (bundle!=null){
            recipeModel = bundle.getParcelable("data");
            recipename= recipeModel.getRecipe_name();
            getSupportActionBar().setTitle(recipename);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        stepList = recipeModel.getSteps();
        ingredientsList = recipeModel.getIngredients();
        adapter = new StepsAdapter(stepList,this);
        recyclerView.setAdapter(adapter);
        final FragmentManager fragmentManager = getSupportFragmentManager();

        if (findViewById(R.id.fulldetailsframelayout)!=null){
            twoPane=true;
        }else {
            twoPane=false;
        }
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (twoPane){
                    IngredientsFragement ingredientsFragement = new IngredientsFragement();
                    ingredientsFragement.setList(ingredientsList);
                    fragmentManager.beginTransaction()
                            .replace(R.id.fulldetailsframelayout,ingredientsFragement)
                            .commit();
                }else {
                    Bundle ingredients_bundel = new Bundle();
                    ingredients_bundel.putParcelableArrayList("ingredient_bundle", (ArrayList<? extends Parcelable>) ingredientsList);
                    Intent i1 = new Intent(getApplicationContext(),DetailsActivity.class);
                    i1.putExtras(ingredients_bundel);
                    startActivity(i1);
                }
            }
        });

        recyclerView.addOnItemTouchListener(new Onitemtouchlistener(this, new Onitemtouchlistener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (twoPane){
                    VideoFragment videoFragment = new VideoFragment();
                    videoFragment.setPosition(position);
                    videoFragment.setStepList(stepList);
                    fragmentManager.beginTransaction()
                            .replace(R.id.fulldetailsframelayout,videoFragment)
                            .commit();

                }else {
                    Bundle steps_bundle = new Bundle();
                    steps_bundle.putParcelableArrayList("steps_bundle", (ArrayList<? extends Parcelable>) stepList);
                    steps_bundle.putInt("position",position);
                    Intent i2 = new Intent(getApplicationContext(),DetailsActivity.class);
                    i2.putExtras(steps_bundle);
                    startActivity(i2);
                }
            }
        }));
    }
}
