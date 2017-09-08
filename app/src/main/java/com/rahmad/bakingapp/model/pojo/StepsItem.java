package com.rahmad.bakingapp.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/**
 * The type Steps item.
 */
public class StepsItem implements Parcelable {

  /**
   * The constant CREATOR.
   */
  public static final Creator<StepsItem> CREATOR = new Creator<StepsItem>() {
    @Override
    public StepsItem createFromParcel(Parcel source) {
      return new StepsItem(source);
    }

    @Override
    public StepsItem[] newArray(int size) {
      return new StepsItem[size];
    }
  };
  @SerializedName("videoURL")
  private String videoURL;
  @SerializedName("description")
  private String description;
  @SerializedName("id")
  private int id;
  @SerializedName("shortDescription")
  private String shortDescription;
  @SerializedName("thumbnailURL")
  private String thumbnailURL;
  @SerializedName("step_id")
  private Integer stepId;
  @SerializedName("recipe_id")
  private Integer recipeId;
  @SerializedName("is_selected")
  private Boolean isSelected;

  /**
   * Instantiates a new Steps item.
   */
  public StepsItem() {
  }

  /**
   * Instantiates a new Steps item.
   *
   * @param in the in
   */
  protected StepsItem(Parcel in) {
    this.videoURL = in.readString();
    this.description = in.readString();
    this.id = in.readInt();
    this.shortDescription = in.readString();
    this.thumbnailURL = in.readString();
    this.stepId = (Integer) in.readValue(Integer.class.getClassLoader());
    this.recipeId = (Integer) in.readValue(Integer.class.getClassLoader());
    this.isSelected = (Boolean) in.readValue(Boolean.class.getClassLoader());
  }

  /**
   * Gets selected.
   *
   * @return the selected
   */
  public Boolean getSelected() {
    return isSelected;
  }

  /**
   * Sets selected.
   *
   * @param selected the selected
   */
  public void setSelected(Boolean selected) {
    isSelected = selected;
  }

  /**
   * Gets step id.
   *
   * @return the step id
   */
  public Integer getStepId() {
    return stepId;
  }

  /**
   * Sets step id.
   *
   * @param stepId the step id
   */
  public void setStepId(Integer stepId) {
    this.stepId = stepId;
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
   * Gets video url.
   *
   * @return the video url
   */
  public String getVideoURL() {
    return videoURL;
  }

  /**
   * Sets video url.
   *
   * @param videoURL the video url
   */
  public void setVideoURL(String videoURL) {
    this.videoURL = videoURL;
  }

  /**
   * Gets description.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets description.
   *
   * @param description the description
   */
  public void setDescription(String description) {
    this.description = description;
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
   * Gets short description.
   *
   * @return the short description
   */
  public String getShortDescription() {
    return shortDescription;
  }

  /**
   * Sets short description.
   *
   * @param shortDescription the short description
   */
  public void setShortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
  }

  /**
   * Gets thumbnail url.
   *
   * @return the thumbnail url
   */
  public String getThumbnailURL() {
    return thumbnailURL;
  }

  /**
   * Sets thumbnail url.
   *
   * @param thumbnailURL the thumbnail url
   */
  public void setThumbnailURL(String thumbnailURL) {
    this.thumbnailURL = thumbnailURL;
  }

  @Override
  public String toString() {
    return
        "StepsItem{" +
            "videoURL = '" + videoURL + '\'' +
            ",description = '" + description + '\'' +
            ",id = '" + id + '\'' +
            ",shortDescription = '" + shortDescription + '\'' +
            ",thumbnailURL = '" + thumbnailURL + '\'' +
            "}";
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.videoURL);
    dest.writeString(this.description);
    dest.writeInt(this.id);
    dest.writeString(this.shortDescription);
    dest.writeString(this.thumbnailURL);
    dest.writeValue(this.stepId);
    dest.writeValue(this.recipeId);
    dest.writeValue(this.isSelected);
  }
}