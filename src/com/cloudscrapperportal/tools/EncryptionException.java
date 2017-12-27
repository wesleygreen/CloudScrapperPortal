package com.cloudscrapperportal.tools;

public class EncryptionException extends Exception {

	private static final long serialVersionUID = 1L;

	public EncryptionException() {
		super("Unknown Exception");
	}

	public EncryptionException(String errMessage) {
		super(errMessage);
	}

	
	
}
