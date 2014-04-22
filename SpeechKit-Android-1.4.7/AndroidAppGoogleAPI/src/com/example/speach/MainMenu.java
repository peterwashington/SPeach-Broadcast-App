package com.example.speach;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * This is the first activity that loads on boot.
 * It has three big buttons: Medical Mode, Training Mode, Combined Mode
 * @author Riya
 *
 */
public class MainMenu extends Activity {
	
	/** AUTO GENERATED **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }
    
    /** AUTO GENERATED **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    
    /**
     * Starts the Medical Mode activity
     * @param view
     */
    public void startMedicalMode(View view) {
    	Intent intent = new Intent(this, MedicalMode.class);
    	startActivity(intent);

    	
	//		EditText sessionNameInput = (EditText) findViewById(R.id.editText1);
	//		final String sessionName = sessionNameInput.getText().toString();
	//		
	//		Runnable r = new Runnable() {
	//			@Override
	//			public void run() {
	//				// get the session id for the new session from the server.
	//			    HttpClient httpclient = new DefaultHttpClient();
	//			    HttpPost httppost = new HttpPost("http://www.yoursite.com/script.php");
	//
	//			    try {
	//			        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	//			        nameValuePairs.add(new BasicNameValuePair("name", sessionName));
	//
	//			        // Execute HTTP Post Request
	//			        HttpResponse response = httpclient.execute(httppost);
	//			        
	//			    } catch (ClientProtocolException e) {
	//			        // TODO Auto-generated catch block
	//			    } catch (IOException e) {
	//			        // TODO Auto-generated catch block
	//			    }
	//		    	
	//			    // start the medical mode.
	//		    	Intent intent = new Intent(MainMenu.this, MedicalMode.class);
	//		    	startActivity(intent);	
	//			}
	//		};
	//		
	//		Thread t = new Thread();
	//		
	//		t.start();
    }
    
}
