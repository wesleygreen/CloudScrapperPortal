package com.cloudscrapperportal.app;

public class PortalUser {
	
	private String email;
	private String password;
	private String fName;
	private String lName;
	private String sType;
	private String CSRFToken;
	
	public PortalUser() {
		this.email = "";
		this.password = "";
		this.fName = "";
		this.lName = "";
		this.sType = "";
		this.CSRFToken = "";
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getsType() {
		return sType;
	}

	public void setsType(String sType) {
		this.sType = sType;
	}
	
	public String getCSRFToken() {
		return CSRFToken;
	}

	public void setCSRFToken(String cSRFToken) {
		this.CSRFToken = cSRFToken;
	}
	
	/*
	 * TODO - update this for the new datasource connection 

	public void loginUser(DataSource dataSource) throws CREUserException {
		if ("".equals(this.email)) throw new CREUserException("Login error: Email not set");
		if ("".equals(this.password)) throw new CREUserException("Login error: Password not set");

		String uuid = UUID.randomUUID().toString();
		String [] params = new String [3];
		params[0] = this.email;
		params[1] = this.password; //password must be encrypted
		params[2] = uuid;

		try {
			ResultSet rs = DBTools.query(params, dataSource, DBTools.LOGIN_USER);
			while (rs.next()) {
				this.fName = rs.getString(1);
				this.lName = rs.getString(2);
			}
			rs.close();
		} catch (DBToolsException e) {
			throw new CREUserException(e.getMessage());
		} catch (SQLException e) {
			throw new CREUserException(e.getMessage());
		}
	}
	
	*/

}
