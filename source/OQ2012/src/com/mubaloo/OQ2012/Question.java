package com.mubaloo.OQ2012;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Question extends Activity implements OnClickListener, OnItemClickListener{
	private ListView answer_list;
	private Map<String, Boolean> answ;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);
        String getName = null;
        TextView countryName = (TextView)findViewById(R.id.countryName);
        TextView countryQuestion = (TextView)findViewById(R.id.countryQuestion);
        
        
        answer_list = (ListView)findViewById(R.id.answer);
        InputStream is;
        String URL1 = "http://sharp-snow-6521.herokuapp.com/countries.json";
        JSONObject jsonObj;
        
        try {
        	
        	is = new URL(URL1).openConnection().getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayBuffer buf = new ByteArrayBuffer(0);
			int current = 0;
			
			while((current = bis.read()) != -1){
				buf.append((byte)current);
			}
			
			String html = new String(buf.toByteArray());
			if(!html.startsWith("["))
				html = "["+html+"]";
			JSONArray jsons = new JSONArray(html); 
			jsonObj = jsons.getJSONObject(36);
			getName = jsonObj.getString("name");
			countryName.setText(getName);
			JSONArray questions = jsonObj.getJSONArray("questions");
			JSONObject questionObj = questions.getJSONObject(0);
			String question = questionObj.getString("question");
			countryQuestion.setText(question);
			
			JSONArray answers = questionObj.getJSONArray("answers");
			answ = new HashMap<String, Boolean>();
			for(int i = 0; i < answers.length(); i++)
			{
				String ans = answers.getJSONObject(i).getString("answer");
				boolean good = answers.getJSONObject(i).getBoolean("good");
				answ.put(ans, good);
			}
	        List<String> answerList = new ArrayList<String>(answ.keySet());
	        String[] Answers = answerList.toArray(new String[answerList.size()]);
	        System.out.println("");
	        
	        answer_list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, Answers));
	        answer_list.setOnItemClickListener(this);
	 
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
		
    	if(answer_list == null)
    		return;
    	
    	String final_ans = ((TextView)arg1).getText().toString();
		Bundle bundle = new Bundle();
    	if(answ.get(final_ans)) bundle.putString("Feedback", "true");
    	else bundle.putString("Feedback", "false");
    	Intent ans_Intent = new Intent(this.getBaseContext(), Answer.class);
   		ans_Intent.putExtras(bundle);
		startActivityForResult(ans_Intent, 0);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}