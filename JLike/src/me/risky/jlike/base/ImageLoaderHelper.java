package me.risky.jlike.base;

import me.risky.jlike.R;
import android.content.Context;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class ImageLoaderHelper {
	
	private ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;
	
	public ImageLoaderHelper(Context context){
		imageLoader.init(ImageLoaderConfiguration.createDefault(context));
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.default_article_img)
				.showImageForEmptyUri(R.drawable.default_article_img)
				.showImageOnFail(R.drawable.default_article_img).cacheInMemory(true).cacheOnDisc(true)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.displayer(new RoundedBitmapDisplayer(20))
				.displayer(new FadeInBitmapDisplayer(300)).build();
	}
	
	public void display(String url, ImageView imageView){
		imageLoader.displayImage(url, imageView, options);
	}
	
	public void load(String url, ImageLoadingListener listener){
		imageLoader.loadImage(url, listener);
	}
}
