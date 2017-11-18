package com.jcs.magazine.bean;

import java.io.Serializable;

/**
 * 所有请求基础模板
 * 泛型E为results字段的实体
 * author：Jics
 * 2017/7/31 14:20
 */
public class BaseMgz<E> implements Serializable {

	private boolean succ;
	private int statusCode;
	private String msg;
	private E results;
	private long time;

	public boolean isSucc() {
		return succ;
	}

	public void setSucc(boolean succ) {
		this.succ = succ;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public E getResults() {
		return results;
	}

	public void setResults(E results) {
		this.results = results;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	@Override
	public String toString() {

		return "BaseMgz{" +
				"succ=" + succ +
				", statusCode=" + statusCode +
				", msg='" + msg + '\'' +
				", results=" + results!=null?results.toString():"null" +
				", time=" + time +
				'}';
	}
}
