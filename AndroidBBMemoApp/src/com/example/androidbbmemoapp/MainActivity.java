package com.example.androidbbmemoapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.dto.LogDTO;

public class MainActivity extends Activity {

	public String tag = "bb";

	private EditText idText;
	private EditText pwdText;

//	BackgroundTask task;
	String requestURL;
//	="http://192.168.0.29:8100/99JSP_myEMP/sel.jsp";
//	http://localhost:8099/BBMemoWebService/LogonController?id=2&pwd=1234
	
	Handler handler = new Handler();
	    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}
	

	public void loginBtnClicked(View v){
		  idText = (EditText) findViewById(R.id.idText);
		  pwdText = (EditText) findViewById(R.id.pwdText);
		  Log.i(tag, "test!!!");
		  String id = idText.getText().toString();
		  String pwd = pwdText.getText().toString();
		
		  Log.i(tag, id);
		  Log.i(tag, pwd);
	
		  
		  requestURL = "http://192.168.0.47:8099/BBMemoWebService/LogonController?id="+id+"&pwd=" + pwd;
		  Log.i(tag, requestURL);
		  
		  ConnectThread thread = new ConnectThread(requestURL);
		  thread.start();
//		  task = new BackgroundTask();
//		  task.execute(requestURL);
		  
		

		/*Intent loginIntent = new Intent(getApplicationContext(), );
		startActivity(loginIntent);*/
	}

	class ConnectThread extends Thread{
		String urlStr;
		public ConnectThread() {
		}
		public ConnectThread(String urlStr) {
			super();
			this.urlStr = urlStr;
		}
		@Override
		public void run() {
			  InputStream is = requestGet(requestURL);
	          String data = streamToString(is);
	          
	          LogDTO logDto = getXML(is);
	          Log.i(tag, logDto.getId());
	          Log.i(tag, logDto.getPwd());
		}
	}
	public InputStream requestGet(String requestURL) {
        Log.i(tag, "requestGet start");
        try {
             
//      1. 
        HttpClient client = new DefaultHttpClient();
       	HttpGet get = new HttpGet(requestURL);
   
//		2.
         HttpResponse response = client.execute(get);
// 		3.
         HttpEntity entity = response.getEntity();
         InputStream is = entity.getContent();
         return is;
             
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }//end requestGet()
    
    public String streamToString(InputStream is) {
       
       StringBuffer buffer = new StringBuffer();
       try {
         
          BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
          String str = reader.readLine();
          while(str != null) {
             buffer.append(str+"\n");
             str = reader.readLine();
          }
          reader.close();
          
      } catch (Exception e) {
         e.printStackTrace();
      }
       
       return buffer.toString();
    }//end streamToString()
    
    
    public LogDTO getXML(InputStream is) {
//       ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
       Log.i(tag, "getXML start!");
       URL text = null;
       LogDTO logDTO = null;
      try {
         text = new URL(requestURL);
         String host = text.getHost();
         Log.i(tag, "new URL !!!   host :: "+host);
      } catch (MalformedURLException e1) {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      }
       try {
          XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
          XmlPullParser parser = factory.newPullParser();
         
         parser.setInput(text.openStream(), "UTF-8");
         
         int eventType = parser.getEventType();
         
//         BoardDTO dto = null;
         Log.i(tag, "before while eventType");
         while( eventType != XmlPullParser.END_DOCUMENT) {
        	    Log.i(tag, "while eventType");
            switch(eventType) {
            
            case XmlPullParser.START_TAG:
            	  Log.i(tag, "XmlPullParser.START_TAG");
               String startTag = parser.getName();
               Log.i(tag, "startTag !!! "+startTag);
               if(startTag.equals("logon")){ 
            	   Log.i(tag, "logon !!!");
            	   logDTO = new LogDTO();
               }
               if(startTag.equals("record")){ 
            	   Log.i(tag, "record !!!");
            	   logDTO = new LogDTO();
               }
               
               if(logDTO != null ) {
                  if(startTag.equals("user_cnt")){ logDTO.setUser_cnt(Integer.parseInt(parser.nextText())); }
                  if(startTag.equals("id")){ logDTO.setId(parser.nextText()); 
                  Log.i(tag, "parser.nextText() :: "+parser.nextText());
                  }
                  if(startTag.equals("pwd")){ logDTO.setPwd(parser.nextText());
                  Log.i(tag, "parser.nextText() :: "+parser.nextText());
                  }
               } else {
            	   Log.i(tag, "logDTO = null"); 
            	   Log.i(tag, "logDTO = null ");   
               }
               break;
               
            case XmlPullParser.END_TAG:
               
               String endTag = parser.getName();
//               if(endTag.equals("record")){ 
//
//                  list.add(dto);
//               }
            }//end switch
            
            eventType = parser.next();
            
         }//end while
         if(logDTO == null){
            Log.d(tag,"logDTO null!!!!!!! emptpy");
         }
            Log.i(tag,logDTO.getId()+" "+logDTO.getPwd());
          
      } catch (XmlPullParserException e) {
    	  Log.i(tag, "XmlPullParserException :: "+e);
         e.printStackTrace();
      } catch (IOException ie) {
    	  Log.i(tag, "IOException :: "+ie);
         ie.printStackTrace();
      }
       return logDTO;
    }
 
    /*
  class BackgroundTask extends AsyncTask<String, String, LogDTO>{
          
          protected LogDTO doInBackground(String ... value){
             InputStream is = requestGet(requestURL);
             String data = streamToString(is);
             
             LogDTO logDto = getXML(is);
             
            return logDto;
          }
          
//          protected void onPostExecute(LogDTO logDto){
//             adapter = new CustomAdapter(getApplicationContext(),list, R.layout.custom_list);
//             lv.setAdapter(adapter);
//          }
   }
   */
  
  @Override
protected void onResume() {
	super.onResume();
//	task = new BackgroundTask();
//    task.execute(requestURL);
}

}
