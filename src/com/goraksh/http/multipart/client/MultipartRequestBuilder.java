package com.goraksh.http.multipart.client;

import java.io.File;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

/**
 * 
 * @author niteshk
 *
 */
public class MultipartRequestBuilder {

	/**
	 * Adds xml entity, file content in respective order
	 * 
	 * @param xmlEntity
	 * @param fileToUpload
	 * @return
	 */
	public MultipartRequest buildXmlJsonMixedPart(String xmlEntity,
			ContentType xmlContentType, File fileToUpload,
			ContentType fileContentType) {
		StringBody xmlBody = new StringBody(xmlEntity, xmlContentType);
		FileBody filebody = new FileBody(fileToUpload, fileContentType,
				fileToUpload.getName());

		MultipartMixedRequest mixedRequest = new MultipartMixedRequest();
		mixedRequest.addStringPart("XML/JSON", xmlBody);
		mixedRequest.addAttachmentPart("FILE", filebody);
		return mixedRequest;

	}
	
	/**
	 * 
	 * @param xmlEntity
	 * @param fileToUpload
	 * @return
	 */
	public MultipartRequest buildXmlMixedPart(String xmlEntity,File fileToUpload) {
		return buildXmlJsonMixedPart(xmlEntity, ContentType.APPLICATION_XML, fileToUpload, ContentType.APPLICATION_OCTET_STREAM );

	}
	
	/**
	 * 
	 * @param xmlEntity
	 * @param fileToUpload
	 * @return
	 */
	public MultipartRequest buildJsonMixedPart(String xmlEntity,File fileToUpload) {
		return buildXmlJsonMixedPart(xmlEntity, ContentType.APPLICATION_JSON, fileToUpload, ContentType.APPLICATION_OCTET_STREAM );

	}
	
	/**
	 * 
	 * @param xmlEntity
	 * @param fileToUpload
	 * @return
	 */
	public MultipartRequest buildFormDataMixedPart(String xmlEntity,File fileToUpload) {
		return buildFormDataMixedPart(xmlEntity, ContentType.APPLICATION_FORM_URLENCODED, fileToUpload, ContentType.APPLICATION_OCTET_STREAM );

	}

	
	/**
	 * 
	 * @param urlFormEncodedParams
	 * @param formContentType
	 * @param fileToUpload
	 * @param fileContentType
	 * @return
	 */
	public MultipartRequest buildFormDataMixedPart(
			String urlFormEncodedParams, ContentType formContentType,
			File fileToUpload, ContentType fileContentType) {
		StringBody xmlBody = new StringBody(urlFormEncodedParams,
				formContentType);
		FileBody filebody = new FileBody(fileToUpload, fileContentType,
				fileToUpload.getName());

		MultipartFormRequest formRequest = new MultipartFormRequest();
		formRequest.addFormPart("FORM-DATA", xmlBody);
		formRequest.addFormPart("FILE", filebody);
		return formRequest;

	}

}
