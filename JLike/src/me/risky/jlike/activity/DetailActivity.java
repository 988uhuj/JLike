package me.risky.jlike.activity;

import me.risky.jlike.R;
import me.risky.jlike.base.BaseFragmentActivity;
import me.risky.jlike.fragment.NewsDetailFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.view.MenuItem;

@EActivity(R.layout.activity_detail)
public class DetailActivity extends BaseFragmentActivity{
	
	Fragment fragment;
	
	@AfterViews
	void afterInject(){
		fragment = new NewsDetailFragment_();
		
		String url = getIntent().getStringExtra("url");
		
		Bundle bundle = new Bundle();
		bundle.putString("url", url);
		fragment.setArguments(bundle);
		
		switchFragment(R.id.fragment, fragment);
	}
	
	
	private void switchFragment(int frameId, Fragment fragment) {
		
		getSupportFragmentManager()
		.beginTransaction()
		.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
		.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out)
		.replace(frameId, fragment)
		.commit();
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
