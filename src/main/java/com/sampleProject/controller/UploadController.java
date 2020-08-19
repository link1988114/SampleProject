package com.sampleProject.controller;


import com.sampleProject.service.FileService;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/upload")
public class UploadController
{
	@RequestMapping("/upload.do") 
	public void upload(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		FileService fileService = new FileService();
		String newfilename = new Date().getTime() + (int)Math.random() * 100+"";
		String result = fileService.doFileUpload(req, "upload", newfilename);
		resp.getWriter().write(result);
	}
  
  
	@RequestMapping("/download.do")
	public void download(String fileName, HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		FileService fileService = new FileService();
		fileService.doFileDownload(req, resp, "upload", fileName, true);
	}
  
  
  
  ///////////////////////////////////////////////
  
//  @RequestMapping("/dataupload.do")
//  public void dataUpload(HttpServletRequest req, HttpServletResponse resp) throws Exception
//  {
//	  FileService fileService = new FileService();
//	  String newfilename = new Date().getTime() + (int)Math.random() * 100+"";
//	  String result = fileService.doFileUpload(req, "upload", newfilename);
//	  
//	  if(result.contains("failed"))
//	  {
//		  resp.getWriter().write(result);
//		  return;
//	  }
//	  else 
//	  {
//		  String path = fileService.getfilePath(req, "upload");
//		  MsgModel msg = new Gson().fromJson(result, MsgModel.class);
//		  String filename = new JSONObject(msg.getMsg()).getJSONArray("data").get(0).toString();
//		  result = fileService.processDataUpload(path, filename);
//		  resp.getWriter().write(result);
//	  }
//  }
  
  

  
}
