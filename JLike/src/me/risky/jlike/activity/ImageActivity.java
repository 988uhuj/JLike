package me.risky.jlike.activity;

import java.io.IOException;

import me.risky.jlike.R;
import me.risky.jlike.base.BaseActivity;
import me.risky.library.base.ImageUtil;
import me.risky.library.function.AsyncHttp;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;
import org.apache.http.Header;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import uk.co.senab.photoview.PhotoViewAttacher;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.BinaryHttpResponseHandler;

@WindowFeature(value = { Window.FEATURE_NO_TITLE })
@EActivity(R.layout.activity_image_page)
public class ImageActivity extends BaseActivity {
	
	private final static String TAG = "ImageActivity";

	private String URL;
	private String title;
	
	@ViewById(R.id.gifImage)
	GifImageView gifImageView;
	@ViewById(R.id.image)
	ImageView imageView;
	@ViewById(R.id.loading)
	ProgressBar progressBar;
	
	@ViewById(R.id.back)
	ImageView backBtn;
	@ViewById(R.id.download)
	ImageView downLoadBtn;
	@ViewById(R.id.type)
	TextView typeTV;
	@ViewById(R.id.title)
	TextView titleTV;
	
	PhotoViewAttacher attacher;

	@AfterViews
	void afterInject(){ 
		URL = getIntent().getStringExtra("url");
		title = getIntent().getStringExtra("title");
		
		titleTV.setText(title);
		if(ImageUtil.isGIF(URL)){
			typeTV.setText("GIF");
		}
		
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		downLoadBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				gifImageView.setDrawingCacheEnabled(true);	//注锟斤拷锟斤拷锟斤拷true锟斤拷锟斤拷false
//				if(FileUtil.writeSDcard(URL, imageView.getDrawingCache())){
//					Toast.makeText(getApplicationContext(), "锟斤拷锟斤拷晒锟�, Toast.LENGTH_SHORT).show();
//				}else{
//					Toast.makeText(getApplicationContext(), "锟斤拷锟斤拷失锟斤拷", Toast.LENGTH_SHORT).show();
//				}
				gifImageView.setDrawingCacheEnabled(false);
			}
		});
		
		attacher = new PhotoViewAttacher(imageView);
		load();
	}
	
	private void load(){
		Log.d(TAG, "URL = " + URL);
		String[] allowedTypes = new String[] { "image/gif", "image/png", "image/jpeg" };	// need
		AsyncHttp.get(URL, new BinaryHttpResponseHandler(allowedTypes) {

			@Override
			public void onSuccess(byte[] binaryData) {
				progressBar.setVisibility(View.GONE);
				if(ImageUtil.isGIF(URL)){
					Log.d(TAG, "load gif");
					try {
						GifDrawable gifFromBytes = new GifDrawable( binaryData );
						Log.d(TAG, "load success");
						gifImageView.setImageDrawable(gifFromBytes);
						imageView.setVisibility(View.GONE);
					} catch (IOException e) {
						Log.d(TAG, e.getMessage());
						e.printStackTrace();
					}
				}else{
					Log.d(TAG, "load other");
					Bitmap bmp = BitmapFactory.decodeByteArray(binaryData, 0, binaryData.length);
					imageView.setImageBitmap(bmp);
					gifImageView.setVisibility(View.GONE);
					attacher.update();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] binaryData, Throwable error) {
				Log.d(TAG, "load error" + error.getMessage());
				Toast.makeText(ImageActivity.this, "网络异常", Toast.LENGTH_LONG).show();
			}
			
		});
	}
	
}
