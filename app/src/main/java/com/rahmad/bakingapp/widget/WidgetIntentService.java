package com.rahmad.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.rahmad.bakingapp.R;
import com.rahmad.bakingapp.database.BakingProvider.Ingredients;
import com.rahmad.bakingapp.database.IngredientsColumns;
import com.rahmad.bakingapp.util.AppSharedPref;


/**
 * The type Widget intent service.
 */
public class WidgetIntentService extends RemoteViewsService {


  @Override
  public RemoteViewsFactory onGetViewFactory(Intent intent) {

    return new WidgetRemoteViewsFactory(this.getApplicationContext(), intent);

  }

  /**
   * The type Widget remote views factory.
   */
  class WidgetRemoteViewsFactory implements RemoteViewsFactory {

    private Context mContext;
    private int mAppWidgetId;
    private Cursor cursor;


    /**
     * Instantiates a new Widget remote views factory.
     *
     * @param context the context
     * @param intent the intent
     */
    public WidgetRemoteViewsFactory(Context context, Intent intent) {

      mContext = context;
      mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
          AppWidgetManager.INVALID_APPWIDGET_ID);
    }


    private void initCursor() {

      if (cursor != null) {
        cursor.close();
      }

      String selectionClause = IngredientsColumns.RECIPE_ID + "=?";
      String selectionArgs[] = new String[1];
      selectionArgs[0] = new AppSharedPref(mContext).getRecipeFavorite();

      cursor = mContext.getContentResolver().query(Ingredients.CONTENT_URI, null,
          selectionClause,
          selectionArgs, null);

    }

    @Override
    public void onCreate() {
      initCursor();

    }

    @Override
    public void onDataSetChanged() {
      initCursor();
    }

    @Override
    public void onDestroy() {
      cursor.close();
    }

    @Override
    public int getCount() {
      return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {

      RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_content);

      if (cursor.getCount() != 0) {
        cursor.moveToPosition(position);

        rv.setTextViewText(R.id.widget_ingredient_name,
            cursor.getString(cursor.getColumnIndex(IngredientsColumns.INGREDIENT)));

        String quantity = cursor.getString(cursor.getColumnIndex(IngredientsColumns.QUANTITY));
        String measure = cursor.getString(cursor.getColumnIndex(IngredientsColumns.MEASURE));

        rv.setTextViewText(R.id.widget_ingredient_measure, quantity + " " + measure);
      }

      return rv;

    }

    @Override
    public RemoteViews getLoadingView() {
      return null;
    }

    @Override
    public int getViewTypeCount() {
      return 1;
    }

    @Override
    public long getItemId(int position) {
      return position;
    }

    @Override
    public boolean hasStableIds() {
      return true;
    }


  }

}
