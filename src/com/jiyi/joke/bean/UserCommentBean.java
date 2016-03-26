package com.jiyi.joke.bean;

public class UserCommentBean {
	private String usercomment_id;
	private String joke_id;
	private String user_id;
	private String time;
	
	public UserCommentBean(){
		
	}

	public String getUsercomment_id() {
		return usercomment_id;
	}

	public void setUsercomment_id(String usercomment_id) {
		this.usercomment_id = usercomment_id;
	}

	public String getJoke_id() {
		return joke_id;
	}

	public void setJoke_id(String joke_id) {
		this.joke_id = joke_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	

}
