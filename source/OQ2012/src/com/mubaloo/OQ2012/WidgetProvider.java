package com.mubaloo.OQ2012;

import java.util.Calendar;

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
	 
	 public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

		 myStats =  context.getSharedPreferences("myStats", Context.MODE_PRIVATE);
		 
		 if(myStats == null)
		 {
			 System.err.println("Unable to get statistics");
			 return;	 
		 }
		 
		 RemoteViews views = new RemoteViews(context.getPackageName(), 
				 R.layout.widget);
		 
		 int completed_days = myStats.getInt("completed", 0);

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
