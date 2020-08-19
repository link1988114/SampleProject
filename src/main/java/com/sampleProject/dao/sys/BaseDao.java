package com.sampleProject.dao.sys;

import com.sampleProject.dao.SQLgetter;

public class BaseDao
{
	protected MybatisConnection db = new MybatisConnection();
	protected SQLgetter sqls = SQLgetter.getInstance();
}
