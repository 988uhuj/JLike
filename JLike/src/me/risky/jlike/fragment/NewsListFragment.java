package me.risky.jlike.fragment;

import java.util.List;

import me.risky.jlike.R;
import me.risky.jlike.activity.DetailActivity_;
import me.risky.jlike.activity.MainActivity;
import me.risky.jlike.adapter.NewsListAdapter;
import me.risky.jlike.base.AbsBaseItemController;
import me.risky.jlike.base.BaseApp;
import me.risky.jlike.base.BaseFragment;
import me.risky.jlike.base.OnItemClickListener;
import me.risky.jlike.bean.Page;
import me.risky.jlike.controller.NewsItemController;
import me.risky.jlike.db.News;
import me.risky.jlike.db.NewsDao;
import me.risky.jlike.service.WelfareService;
import me.risky.jlike.ui.LoadListView;
import me.risky.jlike.ui.LoadListView.OnLoadListener;
import me.risky.jlike.util.UrlsUtil;
import me.risky.library.function.AsyncHttp;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.apache.http.Header;

import android.content.Intent;
import android.util.Log;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.loopj.android.http.AsyncHttpResponseHandler;

@EFragment(R.layout.fragment_news_list)
public class NewsListFragment extends BaseFragment{
	
	private static String TAG = "NewsListFragment";
	
	private String baseUrl;
	private NewsItemController controller;
	private boolean isLoad;
	
	NewsListAdapter<News> adapter;
	@ViewById(R.id.listview)
	LoadListView loadListview;
	ListView listview;
	
	@Bean
	Page page;
	@Bean 
	WelfareService welfareDao;
	
	
	@AfterViews
	void afterInject(){
		// annotations 不能注入T
		if(adapter == null){
			adapter = new NewsListAdapter<News>(getActivity());
		}
		if(controller == null){
			controller = new NewsItemController(getActivity());
			controller.setOnItemClickListener(new OnItemClickListener<News>() {
				
				@Override
				public void onItemClick(int position, News item) {
					toDetailActivity(item.getDetailUrl(), item);
				}
			});
		}
		adapter.setService(controller);
		 
		listview = loadListview.getListView();
        listview.setAdapter(adapter);
        loadListview.setFootBg(R.drawable.list_item_bg);
        loadListview.setOnLoadListener(new OnLoadListener() {
			
			@Override
			public void onLoad() {
				Log.d("load", "load");
				load(false);
			}
		});
        loadListview.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				Log.d(TAG, "refresh");
				page.resetPage();
				load(true);
			}

		});
        
        if(isLoad == false){
        	loadFromDB();
        }else{
        	loadListview.setSelectionFromTop();
        }
	}
	
	@Background
	void loadFromDB(){
		int type = UrlsUtil.getNewsType(baseUrl);
		Log.d(TAG, "load from db type = " + type);
		List<News> list = BaseApp.getDaoSession(getActivity()).getNewsDao().queryBuilder().where((NewsDao.Properties.Type.eq(type))).list();
		Log.d(TAG, "load from db size = " + list.size());
		loadFromDBFinish(list);
	}
	
	@UiThread
	void loadFromDBFinish(List<News> list){
		adapter.addList(list, true);
		adapter.notifyDataSetChanged();
		load(true);
	}
	
	private void load(final boolean isClean){
		String url = UrlsUtil.getNewsListURL(baseUrl, page.getCurrentPage());
		Log.d(TAG, url);
		AsyncHttp.get(url, new AsyncHttpResponseHandler() {
		    @Override
		    public void onSuccess(String response) {
		    	Log.d(TAG, "get response");
		    	parseResponse(response, isClean);
		    }

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable throwable) {
				Log.d(TAG, "Failure" + throwable.getMessage());
				loadFinish();
			}
			
		});
	}
	
	@Background
	void parseResponse(String response, boolean isClean){
		int type = UrlsUtil.getNewsType(baseUrl);
		List<News> list = welfareDao.getWelfareList(response, type);
		if(list == null){
			loadError();
		}else{
			if(isClean){
				// new data
				BaseApp.getDaoSession(getActivity()).getNewsDao().deleteNewsByType(type);
				BaseApp.getDaoSession(getActivity()).getNewsDao().insertInTx(list);
				Log.d(TAG, "save news list size = :" + list.size());
			}
			loadSuccess(list, isClean);	
		}
	}
	
	@UiThread
	void loadSuccess(List<News> list, boolean isClean){
		Log.d(TAG, "loadSuccess");
		if(list.size() == 0){
			Log.d(TAG, "list size = 0");
		}
		adapter.addList(list, isClean);
		adapter.notifyDataSetChanged();
		page.addPage();
		loadFinish();
		isLoad = true;
	}
	
	@UiThread
	void loadError(){
		Log.d(TAG, "loadError");
		loadFinish();
	}
	
	void loadFinish(){
		loadListview.stopLoad(R.string.refresh_list_load_end);
		loadListview.onRefreshComplete();
	}
	
	private void toDetailActivity(String url, News news){
		Intent i = new Intent();
		i.setClass(getActivity(), DetailActivity_.class);
		i.putExtra("url", url);
		i.putExtra("news", news);
		((MainActivity)getActivity()).startActivityAnim(i);
	}
	
	//--------------------
	
	public void setUrl(String url){
		this.baseUrl = url;
	}
	
	public void setService(AbsBaseItemController service){
		// FIXME
//		this.controller = service;
	}
	
}