package com.sampleProject.util;


import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;


import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;


@SuppressWarnings("deprecation")
public class HttpClientUtil
{
	public CloseableHttpClient httpClient = null;
	public CloseableHttpResponse httpResponse = null;
	
	public HttpClientUtil(String type)
	{
		if(type.equals("http"))
		{
			httpClient = createNormalClient();
		}
		else
		{
			httpClient = createSSLClient();
		}
		
	}
	
	
	
	public CloseableHttpClient createSSLClient()
	{
		try
		{
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy()
			{
				public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException
				{
					return true;
				}
			}).build();
			HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
			BasicCookieStore cookieStore = new BasicCookieStore();
			return HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultCookieStore(cookieStore).build();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return HttpClients.createDefault();
	}
	
	public CloseableHttpClient createNormalClient()
	{
		try
		{
			BasicCookieStore cookieStore = new BasicCookieStore();
			return HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return HttpClients.createDefault();
	}
	
	
	public boolean closeHttpClient()
	{
		try
		{
			if(httpResponse != null)
			{
				httpResponse.close();
			}
			if(httpClient != null)
			{
				httpClient.close();
			}
		} 
		catch (Exception e)
		{
			return false;
		}
		return true;
	}
	
	
	
	public String httpsGet(String jsonstr, String url) throws Exception
	{
		String result = "";
		
		String param = "";
		if(jsonstr != null && !jsonstr.equals(""))
		{
			JSONObject json = new JSONObject(jsonstr);
			Iterator<String> iter = json.keys();
			while(iter.hasNext())
			{
				String key = iter.next();
				String value = json.optString(key);
				param+=key+"="+value+"&";
			}
		}
		if(!param.equals(""))
		{
			param = param.substring(0,param.length()-1);
			url=url+"?"+param;
		}
		
		HttpGet httpGet = new HttpGet(url);
		if(httpClient == null)
		{
			httpClient = createSSLClient();
		}
		httpResponse = httpClient.execute(httpGet);
		HttpEntity resEntity = httpResponse.getEntity();
		if(resEntity != null)
		{
			result = EntityUtils.toString(resEntity,"utf8");
		}
		else
		{
			result = "";
		}
		//httpResponse.close();
		//httpClient.close();
		
		return result;
	}
	
	public String httpsPost(String jsonstr, String url) throws Exception
	{
		String result = "";
		
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		if(jsonstr != null && !jsonstr.equals(""))
		{
			JSONObject json = new JSONObject(jsonstr);
			Iterator<String> iter = json.keys();
			while(iter.hasNext())
			{
				String key = iter.next();
				String value = json.optString(key);
				paramList.add(new BasicNameValuePair(key, value));
			}
		}
		HttpPost httpPost = new HttpPost(url);
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "utf8");
		httpPost.setEntity(entity);
		if(httpClient == null)
		{
			httpClient = createSSLClient();
		}
		httpResponse = httpClient.execute(httpPost);
		HttpEntity resEntity = httpResponse.getEntity();
		if(resEntity != null)
		{
			result = EntityUtils.toString(resEntity,"utf8");
		}
		else
		{
			result = "";
		}
		//httpResponse.close();
		//httpClient.close();
		
		return result;
	}
	
	
	
	public String httpGet(String jsonstr, String url) throws Exception
	{
		String result = "";
		
		String param = "";
		if(jsonstr != null && !jsonstr.equals(""))
		{
			JSONObject json = new JSONObject(jsonstr);
			Iterator<String> iter = json.keys();
			while(iter.hasNext())
			{
				String key = iter.next();
				String value = json.optString(key);
				param+=key+"="+value+"&";
			}
		}
		if(!param.equals(""))
		{
			param = param.substring(0,param.length()-1);
			url=url+"?"+param;
		}
		
		System.out.println(url);
		
		HttpGet httpGet = new HttpGet(url);
		if(httpClient == null)
		{
			httpClient = createNormalClient();
		}
		httpResponse = httpClient.execute(httpGet);
		HttpEntity resEntity = httpResponse.getEntity();
		if(resEntity != null)
		{
			result = EntityUtils.toString(resEntity,"utf8");
		}
		else
		{
			result = "";
		}
		//httpResponse.close();
		//httpClient.close();
		
		return result;
	}
	
	
	public String httpPost(String jsonstr, String url) throws Exception
	{
		String result = "";
		
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		if(jsonstr != null && !jsonstr.equals(""))
		{
			JSONObject json = new JSONObject(jsonstr);
			Iterator<String> iter = json.keys();
			while(iter.hasNext())
			{
				String key = iter.next();
				String value = json.optString(key);
				paramList.add(new BasicNameValuePair(key, value));
			}
		}
		HttpPost httpPost = new HttpPost(url);
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "utf8");
		httpPost.setEntity(entity);
		if(httpClient == null)
		{
			httpClient = createNormalClient();
		}
		httpResponse = httpClient.execute(httpPost);
		
		System.out.println(httpResponse);
		
		HttpEntity resEntity = httpResponse.getEntity();
		if(resEntity != null)
		{
			result = EntityUtils.toString(resEntity,"utf8");
		}
		else
		{
			result = "";
		}
		//httpResponse.close();
		//httpClient.close();
		
		return result;
	}
	
	
	public String httpPostXML(String xmlstr, String url, String encoding) throws Exception
	{
		String result = "";
		
		HttpPost httpPost = new HttpPost(url);
		List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
		parameters.add(new BasicNameValuePair("xml", xmlstr));
		httpPost.setEntity(new UrlEncodedFormEntity(parameters, encoding));
		
		if(httpClient == null)
		{
			httpClient = createNormalClient();
		}
		httpResponse = httpClient.execute(httpPost);
		HttpEntity resEntity = httpResponse.getEntity();
		if(resEntity != null)
		{
			result = EntityUtils.toString(resEntity, encoding);
		}
		else
		{
			result = "";
		}
		//httpResponse.close();
		//httpClient.close();
		
		return result;
	}
	
	public String httpsJsonPost(String url,JSONObject jsonObject,String encoding) throws Exception
	{
		String result = "";
		HttpPost httpPost = new HttpPost(url);
		StringEntity strEntity = new StringEntity(jsonObject.toString());
		strEntity.setContentEncoding(encoding);
		strEntity.setContentType("application/json");
		
		httpPost.setEntity(strEntity);
		if(httpClient == null)
		{
			httpClient = createSSLClient();
		}
		httpResponse = httpClient.execute(httpPost);
		HttpEntity resEntity = httpResponse.getEntity();
		if(resEntity != null)
		{
			result = EntityUtils.toString(resEntity,"utf8");
		}
		else
		{
			result = "";
		}
		return result;
	}
	

	
	
	public String getStr(String url, String param)
	{
		String result = "";
		try
		{
			CloseableHttpClient client = HttpClients.createDefault();
			RequestConfig conf = RequestConfig.custom().setSocketTimeout(4000).setConnectTimeout(4000).build();
			param = param.replace("%", "%25");
			param = param.replace(" ", "%20");
			param = param.replace("#", "%23");
			HttpGet httpGet = new HttpGet(url+"?"+param);
			httpGet.setConfig(conf);
			CloseableHttpResponse clientResp = client.execute(httpGet);
			HttpEntity entity = clientResp.getEntity();
			result = EntityUtils.toString(entity,"utf-8");
		} 
		catch (Exception e)
		{
			result = null;
		}
		return result;
	}
	
	
	
	
	
	
	public String sendGet(String jsonstr, String url, String encoding, boolean isHttps)
	{
		String result = "";
		
		try 
		{
			String param = "";
			if(jsonstr != null && !jsonstr.equals(""))
			{
				JSONObject json = new JSONObject(jsonstr);
				Iterator<String> iter = json.keys();
				while(iter.hasNext())
				{
					String key = iter.next();
					String value = json.optString(key);
					param+=key+"="+value+"&";
				}
			}
			if(!param.equals(""))
			{
				param = param.substring(0,param.length()-1);
				url=url+"?"+param;
			}
			
			HttpGet httpGet = new HttpGet(url);
			if(httpClient == null)
			{
				httpClient = isHttps? createSSLClient() : createNormalClient();
			}
			httpResponse = httpClient.execute(httpGet);
			HttpEntity resEntity = httpResponse.getEntity();
			if(resEntity != null)
			{
				result = EntityUtils.toString(resEntity, encoding);
			}
			else
			{
				result = "";
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			result = "";
		}
		finally 
		{
//			closeHttpClient();
		}

		return result;
	}
	
	
	
	public String sendPost(String jsonstr, String xmlStr, String url, String encoding, boolean isHttps, boolean isJson)
	{
		String result = "";
		
		boolean isXML = false;
		if(xmlStr != null)
		{
			isXML = true;
			isJson = false;
		}
		else
		{
			jsonstr = testJsonParams(jsonstr);
			if(jsonstr==null)
			{
				return "post entity error";
			}
		}
		
		String contentStr = isXML? xmlStr : jsonstr;
		try 
		{
			HttpPost httpPost = new HttpPost(url);
			
			//create entity
			if(isJson)
			{
				//json
				StringEntity strEntity = new StringEntity(contentStr);
				strEntity.setContentEncoding(encoding);
				strEntity.setContentType("application/json");
				httpPost.setEntity(strEntity);
			}
			else 
			{
				//form
				if(isXML)
				{
					List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
					parameters.add(new BasicNameValuePair("xml", contentStr));
					httpPost.setEntity(new UrlEncodedFormEntity(parameters, encoding));
				}
				else
				{
					List<NameValuePair> paramList = new ArrayList<NameValuePair>();
					if(contentStr != null && !contentStr.equals(""))
					{
						JSONObject json = new JSONObject(jsonstr);
						Iterator<String> iter = json.keys();
						while(iter.hasNext())
						{
							String key = iter.next();
							String value = json.optString(key);
							paramList.add(new BasicNameValuePair(key, value));
						}
					}
					httpPost.setEntity(new UrlEncodedFormEntity(paramList, encoding));
				}
			}
			
			//send data
			if(httpClient == null)
			{
				httpClient = isHttps? createSSLClient() : createNormalClient();	
			}
			httpResponse = httpClient.execute(httpPost);
			
			//get result
//			System.out.println(httpResponse);
			HttpEntity resEntity = httpResponse.getEntity();
			if(resEntity != null)
			{
				result = EntityUtils.toString(resEntity,encoding);
			}
			else
			{
				result = "";
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			result = "";
		}
		finally 
		{
//			closeHttpClient();
		}
		
		return result;
	}
	
	
	/////////////////////////////////////////////////////////////////////////////
	
	private String testJsonParams(String jsonstr)
	{
		try 
		{
			jsonstr = new JSONObject(jsonstr).toString();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			jsonstr = null;
		}
		return jsonstr;
	}


}
