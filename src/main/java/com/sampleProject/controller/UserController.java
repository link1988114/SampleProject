package com.sampleProject.controller;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.sampleProject.model.MenuModel;
import com.sampleProject.model.MsgModel;
import com.sampleProject.model.UserModel;
import com.sampleProject.service.UserService;
import com.sampleProject.util.ProjectUtil;
import com.sampleProject.util.RequestUtil;


@Controller
@RequestMapping("/user")
public class UserController
{
	@RequestMapping("/login.do")
	public void login(UserModel userModel, HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		UserService userService = new UserService(userModel);
		String result = userService.checkLogin(session);
		resp.getWriter().write(result);
	}
	
	@RequestMapping("/logout.do")
	public String logout(HttpSession session)
	{
		RequestUtil.clearSession(session);
		return "redirect:/visit.do";
	}
	
	@RequestMapping("/reset.do")
	public void resetPassword(UserModel userModel, HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		UserService userService = new UserService(userModel);
		String result = userService.resetPassword();
		resp.getWriter().write(result);
	}
	
	@RequestMapping("/setting.do")
	public void setPassword(UserModel userModel, HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws Exception
	{
		String userInfo = RequestUtil.getSessionInfo(session, "userInfo");
		if(userInfo.equals("none"))
		{
			MsgModel msg = new MsgModel();
			msg.setMsg("请重新登陆");
			resp.getWriter().write(msg.toString());
			return;
		}
		
		String userName = new Gson().fromJson(userInfo, UserModel.class).getUsername();
		userModel.setUsername(userName);
		
		UserService userService = new UserService(userModel);
		String result = userService.setPassword();
		resp.getWriter().write(result);
	}
	
	@RequestMapping("/list.do")
	public void getUserList(UserModel userModel, HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		String searchValue = req.getParameter("search[value]");
		if(searchValue != null)
		{
			userModel.setSearchValue(searchValue);
		}
		UserService userService = new UserService(userModel);
		String result = userService.getUserList();
		resp.getWriter().write(result);
	}
	
	@RequestMapping("/info.do")
	public void getUserInfo(HttpSession session, HttpServletResponse resp) throws Exception
	{
		String userInfo = RequestUtil.getSessionInfo(session, "userInfo");
		resp.getWriter().write(userInfo);
	}
	
	@RequestMapping("/del.do")
	public void deleteUser(UserModel userModel, HttpServletResponse resp) throws Exception
	{
		UserService userService = new UserService(userModel);
		String result = userService.deleteUser();
		resp.getWriter().write(result);
	}
	
	@RequestMapping("/add.do")
	public void addUser(UserModel userModel, HttpServletResponse resp) throws Exception
	{
		userModel.setPassword(ProjectUtil.defaultPassword);
		userModel.setUserlevel(10);
		
		UserService userService = new UserService(userModel);
		String result = userService.addUser();
		resp.getWriter().write(result);
	}
	
	
	/////////////////////////////////////////////////////////////////////////////
	// menu settings
	
	@RequestMapping("/menu.do")
	public void getMenu(UserModel userModel, HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws Exception
	{
		String userInfo = RequestUtil.getSessionInfo(session, "userInfo");
		if(userInfo.equals("none"))
		{
			MsgModel msg = new MsgModel();
			msg.setMsg("请重新登陆");
			resp.getWriter().write(msg.toString());
			return;
		}
		String menuIDs = new Gson().fromJson(userInfo, UserModel.class).getMenu_ids();
		userModel.setMenu_ids(menuIDs);
		UserService userService = new UserService(userModel);
		String result = userService.getMenu();
		resp.getWriter().write(result);
		
	}
	
	
	@RequestMapping("/allmenu.do")
	public void getAllMenu(UserModel userModel, HttpServletResponse resp) throws Exception
	{
		UserService userService = new UserService(userModel);
		String result = userService.getAllMenu();
		resp.getWriter().write(result);
	}
	
	
	@RequestMapping("/updatemenu.do")
	public void updateMenu(MenuModel menuModel, HttpServletResponse resp) throws Exception
	{
		UserService userService = new UserService(null);
		String result = userService.updateMenu(menuModel);
		resp.getWriter().write(result);
	}
	
	

}
