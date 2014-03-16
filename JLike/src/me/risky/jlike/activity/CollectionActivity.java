package me.risky.jlike.activity;

import java.util.List;

import me.risky.jlike.R;
import me.risky.jlike.adapter.NewsListAdapter;
import me.risky.jlike.base.BaseActivity;
import me.risky.jlike.base.BaseApp;
import me.risky.jlike.base.OnItemClickListener;
import me.risky.jlike.base.OnItemLongClickListener;
import me.risky.jlike.controller.CollectionItemController;
import me.risky.jlike.db.Collection;
import me.risky.jlike.ui.LoadListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

@EActivity(R.layout.activity_collection)
public class CollectionActivity extends BaseActivity{
	
	private final static String TAG = "CollectionActivity";
	
	NewsListAdapter<Collection> adapter;
	
	private CollectionItemController controller;
	
	@ViewById(R.id.listview)
	LoadListView loadListview;
	ListView listview;
	
	@AfterViews
	void afterView(){
		setTitle("极客-收藏");
		if(adapter == null){
			adapter = new NewsListAdapter<Collection>(this);
		}
		if(controller == null){
			controller = new CollectionItemController(this);
			controller.setOnItemClickListener(new OnItemClickListener<Collection>() {
				
				@Override
				public void onItemClick(int position, Collection item) {
					toDetailActivity(item.getDetailUrl());
				}
			});
			controller.setOnItemLongClickListener(new OnItemLongClickListener<Collection>() {
				
				@Override
				public void onItemLongClick(int position, Collection item) {
					showCleanDialog(item);
				}
			});
		}
		adapter.setService(controller);
		 
		listview = loadListview.getListView();
        listview.setAdapter(adapter);
        loadListview.setFootBg(R.drawable.list_item_bg);
        loadListview.setEnableAutoLoad(false);
        loadListview.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				Log.d(TAG, "refresh");
				showRefresh();
				loadFromDB();
			}

		});
        
        showRefresh();
        loadFromDB();
	}
	
	@Background
	void loadFromDB(){
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List<Collection> list = BaseApp.getDaoSession(this).getCollectionDao().loadAll();
		Log.d(TAG, "loadFromDB list size : " + list.size());
		adapter.setList(list);
		loadFromDBFinish();
	}
	
	@UiThread
	void loadFromDBFinish(){
		adapter.notifyDataSetChanged();
		loadListview.stopLoad(R.string.refresh_list_load_end);
		loadListview.onRefreshComplete();
		hideRefresh();
		Log.d(TAG, "loadFromDBFinish");
	}
	
	
	
	private void toDetailActivity(String url){
		Intent i = new Intent();
		i.setClass(this, DetailActivity_.class);
		i.putExtra("url", url);
		startActivityAnim(i);
	}
	
	public void showCleanDialog(final Collection collection){
		new AlertDialog.Builder(this)
//		.setTitle("提示")
		.setMessage("确认删除" + collection.getTitle() + "\n")
		.setPositiveButton("确认", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				showRefresh();
				cleanCollection(collection);
			}
		})
		.setNegativeButton("取消", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		})
		.create()
		.show();
	}
	
	@Background
	void cleanCollection(Collection collection){
		BaseApp.getDaoSession(this).getCollectionDao().delete(collection);
		Log.d(TAG, "clean collection ok");
		cleanCollectionFinish();
	}
	
	
	public void showCleanAllDialog(){
		new AlertDialog.Builder(this)
//		.setTitle("提示")
		.setMessage("确认删除全部收藏？\n")
		.setPositiveButton("确认", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				showRefresh();
				cleanAllCollection();
			}
		})
		.setNegativeButton("取消", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		})
		.create()
		.show();
	}
	
	
	
	@Background
	void cleanAllCollection(){
		BaseApp.getDaoSession(this).getCollectionDao().deleteAll();
		Log.d(TAG, "clean all collection");
		cleanCollectionFinish();
	}
	
	@UiThread
	void cleanCollectionFinish(){
		loadFromDB();
		Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
		hideRefresh();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.action_clean:
			showCleanAllDialog();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();  
        inflater.inflate(R.menu.collection, menu);  
        return true;  
	}
}
