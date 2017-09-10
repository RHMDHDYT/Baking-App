package com.rahmad.bakingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.rahmad.bakingapp.R;
import com.rahmad.bakingapp.model.pojo.RecipesItem;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by rahmad on 8/27/17.
 * inbox.rahmad@gmail.com
 * Copyright 2017
 */
public class RecipeItemAdapter extends RecyclerView
    .Adapter<RecipeItemAdapter.ViewHolder> {

  private final List<RecipesItem> mValues;
  private final RecipesAdapterOnClickHandler clickHandler;
  private final Context context;

  /**
   * Instantiates a new Recipe item adapter.
   *
   * @param dummyList the dummy list
   * @param context the context
   * @param clickHandler the click handler
   */
  public RecipeItemAdapter(List<RecipesItem> dummyList, Context context,
      RecipesAdapterOnClickHandler clickHandler) {
    this.mValues = dummyList;
    this.context = context;
    this.clickHandler = clickHandler;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.recipe_list_content, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {

    holder.recipeNameTextView.setText(mValues.get(position).getName());
    String servingContent = context.getResources()
        .getString(R.string.servings_placeholder, mValues.get(position)
            .getServings());
    holder.servingsTextView.setText(servingContent);

    String imageUrl = mValues.get(position).getImage();
    if (!imageUrl.isEmpty()) {
      Picasso.with(context).load(imageUrl).into(holder.imageView);
    }

  }

  @Override
  public int getItemCount() {
    return mValues.size();
  }

  /**
   * The interface Recipes adapter on click handler.
   */
  public interface RecipesAdapterOnClickHandler {

    /**
     * On click.
     *
     * @param data the data
     */
    void onClick(String data);
  }

  /**
   * The type View holder.
   */
  public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    /**
     * The M view.
     */
    public final View mView;
    /**
     * The Recipe name text view.
     */
    @BindView(R.id.text_recipe_name)
    TextView recipeNameTextView;
    /**
     * The Servings text view.
     */
    @BindView(R.id.text_servings_amount)
    TextView servingsTextView;

    /**
     * The Image view.
     */
    @BindView(R.id.imageView)
    ImageView imageView;

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
    public void onClick(View view) {
      int adapterPosition = getAdapterPosition();
      String recipeId = String.valueOf(mValues.get(adapterPosition).getId());
      clickHandler.onClick(recipeId);
    }
  }
}
