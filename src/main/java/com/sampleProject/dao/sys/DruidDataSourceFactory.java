package com.sampleProject.dao.sys;

import java.util.Properties;

import javax.sql.DataSource;

//import org.apache.ibatis.datasource.DataSourceFactory;
import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;

import com.alibaba.druid.pool.DruidDataSource;

public class DruidDataSourceFactory extends PooledDataSourceFactory
{
	private Properties props;
	private static DruidDataSource ds = null;
	
	public void setProperties(Properties properties)
	{
		this.props = properties;
	}
	
	public DataSource getDataSource()
	{
		if(ds==null)
		{
			ds = new DruidDataSource();
			
			ds.setDriverClassName(this.props.getProperty("driver"));
	        ds.setUrl(this.props.getProperty("url"));
	        ds.setUsername(this.props.getProperty("username"));
	        ds.setPassword(this.props.getProperty("password"));
			
	        String value = "";
	        
	        value = this.props.getProperty("initialSize");
	        if(value != null)
	        {
	        	ds.setInitialSize(Integer.parseInt(value));
	        }
	        
	        value = this.props.getProperty("minIdle");
	        if(value != null)
	        {
	        	ds.setMinIdle (Integer.parseInt(value));
	        }
	        
	        value = this.props.getProperty("maxActive");
	        if(value != null)
	        {
	            ds.setMaxActive(Integer.parseInt(value));
	        }
	        
	        value = this.props.getProperty("maxWait");
	        if(value != null)
	        {
	            ds.setMaxWait(Long.parseLong(value));
	        }
	        
	        value = this.props.getProperty("timeBetweenEvictionRunsMillis");
	        if(value != null)
	        {
	            ds.setTimeBetweenEvictionRunsMillis(Long.parseLong(value));
	        }
	        
	        value = this.props.getProperty("minEvictableIdleTimeMillis");
	        if(value != null)
	        {
	            ds.setMinEvictableIdleTimeMillis(Long.parseLong(value));
	        }
	        
	        value = this.props.getProperty("validationQuery");
	        if(value != null)
	        {
	            ds.setValidationQuery(value);
	        }
	        
	        value = this.props.getProperty("testWhileIdle");
	        if(value != null)
	        {
	            ds.setTestWhileIdle(Boolean.parseBoolean(value));
	        }
	        
	        value = this.props.getProperty("testOnBorrow");
	        if(value != null)
	        {
	            ds.setTestOnBorrow(Boolean.parseBoolean(value));
	        }
	        
	        value = this.props.getProperty("testOnReturn");
	        if(value != null)
	        {
	            ds.setTestOnReturn(Boolean.parseBoolean(value));
	        }
	        
	        value = this.props.getProperty("keepAlive");
	        if(value != null)
	        {
	            ds.setKeepAlive(Boolean.parseBoolean(value));
	        }
	        
	        value = this.props.getProperty("removeAbandoned");
	        if(value != null)
	        {
	            ds.setRemoveAbandoned(Boolean.parseBoolean(value));
	        }
	        
	        value = this.props.getProperty("removeAbandonedTimeout");
	        if(value != null)
	        {
	            ds.setRemoveAbandonedTimeout(Integer.parseInt(value));
	        }
	        
	        value = this.props.getProperty("logAbandoned");
	        if(value != null)
	        {
	            ds.setLogAbandoned(Boolean.parseBoolean(value));
	        }
	        
	        value = this.props.getProperty("poolPreparedStatements");
	        if(value != null)
	        {
	            ds.setPoolPreparedStatements(Boolean.parseBoolean(value));
	        }
	        
	        value = this.props.getProperty("maxPoolPreparedStatementPerConnectionSize");
	        if(value != null)
	        {
	            ds.setMaxPoolPreparedStatementPerConnectionSize(Integer.parseInt(value));
	        }
	        
	        value = this.props.getProperty("useGlobalDataSourceStat");
	        if(value != null)
	        {
	        	ds.setUseGlobalDataSourceStat(Boolean.parseBoolean(value));
	        }
	        
	        value = this.props.getProperty("stat");
	        if(value != null)
	        {
	            try
	    		{
	            	ds.setFilters(this.props.getProperty("filters")); 
	    		} 
	            catch (Exception e)
	    		{
	    			//e.printStackTrace();
	    		}
	        }
	   
	        
			try
			{
				ds.init();
				//System.out.println("init ds");
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			//System.out.println("old ds");
		}
		
		return ds;
	}

}
