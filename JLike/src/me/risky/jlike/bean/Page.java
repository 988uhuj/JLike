package me.risky.jlike.bean;

import org.androidannotations.annotations.EBean;

/**
 *
 */
@EBean
public class Page {
	public int page = 1;
	
	public int contentMutiPages;
	public boolean contentFirstPage = true;
	
	public Page(){
		page = 1;
	}
	
	public void resetPage(){
		page = 1;
	}
	public void setPage(int num){
		page = num;
	}
	public int getCurrentPage(){
		return page;
	}
	public void addPage(){
		page ++;
	}
	public String getNextPageAndAdd(){
		page ++;
		return String.valueOf(page);
	}
	
	public String getNextPage(){
		return String.valueOf(page + 1);
	}
	
}
