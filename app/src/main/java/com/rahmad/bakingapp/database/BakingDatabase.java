package com.rahmad.bakingapp.database;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by rahmad on 8/20/17.
 * inbox.rahmad@gmail.com
 * Copyright 2017
 */
@Database(version = BakingDatabase.VERSION,
    packageName = "com.rahmad.bakingapp.database.provider")
public final class BakingDatabase {

  /**
   * The constant VERSION.
   */
  public static final int VERSION = 1;
  /**
   * The constant RECIPES.
   */
  @Table(RecipeColumns.class)
  public static final String RECIPES = "recipes";
  /**
   * The constant INGREDIENTS.
   */
  @Table(IngredientsColumns.class)
  public static final String INGREDIENTS = "ingredients";
  /**
   * The constant STEPS.
   */
  @Table(StepsColumns.class)
  public static final String STEPS = "steps";

  private BakingDatabase() {
  }

}
