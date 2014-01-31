package com.goraksh.fitnesse.fixture;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.List;

import com.goraksh.http.client.util.Factory;
import com.goraksh.http.client.util.MyLog;

import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.RequestLine;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smartrics.rest.client.RestClient;
import smartrics.rest.client.RestData;
import smartrics.rest.client.RestRequest;
import smartrics.rest.client.RestRequest.Method;
import smartrics.rest.client.RestResponse;
import smartrics.rest.fitnesse.fixture.support.Config;

import com.goraksh.http.multipart.client.MultipartRequest;
import com.goraksh.http.multipart.client.MultipartResponse;

/**
 * 
 * @author niteshk
 * 
 */
public class MultipartRestClient implements RestClient {

	private static Logger LOG = LoggerFactory
			.getLogger(MultipartRestClient.class);
	
	private static MyLog log = Factory.get(MultipartRestClient.class);

	private final CloseableHttpClient client;
	private MultipartRequest multipartRequest;
	private String baseUrl;

	public MultipartRestClient(CloseableHttpClient client) {
		if (client == null)
			throw new IllegalArgumentException("Null HttpClient instance");

    	this.client = client;
		
	}

	@Override
	public void setBaseUrl(String bUrl) {
		this.baseUrl = bUrl;
	}

	@Override
	public RestResponse execute(RestRequest request) {
		return execute(getBaseUrl(), request);
	}
	
	public RestResponse execute(RestRequest request, Config config) {
		return execute(getBaseUrl(), request, config);
	}

	public RestResponse execute(String hostAddr, RestRequest request, Config config ) {
		RestMultipartEntityBuilder builder = new RestMultipartEntityBuilder(request, config);
		this.multipartRequest = builder.buildMultipartEntity();
		
		return execute(hostAddr, request);
	}
	
	protected MultipartRequest getMultipartRequest() {
		return this.multipartRequest;
	}

		
	@Override
	public RestResponse execute(String hostAddr, RestRequest request) {
		if (request == null || !request.isValid())
			throw new IllegalArgumentException("Invalid request " + request);

		if (request.getTransactionId() == null)
			request.setTransactionId(Long.valueOf(System.currentTimeMillis()));
		LOG.debug("request: {}", request);
		
		log.log("Executing client with : " + request );

		HttpEntityEnclosingRequestBase httpPostOrPut = null;
		
		try {
		httpPostOrPut = instantiateHttpRequestMethod(request);
		} catch ( Exception e ) {
			log.log( "Exception instantiating: " + e.toString() );
		}
		configureHttpMethod(httpPostOrPut, hostAddr, request);
		
		httpPostOrPut.setEntity( getMultipartRequest().getMultipartEntity());
		
		RestResponse resp = new RestResponse();
		resp.setTransactionId(request.getTransactionId());
		resp.setResource(request.getResource());

		CloseableHttpResponse httpResponse;
		HttpEntity resEntity;
		StatusLine statusLine;
		RequestLine requestLine = httpPostOrPut.getRequestLine();
		
		try {

			httpResponse = client.execute(httpPostOrPut);

			try {
				
				Header[] headers = httpResponse.getAllHeaders();
				for (Header h : headers) {
					resp.addHeader(h.getName(), h.getValue());
				}

				statusLine = httpResponse.getStatusLine();
				resEntity = httpResponse.getEntity();
				MultipartResponse mr = new MultipartResponse(resEntity,
						requestLine, statusLine,
						new com.goraksh.http.client.Header( headers ));

				resp.setStatusCode(mr.getStatusLine().getStatusCode());
				resp.setStatusText(mr.getStatusLine().getReasonPhrase());
				resp.setRawBody(mr.getByteContent());
			} finally {
				if (httpResponse != null)
					httpResponse.close();
			}

		} catch (IOException e) {
			String message = "Http call failed for protocol failure";
			throw new IllegalStateException(message, e);
		} catch (Exception e) {
			String message = "Http call failed for IO failure";
			throw new IllegalStateException(message, e);
		} finally {
              if ( client != null )
				try {
					client.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException("Not able to close CLoseable Http Multipart client. " + e.getMessage() );
				}
		}
		LOG.debug("response: {}", resp);
		return resp;
	}

	@Override
	public String getBaseUrl() {
		return this.baseUrl;
	}

	protected HttpEntityEnclosingRequestBase instantiateHttpRequestMethod(
			RestRequest request) {

		Method allowed = request.getMethod();

		if (Method.Post != allowed && Method.Put != allowed)
			throw new RuntimeException(
					"Method not allowed for Multipart request. Method: "
							+ allowed);

		String mName = request.getMethod().toString();
		String className = getMethodClassnameFromMethodName(mName);
		
		log.log("Http post method: " + className );
		try {
			Class<HttpEntityEnclosingRequestBase> clazz = (Class<HttpEntityEnclosingRequestBase>) Class
					.forName(className);
			if (className.endsWith("TraceMethod")) {
				return clazz.getConstructor(String.class).newInstance(
						"http://dummy.com");
			} else {
				return clazz.newInstance();
			}
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(className
					+ " not found: you may be using a too old or "
					+ "too new version of HttpClient", e);
		} catch (InstantiationException e) {
			throw new IllegalStateException("An object of type " + className
					+ " cannot be instantiated", e);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException("The default ctor for type "
					+ className + " cannot be accessed", e);
		} catch (RuntimeException e) {
			throw new IllegalStateException("Exception when instantiating: "
					+ className, e);
		} catch (InvocationTargetException e) {
			throw new IllegalStateException(
					"The ctor with String.class arg for type " + className
							+ " cannot be invoked", e);
		} catch (NoSuchMethodException e) {
			throw new IllegalStateException(
					"The ctor with String.class arg for type " + className
							+ " doesn't exist", e);
		}
	}

	protected String getMethodClassnameFromMethodName(String mName) {
		return String.format("org.apache.http.client.methods.Http%s", mName);
	}

	private void addHeaders(HttpEntityEnclosingRequestBase m,
			RestRequest request) {
		for (RestData.Header h : request.getHeaders()) {
			m.addHeader(h.getName(), h.getValue());
		}
	}

	/**
	 * Adds request headers and Normalise URi
	 * @param requestMethod
	 * @param hostAddr
	 * @param request
	 */
	protected void configureHttpMethod(
			HttpEntityEnclosingRequestBase requestMethod, String hostAddr,
			final RestRequest request) {
		addHeaders(requestMethod, request);

		log.log("Headers added");
		setUri(requestMethod, hostAddr, request);
	}

	public String getContentType(RestRequest request) {
		List<smartrics.rest.client.RestData.Header> values = request
				.getHeader("Content-Type");
		String v = "text/xml";
		if (values.size() != 0)
			v = values.get(0).getValue();
		return v;
	}

	private void setUri(HttpEntityEnclosingRequestBase m, String hostAddr,
			RestRequest request) {
		String host = hostAddr;

		log.log("host: " + host);
		if (host == null)
			throw new IllegalStateException(
					"hostAddress is null: please config httpClient host configuration or "
							+ "pass a valid host address or config a baseUrl on this client");

		String uriString = host + request.getResource();
		boolean escaped = request.isResourceUriEscaped();
		String query = request.getQuery();

		try {

			log.log("Client. Setting  Uri string: " + uriString);
			
			java.net.URI uri = null;
			m.setURI( uri = createUri( uriString, escaped, query));
			
			log.log("Client. Uri set: " + uri.toString() );
		} catch (URIException | URISyntaxException e) {
			throw new IllegalStateException("Problem when building URI: "
					+ uriString, e);
		} catch (NullPointerException e) {
			throw new IllegalStateException("Building URI with null string", e);
		}
	}

	protected java.net.URI createUri(String uriString, boolean escaped,
			String query) throws URIException, URISyntaxException {
		URI u = new org.apache.commons.httpclient.URI(uriString, escaped);

		// return new org.apache.commons.httpclient.URI(uriString, escaped);
		return new java.net.URI(u.getScheme(), u.getAuthority(), u.getPath(),
				query, u.getFragment());
	}

}
