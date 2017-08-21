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

public interface StepsColumns {

  @DataType(INTEGER)
  @PrimaryKey
  @AutoIncrement
  String _ID = "_id";
  @DataType(INTEGER)
  String STEP_ID = "step_id";
  @DataType(INTEGER)
  String RECIPE_ID = "recipe_id";
  @DataType(TEXT)
  String SHORT_DESC = "short_desc";
  @DataType(TEXT)
  String DESC = "desc";
  @DataType(TEXT)
  String VIDEO_URL = "video_url";
  @DataType(TEXT)
  String THUMB_URL = "thumb_url";

}
