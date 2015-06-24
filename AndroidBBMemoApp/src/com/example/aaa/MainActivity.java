package com.example.aaa;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	public static final int REQUEST_CODE_ANOTHER = 1001;
	public String tag = "bb";
	
	EditText idText;
	EditText pwdText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		idText = (EditText) findViewById(R.id.idText);
		pwdText = (EditText) findViewById(R.id.pwdText);
		
	}
	public void onButton1Clicked(View v){
		
		Intent myIntent = new Intent(getApplicationContext(),AnotherActivity.class);
		startActivity(myIntent);
	}
	
	public void loginBtnClicked(){
		
		  Log.i(tag, "test!!!");
//		  String id = idText.getText().toString();
//		  String pwd = pwdText.getText().toString();
//		
//		  Log.i(tag, id);
//		  Log.i(tag, pwd);
//		idText = findViewById(R.layout.memo)
		
		/*Intent loginIntent = new Intent(getApplicationContext(), );
		startActivity(loginIntent);*/
	}


	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}*/
}
