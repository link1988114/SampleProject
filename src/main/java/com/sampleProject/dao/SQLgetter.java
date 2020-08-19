package com.sampleProject.dao;

public class SQLgetter 
{	
	private static SQLgetter instance = null;
	
	private SQLgetter()
	{
	}
	
	public static SQLgetter getInstance()
	{
		if(instance == null)
		{
			instance = new SQLgetter();
		}
		return instance;
	}
	

	/////////////////////////////////////////////////////////////////////////////////////////
	
	public String insert_user_addUser = "com.sampleProject.mapper.UserMapper.addUser";
	public String select_user_checkUser = "com.sampleProject.mapper.UserMapper.checkUser";
	public String select_user_getUserList = "com.sampleProject.mapper.UserMapper.getUserList";
	public String select_user_getUserListCounts = "com.sampleProject.mapper.UserMapper.getUserListCounts";
	public String update_user_updateLoginLog = "com.sampleProject.mapper.UserMapper.updateLoginLog";
	public String update_user_resetTryCounts = "com.sampleProject.mapper.UserMapper.resetTryCounts";
	public String update_user_resetPassword = "com.sampleProject.mapper.UserMapper.resetPassword";
	public String update_user_setPassword = "com.sampleProject.mapper.UserMapper.setPassword";
	public String delete_user_deleteUser = "com.sampleProject.mapper.UserMapper.deleteUser";
	public String select_user_getMenuIDs = "com.sampleProject.mapper.UserMapper.getMenuIDs";
	public String select_user_getMenu = "com.sampleProject.mapper.UserMapper.getMenu";
	public String select_user_getAllMenu = "com.sampleProject.mapper.UserMapper.getAllMenu";
	public String select_user_getLevelMenus = "com.sampleProject.mapper.UserMapper.getLevelMenus";
	public String update_user_updateMenu = "com.sampleProject.mapper.UserMapper.updateMenu";
	
	
	public String select_wx_getToken = "com.sampleProject.mapper.WxMapper.getToken";
	public String insert_wx_saveToken = "com.sampleProject.mapper.WxMapper.saveToken";
	
	
}
