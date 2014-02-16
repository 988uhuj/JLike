/**
 * 
 */
package me.risky.jlike.base;

import org.androidannotations.annotations.EFragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * @author chenupt@gmail.com
 *
 * @Description:TODO
 *
 * @date 2013��12��1�� ����2:50:56 
 *
 */
@EFragment
public abstract class BaseFragment extends Fragment{
	
	public static String TAG = "BaseFragment";
	
	private OnAsyncTaskListener listener;


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.d(TAG, "onActivityCreated");
		initComponent();
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onAttach(Activity activity) {
		Log.d(TAG, "onAttach");
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		setRetainInstance(true);
		init();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView");
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onDetach() {
		Log.d(TAG, "onDetach");
		super.onDetach();
	}
	
	
	
	
	//----------------------------------
	
	
	
	
	// �Զ�����ӵķ���
	protected void init(){
		
	}
	protected void initComponent(){
		
	}
	
	
	public void setOnThreadListener(OnAsyncTaskListener listener){
		this.listener = listener;
	}
	
	
	public void executeThread(String params){
		new BaseAsyncTask(listener).execute(params);
	}
	
}
