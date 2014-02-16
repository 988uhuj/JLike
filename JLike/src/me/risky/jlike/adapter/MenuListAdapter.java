package me.risky.jlike.adapter;

import java.util.ArrayList;
import java.util.List;

import me.risky.jlike.R;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;

public class MenuListAdapter extends BaseAdapter {
	private ViewHolder holder;
	private LayoutInflater layoutInflater;
	private Context context;
	private List<Object> list;
	
	private int clickPosition = -1;

	public MenuListAdapter(Context c) {
		super();
		layoutInflater = (LayoutInflater) LayoutInflater.from(c);
		list = new ArrayList<Object>();
	}

	public void setList(List<Object> list) {
		this.list = list;
	}

	public void addList(List<Object> list) {
		this.list.addAll(list);
	}

	public void clearList() {
		this.list.clear();
	}

	public List<Object> getList() {
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
		if (null == convertView) {
			convertView = layoutInflater.inflate(R.layout.list_item_menu, null);
			holder = new ViewHolder();
			holder.radioButton = (RadioButton) convertView.findViewById(R.id.radioBtn);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String item = (String) list.get(position); // ��ȡ��ǰ�����
		Log.d("Main", position+"position");
		final int tempPosition = position;
		if (null != item) {
			holder.radioButton.setText(item);
			holder.radioButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(isChecked){
                        clickPosition = tempPosition;
                        notifyDataSetChanged();
                        listener.onSelect(clickPosition);
                    }
				}
			});
			
			if(clickPosition == position){
				holder.radioButton.setChecked(true);
            }else{
            	holder.radioButton.setChecked(false);
            }
		}
		return convertView;
	}
	

	private class ViewHolder {
		RadioButton radioButton;
	}
	
	public interface OnMenuItemSelectListener{
		public void onSelect(int position);
	}
	
	private OnMenuItemSelectListener listener;
	
	public void setOnMenuItemSelectListener(OnMenuItemSelectListener listener){
		this.listener = listener;
	}
	
}