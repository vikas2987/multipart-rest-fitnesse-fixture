package com.goraksh.http.multipart.client;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.http.HttpEntity;
import org.apache.http.RequestLine;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.goraksh.http.client.Header;
import com.goraksh.http.client.util.TextReader;

/**
 * 
 * @author niteshk
 *
 */
public class MultipartPost {

	private MultipartRequest request;
	private Header header;
	private HttpPost httpPost;
	private String completeUrl;

	private CloseableHttpClient httpclient;
	private TextReader textReader;

	public MultipartPost(MultipartRequest request, Header header,
			String completerUrl) {
		this.request = request;
		this.header = header;
		this.completeUrl = completerUrl;

		init();
	}

	private void init() {
		this.httpclient = HttpClients.createDefault();
		this.httpPost = new HttpPost(completeUrl);
		this.textReader = new TextReader();
}

	private void processHeaders() {

		Set<String> set = header.keyset();
		Iterator<String> iter = set.iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			httpPost.setHeader(key, header.get(key));
		}
	}

	/**
	 * 
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public MultipartResponse postAndClose() throws ClientProtocolException, IOException {

		processHeaders();
		
		RequestLine requestLine;
		StatusLine statusLine;
		Header header;
		
		try {
			HttpEntity reqEntity = request.getMultipartEntity();
			httpPost.setEntity(reqEntity);
			
			requestLine = httpPost.getRequestLine();

			CloseableHttpResponse response = httpclient.execute(httpPost);
			HttpEntity resEntity;
			try {
				
				statusLine = response.getStatusLine();
				header = new Header( response.getAllHeaders() );
				resEntity = response.getEntity();
				return response( resEntity, requestLine, statusLine, header );
			
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
	}
	
	/**
	 * 
	 * @param resEntity
	 * @param requestLine
	 * @param statusLine
	 * @param responseHeader
	 * @return
	 * @throws IOException
	 */
	public MultipartResponse response( HttpEntity resEntity, RequestLine requestLine, StatusLine statusLine, Header responseHeader ) throws IOException {
		return new MultipartResponse(resEntity, requestLine, statusLine, header );
		
	}

}
