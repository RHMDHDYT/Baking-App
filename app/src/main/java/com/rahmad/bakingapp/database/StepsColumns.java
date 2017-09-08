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

  /**
   * The constant _ID.
   */
  @DataType(INTEGER)
  @PrimaryKey
  @AutoIncrement
  String _ID = "_id";
  /**
   * The constant STEP_ID.
   */
  @DataType(INTEGER)
  String STEP_ID = "step_id";
  /**
   * The constant RECIPE_ID.
   */
  @DataType(INTEGER)
  String RECIPE_ID = "recipe_id";
  /**
   * The constant SHORT_DESC.
   */
  @DataType(TEXT)
  String SHORT_DESC = "short_desc";
  /**
   * The constant DESC.
   */
  @DataType(TEXT)
  String DESC = "desc";
  /**
   * The constant VIDEO_URL.
   */
  @DataType(TEXT)
  String VIDEO_URL = "video_url";
  /**
   * The constant THUMB_URL.
   */
  @DataType(TEXT)
  String THUMB_URL = "thumb_url";

}
