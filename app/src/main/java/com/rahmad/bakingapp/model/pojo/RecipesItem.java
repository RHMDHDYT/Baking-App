package com.rahmad.bakingapp.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Recipes item.
 */
public class RecipesItem implements Parcelable {

  /**
   * The constant CREATOR.
   */
  public static final Creator<RecipesItem> CREATOR = new Creator<RecipesItem>() {
    @Override
    public RecipesItem createFromParcel(Parcel source) {
      return new RecipesItem(source);
    }

    @Override
    public RecipesItem[] newArray(int size) {
      return new RecipesItem[size];
    }
  };
  @SerializedName("image")
  private String image;
  @SerializedName("servings")
  private int servings;
  @SerializedName("name")
  private String name;
  @SerializedName("ingredients")
  private List<IngredientsItem> ingredients;
  @SerializedName("id")
  private int id;
  @SerializedName("steps")
  private List<StepsItem> steps;

  /**
   * Instantiates a new Recipes item.
   */
  public RecipesItem() {
  }

  /**
   * Instantiates a new Recipes item.
   *
   * @param in the in
   */
  protected RecipesItem(Parcel in) {
    this.image = in.readString();
    this.servings = in.readInt();
    this.name = in.readString();
    this.ingredients = new ArrayList<IngredientsItem>();
    in.readList(this.ingredients, IngredientsItem.class.getClassLoader());
    this.id = in.readInt();
    this.steps = new ArrayList<StepsItem>();
    in.readList(this.steps, StepsItem.class.getClassLoader());
  }

  /**
   * Gets image.
   *
   * @return the image
   */
  public String getImage() {
    return image;
  }

  /**
   * Sets image.
   *
   * @param image the image
   */
  public void setImage(String image) {
    this.image = image;
  }

  /**
   * Gets servings.
   *
   * @return the servings
   */
  public int getServings() {
    return servings;
  }

  /**
   * Sets servings.
   *
   * @param servings the servings
   */
  public void setServings(int servings) {
    this.servings = servings;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets name.
   *
   * @param name the name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets ingredients.
   *
   * @return the ingredients
   */
  public List<IngredientsItem> getIngredients() {
    return ingredients;
  }

  /**
   * Sets ingredients.
   *
   * @param ingredients the ingredients
   */
  public void setIngredients(List<IngredientsItem> ingredients) {
    this.ingredients = ingredients;
  }

  /**
   * Gets id.
   *
   * @return the id
   */
  public int getId() {
    return id;
  }

  /**
   * Sets id.
   *
   * @param id the id
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Gets steps.
   *
   * @return the steps
   */
  public List<StepsItem> getSteps() {
    return steps;
  }

  /**
   * Sets steps.
   *
   * @param steps the steps
   */
  public void setSteps(List<StepsItem> steps) {
    this.steps = steps;
  }

  @Override
  public String toString() {
    return
        "RecipesItem{" +
            "image = '" + image + '\'' +
            ",servings = '" + servings + '\'' +
            ",name = '" + name + '\'' +
            ",ingredients = '" + ingredients + '\'' +
            ",id = '" + id + '\'' +
            ",steps = '" + steps + '\'' +
            "}";
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.image);
    dest.writeInt(this.servings);
    dest.writeString(this.name);
    dest.writeList(this.ingredients);
    dest.writeInt(this.id);
    dest.writeList(this.steps);
  }
}