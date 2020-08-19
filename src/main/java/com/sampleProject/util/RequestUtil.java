package com.sampleProject.util;

import java.io.ByteArrayOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class RequestUtil
{
	public static void setEncoding(HttpServletRequest req, HttpServletResponse resp, String encode) throws Exception
	{
		req.setCharacterEncoding(encode);	
		resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("text/plain;charset="+encode);
	}
	
	public static void setSession(HttpSession session, String key,Object content)
	{
		session.setAttribute(key, content);
	}
	
	@SuppressWarnings("deprecation")
	public static void clearSession(HttpSession session)
	{
		String[] arr = session.getValueNames();
		for(int i=0;i<arr.length;i++)
		{
			session.removeAttribute(arr[i]);
		}
	}
	
	public static String getXmlStr(HttpServletRequest req)
	{
		TransformerFactory tf = TransformerFactory.newInstance();
		try 
		{
			DocumentBuilderFactory docbf = DocumentBuilderFactory.newInstance();   
			DocumentBuilder docb = docbf.newDocumentBuilder();   
			Document doc = docb.parse(req.getInputStream()); 
			
			Transformer t = tf.newTransformer();
			t.setOutputProperty(OutputKeys.ENCODING,"UTF-8");
			t.setOutputProperty(OutputKeys.METHOD, "html");  
			t.setOutputProperty(OutputKeys.VERSION, "4.0");  
			t.setOutputProperty(OutputKeys.INDENT, "no");  
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			t.transform(new DOMSource(doc), new StreamResult(bos));
			
			return bos.toString();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return "";
	}
	
	public static String getSessionInfo(HttpSession session, String key)
	{
		String result = "none";
		try
		{
			result = session.getAttribute(key).toString();
			if(result==null || result.equals(""))
			{
				result = "none";
			}
		}
		catch(Exception e)
		{
			result = "none";
		}
		return result;
	}
	
	public static String getfilePath(HttpServletRequest req, String testPath)
	{
		String temp = req.getSession().getServletContext().getRealPath(testPath);
		if(temp == null || temp.equals(""))
		{
			try
			{
				temp = req.getSession().getServletContext().getResource(testPath).getFile();
			} 
			catch (Exception e)
			{
				e.printStackTrace();
				temp = "error";
			}
		}
		return temp;
	}
	
	public static String getIpAddr(HttpServletRequest req)  
	{
		String ip = req.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = req.getHeader( "Proxy-Client-IP" );
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = req.getHeader( "WL-Proxy-Client-IP" );
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = req.getRemoteAddr();;
		}
		return ip;
	 }
	
	

}
