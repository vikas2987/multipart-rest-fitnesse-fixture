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
public class MultipartMixedRequest implements MultipartRequest {
	
	private List<ContentBody> contentBody;
	
	private MimeSubType multipartMimeSubtype;
	
	 private MixedMultipartEntity reqEntity;
	
	public MultipartMixedRequest( ) {
		this.contentBody = new ArrayList<>(5);
		this.reqEntity = new MixedMultipartEntity( MimeSubType.MIXED );
	}
	
	public MultipartMixedRequest addAttachmentPart( String name, ContentBody body ) {
		this.reqEntity.addAttachmentPart(name, body);
		return this;
	}
	
	public MultipartMixedRequest addStringPart( String name, ContentBody body ) {
		this.reqEntity.addMixedPart( name, body);
		return this;
	}
	
	public HttpEntity getMultipartEntity() {
		return this.reqEntity;
	}
	
	public MimeSubType getMimeSubtype() {
		return this.multipartMimeSubtype;
	}

}
