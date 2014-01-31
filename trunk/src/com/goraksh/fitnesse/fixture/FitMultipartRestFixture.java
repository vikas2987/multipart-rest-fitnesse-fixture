package com.goraksh.fitnesse.fixture;

import java.util.Map;

import com.goraksh.http.client.util.Factory;
import com.goraksh.http.client.util.MyLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import smartrics.rest.fitnesse.fixture.FitFormatter;
import smartrics.rest.fitnesse.fixture.FitRestFixture;
import smartrics.rest.fitnesse.fixture.FitRow;
import smartrics.rest.fitnesse.fixture.RestFixture;
import smartrics.rest.fitnesse.fixture.RestFixture.Runner;
import smartrics.rest.fitnesse.fixture.support.CellFormatter;
import smartrics.rest.fitnesse.fixture.support.Config;
import smartrics.rest.fitnesse.fixture.support.RowWrapper;
import smartrics.rest.fitnesse.fixture.support.Url;
import fit.ActionFixture;
import fit.Parse;

/**
 * 
 * @author niteshk
 *
 */
public class FitMultipartRestFixture  extends ActionFixture {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(FitRestFixture.class);

	private static final MyLog log = Factory.get(FitMultipartRestFixture.class);
	
	protected MultipartRestFixture restFixture;

	public String toString() {
		return restFixture.toString();
	}

	public void uploadFile() {
		restFixture.uploadFile();
	}

	public String getUploadedFile() {
		return restFixture.getUploadedFile();
	}

	/**
	 * See {@link RestFixture#getLastEvaluation()}
	 * 
	 * @return last JS evaluation
	 */
	public String getLastEvaluation() {
		return restFixture.getLastEvaluation();
	}

	/**
	 * @return delegates to {@link RestFixture#getBaseUrl()}
	 */
	public String getBaseUrl() {
		return restFixture.getBaseUrl();
	}

	/**
	 * delegates to {@link RestFixture#setBaseUrl(Url)}
	 * 
	 * @param url
	 *            the base url.
	 */
	public void setBaseUrl(Url url) {
		restFixture.setBaseUrl(url);
	}

	/**
	 * delegates to {@link RestFixture#getDefaultHeaders()}
	 * 
	 * @return the default headers.
	 */
	public Map<String, String> getDefaultHeaders() {
		return restFixture.getDefaultHeaders();
	}

	/**
	 * delegates to {@link RestFixture#getFormatter()}
	 * 
	 * @return the cell formatter for Fit.
	 */
	public CellFormatter<?> getFormatter() {
		return restFixture.getFormatter();
	}

	/**
	 * delegates to {@link RestFixture#setMultipartFileName()}
	 */
	public void setMultipartFileName() {
		restFixture.setMultipartFileName();
	}

	/**
	 * delegates to {@link RestFixture#getMultipartFileName()}
	 * 
	 * @return the multipart filename to upload.
	 */
	public String getMultipartFileName() {
		return restFixture.getMultipartFileName();
	}

	/**
	 * delegates to {@link RestFixture#setFileName()}
	 */
	public void setFileName() {
		restFixture.setFileName();
	}

	/**
	 * delegates to {@link RestFixture#getFileName()}
	 * 
	 * @return the name of the file to upload
	 */
	public String getFileName() {
		return restFixture.getFileName();
	}

	/**
	 * delegates to {@link RestFixture#setMultipartFileParameterName()}
	 */
	public void setMultipartFileParameterName() {
		restFixture.setMultipartFileParameterName();
	}

	/**
	 * delegates to {@link RestFixture#getMultipartFileParameterName()}
	 * 
	 * @return the name of the parameter containing the multipart file to
	 *         upload.
	 */
	public String getMultipartFileParameterName() {
		return restFixture.getMultipartFileParameterName();
	}

	/**
	 * delegates to {@link RestFixture#setBody()}
	 */
	public void setBody() {
		restFixture.setBody();
	}

	public void setHeader() {
		restFixture.setHeader();
	}

	public void setHeaders() {
		restFixture.setHeaders();
	}

	public void PUT() {
		restFixture.PUT();
	}

	public void GET() {
		restFixture.GET();
	}

	public void DELETE() {
		restFixture.DELETE();
	}

	public void POST() {
		restFixture.POST();
	}

	public void HEAD() {
		restFixture.HEAD();
	}

	public void OPTIONS() {
		restFixture.OPTIONS();
	}

	public void TRACE() {
		restFixture.TRACE();
	}

	public void let() {
		restFixture.let();
	}

	public void comment() {
		restFixture.comment();
	}

	public void evalJs() {
		restFixture.evalJs();
	}

	public void processRow(RowWrapper<?> currentRow) {
		restFixture.processRow(currentRow);
	}

	public Map<String, String> getHeaders() {
		return restFixture.getHeaders();
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void doCells(Parse parse) {
		
		LOG.debug("Into Do cells");
		 log.log("debug inot cells");
		instantiateRestFixture();
		
		RowWrapper currentRow = new FitRow(parse);
		try {
			restFixture.processRow(currentRow);
		} catch (Exception exception) {
			LOG.error("Exception when processing row "
					+ currentRow.getCell(0).text(), exception);
			restFixture.getFormatter().exception(currentRow.getCell(0),
					exception);
		}
	}
	
	/**
	 * 
	 */
	protected void instantiateRestFixture() {
		if (restFixture == null) {
			
			Config config = Config.getConfig(getConfigNameFromArgs());
			String url = getBaseUrlFromArgs();
			
			
			restFixture = new MultipartRestFixture( url, config.getName() );
			restFixture.initialize(Runner.FIT);
			((FitFormatter) restFixture.getFormatter())
					.setActionFixtureDelegate(this);
		}
	}
	
	protected String getConfigNameFromArgs() {
		if (args.length >= 2) {
			return args[1];
		}
		return null;
	}
	
	protected String getBaseUrlFromArgs() {
		if (args.length > 0) {
			return args[0];
		}
		return null;
	}
	
	public Config getConfig() {
		return restFixture.getConfig();
	}


}
