package com.jcs.magazine.bean;

import java.util.List;

/**
 * result字段为列表形式的json
 * T为list的单个bean类型
 * author：Jics
 * 2017/8/1 09:18
 */
public class BaseListTemplet<T> extends BaseMgz<BaseListTemplet<T>> {

	private int total;

	private List<T> body;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getBody() {
		return body;
	}

	public void setBody(List<T> body) {
		this.body = body;
	}

	public String print(){
		return super.toString();
	}
	@Override
	public String toString() {
		return "BaseListTemplet{" +
				"total=" + total +
				", body=" + body.toString() +
				'}';
	}
}
