package com.sampleProject.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sampleProject.dao.DaoWx;
import com.sampleProject.model.WxModel;

public class WxCloudUtil
{
	private String appid = "wxa94fbe83acb237cf";
	private String secret = "b6c6fc24cbfcb6ed9279fe5bcd77e4c2";
	
	private String entranceBase = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri=";
	private String entranceTail = "&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
	
	private String tokenFile = "token.ini";
	private String cloud_env = "siteshot-8rpc1";
    		
	private String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret;
	private String query_url = "https://api.weixin.qq.com/tcb/databasequery?access_token=";
	private String count_url = "https://api.weixin.qq.com/tcb/databasecount?access_token=";
	private String update_url = "https://api.weixin.qq.com/tcb/databaseupdate?access_token=";
	private String delete_url = "https://api.weixin.qq.com/tcb/databasedelete?access_token=";
	private String add_url = "https://api.weixin.qq.com/tcb/databaseadd?access_token=";
	private String download_url = "https://api.weixin.qq.com/tcb/batchdownloadfile?access_token=";
	private String deletefile_url = "https://api.weixin.qq.com/tcb/batchdeletefile?access_token=";
	private String dbExport_url = "https://api.weixin.qq.com/tcb/databasemigrateexport?access_token=";
	private String dbExportStatus_url = "https://api.weixin.qq.com/tcb/databasemigratequeryinfo?access_token=";
	
	@SuppressWarnings("unused")
	private String downloadFolder = "img/";
	
	
	public WxCloudUtil()
	{
		
	}
	
	public WxCloudUtil(String tokenFile, String downloadFolder, String appid, String secret, String cloud_env)
	{
		this.tokenFile = tokenFile;
		this.downloadFolder = downloadFolder;
		this.appid = appid;
		this.secret = secret;
		this.cloud_env = cloud_env;
		
		this.token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret;
	}
	


	
	/////////////////////////////////////////////////////////////////////////////
	

	
	public String getBaseAuthUrl(String redirUrl)
	{
		//只能带一个自定义参数
		return entranceBase+redirUrl+entranceTail;
	}
	
	//初始获取token
	public String getToken(String source)
	{
		String token = null;
		double expire = -1;
		
		if(source.equals("file"))
		{
			FileUtil fp = new FileUtil(tokenFile, "utf8");
			String line = fp.read();
			if(line != null)
			{
				token = line.split("\\|")[0];
				expire = Double.parseDouble(line.split("\\|")[1]);
			}
		}
		else if(source.equals("db"))
		{
			WxModel wxModel = new WxModel();
			wxModel.setAppid(appid);
			DaoWx daoWx = new DaoWx();
			WxModel temp = daoWx.getAppInfo(wxModel);
			if(temp != null)
			{
				token = temp.getToken();
				expire = Double.parseDouble(temp.getExpire());
			}
		}
		
		long now = new Date().getTime();
		if(now >= expire || token == null)
		{
			token = refreshToken(source);
			if(token != null)
			{
				expire = Double.parseDouble(token.split("\\|")[1]);
				token = token.split("\\|")[0];
			}
		}
		
		return token==null ? token : token+"|"+expire;
	}
	
	
	//检查token有效  返回可用token|expire
	public String checkToken(String token, double expire, String source)
	{
		if(new Date().getTime() >= expire)
		{
			token = refreshToken(source);
		}
		return token;  // null    or   token|expire
	}
	
	
	
	//云函数查询     query count delete update add deletefile 
	public JSONObject cloudQuery(String queryType, String token, JSONObject query)
	{
		String url = "";
		if(queryType.equals("query"))
		{
			// query:""
			url = query_url + token;
		}
		else if(queryType.equals("count"))
		{
			// query:""
			url = count_url + token;
		}
		else if(queryType.equals("delete"))
		{
			// query:""
			url = delete_url + token;
		}
		else if(queryType.equals("update"))
		{
			// query:""
			url = update_url + token;
		}
		else if(queryType.equals("add"))
		{
			// query:""
			url = add_url + token;
		}
		
		else if(queryType.equals("deletefile"))
		{
			//fileid_list:[]
			url = deletefile_url + token;
		}
		
		query.put("env", cloud_env);
		
		return sendCloudAction(url, query);  //null  or   json
	}
	
	
	//云函数数据库导出
	public String cloudExport(String filename, String filetype, String token, JSONObject query)
	{
		String downURL = null;
		
		filetype = filetype.equals("csv") ? "2" : "1";
		String url = dbExport_url + token;
		query.put("env", cloud_env);
		query.put("file_type", filetype);
		query.put("file_path", filename);
		
		JSONObject re = sendCloudAction(url, query);
		if(re != null && re.optString("errcode").equals("0"))
		{
			//success
			String jobID = re.optString("job_id");
			
			url = dbExportStatus_url + token;
			query = new JSONObject();
			query.put("job_id", jobID);
			query.put("env", cloud_env);
			re = sendCloudAction(url, query);
			if(re != null && re.optString("status").equals("success"))
			{
				downURL = re.optString("file_url");
			}
		}
		
		return downURL;
	}
	
	
	//云函数下载文件
	public List<String> cloudDownload(String[] fileIDs, int maxAge)
	{
		String url = download_url;
		JSONArray arr = new JSONArray();
		JSONObject query = new JSONObject();
		query.put("env", cloud_env);
		query.put("file_list", arr);
		for(int i=0; i<fileIDs.length; i++)
		{
			JSONObject temp = new JSONObject();
			temp.put("fileid", fileIDs[i]);
			temp.put("max_age", maxAge);
			arr.put(temp);
		}
		JSONObject re = sendCloudAction(url, query);
		List<String> downUrls = new ArrayList<String>();
		if(re.optString("errcode").equals("0"))
		{
			JSONArray list = re.optJSONArray("file_list");
			for(int i=0; i<list.length(); i++)
			{
				JSONObject item = list.getJSONObject(i);
				if(item.optString("status").equals("0"))
				{
					downUrls.add(item.optString("download_url"));
				}
				else
				{
					downUrls.add("");
				}
			}
		}
		else
		{
			downUrls = null;
		}
		
		return downUrls;
	}
	
	
	public String cloudUrlToHttpUrl(String cloudUrl)
	{
		String url = "";
		String[] arr = cloudUrl.split("\\.");
		String[] contentArr = arr[1].split("\\/");
		url = "https://"+contentArr[0]+".tcb.qcloud.la"+cloudUrl.replace(arr[0]+"."+contentArr[0], "");
		return url;
	}
	
	
	/////////////////////////////////////////////////////////////////////////////

	//token更新     token|expire
	private String refreshToken(String source)
	{
		String token = null;
		double expire = -1;
		
		long now = new Date().getTime();
		HttpClientUtil client = new HttpClientUtil("https");
		try 
		{
			String context = client.sendGet("", token_url, "utf8", true);
			if(!context.contains("errcode"))
			{
				JSONObject temp = new JSONObject(context);
				token = temp.optString("access_token");
				expire = now + temp.optDouble("expires_in")*1000;
				
				if(!token.equals(""))
				{
					if(source.equals("file"))
					{
						FileUtil fp = new FileUtil(tokenFile, "utf8");
						fp.write(token+"|"+expire, false);
					}
					else if(source.equals("db"))
					{
						DaoWx daoWx = new DaoWx();
						WxModel wxModel = new WxModel();
						wxModel.setAppid(appid);
						wxModel.setToken(token);
						wxModel.setExpire(expire+"");
						int flag = daoWx.updateAppInfo(wxModel);
						token = flag<=0 ? null : token;
					}
				}
			}
		} 
		catch (Exception e) 
		{
			client.closeHttpClient();
			token = null;
		}
		
		return token==null ? token : token+"|"+expire;
	}
	
	
	//发起云函数请求
	private JSONObject sendCloudAction(String url, JSONObject query)
	{
		JSONObject re = null;
		try 
		{
			if(url != null && url.contains("http"))
			{
				HttpClientUtil httpclient = new HttpClientUtil("https");
				String res = httpclient.httpsJsonPost(url, query, "utf8");
				re = new JSONObject(res);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return re;
	}
	
	
}
