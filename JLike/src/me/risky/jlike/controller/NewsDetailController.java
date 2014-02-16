package me.risky.jlike.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import me.risky.jlike.R;
import me.risky.jlike.base.AbsBaseItemController;
import me.risky.jlike.base.ImageLoaderHelper;
import me.risky.jlike.base.OnItemClickListener;
import me.risky.jlike.bean.MyTagHandler;
import me.risky.jlike.bean.WelfareDetail;
import me.risky.jlike.util.Constants;
import me.risky.library.function.AsyncHttp;
import me.risky.library.function.HttpService;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.http.BinaryHttpResponseHandler;

@EBean
public class NewsDetailController extends AbsBaseItemController{

	private ViewHolder holder;
	private ImageLoaderHelper imageLoaderHelper;
	
	public NewsDetailController(Context context) {
		super(context);
		imageLoaderHelper = new ImageLoaderHelper(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent, Object item) {
		final WelfareDetail detail = (WelfareDetail) item;
		Log.d("Controller", position+"position");
		if (null == convertView) {
			holder = new ViewHolder();
			switch(detail.getType()){
			case Constants.DEF_WELFARE_DETAIL.TITLE:
				convertView = layoutInflater.inflate(R.layout.list_item_detail_head, null);
				holder.textView = (TextView) convertView.findViewById(R.id.title);
				break;
			case Constants.DEF_WELFARE_DETAIL.CONTENT:
				convertView = layoutInflater.inflate(R.layout.list_item_detail_content, null);
				holder.textView = (TextView) convertView.findViewById(R.id.textView);
				break;
			case Constants.DEF_WELFARE_DETAIL.IMG:
				convertView = layoutInflater.inflate(R.layout.list_item_detail_img, null);
				holder.img = (GifImageView) convertView.findViewById(R.id.imageView);
				break;
			case Constants.DEF_WELFARE_DETAIL.TAG:
				convertView = layoutInflater.inflate(R.layout.list_item_detail_tag, null);
				holder.textView = (TextView) convertView.findViewById(R.id.textView);
				break;
			default:
				break;
			}
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (null != item) {
			final int p = position;
			switch(detail.getType()){
			case Constants.DEF_WELFARE_DETAIL.IMG:
				Log.d("display", detail.getImgSrc());
//				imageLoaderHelper.display(detail.getImgSrc(), holder.img);
//				Drawable drawable = context.getResources().getDrawable(R.drawable.test);
//				holder.img.setBackgroundResource(R.drawable.test);
//				holder.img.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View arg0) {
//						onClickListener.onItemClick(p, detail);
//					}
//				});
				
//				AsyncHttp.get(detail.getImgSrc(), new BinaryHttpResponseHandler() {
//
//					@Override
//					public void onSuccess(byte[] binaryData) {
//						Log.d("img","load1");
//				        try {
//				        	GifDrawable gifFromBytes = new GifDrawable( binaryData );
//							Log.d("img","load");
//							holder.img.setImageDrawable(gifFromBytes);
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//					}
//					
//				});
				
				load(detail.getImgSrc());
				break;
			case Constants.DEF_WELFARE_DETAIL.TITLE:
				holder.textView.setText(Html.fromHtml(detail.getTitle(), null, new MyTagHandler()));
				break;
			case Constants.DEF_WELFARE_DETAIL.CONTENT:
				holder.textView.setText(Html.fromHtml(detail.getContent(), null, new MyTagHandler()));
				break;
			case Constants.DEF_WELFARE_DETAIL.TAG:
				holder.textView.setText(Html.fromHtml(detail.getTag(), null, new MyTagHandler()));
				break;
			default:
				break;
			}
		}
		return convertView;
	}
	
	@Background
	void load(String url){
		InputStream is = HttpService.HttpGetBmpInputStream(url);
		BufferedInputStream bis = new BufferedInputStream( is );
        try {
			GifDrawable gifFromStream = new GifDrawable( bis );
			Log.d("img","load");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public int getItemViewType(int position, Object item) {
		int result = 0;
		WelfareDetail detail = (WelfareDetail) item;
		switch(detail.getType()){
		case Constants.DEF_WELFARE_DETAIL.TITLE:
			result = 0;
			break;
		case Constants.DEF_WELFARE_DETAIL.CONTENT:
			result = 1;
			break;
		case Constants.DEF_WELFARE_DETAIL.IMG:
			result = 2;
			break;
		case Constants.DEF_WELFARE_DETAIL.TAG:
			result = 3;
			break;
		default:
			break;
		}
		return result;
	}

	@Override
	public int getViewTypeCount() {
		return 4;
	}




	private class ViewHolder {
		 TextView textView;
	     GifImageView img;
	}
	
	
	private OnItemClickListener<WelfareDetail> onClickListener;
	
	public void setOnItemClickListener(OnItemClickListener<WelfareDetail> onClickListener){
		this.onClickListener = onClickListener;
	}

}
