package com.goraksh.fitnesse.fixture;

import java.io.File;
import java.util.*;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;

import smartrics.rest.client.RestRequest;
import smartrics.rest.fitnesse.fixture.support.Config;
import smartrics.rest.fitnesse.fixture.support.Tools;

import com.goraksh.http.multipart.client.MultipartRequest;
import com.goraksh.http.multipart.client.MultipartRequestBuilder;

/**
 * 
 * @author niteshk
 *
 */
public class RestMultipartEntityBuilder {
	
	private MultipartRequestBuilder builder;
	
	private RestRequest request;
	private Config config;
	
	public RestMultipartEntityBuilder( RestRequest request, Config config ) {
		this.request = request;
		this.config = config;	
		this.builder = new MultipartRequestBuilder();
	}
	
	private ContentType getContentType( Map<String, String> map )  {
		Set<String> set = map.keySet();
		Iterator<String> iter = set.iterator();
		while( iter.hasNext() ) {
			String key = iter.next();
			ContentType type = FixtureConstants.getType( key );
			if ( type != null )
				return type;
		}
		
		throw new RuntimeException("Invalid Content Type :" + map.toString() );
	}
	
	public MultipartRequest buildMultipartEntity() {
		String contentHandler = config.get( FixtureConstants.CONTENT_HANDLER_MAP );
		Map<String, String> contentMap = Tools.convertStringToMap(contentHandler, "=", FixtureConstants.LINE_SEPARATOR, true);
		
		ContentType entityContentype = getContentType( contentMap );
		String filename = request.getMultipartFileName();
		
		if ( filename == null ) {
			throw new IllegalArgumentException("Invalid Multipart Request. No filename ot be uploaded" );
		}
		
		File fileToUpload = new File( filename );
		
		if ( !FixtureUtil.checkIfFileExist(fileToUpload )) {
			throw new IllegalArgumentException("Invalid Multipart Request. Invalid Filename or Is directory : " + filename );
		}
		
		String entityStr = request.getBody();
		ContentType fileContenttype = FixtureConstants.FILE_CONTENT_TYPE;
		
		if ( ContentType.APPLICATION_XML == entityContentype || ContentType.APPLICATION_JSON == entityContentype)
		return builder.buildXmlJsonMixedPart(entityStr, entityContentype, fileToUpload,  fileContenttype);
		
		if ( ContentType.APPLICATION_FORM_URLENCODED == entityContentype ) {
			return builder.buildFormDataMixedPart(entityStr, entityContentype, fileToUpload, fileContenttype);
		}
		
		throw new RuntimeException( "Unsupported content type. Supported are XML, JSON, FORM-DATA" );
	}

}
