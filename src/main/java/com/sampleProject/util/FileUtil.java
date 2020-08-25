package com.sampleProject.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class FileUtil
{
	private String encode = "";
	private String path = "";
	
	public FileUtil(String path,String encode)
	{
		this.path = path;
		this.encode = encode;
	}
	
	public FileUtil()
	{
		
	}
	
	public String read()
	{
		String result = "";
		try
		{
			File file = new File(path);
			if(file.isFile() && file.exists())
			{
				InputStreamReader fin = new InputStreamReader(new FileInputStream(file),encode);
				BufferedReader bufreader = new BufferedReader(fin);
				String line = "";
				while((line=bufreader.readLine()) != null)
				{
					result+=line;
				}
				bufreader.close();
				fin.close();
			}
			else 
			{
				result = null;
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			result = null;
		}
		return result;
	}
	
	
	public String write(String content, Boolean isAppend)
	{
		File file = new File(path);
		try
		{
			if(!file.exists())
			{
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile(),isAppend);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();	
			fw.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return file.getName();
	}
	
	public String PrintStreamToFile(PrintStream ps, String content)
	{
		FileOutputStream fout = null;
		try
		{
			fout = new FileOutputStream(path, true);
			ps = new PrintStream(fout);
			//ps.println(content);
			ps.append(content);
		} 
		catch (Exception e)
		{
			// TODO: handle exception
		}
		finally 
		{
			if(ps != null)
			{
				ps.close();
			}
		}
		return "done";
	}
	
	public Long getFilesize()
	{
		File f = new File(path);
		return f.getTotalSpace();
	}
	
	public String getFilename()
	{
		File f = new File(path);
		return f.getName();
	}
	
	
	public String createDir(String destDirName) 
	{
		String result = "";
		
		if (!destDirName.endsWith(File.separator)) 
		{
			destDirName = destDirName + File.separator;
		}
		
		File dir = new File(destDirName);
		if (dir.exists()) 
		{
			System.out.println("dirExist");
			result = destDirName;
			return result;
		}
		if (dir.mkdirs()) 
		{
			System.out.println("success:" + destDirName);
			result = destDirName;
		} 
		else 
		{
			System.out.println("failed");
			result = "failed";
		}
		return result;
	}
	
}
