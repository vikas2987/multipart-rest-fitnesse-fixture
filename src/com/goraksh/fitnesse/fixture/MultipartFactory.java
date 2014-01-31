package com.goraksh.fitnesse.fixture;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;

import com.goraksh.http.multipart.client.MultipartRequest;

import smartrics.rest.client.RestClient;
import smartrics.rest.fitnesse.fixture.PartsFactory;
import smartrics.rest.fitnesse.fixture.support.Config;

/**
 * 
 * @author niteshk
 * 
 */
public class MultipartFactory extends PartsFactory {

	protected MultipartRestClient lastCalledClient; // Not to be used. Just a hack
	
	public RestClient buildRestClient(final Config config) {
		
		if ( lastCalledClient != null ) return lastCalledClient;
		
		CloseableHttpClient httpClient = new MultipartHttpClientBuilder()
				.createCloseableHttpClient(config);

		this.lastCalledClient = new MultipartRestClient(httpClient);
		return lastCalledClient;
	}	
}
