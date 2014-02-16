package me.risky.jlike.util;

public class UrlsUtil {
	
	public static String NEWS_LIST_URL_1 = "http://zhaofuli.net/category/songfuli";
	public static String NEWS_LIST_URL_2 = "http://zhaofuli.net/category/ruanjiansenlin";
	public static String NEWS_LIST_URL_3 = "http://zhaofuli.net/category/neihannidongde";
	public static String NEWS_LIST_URL_4 = "http://zhaofuli.net/category/tupianfayuandi";
	public static String NEWS_LIST_URL_5 = "http://zhaofuli.net/category/jike";
	
	public static String getBaseNewsListURL(int newsType){
		String url = "";
		switch(newsType){
		case Constants.DEF_NEWS_TYPE.TYPE1:
			url = NEWS_LIST_URL_1;
			break;
		case Constants.DEF_NEWS_TYPE.TYPE2:
			url = NEWS_LIST_URL_2;
			break;
		case Constants.DEF_NEWS_TYPE.TYPE3:
			url = NEWS_LIST_URL_3;
			break;
		case Constants.DEF_NEWS_TYPE.TYPE4:
			url = NEWS_LIST_URL_4;
			break;
		case Constants.DEF_NEWS_TYPE.TYPE5:
			url = NEWS_LIST_URL_5;
			break;
		default:
			break;
		}
		return url;
	}
	
	
	public static String[] getNewsUrlList(){
		String[] urls = new String[5];
		urls[0] = getBaseNewsListURL(Constants.DEF_NEWS_TYPE.TYPE1);
		urls[1] = getBaseNewsListURL(Constants.DEF_NEWS_TYPE.TYPE2);
		urls[2] = getBaseNewsListURL(Constants.DEF_NEWS_TYPE.TYPE3);
		urls[3] = getBaseNewsListURL(Constants.DEF_NEWS_TYPE.TYPE4);
		urls[4] = getBaseNewsListURL(Constants.DEF_NEWS_TYPE.TYPE5);
		return urls;
	}
	
	public static String getNewsListURL(String baseUrl, int page){
		if(page == 1){
			return baseUrl;	//第一页不做处理
		}
		return baseUrl + "/page/" + page;
	}
}
