package com.goraksh.fitnesse.fixture;

import java.util.HashMap;
import java.util.Map;

import com.goraksh.http.client.util.Factory;
import com.goraksh.http.client.util.MyLog;

import smartrics.rest.client.RestRequest;
import smartrics.rest.client.RestResponse;
import smartrics.rest.fitnesse.fixture.RestFixture;
import smartrics.rest.fitnesse.fixture.support.CellWrapper;
import smartrics.rest.fitnesse.fixture.support.Config;
import smartrics.rest.fitnesse.fixture.support.StringTypeAdapter;
import smartrics.rest.fitnesse.fixture.support.Tools;
import smartrics.rest.fitnesse.fixture.support.Url;

/**
 * Rest Fixture that uses Multipart factory
 * 
 * @author niteshk
 * 
 */
public class MultipartRestFixture extends RestFixture {

	/**
	 * This must be different from the filename of RestFixture
	 */
	protected String uploadedFile;

	/**
	 * A field with the same name also exists in the super class. Both arr
	 * rferences to the same object
	 */
	protected MultipartFactory partsFactory;

	private RestRequest lastRequest;

	protected boolean followRedirects = true;

	private MultipartRestClient restClient;
	
	private static final MyLog log = Factory.get(MultipartRestFixture.class);

	public MultipartRestFixture(MultipartFactory partsFactory, String hostName,
			String configName) {
		super(partsFactory, hostName, configName);
		this.partsFactory = partsFactory;
		//this.restClient = partsFactory.lastCalledClient; // this is null;
		this.restClient = (MultipartRestClient)partsFactory.buildRestClient( getConfig() );
	}

	public MultipartRestFixture(String hostName, String configName) {
		this(new MultipartFactory(), hostName, configName);
	}

	public MultipartRestFixture(String hostName) {
		this(hostName, Config.DEFAULT_CONFIG_NAME);
	}

	/**
	 * Should never be called
	 */
	@Deprecated
	public MultipartRestFixture() {
		super();
	}

	protected void initialize(Runner runner) {
		super.initialize(runner);
	}

	public void uploadFile() {
		CellWrapper cell = row.getCell(1);
		if (cell == null) {
			getFormatter().exception(row.getCell(0),
					"You must pass a parameter name to set");
		} else {
			uploadedFile = GLOBALS.substitute(cell.text());
			
			log.log("Logging upload file: " + uploadedFile );
			renderReplacement(cell, uploadedFile);
		}

	}

	/**
	 * 
	 * @param cell
	 * @param actual
	 */
	protected void renderReplacement(CellWrapper cell, String actual) {
		StringTypeAdapter adapter = new StringTypeAdapter();
		adapter.set(actual);
		if (!adapter.equals(actual, cell.body())) {
			// eg - a substitution has occurred
			getFormatter().right(cell, adapter);
		}
	}

	public String getUploadedFile() {
		return this.uploadedFile;
	}

	protected void setLastRequest(RestRequest lastRequest) {
		this.lastRequest = lastRequest;
	}

	protected void doMethod(String method, String resUrl,
			Map<String, String> headers, String rBody) {
		setLastRequest(partsFactory.buildRestRequest());
		getLastRequest().setMethod(RestRequest.Method.valueOf(method));
		getLastRequest().addHeaders(headers);
		getLastRequest().setFollowRedirect(followRedirects);
		getLastRequest().setResourceUriEscaped(resourceUrisAreEscaped);
		if (fileName != null) {
			getLastRequest().setFileName(fileName);
		}
		if (multipartFileName != null) {
			getLastRequest().setMultipartFileName(multipartFileName);
		}

		if (uploadedFile != null) {
			getLastRequest().setMultipartFileName(uploadedFile);
		}

		log.log("Res Url into Fixture: " + resUrl);
		getLastRequest().setMultipartFileParameterName(
				multipartFileParameterName);
		String[] uri = resUrl.split("\\?");
		String[] thisRequestUrlParts = buildThisRequestUrl(uri[0]);
		
		log.log("thisRequestUrlParts: " + thisRequestUrlParts[0] );
		
		log.log("Setting resource : " + thisRequestUrlParts[1]);
		getLastRequest().setResource(thisRequestUrlParts[1]);
		
		
		if (uri.length == 2) {
			getLastRequest().setQuery(uri[1]);
			log.log("Setting query:" + uri[1]);
		}
		if ("Post".equals(method) || "Put".equals(method)) {
			getLastRequest().setBody(rBody);
		}
		try {
		restClient.setBaseUrl(thisRequestUrlParts[0]);
		} catch (Exception e ) {
			log.log("Exception thisRequestUrlParts[0]: " + e.toString());
		}
		
		log.log("Setting baseurl: " + thisRequestUrlParts[0]);
		RestResponse response = restClient.execute(getLastRequest(),
				getConfig());
		setLastResponse(response);
	}

	private RestResponse lastResponse;

	private void setLastResponse(RestResponse lastResponse) {
		this.lastResponse = lastResponse;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void doMethod(String body, String method) {
		CellWrapper urlCell = row.getCell(1);
		String url = deHtmlify(stripTag(urlCell.text()));
		String resUrl = GLOBALS.substitute(url);
		String rBody = GLOBALS.substitute(body);
		Map<String, String> rHeaders = substitute(getHeaders());
		try {
			doMethod(method, resUrl, rHeaders, rBody);
			completeHttpMethodExecution();
		} catch (RuntimeException e) {
			getFormatter().exception(
					row.getCell(0),
					"Execution of " + method + " caused exception '"
							+ e.getMessage() + "'");
		}
	}

	private String deHtmlify(String someHtml) {
		return Tools.fromHtml(someHtml);
	}

	private Map<String, String> substitute(Map<String, String> headers) {
		Map<String, String> sub = new HashMap<String, String>();
		for (Map.Entry<String, String> e : headers.entrySet()) {
			sub.put(e.getKey(), GLOBALS.substitute(e.getValue()));
		}
		return sub;
	}

	private String stripTag(String somethingWithinATag) {
		return Tools.fromSimpleTag(somethingWithinATag);
	}

	private String[] buildThisRequestUrl(String uri) {
		String[] parts = new String[2];

		String baseUrl = getBaseUrl();
		
		log.log("Fixture. Base Url:" + baseUrl );
		
		if (baseUrl == null || uri.startsWith(baseUrl.toString())) {
			Url url = new Url(uri);
			parts[0] = url.getBaseUrl();
			parts[1] = url.getResource();
		} else {
			try {
				Url attempted = new Url( uri);
				parts[0] = attempted.getBaseUrl();
				parts[1] = attempted.getResource();
			} catch (RuntimeException e) {
				parts[0] = baseUrl;
				parts[1] = uri;
				
				log.log("Exceptuon while getting base url: " + e.toString() + " set new parts. parts[0]: " + parts[0] + " .parst[1] :" + parts[1]);

			}
		}
		return parts;
	}

	protected RestResponse getLastResponse() {
		return this.lastResponse;
	}

	protected RestRequest getLastRequest() {
		return this.lastRequest;
	}

}
