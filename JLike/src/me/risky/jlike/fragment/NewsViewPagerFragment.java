package me.risky.jlike.fragment;

import me.risky.jlike.R;
import me.risky.jlike.adapter.MainPagerAdapter;
import me.risky.jlike.base.AbsBaseItemController;
import me.risky.jlike.base.BaseFragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;


public class NewsViewPagerFragment extends BaseFragment{
	
	private ViewPager viewPager;
	private PagerSlidingTabStrip tabs;
	private MainPagerAdapter mainPagerAdapter;
	
	private OnPageChangeListener listener;
	private String[] titles;
	private String[] urls;
	private AbsBaseItemController[] services;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_viewpager_news, null);
	}

	@Override
	protected void init() {
		mainPagerAdapter = new MainPagerAdapter(getFragmentManager());
		mainPagerAdapter.setTitles(titles);
		mainPagerAdapter.setUrls(urls);
		mainPagerAdapter.setServices(services);
	}

	@Override
	protected void initComponent() {
		viewPager = (ViewPager) getView().findViewById(R.id.viewPager);
		tabs = (PagerSlidingTabStrip) getView().findViewById(R.id.tabs);
		
		viewPager.setAdapter(mainPagerAdapter);
//		tabs.setCurrentTextColorResource(R.color.tab_color);
		tabs.setTextColorResource(R.color.tab_color_uncheck);
		tabs.setUnderlineHeight((int) getResources().getDimension(R.dimen.sliding_underlineHeight));
		tabs.setIndicatorHeight((int) getResources().getDimension(R.dimen.sliding_indicatorHeight));
		tabs.setTextSize((int) getResources().getDimension(R.dimen.sliding_textSize));
		tabs.setIndicatorColor(getResources().getColor(R.color.tab_color));
		tabs.setTabPaddingLeftRight((int) getResources().getDimension(R.dimen.sliding_tabPaddingLeftRight));
		tabs.setDividerColor(getResources().getColor(R.color.transparent));
		tabs.setShouldExpand(false);		//�����򣬵�Ϊ������ǩҳʱ������Ϊtrue
		tabs.setViewPager(viewPager);
		tabs.setOnPageChangeListener(listener);
	}
	
	
	
	
	// -------------------
	
		
	public void setOnPageChangeListener(OnPageChangeListener listener){
		this.listener = listener;
	}
	
	public void setTitles(String[] titles){
		this.titles = titles;
	}
	
	public void setUrls(String[] urls){
		this.urls = urls;
	}
	
	public void setServices(AbsBaseItemController[] services){
		this.services = services;
	}
	
	public void notifyDataSetChanged(){
		mainPagerAdapter.notifyDataSetChanged();
		tabs.notifyDataSetChanged();
	}

}