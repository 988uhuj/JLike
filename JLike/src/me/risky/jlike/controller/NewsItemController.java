package me.risky.jlike.controller;

import me.risky.jlike.R;
import me.risky.jlike.base.AbsBaseItemController;
import me.risky.jlike.base.ImageLoaderHelper;
import me.risky.jlike.base.OnItemClickListener;
import me.risky.jlike.db.News;
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
			final News news = (News) item;
			Log.d("Jlike", news.getTitle());
			holder.title.setText(news.getTitle());
			holder.content.setText(news.getContent());
			holder.date.setText(news.getDateTime());
			if(news.getImageSrc() != null){
				imageLoaderHelper.display(news.getImageSrc(), holder.newsImg);
				holder.newsImg.setVisibility(View.VISIBLE);
			}else{
				holder.newsImg.setVisibility(View.GONE);
			}
			final int p = position;
			holder.main.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(onClickListener != null){
						onClickListener.onItemClick(p, news);
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
	
	private OnItemClickListener<News> onClickListener;
	
	public void setOnItemClickListener(OnItemClickListener<News> onClickListener){
		this.onClickListener = onClickListener;
	}
	
	
}
