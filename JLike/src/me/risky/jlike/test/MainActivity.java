package me.risky.jlike.test;

import me.risky.jlike.R;

import org.androidannotations.annotations.EActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

@EActivity
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		String url = "http://zhaofuli.net/category/songfuli";
//		AsyncHttp.get(url, new AsyncHttpResponseHandler() {
//		    @Override
//		    public void onSuccess(String response) {
//		    	WelfareDao welfareHtml = new WelfareDao();
//		    	Log.d("JLike", welfareHtml.getWelfareList(response).toString());
//		    }
//		});
		
//		String url2 = "http://zhaofuli.net/3815.html";
//		AsyncHttp.get(url2, new AsyncHttpResponseHandler() {
//		    @Override
//		    public void onSuccess(String response) {
//		    	WelfareDao welfareHtml = new WelfareDao();
//		    	Log.d("JLike", welfareHtml.getWelfareDetail(response).toString());
//		    }
//		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
