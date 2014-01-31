package com.goraksh.fitnesse.fixture;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.entity.ContentType;

/**
 * 
 * @author niteshk
 *
 */
public class FixtureConstants {
	
	public static final ContentType FILE_CONTENT_TYPE = ContentType.APPLICATION_OCTET_STREAM;
	public static final ContentType XML_CONTENT_TYPE = ContentType.APPLICATION_XML;
	
	public static final String COM_EGAIN_RESTFIXTURE_MIME_CONTENT = "com.egain.restfixture.mime.content";
	
	public static final String PARTS_FACTORY = "rest.fitnesse.fixture.partsfactor";
	
	public static final String CONTENT_HANDLER_MAP = "restfixture.content.handlers.map";
	
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	private static Map<String, ContentType> contentTypeSet;
	
	static  {
		contentTypeSet = new HashMap<>();
		contentTypeSet.put("application/xml", ContentType.APPLICATION_XML);
		contentTypeSet.put("application/json", ContentType.APPLICATION_JSON);
		contentTypeSet.put("application/x-www-form-urlencoded", ContentType.APPLICATION_FORM_URLENCODED);
		contentTypeSet.put("XML", ContentType.APPLICATION_XML);
		contentTypeSet.put("JSON", ContentType.APPLICATION_JSON);
		contentTypeSet.put("FORM-DATA", ContentType.APPLICATION_FORM_URLENCODED);
	}
	
	public static ContentType getType( String typeStre ) {
		return contentTypeSet.get( typeStre );
	}

}
