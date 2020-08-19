package com.sampleProject.service;

import java.util.Date;

import org.json.JSONObject;

import com.sampleProject.dao.DaoWx;
import com.sampleProject.model.MsgModel;
import com.sampleProject.model.WxModel;
import com.sampleProject.util.HttpClientUtil;
import com.sampleProject.util.WxSharedInfo;

public class WxService extends BaseService
{
	public WxService()
	{
		
	}
	
	
	public MsgModel getToken(WxModel wxModel)
	{
		MsgModel msg = new MsgModel();
		
		DaoWx daoWx = new DaoWx();
		WxModel temp = daoWx.getToken(wxModel);
		String token = null;
		long now = new Date().getTime();
		
		if(temp != null)
		{
			//check expire
			token = temp.getWx_token();
			long expire = Long.parseLong(temp.getWx_expire());
			
			if(now >= expire)
			{
				//get new token   save new token and expire
				token = getNewToken(now);
			}
		}
		else
		{
			//get new token   save new token and expire
			token = getNewToken(now);
		}
		
		msg.simplePack(token!=null, token, "获取token失败");
		return msg;
	}
	
	
	//////////////////////////////////////////////////////////
	
	private String getNewToken(long starttime)
	{
		WxModel result = new WxModel();
		String token = null;
		
		HttpClientUtil httpclient = new HttpClientUtil("https");
		String url = WxSharedInfo.GetInstance().token_url;
		try 
		{
			String context = httpclient.sendGet("", url, "utf8", true);
			if(context.contains("errcode"))
			{
				token = null;
			}
			else
			{
				JSONObject temp = new JSONObject(context);
				result.setWx_token(temp.optString("access_token"));
				result.setWx_expire((starttime+temp.optInt("expires_in")*1000)+"");
				
				boolean flag = saveToken(result);
				token = flag? result.getWx_token() : null;
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			token = null;
		}
		return token;
	}
	
	
	private boolean saveToken(WxModel wxModel)
	{
		DaoWx daoWx = new DaoWx();
		int flag = daoWx.saveToken(wxModel);
		return flag>0? true : false;
	}
	

}
