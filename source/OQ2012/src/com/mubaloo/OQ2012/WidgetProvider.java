package com.mubaloo.OQ2012;

import java.util.Calendar;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

public class WidgetProvider extends AppWidgetProvider {
	
	 private SharedPreferences myStats;
	 
	 
	 
	  public void onReceive(Context context, Intent intent) {
		  AppWidgetManager appWidgetmanager = AppWidgetManager.getInstance(context);
		  int id = intent.getIntExtra(
				  AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
		  updateWidget(context, appWidgetmanager , new int[] {id} );
		  super.onReceive(context, intent);
		  
	  }
	  
	 public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

		 updateWidget(context, appWidgetManager, appWidgetIds);
		 super.onUpdate(context, appWidgetManager, appWidgetIds);
	 }

	private void updateWidget(Context context,
			AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		myStats =  context.getSharedPreferences("myStats", Context.MODE_PRIVATE);
		 if(myStats == null)
		 {
			 System.err.println("Unable to get statistics");
			 return;	 
		 }
		 
		 RemoteViews views = new RemoteViews(context.getPackageName(), 
				 R.layout.widget);
		 
		 Intent intent = new Intent(context, OQ2012Activity.class);
		 PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
		 views.setOnClickPendingIntent(R.id.widgetLayout, pendingIntent);
		 
		 int completed_days = myStats.getInt("completed", 0);
		 views.setTextViewText(R.id.widget_title, "Olympic Quiz 2012");
		 int days = getDaysLeft();
		// Display a message that a user has to wait
		if ((days >= completed_days)&&(completed_days != 0)) {
			views.setTextViewText(R.id.widget_text, "You have already answered todays questions.");
		} else {
			views.setTextViewText(R.id.widget_text, "Go on, take the challenge!");
		}
		
		appWidgetManager.updateAppWidget(appWidgetIds, views);
	}
	 
	 private int getDaysLeft() {
			Calendar c = Calendar.getInstance();
	    	int start_day = 208+1;	// 27 July + leap year
	    	int current_day = c.get(Calendar.DAY_OF_YEAR);
	    	int days = start_day-current_day;
			return days;
	}
}
