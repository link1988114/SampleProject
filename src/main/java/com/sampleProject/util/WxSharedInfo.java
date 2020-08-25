package com.sampleProject.util;

public class WxSharedInfo
{
	private static WxSharedInfo instance = null;
	
	public String appid = "wxa94fbe83acb237cf";
	public String secret = "b6c6fc24cbfcb6ed9279fe5bcd77e4c2";
	public String baseurl = "http://www.postsz.cn/zyxf/";
	public String imgPath = "http://www.postsz.cn/zyxf/upload/";
	//public String imgPath = "http://192.168.99.219:7650/WebRoot/productImg/";
	//http://218.4.142.147/weixinportal/bonusReg.html?openid=111111
	public String entranceBase = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri=";
	public String entranceTail = "&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
	
	
	public String db_env = "su-kuaihuoqu-test-m07h8";
	public String collection = "order";
	public String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret;
	public String query_url = "https://api.weixin.qq.com/tcb/databasequery?access_token=";
	public String count_url = "https://api.weixin.qq.com/tcb/databasecount?access_token=";
	
	private WxSharedInfo()
	{

	}
	
	public static WxSharedInfo GetInstance()
	{
		if(instance==null)
		{
			instance = new WxSharedInfo();
		}
		return instance;
	}
	
	
}
