package com.cloudscrapperportal.app;

public class PortalUserException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public PortalUserException() {
		super("Unknown Exception");
	}

	public PortalUserException(String errMessage) {
		super(errMessage);
	}

}
