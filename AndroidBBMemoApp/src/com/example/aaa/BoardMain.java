package com.example.aaa;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.xmlpull.v1.*;

import android.app.Activity;
import android.content.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.*;
import android.view.*;
import android.widget.*;

public class BoardMain extends Activity{
   
    Button btn;
    ListView lv;
     
    CustomAdapter adapter;
    
    BackgroundTask task;
    String requestURL="http://192.168.0.29:8100/99JSP_myEMP/sel.jsp";
    
    
    Handler handler = new Handler();

	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.board);
        
        
        lv = (ListView)findViewById(R.id.listView1);
   
    }//end onCreate
     
   public InputStream requestGet(String requestURL) {
     
        Log.i("xxx", "requestGet start");
        try {
             
        //1.
            HttpClient client = new DefaultHttpClient();
             
   
            HttpGet get = new HttpGet(requestURL);
             
            //2. ��û
         HttpResponse response = client.execute(get);
         
            //3. ��� �ޱ�
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
    
    
    public ArrayList<BoardDTO> getXML(InputStream is) {
       ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
       Log.i("xxx", "getXML start!");
       URL text = null;
      try {
         text = new URL(requestURL);
      } catch (MalformedURLException e1) {
         // TODO Auto-generated catch block
         e1.printStackTrace();
      }
       try {
          XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
          XmlPullParser parser = factory.newPullParser();
         
         parser.setInput(text.openStream(), "UTF-8");
         
         int eventType = parser.getEventType();
         
         BoardDTO dto = null;
         
         while( eventType != XmlPullParser.END_DOCUMENT) {
            switch(eventType) {
            
            case XmlPullParser.START_TAG:
               String startTag = parser.getName();
               if(startTag.equals("record")){ dto = new BoardDTO(); }
               
               if(dto != null ) {
                  if(startTag.equals("b_num")){ dto.setB_num(Integer.parseInt(parser.nextText())); }
                  if(startTag.equals("b_name")){ dto.setB_name(parser.nextText()); }
                  if(startTag.equals("b_content")){ dto.setB_content(parser.nextText()); }
               } else { Log.i("xxx", "dto = null"); }
               break;
               
            case XmlPullParser.END_TAG:
               
               String endTag = parser.getName();
               if(endTag.equals("record")){ 

                  list.add(dto);
               }
            }//end switch
            
            eventType = parser.next();
            
         }//end while
         if(list.isEmpty()){
            Log.d("empty","emptyadsasdasdasds");
         }
          for( BoardDTO xx : list){
                 Log.i("xxx",xx.getB_num()+" "+xx.getB_name()+" "+xx.getB_content());
              }
          
      } catch (XmlPullParserException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (IOException ie) {
         // TODO Auto-generated catch block
         ie.printStackTrace();
      }
       return list;
    }
       
       class BackgroundTask extends AsyncTask<String, String, ArrayList<BoardDTO>>{
          
          protected ArrayList<BoardDTO> doInBackground(String ... value){
             InputStream is = requestGet(requestURL);
             String data = streamToString(is);
             
             ArrayList<BoardDTO> list = getXML(is);
             
            return list;
          }
          
          protected void onPostExecute(ArrayList<BoardDTO> list){
             adapter = new CustomAdapter(getApplicationContext(),list, R.layout.custom_list);
             lv.setAdapter(adapter);
          }
       }
       
   @Override
   protected void onResume() {
      super.onResume();
      
      //String requestURL="http://localhost:8338/99JSP_myEMP/select2.jsp";
      task = new BackgroundTask();
      task.execute(requestURL);
      
      //InputStream is = requestGet(requestURL);
      //String data = streamToString(is);
      
      //ArrayList<EmpDTO> list = getXML(is);
      //adapter = new CustomAdapter(getApplicationContext(),list,R.layout.custom_list);
       // lv.setAdapter(adapter);
   }
}
    
