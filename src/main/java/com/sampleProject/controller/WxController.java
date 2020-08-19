package com.sampleProject.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sampleProject.model.MsgModel;
import com.sampleProject.model.WxModel;
import com.sampleProject.service.WxService;
import com.sampleProject.util.HttpClientUtil;
import com.sampleProject.util.RequestUtil;
import com.sampleProject.util.WxSharedInfo;


@Controller
@RequestMapping("/wx")
public class WxController
{
	@RequestMapping("/init.do")
	public String init(String code, String promote_code, HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws Exception
	{
		RequestUtil.setSession(session, "reviseFlag", "0");
		
		String redirectURL = "";

		String appid = WxSharedInfo.GetInstance().appid;
		String secret = WxSharedInfo.GetInstance().secret;
		String getidURL = "https://api.weixin.qq.com/sns/oauth2/access_token";
		String param = "appid="+appid+"&secret="+secret+"&code="+code+"&grant_type=authorization_code";
			
		HttpClientUtil client = new HttpClientUtil("http");
		String jsonstr = client.getStr(getidURL, param);
		client.closeHttpClient();
		
		
		if(promote_code==null)
		{
			promote_code = "";
		}
		RequestUtil.setSession(session, "promoteCode", promote_code);
		
		//String jsonstr = "{openid:'test123'}";
		//WxService wxService = new WxService();
		//redirectURL = wxService.checkOpenid(jsonstr, session);

		return "redirect:"+redirectURL;
	}
	
	
	@RequestMapping("/getToken.do")
	public void getToken(WxModel wxModel, HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws Exception
	{
		wxModel.setSerial("1");
		WxService wxService = new WxService();
		MsgModel msg = wxService.getToken(wxModel);
		resp.getWriter().write(msg.toString());
	}
	


}
