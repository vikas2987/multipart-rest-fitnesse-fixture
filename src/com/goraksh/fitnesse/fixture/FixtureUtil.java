package com.goraksh.fitnesse.fixture;

import java.io.File;

import com.goraksh.http.client.util.TextReader;

import com.goraksh.http.client.util.Factory;
import com.goraksh.http.client.util.MyLog;

import smartrics.rest.fitnesse.fixture.PartsFactory;
import smartrics.rest.fitnesse.fixture.support.Config;

/**
 * 
 * @author niteshk
 *
 */
public class FixtureUtil {
	
	private static final MyLog log = Factory.get( FixtureUtil.class);
	public static boolean checkIfFileExist( File toUpload ) {
		
		log.log("File name: " + toUpload.getAbsolutePath() );
		TextReader reader = new TextReader();
		
		try {
		byte[] by = reader.getContentByteArray(toUpload);
		log.log("File content: " + new String( by ));
		} catch ( Exception e ) {
			log.log("Error in file reading: " + e.toString());
			return false;
		}
		
		if ( !toUpload.exists() ) return false;
		if ( toUpload.isDirectory() ) return false;
		if ( !toUpload.isFile() ) return false;
		
		return true;
    	
    }
	
	public static PartsFactory instantiateFactory(Config config) {
		String partFactory = config
				.get(FixtureConstants.PARTS_FACTORY);
		try {
			Class<PartsFactory> clazz = (Class<PartsFactory>) Class
					.forName(partFactory);
			return clazz.newInstance();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			throw new IllegalStateException("Could not instantiate class :"
					+ partFactory);
		} catch (InstantiationException e) {
			throw new IllegalStateException("Could not instantiate class :"
					+ partFactory);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException("Could not instantiate class :"
					+ partFactory);
		}
	}

}
