package com.rahmad.bakingapp.util;

import com.rahmad.bakingapp.model.pojo.RecipesItem;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * The interface Api interface.
 */
public interface ApiInterface {

  /**
   * Gets recipes data.
   *
   * @return the recipes data
   */
  @GET("topher/2017/May/59121517_baking/baking.json")
  Call<ArrayList<RecipesItem>> getRecipesData();

}
