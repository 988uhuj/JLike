package me.risky.jlike.dao;

import java.util.ArrayList;
import java.util.List;

import me.risky.jlike.bean.WelfareDetail;
import me.risky.jlike.bean.WelfareItem;
import me.risky.jlike.util.Constants;

import org.androidannotations.annotations.EBean;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;


@EBean
public class WelfareDao {
	
	public List<WelfareItem> getWelfareList(String htmlContent){
		List<WelfareItem> list = new ArrayList<WelfareItem>();
		try {
			Document doc = Jsoup.parse(htmlContent);
			Elements articles = doc.getElementsByTag("article");
			for(Element article : articles){
				// Img
				Element img = article.getElementsByTag("img").get(0);
				String imgSrc = img.attr("src");
				// Title
				Element title = article.getElementsByTag("header").get(0);
				String titleStr = title.text();
				Log.d("WelfareDao", "titleSrc" + imgSrc);
				Log.d("WelfareDao", "titleSrc" + title.text());
				// Content 
				Element content = article.getElementsByClass("note").get(0);
				String contentStr = content.ownText();
				// url 
				Element url = title.getElementsByTag("a").get(0);
				String detailUrl = url.attr("href");
				
				// set
				WelfareItem item = new WelfareItem();
				item.setImgSrc(imgSrc);
				item.setTitle(titleStr);
				item.setContent(contentStr);
				item.setDetailUrl(detailUrl);
				list.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}
	
	public List<WelfareDetail> getWelfareDetail(String htmlContent){
		List<WelfareDetail> list = new ArrayList<WelfareDetail>();
		
		try {
			Document doc = Jsoup.parse(htmlContent);
			Element content = doc.getElementsByClass("content").get(0);
			// Title
			Element title = content.getElementsByTag("header").get(0);
			Element titleText = title.getElementsByTag("a").get(0);
			String titleStr = titleText.ownText();
			WelfareDetail detail = new WelfareDetail();
			detail.setTitle(titleStr);
			detail.setType(Constants.DEF_WELFARE_DETAIL.TITLE);
			list.add(detail);
			
			Element articleContent = content.getElementsByClass("article-content").get(0);
			Elements articleContentP = articleContent.getElementsByTag("p");
//			for(Element p : articleContentP){
			for(int i = 0; i < articleContentP.size() - 1; i ++){
				Element p = articleContentP.get(i);
				// Img
				Elements imgs = p.getElementsByTag("img");
				for(Element img : imgs){
					String imgSrc = img.attr("src");
					
					detail = new WelfareDetail();
					detail.setImgSrc(imgSrc);
					detail.setType(Constants.DEF_WELFARE_DETAIL.IMG);
					list.add(detail);
				}
				// 已抽取出图片
				if(imgs.size() > 0){
					continue ;
				}
				
				// Content
				String contentP = p.html();
				
				// set
				WelfareDetail item = new WelfareDetail();
				item.setContent(contentP);
				item.setType(Constants.DEF_WELFARE_DETAIL.CONTENT);
				list.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}
}
