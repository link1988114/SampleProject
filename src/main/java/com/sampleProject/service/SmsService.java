package com.sampleProject.service;

import com.sampleProject.util.AliSmsUtil;

public class SmsService
{
	private String aliKey = "";
	private String aliSecret = "";
	private String aliTemplate = "";
	private String aliSign = "";
	
	public SmsService()
	{
		
	}
	
	public String sendSms(String phone, String content)
	{
		AliSmsUtil aliSms = new AliSmsUtil(aliKey, aliSecret, aliTemplate, aliSign);
		String result = aliSms.send(phone, "{\"number\":\""+content+"\"}");
		return result;
	}

}
