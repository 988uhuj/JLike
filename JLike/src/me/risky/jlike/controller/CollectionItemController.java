package me.risky.jlike.controller;

import me.risky.jlike.R;
import me.risky.jlike.base.AbsBaseItemController;
import me.risky.jlike.base.ImageLoaderHelper;
import me.risky.jlike.base.OnItemClickListener;
import me.risky.jlike.base.OnItemLongClickListener;
import me.risky.jlike.db.Collection;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CollectionItemController extends AbsBaseItemController{
	private ViewHolder holder;
	private ImageLoaderHelper imageLoaderHelper;
	
	public CollectionItemController(Context context) {
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
			final Collection collection = (Collection) item;
			Log.d("Jlike", collection.getTitle());
			holder.title.setText(collection.getTitle());
			holder.content.setText(collection.getContent());
			holder.date.setText(collection.getDateTime());
			if(collection.getImageSrc() != null){
				imageLoaderHelper.display(collection.getImageSrc(), holder.newsImg);
				holder.newsImg.setVisibility(View.VISIBLE);
			}else{
				holder.newsImg.setVisibility(View.GONE);
			}
			final int p = position;
			holder.main.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(onClickListener != null){
						onClickListener.onItemClick(p, collection);
					}
				}
			});
			holder.main.setOnLongClickListener(new OnLongClickListener() {
				
				@Override
				public boolean onLongClick(View arg0) {
					onLongClickListener.onItemLongClick(p, collection);
					return false;
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
	
	private OnItemClickListener<Collection> onClickListener;
	private OnItemLongClickListener<Collection> onLongClickListener;
	
	public void setOnItemClickListener(OnItemClickListener<Collection> onClickListener){
		this.onClickListener = onClickListener;
	}
	
	public void setOnItemLongClickListener(OnItemLongClickListener<Collection> onLongClickListener){
		this.onLongClickListener = onLongClickListener;
	}
}
