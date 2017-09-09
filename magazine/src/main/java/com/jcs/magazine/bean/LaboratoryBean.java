package com.jcs.magazine.bean;

import android.content.Intent;
import android.support.annotation.DrawableRes;

import java.io.Serializable;

/**
 * author：Jics
 * 2017/9/8 19:13
 */
public class LaboratoryBean implements Serializable {

	private int res;
	private String title;
	private String description;
	private Intent intent;

	public LaboratoryBean() {
	}

	public LaboratoryBean(@DrawableRes int res, String title, String description, Intent intent) {
		this.res = res;
		this.title = title;
		this.description = description;
		this.intent = intent;
	}

	public int getRes() {
		return res;
	}

	public void setRes(@DrawableRes int res) {
		this.res = res;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Intent getIntent() {
		return intent;
	}

	public void setIntent(Intent intent) {
		this.intent = intent;
	}
}
