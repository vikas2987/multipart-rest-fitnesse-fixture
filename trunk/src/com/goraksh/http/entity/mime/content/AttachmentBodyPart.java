package com.goraksh.http.entity.mime.content;

import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.MIME;
import org.apache.http.entity.mime.content.ContentBody;

/**
 * 
 * @author niteshk
 *
 */
public class AttachmentBodyPart  extends FormBodyPart {
	
	public AttachmentBodyPart(String name, ContentBody body) {
		super(name, body);
	}

	protected void generateContentDisp(final ContentBody body) {
		 StringBuilder buffer = new StringBuilder();
		        // buffer.append("form-data; name=\"");
		        // buffer.append(getName());
		        // buffer.append("\"");
		 buffer.append( "attachment" );
		         if (body.getFilename() != null) {
		             buffer.append("; filename=\"");
		              buffer.append(body.getFilename());
		              buffer.append("\"");
		          }
		          addField(MIME.CONTENT_DISPOSITION, buffer.toString());
	}
	
}
