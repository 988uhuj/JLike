package me.risky.jlike.fragment;

import java.util.List;

import me.risky.jlike.R;
import me.risky.jlike.activity.DetailActivity_;
import me.risky.jlike.adapter.NewsListAdapter;
import me.risky.jlike.base.AbsBaseItemController;
import me.risky.jlike.base.BaseFragment;
import me.risky.jlike.base.OnItemClickListener;
import me.risky.jlike.bean.Page;
import me.risky.jlike.bean.WelfareItem;
import me.risky.jlike.controller.NewsItemController;
import me.risky.jlike.dao.WelfareDao;
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
	
	NewsListAdapter<WelfareItem> adapter;
	@ViewById(R.id.listview)
	LoadListView loadListview;
	ListView listview;
	
	@Bean
	Page page;
	@Bean 
	WelfareDao welfareDao;
	
	
	@AfterViews
	void afterInject(){
		// annotations 不能注入T
		if(adapter == null){
			adapter = new NewsListAdapter<WelfareItem>(getActivity());
		}
		if(controller == null){
			controller = new NewsItemController(getActivity());
			controller.setOnItemClickListener(new OnItemClickListener<WelfareItem>() {
				
				@Override
				public void onItemClick(int position, WelfareItem item) {
					toDetailActivity(item.getDetailUrl());
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
				load();
			}
		});
        loadListview.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				Log.d(TAG, "refresh");
				page.resetPage();
				load();
			}

		});
        
        if(isLoad == false){
        	load();
        }else{
        	loadListview.setSelectionFromTop();
        }
	}
	
	private void load(){
		String url = UrlsUtil.getNewsListURL(baseUrl, page.getCurrentPage());
		Log.d(TAG, url);
		AsyncHttp.get(url, new AsyncHttpResponseHandler() {
		    @Override
		    public void onSuccess(String response) {
		    	Log.d(TAG, "get response");
		    	parseResponse(response);
		    }
		});
	}
	
	@Background
	void parseResponse(String response){
		List<WelfareItem> list = welfareDao.getWelfareList(response);
		if(list == null){
			loadError();
		}else{
			loadSuccess(list);
		}
	}
	
	@UiThread
	void loadSuccess(List<WelfareItem> list){
		Log.d(TAG, "loadSuccess");
		adapter.addList(list);
		adapter.notifyDataSetChanged();
		page.addPage();
		loadListview.onRefreshComplete();
		loadListview.stopLoad(R.string.refresh_list_load_end);
		isLoad = true;
	}
	
	@UiThread
	void loadError(){
		Log.d(TAG, "loadError");
		loadListview.stopLoad(R.string.refresh_list_load_error);
		loadListview.onRefreshComplete();
	}
	
	private void toDetailActivity(String url){
		Intent i = new Intent();
		i.setClass(getActivity(), DetailActivity_.class);
		i.putExtra("url", url);
		getActivity().startActivity(i);
		getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
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