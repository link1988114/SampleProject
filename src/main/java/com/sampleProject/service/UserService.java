package com.sampleProject.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sampleProject.dao.DaoUser;
import com.sampleProject.model.MenuModel;
import com.sampleProject.model.MsgModel;
import com.sampleProject.model.TableModel;
import com.sampleProject.model.UserModel;
import com.sampleProject.util.ProjectUtil;
import com.sampleProject.util.RequestUtil;
import com.sampleProject.util.TimeUtil;

public class UserService extends BaseService
{
	private UserModel userModel;
	private DaoUser userDB = new DaoUser();

	
	public UserService(UserModel userModel)
	{
		this.userModel = userModel;
	}
	
	public String checkLogin(HttpSession session) throws Exception
	{
		MsgModel msgModel = new MsgModel();
		int tryMax = 5;
		int resetTime = 30000;
		
		UserModel temp = userDB.checkLogin(userModel);
		if(temp==null)
		{
			msgModel.setMsg("用户名或密码错误"); // no user
		}
		else
		{
			int tryCounts = temp.getTry_counts();
			int counting = temp.getCounting();
			Date loginTime = new Date();
			if(temp.getLogin_time()==null || temp.getLogin_time().equals(""))
			{
				loginTime.setTime(0);
			}
			else
			{
				loginTime = TimeUtil.stringToDate(temp.getLogin_time(), "yyyyMMdd HH:mm:ss");
			}
			Date now = TimeUtil.getNowDate();
			
			if(now.getTime()-loginTime.getTime() > resetTime)
			{
				userDB.resetTryCounts(temp);
				tryCounts = 0;
			}
			
			if(tryCounts >= tryMax)
			{
				msgModel.setMsg("操作次数过多,请30秒后再试");
			}
			else
			{
				temp.setTry_counts(tryCounts+1);
				temp.setLogin_time(TimeUtil.getNow());
					
				userDB.updateLoginLog(temp);
					
				if(counting==1)
				{
					msgModel.setResult("success");
					
					//get menu ids
					String menuIDs = userDB.getMenuIDs(temp.getUserlevel());
					temp.setMenu_ids(menuIDs);
					
					//set userinfo
					RequestUtil.setSession(session, "userInfo", temp);
					
					//get brch info
					//
					
					//reset counts
					userDB.resetTryCounts(temp);
				}
				else
				{
					msgModel.setMsg("用户名或密码错误");  //password error
				}
			}
		}
		return msgModel.toString();
	}
	
	public String resetPassword()
	{
		MsgModel msgModel = new MsgModel();
		userModel.setPassword(ProjectUtil.defaultPassword);
		int result = userDB.resetPassword(userModel);
		if(result==1)
		{
			msgModel.setResult("success");
			msgModel.setMsg("重置密码成功");
		}
		else
		{
			msgModel.setMsg("重置密码失败");
		}
		return msgModel.toString();
	}
	
	public String setPassword()
	{
		MsgModel msgModel = new MsgModel();
		
		if(userModel.getOldPW().equals(userModel.getNewPW()))
		{
			msgModel.setMsg("新旧密码不能相同");
		}
		else
		{
			int result = userDB.setPassword(userModel);
			if(result==1)
			{
				msgModel.setResult("success");
				msgModel.setMsg("设置密码成功");
			}
			else
			{
				msgModel.setMsg("旧密码输入不正确");
			}
		}
		return msgModel.toString();
	}
	
	public String getUserList()
	{
		TableModel tableModel = new TableModel();
		
		List<UserModel> list = userDB.getUserList(userModel);
		int total = userDB.getUserListCounts(userModel);
		
		tableModel.setDraw(userModel.getDraw());
		tableModel.setData(ListToJsonArray(list));
		tableModel.setRecordsFiltered(total);
		tableModel.setRecordsTotal(total);
		
		return tableModel.toString();
	}
	
	public String deleteUser()
	{
		MsgModel msg = new MsgModel();
		
		int result = userDB.deleteUser(userModel);
		if(result != 1)
		{
			msg.setMsg("删除失败");
		}
		else
		{
			msg.setResult("success");
			msg.setMsg("删除成功");
		}
		
		return msg.toString();
	}
	
	public String addUser()
	{
		MsgModel msg = new MsgModel();
		
		int result = userDB.addUser(userModel);
		if(result != 1)
		{
			msg.setMsg("用户名已使用");
		}
		else
		{
			msg.setResult("success");
			msg.setMsg("添加成功");
		}
		
		return msg.toString();
	}
	
	
	
	//////////////////////////////////////////////////////////////////////////
	//menu info
	
	public String getMenu()
	{
		MsgModel msg = new MsgModel();
		
		String menuIDs = userModel.getMenu_ids();
		String[] ids = menuIDs.substring(0,menuIDs.length()-1).split("\\|");

		List<MenuModel> list = userDB.getMenu(ids);
		String menuStr = getMenuJsonstr(list);
		if(menuStr != null)
		{
			msg.setResult("success");
			msg.setMsg(menuStr);
		}
		return msg.toString();
	}
	
	
	public String getAllMenu()
	{
		MsgModel msg = new MsgModel();
		
		List<MenuModel> list = userDB.getAllMenu();
		String menuStr = getMenuJsonstr(list);
		String levelMenus = getLevelMenus();
		
		JSONObject json = new JSONObject();
		json.put("allmenu", menuStr);
		json.put("levels", levelMenus);
		
		if(menuStr != null)
		{
			msg.setResult("success");
			msg.setMsg(json.toString());
		}
		return msg.toString();
	}
	
	public String updateMenu(MenuModel menuModel)
	{
		MsgModel msg = new MsgModel();
		
		int result = userDB.updateMenu(menuModel);
		if(result==1)
		{
			msg.setResult("success");
			msg.setMsg("更新成功");
		}
		return msg.toString();
	}
	
	
	
	
	//////////////////////////////////////////////////////////////////////////
	//private 
	private String getMenuJsonstr(List<MenuModel> list)
	{
		String menuStr = null;
		if(list != null)
		{
			if(list.size()>0)
			{
				JSONObject tempJson = new JSONObject();
				ArrayList<String> menuLabels = new ArrayList<String>();
				for(MenuModel item : list)
				{
					JSONObject menuObj = new JSONObject();
					menuObj.put("title", item.getTitle());
					menuObj.put("url", item.getUrl());
					menuObj.put("menuid", item.getMenu_id());
					menuObj.put("icon", item.getIcon());
					
					if(item.getParent_id()==0)
					{
						JSONArray tempArr = new JSONArray();
						menuObj.put("children", tempArr);
						tempJson.put("_"+item.getMenu_id(), menuObj);
						menuLabels.add("_"+item.getMenu_id());
					}
					else 
					{
						JSONArray tempArr = tempJson.getJSONObject("_"+item.getParent_id()).getJSONArray("children");
						tempArr.put(menuObj);
					}
				}
				
				//get final json
				JSONObject json = new JSONObject();
				JSONArray arr = new JSONArray();
				json.put("data", arr);
				for(int i=0; i<menuLabels.size(); i++)
				{
					arr.put(tempJson.get(menuLabels.get(i)));
				}
				menuStr = json.toString();
			}
		}
		return menuStr;
	}

	private String getLevelMenus()
	{
		List<MenuModel> list = userDB.getLevelMenus();
		return ListToJsonstr(list);
	}	

}
