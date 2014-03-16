package me.risky.jlike.adapter;

import java.util.ArrayList;
import java.util.List;

import me.risky.jlike.base.AbsBaseItemController;

import org.androidannotations.annotations.EBean;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

@EBean
public class NewsListAdapter<T> extends BaseAdapter {
	
	private List<T> list;
	private AbsBaseItemController service;

	public NewsListAdapter(Context c) {
		super();
		list = new ArrayList<T>();
	}
	
	public void setService(AbsBaseItemController service){
		this.service = service;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public void addList(List<T> list) {
		this.list.addAll(list);
	}
	
	public void addList(List<T> list, boolean isClean) {
		if(isClean){
			this.list.clear();
		}
		this.list.addAll(list);
	}

	public void clearList() {
		this.list.clear();
	}

	public List<T> getList() {
		return list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return service.getView(position, convertView, parent, list.get(position));
	}

	@Override
	public int getItemViewType(int position) {
		return service.getItemViewType(position, list.get(position));
	}

	@Override
	public int getViewTypeCount() {
		return service.getViewTypeCount();
	}
	
	

}