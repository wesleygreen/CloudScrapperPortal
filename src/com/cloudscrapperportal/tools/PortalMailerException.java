package com.cloudscrapperportal.tools;

public class PortalMailerException extends Exception {

	private static final long serialVersionUID = 1L;

	public PortalMailerException() {
		super("Unknown Exception");
	}

	public PortalMailerException(String errMessage) {
		super(errMessage);
	}
	
}
