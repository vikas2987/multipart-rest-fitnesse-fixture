package com.goraksh.http.resource;

/**
 * 
 * @author niteshk
 *
 */
public class KbLogin {
	
	private String userName;
	
	private String password;
	
	private String xml;
		
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void reset() {
		userName = null;
		password = null;
	}
	
	public String toString() {
		if ( xml != null ) 
			return xml;
		
		StringBuilder sb = new StringBuilder();
		String loginUser = userName != null ? userName : "";
		String loginPwd = password != null ? password : "";
		
		sb.append(
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")
				.append("<login xmlns=\"http://bindings.egain.com/ws/model/v12/gen/platform\">")
				.append("<userName>").append( loginUser ).append("</userName>")
				.append("<password>").append(loginPwd).append("</password>")
				.append("</login>");

		xml = sb.toString();
		return xml;
		
	}
}