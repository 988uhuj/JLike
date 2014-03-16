package me.risky.jlike.base;

import me.risky.jlike.R;
import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Window;

public class BaseActivity extends SherlockActivity{

	private ActionBar bar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		super.onCreate(savedInstanceState);
		bar = getSupportActionBar();
		bar.setDisplayHomeAsUpEnabled(true);
		hideRefresh();
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}
	
	public void startActivityAnim(Intent intent){
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	
	public void showRefresh(){
		setSupportProgressBarIndeterminateVisibility(true);	
	}
	
	public void hideRefresh(){
		setSupportProgressBarIndeterminateVisibility(false);	
	}
}
