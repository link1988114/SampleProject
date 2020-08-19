package com.sampleProject.util;


import java.io.File;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class FileUpload 
{
	@SuppressWarnings("rawtypes")
	public static List<String> springUpload(HttpServletRequest req, String storePath, String filename)
	{
		List<String> filelist = new ArrayList<String>();
		CommonsMultipartResolver multResolver = new CommonsMultipartResolver(req.getSession().getServletContext());
		if(multResolver.isMultipart(req))
		{
			MultipartHttpServletRequest multiReq = (MultipartHttpServletRequest)req;
			Iterator iter = multiReq.getFileNames();
			while(iter.hasNext())
			{
				MultipartFile file = multiReq.getFile(iter.next().toString());
				if(file != null)
				{
					String originalName = file.getOriginalFilename();		
					String suffix = originalName.split("\\.")[originalName.split("\\.").length-1];
					String newFilename = filename+"."+suffix;
					
					String temp = getfilePath(req, storePath);
					//System.out.println(temp);
					
					if(temp==null || temp.equals("error"))
					{
						return null;
					}
					
					String path = temp + File.separator + newFilename;
					try 
					{
						file.transferTo(new File(path));
						filelist.add(newFilename);
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
			}
		}
		return filelist;
	}

	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////
	
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
	
	
	
}
