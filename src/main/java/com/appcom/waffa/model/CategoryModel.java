package com.appcom.waffa.model;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

@Validated
public class CategoryModel {
 
	
	private int categoryId;
	
	private String categoryNameAr;
	
	private String categoryNameEng;
	
	private String imageUrl;
	
	
	public String getCategoryNameAr() {
		return categoryNameAr;
	}
	public void setCategoryNameAr(String categoryNameAr) {
		this.categoryNameAr = categoryNameAr;
	}
	public String getCategoryNameEng() {
		return categoryNameEng;
	}
	public void setCategoryNameEng(String categoryNameEng) {
		this.categoryNameEng = categoryNameEng;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public int getId() {
		return categoryId;
	}
	public void setId(int id) {
		this.categoryId = id;
	}

	
	
}
