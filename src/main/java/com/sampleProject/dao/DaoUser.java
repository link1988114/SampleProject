package com.sampleProject.dao;


import java.util.List;

import com.sampleProject.dao.sys.BaseDao;
import com.sampleProject.model.MenuModel;
import com.sampleProject.model.UserModel;

public class DaoUser extends BaseDao
{	
	public UserModel checkLogin(UserModel userModel)
	{
		UserModel temp = (UserModel)db.selectOne(sqls.select_user_checkUser, userModel);
		return temp;
	}
	
	public int updateLoginLog(UserModel userModel)
	{
		int temp = db.update(SQLgetter.getInstance().update_user_updateLoginLog, userModel);
		return temp;
	}
	
	public int resetTryCounts(UserModel userModel)
	{
		int temp = db.update(SQLgetter.getInstance().update_user_resetTryCounts, userModel);
		return temp;
	}
	
	public int resetPassword(UserModel userModel)
	{
		int temp = db.update(SQLgetter.getInstance().update_user_resetPassword, userModel);
		return temp;
	}
	
	public int setPassword(UserModel userModel)
	{
		int temp = db.update(SQLgetter.getInstance().update_user_setPassword, userModel);
		return temp;
	}
	
	public List<UserModel> getUserList(UserModel userModel)
	{
		List<UserModel> list = db.selectList(SQLgetter.getInstance().select_user_getUserList, userModel);
		return list;
	}
	
	public int getUserListCounts(UserModel userModel)
	{
		return (Integer)db.selectOne(SQLgetter.getInstance().select_user_getUserListCounts, userModel);
	}
	
	public int deleteUser(UserModel userModel)
	{
		return db.delete(SQLgetter.getInstance().delete_user_deleteUser, userModel);
	}
	
	public int addUser(UserModel userModel)
	{
		return db.insert(SQLgetter.getInstance().insert_user_addUser, userModel);
	}
	
	
	/////////////////////////////////////////////////////////////////////
	// menu info
	
	public String getMenuIDs(int userlevel)
	{
		return (String)db.selectOne(sqls.select_user_getMenuIDs, userlevel);
	}
	
	public List<MenuModel> getMenu(String[] ids)
	{
		List<MenuModel> list = db.selectList(sqls.select_user_getMenu, ids, 1);
		return list;
	}
	
	public List<MenuModel> getAllMenu()
	{
		List<MenuModel> result = db.selectList(sqls.select_user_getAllMenu, new MenuModel());
		return result;
	}
	
	public List<MenuModel> getLevelMenus()
	{
		List<MenuModel> result = db.selectList(sqls.select_user_getLevelMenus, null);
		return result;
	}
	
	public int updateMenu(MenuModel menuModel)
	{
		return db.update(sqls.update_user_updateMenu, menuModel);
	}
	
	
}
