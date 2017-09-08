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

  /**
   * The constant _ID.
   */
  @DataType(INTEGER)
  @PrimaryKey
  @AutoIncrement
  String _ID = "_id";
  /**
   * The constant INGREDIENT_ID.
   */
  @DataType(INTEGER)
  String INGREDIENT_ID = "ingredient_id";
  /**
   * The constant RECIPE_ID.
   */
  @DataType(INTEGER)
  String RECIPE_ID = "recipe_id";
  /**
   * The constant QUANTITY.
   */
  @DataType(TEXT)
  String QUANTITY = "quantity";
  /**
   * The constant MEASURE.
   */
  @DataType(TEXT)
  String MEASURE = "measure";
  /**
   * The constant INGREDIENT.
   */
  @DataType(TEXT)
  String INGREDIENT = "ingredient";
  /**
   * The constant ISCHECKED.
   */
  @DataType(INTEGER)
  String ISCHECKED = "is_checked";

}
