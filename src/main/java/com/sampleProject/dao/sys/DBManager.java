package com.sampleProject.dao.sys;


public class DBManager
{
	private static PooledConnection conn;
	private static PoolAccess connectionPool;
	private static DBManager instance = null;
	
	//private static String jdbcDriver = "com.mysql.jdbc.Driver";   //mysql
	//private static String dbURL = "jdbc:mysql://XXXXXX/test";  //mysql
	
	//private static String jdbcDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";   //  sql server
	//private static String dbURL="jdbc:sqlserver://XXXXX:port; DatabaseName=XXXX";  //sql server
	
	//private static String jdbcDriver = "oracle.jdbc.driver.OracleDriver"; //oracle
	//private static String dbURL="jdbc:oracle:thin:@XXXXX:port:sid";  //oracle
	
	private static String jdbcDriver = "com.mysql.jdbc.Driver";
	private static String dbURL = "jdbc:mysql://localhost:3306/test";
	private static String username = "root";
	private static String pw = "root";
	
	
	public DBManager()
	{
		if(instance != null)
		{
			return;
		}
		connectionPool = new PoolAccess(jdbcDriver, dbURL, username, pw);
		try
		{
			connectionPool.createPool();
			instance = this;
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static PooledConnection getConnection()
	{
		if(instance == null)
		{
			new DBManager();
		}
		try
		{
			conn = connectionPool.getConnection();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return conn;
	}
	
	public void close()
	{
		try
		{
			connectionPool.closeConnectoinPool();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
