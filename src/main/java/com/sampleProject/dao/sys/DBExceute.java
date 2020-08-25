package com.sampleProject.dao.sys;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sampleProject.util.Stringfilter;



public class DBExceute
{
	//select
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List executeQuery(String sql, String[] params, Class<?> modelclass)
	{
		List result = new ArrayList();
		ResultSet rs = null;
		PreparedStatement ps = null;
		PooledConnection conn = null;
		conn = DBManager.getConnection();
		
		try
		{
			ps = conn.prepareStatement(sql);
			for(int i=0; i<params.length; i++)
			{
				ps.setString(i+1, params[i]);
			}
			rs = ps.executeQuery();
			
			int size = rs.getMetaData().getColumnCount();
			while(rs.next())
			{
			    Object temp = modelclass.newInstance();
				for (int i=1; i<=size; i++)
				{
					
					String key = rs.getMetaData().getColumnLabel(i);
					String content = rs.getString(i);	
					Method m = modelclass.getMethod("set"+Stringfilter.upperCapital(key), String.class);
					m.invoke(temp, content);
				}
				result.add(temp);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			result = new ArrayList();
		}
		finally
		{
			if(conn != null)
			{
				conn.close();
			}
		}
		return result;
	}
	
	//select limit ?,?
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List executePageQuery(String sql, String[] params, Class<?> modelclass)
	{
		List result = new ArrayList();
		ResultSet rs = null;
		PreparedStatement ps = null;
		PooledConnection conn = null;
		conn = DBManager.getConnection();
		
		try
		{
			ps = conn.prepareStatement(sql);
			for(int i=0; i<params.length; i++)
			{
				if(i>=params.length-2)
				{
					ps.setInt(i+1, Integer.parseInt(params[i]));
				}
				else
				{
					ps.setString(i+1, params[i]);
				}
			}
			rs = ps.executeQuery();
			
			int size = rs.getMetaData().getColumnCount();
			while(rs.next())
			{
			    Object temp = modelclass.newInstance();
				for (int i=1; i<=size; i++)
				{
					
					String key = rs.getMetaData().getColumnLabel(i);
					String content = rs.getString(i);	
					Method m = modelclass.getMethod("set"+Stringfilter.upperCapital(key), String.class);
					m.invoke(temp, content);
				}
				result.add(temp);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			result = null;
		}
		finally
		{
			if(conn != null)
			{
				conn.close();
			}
		}
		return result;
	}
	
	//select count
	public int getCount(String sql,String[] params)
	{
		int result = 0;
		ResultSet rs = null;
		PreparedStatement ps = null;
		PooledConnection conn = null;
		conn = DBManager.getConnection();
		try
		{
			ps = conn.prepareStatement(sql);
			for(int i=0;i<params.length;i++)
			{
				ps.setString(i+1, params[i]);
			}
			rs = ps.executeQuery();
			if(rs.next())
			{
				result = rs.getInt(1);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(conn != null)
			{
				conn.close();
			}
		}
		return result;
	}
	
	
	//insert update
	public int executeModify(String sql, String[] params)
	{
		int result = 0;
		PreparedStatement ps = null;
		PooledConnection conn = null;
		conn = DBManager.getConnection();
		
		try
		{
			ps = conn.prepareStatement(sql);
			for(int i=0; i<params.length; i++)
			{
				ps.setString(i+1, params[i]);
			}
			result = ps.executeUpdate();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			result = -1;
		}
		finally
		{
			if(conn != null)
			{
				conn.close();
			}
		}
		return result;
	}
	
	
	//load in file by stream
	public String loadInByStream(String sql, InputStream is)
	{
		String result = "error";
		
		if(is==null)
		{
			return "no data";
		}
		
		PreparedStatement ps = null;
		PooledConnection conn = null;
		conn = DBManager.getConnection();
		try
		{
			ps = conn.prepareStatement(sql);
			if (ps.isWrapperFor(com.mysql.jdbc.Statement.class)) 
			{  
				com.mysql.jdbc.PreparedStatement ps2 = ps.unwrap(com.mysql.jdbc.PreparedStatement.class);
				ps2.setLocalInfileInputStream(is);  
	            result = ps2.executeUpdate()+"";
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			result = "error";
		}
		return result;
	}

}
