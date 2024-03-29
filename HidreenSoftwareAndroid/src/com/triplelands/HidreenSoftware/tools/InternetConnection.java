package com.triplelands.HidreenSoftware.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
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

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.triplelands.HidreenSoftware.app.DataManager;

public class InternetConnection {
	
	private InternetConnectionHandler listener;
	private URLConnection conn;
	private HttpURLConnection httpConn;
	private InputStream is;
	private DataManager manager;

	public InternetConnection(Context ctx, InternetConnectionHandler listener) {
		this.listener = listener;
		manager = DataManager.getInstance(ctx);
	}

//	public void setAndAccessURL(String url) {
//		if (Thread.interrupted())
//			listener.onConnectionCancelled();
//		
//		try {
//			HttpClient client = new DefaultHttpClient();
//			HttpGet get = new HttpGet(url);
//			HttpResponse response = client.execute(get);
//			HttpEntity entity = response.getEntity();
//			is = entity.getContent();
//			listener.onReceivedResponse(is, (int)entity.getContentLength());
//		} catch (ClientProtocolException e) {
//			listener.onErrorConnection(e);
//		} catch (IOException e) {
//			listener.onErrorConnection(e);
//		} 
//	}
	
	public void setAndAccessURL(String urlString) {
		Log.i("tag", "akses mulai: " + urlString);
		// while(isConnecting){
		try {
			if (Thread.interrupted())
				listener.onConnectionCancelled();

			int response = -1;

			URL url = new URL(urlString);
			conn = url.openConnection();

			if (!(conn instanceof HttpURLConnection))
				throw new IOException("Not an HTTP connection");
			
			HttpURLConnection httpConn = (HttpURLConnection) conn;
			httpConn.setAllowUserInteraction(false);
			httpConn.setInstanceFollowRedirects(true);
			httpConn.setRequestMethod("GET");
			conn.setRequestProperty("Cookie",
					"UA=hisoftAndroid; HSSession=" + manager.getSessionId() + "; HSEmail="
							+ manager.getEmail());

			httpConn.setConnectTimeout(60000);
			httpConn.connect();

			int length = httpConn.getContentLength();

			response = httpConn.getResponseCode();
			if (response == HttpURLConnection.HTTP_OK) {
				is = httpConn.getInputStream();

				if (listener != null) {
					Log.i("tag", "sukses");
					listener.onReceivedResponse(is, length);
				}
			}
			if (response == HttpURLConnection.HTTP_MOVED_PERM
					|| response == HttpURLConnection.HTTP_MOVED_TEMP
					|| response == HttpURLConnection.HTTP_SEE_OTHER) {
				String location = httpConn.getHeaderField("location");
				setAndAccessURL(location);
			}
			if (response != HttpURLConnection.HTTP_OK) {
				Log.e("tag", "not ok: " + response);
				listener.onConnectionResponseNotOk();
			}

			httpConn.disconnect();
			if (is != null) {
				is.close();
				is = null;
			}

		} catch (SocketTimeoutException ex) {
			if (listener != null) {
				Log.e("error", "connection timeout");
				listener.onConnectionTimeout();
			}
		} catch (InterruptedIOException ex) {
			if (listener != null) {
				listener.onConnectionCancelled();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			if (listener != null) {
				Log.e("error", "error connecting to the internet");
				listener.onErrorConnection(ex);
			}
		}
	}

	public void postData(String url, String[] params) {

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);

		try {
			
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			for (int i = 0; i < params.length; i++) {
				String name, value;					
				String[] exploded = TextUtils.split(params[i], "=");
				
				name = exploded[0];
				if(exploded.length < 2){
					value = null;
				}else{
					value = exploded[1];
				}
					
				nameValuePairs.add(new BasicNameValuePair(name, value));
			}
			
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			httppost.addHeader("Content-Type",
					"application/x-www-form-urlencoded");
			httppost.setHeader("Cookie", "UA=hisoftAndroid; HSSession=" + manager.getSessionId() + "; HSEmail="
					+ manager.getEmail());

			HttpResponse response = httpclient.execute(httppost);

			InputStream is = response.getEntity().getContent();
			int length = (int) response.getEntity().getContentLength();
			listener.onReceivedResponse(is, length);
		} catch (ClientProtocolException e) {
			listener.onErrorConnection(e);
		} catch (IOException e) {
			listener.onErrorConnection(e);
		}
	}

	public void cancel(Thread name) {
		name.interrupt();
		try {
			if (is != null) {
				is.close();
				is = null;
			}
			if (httpConn != null) {
				httpConn.disconnect();
				httpConn = null;
			}
			if (conn != null) {
				conn = null;
			}
			listener.onConnectionCancelled();
		} catch (Exception e) {
			listener.onErrorConnection(e);
		}
	}

}
