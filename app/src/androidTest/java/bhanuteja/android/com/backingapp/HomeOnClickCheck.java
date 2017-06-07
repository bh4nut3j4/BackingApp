package bhanuteja.android.com.backingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import bhanuteja.android.com.backingapp.ui.activities.HomeActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static java.lang.Thread.sleep;

/**
 * Created by root on 6/7/17.
 */

@RunWith(AndroidJUnit4.class)
public class HomeOnClickCheck {

    @Rule public ActivityTestRule<HomeActivity> activityActivityTestRule = new ActivityTestRule<HomeActivity>(HomeActivity.class);

    @Test
    public void checkonclick(){
        try {
            sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }
        onView(withId(R.id.homerecylerview)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.ingredientsviewtext)).check(matches(isDisplayed()));
        onView(withId(R.id.stepstext)).check(matches(isDisplayed()));
    }
}
