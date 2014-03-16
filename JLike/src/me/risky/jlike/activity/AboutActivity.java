package me.risky.jlike.activity;

import me.risky.jlike.R;
import me.risky.jlike.base.BaseActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import com.actionbarsherlock.view.MenuItem;

import android.view.View;
import android.widget.Button;

@EActivity(R.layout.activity_about)
public class AboutActivity extends BaseActivity{
	
	@ViewById(R.id.updateBtn)
	Button updateBtn;
	
	@AfterViews
	void afterView(){
		setTitle("极客-关于");
		updateBtn.setVisibility(View.INVISIBLE);
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
