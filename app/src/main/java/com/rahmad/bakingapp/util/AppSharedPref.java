package com.rahmad.bakingapp.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * The type App shared pref.
 */
public class AppSharedPref {

  private static final String FILENAME = "APP_SHARED_DATA";
  private final String recipeFavorite = "RECIPE_FAVORITE";
  private final String recipeFavoriteName = "RECIPE_FAVORITE_NAME";

  private final SharedPreferences sharedPreferences;
  private final SharedPreferences.Editor editor;

  /**
   * Instantiates a new App shared pref.
   *
   * @param context the context
   */
  public AppSharedPref(Context context) {
    sharedPreferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
    editor = sharedPreferences.edit();
  }

  /**
   * Gets recipe favorite.
   *
   * @return the recipe favorite
   */
  public String getRecipeFavorite() {
    return sharedPreferences.getString(recipeFavorite, "");
  }

  /**
   * Sets recipe favorite.
   *
   * @param recipeFavorite the recipe favorite
   */
  public void setRecipeFavorite(String recipeFavorite) {
    editor.putString(this.recipeFavorite, recipeFavorite);
    editor.apply();
  }

  /**
   * Gets recipe favorite name.
   *
   * @return the recipe favorite name
   */
  public String getRecipeFavoriteName() {
    return sharedPreferences.getString(recipeFavoriteName, "");
  }

  /**
   * Sets recipe favorite name.
   *
   * @param recipeFavoriteName the recipe favorite name
   */
  public void setRecipeFavoriteName(String recipeFavoriteName) {
    editor.putString(this.recipeFavoriteName, recipeFavoriteName);
    editor.apply();
  }

  /**
   * Clear.
   */
  public void clear() {
    editor.clear();
    editor.commit();
  }

  /**
   * Commit boolean.
   *
   * @return the boolean
   */
  public boolean commit() {
    return editor.commit();
  }
}
