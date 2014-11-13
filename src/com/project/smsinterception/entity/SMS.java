package com.project.smsinterception.entity;

import java.io.Serializable;

public class SMS implements Serializable{
	
	private int id;
	private long time;
	private String body;
	private String num;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	
	public SMS(long time, String body, String num) {
		super();
		this.time = time;
		this.body = body;
		this.num = num;
	}
	public SMS() {
		super();
	}
	public SMS(String body, String num) {
		super();
		this.body = body;
		this.num = num;
		this.time = System.currentTimeMillis();
	}
	
	
	@Override
	public String toString() {
		return "SMS [id=" + id + ", time=" + time + ", body=" + body + ", num="
				+ num + "]";
	}
	
	
}
