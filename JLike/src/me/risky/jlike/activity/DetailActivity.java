package me.risky.jlike.activity;

import java.util.Date;
import java.util.List;

import me.risky.jlike.R;
import me.risky.jlike.base.BaseApp;
import me.risky.jlike.base.BaseFragmentActivity;
import me.risky.jlike.db.Collection;
import me.risky.jlike.db.CollectionDao;
import me.risky.jlike.db.News;
import me.risky.jlike.fragment.NewsDetailFragment_;
import me.risky.jlike.fragment.OnFragRefreshListener;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.apache.http.client.utils.DateUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

@EActivity(R.layout.activity_detail)
public class DetailActivity extends BaseFragmentActivity{
	
	private final static String TAG = "DetailActivity";
	
	NewsDetailFragment_ fragment;
	
	private News news; 
	private boolean hasCollected; 
	
	@AfterViews
	void afterView(){
		setTitle("极客-详情");
		
		hasCollected = true;
		fragment = new NewsDetailFragment_();
		
		String url = getIntent().getStringExtra("url");
		Object object = getIntent().getSerializableExtra("news"); 
		if(object != null){
			news = (News) object;
			checkHasCollected(news);
		}else{
			// from collection 
		}
		
		Bundle bundle = new Bundle();
		bundle.putString("url", url);
		bundle.putSerializable("news", news);
		fragment.setArguments(bundle);
		fragment.setOnFragRefreshListener(new OnFragRefreshListener() {
			
			@Override
			public void onStart() {
				showRefresh();
			}
			
			@Override
			public void onFinish() {
				hideRefresh();
			}
		});
		
		switchFragment(R.id.fragment, fragment);
		showRefresh();
	}
	
	@Background
	void checkHasCollected(News news){
		List<Collection> list = BaseApp.getDaoSession(this)
			.getCollectionDao()
			.queryBuilder()
			.where(CollectionDao.Properties.DetailUrl.eq(news.getDetailUrl())).list();
		Log.d(TAG, "check has collected : " + list.size());
		if(list.size() > 0){
			hasCollected = true;
		}else{
			hasCollected = false;
		}
		checkHasCollectedFinish();
	}
	
	@UiThread
	void checkHasCollectedFinish(){
		// refresh the action menu
		supportInvalidateOptionsMenu();
	}
	
	private void switchFragment(int frameId, Fragment fragment) {
		
		getSupportFragmentManager()
		.beginTransaction()
		.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
		.setCustomAnimations(R.anim.push_up_in, R.anim.push_up_out)
		.replace(frameId, fragment)
		.commit();
	}
	
	
	@Background
	void saveCollect(){
		// 保存收藏
		Collection collection = new Collection();
		collection.setTitle(news.getTitle());
		collection.setContent(news.getContent());
		collection.setImageSrc(news.getImageSrc());
		collection.setDateTime(news.getDateTime());
		collection.setDetailUrl(news.getDetailUrl());
		collection.setType(news.getType());
		collection.setScanNum(news.getScanNum());
		collection.setCreateTime(DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
		Log.d(TAG, collection.getCreateTime());
		BaseApp.getDaoSession(this).getCollectionDao().insert(collection);
		saveCollectFinish();
	}
	
	@UiThread
	void saveCollectFinish(){
		Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
		hideRefresh();
		supportInvalidateOptionsMenu();
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.action_star:
			Log.d(TAG, "action bar collect");
			if(!hasCollected){
				hasCollected = true;
				showRefresh();
				saveCollect();
			}else{
				Toast.makeText(getApplicationContext(), "该内容已收藏", Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if(hasCollected){
			MenuInflater inflater = getSupportMenuInflater();  
	        inflater.inflate(R.menu.detail, menu);  
		}else{
			MenuInflater inflater = getSupportMenuInflater();  
	        inflater.inflate(R.menu.detail_no, menu);  
		}
        return true;  
	}
}
