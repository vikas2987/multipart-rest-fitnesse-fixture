package com.goraksh.http.multipart.client;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.RequestLine;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;

import com.goraksh.http.client.Header;

/**
 * 
 * @author niteshk
 * 
 */
public class MultipartResponse {

	private HttpEntity responseEntity;
	
	private RequestLine requestLine;
	private StatusLine statusLine;
	private  Header responseHeader;
	private long contentLen;
	
	private String contentMimeType;
	private String charset;
	
	private String content;
	
	private byte[] byteContent;

	public MultipartResponse(HttpEntity responseEntity) throws IOException {
		this.responseEntity = responseEntity;
		this.contentLen = responseEntity.getContentLength();
		EntityUtils.consume(responseEntity);
	}
	
	public MultipartResponse(HttpEntity responseEntity, RequestLine requestLine, StatusLine statusLine, Header header ) throws IOException {
		this.responseEntity = responseEntity;
		this.requestLine = requestLine;
		this.statusLine = statusLine;
		this.responseHeader = header;
		//EntityUtils.consume(responseEntity);
		
		init();
	}
	
	private void init() throws IOException {
		contentCharSet();
		contentMimeType();
		toByteArray();
		//toContentString();
	}
	
	public RequestLine getRequestLine() {
		return this.requestLine;
	}

	public void contentCharSet() {
		charset = EntityUtils.getContentCharSet(responseEntity);
	}

	public void contentMimeType() {
		contentMimeType = EntityUtils.getContentMimeType(responseEntity);
	}

	public void toByteArray() throws IOException {
		byteContent = EntityUtils.toByteArray(responseEntity);
	}

	public void toContentString() throws ParseException, IOException {

		content = EntityUtils.toString(responseEntity);

	}

	public void toContentString(Charset charset) throws ParseException,
			IOException {
		content = EntityUtils.toString(responseEntity, charset);

	}

	
	/**
	 * @return the statusLine
	 */
	public StatusLine getStatusLine() {
		return statusLine;
	}

	/**
	 * @return the responseHeader
	 */
	public Header getResponseHeader() {
		return responseHeader;
	}

	/**
	 * @return the contentLen
	 */
	public long getContentLen() {
		return contentLen;
	}

	/**
	 * @return the responseEntity
	 */
	public HttpEntity getResponseEntity() {
		return responseEntity;
	}

	/**
	 * @return the contentMimeType
	 */
	public String getContentMimeType() {
		return contentMimeType;
	}

	/**
	 * @return the charset
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		
		if ( this.content != null ) return content;
		
		if ( byteContent == null ) return null;
		return new String( byteContent );
	}

	/**
	 * @return the byteContent
	 */
	public byte[] getByteContent() {
		return byteContent;
	}

}
