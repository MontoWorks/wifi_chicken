package asia.scri.wifi_chicken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ListActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.net.http.AndroidHttpClient;
//wifi関連
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class MainActivity extends Activity implements APICallBack {
	// private String[] mStrings = new String[10];

	//ValueにはAPの最終確認時刻(UNIXタイム）
	HashMap<String, Long> apStates;
	JSONObject venues;

	private Handler handler = new Handler();
	private static final long UPDATE_INTERVAL = 1000;
	private ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// リスト使うよ
		lv = new ListView(this);
		setContentView(lv);

		// wifiスキャンした結果をリストへ
		final WifiManager manager = (WifiManager) getSystemService(WIFI_SERVICE);
		if (manager.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
			List<ScanResult> results = manager.getScanResults();
			final String[] items = new String[results.size()];
			for (int i = 0; i < results.size(); ++i) {
				items[i] = results.get(i).SSID + ":" + results.get(i).level;
			}
			final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, items);
			lv.setAdapter(adapter);
		}

		
		GetVenuesAsyncTask getVenueAsyncTask = new GetVenuesAsyncTask(this);
		getVenueAsyncTask.execute();
		/*
		 * //http通信 AndroidHttpClient httpClient =
		 * AndroidHttpClient.newInstance("User Agent"); HttpGet request = new
		 * HttpGet("http://buskul.ap01.aws.af.cm/higashi.php"); HttpResponse
		 * response = null; try { response = httpClient.execute(request);
		 * 
		 * String datahoge = response.toString(); Log.d("tag", datahoge);
		 * 
		 * } catch (IOException e) { e.printStackTrace(); }
		 * 
		 * /*String url="http://buskul.ap01.aws.af.cm/higashi.php"; String
		 * contents[] = new String[10]; HttpClient httpClient = new
		 * DefaultHttpClient(); HttpPost post = new HttpPost(url); ArrayList
		 * <NameValuePair> params = new ArrayList <NameValuePair>(); params.add(
		 * new BasicNameValuePair("content", contents[0])); HttpResponse res =
		 * null;
		 * 
		 * try { post.setEntity(new UrlEncodedFormEntity(params, "utf-8")); res
		 * = httpClient.execute(post); } catch (IOException e) {
		 * e.printStackTrace(); }
		 */

		/*
		 * ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		 * android.R.layout.simple_list_item_1, mStrings);
		 * 
		 * lv.setAdapter(adapter);
		 */
		/*
		 * setContentView(R.layout.activity_main); //リストビュー ListView lv = new
		 * ListView(this); setContentView(lv);
		 * //System.out.println(mStrings[0]); //mStrings[0] = "ほげ";
		 * 
		 * // lv.setSelection(1); // lv.setTextFilterEnabled(true); WifiManager
		 * wifiManager = (WifiManager) getSystemService(WIFI_SERVICE); WifiInfo
		 * w_info = wifiManager.getConnectionInfo(); /*mStrings[0] =
		 * "Sample"+"SSID:"+w_info.getSSID(); mStrings[1] =
		 * "Sample"+"BSSID:"+w_info.getBSSID(); mStrings[2] =
		 * "Sample"+"IP Address:"+w_info.getIpAddress(); mStrings[3] =
		 * "Sample"+"Mac Address:"+w_info.getMacAddress(); mStrings[4] =
		 * "Sample"+"Network ID:"+w_info.getNetworkId(); mStrings[5] =
		 * "Sample"+"Link Speed:"+w_info.getLinkSpeed();
		 */
		/*
		 * mStrings[0] = "Sample"+"BSSID:"+w_info.getBSSID(); mStrings[1] =
		 * "Sample"+"BSSID:"+w_info.getBSSID(); mStrings[2] =
		 * "Sample"+"BSSID:"+w_info.getBSSID(); mStrings[3] =
		 * "Sample"+"BSSID:"+w_info.getBSSID(); mStrings[4] =
		 * "Sample"+"BSSID:"+w_info.getBSSID(); mStrings[5] =
		 * "Sample"+"BSSID:"+w_info.getBSSID();
		 */
		/*
		 * WifiManager wifiManager = (WifiManager)
		 * getSystemService(WIFI_SERVICE); int wifiState =
		 * wifiManager.getWifiState();
		 * 
		 * System.out.println（ wifiState ）; switch (wifiState) { /*case
		 * WifiManager.WIFI_STATE_DISABLING: mStrings =
		 * ("WifiState","WIFI_STATE_DISABLING"); break;
		 *//*
			 * case WifiManager.WIFI_STATE_DISABLED: mStrings[1] = "WifiState"+
			 * "WIFI_STATE_DISABLED"; break; case
			 * WifiManager.WIFI_STATE_ENABLING: Log.v("WifiState",
			 * "WIFI_STATE_ENABLING"); break; case
			 * WifiManager.WIFI_STATE_ENABLED: Log.v("WifiState",
			 * "WIFI_STATE_ENABLED"); break; case
			 * WifiManager.WIFI_STATE_UNKNOWN: Log.v("WifiState",
			 * "WIFI_STATE_UNKNOWN"); break; }
			 */
		// リストに書き出し

		// APStateの初期化
		apStates = new HashMap<String, Long>();

		// JSONObjectでVenueの情報を格納
		try {
			venues = new JSONObject(
					"{\"00:24:a5:31:29:18\":"
					+ "{\"name\":\"Test Venue\"}}");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			Log.d("venues", venues.toString(2));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 強制的に代入
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onReceiveJSONObject(JSONObject jsonObject) {
/*		try {
		Toast.makeText(this, jsonObject.toString(2), Toast.LENGTH_LONG)
				.show();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	@Override
	protected void onResume() {
		super.onResume();

		// Handlerを生成し、 AP情報を監視、更新
		handler.postDelayed(new APUpdater(), UPDATE_INTERVAL);
	}

	class APUpdater implements Runnable {
		@Override
		public void run() {
			WifiManager manager = (WifiManager) getSystemService(WIFI_SERVICE);
			if (manager.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {

				List<ScanResult> results = manager.getScanResults();
				String[] items = new String[results.size()];
				for (int i = 0; i < results.size(); ++i) {
					// 対象のSSIDがVenueとして登録されている場合
					
					items[i] = results.get(i).SSID + ":" + results.get(i).level;
				}
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						MainActivity.this, android.R.layout.simple_list_item_1,
						items);
				lv.setAdapter(adapter);
				manager.startScan();
			}
			handler.post(new CheckInOutChecker());
			handler.postDelayed(new APUpdater(), UPDATE_INTERVAL);
		}
	}

	class CheckInOutChecker implements Runnable {
		@Override
		public void run() {
			// それぞれのVenueに対して、チェックインしているかを確認
			WifiManager manager = (WifiManager) getSystemService(WIFI_SERVICE);
			if (manager.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
				List<ScanResult> results = manager.getScanResults();
				for (int i = 0; i < results.size(); ++i) {
					String bssid = results.get(i).BSSID;
					Log.d("bssid=",apStates.toString());
					// 登録ベニューにヒットした場合、チェクインの可能性あり
					try {
						JSONObject venue = venues.getJSONObject(bssid);
						// 存在する場合、apStatesを確認して、既にチェックインしていないか確認。
						Log.d("appStates=",apStates.toString());
						
						if (apStates.get(bssid) == null) {
							// チェックイン対象 (一回だけチェックイン）
							Toast.makeText(MainActivity.this,
									"Checkin at " + venue.get("name"), Toast.LENGTH_LONG)
									.show();
							
						}
						// いずれにせよ最終確認時刻を登録
						apStates.put(bssid, Long.valueOf(System.currentTimeMillis()));

					} catch (JSONException e) {
						// 存在しない場合、対象外
						Log.d("no venue : ",e.getMessage());
					}

				}
			}
		}
	}
}
