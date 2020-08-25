package com.sampleProject.model;


public class MsgModel extends BaseModel
{
	private String result = "failed";
	private String msg = "";
	
	
	
	public String getResult()
	{
		return result;
	}
	public void setResult(String result)
	{
		this.result = result;
	}
	public String getMsg()
	{
		return msg;
	}
	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	
	
	
	public void simplePack(boolean flag, String trueMsg, String falseMsg)
	{
		if(flag)
		{
			this.result = "success";
			this.msg = trueMsg;
		}
		else 
		{
			this.result = "error";
			this.msg = falseMsg;
		}
	}
	

}
