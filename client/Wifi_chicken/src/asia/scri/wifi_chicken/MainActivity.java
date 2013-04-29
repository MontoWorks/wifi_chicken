package asia.scri.wifi_chicken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
//http通信
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.net.http.AndroidHttpClient;
//wifi関連
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class MainActivity extends Activity {
	//private String[] mStrings =  new String[10];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//リスト使うよ
		ListView lv = new ListView(this);
		setContentView(lv);
		//wifiスキャンした結果をリストへ
		final WifiManager manager = (WifiManager) getSystemService(WIFI_SERVICE);
        if (manager.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
        	List<ScanResult> results = manager.getScanResults();
        	final String[] items = new String[results.size()];
        	for (int i=0;i<results.size();++i) {
        		items[i] = results.get(i).SSID+":"+results.get(i).level;
        	}
        	final ArrayAdapter<String> adapter = 
        		new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        	lv.setAdapter(adapter);
        }
        
        //http通信
        AndroidHttpClient httpClient = AndroidHttpClient.newInstance("User Agent");
        HttpGet request = new HttpGet("http://buskul.ap01.aws.af.cm/higashi.php");
        HttpResponse response = null;
        try {
            response = httpClient.execute(request);
           
            String datahoge = response.toString();
            Log.d("tag", datahoge);
            	
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        /*String url="http://buskul.ap01.aws.af.cm/higashi.php";
        String contents[] = new String[10];
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        ArrayList <NameValuePair> params = new ArrayList <NameValuePair>();
		params.add( new BasicNameValuePair("content", contents[0]));
        HttpResponse res = null;
         
        try {
            post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
            res = httpClient.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        
    	/*
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mStrings);
		
		lv.setAdapter(adapter);*/
		/*setContentView(R.layout.activity_main);
		//リストビュー
		ListView lv = new ListView(this);
		setContentView(lv);
		//System.out.println(mStrings[0]);
		//mStrings[0] = "ほげ";
		
		// lv.setSelection(1);
		// lv.setTextFilterEnabled(true);
		WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo w_info = wifiManager.getConnectionInfo();
        /*mStrings[0] = "Sample"+"SSID:"+w_info.getSSID();
        mStrings[1] = "Sample"+"BSSID:"+w_info.getBSSID();
        mStrings[2] = "Sample"+"IP Address:"+w_info.getIpAddress();
        mStrings[3] = "Sample"+"Mac Address:"+w_info.getMacAddress();
        mStrings[4] = "Sample"+"Network ID:"+w_info.getNetworkId();
        mStrings[5] = "Sample"+"Link Speed:"+w_info.getLinkSpeed();
		*/
        /*
        mStrings[0] = "Sample"+"BSSID:"+w_info.getBSSID();
        mStrings[1] = "Sample"+"BSSID:"+w_info.getBSSID();
        mStrings[2] = "Sample"+"BSSID:"+w_info.getBSSID();
        mStrings[3] = "Sample"+"BSSID:"+w_info.getBSSID();
        mStrings[4] = "Sample"+"BSSID:"+w_info.getBSSID();
        mStrings[5] = "Sample"+"BSSID:"+w_info.getBSSID();
        */
		/*
		WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
		int wifiState = wifiManager.getWifiState();
		
		System.out.println（ wifiState ）;
		switch (wifiState) {
		/*case WifiManager.WIFI_STATE_DISABLING:
			mStrings = ("WifiState","WIFI_STATE_DISABLING");
		    break;
		*//*case WifiManager.WIFI_STATE_DISABLED:
			mStrings[1] = "WifiState"+ "WIFI_STATE_DISABLED";
		    break;
		case WifiManager.WIFI_STATE_ENABLING:
		    Log.v("WifiState", "WIFI_STATE_ENABLING");
		    break;
		case WifiManager.WIFI_STATE_ENABLED:
		    Log.v("WifiState", "WIFI_STATE_ENABLED");
		    break;
		case WifiManager.WIFI_STATE_UNKNOWN:
		    Log.v("WifiState", "WIFI_STATE_UNKNOWN");
		    break;
		}*/
		//リストに書き出し
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
