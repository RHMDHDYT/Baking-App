package com.rahmad.bakingapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.rahmad.bakingapp.adapter.RecipeItemAdapter;
import com.rahmad.bakingapp.adapter.RecipeItemAdapter.RecipesAdapterOnClickHandler;
import com.rahmad.bakingapp.database.BakingProvider.Ingredients;
import com.rahmad.bakingapp.database.BakingProvider.Recipes;
import com.rahmad.bakingapp.database.BakingProvider.Steps;
import com.rahmad.bakingapp.database.IngredientsColumns;
import com.rahmad.bakingapp.database.RecipeColumns;
import com.rahmad.bakingapp.database.StepsColumns;
import com.rahmad.bakingapp.model.pojo.IngredientsItem;
import com.rahmad.bakingapp.model.pojo.RecipesItem;
import com.rahmad.bakingapp.model.pojo.StepsItem;
import com.rahmad.bakingapp.util.ApiClient;
import com.rahmad.bakingapp.util.ApiInterface;
import com.rahmad.bakingapp.util.NetworkUtil;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by rahmad on 8/27/17.
 * inbox.rahmad@gmail.com
 * Copyright 2017
 */
public class RecipeListFragment extends Fragment {

  /**
   * The Progress bar.
   */
  @BindView(R.id.progress_bar)
  ContentLoadingProgressBar progressBar;
  /**
   * The Recycler view.
   */
  @BindView(R.id.recipe_list)
  RecyclerView recyclerView;
  /**
   * The Text info caption.
   */
  @BindView(R.id.text_info_caption)
  TextView textInfoCaption;
  private Unbinder unbinder;
  private ApiInterface apiService;
  private Call<ArrayList<RecipesItem>> callRecipes;
  private List<RecipesItem> recipeItemsData = new ArrayList<>();
  private RecipeItemAdapter adapter;
  private RecipeListActivity recipeListActivity;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.recipe_list, container, false);
    unbinder = ButterKnife.bind(this, view);

    init();
    initListener();

    //get instance data / load from network
    getRecipesDataInstance(savedInstanceState);

    return view;
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    outState.putParcelableArrayList(Constant.KEY_RECIPE_INSTANCE,
        (ArrayList<? extends Parcelable>) recipeItemsData);

    super.onSaveInstanceState(outState);
  }

  private void init() {
    recipeListActivity = (RecipeListActivity) getActivity();

    //set api caller
    apiService = ApiClient.getClient().create(ApiInterface.class);
    progressBar.setVisibility(View.GONE);
    showInfoCaption(false);

    //set list config
    recyclerView.setHasFixedSize(true);
    recyclerView.setLayoutManager(
        new GridLayoutManager(getActivity(), getActivity().getResources().getInteger(R
            .integer.column_recipe_value)));

  }

  private void initListener() {
    adapter = new RecipeItemAdapter(recipeItemsData, getContext(),
        new RecipesAdapterOnClickHandler() {
          @Override
          public void onClick(String data) {
            Intent intent = new Intent(getActivity(), RecipeDetailActivity.class);
            intent.putExtra(Constant.RECIPE_ID, data);
            intent.putExtra(Constant.RECIPE_NAME,
                recipeItemsData.get(Integer.parseInt(data) - 1).getName());
            startActivity(intent);
          }
        });

    recyclerView.setAdapter(adapter);

  }

  private void getRecipesDataInstance(Bundle savedInstanceState) {
    //null and check saved instance state
    if (savedInstanceState == null || !savedInstanceState.containsKey(Constant.KEY_RECIPE_INSTANCE)) {
      checkIfDataExist();
    } else {
      List<RecipesItem> parcelableData = savedInstanceState
          .getParcelableArrayList(Constant.KEY_RECIPE_INSTANCE);

      recipeItemsData.addAll(parcelableData);

      adapter.notifyDataSetChanged();

      if (recipeListActivity.mIdlingResource != null) {
        recipeListActivity.mIdlingResource.setIdleState(false);
      }
    }
  }

  private void checkIfDataExist() {
    Cursor cursor = getActivity().getContentResolver()
        .query(Recipes.CONTENT_URI, null, null, null, null);

    if (cursor != null) {
      if (cursor.moveToFirst()) {
        getRecipeFromDb(cursor);
      } else {
        getRecipesFromNetwork();
      }
    } else {
      getRecipesFromNetwork();
    }
  }

  private void getRecipesFromNetwork() {
    if (recipeListActivity.mIdlingResource != null) {
      recipeListActivity.mIdlingResource.setIdleState(true);
    }

    progressBar.setVisibility(View.VISIBLE);

    if (NetworkUtil.isOnline(getActivity())) {
      callRecipes = apiService.getRecipesData();
      callRecipes.clone().enqueue(new Callback<ArrayList<RecipesItem>>() {
        @Override
        public void onResponse(Call<ArrayList<RecipesItem>> call,
            retrofit2.Response<ArrayList<RecipesItem>> response) {

          if (response.body() == null || response.body().size() == 0) {

          } else {

            for (int i = 0; i < response.body().size(); i++) {

              RecipesItem recipeModel = response.body().get(i);

              //mapping recipes data
              ContentValues recipesValues = new ContentValues();

              recipesValues.put(RecipeColumns.RECIPE_ID, recipeModel.getId());
              recipesValues.put(RecipeColumns.NAME, recipeModel.getName());
              recipesValues.put(RecipeColumns.SERVINGS, recipeModel.getServings());
              recipesValues.put(RecipeColumns.IMAGE, recipeModel.getImage());

              //save data to database
              getActivity().getContentResolver().insert(Recipes.CONTENT_URI, recipesValues);

              //get list of ingredients on current recipe
              List<IngredientsItem> listIngredients = recipeModel.getIngredients();
              ContentValues[] cVListIngredients = new ContentValues[listIngredients.size()];

              for (int j = 0; j < listIngredients.size(); j++) {

                ContentValues ingredientsValues = new ContentValues();
                IngredientsItem ingredientModel = listIngredients.get(j);

                ingredientsValues.put(IngredientsColumns.INGREDIENT_ID, j + 1);
                ingredientsValues.put(IngredientsColumns.RECIPE_ID, recipeModel.getId());
                ingredientsValues.put(IngredientsColumns.QUANTITY, ingredientModel.getQuantity());
                ingredientsValues.put(IngredientsColumns.MEASURE, ingredientModel.getMeasure());
                ingredientsValues
                    .put(IngredientsColumns.INGREDIENT, ingredientModel.getIngredient());
                ingredientsValues.put(IngredientsColumns.ISCHECKED, 0);

                cVListIngredients[j] = ingredientsValues;
              }

              getActivity().getContentResolver()
                  .bulkInsert(Ingredients.CONTENT_URI, cVListIngredients);

              //get list of steps on current recipe
              List<StepsItem> listSteps = recipeModel.getSteps();
              ContentValues[] cVListSteps = new ContentValues[listSteps.size()];

              for (int j = 0; j < listSteps.size(); j++) {

                ContentValues stepsValues = new ContentValues();
                StepsItem stepModel = listSteps.get(j);

                stepsValues.put(StepsColumns.STEP_ID, j + 1);
                stepsValues.put(StepsColumns.RECIPE_ID, recipeModel.getId());
                stepsValues.put(StepsColumns.SHORT_DESC, stepModel.getShortDescription());
                stepsValues.put(StepsColumns.DESC, stepModel.getDescription());
                stepsValues.put(StepsColumns.VIDEO_URL, stepModel.getVideoURL());
                stepsValues.put(StepsColumns.THUMB_URL, stepModel.getThumbnailURL());

                cVListSteps[j] = stepsValues;
              }

              getActivity().getContentResolver().bulkInsert(Steps.CONTENT_URI, cVListSteps);
            }
          }

          progressBar.setVisibility(View.GONE);

          checkIfDataExist();
        }

        @Override
        public void onFailure(Call<ArrayList<RecipesItem>> call, Throwable t) {

          String caption = getString(R.string.failed_load_data_caption);
          showInfoCaption(true, caption);

        }
      });
    } else {
      String caption = getString(R.string.no_internet_connection_caption);
      showInfoCaption(true, caption);
    }


  }

  private void getRecipeFromDb(Cursor cursor) {

    List<RecipesItem> listRecipesItem = new ArrayList<>();

    if (cursor != null) {
      if (cursor.moveToFirst()) {
        do {
          RecipesItem object = new RecipesItem();

          object.setId(cursor.getInt(cursor.getColumnIndex(RecipeColumns.RECIPE_ID)));
          object.setName(cursor.getString(cursor.getColumnIndex(RecipeColumns.NAME)));
          object.setImage(cursor.getString(cursor.getColumnIndex(RecipeColumns.IMAGE)));
          object.setServings(cursor.getInt(cursor.getColumnIndex(RecipeColumns.SERVINGS)));

          listRecipesItem.add(object);

        } while (cursor.moveToNext());

        recipeItemsData.addAll(listRecipesItem);
      }
      cursor.close();
    } else {
      String caption = getString(R.string.no_data_caption);
      showInfoCaption(true, caption);
    }

    adapter.notifyDataSetChanged();

    if (recipeListActivity.mIdlingResource != null) {
      recipeListActivity.mIdlingResource.setIdleState(false);
    }
  }


  private void showInfoCaption(Boolean isVisible) {
    showInfoCaption(isVisible, "");
  }

  private void showInfoCaption(Boolean isVisible, String message) {

    if (isVisible) {
      textInfoCaption.setVisibility(View.VISIBLE);
      textInfoCaption.setText(message);
    } else {
      textInfoCaption.setVisibility(View.GONE);
    }
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

}
