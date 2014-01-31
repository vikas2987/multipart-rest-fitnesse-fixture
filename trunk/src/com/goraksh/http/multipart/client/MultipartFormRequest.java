package com.goraksh.http.multipart.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.content.ContentBody;

import com.goraksh.http.entity.mime.MimeSubType;
import com.goraksh.http.entity.mime.MixedMultipartEntity;

/**
 * 
 * @author niteshk
 *
 */
public class MultipartFormRequest implements MultipartRequest {
	
	private List<ContentBody> contentBody;
	
	private MimeSubType multipartMimeSubtype;
	
	 private MixedMultipartEntity reqEntity;
	
	public MultipartFormRequest( ) {
		this.contentBody = new ArrayList<>(5);
		MixedMultipartEntity reqEntity = new MixedMultipartEntity( MimeSubType.FORMDATA );
	}
	
	public MultipartFormRequest addFormPart( String name, ContentBody body ) {
		this.reqEntity.addFormPart(name, body );
		return this;
	}
	
	public HttpEntity getMultipartEntity() {
		return this.reqEntity;
	}
	
	public MimeSubType getMimeSubtype() {
		return this.multipartMimeSubtype;
	}

}
