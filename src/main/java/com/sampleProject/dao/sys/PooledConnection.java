package com.sampleProject.dao.sys;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

public class PooledConnection
{
	private Connection connection = null;
	private boolean busy;
	
	public PooledConnection(Connection connection)
	{
		this.connection = connection;
	}
	
	public ResultSet executeQuery(String sql) throws SQLException
	{
		return connection.createStatement().executeQuery(sql);
	}
	
	public int executeUpate(String sql) throws SQLException
	{
		return connection.createStatement().executeUpdate(sql);
	}
	
	public Connection getConnection()
	{
		return connection;
	}
	public void setConnection(Connection connection)
	{
		this.connection = connection;
	}
	
	public PreparedStatement prepareStatement(String sql) throws SQLException
	{
		return (PreparedStatement) connection.prepareStatement(sql);
	}
	
	public boolean isBusy()
	{
		return busy;
	}
	public void setBusy(boolean value)
	{
		this.busy = value;
	}
	
	public void setAutoCommit(boolean flag) throws SQLException
	{
		connection.setAutoCommit(flag);
	}
	
	public void commit() throws SQLException
	{
		connection.commit();
	}
	
	public void rollback() throws SQLException
	{
		connection.rollback();
	}
	
	public void close()
	{
		busy = false;
	}

}
