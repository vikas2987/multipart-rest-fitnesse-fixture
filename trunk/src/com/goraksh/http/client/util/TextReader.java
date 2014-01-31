package com.goraksh.http.client.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * 
 * @author niteshk
 *
 */
public class TextReader
{

	public byte[] getByteArrayFromInputStream(InputStream in) throws IOException
	{
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] data = new byte[4096];

		while ((nRead = in.read(data, 0, data.length)) != -1)
			buffer.write(data, 0, nRead);

		buffer.flush();
		buffer.close();
		return buffer.toByteArray();
	}

	public String getString(byte[] bytes) throws UnsupportedEncodingException
	{
		String s = new String(bytes, "ISO-8859-1");
		
		return s;
	}

	private InputStream getInputStream(String filename) throws FileNotFoundException
	{
		return getInputStream(new File(filename));
	}
	
	private InputStream getInputStream(File filename) throws FileNotFoundException
	{
		FileInputStream in = new FileInputStream(filename) ;
		return in;
	}

	public byte[] getContentByteArray(String filename) throws IOException
	{
		return getByteArrayFromInputStream( getInputStream (filename));
	}
	
	public byte[] getContentByteArray(File filename) throws IOException
	{
		return getByteArrayFromInputStream( getInputStream (filename));
	}
	
	public static void main( String[] args ) {
		String s = "/system/ws/v12/internal/stream/0b5cd3c5-c070-4bfc-a748-920aa5e0feb7";
		
		System.out.println( s.substring( s.indexOf("eam/") + 4 ));
	}
}
