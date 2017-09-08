package com.rahmad.bakingapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.rahmad.bakingapp.adapter.IngredientItemAdapter;
import com.rahmad.bakingapp.adapter.StepItemAdapter;
import com.rahmad.bakingapp.adapter.StepItemAdapter.StepItemAdapterOnClickHandler;
import com.rahmad.bakingapp.database.BakingProvider.Ingredients;
import com.rahmad.bakingapp.database.BakingProvider.Steps;
import com.rahmad.bakingapp.database.IngredientsColumns;
import com.rahmad.bakingapp.database.StepsColumns;
import com.rahmad.bakingapp.model.pojo.IngredientsItem;
import com.rahmad.bakingapp.model.pojo.StepsItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rahmad on 9/1/17.
 * inbox.rahmad@gmail.com
 * Copyright 2017
 */
public class RecipeDetailListFragment extends Fragment {

  /**
   * The Ingredients list.
   */
  @BindView(R.id.ingredient_list)
  RecyclerView ingredientsList;
  /**
   * The Steps list.
   */
  @BindView(R.id.step_list)
  RecyclerView stepsList;
  private Unbinder unbinder;
  private String recipeId;
  private String recipeName;
  private StepItemAdapter adapter;
  private List<StepsItem> stepItemsDatas = new ArrayList<>();

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.recipe_detail_list, container, false);
    unbinder = ButterKnife.bind(this, view);

    Bundle bundle = getArguments();
    recipeId = bundle.getString(Constant.RECIPE_ID);
    recipeName = bundle.getString(Constant.RECIPE_NAME);

    init();
    initListener();

    getDataIngredientsFromDb();
    getDataStepsFromDb();

    return view;
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  private void init() {
    //disable nested scrolling to make a smooth scrolling
    ingredientsList.setNestedScrollingEnabled(false);
    stepsList.setNestedScrollingEnabled(false);

    ingredientsList.setFocusable(false);
    stepsList.setFocusable(false);
  }

  private void initListener() {
    adapter = new StepItemAdapter(stepItemsDatas, getContext(),
        new StepItemAdapterOnClickHandler() {
          @Override
          public void onClick(String data, int position) {
            if (RecipeDetailActivity.mTwoPane) {

              adapter.setSelected(position);

              RecipeDetailActivity recipeDetailActivity = (RecipeDetailActivity) getActivity();
              recipeDetailActivity.loadVideoStepFragment(data, stepItemsDatas);
            } else {
              Intent intent = new Intent(getActivity(), RecipeVideoActivity.class);

              Bundle bundle = new Bundle();
              bundle.putBoolean(Constant.KEY_STAND_ALONE_MODE, true);
              bundle.putString(Constant.KEY_STEP_ID, data);
              bundle.putParcelableArrayList(Constant.KEY_STEPS_DATA,
                  (ArrayList<? extends Parcelable>) stepItemsDatas);
              bundle.putString(Constant.STEP_NAME,
                  stepItemsDatas.get(Integer.parseInt(data) - 1).getShortDescription());

              intent.putExtra(Constant.KEY_STEP_BUNDLE, bundle);
              startActivity(intent);
            }
          }
        });

    adapter.notifyDataSetChanged();
  }

  private void getDataIngredientsFromDb() {
    String selectionClause = IngredientsColumns.RECIPE_ID + "=?";
    String selectionArgs[] = new String[1];
    selectionArgs[0] = recipeId;

    Cursor cursor = getActivity().getContentResolver().query(Ingredients.CONTENT_URI, null,
        selectionClause,
        selectionArgs, null);

    List<IngredientsItem> ingredientsItemsData = new ArrayList<>();

    if (cursor != null) {
      if (cursor.moveToFirst()) {
        do {
          IngredientsItem object = new IngredientsItem();
          object.setIngredient(
              cursor.getString(cursor.getColumnIndex(IngredientsColumns.INGREDIENT)));
          object.setQuantity(cursor.getString(cursor.getColumnIndex(IngredientsColumns.QUANTITY)));
          object.setMeasure(cursor.getString(cursor.getColumnIndex(IngredientsColumns.MEASURE)));
          object.setIsChecked(cursor.getInt(cursor.getColumnIndex(IngredientsColumns.ISCHECKED)));
          object.setRecipeId(Integer.valueOf(recipeId));
          object.setIngredientId(
              cursor.getInt(cursor.getColumnIndex(IngredientsColumns.INGREDIENT_ID)));

          ingredientsItemsData.add(object);

        } while (cursor.moveToNext());

        IngredientItemAdapter adapter = new IngredientItemAdapter
            (ingredientsItemsData, getContext());

        ingredientsList.setAdapter(adapter);

        adapter.notifyDataSetChanged();
      }
      cursor.close();
    } else {
      //nodata available
    }

  }

  private void getDataStepsFromDb() {
    String selectionClause = StepsColumns.RECIPE_ID + "=?";
    String selectionArgs[] = new String[1];
    selectionArgs[0] = recipeId;

    Cursor cursor = getActivity().getContentResolver().query(Steps.CONTENT_URI, null,
        selectionClause,
        selectionArgs, null);

    List<StepsItem> stepItemsData = new ArrayList<>();

    if (cursor != null) {
      if (cursor.moveToFirst()) {
        do {
          StepsItem object = new StepsItem();
          object.setRecipeId(cursor.getInt(cursor.getColumnIndex(StepsColumns.RECIPE_ID)));
          object.setStepId(cursor.getInt(cursor.getColumnIndex(StepsColumns.STEP_ID)));
          object.setShortDescription(
              cursor.getString(cursor.getColumnIndex(StepsColumns.SHORT_DESC)));
          object.setDescription(cursor.getString(cursor.getColumnIndex(StepsColumns.DESC)));
          object.setVideoURL(cursor.getString(cursor.getColumnIndex(StepsColumns.VIDEO_URL)));
          object.setThumbnailURL(cursor.getString(cursor.getColumnIndex(StepsColumns.THUMB_URL)));

          stepItemsData.add(object);

        } while (cursor.moveToNext());

        stepItemsDatas.addAll(stepItemsData);

        adapter.notifyDataSetChanged();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(stepsList
            .getContext(), DividerItemDecoration.VERTICAL);
        stepsList.addItemDecoration(dividerItemDecoration);

        stepsList.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        //set initial detail page
        if (RecipeDetailActivity.mTwoPane) {
          RecipeDetailActivity recipeDetailActivity = (RecipeDetailActivity) getActivity();
          recipeDetailActivity.loadVideoStepFragment("1", stepItemsDatas);
          adapter.setSelected(0);
        }
      }
      cursor.close();
    } else {
      //nodata available
    }

  }
}
