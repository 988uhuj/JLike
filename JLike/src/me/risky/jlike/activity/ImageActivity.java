package me.risky.jlike.activity;

import java.io.ByteArrayInputStream;

import me.risky.jlike.R;
import me.risky.jlike.base.BaseActivity;
import me.risky.jlike.base.BaseFragmentActivity;
import me.risky.library.function.AsyncHttp;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.NoTitle;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;
import org.apache.http.Header;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.BinaryHttpResponseHandler;

@WindowFeature(value = { Window.FEATURE_NO_TITLE })
@EActivity(R.layout.activity_image_page)
public class ImageActivity extends BaseActivity {

	private String URL;
	
	@ViewById(R.id.image)
	ImageView imageView;
	@ViewById(R.id.loading)
	ProgressBar progressBar;
	
	@ViewById(R.id.back)
	ImageView backBtn;
	@ViewById(R.id.download)
	ImageView downLoadBtn;
	
	PhotoViewAttacher attacher;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		URL = getIntent().getStringExtra("url");
		System.out.println("URL" + URL);
	}
	
	@AfterViews
	void afterInject(){
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		downLoadBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				imageView.setDrawingCacheEnabled(true);	//ע������true����false
//				if(FileUtil.writeSDcard(URL, imageView.getDrawingCache())){
//					Toast.makeText(getApplicationContext(), "����ɹ�", Toast.LENGTH_SHORT).show();
//				}else{
//					Toast.makeText(getApplicationContext(), "����ʧ��", Toast.LENGTH_SHORT).show();
//				}
				imageView.setDrawingCacheEnabled(false);
			}
		});
		
		attacher = new PhotoViewAttacher(imageView);
		
		
		load();
	}
	
	private void load(){
		AsyncHttp.get(URL, new BinaryHttpResponseHandler() {

			@Override
			public void onSuccess(byte[] binaryData) {
				Bitmap bmp = BitmapFactory.decodeStream(new ByteArrayInputStream(binaryData));
				imageView.setImageBitmap(bmp);
				progressBar.setVisibility(View.GONE);
				attacher.update();
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] binaryData, Throwable error) {
				Toast.makeText(ImageActivity.this, "网络出错", Toast.LENGTH_LONG).show();
			}
		   
			
		});
	}
	
}
