package com.rahmad.bakingapp.database;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by rahmad on 8/20/17.
 * inbox.rahmad@gmail.com
 * Copyright 2017
 */

public interface IngredientsColumns {

  @DataType(INTEGER)
  @PrimaryKey
  @AutoIncrement
  String _ID = "_id";
  @DataType(INTEGER)
  @AutoIncrement
  String INGREDIENT_ID = "ingredient_id";
  @DataType(INTEGER)
  String RECIPE_ID = "recipe_id";
  @DataType(INTEGER)
  String QUANTITY = "quantity";
  @DataType(TEXT)
  String MEASURE = "measure";
  @DataType(TEXT)
  String INGREDIENT = "ingredient";

}
