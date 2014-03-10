package com.example.speach;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * The medical mode.
 * It has three button: Start, Stop and Clear
 * Start will start the speech-to-text conversion, until the Stop button is pressed.
 * Clear button will clear out all the text in the textbox
 * @author Riya
 *
 */
public class MedicalMode extends Activity {
	/**
	 * Android Speech Recognizer
	 */
	private SpeechRecognizer sr;
	
	/**
	 * The intent that is passed along throughout the life of the activity
	 */
	private Intent intent;
	
	/**
	 * A inner class implements the RecognitionListener so that 
	 * we can use the speech API without the annoying google dialogue.
	 *
	 */
	private class listener implements RecognitionListener {

		@Override
		public void onBeginningOfSpeech() {
			//System.out.println("onBeginningOfSpeech");
		}

		@Override
		public void onBufferReceived(byte[] arg0) {
			//System.out.println("onBufferReceived");
		}

		@Override
		public void onEndOfSpeech() {
			//System.out.println("onEndOfSpeech");
		}

		@Override
		public void onError(int error) {
			//System.out.println("onError");
			String mError = "";
	        String mStatus = "Error detected";
	        switch (error) {
	        case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:                
	            mError = " network timeout"; 
	            //startListening();
	            break;
	        case SpeechRecognizer.ERROR_NETWORK: 
	            mError = " network" ;
	            //toast("Please check data bundle or network settings");
	            return;
	        case SpeechRecognizer.ERROR_AUDIO: 
	            mError = " audio"; 
	            break;
	        case SpeechRecognizer.ERROR_SERVER: 
	            mError = " server"; 
	            //startListening();
	            break;
	        case SpeechRecognizer.ERROR_CLIENT: 
	            mError = " client"; 
	            break;
	        case SpeechRecognizer.ERROR_SPEECH_TIMEOUT: 
	            mError = " speech time out" ; 
	            break;
	        case SpeechRecognizer.ERROR_NO_MATCH: 
	            mError = " no match" ; 
	            sr.startListening(intent);
	            break;
	        case SpeechRecognizer.ERROR_RECOGNIZER_BUSY: 
	            mError = " recogniser busy" ; 
	            break;
	        case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS: 
	            mError = " insufficient permissions" ; 
	            break;

	        }
	        System.out.println("Error: " +  error + " - " + mError);
		}

		@Override
		public void onEvent(int arg0, Bundle arg1) {
			//System.out.println("onEvent");
		}

		@Override
		public void onPartialResults(Bundle arg0) {
			//System.out.println("onPartialResults");
		}

		@Override
		public void onReadyForSpeech(Bundle arg0) {
			//System.out.println("onReadyForSpeech");
		}

		/**
		 * Recursively calls startListening() so that the speech recognizer will keep on listening to the voice
		 * Note that during the time that the results are made and startListening() called, all utterances are 
		 * not buffered and therefore lost.
		 * 
		 * Send the results to the server.
		 */
		@Override
		public void onResults(Bundle data) {
			//System.out.println("onResults");
			final ArrayList<String> text = data.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION); // obtain the result
			EditText displayText = (EditText) findViewById(R.id.display_message);
            displayText.append(text.get(0)+"\n"); // put text on the text box
            
            Runnable r = new Runnable() {

				public void run() {
					// Create a new HttpClient and Post Header.
		            HttpClient httpclient = new DefaultHttpClient();
		            HttpPost httppost = new HttpPost("http://10.117.76.102:9000/api/add");
		            //only works on Rice owls -Andres
		            try {
		                // Add your data
		                httppost.setEntity(new StringEntity(text.get(0)));
		                // Execute HTTP Post Request
		                HttpResponse response = httpclient.execute(httppost);
		                
		            } catch (ClientProtocolException e) {
		                System.out.println("CLIENT-PROTOCOL-EXCEPTION!!!!!");
		            } catch (IOException e) {
		                System.out.println("IO-EXCEPTION!!!!!");
		            }
					
				}
            	
            };
            
            Thread t = new Thread(r);
            t.start();
           
            
            sr.startListening(intent); // recursively call
		}

		@Override
		public void onRmsChanged(float arg0) {
			//System.out.println("onRmsChanged");
		}
		
	}
	
	/** AUTO GENERATED **/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_medical_mode);
		
		/**
		 * Speech Recognition
		 */
		sr = SpeechRecognizer.createSpeechRecognizer(this); 
        sr.setRecognitionListener(new listener()); // Sets the RecognizerListner to the inner class
	}
	
	/** AUTO GENERATED **/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.medical_mode, menu);
		return true;
	}
	
	/**
	 * Clears the text written in the text box
	 * @param view
	 */
	public void clearText(View view) {
		EditText displayText = (EditText) findViewById(R.id.display_message);
        displayText.setText("");
	}
	
	/**
	 * Stops the Recognizer
	 * @param view
	 */
	public void stopRecognizer(View view) {
		sr.stopListening();
		sr.cancel();
	}
	
	/**
	 * Starts the Recognizer
	 * @param view
	 */
	public void startRecognizer(View view) {
		intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US"); // TODO: Try out the "FREE_FORM"
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName()); // Need to specify this
        sr.startListening(intent);
    }

}
