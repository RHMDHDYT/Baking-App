package com.rahmad.bakingapp.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.rahmad.bakingapp.R;
import com.rahmad.bakingapp.adapter.IngredientItemAdapter.ViewHolder;
import com.rahmad.bakingapp.database.BakingProvider.Ingredients;
import com.rahmad.bakingapp.database.IngredientsColumns;
import com.rahmad.bakingapp.model.pojo.IngredientsItem;
import java.util.List;

/**
 * Created by rahmad on 9/1/17.
 * inbox.rahmad@gmail.com
 * Copyright 2017
 */


public class IngredientItemAdapter
    extends RecyclerView.Adapter<ViewHolder> {

  private final List<IngredientsItem> mValues;
  private Context context;

  /**
   * Instantiates a new Ingredient item adapter.
   *
   * @param items the items
   * @param context the context
   */
  public IngredientItemAdapter(List<IngredientsItem> items, Context context) {
    mValues = items;
    this.context = context;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.ingredient_list_content, parent, false);

    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, final int position) {
    String ingredient = mValues.get(position).getIngredient();
    String qty = mValues.get(position).getQuantity();
    String measure = mValues.get(position).getMeasure();
    Integer isChecked = mValues.get(position).getIsChecked();

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(ingredient).append(" ");
    stringBuilder.append(qty).append(" ").append(measure);

    holder.mContentView.setText(stringBuilder.toString());

    if (isChecked == 1) {
      holder.mContentView.setChecked(true);
    } else {
      holder.mContentView.setChecked(false);
    }

    holder.mContentView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {

        String ingredientId = String.valueOf(mValues.get(position).getIngredientId());
        String recipeId = String.valueOf(mValues.get(position).getRecipeId());

        ContentValues contentValues = new ContentValues();
        contentValues.put(IngredientsColumns.ISCHECKED,
            convertCheckedToInt(holder.mContentView.isChecked()));

        context.getContentResolver().update(Ingredients.CONTENT_URI, contentValues,
            IngredientsColumns.INGREDIENT_ID + "=?" + " AND " + IngredientsColumns.RECIPE_ID + "=?",
            new String[]{ingredientId, recipeId});
      }
    });
  }

  private int convertCheckedToInt(Boolean isChecked) {
    if (isChecked) {
      return 1;
    } else {
      return 0;
    }
  }

  @Override
  public int getItemCount() {
    return mValues.size();
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
     * The M content view.
     */
    @BindView(R.id.content)
    CheckBox mContentView;

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

    }
  }
}