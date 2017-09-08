package com.rahmad.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.rahmad.bakingapp.model.pojo.StepsItem;

/**
 * Created by rahmad on 9/4/17.
 * inbox.rahmad@gmail.com
 * Copyright 2017
 */
public class StepFragmentModel implements Parcelable {

  private String stepId;
  private StepsItem currentStepItem;
  private String url;
  private String nextTitle;
  private String previousTitle;
  private Boolean standAloneMode;
  private Long videoPosition;

  /**
   * Instantiates a new Step fragment model.
   */
  public StepFragmentModel() {
  }


  public Long getVideoPosition() {
    return videoPosition;
  }

  public void setVideoPosition(Long videoPosition) {
    this.videoPosition = videoPosition;
  }

  /**
   * Gets stand alone mode.
   *
   * @return the stand alone mode
   */
  public Boolean getStandAloneMode() {
    return standAloneMode;
  }

  /**
   * Sets stand alone mode.
   *
   * @param standAloneMode the stand alone mode
   */
  public void setStandAloneMode(Boolean standAloneMode) {
    this.standAloneMode = standAloneMode;
  }

  /**
   * Gets step id.
   *
   * @return the step id
   */
  public String getStepId() {
    return stepId;
  }

  /**
   * Sets step id.
   *
   * @param stepId the step id
   */
  public void setStepId(String stepId) {
    this.stepId = stepId;
  }

  /**
   * Gets current step item.
   *
   * @return the current step item
   */
  public StepsItem getCurrentStepItem() {
    return currentStepItem;
  }

  /**
   * Sets current step item.
   *
   * @param currentStepItem the current step item
   */
  public void setCurrentStepItem(StepsItem currentStepItem) {
    this.currentStepItem = currentStepItem;
  }

  /**
   * Gets url.
   *
   * @return the url
   */
  public String getUrl() {
    return url;
  }

  /**
   * Sets url.
   *
   * @param url the url
   */
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * Gets next title.
   *
   * @return the next title
   */
  public String getNextTitle() {
    return nextTitle;
  }

  /**
   * Sets next title.
   *
   * @param nextTitle the next title
   */
  public void setNextTitle(String nextTitle) {
    this.nextTitle = nextTitle;
  }

  /**
   * Gets previous title.
   *
   * @return the previous title
   */
  public String getPreviousTitle() {
    return previousTitle;
  }

  /**
   * Sets previous title.
   *
   * @param previousTitle the previous title
   */
  public void setPreviousTitle(String previousTitle) {
    this.previousTitle = previousTitle;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.stepId);
    dest.writeParcelable(this.currentStepItem, flags);
    dest.writeString(this.url);
    dest.writeString(this.nextTitle);
    dest.writeString(this.previousTitle);
    dest.writeValue(this.standAloneMode);
    dest.writeValue(this.videoPosition);
  }

  protected StepFragmentModel(Parcel in) {
    this.stepId = in.readString();
    this.currentStepItem = in.readParcelable(StepsItem.class.getClassLoader());
    this.url = in.readString();
    this.nextTitle = in.readString();
    this.previousTitle = in.readString();
    this.standAloneMode = (Boolean) in.readValue(Boolean.class.getClassLoader());
    this.videoPosition = (Long) in.readValue(Long.class.getClassLoader());
  }

  public static final Creator<StepFragmentModel> CREATOR = new Creator<StepFragmentModel>() {
    @Override
    public StepFragmentModel createFromParcel(Parcel source) {
      return new StepFragmentModel(source);
    }

    @Override
    public StepFragmentModel[] newArray(int size) {
      return new StepFragmentModel[size];
    }
  };
}
