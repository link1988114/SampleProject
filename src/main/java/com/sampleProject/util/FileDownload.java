package com.sampleProject.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/*
 * 
 * 		sample:
 * 
		FileDownload.Download(req, resp, "application/octet-stream", "upload", filename,suffix, true);
 * 
 * 
 */


public class FileDownload
{	
	public static void Download(HttpServletRequest req, HttpServletResponse resp, String downloadHttpType, String path, String filenameWithoutSuffix, String suffix, boolean delOrigin) 	
			throws ServletException, IOException 
	{
		
		resp.reset();
		//resp.setContentLength(Integer.parseInt(filesize));
		resp.setContentType(downloadHttpType);   //download default type is   application/octet-stream
		resp.setHeader("Content-Disposition", "attachment;filename="+filenameWithoutSuffix+"."+suffix);
		
		String realName = getfilePath(req, path) + File.separator + filenameWithoutSuffix+"."+suffix;
		InputStream fin = new FileInputStream(realName);
		OutputStream fout = resp.getOutputStream();
		
		byte[] b = new byte[1024]; 
		int temp = fin.read(b); 
		while(temp != -1)
		{ 
			fout.write(b, 0, temp);
			temp = fin.read(b);
		} 

		fout.flush();
		resp.flushBuffer();
		fin.close();
		fout.close();
		
		if(delOrigin)
		{
			File file = new File(realName);
			file.delete();
		}
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
