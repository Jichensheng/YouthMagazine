package com.jcs.magazine.bean;

import java.util.List;

/**
 * author：Jics
 * 2017/7/31 14:20
 */
public class MgzBean<T> {

	/**
	 * succ : true
	 * statusCode : 200
	 * msg : 回馈信息
	 * results : {"vol":53,"editorship":"季晨生","subeditor":"Jcs","images":" http: //xxx.xxx.xxx/xxx"}
	 * time : 1452839930069
	 */

	private boolean succ;
	private int statusCode;
	private String msg;
	private List<T> results;
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

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
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
		String list="";
		for (T result : results) {
			list+=result.toString()+'\n';
		}
		return "MgzBean{" +
				"succ=" + succ +
				", statusCode=" + statusCode +
				", msg='" + msg + '\'' +
				", results=" + list +
				", time=" + time +
				'}';
	}
}
