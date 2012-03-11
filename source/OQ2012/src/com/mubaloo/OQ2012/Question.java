package com.mubaloo.OQ2012;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Question extends Activity implements OnItemClickListener {
	private ListView answer_list;
	private Map<String, Boolean> answ;
	public int i;
	public int score;
	public int country;
	private Bundle bundle = new Bundle();
	public long start;
	public long duration;
	int Gold;
	int Silver;
	int Bronze;
	Context context;
	String[] Answers;
	private boolean isfinished;
	Bitmap bitmap;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question);
		String getName = null;
		TextView countryName = (TextView)findViewById(R.id.countryName);
		TextView countryQuestion = (TextView)findViewById(R.id.countryQuestion);

		context = this;
		answer_list = (ListView)findViewById(R.id.answer);
		InputStream is;
		JSONObject jsonObj;

		try {

			Calendar c = Calendar.getInstance();
			int start_day = 208+1; // 27 July + leap year
			int current_day = c.get(Calendar.DAY_OF_YEAR);
			int country = 149 - (start_day-current_day);
			String URL1 = "http://sharp-snow-6521.herokuapp.com/countries/"+country+".json";

			is = new URL(URL1).openConnection().getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayBuffer buf = new ByteArrayBuffer(0);
			int current = 0;

			while((current = bis.read()) != -1){
				buf.append((byte)current);
			}
			bis.close();
			String html = new String(buf.toByteArray());

			jsonObj = new JSONObject(html);

			getName = jsonObj.getString("name");
			countryName.setText("Questions on "+getName);

			// Get flag
			View layout = findViewById(R.id.flagLayout); 
			try 
			{
				String imageUrl = jsonObj.getString("image_url");
				bitmap = BitmapHelper.get(new URL(imageUrl));
				Drawable d = new BitmapDrawable(bitmap);
				layout.setBackgroundDrawable(d);
			} 
			catch (MalformedURLException e) 
			{
				layout.setBackgroundResource(R.drawable.background);
				e.printStackTrace();
			} 
			catch (IOException e) 
			{
				layout.setBackgroundResource(R.drawable.background); 
				e.printStackTrace();
			}

			JSONArray questions = jsonObj.getJSONArray("questions");
			JSONObject questionObj;
			bundle = this.getIntent().getExtras();
			if(bundle.isEmpty()) {
				i=0;
				score  = 0 ;
				Gold = 0;
				Silver = 0;
				Bronze = 0;
			}
			else {
				bundle = this.getIntent().getExtras();
				i = bundle.getInt("number");
				score = bundle.getInt("Score");
				Gold = bundle.getInt("Gold");
				Silver = bundle.getInt("Silver");
				Bronze = bundle.getInt("Bronze");
			}


			questionObj = questions.getJSONObject(i);

			String question = questionObj.getString("question");
			countryQuestion.setText("Your score: " + score + "\n" + (i+1) +". "+ question);
			JSONArray answers = questionObj.getJSONArray("answers");
			answ = new HashMap<String, Boolean>();
			for(int i = 0; i < answers.length(); i++)
			{
				String ans = answers.getJSONObject(i).getString("answer");
				boolean good = answers.getJSONObject(i).getBoolean("good");
				answ.put(ans, good);
			}
			List<String> answerList = new ArrayList<String>(answ.keySet());
			Collections.shuffle(answerList);
			Answers = answerList.toArray(new String[answerList.size()]);
			start = System.currentTimeMillis();
			new CountDownTimer(12000, 1000) 
			{
				public void onTick(long millisUntilFinished) 
				{
					
					answer_list.setAdapter(new ArrayAdapter<String>(
							context, android.R.layout.simple_list_item_single_choice, Answers));
					answer_list.setOnItemClickListener((OnItemClickListener) context);
				}

				public void onFinish() 
				{
					if (isfinished) return;
					bundle.putString("Feedback", "false");
					bundle.putInt("number", i);
					bundle.putInt("Score", score);
					bundle.putLong("Duration", 12000);
					bundle.putInt("Gold", Gold);
					bundle.putInt("Silver", Silver);
					bundle.putInt("Bronze", Bronze);

					Intent ans_Intent = new Intent(context, Answer.class);
					ans_Intent.putExtras(bundle);
					startActivityForResult(ans_Intent, 0);
				}
			}.start();

		} catch (MalformedURLException e) {
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Error");
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		duration = System.currentTimeMillis() - start;

		if(answer_list == null) return;

		String final_ans = ((TextView)arg1).getText().toString();

		if(answ.get(final_ans)) bundle.putString("Feedback", "true");
		else bundle.putString("Feedback", "false");
		bundle.putInt("number", i);
		bundle.putInt("Score", score);
		bundle.putLong("Duration", duration);
		bundle.putInt("Gold", Gold);
		bundle.putInt("Silver", Silver);
		bundle.putInt("Bronze", Bronze);

		Intent ans_Intent = new Intent(this.getBaseContext(), Answer.class);
		ans_Intent.putExtras(bundle);
		startActivityForResult(ans_Intent, 0);
		isfinished = true;
	}
}