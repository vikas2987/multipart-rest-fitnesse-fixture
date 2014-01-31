package com.goraksh.http.entity.mime;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Random;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.HttpMultipart;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import com.goraksh.http.entity.mime.content.AttachmentBodyPart;
import com.goraksh.http.entity.mime.content.MixedBodyPart;

/**
 * 
 * @author niteshk
 *
 */
public class MixedMultipartEntity implements HttpEntity {

    /**
     * The pool of ASCII chars to be used for generating a multipart boundary.
     */
    private final static char[] MULTIPART_CHARS =
        "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
            .toCharArray();

    private final HttpMultipart multipart;
    private final Header contentType;

     private long length;
    private volatile boolean dirty;     
    private MimeSubType subtype;

    
    public MixedMultipartEntity(
            HttpMultipartMode mode,
            String boundary,
            Charset charset, MimeSubType subtype) {
        super();
        if (boundary == null) {
            boundary = generateBoundary();
        }
        if (mode == null) {
            mode = HttpMultipartMode.STRICT;
        }
        
        this.dirty = true;
        this.subtype = subtype;
     
        
        this.multipart = new HttpMultipart(subtype.subtypeAsString(), charset, boundary, mode);
        this.contentType = new BasicHeader(
                HTTP.CONTENT_TYPE,
                generateContentType(boundary, charset));
           
    }

    /**
     * Creates an instance using the specified {@link HttpMultipartMode} mode.
     * Boundary and charset are set to {@code null}.
     * @param mode the desired mode
     */
    public MixedMultipartEntity(final HttpMultipartMode mode, MimeSubType subtype) {
        this(mode, null, null, subtype);
    }

    /**
     * Creates an instance using mode {@link HttpMultipartMode#STRICT}
     */
    public MixedMultipartEntity( MimeSubType subtype ) {
        this(HttpMultipartMode.STRICT, null, null, subtype);
       }

    protected String generateContentType(
            final String boundary,
            final Charset charset) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(getMimeType())
        .append("; boundary=");
        //.append("multipart/mixed; boundary=");
        buffer.append(boundary);
        if (charset != null) {
            buffer.append("; charset=");
            buffer.append(charset.name());
        }
        return buffer.toString();
    }
    
    private String getMimeType() {
    	return "multipart/" + subtype.subtypeAsString(); 
    }

    protected String generateBoundary() {
        StringBuilder buffer = new StringBuilder();
        Random rand = new Random();
        int count = rand.nextInt(11) + 30; // a random size from 30 to 40
        for (int i = 0; i < count; i++) {
            buffer.append(MULTIPART_CHARS[rand.nextInt(MULTIPART_CHARS.length)]);
        }
        return buffer.toString();
    }

    public void addPart(final FormBodyPart bodyPart) {
        this.multipart.addBodyPart(bodyPart);
        this.dirty = true;
    }

    public void addFormPart(final String name, final ContentBody contentBody) {
        addPart(new FormBodyPart(name, contentBody));
       
    }
    
    public void addMixedPart(final String name, final ContentBody contentBody) {
        addPart(new MixedBodyPart(name, contentBody));
        this.dirty = true;
       
    }
    
    public void addAttachmentPart(final String name, final ContentBody contentBody) {
        addPart(new AttachmentBodyPart(name, contentBody));
        this.dirty = true;
       
    }

    public boolean isRepeatable() {
        for (FormBodyPart part: this.multipart.getBodyParts()) {
            ContentBody body = part.getBody();
            if (body.getContentLength() < 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isChunked() {
        return !isRepeatable();
    }

    public boolean isStreaming() {
        return !isRepeatable();
    }

    public long getContentLength() {
        if (this.dirty) {
            this.length = this.multipart.getTotalLength();
            this.dirty = false;
        }
        return this.length;
    }

    public Header getContentType() {
        return this.contentType;
    }

    public Header getContentEncoding() {
        return null;
    }

    public void consumeContent()
        throws IOException, UnsupportedOperationException{
        if (isStreaming()) {
            throw new UnsupportedOperationException(
                    "Streaming entity does not implement #consumeContent()");
        }
    }

    public InputStream getContent() throws IOException, UnsupportedOperationException {
        throw new UnsupportedOperationException(
                    "Multipart form entity does not implement #getContent()");
    }

    public void writeTo(final OutputStream outstream) throws IOException {
        this.multipart.writeTo(outstream);
    }

}
