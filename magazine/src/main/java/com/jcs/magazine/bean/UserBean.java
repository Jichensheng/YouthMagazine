package com.jcs.magazine.bean;

import com.jcs.magazine.network.YzuClientDemo;

import java.io.Serializable;

/**
 * author：Jics
 * 2017/8/25 14:29
 */
public class UserBean implements Serializable {
	private String uid;//用户ID
	private String name;//姓名
	private String nick;//昵称
	private String regDate;//注册时间
	private String head;//头像地址永远不变
	private String phone;//电话
	private String sId;//学号
	private String college;//学院
	private String sex;//性别
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

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getHead() {
		return YzuClientDemo.RESOURCE_HEAD_HOST+head;
	}
	public String getHeadName(){
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "UserBean{" +
				"uid='" + uid + '\'' +
				", name='" + name + '\'' +
				", nick='" + nick + '\'' +
				", regDate='" + regDate + '\'' +
				", head='" + head + '\'' +
				", phone='" + phone + '\'' +
				", sId='" + sId + '\'' +
				", college='" + college + '\'' +
				", sex='" + sex + '\'' +
				", psw='" + psw + '\'' +
				", token='" + token + '\'' +
				'}';
	}
}
