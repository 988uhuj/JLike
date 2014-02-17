package me.risky.jlike.bean;

public class WelfareDetail {
	private String title;
	private String content;
	private String tag;
	private String imgSrc;
	private boolean isGifImg;
	
	private int type;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getImgSrc() {
		return imgSrc;
	}
	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public boolean isGifImg() {
		return isGifImg;
	}
	public void setGifImg(boolean isGifImg) {
		this.isGifImg = isGifImg;
	}
	
	
}
