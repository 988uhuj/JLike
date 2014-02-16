package me.risky.jlike.base;

import java.util.Map;

import me.risky.library.function.*;
import android.os.AsyncTask;

//�첽�߳��࣬����̳п�ʹ���첽�������
public class BaseAsyncTask extends AsyncTask<String, Integer, Map<String, Object>> {

	private OnAsyncTaskListener listener;

	public BaseAsyncTask(OnAsyncTaskListener listener) {
		this.listener = listener;
	}

	@Override
	protected Map<String, Object> doInBackground(String... params) {
		HttpService httpService = new HttpService();
		String result = httpService.get(params[0]);
		return listener.doInBackground(result);
	}

	@Override
	protected void onPostExecute(Map<String, Object> result) {
		listener.onPostExecute(result);
		super.onPostExecute(result);
	}
}