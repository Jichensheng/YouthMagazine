package com.jcs.magazine.bean;

import java.io.Serializable;

/**
 * author：Jics
 * 2017/8/25 14:29
 */
public class UserBean implements Serializable {
	private String uid;//用户ID
	private String name;//姓名
	private String nick;//昵称
	private String regData;//注册时间
	private String head;//头像地址
	private String phone;//电话
	private String sId;//学号
	private String college;//学院
	private String psw;
	private String token;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPsw() {
		return psw;
	}

	public void setPsw(String psw) {
		this.psw = psw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getRegData() {
		return regData;
	}

	public void setRegData(String regData) {
		this.regData = regData;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getsId() {
		return sId;
	}

	public void setsId(String sId) {
		this.sId = sId;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "UserBean{" +
				"uid='" + uid + '\'' +
				", name='" + name + '\'' +
				", nick='" + nick + '\'' +
				", regData='" + regData + '\'' +
				", head='" + head + '\'' +
				", phone='" + phone + '\'' +
				", sId='" + sId + '\'' +
				", college='" + college + '\'' +
				", token='" + token + '\'' +
				'}';
	}
}
