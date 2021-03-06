package bhanuteja.android.com.backingapp.ui.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import bhanuteja.android.com.backingapp.R;
import bhanuteja.android.com.backingapp.ui.activities.HomeActivity;

/**
 * Implementation of App Widget functionality.
 */
public class HomeWidget extends AppWidgetProvider {


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.home_widget);
            Intent intent = new Intent(context, HomeActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
            remoteViews.setOnClickPendingIntent(R.id.appwidget_text,pendingIntent);

            Intent intent1 = new Intent(context,WidgetService.class);
            remoteViews.setRemoteAdapter(R.id.widgetlistview,intent1);
            appWidgetManager.updateAppWidget(appWidgetId,remoteViews);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName componentName = new ComponentName(context, WidgetDataProvider.class);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetManager.getAppWidgetIds(componentName), R.id.widgetlistview);
        }
        super.onReceive(context, intent);
    }
}

