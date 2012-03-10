package com.mubaloo.OQ2012;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Question extends ListActivity implements OnClickListener {
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);
        String getName = null;
        TextView countryName;
        TextView countryQuestion;
        ListView answer_list;
        countryName = (TextView)findViewById(R.id.countryName);
        countryQuestion = (TextView)findViewById(R.id.countryQuestion);
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
			jsonObj = jsons.getJSONObject(0);
			getName = jsonObj.getString("name");
			countryName.setText(getName);
			JSONArray questions = jsonObj.getJSONArray("questions");
			String question = questions.getJSONObject(0).getString("question");
			countryQuestion.setText(question);
			
			JSONArray answers = jsonObj.getJSONArray("answers");
			HashMap answ = new HashMap();
			for(int i = 0; i < answers.length(); i++)
			{
				String ans = answers.getJSONObject(i).getString("answer");
				boolean good = answers.getJSONObject(i).getBoolean("good");
				answ.put(ans, good);
			}
	        List<String> answerList = new ArrayList<String>(answ.keySet());
	        String[] Answers = answerList.toArray(new String[answerList.size()]);
			setListAdapter(new ArrayAdapter<String>(this, R.id.answer, Answers));
		
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}