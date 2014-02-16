package me.risky.jlike.controller;

import me.risky.jlike.R;
import me.risky.jlike.base.AbsBaseItemController;
import me.risky.jlike.base.ImageLoaderHelper;
import me.risky.jlike.base.OnItemClickListener;
import me.risky.jlike.bean.WelfareItem;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsItemController extends AbsBaseItemController{

	private ViewHolder holder;
	private ImageLoaderHelper imageLoaderHelper;
	
	public NewsItemController(Context context) {
		super(context);
		imageLoaderHelper = new ImageLoaderHelper(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent, Object item) {
		if (null == convertView) {
			convertView = layoutInflater.inflate(R.layout.list_item_news, null);
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.content = (TextView) convertView.findViewById(R.id.content);
			holder.date = (TextView) convertView.findViewById(R.id.date);
			holder.newsImg = (ImageView) convertView.findViewById(R.id.newsImg);
			holder.main = (ViewGroup) convertView.findViewById(R.id.main);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (null != item) {
			final WelfareItem welfareItem = (WelfareItem) item;
			Log.d("Jlike", welfareItem.getTitle());
			holder.title.setText(welfareItem.getTitle());
			holder.content.setText(welfareItem.getContent());
//			holder.date.setText(newsItem.getDate());
			if(welfareItem.getImgSrc() != null){
				imageLoaderHelper.display(welfareItem.getImgSrc(), holder.newsImg);
				holder.newsImg.setVisibility(View.VISIBLE);
			}else{
				holder.newsImg.setVisibility(View.GONE);
			}
			final int p = position;
			holder.main.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(onClickListener != null){
						onClickListener.onItemClick(p, welfareItem);
					}
				}
			});
		}
		return convertView;
	}
	
	
	
	private class ViewHolder {
		ViewGroup main;
		TextView title;
		TextView content;
		TextView date;
		ImageView newsImg;
	}
	
	private OnItemClickListener<WelfareItem> onClickListener;
	
	public void setOnItemClickListener(OnItemClickListener<WelfareItem> onClickListener){
		this.onClickListener = onClickListener;
	}
	
	
}
