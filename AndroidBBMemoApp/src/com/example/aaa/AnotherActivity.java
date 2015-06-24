package com.example.aaa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class AnotherActivity extends Activity {
	int a = 1;
	public String tag = "bb";
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
	        setContentView(R.layout.another);
	      
	 }
	 public void Button11(View v){
		 	Log.i(tag, "clicked!! board btn");
			Intent myIntent = new Intent(getApplicationContext(),BoardMain.class);
			startActivity(myIntent);
		}

}
