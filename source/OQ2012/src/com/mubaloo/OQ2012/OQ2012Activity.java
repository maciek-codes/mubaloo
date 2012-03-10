package com.mubaloo.OQ2012;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class OQ2012Activity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("Hello, Olympic Quiz 2012");
        setContentView(tv);
    }
}