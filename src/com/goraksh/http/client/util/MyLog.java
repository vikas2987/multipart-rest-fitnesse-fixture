package com.goraksh.http.client.util;

import java.io.BufferedWriter;
import java.io.IOException;

public class MyLog {
	
	private BufferedWriter bw;
	private static String sep = System.getProperty("line.separator");
	
	MyLog( BufferedWriter bw ) {
		this.bw = bw;
	}
	
	public void log(String str ) {
		try {
			bw.write( sep + str );
			bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new NullPointerException("cannot write");
		}
	}

}
