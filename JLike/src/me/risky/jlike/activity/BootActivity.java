package me.risky.jlike.activity;

import java.util.UUID;

import me.risky.jlike.R;
import me.risky.jlike.base.BaseNoTitleActivity;
import me.risky.jlike.util.Prefs_;
import me.risky.library.function.AsyncHttp;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.WindowFeature;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.apache.http.Header;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Window;

import com.loopj.android.http.AsyncHttpResponseHandler;

@WindowFeature(value = { Window.FEATURE_NO_TITLE })
@EActivity(R.layout.activity_boot)
public class BootActivity extends BaseNoTitleActivity{	
	
	private final static String TAG = BootActivity.class.getName();
	
	@Pref
    Prefs_ prefs;
	
	@AfterInject
	void afterInject(){
		logic();
//		sendMsg();
		delay();
	}
	
	@Background
	void delay(){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		toActivity();
	}
	
	@UiThread
	void toActivity(){
		toMainActivity();
		sendNotification();
		finish();
	}
	
	private void logic(){
		// First boot
		if(!prefs.UUID().exists()){
			String uuid = getUUID();
			prefs.UUID().put(uuid);
			Log.d(TAG, "uuid=" + uuid);
		}
	}
	
	// 打开应用发送统计信息
	private void sendMsg(){
		String url = "";
		AsyncHttp.get(url, new AsyncHttpResponseHandler(){

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable error) {
				Log.d(TAG, "error" + error.getMessage());
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				Log.d(TAG, "success");
			}
			
		});
	}
	
	private String getUUID() {
		final TelephonyManager tm = (TelephonyManager) getBaseContext()
				.getSystemService(Context.TELEPHONY_SERVICE);
		final String tmDevice, tmSerial, androidId;
		tmDevice = "" + tm.getDeviceId();
		tmSerial = "" + tm.getSimSerialNumber();
		androidId = ""
				+ android.provider.Settings.Secure.getString(
						getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);
		UUID deviceUuid = new UUID(androidId.hashCode(),
				((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
		String uniqueId = deviceUuid.toString();
		return uniqueId;
	}
	
	private void toMainActivity(){
		Intent i = new Intent();
		i.setClass(this, MainActivity_.class);
		startActivity(i);
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}
	
	private void sendNotification(){
		NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);               
		Notification n = new Notification(R.drawable.ic_launcher, "发现新版本", System.currentTimeMillis());             
		n.flags = Notification.FLAG_AUTO_CANCEL;                
		Intent i = new Intent(getApplicationContext(), AboutActivity_.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);           
		//PendingIntent
		PendingIntent contentIntent = PendingIntent.getActivity(
				getApplicationContext(), 
		        R.string.app_name, 
		        i, 
		        PendingIntent.FLAG_UPDATE_CURRENT);
		
		n.setLatestEventInfo(
				getApplicationContext(), 
		        "极客有新版本了", 
		        "速速来更新，点击查看详情", 
		        contentIntent);
		nm.notify(R.string.app_name, n);
		
//		Notification n = new Notification.Builder(getApplicationContext())
//		.setSmallIcon(R.drawable.ic_launcher).setContentTitle("发现新版本")
//		.setContentText("点击立即更新")
//		.setFullScreenIntent(contentIntent, true)
//		.build();
	}
}
