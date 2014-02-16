package me.risky.jlike.base;

import me.risky.jlike.R;
import android.app.Activity;
import android.content.Intent;

public class BaseActivity extends Activity{

	
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
