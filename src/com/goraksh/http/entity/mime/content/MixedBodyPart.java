package com.goraksh.http.entity.mime.content;

import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.content.ContentBody;

/**
 * 
 * @author niteshk
 *
 */
public class MixedBodyPart  extends FormBodyPart {
	
	private String subtype; // allowed values = "attachment" , "form-data"
	
	public MixedBodyPart(String name, ContentBody body) {
		super(name, body);
		// TODO Auto-generated constructor stub
	}

	protected void generateContentDisp(final ContentBody body) {
		// do nothing
	}
	
}
