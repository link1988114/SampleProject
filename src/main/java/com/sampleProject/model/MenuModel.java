package com.sampleProject.model;


public class MenuModel extends BaseModel
{	
	private int menu_id = -1;
	private String title = "";
	private String url = "";
	private int parent_id = -1;
	private String icon = "";
	
	private int userlevel = -1;
	private String level_name = "";
	private String menu_ids = "";
	
	private int draw = 0;
	private int start = 0;   //start index
	private int length = 10;  //volumn
	
	private String searchValue = "";

	
	
	public int getMenu_id()
	{
		return menu_id;
	}

	public void setMenu_id(int menu_id)
	{
		this.menu_id = menu_id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public int getParent_id()
	{
		return parent_id;
	}

	public void setParent_id(int parent_id)
	{
		this.parent_id = parent_id;
	}

	public String getIcon()
	{
		return icon;
	}

	public void setIcon(String icon)
	{
		this.icon = icon;
	}

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

	public String getSearchValue()
	{
		return searchValue;
	}

	public void setSearchValue(String searchValue)
	{
		this.searchValue = searchValue;
	}

	public int getUserlevel()
	{
		return userlevel;
	}

	public void setUserlevel(int userlevel)
	{
		this.userlevel = userlevel;
	}

	public String getLevel_name()
	{
		return level_name;
	}

	public void setLevel_name(String level_name)
	{
		this.level_name = level_name;
	}

	public String getMenu_ids()
	{
		return menu_ids;
	}

	public void setMenu_ids(String menu_ids)
	{
		this.menu_ids = menu_ids;
	}


}
