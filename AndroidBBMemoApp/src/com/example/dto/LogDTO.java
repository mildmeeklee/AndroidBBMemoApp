package com.example.dto;

public class LogDTO {
	String id;
	String pwd;
	int user_cnt;
	
	public int getUser_cnt() {
		return user_cnt;
	}
	public void setUser_cnt(int user_cnt) {
		this.user_cnt = user_cnt;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;	
	}

	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	@Override
	public String toString() {
		return "id :: "+id+", pwd :: "+pwd;
	}
	

	public LogDTO() {
	}
	LogDTO(String id, String pwd){
		this.id = id;
		this.pwd = pwd;
		
	}
	
}
