package com.rahmad.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import com.rahmad.bakingapp.R;
import com.rahmad.bakingapp.util.AppSharedPref;

/**
 * The type Widget provider.
 */
public class WidgetProvider extends android.appwidget.AppWidgetProvider {

  /**
   * Update app widget.
   *
   * @param context the context
   * @param appWidgetManager the app widget manager
   * @param appWidgetId the app widget id
   */
  static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
      int appWidgetId) {

    RemoteViews views = new RemoteViews(context.getPackageName(),
        R.layout.widget_list_ingredient);

    String recipeName = new AppSharedPref(context).getRecipeFavoriteName();

    Intent intent = new Intent(context, WidgetIntentService.class);
    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
    intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

    if (recipeName.isEmpty()) {
      String caption = context.getResources().getString(R.string.no_fav_recipe_caption);
      views.setTextViewText(R.id.widget_recipe_name, caption);
    } else {
      views.setTextViewText(R.id.widget_recipe_name, recipeName);
    }

    views.setRemoteAdapter(appWidgetId, R.id.ing_widget_list, intent);

    appWidgetManager.updateAppWidget(appWidgetId, views);
  }

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

    for (int appWidgetId : appWidgetIds) {

      updateAppWidget(context, appWidgetManager, appWidgetId);
    }

    super.onUpdate(context, appWidgetManager, appWidgetIds);
  }

  @Override
  public void onEnabled(Context context) {
  }

  @Override
  public void onDisabled(Context context) {
  }
}

