package com.rahmad.bakingapp.database;

import net.simonvt.schematic.annotation.Table;

/**
 * Created by rahmad on 8/20/17.
 * inbox.rahmad@gmail.com
 * Copyright 2017
 */

public class BakingDatabase {

  public static final int VERSION = 1;

  @Table(RecipeColumns.class)
  public static final String RECIPES = "recipes";
  @Table(IngredientsColumns.class)
  public static final String INGREDIENTS = "ingredients";
  @Table(StepsColumns.class)
  public static final String STEPS = "steps";

}
