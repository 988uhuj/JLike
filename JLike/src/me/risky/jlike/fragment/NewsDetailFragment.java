package me.risky.jlike.fragment;

import java.util.List;

import me.risky.jlike.R;
import me.risky.jlike.activity.ImageActivity_;
import me.risky.jlike.adapter.NewsListAdapter;
import me.risky.jlike.base.BaseFragment;
import me.risky.jlike.base.OnItemClickListener;
import me.risky.jlike.bean.WelfareDetail;
import me.risky.jlike.controller.NewsDetailController;
import me.risky.jlike.dao.WelfareDao;
import me.risky.library.function.AsyncHttp;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.loopj.android.http.AsyncHttpResponseHandler;

@EFragment(R.layout.fragment_news_detail)
public class NewsDetailFragment extends BaseFragment{
	
	private static String TAG = "NewsDetailFragment";
	private String url;

	@ViewById(R.id.listview)
	ListView listview;
	NewsListAdapter<WelfareDetail> adapter;
	@ViewById(R.id.progressBar)
	ProgressBar progressBar;
	@ViewById(R.id.reLoadImage)
	ImageView reLoadImage;
	
	@Bean 
	WelfareDao welfareDao;
//	@Bean
	NewsDetailController controller;
	

	@AfterViews
	void afterInject(){
		url = getArguments().getString("url");
		if(adapter == null){
			adapter = new NewsListAdapter<WelfareDetail>(getActivity());
		}
		if(controller == null){
			controller = new NewsDetailController(getActivity());
			controller.setOnItemClickListener(new OnItemClickListener<WelfareDetail>() {

				@Override
				public void onItemClick(int position, WelfareDetail item) {
					toImageActvity(item.getImgSrc());
				}
			});
		}
		
		
		adapter.setService(controller);
		listview.setAdapter(adapter);
		
		load();
	}
	
	private void load(){
		Log.d(TAG, url);
		onFragRefreshListener.onStart();
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
		List<WelfareDetail> list = welfareDao.getWelfareDetail(response);
		if(list == null){
			loadError();
		}else{
			loadSuccess(list);
		}
		loadFinish();
		
	}
	
	@UiThread
	void loadFinish(){
		progressBar.setVisibility(View.GONE);
		onFragRefreshListener.onFinish();
	}
	
	@UiThread
	void loadSuccess(List<WelfareDetail> list){
		Log.d(TAG, "loadSuccess");
		adapter.addList(list);
		adapter.notifyDataSetChanged();
	}
	
	@UiThread
	void loadError(){
		Log.d(TAG, "loadError");
		reLoadImage.setVisibility(View.VISIBLE);
	}
	
	private void toImageActvity(String url){
		Intent i = new Intent();
		i.setClass(getActivity(), ImageActivity_.class);
		i.putExtra("url", url);
		i.putExtra("title", adapter.getList().get(0).getTitle());
		getActivity().startActivity(i);
		getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_no);
	}
	
	//----------------
	
	private OnFragRefreshListener onFragRefreshListener;
	
	public void setOnFragRefreshListener(OnFragRefreshListener listener){
		this.onFragRefreshListener = listener;
	}
	
	
	
}	
