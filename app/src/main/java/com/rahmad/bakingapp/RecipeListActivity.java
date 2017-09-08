package com.rahmad.bakingapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.facebook.stetho.Stetho;
import com.rahmad.bakingapp.util.SimpleIdlingResource;


/**
 * The type Recipe list activity.
 */
public class RecipeListActivity extends AppCompatActivity {


  @Nullable
  public SimpleIdlingResource mIdlingResource;
  /**
   * The Frame layout.
   */
  @BindView(R.id.frameLayout)
  FrameLayout frameLayout;
  /**
   * The Toolbar.
   */
  @BindView(R.id.toolbar)
  Toolbar toolbar;

  @VisibleForTesting
  @NonNull
  public IdlingResource getIdlingResource() {
    if (mIdlingResource == null) {
      mIdlingResource = new SimpleIdlingResource();
    }
    return mIdlingResource;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe_list);
    ButterKnife.bind(this);
    Stetho.initializeWithDefaults(this);

    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setTitle(getString(R.string.app_name));
    }

    loadRecipeListFragment(savedInstanceState);
  }

  private void loadRecipeListFragment(Bundle savedInstanceState) {

    RecipeListFragment fragment;
    if (savedInstanceState != null) {
      fragment = (RecipeListFragment) getSupportFragmentManager()
          .findFragmentByTag(Constant.KEY_RECIPE_FRAGMENT);
    } else {
      fragment = new RecipeListFragment();
      getSupportFragmentManager().beginTransaction()
          .add(frameLayout.getId(), fragment, Constant.KEY_RECIPE_FRAGMENT).commit();
    }

  }

}
