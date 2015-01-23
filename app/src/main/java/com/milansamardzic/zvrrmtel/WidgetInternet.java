package com.milansamardzic.zvrrmtel;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.milansamardzic.pozovime.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ms on 1/10/15.
 */
public class WidgetInternet extends AppWidgetProvider {


    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        for (int i = 0; i < N; i++) {
            int appWidgetId = appWidgetIds[i];

            String phone = "*100*6*2*2*3#";
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", phone, null));

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            views.setOnClickPendingIntent(R.id.widgetButton, pendingIntent);

              views.setTextViewText(R.id.widgetButton, "INTERNET");

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

    }

}
