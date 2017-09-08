package com.rahmad.bakingapp;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class BakingAppPageUiTest {

  private IdlingResource mIdlingResource;
  @Rule
  public ActivityTestRule<RecipeListActivity> mActivityTestRule = new ActivityTestRule<>(
      RecipeListActivity.class);


  @Before
  public void registerIdlingResource() {

    mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();

    Espresso.registerIdlingResources(mIdlingResource);

  }

  @Test
  public void checkIfRecipeListDisplayed() {
    onView(withId(R.id.recipe_list)).check(matches(isDisplayed()));
  }

  @Test
  public void checkIfIngredientListDisplayed() {
    ViewInteraction recyclerView = onView(withId(R.id.recipe_list));
    recyclerView.perform(actionOnItemAtPosition(0, click()));

    onView(withId(R.id.ingredient_list)).check(matches(isDisplayed()));
  }

  @Test
  public void checkIfStepListDisplayed() {
    ViewInteraction recyclerView = onView(withId(R.id.recipe_list));
    recyclerView.perform(actionOnItemAtPosition(0, click()));

    onView(withId(R.id.step_list)).check(matches(isDisplayed()));
  }

  @Test
  public void checkIfStepDetailDisplayed() {
    ViewInteraction recyclerView = onView(withId(R.id.recipe_list));
    recyclerView.perform(actionOnItemAtPosition(0, click()));

    ViewInteraction stepRecycler = onView(withId(R.id.step_list));
    stepRecycler.perform(actionOnItemAtPosition(0, click()));

    onView(withId(R.id.text_step)).check(matches(isDisplayed()));
  }

  @After
  public void unregisterIdlingResource() {
    if (mIdlingResource != null) {
      Espresso.unregisterIdlingResources(mIdlingResource);
    }
  }

}
