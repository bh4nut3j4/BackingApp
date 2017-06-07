package bhanuteja.android.com.backingapp.ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import bhanuteja.android.com.backingapp.R;
import bhanuteja.android.com.backingapp.ui.fragments.IngredientsFragement;
import bhanuteja.android.com.backingapp.ui.fragments.VideoFragment;
import bhanuteja.android.com.backingapp.ui.models.IngredientsModel;
import bhanuteja.android.com.backingapp.ui.models.StepsModel;

public class DetailsActivity extends AppCompatActivity {
    List<IngredientsModel> ingredientsModelList;
    List<StepsModel> stepsModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        FragmentManager fragmentManager= getSupportFragmentManager();
        ingredientsModelList = getIntent().getExtras().getParcelableArrayList("ingredient_bundle");
        stepsModelList = getIntent().getExtras().getParcelableArrayList("steps_bundle");

        if (stepsModelList!=null){
            VideoFragment videoFragment = new VideoFragment();
            videoFragment.setPosition(getIntent().getIntExtra("position",0));
            videoFragment.setStepList(stepsModelList);
            fragmentManager.beginTransaction()
                    .replace(R.id.details_frame,videoFragment)
                    .commit();
        }else if(savedInstanceState==null){
            IngredientsFragement ingredientsFragement = new IngredientsFragement();
            ingredientsFragement.setList(ingredientsModelList);
            fragmentManager.beginTransaction()
                    .replace(R.id.details_frame,ingredientsFragement)
                    .commit();
        }else {
            fragmentManager.findFragmentById(R.id.details_frame);
        }

    }
}
