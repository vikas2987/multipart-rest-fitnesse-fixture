package com.goraksh.http.multipart.client;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.goraksh.http.client.KbBaseHttpClient;
import com.goraksh.http.client.util.TextReader;
import com.goraksh.http.entity.mime.MimeSubType;
import com.goraksh.http.entity.mime.MixedMultipartEntity;
import com.goraksh.http.resource.AttachmentUriInfo;
import com.goraksh.http.resource.KbAttachment;

/**
 * Example how to use multipart/form encoded POST request.
 */
public class MultipartPost2 {
	
	public void simpleTest( KbAttachment attachment, String internalAttachemntUrl, File toUpload, KbBaseHttpClient baseClient ) throws ClientProtocolException, IOException {
       
      	CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost( internalAttachemntUrl );

            
            TextReader reader = new TextReader();

    		byte[] bytesToImport = reader.getContentByteArray(toUpload);

    		
    		//Byte
    		//ByteArrayBody byt = new ByteArrayBody( bytesToImport, ContentType.DEFAULT_BINARY, toUpload.getPath() );
    		FileBody byt = new FileBody( toUpload, ContentType.TEXT_PLAIN, toUpload.getName() );
            //StringBody xmlBody = new StringBody( attachment.toString(), ContentType.APPLICATION_XML );
            StringBody xmlBody = new StringBody( attachment.toString(), ContentType.APPLICATION_XML );
            
            ContentType type = ContentType.APPLICATION_XML;

            httppost.setHeader( baseClient.getXEgainKey(),  baseClient.getXEgainHeader() );
                                       
                       
            MixedMultipartEntity reqEntity = new MixedMultipartEntity( MimeSubType.MIXED );
            reqEntity.addMixedPart("entity",  xmlBody );
            reqEntity.addAttachmentPart("FILE", byt);
            		          		
                   
            System.out.println( reqEntity.getContentType() );

            httppost.setEntity(reqEntity);

            System.out.println("\nRequest URI:\n " + httppost.getRequestLine());
            System.out.println("\nRequest XML Body\n" + attachment.toString());
            
            CloseableHttpResponse response = httpclient.execute(httppost);
            HttpEntity resEntity;
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                resEntity = response.getEntity();
                if (resEntity != null) {
                    System.out.println("Response content length: " + resEntity.getContentLength());
                    System.out.println( resEntity);
                }
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
             }
        } finally {
            httpclient.close();
        }
    }

    public static void main(String[] args) throws Exception {
    	
    	String path = "/kb/attachment/internal";
		String host = "ggnv64w4";
		String port = "9001";

		String loginUser = "kbagent1";
		String loginPwd = "egain123";

		String loginPath = "/user/login";

		String logoutPath = "/user/logout";
		
		String attachmentResource =  "/kb/attachment/internal" ;
		
		String baseFilePath = "D:\\qa-testcase";
		
		String s = null;
		
		System.out.println("hello" + s );

		KbBaseHttpClient client = new KbBaseHttpClient(host, port, loginUser,
				loginPwd, loginPath, logoutPath);
		
		
		
		client.login();
		System.out.println("Logged in Succesfully. Will send post to internal attachments\n\n");
		MultipartPost2 multipart = new MultipartPost2();
		multipart.internalAttachmentTest1( client, attachmentResource, baseFilePath );

		System.out.println("\n\nWill now logout");
		client.logout();
		
		
		System.out.println("Again=====================");
		client.login();
		System.out.println("Logged in Succesfully. Will send post to internal attachments\n\n");
		internalAttachmentTest2( client, attachmentResource, baseFilePath );

		System.out.println("\n\nWill now logout");
		client.logout();
		

    }
    
    public static void internalAttachmentTest2(KbBaseHttpClient client, String attachmentResourceUrl, String baseFilePath ) {
    	long articleId = 201400000002063l;
    	String lang = "nl_nl";
    	String attribute = "all";
    	
    	String attachmentName = "attach-1";
    	String filename = "hello.txt";
    	
    	AttachmentUriInfo uriInfo = new AttachmentUriInfo();
    	uriInfo.setArticleId(articleId);
    	uriInfo.setLang(lang);
    	uriInfo.setAttribute(attribute);
    	
    	String internalAttachemntUrl  = uriInfo.getCompleteUri( client.getBaseUrl() + attachmentResourceUrl );
    	KbAttachment attachment = new KbAttachment();
    	attachment.setName( attachmentName );
    	
    	
    	File toUpload = new File( baseFilePath + "\\" + filename );
    	
    	checkIfFileExist( toUpload );
    	
    	try {
    		doPost(attachment, internalAttachemntUrl, toUpload, client);
    	} catch ( Exception e ) {
    		e.printStackTrace();
    	}
    }
    
    public static void doPost( KbAttachment attachment, String internalAttachemntUrl, File toUpload, KbBaseHttpClient baseClient ) throws ClientProtocolException, IOException {
    	com.goraksh.http.client.Header header = new com.goraksh.http.client.Header();
    	header.add( baseClient.getXEgainKey(),  baseClient.getXEgainHeader() );
    	
    	MultipartRequestBuilder builder = new MultipartRequestBuilder();
    	MultipartRequest request = builder.buildXmlMixedPart( attachment.toString(), toUpload );
    	
    	MultipartPost multipartPost = new MultipartPost(request, header, internalAttachemntUrl);
    	MultipartResponse response = multipartPost.postAndClose();
    	
    	System.out.println( "Request URI: " + response.getRequestLine() );
    	System.out.println("Resposne Status line :" + response.getStatusLine() );
    	
    	System.out.println("Response Content: " + response.getContent() );
    }
    
    
    private void internalAttachmentTest1( KbBaseHttpClient client, String attachmentResourceUrl, String baseFilePath ) {
    	long articleId = 201400000002063l;
    	String lang = "nl_nl";
    	String attribute = "all";
    	
    	String attachmentName = "attach-1";
    	String filename = "hello.txt";
    	
    	AttachmentUriInfo uriInfo = new AttachmentUriInfo();
    	uriInfo.setArticleId(articleId);
    	uriInfo.setLang(lang);
    	uriInfo.setAttribute(attribute);
    	
    	String internalAttachemntUrl  = uriInfo.getCompleteUri( client.getBaseUrl() + attachmentResourceUrl );
    	KbAttachment attachment = new KbAttachment();
    	attachment.setName( attachmentName );
    	
    	
    	File toUpload = new File( baseFilePath + "\\" + filename );
    	
    	checkIfFileExist( toUpload );
    	
    	try {
    	simpleTest(attachment, internalAttachemntUrl, toUpload, client);
    	} catch ( Exception e ) {
    		e.printStackTrace();
    	}
    }
    
    private static void checkIfFileExist( File toUpload ) {
    	TextReader reader = new TextReader();
    	try {
    	byte[] arr = reader.getContentByteArray( toUpload );
    	
    	System.out.println( new String( arr ));
    	} catch ( Exception e ) {
    		e.printStackTrace();
    	}
    	
    }

}

