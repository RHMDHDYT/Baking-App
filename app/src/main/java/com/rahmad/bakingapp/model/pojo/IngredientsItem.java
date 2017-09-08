package com.rahmad.bakingapp.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/**
 * The type Ingredients item.
 */
public class IngredientsItem implements Parcelable {

  /**
   * The constant CREATOR.
   */
  public static final Creator<IngredientsItem> CREATOR = new Creator<IngredientsItem>() {
    @Override
    public IngredientsItem createFromParcel(Parcel source) {
      return new IngredientsItem(source);
    }

    @Override
    public IngredientsItem[] newArray(int size) {
      return new IngredientsItem[size];
    }
  };
  @SerializedName("quantity")
  private String quantity;
  @SerializedName("measure")
  private String measure;
  @SerializedName("ingredient")
  private String ingredient;
  @SerializedName("is_checked")
  private Integer isChecked;
  @SerializedName("recipe_id")
  private Integer recipeId;
  @SerializedName("ingredient_id")
  private Integer ingredientId;

  /**
   * Instantiates a new Ingredients item.
   */
  public IngredientsItem() {
  }

  /**
   * Instantiates a new Ingredients item.
   *
   * @param in the in
   */
  protected IngredientsItem(Parcel in) {
    this.quantity = in.readString();
    this.measure = in.readString();
    this.ingredient = in.readString();
    this.isChecked = (Integer) in.readValue(Integer.class.getClassLoader());
    this.recipeId = (Integer) in.readValue(Integer.class.getClassLoader());
    this.ingredientId = (Integer) in.readValue(Integer.class.getClassLoader());
  }

  /**
   * Gets recipe id.
   *
   * @return the recipe id
   */
  public Integer getRecipeId() {
    return recipeId;
  }

  /**
   * Sets recipe id.
   *
   * @param recipeId the recipe id
   */
  public void setRecipeId(Integer recipeId) {
    this.recipeId = recipeId;
  }

  /**
   * Gets ingredient id.
   *
   * @return the ingredient id
   */
  public Integer getIngredientId() {
    return ingredientId;
  }

  /**
   * Sets ingredient id.
   *
   * @param ingredientId the ingredient id
   */
  public void setIngredientId(Integer ingredientId) {
    this.ingredientId = ingredientId;
  }

  /**
   * Gets is checked.
   *
   * @return the is checked
   */
  public Integer getIsChecked() {
    return isChecked;
  }

  /**
   * Sets is checked.
   *
   * @param isChecked the is checked
   */
  public void setIsChecked(Integer isChecked) {
    this.isChecked = isChecked;
  }

  /**
   * Gets quantity.
   *
   * @return the quantity
   */
  public String getQuantity() {
    return quantity;
  }

  /**
   * Sets quantity.
   *
   * @param quantity the quantity
   */
  public void setQuantity(String quantity) {
    this.quantity = quantity;
  }

  /**
   * Gets measure.
   *
   * @return the measure
   */
  public String getMeasure() {
    return measure;
  }

  /**
   * Sets measure.
   *
   * @param measure the measure
   */
  public void setMeasure(String measure) {
    this.measure = measure;
  }

  /**
   * Gets ingredient.
   *
   * @return the ingredient
   */
  public String getIngredient() {
    return ingredient;
  }

  /**
   * Sets ingredient.
   *
   * @param ingredient the ingredient
   */
  public void setIngredient(String ingredient) {
    this.ingredient = ingredient;
  }

  @Override
  public String toString() {
    return
        "IngredientsItem{" +
            "quantity = '" + quantity + '\'' +
            ",measure = '" + measure + '\'' +
            ",ingredient = '" + ingredient + '\'' +
            "}";
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.quantity);
    dest.writeString(this.measure);
    dest.writeString(this.ingredient);
    dest.writeValue(this.isChecked);
    dest.writeValue(this.recipeId);
    dest.writeValue(this.ingredientId);
  }
}