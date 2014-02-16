package me.risky.jlike.bean;

import org.androidannotations.annotations.EBean;

@EBean
public class WelfareItem {
	private String title;
	private String dateTime;
	private String scanNum;
	private String imgSrc;
	private String content;
	private String detailUrl;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getScanNum() {
		return scanNum;
	}
	public void setScanNum(String scanNum) {
		this.scanNum = scanNum;
	}
	public String getImgSrc() {
		return imgSrc;
	}
	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDetailUrl() {
		return detailUrl;
	}
	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}
	
	
}
