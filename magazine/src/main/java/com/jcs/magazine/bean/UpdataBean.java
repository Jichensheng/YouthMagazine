package com.jcs.magazine.bean;

import java.io.Serializable;

/**
 * author：Jics
 * 2017/8/14 13:51
 */
public class UpdataBean  implements Serializable {

	/**
	 * versionNo : 9
	 * versionName : 1.0.4
	 * loadAddress : /upload/20160718192032_eSuzhou_V9_1.0.4_20160718.apk
	 */

	private int versionNo;
	private String versionName;
	private String loadAddress;
	private String content;

	public int getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(int versionNo) {
		this.versionNo = versionNo;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getLoadAddress() {
		return loadAddress;
	}

	public void setLoadAddress(String loadAddress) {
		this.loadAddress = loadAddress;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "UpdataBean{" +
				"versionNo=" + versionNo +
				", versionName='" + versionName + '\'' +
				", loadAddress='" + loadAddress + '\'' +
				", content='" + content + '\'' +
				'}';
	}
}
