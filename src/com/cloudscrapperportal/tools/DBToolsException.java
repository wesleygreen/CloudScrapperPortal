package com.cloudscrapperportal.tools;

public class DBToolsException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public DBToolsException() {
		super("Unknown Exception");
	}

	public DBToolsException(String errMessage) {
		super(errMessage);
	}

}
