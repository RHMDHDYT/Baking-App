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
public interface RecipeColumns {

  /**
   * The constant _ID.
   */
  @DataType(INTEGER)
  @PrimaryKey
  @AutoIncrement
  String _ID = "_id";
  /**
   * The constant RECIPE_ID.
   */
  @DataType(INTEGER)
  String RECIPE_ID = "recipe_id";
  /**
   * The constant NAME.
   */
  @DataType(TEXT)
  String NAME = "name";
  /**
   * The constant SERVINGS.
   */
  @DataType(INTEGER)
  String SERVINGS = "servings";
  /**
   * The constant IMAGE.
   */
  @DataType(TEXT)
  String IMAGE = "image";


}
