package bhanuteja.android.com.backingapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import bhanuteja.android.com.backingapp.ui.activities.HomeActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.lang.Thread.sleep;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by root on 6/6/17.
 */

@RunWith(AndroidJUnit4.class)
public class HomeActivityTextCheck {

    @Rule public ActivityTestRule<HomeActivity> activityTestRule = new ActivityTestRule<HomeActivity>(HomeActivity.class);

    @Test
    public void CheckRecipeNameOnGridView(){
        try {
            sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }
        onView(allOf(withId(R.id.recipename),withText("Nutella Pie")));
        onView(allOf(withId(R.id.recipename),withText("Brownies")));
        onView(allOf(withId(R.id.recipename),withText("Yellow Cake")));
        onView(allOf(withId(R.id.recipename),withText("CheeseCake")));

    }
}
