package me.risky.jlike.base;

import java.util.Map;

public interface OnAsyncTaskListener {
	public void onPostExecute(Map<String, Object> result);
	public Map<String, Object> doInBackground(String response);
}
