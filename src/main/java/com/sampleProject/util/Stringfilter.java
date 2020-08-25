package com.sampleProject.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Stringfilter
{
	public static String numOnly(String str)
	{
		return str.replaceAll("[^0-9]", "");
	}
	
	public static String numOnly2(String str)
	{
		String temp = str.replaceAll("[^0-9]", "");
		if(temp.equals(""))
		{
			temp = "0";
		}
		return temp;
	}

	////////////////////////////////////////////////////////////////////////
	//xml
	
	public static String getXmlTagValue(String xmlstr, String tag)
	{
		String result = "";
		
		Pattern pattern = Pattern.compile("<"+tag+">.*</"+tag+">");
		Matcher matcher = pattern.matcher(xmlstr);
		if(matcher.find())
		{
			//System.out.println(matcher.group());
			result = matcher.group().replace("<"+tag+"><![CDATA[","");
			result = result.replace("]]></"+tag+">","");
		}
		return result;
	}
	
	
	/////////////////////////////////////////////////////////////////////////
	
	public static String upperCapital(String str)
	{
        char[] cs = str.toCharArray();
        if (97 <= cs[0] && cs[0] <= 122) 
        {
        	cs[0] -= 32;
        }
        return String.valueOf(cs);
	}
}
