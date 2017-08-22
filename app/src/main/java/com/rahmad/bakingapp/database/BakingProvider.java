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

  private BakingProvider() {
  }

  public static final String AUTHORITY = "com.rahmad.bakingapp.database.BakingProvider";

  @TableEndpoint(table = BakingDatabase.RECIPES)
  public static class Recipes {

    @ContentUri(
        path = "recipes",
        type = "vnd.android.cursor.dir/recipe",
        defaultSort = RecipeColumns.NAME + " ASC")
    public static final Uri RECIPES = Uri.parse("content://" + AUTHORITY + "/recipes");
  }

  @TableEndpoint(table = BakingDatabase.INGREDIENTS)
  public static class Ingredients {

    @ContentUri(
        path = "ingredients",
        type = "vnd.android.cursor.dir/ingredient",
        defaultSort = IngredientsColumns.INGREDIENT_ID + " ASC")
    public static final Uri INGREDIENTS = Uri.parse("content://" + AUTHORITY + "/ingredients");
  }

  @TableEndpoint(table = BakingDatabase.STEPS)
  public static class Steps {

    @ContentUri(
        path = "steps",
        type = "vnd.android.cursor.dir/step",
        defaultSort = StepsColumns.STEP_ID + " ASC")
    public static final Uri STEPS = Uri.parse("content://" + AUTHORITY + "/steps");
  }
}
