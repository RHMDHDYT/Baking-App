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

  @DataType(INTEGER)
  @PrimaryKey
  @AutoIncrement
  String _ID = "_id";
  @DataType(INTEGER)
  String RECIPE_ID = "recipe_id";
  @DataType(TEXT)
  String NAME = "name";
  @DataType(INTEGER)
  String SERVINGS = "servings";
  @DataType(TEXT)
  String IMAGE = "image";


}
