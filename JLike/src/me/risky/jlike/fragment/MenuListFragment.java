package me.risky.jlike.fragment;

import java.util.ArrayList;
import java.util.List;

import me.risky.jlike.R;
import me.risky.jlike.adapter.MenuListAdapter;
import me.risky.jlike.adapter.MenuListAdapter.OnMenuItemSelectListener;
import me.risky.jlike.base.BaseFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MenuListFragment extends BaseFragment{

	private List<Object> testData;
	private MenuListAdapter adapter;
	
	private ListView listview;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_menu_list, null);
	}

	@Override
	protected void init() {
		adapter = new MenuListAdapter(getActivity());
		testData = new ArrayList<Object>();
		for(int i = 0; i < 4; i ++){
			testData.add("item" + i);
		}
		adapter.setList(testData);
	}

	@Override
	protected void initComponent() {
		listview = (ListView) getView().findViewById(R.id.listview);
		listview.setAdapter(adapter);
		adapter.setOnMenuItemSelectListener(listener);
	}
	
	private OnMenuItemSelectListener listener;
	public void setOnMenuItemSelectListener(OnMenuItemSelectListener listener){
		this.listener = listener;
	}

}
