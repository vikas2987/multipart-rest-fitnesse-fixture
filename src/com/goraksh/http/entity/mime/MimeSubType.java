package com.goraksh.http.entity.mime;

/**
 * 
 * @author niteshk
 *
 */
public enum MimeSubType {
	
	MIXED("mixed"), FORMDATA("form-data");
	
	private String subtype;
	
	MimeSubType( String subtype ) {
		this.subtype = subtype;
	}
	
	public String subtypeAsString() {
		return this.subtype;
	}
}
