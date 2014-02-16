package me.risky.jlike.base;

import me.risky.jlike.bean.WelfareItem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class AbsBaseItemController {
	protected LayoutInflater layoutInflater;
	protected Context context;
	
	private int viewTypeCount;
	
	public AbsBaseItemController(Context context){
		this.context = context;
		layoutInflater = (LayoutInflater) LayoutInflater.from(context);
		viewTypeCount = 1;
	}
	
	public abstract View getView(int position, View convertView, ViewGroup parent,Object item);
	
	public int getItemViewType(int position, Object item) {
		return 0;
	}
	
	public int getViewTypeCount() {
		return viewTypeCount;
	}
	
	
}
