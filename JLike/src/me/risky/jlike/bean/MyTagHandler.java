package me.risky.jlike.bean;

import org.xml.sax.XMLReader;

import android.graphics.Color;
import android.text.Editable;
import android.text.Html.TagHandler;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 标签Handler，筛选出标签内容
 * 
 * @author Dave
 * 
 */
public class MyTagHandler implements TagHandler {

	private final static String TAG = "MyTagHandler";

	boolean first = true;
	String parent = "ul";
	int index = 1;

	private int sIndex = 0;
	private int eIndex = 0;

	@Override
	public void handleTag(boolean opening, String tag, Editable output,
	        XMLReader xmlReader) {

		Log.d(TAG, "tag is = " + tag);
		// 系统标签拦截不到
		
		//列表标签
	    if(tag.equals("ul")) parent="ul";
	    else if(tag.equals("ol")) parent="ol";
	    if(tag.equals("li")){
	        if(parent.equals("ul")){
	            if(first){
	                output.append("\n• ");
	                first= false;
	            }else{
	                first = true;
	            }
	        }
	        else{
	            if(first){
	                output.append("\n"+index+". ");
	                first= false;
	                index++;
	            }else{
	                first = true;
	            }
	        }   
	    }
	    
	    //bold标签
	    if(tag.equals("bold")){
	    	System.out.println("tag" + tag);
	    	if (opening) {
                sIndex=output.length();
                System.out.println("sIndex" + sIndex);
            }else {
                eIndex=output.length();
                System.out.println("eIndex" + eIndex);
                output.setSpan(new ForegroundColorSpan(Color.BLACK), sIndex, eIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                //output.setSpan(new SubscriptSpan(), sIndex, eIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
	    }
	    
	    
	    // a标签
	    
	    if(tag.equals("click")){
	    	Log.d(TAG, "tag a");
	    	if(opening){
	    		sIndex=output.length();
	    	}else{
	    		eIndex=output.length();
	    		
	    		final Editable out = output;
	    		output.setSpan(new ClickableSpan() {
					
					@Override
					public void onClick(View arg0) {
						Log.d(TAG, "click a  " + sIndex + "  " + eIndex + "  " + out.toString());
						URLSpan[] urls =  out.getSpans(sIndex, eIndex, URLSpan.class);
				        Log.d(TAG, "url length : " + urls.length);
				        if(urls.length > 0)
				        	Log.d(TAG, "url:" + urls[0]);
					}
				}, sIndex, eIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	    	}
	    }
	}
	public class MyClickableSpan extends ClickableSpan {  
        
        private String mSpan;  
  
        public MyClickableSpan(String span) {  
            mSpan = span;  
        }  
  
        @Override  
        public void onClick(View view) {  
            Log.e("MyTagHandler", "span:" + mSpan);
            if(onLinkClickListener != null)
            	onLinkClickListener.onClick(view, mSpan);
        }  
        
        private OnLinkClickListener onLinkClickListener;
        public void setOnClickListener(OnLinkClickListener onLinkClickListener){
    		this.onLinkClickListener = onLinkClickListener;
    	}
    }  
	
	
	
	
	
	public interface OnLinkClickListener {
		public void onClick(View v, String url);
	}
}
