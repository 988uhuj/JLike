package me.risky.jlike.base;

import me.risky.jlike.R;
import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class BaseFragmentActivity extends SherlockFragmentActivity{

	private ActionBar bar;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		bar = getSupportActionBar();
		bar.setTitle("OSChina");
		bar.setDisplayHomeAsUpEnabled(true);
	}

	

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}
	
	
	public void setTitle(String title){
		bar.setTitle(title);
	}
	
	public void setDisplayHomeAsUpEnabled(boolean display){
		bar.setDisplayHomeAsUpEnabled(display);
	}
	
	public void startActivityAnim(Intent intent){
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	
}
