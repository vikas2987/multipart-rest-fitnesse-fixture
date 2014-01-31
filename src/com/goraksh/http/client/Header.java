package com.goraksh.http.client;

import java.util.*;
import java.util.Map.Entry;

/**
 * ]
 * @author niteshk
 *
 */
public class Header {

	private Map<String, String> headerMap;
	private org.apache.http.Header[] header;

	public Header() {
		this.headerMap = new HashMap<>();
	}
	
	public Header( org.apache.http.Header[] header ) {
		this( );	
		if ( header == null || header.length == 0 )
			return;
		
		for( org.apache.http.Header h : header ) {
			add( h );
		}
	}

	public void add(String name, String value) {
		this.headerMap.put(name, value);
	}
	
	public void add( org.apache.http.Header header ) {
		add( header.getName(), header.getValue() );
	}
	
	public Set<Entry<String, String>> getEntrySet() {
		return this.headerMap.entrySet();
	}
	
	public Set<String> keyset() {
		return this.headerMap.keySet();
	}
	
	public String get( String key ) {
		return headerMap.get( key );
	}

}
