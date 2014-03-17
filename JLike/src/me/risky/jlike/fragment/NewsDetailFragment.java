package me.risky.jlike.fragment;

import java.util.List;

import me.risky.jlike.R;
import me.risky.jlike.activity.ImageActivity_;
import me.risky.jlike.activity.WebViewActivity;
import me.risky.jlike.activity.WebViewActivity_;
import me.risky.jlike.adapter.NewsListAdapter;
import me.risky.jlike.base.BaseFragment;
import me.risky.jlike.base.BaseFragmentActivity;
import me.risky.jlike.base.OnItemClickListener;
import me.risky.jlike.bean.MyTagHandler.OnLinkClickListener;
import me.risky.jlike.bean.WelfareDetail;
import me.risky.jlike.controller.NewsDetailController;
import me.risky.jlike.service.WelfareService;
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
	WelfareService welfareDao;
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
			
			controller.setOnLinkClickListener(new OnLinkClickListener() {
				
				@Override
				public void onClick(View v, String url) {
					toWebViewActivity(url);
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

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable throwable) {
				Log.d(TAG, "http error : " + throwable.getMessage());
				loadFinish();
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
	
	private void toWebViewActivity(String url){
		Intent i = new Intent();
		i.setClass(getActivity(), WebViewActivity_.class);
		i.putExtra("url", url);
		((BaseFragmentActivity)getActivity()).startActivityAnim(i);
	}
	
	//----------------
	
	private OnFragRefreshListener onFragRefreshListener;
	
	public void setOnFragRefreshListener(OnFragRefreshListener listener){
		this.onFragRefreshListener = listener;
	}
	
	
	
}	
