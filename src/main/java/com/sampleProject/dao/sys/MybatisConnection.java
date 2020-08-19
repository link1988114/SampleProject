package com.sampleProject.dao.sys;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisConnection
{
	private String resource = "";
	private SqlSession session = null;
	
	public MybatisConnection(String resource)
	{
		this.resource = resource;
	}
	
	public MybatisConnection()
	{
		this.resource = "conf.xml";
	}
	
	/*
	public SqlSessionFactory startSession()
	{
		InputStream is =  cLoader.getResourceAsStream(resource);	
		SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(is);
		return sf;
	}
	*/
	
	public SqlSession openSession()
	{
		try
		{
			InputStream is = Resources.getResourceAsStream(resource);	
			SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(is);
			session = sf.openSession();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			closeSession();
		}
		
		return session;
	}
	
	public SqlSession openSession(boolean autoCommit)
	{
		try
		{
			InputStream is =  Resources.getResourceAsStream(resource);	
			SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(is);
			session = sf.openSession(autoCommit);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			closeSession();
		}
		
		return session;
	}
	
	public SqlSession openBatSession()
	{
		try
		{
			InputStream is =  Resources.getResourceAsStream(resource);	
			SqlSessionFactory sf = new SqlSessionFactoryBuilder().build(is);
			session = sf.openSession(ExecutorType.BATCH, false);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			closeSession();
		}
		return session;
	}
	
	public SqlSession getSession()
	{
		return session;
	}
	
	public void closeSession()
	{
		if(session != null)
		{
			session.clearCache();
			session.close();
		}
	}

	public int insert(String statementID, Object modelObj)
	{
		int result = -1;
		try
		{
			openSession();
			result = session.insert(statementID, modelObj);
			session.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			if(session != null){session.rollback();}
		}
		finally
		{
			closeSession();
		}
		return result;
	}
	
	public int batchInsert(String statementID, Object[] modelObjs)
	{
		int result = 0;
//		int count=0;
		
		try
		{
			openBatSession();
//			for(Object obj : modelObjs)
//			{
//				result += session.insert(statementID, obj);
//				count++;
//				if(count>=10000)
//				{
//					session.commit();
//					session.clearCache();
//					count=0;
//				}
//			}
			
			for(Object obj : modelObjs)
			{
				session.insert(statementID, obj);
				result++;
			}

			session.commit();
			session.clearCache();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			if(session != null){session.rollback();}
			result = -1;
		}
		finally
		{
			closeSession();
		}
		
		return result;
	}
	
	public int batchInsert(String statementID, List<?> modelObjs)
	{
		int result = 0;
//		int count=0;
		
		try 
		{
			openBatSession();
//			for(Object obj : modelObjs)
//			{
//				result += session.insert(statementID, obj);
//				count++;
//				if(count>=10000)
//				{
//					session.commit();
//					session.clearCache();
//					count=0;
//				}
//			}
			
			for(Object obj : modelObjs)
			{
				session.insert(statementID, obj);
				result++;
			}
			
			session.commit();
			session.clearCache();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			if(session != null){session.rollback();}
			result = -1;
		}
		finally
		{
			closeSession();
		}
		
		return result;
	}
	
	public int update(String statementID, Object modelObj)
	{
		int result = -1;
		try
		{
			openSession();
			result = session.update(statementID, modelObj);
			session.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			if(session != null){session.rollback();}
		}
		finally
		{
			closeSession();
		}
		return result;
	}
	
	public int batchUpdate(String statementID, Object[] modelObjs)
	{
		int result = 0;
//		int count = 0;
		try
		{
			openBatSession();
//			for(Object obj : modelObjs)
//			{
//				result = session.update(statementID, obj);
//				count++;
//				if(count>=10000)
//				{
//					session.commit();
//					session.clearCache();
//					count=0;
//				}
//			}
			
			for(Object obj : modelObjs)
			{
				session.update(statementID, obj);
				result++;
			}
			
			session.commit();
			session.clearCache();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			if(session != null){session.rollback();}
			result = -1;
		}
		finally
		{
			closeSession();
		}
		
		return result;
	}
	
	public int batchUpdate(String statementID, List<?> modelObjs)
	{
		int result = 0;
//		int count = 0;
		try
		{
			openBatSession();
//			for(Object obj : modelObjs)
//			{
//				result = session.update(statementID, obj);
//				count++;
//				if(count>=10000)
//				{
//					session.commit();
//					session.clearCache();
//					count=0;
//				}
//			}
			
			for(Object obj : modelObjs)
			{
				session.update(statementID, obj);
				result++;
			}
			
			session.commit();
			session.clearCache();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			if(session != null){session.rollback();}
			result = -1;
		}
		finally
		{
			closeSession();
		}
		
		return result;
	}
	
	public int delete(String statementID, Object modelObj)
	{
		int result = -1;
		try
		{
			openSession();
			result = session.delete(statementID, modelObj);
			session.commit();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			if(session != null){session.rollback();}
		}
		finally
		{
			closeSession();
		}
		return result;
	}
	
	public Object selectOne(String statementID, Object modelObj)
	{
		Object result = null;
		try
		{
			openSession();
			result = session.selectOne(statementID, modelObj);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeSession();
		}
		return result;
	}
	
	public Object selectOne(String statementID)
	{
		Object result = null;
		try
		{
			openSession();
			result = session.selectOne(statementID);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeSession();
		}
		return result;
	}
	
	@SuppressWarnings("hiding")
	public <Object>List<Object> selectList(String statementID, Object modelObj)
	{
		List<Object> result = null;
		try
		{
			openSession();
			result = session.selectList(statementID, modelObj);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeSession();
		}
		return result;
	}
	
	public <E>List<E> selectList(String statementID, Object modelObj, int flag)
	{
		List<E> result = null;
		try
		{
			openSession();
			result = session.selectList(statementID, modelObj);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			closeSession();
		}
		return result;
	}

}
