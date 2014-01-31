package com.goraksh.http.resource;

/**
 * 
 * @author niteshk
 *
 */
public class AttachmentUriInfo {

	private Long articleId;
	
	private String lang;
	
	private String attribute;

	/**
	 * @return the articleId
	 */
	public Long getArticleId() {
		return articleId;
	}

	/**
	 * @param articleId the articleId to set
	 */
	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	/**
	 * @return the lang
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * @param lang the lang to set
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * @return the attribute
	 */
	public String getAttribute() {
		return attribute;
	}

	/**
	 * @param attribute the attribute to set
	 */
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	
	
	public String getCompleteUri( String baseUri ) {
		return baseUri + "?article=" + ( getArticleId()!= null ? getArticleId(): "" )
				+ "&$lang=" + ( getLang() != null ? getLang() : "" )
				 + "&$attribute=" +  ( getAttribute() != null ? getAttribute() : "all");
	}
	
	
}
