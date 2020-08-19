package com.sampleProject.model;

import com.google.gson.JsonArray;

public class TableModel extends BaseModel
{
	private int draw;
	private int start = 0;
	private int length = 10;
	
	private String searchKey = "";
	
	private int recordsTotal = 0;
	private int recordsFiltered = 0;
	
	private JsonArray data = null;
	



	public int getDraw()
	{
		return draw;
	}

	public void setDraw(int draw)
	{
		this.draw = draw;
	}

	public int getStart()
	{
		return start;
	}

	public void setStart(int start)
	{
		this.start = start;
	}

	public int getLength()
	{
		return length;
	}

	public void setLength(int length)
	{
		this.length = length;
	}

	public int getRecordsTotal()
	{
		return recordsTotal;
	}

	public void setRecordsTotal(int recordsTotal)
	{
		this.recordsTotal = recordsTotal;
	}

	public int getRecordsFiltered()
	{
		return recordsFiltered;
	}

	public void setRecordsFiltered(int recordsFiltered)
	{
		this.recordsFiltered = recordsFiltered;
	}

	public String getSearchKey()
	{
		return searchKey;
	}

	public void setSearchKey(String searchKey)
	{
		this.searchKey = searchKey;
	}

	public JsonArray getData()
	{
		return data;
	}

	public void setData(JsonArray data)
	{
		this.data = data;
	}
	
	

}
