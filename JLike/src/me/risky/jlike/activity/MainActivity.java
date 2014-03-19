package me.risky.jlike.activity;

import java.util.Arrays;

import me.risky.jlike.R;
import me.risky.jlike.base.BaseFragmentActivity;
import me.risky.jlike.controller.NewsItemController;
import me.risky.jlike.fragment.NewsViewPagerFragment;
import me.risky.jlike.util.UrlsUtil;

import org.androidannotations.annotations.EActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

@EActivity
public class MainActivity extends BaseFragmentActivity{

	private final static String TAG = "MainActivity";
	
//	private SlidingMenu menu;
	
	private NewsViewPagerFragment contentFragment;
//	private MenuListFragment menuFragment;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MobclickAgent.onEvent(this, "onMain");
		setContentView(R.layout.activity_main);
		setDisplayHomeAsUpEnabled(false);
		
		findView();
		initData();
		initComponent();
		
		action();
		
		// 友盟自动更新
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.update(getApplicationContext());
	}
	
	
	
	private void findView(){
	}
	
	private void initData(){
        // configure the SlidingMenu
//        menu = new SlidingMenu(this);
        
        contentFragment = new NewsViewPagerFragment();
//        menuFragment = new MenuListFragment();
	}
	
	private void initComponent(){
//		menu.setMode(SlidingMenu.LEFT);
//		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
//		menu.setShadowWidthRes(R.dimen.shadow_width);
//		menu.setShadowDrawable(R.drawable.shadow);
//		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
//		menu.setFadeEnabled(false);
//		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
//		menu.setMenu(R.layout.sliding_menu);
//		menu.setBehindScrollScale(0);	//
//		menu.setSelectorEnabled(false);
//		
//		
//		contentFragment.setOnPageChangeListener(new OnPageChangeListener() {
//			
//			@Override
//			public void onPageSelected(int position) {
//				if(position == 0){
//					menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
//				}else{
//					menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
//				}
//			}
//			
//			@Override
//			public void onPageScrolled(int arg0, float arg1, int arg2) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onPageScrollStateChanged(int arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
		
		
		
		//----menu-----
//		menuFragment.setOnMenuItemSelectListener(new OnMenuItemSelectListener() {
//
//			@Override
//			public void onSelect(int position) {
//				Log.d("MainActivity", "click = " + position);
//				switch(position){
//				case 0:
//					switchFragment(R.id.mainFrame, contentFragment);
//					initNewsFragments();
//					contentFragment.notifyDataSetChanged();
//					break;
//				case 1:
////					switchFragment(R.id.mainFrame, contentFragment);
////					String[] titles2 = getResources().getStringArray(R.array.titles2);
////					contentFragment.setTitles(titles2);
////					AbsBaseItemService service2 = new NewsItemService(getApplicationContext());
////					AbsBaseItemService[] services2 = new AbsBaseItemService[titles2.length];
////					Arrays.fill(services2, service2);
////					contentFragment.setServices(services2);
////					contentFragment.notifyDataSetChanged();
//					break;
//				case 2:
////					switchFragment(R.id.mainFrame, new TestFragment());
//					break;
//				default:
//					break;
//				}
//				
//			}
//			
//		});
		
	}
	
	private void initNewsFragments(){
		String[] titles = getResources().getStringArray(R.array.titles);
		NewsItemController service = new NewsItemController(getApplicationContext());
		NewsItemController[] services = new NewsItemController[titles.length];
		Arrays.fill(services, service);
		contentFragment.setTitles(titles);
		contentFragment.setUrls(UrlsUtil.getNewsUrlList());
		contentFragment.setServices(services);
	}
	
	
	private void action(){
		initNewsFragments();
		
		// Set Menu Fragment
//		switchFragment(R.id.menuLayout, menuFragment);
		switchFragment(R.id.mainFrame, contentFragment);
		
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
//			menu.toggle();
			break;
		case R.id.action_star:
			Log.d(TAG, "action bar star");
			startActivityAnim(new Intent(this, CollectionActivity_.class));
			break;
		case R.id.action_about:
			Log.d(TAG, "action bar about");
			startActivityAnim(new Intent(this, AboutActivity_.class));
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();  
        inflater.inflate(R.menu.main, menu);  
        return true;  
	}
	
	
	private void switchFragment(Fragment fragment) {
		
		getSupportFragmentManager()
		.beginTransaction()
		.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
		.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out)
		.replace(R.id.menuLayout, fragment)
		.commit();
	}
	private void switchFragment(int frameId, Fragment fragment) {
		
		getSupportFragmentManager()
		.beginTransaction()
		.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
		.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out)
		.replace(frameId, fragment)
		.commit();
	}

}
