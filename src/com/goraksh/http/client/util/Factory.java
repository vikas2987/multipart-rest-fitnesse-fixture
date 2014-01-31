package com.goraksh.http.client.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.NullArgumentException;

public class Factory {

	private static Map<Class<?>, MyLog> map;
	
	private static Factory instance = new Factory();
	
	static BufferedWriter bw;
	
	private Factory() {
		map = new HashMap<>();
		try {
			bw = new BufferedWriter( new FileWriter( new File("D:\\log.log")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("File Not Found" );
		}
	}
	
	public static MyLog get( Class<?> clz ) {
		MyLog log = map.get( clz );
		if ( log == null ) {
			log = new MyLog( bw );
		}
		return log;
	}
	
	public static void close() {
		try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("Cannot close");
		}
	}
}
