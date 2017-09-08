package com.rahmad.bakingapp.database;

import android.net.Uri;
import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by rahmad on 8/20/17.
 * inbox.rahmad@gmail.com
 * Copyright 2017
 */
@ContentProvider(
    authority = BakingProvider.AUTHORITY,
    database = BakingDatabase.class,
    packageName = "com.rahmad.bakingapp.database.provider")
public class BakingProvider {

  /**
   * The constant AUTHORITY.
   */
  public static final String AUTHORITY = "com.rahmad.bakingapp.database.BakingProvider";

  private BakingProvider() {
  }

  /**
   * The type Recipes.
   */
  @TableEndpoint(table = BakingDatabase.RECIPES)
  public static class Recipes {

    /**
     * The constant CONTENT_URI.
     */
    @ContentUri(
        path = "recipes",
        type = "vnd.android.cursor.dir/recipe",
        defaultSort = RecipeColumns.RECIPE_ID + " ASC")
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/recipes");
  }

  /**
   * The type Ingredients.
   */
  @TableEndpoint(table = BakingDatabase.INGREDIENTS)
  public static class Ingredients {

    /**
     * The constant CONTENT_URI.
     */
    @ContentUri(
        path = "ingredients",
        type = "vnd.android.cursor.dir/ingredient",
        defaultSort = IngredientsColumns.INGREDIENT_ID + " ASC")
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/ingredients");
  }

  /**
   * The type Steps.
   */
  @TableEndpoint(table = BakingDatabase.STEPS)
  public static class Steps {

    /**
     * The constant CONTENT_URI.
     */
    @ContentUri(
        path = "steps",
        type = "vnd.android.cursor.dir/step",
        defaultSort = StepsColumns.STEP_ID + " ASC")
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/steps");
  }
}
