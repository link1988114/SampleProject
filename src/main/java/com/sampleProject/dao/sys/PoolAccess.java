package com.sampleProject.dao.sys;

import java.util.Vector;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;


public class PoolAccess
{
	private String jdbcDriver = "";
	private String dbURL = "";
	private String username = "";
	private String pw = "";
	
	private int initialConnections = 1;
	private int increaseConnections = 5;
	private int maxConnections = 200;
	
	private Vector<PooledConnection> connections = null;
	
	public PoolAccess(String jdbcDriver,String dbURL, String username,String pw)
	{
		this.jdbcDriver = jdbcDriver;
		this.dbURL = dbURL;
		this.username = username;
		this.pw = pw;
		try
		{
			createPool();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public synchronized void createPool() throws Exception
	{
		if(connections != null)
		{
			return;
		}
		Driver driver = (Driver)Class.forName(this.jdbcDriver).newInstance();
		DriverManager.registerDriver(driver);
		connections = new Vector<PooledConnection>();
		createConnections(this.initialConnections);
	}
	
	private void createConnections(int numConnections) throws SQLException
	{
		for(int i=0;i<numConnections;i++)
		{
			if(this.maxConnections >0 && this.connections.size() >= this.maxConnections)
			{
				System.out.println("connection pool is full");
				break;
			}
			try
			{
				connections.addElement(new PooledConnection(newConnection()));
			} 
			catch (Exception e)
			{
				System.out.println("connect db failed");
			}
		}
	}
	
	public synchronized PooledConnection getConnection() throws SQLException
	{
		if(connections==null)
		{
			return null;
		}
		PooledConnection conn = getFreeConnection();
		while(conn==null)
		{
			wait(250);
			conn = getFreeConnection();
		}
		return conn;
	}
	
	private Connection newConnection() throws SQLException
	{
		String connString = dbURL+"?user="+username+"&password="+pw+"&UseUnicode=true&characterEncoding=utf8";
		Connection conn = null;
		if(jdbcDriver.contains("com.microsoft.sqlserver") || jdbcDriver.contains("oracle.jdbc.driver.OracleDriver"))
		{
			conn = DriverManager.getConnection(dbURL,username,pw);
		}
		else
		{
			conn = DriverManager.getConnection(connString);
		}
		
		if(connections.size()==0)
		{
			DatabaseMetaData metaData = conn.getMetaData();
			int driverMaxConnections = metaData.getMaxConnections();
			if(driverMaxConnections>0 && this.maxConnections>driverMaxConnections)
			{
				this.maxConnections = driverMaxConnections;
			}
		}
		return conn;
	}
	
	private PooledConnection getFreeConnection() throws SQLException
	{
		PooledConnection conn =findFreeConnection();
		if(conn==null)
		{
			createConnections(increaseConnections);
			conn = findFreeConnection();
			if(conn==null)
			{
				return null;
			}
		}
		return conn;
	}
	
	private PooledConnection findFreeConnection() throws SQLException
	{
		for(int i=0;i<connections.size();i++)
		{
			PooledConnection connPool = connections.elementAt(i);
			if(!connPool.isBusy())
			{
				Connection conn = connPool.getConnection();
				connPool.setBusy(true);
				if(!isValid(conn))
				{
					try
					{
						conn = newConnection();
						connPool.setConnection(conn);
					} 
					catch (Exception e)
					{
						e.printStackTrace();
						connections.remove(i--);
						continue;
					}
				}
				return connPool;
			}
		}
		return null;
	}
	
	private boolean isValid(Connection conn)
	{
		try
		{
			return conn.isValid(3000);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	public void returnConnection(Connection conn)
	{
		if(connections == null)
		{
			return;
		}
		PooledConnection pConn = null;
		Enumeration<PooledConnection> enumerate = connections.elements();
		while (enumerate.hasMoreElements())
		{
			pConn = (PooledConnection)enumerate.nextElement();
			if(conn==pConn.getConnection())
			{
				pConn.setBusy(false);
				break;
			}		
		}
	}
	
	public synchronized void refreshConnetions() throws SQLException
	{
		if(connections == null)
		{
			return;
		}
		PooledConnection pConn = null;
		Enumeration<PooledConnection> enumerate = connections.elements();
		while(enumerate.hasMoreElements())
		{
			pConn = (PooledConnection)enumerate.nextElement();
			if(pConn.isBusy())
			{
				wait(5000);
			}
			closeConnection(pConn.getConnection());
			pConn.setConnection(newConnection());
			pConn.setBusy(false);
		}
	}
	
	public synchronized void closeConnectoinPool()
	{
		if(connections==null)
		{
			return ;
		}
		PooledConnection pConn = null;
		Enumeration<PooledConnection> enumerate = connections.elements();
	    while(enumerate.hasMoreElements())
	    {
	    	pConn = (PooledConnection)enumerate.nextElement();
	    	if(pConn.isBusy())
	    	{
	    		wait(5000);
	    	}
	    	closeConnection(pConn.getConnection());
	    	connections.removeElement(pConn);
	    }
	    connections = null;
	}
	
	private void closeConnection(Connection conn)
	{
		try
		{
			conn.close();
		} 
		catch (Exception e)
		{
			System.out.println("close error");
		}
	}
	
	private void wait(int mSeconds)
	{
		try
		{
			Thread.sleep(mSeconds);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public int getinitialConnections()
	{
		return this.initialConnections;
	}
	public void setinitialConnections(int value)
	{
		this.initialConnections = value;
	}
	
	public int getincreaseConnections()
	{
		return this.increaseConnections;
	}
	public void setincreaseConnections(int value)
	{
		this.increaseConnections = value;
	}
	
	public int getmaxConnections()
	{
		return this.maxConnections;
	}
	public void setmaxConnections(int value)
	{
		this.maxConnections = value;
	}

}
