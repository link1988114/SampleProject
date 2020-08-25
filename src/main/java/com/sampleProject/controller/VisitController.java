package com.sampleProject.controller;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sampleProject.util.CaptchaUtil;
import com.sampleProject.util.RequestUtil;
import com.sampleProject.service.QrService;

@Controller
public class VisitController
{
	@RequestMapping("/visit.do")
	public String test(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		return "redirect:/page/login.html";
	}

	@RequestMapping("/logined.do")
	public void logined(HttpServletResponse resp, HttpSession session) throws Exception
	{
		String jsonstr = RequestUtil.getSessionInfo(session, "userInfo");
		if(jsonstr.equals("none"))
		{
			resp.getWriter().write("failed");
		}
		else 
		{
			resp.getWriter().write("success");
		}
		/*
		UserModel userModel = new Gson().fromJson(jsonstr, UserModel.class);
		int userLevel = userModel.getUserlevel();
		if(userLevel >= 100)
		{
			//admin
			return "redirect:/page/main_admin.html";
		}
		else
		{
			//user
			return "redirect:/page/main_user.html";
		}
		*/		
	}
	
	@RequestMapping("/captcha.do")
	public void getCaptcha(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws Exception
	{
		//return stream      page:  <img src=".....captcha.do" />
		resp.setContentType("image/jpeg");
		resp.setHeader("Pragma", "No-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expire", 0);
		CaptchaUtil captcha = new CaptchaUtil();
		captcha.getRandcode(req, resp, session);
	}
	
	@RequestMapping("/getBase64QRCode.do")
	public void getBase64QRCode(String content, HttpServletResponse resp, HttpSession session) throws Exception
	{
		//return base64      page:   <img src="data:image/jpeg;base64,"+imgData />
		QrService qrService = new QrService();
		qrService.getBase64QRCode(content, resp.getOutputStream());
	}
	
	
	@RequestMapping("/getStreamQRCode.do")
	public void getStreamQRCode(String content, HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws Exception
	{
		//return stream      page:    <img src="......getStreamQRCode.do" />
		resp.setContentType("image/jpeg");
		resp.setHeader("Pragma", "No-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expire", 0);
		QrService qrService = new QrService();
		qrService.getStreamQRCode(content, resp.getOutputStream());
	}
	
	
	
	
	@RequestMapping("/report.do")
	public void redirectReport(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		//fine report redirect
		String params = req.getQueryString();
		System.out.println(params);
		String url = "http://10.136.77.82:8601/WebReport/ReportServer";
		resp.sendRedirect(url + "?" + params);
	}
	
}
