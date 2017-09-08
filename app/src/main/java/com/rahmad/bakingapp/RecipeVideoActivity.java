package com.rahmad.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.facebook.stetho.Stetho;
import com.rahmad.bakingapp.model.pojo.StepsItem;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Recipe video activity.
 */
public class RecipeVideoActivity extends AppCompatActivity {

  /**
   * The Toolbar.
   */
  @BindView(R.id.toolbar)
  Toolbar toolbar;
  /**
   * The Frame layout.
   */
  @BindView(R.id.frameLayout)
  FrameLayout frameLayout;
  /**
   * The Steps items data.
   */
  List<StepsItem> stepsItemsData;
  /**
   * The Step id.
   */
  String stepId;
  /**
   * The Step name.
   */
  String stepName;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe_video);
    Stetho.initializeWithDefaults(this);
    ButterKnife.bind(this);

    Intent intent = getIntent();

    Bundle bundleData = intent.getBundleExtra(Constant.KEY_STEP_BUNDLE);
    stepsItemsData = bundleData.getParcelableArrayList(Constant.KEY_STEPS_DATA);
    stepId = bundleData.getString(Constant.KEY_STEP_ID);
    stepName = bundleData.getString(Constant.STEP_NAME);

    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setTitle(stepName);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    loadVideoStepFragment(savedInstanceState);
  }

  private void loadVideoStepFragment(Bundle savedInstanceState) {

    RecipeVideoFragment fragment;
    if (savedInstanceState != null) {
      fragment = (RecipeVideoFragment) getSupportFragmentManager()
          .findFragmentByTag(Constant.KEY_VIDEO_STEP_FRAGMENT);
    } else {
      fragment = new RecipeVideoFragment();

      Bundle bundle = new Bundle();
      bundle.putBoolean(Constant.KEY_STAND_ALONE_MODE, true);
      bundle.putString(Constant.KEY_STEP_ID, stepId);
      bundle.putParcelableArrayList(Constant.KEY_STEPS_DATA,
          (ArrayList<? extends Parcelable>) stepsItemsData);

      fragment.setArguments(bundle);

      getSupportFragmentManager().beginTransaction()
          .replace(frameLayout.getId(), fragment, Constant.KEY_VIDEO_STEP_FRAGMENT).commit();
    }

  }

  @Override
  public boolean onSupportNavigateUp() {
    finish();
    return true;
  }
}
