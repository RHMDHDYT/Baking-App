package com.rahmad.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.facebook.stetho.Stetho;
import com.rahmad.bakingapp.model.pojo.StepsItem;
import com.rahmad.bakingapp.util.AppSharedPref;
import com.rahmad.bakingapp.widget.WidgetProvider;
import java.util.ArrayList;
import java.util.List;


/**
 * The type Recipe detail activity.
 */
public class RecipeDetailActivity extends AppCompatActivity {

  /**
   * Whether or not the activity is in two-pane mode, i.e. running on a tablet
   * device.
   */
  public static boolean mTwoPane = false;
  /**
   * The Toolbar.
   */
  @BindView(R.id.toolbar_title)
  Toolbar toolbar;
  /**
   * The App bar.
   */
  @BindView(R.id.app_bar)
  AppBarLayout appBar;
  /**
   * The Frame layout list.
   */
  @BindView(R.id.frameLayout_list)
  FrameLayout frameLayoutList;
  /**
   * The Frame layout content.
   */
  @Nullable
  @BindView(R.id.frameLayout_content)
  FrameLayout frameLayoutContent;
  /**
   * The Fab.
   */
  @BindView(R.id.fab)
  FloatingActionButton fab;
  private String recipeId;
  private String recipeName;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe_detail);
    ButterKnife.bind(this);
    Stetho.initializeWithDefaults(this);

    Intent intent = getIntent();
    recipeId = intent.getStringExtra(Constant.RECIPE_ID);
    recipeName = intent.getStringExtra(Constant.RECIPE_NAME);

    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setTitle(recipeName);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    init(savedInstanceState);
    initListener();
  }

  private void init(Bundle savedInstanceState) {
    setFavoriteUiButton();

    checkIsTwoPaneMode();

    loadIngredientListFragment(savedInstanceState);

    if (mTwoPane) {
      loadStepListFragment(savedInstanceState);
    }
  }

  private void initListener() {
    fab.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        setFavoriteToggle();
        updateWidget();
      }
    });
  }

  private void updateWidget() {
    int[] ids = AppWidgetManager.getInstance(this)
        .getAppWidgetIds(new ComponentName(this, WidgetProvider.class));
    WidgetProvider ingredientWidget = new WidgetProvider();
    ingredientWidget.onUpdate(this, AppWidgetManager.getInstance(this), ids);

    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
    ComponentName thisWidget = new ComponentName(this, WidgetProvider.class);
    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.ing_widget_list);

  }

  private void setFavoriteToggle() {
    AppSharedPref appSharedPref = new AppSharedPref(getApplicationContext());
    String recipeId = appSharedPref.getRecipeFavorite();

    if (recipeId.equals(this.recipeId)) {
      appSharedPref.setRecipeFavorite("");
      appSharedPref.setRecipeFavoriteName("");
      appSharedPref.commit();
    } else {
      appSharedPref.setRecipeFavorite(this.recipeId);
      appSharedPref.setRecipeFavoriteName(this.recipeName);
      appSharedPref.commit();
    }

    setFavoriteUiButton();
  }

  private void setFavoriteUiButton() {
    AppSharedPref appSharedPref = new AppSharedPref(getApplicationContext());
    String recipeId = appSharedPref.getRecipeFavorite();

    if (recipeId.isEmpty()) {
      fab.setImageResource(R.drawable.ic_favorite_white);
    } else if (recipeId.equals(this.recipeId)) {
      fab.setImageResource(R.drawable.ic_favorite_red);
    }
  }

  private void checkIsTwoPaneMode() {
    if (frameLayoutContent != null) {
      mTwoPane = true;
    } else {
      mTwoPane = false;
    }
  }

  private void loadIngredientListFragment(Bundle savedInstanceState) {

    RecipeDetailListFragment fragment;
    if (savedInstanceState != null) {
      fragment = (RecipeDetailListFragment) getSupportFragmentManager()
          .findFragmentByTag(Constant.KEY_INGREDIENT_LIST_FRAGMENT);
    } else {
      fragment = new RecipeDetailListFragment();

      Bundle bundle = new Bundle();
      bundle.putString(Constant.RECIPE_ID, recipeId);
      fragment.setArguments(bundle);

      getSupportFragmentManager().beginTransaction()
          .add(frameLayoutList.getId(), fragment, Constant.KEY_INGREDIENT_LIST_FRAGMENT).commit();
    }

  }

  /**
   * Load step list fragment.
   *
   * @param savedInstanceState the saved instance state
   */
  public void loadStepListFragment(Bundle savedInstanceState) {

    RecipeVideoFragment fragment;
    if (savedInstanceState != null) {
      fragment = (RecipeVideoFragment) getSupportFragmentManager()
          .findFragmentByTag(Constant.KEY_STEP_LIST_FRAGMENT);
    } else {
      fragment = new RecipeVideoFragment();

      Bundle bundle = new Bundle();
      bundle.putString(Constant.RECIPE_ID, recipeId);
      bundle.putBoolean(Constant.KEY_INITIAL_DETAIL_PAGE, true);
      fragment.setArguments(bundle);

      getSupportFragmentManager().beginTransaction()
          .add(frameLayoutContent.getId(), fragment, Constant.KEY_STEP_LIST_FRAGMENT).commit();
    }

  }

  /**
   * Load video step fragment.
   *
   * @param stepId the step id
   * @param stepsItemsData the steps items data
   */
  public void loadVideoStepFragment(String stepId, List<StepsItem> stepsItemsData) {

    RecipeVideoFragment fragment;

    fragment = new RecipeVideoFragment();

    Bundle bundle = new Bundle();
    bundle.putString(Constant.KEY_STEP_ID, stepId);
    bundle.putParcelableArrayList(Constant.KEY_STEPS_DATA,
        (ArrayList<? extends Parcelable>) stepsItemsData);

    fragment.setArguments(bundle);

    getSupportFragmentManager().beginTransaction()
        .replace(frameLayoutContent.getId(), fragment, Constant.KEY_VIDEO_STEP_FRAGMENT).commit();
  }

  @Override
  public boolean onSupportNavigateUp() {
    finish();
    return true;
  }

}
