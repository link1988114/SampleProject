package com.sampleProject.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class BaseService
{
	public String ListToJsonstr(List<?> list)
	{
		if(list == null)
		{
			return "error";
		}
		if(list.size()==0)
		{
			return "none";
		}
		else
		{
			JsonObject json = new JsonObject();
			JsonArray arr = new JsonArray();
			json.add("data", arr);
			for(int i=0;i<list.size();i++)
			{
				arr.add(new Gson().toJsonTree(list.get(i)));
			}
			return json.toString();
		}
	}
	
	public JsonArray ListToJsonArray(List<?> list)
	{
		if(list == null || list.size()==0)
		{
			return new JsonArray();
		}
		else
		{
			JsonArray arr = new JsonArray();
			for(int i=0;i<list.size();i++)
			{
				arr.add(new Gson().toJsonTree(list.get(i)));
			}
			return arr;
		}
	}
	
	public String getfilePath(HttpServletRequest req, String testPath)
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
