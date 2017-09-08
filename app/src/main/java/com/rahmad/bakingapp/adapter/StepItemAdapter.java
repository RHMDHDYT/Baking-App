package com.rahmad.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.rahmad.bakingapp.R;
import com.rahmad.bakingapp.adapter.StepItemAdapter.ViewHolder;
import com.rahmad.bakingapp.model.pojo.StepsItem;
import java.util.List;

/**
 * Created by rahmad on 9/1/17.
 * inbox.rahmad@gmail.com
 * Copyright 2017
 */


public class StepItemAdapter extends RecyclerView.Adapter<ViewHolder> {

  private final List<StepsItem> mValues;
  private Context context;
  private StepItemAdapterOnClickHandler clickHandler;

  /**
   * Instantiates a new Step item adapter.
   *
   * @param items the items
   * @param context the context
   * @param clickHandler the click handler
   */
  public StepItemAdapter(List<StepsItem> items, Context context, StepItemAdapterOnClickHandler
      clickHandler) {
    mValues = items;
    this.context = context;
    this.clickHandler = clickHandler;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.step_list_content, parent, false);

    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, final int position) {
    String stepNo = String.valueOf(mValues.get(position).getStepId());
    String step = mValues.get(position).getShortDescription();
    Boolean isSelected = mValues.get(position).getSelected();

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(stepNo).append(". ");
    stringBuilder.append(step);

    holder.mContentView.setText(stringBuilder.toString());

    if (isSelected == null || !isSelected) {
      holder.linearItem.setBackgroundColor(context.getResources().getColor(R.color.white_ff));
      holder.mContentView.setTextColor(context.getResources().getColor(R.color.gray_75));
      holder.iconRight.setImageResource(R.drawable.ic_chevron_right);
    } else {
      holder.linearItem.setBackgroundColor(context.getResources().getColor(R.color.red_D32F));
      holder.mContentView.setTextColor(context.getResources().getColor(R.color.white_ff));
      holder.iconRight.setImageResource(R.drawable.ic_chevron_right_white);
    }
  }

  @Override
  public int getItemCount() {
    return mValues.size();
  }

  /**
   * Sets selected.
   *
   * @param position the position
   */
  public void setSelected(int position) {
    for (int i = 0; i < mValues.size(); i++) {
      mValues.get(i).setSelected(false);
    }

    mValues.get(position).setSelected(true);

    notifyDataSetChanged();
  }

  /**
   * The interface Step item adapter on click handler.
   */
  public interface StepItemAdapterOnClickHandler {

    /**
     * On click.
     *
     * @param stepId the step id
     * @param position the position
     */
    void onClick(String stepId, int position);
  }

  /**
   * The type View holder.
   */
  public class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

    /**
     * The M view.
     */
    public final View mView;
    /**
     * The M content view.
     */
    @BindView(R.id.content)
    TextView mContentView;
    /**
     * The Linear item.
     */
    @BindView(R.id.linear_item)
    LinearLayout linearItem;
    /**
     * The Icon right.
     */
    @BindView(R.id.icon_right)
    ImageView iconRight;

    /**
     * Instantiates a new View holder.
     *
     * @param view the view
     */
    public ViewHolder(View view) {
      super(view);
      mView = view;
      view.setOnClickListener(this);
      ButterKnife.bind(this, view);

    }

    @Override
    public String toString() {
      return super.toString() + " '" + mContentView.getText() + "'";
    }

    @Override
    public void onClick(View view) {
      int adapterPosition = getAdapterPosition();
      String stepId = String.valueOf(mValues.get(adapterPosition).getStepId());
      clickHandler.onClick(stepId, adapterPosition);
    }
  }
}