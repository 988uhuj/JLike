/**
 * 
 */
package me.risky.jlike.adapter;

import me.risky.jlike.base.AbsBaseItemController;
import me.risky.jlike.fragment.NewsListFragment;
import me.risky.jlike.fragment.NewsListFragment_;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

/**
 * @author chenupt@gmail.com
 *
 * @Description:TODO
 *
 *
 */
public class MainPagerAdapter extends FragmentPagerAdapter{
	
	public static String TAG = "MainPagerAdapter";
	
	private String[] titles;
	private String[] urls;
	private AbsBaseItemController[] services;
	
	public MainPagerAdapter(FragmentManager fm) {
		super(fm);
	}
	
	@Override
	public Fragment getItem(int position) {
		NewsListFragment fragment = new NewsListFragment_();
		fragment.setUrl(urls[position]);
		fragment.setService(services[position]);
		return fragment;
	}
	
	@Override
	public int getCount() {
		return titles.length;
	}


	@Override
	public CharSequence getPageTitle(int position) {
		return titles[position];
	}
	
	@Override
	public int getItemPosition(Object object) {
		return PagerAdapter.POSITION_NONE;
	}
	
	//------------------
	
	

	public void setTitles(String[] titles){
		this.titles = titles;
	}
	
	public void setUrls(String[] urls){
		this.urls = urls;
	}

	public void setServices(AbsBaseItemController[] services){
		this.services = services;
	}
	
	
	

}
