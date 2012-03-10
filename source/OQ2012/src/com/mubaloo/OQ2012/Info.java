package com.mubaloo.OQ2012;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class Info extends Activity 
{
	private TextView tv_info;
	private Typeface customFont;
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        tv_info = (TextView) findViewById(R.id.tv_info);
        tv_info.setSingleLine(false);
        tv_info.setText("Developed By:\n"+"-Derek Bekoe\n"+"-Maciej Kumorek\n"+"-Madalina Patrichi\n"+"-Ricardo Rendon Cepeda\n"+"\n"+"Sponsored By:\n"+"-University of Bristol\n"+"-Mubaloo");
        customFont 	= Typeface.createFromAsset(getAssets(), "fonts/LONDON2012.TTF");
        tv_info.setTypeface(customFont);
        tv_info.setTextColor(Color.WHITE);
    }
}
