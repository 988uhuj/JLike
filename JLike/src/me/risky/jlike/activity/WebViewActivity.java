package me.risky.jlike.activity;

import me.risky.jlike.R;
import me.risky.jlike.base.BaseActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import com.actionbarsherlock.view.MenuItem;

import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@EActivity(R.layout.activity_webview)
public class WebViewActivity extends BaseActivity{

	WebView webView;
	String baseUrl;
	
	
	@AfterViews
	void afterViews(){
		baseUrl = getIntent().getStringExtra("url");
		
		webView = (WebView) findViewById(R.id.webview);
		
		WebSettings webSettings = webView.getSettings();    
		webSettings.setJavaScriptEnabled(true);
		webView.getSettings().setLoadsImagesAutomatically(true);
		webView.setWebViewClient(new WebViewClient(){

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				
				view.loadUrl(url);    
				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				hideRefresh();
				super.onPageFinished(view, url);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				showRefresh();
				super.onPageStarted(view, url, favicon);
			}
			
		});
		webView.loadUrl(baseUrl);    
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
			webView.goBack(); // goBack()表示返回WebView的上一页面
			return true;
		}
		return false;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
