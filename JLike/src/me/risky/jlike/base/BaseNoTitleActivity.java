package me.risky.jlike.base;

import me.risky.jlike.R;
import android.content.Intent;

import com.actionbarsherlock.app.SherlockActivity;

public class BaseNoTitleActivity extends SherlockActivity{

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}
	
	public void startActivityAnim(Intent intent){
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	
}
