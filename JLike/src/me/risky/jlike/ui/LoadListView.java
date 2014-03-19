package me.risky.jlike.ui;

import me.risky.jlike.R;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class LoadListView extends PullToRefreshListView{
	private String TAG = "LoadListView";
	private boolean isLoading;
	private boolean enableAutoLoad;
	
	private OnLoadListener listener;
	
	private TextView footTextView;
	private ViewGroup mainFootLayout;
	private ProgressBar progressBar;
	
	private ListView listview;

	private int scrollPos;
	private int scrollTop;
	
	public LoadListView(Context context) {
		super(context);
		init(context);
		
	}
	public LoadListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context c){
		enableAutoLoad = true;
		listview = this.getRefreshableView();
		listview.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				Log.d(TAG, view.getLastVisiblePosition() + "position");
				// Loading data when last position is showing;
				if (view.getLastVisiblePosition() == view.getCount() - 1 && enableAutoLoad) {
					startLoad();
				}
				
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					scrollPos = listview.getFirstVisiblePosition();
				}
				
				Log.d(TAG, "onScrollStateChanged");
				View v = listview.getChildAt(0);
				scrollTop = (v == null) ? 0 : v.getTop();
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
			}
		});
		
		View footView = inflate(c, R.layout.refresh_list_foot, null);
		listview.addFooterView(footView);
		
		footTextView = (TextView) footView.findViewById(R.id.footTV);
		progressBar = (ProgressBar) footView.findViewById(R.id.progressBar);
		mainFootLayout = (ViewGroup) footView.findViewById(R.id.main);
		mainFootLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(enableAutoLoad){
					startLoad();
				}
			}
		});
	}
	
	private void stop() {
		isLoading = false;
		progressBar.setVisibility(View.GONE);
	}
	
	
	public interface OnLoadListener{
		public void onLoad();
	}
	
	
	public void setOnLoadListener(OnLoadListener listener){
		this.listener = listener;
	}
	
	
	public boolean IsLoading(){
		return isLoading;
	}
	
	public void startLoad(){
		if(isLoading){
			Log.d(TAG, "Loading");
			return;
		}
		isLoading = true;
		footTextView.setText(R.string.refresh_list_loading);
		progressBar.setVisibility(View.VISIBLE);
		// 延迟刷新
//		listener.onLoad();
		handler.sendEmptyMessageDelayed(0, 500);
	}
	
	public void stopLoad(){
		footTextView.setText("");
		stop();
	}
	
	public void stopLoad(int textRes){
		footTextView.setText(textRes);
		stop();
	}
	
	public void stopLoad(String text){
		footTextView.setText(text);
		stop();
	}
	
	public void setFootBg(int resId){
		mainFootLayout.setBackgroundResource(resId);
	}
	
	public ListView getListView(){
		return listview;
	}
	
	// 设置记录的位置
	public void setSelectionFromTop(){
		listview.setSelectionFromTop(scrollPos, scrollTop);
	}
	
	public void setEnableAutoLoad(boolean isEnableAutoLoad){
		this.enableAutoLoad = isEnableAutoLoad;
	}
	
	
	public Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			listener.onLoad();
			super.handleMessage(msg);
		}
		
	};
}
