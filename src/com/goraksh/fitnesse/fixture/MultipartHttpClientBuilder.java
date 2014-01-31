package com.goraksh.fitnesse.fixture;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import smartrics.rest.fitnesse.fixture.support.Config;

/**
 * 
 * @author niteshk
 * 
 */
public class MultipartHttpClientBuilder extends HttpClientBuilder {

	public MultipartHttpClientBuilder() {
		super();
	}

	public CloseableHttpClient createCloseableHttpClient(final Config config) {
		return HttpClients.createDefault();

	}

}