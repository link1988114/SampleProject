package com.sampleProject.model;


public class UserModel extends BaseModel
{
	private String username = "";
	private String password = "";
	private int userlevel = 0;
	private String login_time = "";
	private int try_counts = 0;
	private String brch_id = "";
	
	
	private String menu_ids = "";
	private String level_name = "";
	
	
	private int draw = 0;
	private int start = 0;   //start index
	private int length = 10;  //volumn
	
	private String searchValue = "";

	private int counting = 0;
	private String oldPW = "";
	private String newPW = "";
	
	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public int getUserlevel()
	{
		return userlevel;
	}

	public void setUserlevel(int userlevel)
	{
		this.userlevel = userlevel;
	}

	public String getLogin_time()
	{
		return login_time;
	}

	public void setLogin_time(String login_time)
	{
		this.login_time = login_time;
	}

	public int getTry_counts()
	{
		return try_counts;
	}

	public void setTry_counts(int try_counts)
	{
		this.try_counts = try_counts;
	}

	public String getBrch_id()
	{
		return brch_id;
	}

	public void setBrch_id(String brch_id)
	{
		this.brch_id = brch_id;
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

	public int getCounting()
	{
		return counting;
	}

	public void setCounting(int counting)
	{
		this.counting = counting;
	}

	public String getOldPW()
	{
		return oldPW;
	}

	public void setOldPW(String oldPW)
	{
		this.oldPW = oldPW;
	}

	public String getNewPW()
	{
		return newPW;
	}

	public void setNewPW(String newPW)
	{
		this.newPW = newPW;
	}

	public String getMenu_ids()
	{
		return menu_ids;
	}

	public void setMenu_ids(String menu_ids)
	{
		this.menu_ids = menu_ids;
	}

	public String getLevel_name()
	{
		return level_name;
	}

	public void setLevel_name(String level_name)
	{
		this.level_name = level_name;
	}
	

}
