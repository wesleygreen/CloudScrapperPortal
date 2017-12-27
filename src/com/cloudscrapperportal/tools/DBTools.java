package com.cloudscrapperportal.tools;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class DBTools {
	
	public static final int ADD_USER = 1;
	public static final int VERIFY = 2;
	public static final int LOGIN_USER = 3;
	
	public static void update(String [] params, DataSource dataSource, int type) throws  DBToolsException{
		Connection con;
		CallableStatement cs;
			try {		
				con = dataSource.getConnection();
				switch (type) {
				
					case ADD_USER:
						if (params.length!=6) throw new DBToolsException ("Inccorrect Parameters for login");
						cs = con.prepareCall("{call sp_addUser (?,?,?,?,?,?)}"); 
						cs.setString(1, params[0]);
						cs.setString(2, params[1]);
						cs.setString(3, params[2]);
						cs.setString(4, params[3]);
						cs.setString(5, params[4]);
						cs.setString(6, params[5]);						
						break;
					
					case VERIFY:
						if (params.length!=1) throw new DBToolsException ("Inccorrect Parameters for verification");
						cs = con.prepareCall("{call sp_verifyUser (?)}"); 
						cs.setString(1, params[0]);						
						break;

						
					default:
						throw new DBToolsException("Invalid update type");
						
				}
				
				cs.executeUpdate();
				cs.close();
				con.close();
				
				
			} catch (SQLException ex) {
				throw new DBToolsException(ex.getMessage());
			} catch (NullPointerException e) {
				throw new DBToolsException("Error getting database connection: " + e.getMessage());
			}
		
	}
	
	public static ResultSet query (String [] params, DataSource dataSource, int type) throws DBToolsException{		
		Connection con;
		CallableStatement cs;
		try {	
			con = dataSource.getConnection();
			switch (type) {
			
				case LOGIN_USER:
					if (params.length!=3) throw new DBToolsException("Incorrect number of parameters for GET_ANSWERS");
					cs = con.prepareCall("{call sp_loginUser (?,?,?)}");
					cs.setString(1, params[0]);
					cs.setString(2, params[1]);
					cs.setString(3, params[2]);
					break;
					
				default:
					throw new DBToolsException("Invalid query type");
					
			}	
			ResultSet rs = cs.executeQuery();
			con.close();
			return rs;
		} catch (SQLException ex) {
			throw new DBToolsException("Login Error: " + ex.getMessage());
		} catch (NullPointerException e) {
			throw new DBToolsException("Error getting database connection: " + e.getMessage());
		}
		
	}
	
}
