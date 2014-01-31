package com.goraksh.http.resource;

/**
 * Only {@code nane} attribute for attachment is added, as this Object primarily meant for Testing
 * Internal Attachment Only, for which {@code name} sufficecs
 * Add other attributes if required.
 * @author niteshk
 *
 */
public class KbAttachment {

	private String name;
	
	public void setName( String name ) {
		this.name = name;
	}
	
	private String xml;
	
	public String toString() {
	
		if ( xml != null ) return xml;
		StringBuilder sb = new StringBuilder();
		sb.append( "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" )
		.append( "<attachment xmlns=\"http://bindings.egain.com/ws/model/v12/gen/kb\">")
		.append( "<name>").append( name != null ? name : "" ).append( "</name>")
		.append( "</attachment>" );
		
		this.xml = sb.toString();
		return xml;
	}
}
