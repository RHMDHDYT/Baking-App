package com.rahmad.bakingapp;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.MediaSessionCompat.Callback;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v4.media.session.PlaybackStateCompat.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayer.EventListener;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.rahmad.bakingapp.model.StepFragmentModel;
import com.rahmad.bakingapp.model.pojo.StepsItem;
import java.util.List;

/**
 * The type Recipe video fragment.
 */
public class RecipeVideoFragment extends Fragment implements EventListener {

  private static MediaSessionCompat mMediaSession;
  /**
   * The Navigation section.
   */
  @BindView(R.id.navigation_section)
  LinearLayout navigationSection;
  /**
   * The Card view step.
   */
  @BindView(R.id.card_view_step)
  CardView cardViewStep;
  /**
   * The Video player.
   */
  @BindView(R.id.video_player)
  SimpleExoPlayerView videoPlayer;
  /**
   * The Text step.
   */
  @Nullable
  @BindView(R.id.text_step)
  TextView textStep;
  /**
   * The Button previous.
   */
  @Nullable
  @BindView(R.id.button_previous)
  Button buttonPrevious;
  /**
   * The Button next.
   */
  @Nullable
  @BindView(R.id.button_next)
  Button buttonNext;
  /**
   * The Unbinder.
   */
  Unbinder unbinder;
  private SimpleExoPlayer mExoPlayer;
  private Builder mStateBuilder;
  private List<StepsItem> stepsItemList;
  private String stepId;
  private StepsItem currentStepItem;
  private String url = "";
  private String nextTitle = "";
  private String previousTitle = "";
  private Boolean initialMode;
  private Boolean standAloneMode;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.recipe_video_step, container, false);
    unbinder = ButterKnife.bind(this, rootView);

    Bundle bundle = getArguments();

    standAloneMode = bundle.getBoolean(Constant.KEY_STAND_ALONE_MODE);
    initialMode = bundle.getBoolean(Constant.KEY_INITIAL_DETAIL_PAGE);

    if (initialMode == null || initialMode == false) {
      stepsItemList = bundle.getParcelableArrayList(Constant.KEY_STEPS_DATA);
      stepId = bundle.getString(Constant.KEY_STEP_ID);

      getRecipesDataInstance(savedInstanceState);
    } else {
      navigationSection.setVisibility(View.GONE);
      cardViewStep.setVisibility(View.GONE);
    }

    return rootView;
  }


  private void getRecipesDataInstance(Bundle savedInstanceState) {
    //null and check saved instance state
    if (savedInstanceState != null && savedInstanceState.containsKey(Constant.KEY_STATE_MODEL)) {
      StepFragmentModel stepFragmentModel = savedInstanceState.getParcelable(Constant.KEY_STATE_MODEL);
      stepId = stepFragmentModel.getStepId();
      currentStepItem = stepFragmentModel.getCurrentStepItem();
      url = stepFragmentModel.getUrl();
      nextTitle = stepFragmentModel.getNextTitle();
      previousTitle = stepFragmentModel.getPreviousTitle();
      standAloneMode = stepFragmentModel.getStandAloneMode();
    }

    init();
    initListener();
    initializeMediaSession();
    initializePlayer();
    setStepUiContent();
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    StepFragmentModel stepFragmentModel = new StepFragmentModel();
    stepFragmentModel.setStepId(stepId);
    stepFragmentModel.setCurrentStepItem(currentStepItem);
    stepFragmentModel.setUrl(url);
    stepFragmentModel.setNextTitle(nextTitle);
    stepFragmentModel.setPreviousTitle(previousTitle);
    stepFragmentModel.setStandAloneMode(standAloneMode);

    outState.putParcelable(Constant.KEY_STATE_MODEL, stepFragmentModel);

    super.onSaveInstanceState(outState);
  }

  private void mappingCurrentStep() {
    currentStepItem = stepsItemList.get(Integer.parseInt(stepId) - 1);
  }

  private void setStepUiContent() {
    cardViewStep.setVisibility(View.VISIBLE);
    textStep.setText(currentStepItem.getDescription());
  }

  private void setButtonUiContent() {
    if (Integer.parseInt(stepId) != stepsItemList.size()) {
      nextTitle = stepsItemList.get(Integer.parseInt(stepId)).getShortDescription();
    }

    if (Integer.parseInt(stepId) != 1) {
      previousTitle = stepsItemList.get(Integer.parseInt(stepId) - 2).getShortDescription();
    }

    if (Integer.parseInt(stepId) == 1) {
      setButtonBackUiIsEnabled(false);
      setButtonNextUiIsEnabled(true);
    } else {
      if (Integer.parseInt(stepId) == stepsItemList.size()) {
        setButtonNextUiIsEnabled(false);
      } else {
        setButtonNextUiIsEnabled(true);
      }
      setButtonBackUiIsEnabled(true);
    }


  }

  private void init() {
    mappingCurrentStep();

    setButtonUiContent();

  }

  private void initListener() {
    buttonPrevious.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        int tempInt = Integer.parseInt(stepId);
        tempInt = tempInt - 1;
        stepId = String.valueOf(tempInt);

        mappingCurrentStep();
        releasePlayer();
        initializeMediaSession();
        initializePlayer();
        setButtonUiContent();
        setStepUiContent();
      }
    });

    buttonNext.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        int tempInt = Integer.parseInt(stepId);
        tempInt = tempInt + 1;
        stepId = String.valueOf(tempInt);

        mappingCurrentStep();
        releasePlayer();
        initializeMediaSession();
        initializePlayer();
        setButtonUiContent();
        setStepUiContent();
      }
    });
  }

  private void initializePlayer() {
    if (currentStepItem != null) {
      url = currentStepItem.getVideoURL();
    }

    //if url not null and not empty
    if (url != null && !url.isEmpty()) {
      //if in landscape mode
      if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE &&
          standAloneMode) {
        videoPlayer.getLayoutParams().height = LayoutParams.MATCH_PARENT;
        videoPlayer.getLayoutParams().width = LayoutParams.MATCH_PARENT;

        MarginLayoutParams params = (MarginLayoutParams) videoPlayer.getLayoutParams();
        params.setMargins(0, 0, 0, 0);

        navigationSection.setVisibility(View.GONE);
        cardViewStep.setVisibility(View.GONE);
        textStep.setVisibility(View.GONE);

        hideSystemUI();
      }
    }

    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE &&
        !standAloneMode) {
      navigationSection.setVisibility(View.GONE);
    }

    // Initialize the player.
    if (url.isEmpty()) {
      videoPlayer.setVisibility(View.GONE);
    } else {
      videoPlayer.setVisibility(View.VISIBLE);
      initializePlayer(Uri.parse(url));
    }
  }

  private void initializeMediaSession() {
    mMediaSession = new MediaSessionCompat(getContext(), Constant.MEDIA_SESSION_TAG);
    mMediaSession.setFlags(
        MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
            MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

    mMediaSession.setMediaButtonReceiver(null);
    mStateBuilder = new Builder()
        .setActions(
            PlaybackStateCompat.ACTION_PLAY |
                PlaybackStateCompat.ACTION_PAUSE |
                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                PlaybackStateCompat.ACTION_PLAY_PAUSE);

    mMediaSession.setPlaybackState(mStateBuilder.build());
    mMediaSession.setCallback(new MySessionCallback());
    mMediaSession.setActive(true);
  }

  /**
   * Initialize ExoPlayer.
   *
   * @param mediaUri The URI of the sample to play.
   */
  private void initializePlayer(Uri mediaUri) {
    if (mExoPlayer == null) {
      // Create an instance of the ExoPlayer.
      TrackSelector trackSelector = new DefaultTrackSelector();
      LoadControl loadControl = new DefaultLoadControl();
      mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
      videoPlayer.setPlayer(mExoPlayer);

      // Set the ExoPlayer.EventListener to this activity.
      mExoPlayer.addListener(this);

      // Prepare the MediaSource.
      String userAgent = Util.getUserAgent(getActivity(), "BakingApp");
      MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
          getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
      mExoPlayer.prepare(mediaSource);
      mExoPlayer.setPlayWhenReady(true);
    }
  }


  /**
   * Release ExoPlayer.
   */
  private void releasePlayer() {
    if (mExoPlayer != null) {
      mExoPlayer.stop();
      mExoPlayer.release();
      mExoPlayer = null;
    }

  }

  @Override
  public void onTimelineChanged(Timeline timeline, Object manifest) {

  }

  @Override
  public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

  }

  @Override
  public void onLoadingChanged(boolean isLoading) {

  }

  @Override
  public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
    if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
      mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
          mExoPlayer.getCurrentPosition(), 1f);
    } else if ((playbackState == ExoPlayer.STATE_READY)) {
      mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
          mExoPlayer.getCurrentPosition(), 1f);
    }
    mMediaSession.setPlaybackState(mStateBuilder.build());
  }

  @Override
  public void onPlayerError(ExoPlaybackException error) {

  }

  @Override
  public void onPositionDiscontinuity() {

  }

  private void hideSystemUI() {
    ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    //Use Google's "LeanBack" mode to get fullscreen in landscape
    getActivity().getWindow().getDecorView().setSystemUiVisibility(
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
    releasePlayer();
    if (mMediaSession != null) {
      mMediaSession.setActive(false);
    }
  }

  private void setButtonBackUiIsEnabled(Boolean status) {
    if (status) {
      buttonPrevious.setEnabled(true);
      buttonPrevious.setBackgroundResource(R.drawable.background_button_rounded_colored_border);
      buttonPrevious.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));

      if (Integer.parseInt(stepId) == stepsItemList.size()) {
        buttonPrevious.setText(getString(R.string.back_placeholder, previousTitle));
      } else {
        buttonPrevious.setText(getString(R.string.back_caption));
      }
    } else {
      buttonPrevious.setEnabled(false);
      buttonPrevious.setBackgroundResource(R.drawable.background_button_rounded_disabled_border);
      buttonPrevious.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_75));
      buttonPrevious.setText(getString(R.string.back_caption));
    }
  }

  private void setButtonNextUiIsEnabled(Boolean status) {
    if (status) {
      buttonNext.setEnabled(true);
      buttonNext.setBackgroundResource(R.drawable.background_button_rounded_colored_border);
      buttonNext.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
      buttonNext.setText(getString(R.string.next_placeholder, nextTitle));
    } else {
      buttonNext.setEnabled(false);
      buttonNext.setBackgroundResource(R.drawable.background_button_rounded_disabled_border);
      buttonNext.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_75));
      buttonNext.setText(getString(R.string.next_caption));
    }
  }

  private class MySessionCallback extends Callback {

    @Override
    public void onPlay() {
      mExoPlayer.setPlayWhenReady(true);
    }

    @Override
    public void onPause() {
      mExoPlayer.setPlayWhenReady(false);
    }

    @Override
    public void onSkipToPrevious() {
      mExoPlayer.seekTo(0);
    }
  }
}
