package com.sampleProject.dao;


import com.sampleProject.dao.sys.BaseDao;
import com.sampleProject.model.WxModel;

public class DaoWx extends BaseDao
{
	public WxModel getToken(WxModel wxModel)
	{
		return (WxModel)db.selectOne(sqls.select_wx_getToken, wxModel);
	}
	
	public int saveToken(WxModel wxModel)
	{
		return db.update(sqls.insert_wx_saveToken, wxModel);
	}
	
	
	

	
}
