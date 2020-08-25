package com.sampleProject.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil
{
	public static String zipFiles(String[] filenamesWithPath, String outputRealPath, String zipFilename)
	{
		String result = "";
		ZipOutputStream zipOut = null;
		FileInputStream in = null;
		try
		{
			zipOut = new ZipOutputStream(new FileOutputStream(outputRealPath+File.separator+zipFilename));
			for(String filename : filenamesWithPath)
			{
				File source = new File(outputRealPath+File.separator+filename);
				zipOut.putNextEntry(new ZipEntry(source.getName()));
				in = new FileInputStream(source);
				byte[] buf = new byte[1024];
				int len;
	            while ((len = in.read(buf)) > 0) 
	            {  
	                zipOut.write(buf, 0, len);  
	            }
	            in.close();
	            source.delete();
			}
			zipOut.closeEntry();
			zipOut.close();
			result = zipFilename;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			result = "error";
		}
		finally 
		{
			try
			{
				if(zipOut != null)
				{
					zipOut.closeEntry();
					zipOut.close();
				}
				if(in != null)
				{
					in.close();
				}
			}
			catch(Exception e)
			{}
		}
		return result;
	}

}
