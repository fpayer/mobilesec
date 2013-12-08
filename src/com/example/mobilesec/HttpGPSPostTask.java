package com.example.mobilesec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;

public class HttpGPSPostTask extends AsyncTask<String, Long, Integer>{
		@Override
		protected Integer doInBackground(String... coordinates) {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost("http://162.243.27.156/gps");
			try {
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
				nameValuePairs.add(new BasicNameValuePair("json", (String)coordinates[0]));

				post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// Execute HTTP Post Request
				HttpResponse response = client.execute(post);
				System.out.println("=======RESPONSE: " + response + " =========");

			} catch (ClientProtocolException e) {
				System.out.println("CLIENT PROTOCOL EXCEPTION: " + e);
			} catch (IOException e) {
				System.out.println("IO EXCEPTION: " + e);
			}
			return Integer.valueOf(0);
		}

		protected void onProgressUpdate(Integer... progress) {

		}

		protected void onPostExecute(Long result) {
		}
	}