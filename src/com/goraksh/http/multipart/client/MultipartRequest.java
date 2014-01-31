package com.goraksh.http.multipart.client;

import org.apache.http.HttpEntity;

import com.goraksh.http.entity.mime.MimeSubType;

/**
 * 
 * @author niteshk
 *
 */
public interface MultipartRequest {

	public HttpEntity getMultipartEntity();
	public MimeSubType getMimeSubtype();
}
