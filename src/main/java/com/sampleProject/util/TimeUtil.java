package com.sampleProject.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @param args
 * time sample:  20170101  2017/01/01   2017:01:01   2017 01 01
 */

public class TimeUtil
{
	public static int[] monthDays={31,28,31,30,31,30,31,31,30,31,30,31};
	
	public static String getNow()
	{
		DateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		return df.format(new Date());
	}
	
	public static Date getNowDate()
	{
		return new Date();
	}
	
	public static Date stringToDate(String strDate, String format) throws Exception
	{
		DateFormat df = new SimpleDateFormat(format);
		return df.parse(strDate);
	}
	
	public static String dateToString(Date date, String format)
	{
		DateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}
	
	
	public static String formatTime(String date)
	{
		return date.replaceAll("-|:|/| ", "");
	}
	public static String formatTime(Date date)
	{
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		return df.format(date);
	}
	
	public static boolean isLeapYear(String date)
	{
		String temp = date.substring(0,4);
		int year = Integer.parseInt(temp);
		if(year%4==0 && year%100 != 0 || year%400==0)
		{
			return true;
		}
		return false;
	}
	@SuppressWarnings("deprecation")
	public static boolean isLeapYear(Date date)
	{
		int year = date.getYear()+1900;
		if(year%4==0 && year%100 != 0 || year%400==0)
		{
			return true;
		}
		return false;
	}
	public static String getYearDays(String date)
	{
		if(isLeapYear(date))
		{
			return 366+"";
		}
		else 
		{
			return 365+"";
		}
	}
	public static String getMonthDays(String year, String monthindex)
	{
		//month index 1~12
		int index = Integer.parseInt(monthindex);
		if(index<1){index=1;};
		if(index>12){index=12;};
		int temp = monthDays[index-1];
		if(index==2)
		{
			if(isLeapYear(year))
			{
				temp=29;
			}
		}
		return temp+"";
	}
	/////////////////////////////////////////////////////////////////////////////
	
	public static String dayCounts(Date date1, Date date2)
	{
		long delta = date1.getTime()>date2.getTime()?date1.getTime()-date2.getTime():date2.getTime()-date1.getTime();
		long days = delta/1000/60/60/24+1;
		return String.valueOf(days);
	}
	public static String dayCounts(String datestr1, String datestr2)
	{
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		try
		{
			Date date1 = df.parse(datestr1);
			Date date2 = df.parse(datestr2);
			long delta = date1.getTime()>date2.getTime()?date1.getTime()-date2.getTime():date2.getTime()-date1.getTime();
			long days = delta/1000/60/60/24+1;
			return String.valueOf(days);
		} 
		catch (Exception e)
		{
			return "-1";
		}
	}

	/////////////////////////////////////////////////////////////////////////////
	
	@SuppressWarnings("deprecation")
	public static String getEndOfLastMonth(Date timeDate)
	{
		int year = timeDate.getYear()+1900;
		int monthIndex = timeDate.getMonth()-1;
		if(monthIndex<0)	
		{
			monthIndex += 12;
			year--;
		}
		
		Date temp = new Date();
		temp.setYear(year-1900);
		temp.setMonth(monthIndex);
		int totaldays = monthDays[monthIndex];
		if(monthIndex == 1 && isLeapYear(String.valueOf(year)))
		{
			totaldays++;
		}
		temp.setDate(totaldays);
		
		return new SimpleDateFormat("yyyyMMdd").format(temp);
	}
	
	@SuppressWarnings("deprecation")
	public static String getEndOfMonthBeforeLastMonth(Date timeDate)
	{
		int year = timeDate.getYear()+1900;
		int monthIndex = timeDate.getMonth()-2;
		if(monthIndex<0)	
		{
			monthIndex += 12;
			year--;
		}
		
		Date temp = new Date();
		temp.setYear(year-1900);
		temp.setMonth(monthIndex);
		int totaldays = monthDays[monthIndex];
		if(monthIndex == 1 && isLeapYear(String.valueOf(year)))
		{
			totaldays++;
		}
		temp.setDate(totaldays);
		
		return new SimpleDateFormat("yyyyMMdd").format(temp);
	}
	
	@SuppressWarnings("deprecation")
	public static String getEndOfLastYear(Date timeDate)
	{
		int year = timeDate.getYear()+1900-1;
		int monthIndex = 11;
		
		Date temp = new Date();
		temp.setYear(year-1900);
		temp.setMonth(monthIndex);
		temp.setDate(31);
		
		return new SimpleDateFormat("yyyyMMdd").format(temp);
	}
	
	@SuppressWarnings("deprecation")
	public static String getEndOfYearBeforeLastYear(Date timeDate)
	{
		int year = timeDate.getYear()+1900-2;
		int monthIndex = 11;
		
		Date temp = new Date();
		temp.setYear(year-1900);
		temp.setMonth(monthIndex);
		temp.setDate(31);
		
		return new SimpleDateFormat("yyyyMMdd").format(temp);
	}
	
	@SuppressWarnings("deprecation")
	public static String getEndOfLastYearLastMonth(Date timeDate)
	{
		int year = timeDate.getYear()+1900-1;
		int monthIndex = timeDate.getMonth()-1;
		if(monthIndex<0)	
		{
			monthIndex += 12;
			year--;
		}
		
		Date temp = new Date();
		temp.setYear(year-1900);
		temp.setMonth(monthIndex);
		int totaldays = monthDays[monthIndex];
		if(monthIndex == 1 && isLeapYear(String.valueOf(year)))
		{
			totaldays++;
		}
		temp.setDate(totaldays);
		
		return new SimpleDateFormat("yyyyMMdd").format(temp);
	}
	
	@SuppressWarnings("deprecation")
	public static String getSameDayOfLastYear(Date timeDate)
	{
		int year = timeDate.getYear()+1900-1;
		int monthIndex = timeDate.getMonth();
		int day = timeDate.getDate();
		
		Date temp = new Date();
		temp.setYear(year-1900);
		temp.setMonth(monthIndex);
		if(monthIndex==1)
		{
			if(isLeapYear(String.valueOf(year+1)))
			{
				if(day==29)
				{
					day=28;
				}
			}
			else 
			{
				if(day==28)
				{
					if(isLeapYear(String.valueOf(year)))
					{
						day=29;
					}
				}
			}
		}
		temp.setDate(day);
		
		return new SimpleDateFormat("yyyyMMdd").format(temp);
	}
	
	@SuppressWarnings("deprecation")
	public static String getFirstOfMonth(Date timeDate)
	{
		int year = timeDate.getYear()+1900;
		int monthIndex = timeDate.getMonth();
		
		Date temp = new Date();
		temp.setYear(year-1900);
		temp.setMonth(monthIndex);
		temp.setDate(1);
		
		return new SimpleDateFormat("yyyyMMdd").format(temp);
	}
	
	@SuppressWarnings("deprecation")
	public static String getFirstOfYear(Date timeDate)
	{
		int year = timeDate.getYear()+1900;
		
		Date temp = new Date();
		temp.setYear(year-1900);
		temp.setMonth(0);
		temp.setDate(1);
		
		return new SimpleDateFormat("yyyyMMdd").format(temp);
	}
	
	@SuppressWarnings({ "deprecation" })
	public static String getFirstOfLastYear(Date timeDate)
	{
		int year = timeDate.getYear()+1900-1;
		
		Date temp = new Date();
		temp.setYear(year-1900);
		temp.setMonth(0);
		temp.setDate(1);
		
		return new SimpleDateFormat("yyyyMMdd").format(temp);
	}
	
	@SuppressWarnings("deprecation")
	public static String getFirstOfYearBeforeLastYear(Date timeDate)
	{
		int year = timeDate.getYear()+1900-2;
		
		Date temp = new Date();
		temp.setYear(year-1900);
		temp.setMonth(0);
		temp.setDate(1);
		
		return new SimpleDateFormat("yyyyMMdd").format(temp);
	}
	
	@SuppressWarnings("deprecation")
	public static String getFirstOfLastMonth(Date timeDate)
	{
		int year = timeDate.getYear()+1900;
		int monthIndex = timeDate.getMonth()-1;
		if(monthIndex<0)	
		{
			monthIndex += 12;
			year--;
		}
		
		Date temp = new Date();
		temp.setYear(year-1900);
		temp.setMonth(monthIndex);
		temp.setDate(1);
		
		return new SimpleDateFormat("yyyyMMdd").format(temp);
	}
	
	@SuppressWarnings("deprecation")
	public static String getFirstOfLastYearMonth(Date timeDate)
	{
		int year = timeDate.getYear()+1900-1;
		int monthIndex = timeDate.getMonth();
		
		Date temp = new Date();
		temp.setYear(year-1900);
		temp.setMonth(monthIndex);
		temp.setDate(1);
		
		return new SimpleDateFormat("yyyyMMdd").format(temp);
	}
	
	@SuppressWarnings("deprecation")
	public static String getFirstOfLastYearDec(Date timeDate)
	{
		int year = timeDate.getYear()+1900-1;
		int monthIndex = 11;
		
		Date temp = new Date();
		temp.setYear(year-1900);
		temp.setMonth(monthIndex);
		temp.setDate(1);
		
		return new SimpleDateFormat("yyyyMMdd").format(temp);
	}
	
	@SuppressWarnings("deprecation")
	public static String getFirstOfYearBeforeLastYearDec(Date timeDate)
	{
		int year = timeDate.getYear()+1900-2;
		int monthIndex = 11;
		
		Date temp = new Date();
		temp.setYear(year-1900);
		temp.setMonth(monthIndex);
		temp.setDate(1);
		
		return new SimpleDateFormat("yyyyMMdd").format(temp);
	}
	
	@SuppressWarnings("deprecation")
	public static String getFirstOfLastYearLastMonth(Date timeDate)
	{
		int year = timeDate.getYear()+1900-1;
		int monthIndex = timeDate.getMonth()-1;
		if(monthIndex<0)	
		{
			monthIndex += 12;
			year--;
		}
		
		Date temp = new Date();
		temp.setYear(year-1900);
		temp.setMonth(monthIndex);
		temp.setDate(1);
		
		return new SimpleDateFormat("yyyyMMdd").format(temp);
	}
	
	
	/////////////////////////////////////////////////////////////////////////////
	
	public static void showTimeUtil(Date time1)
	{
		System.out.println("当前:"+formatTime(time1));
		System.out.println("上月末:"+getEndOfLastMonth(time1));
		System.out.println("前月末:"+getEndOfMonthBeforeLastMonth(time1));
		System.out.println("去年末:"+getEndOfLastYear(time1));
		System.out.println("前年末:"+getEndOfYearBeforeLastYear(time1));
		System.out.println("去年上月末:"+getEndOfLastYearLastMonth(time1));
		System.out.println("去年同期:"+getSameDayOfLastYear(time1));
		System.out.println("本月初:"+getFirstOfMonth(time1));
		System.out.println("本年初:"+getFirstOfYear(time1));
		System.out.println("去年初:"+getFirstOfLastYear(time1));
		System.out.println("前年初:"+getFirstOfYearBeforeLastYear(time1));
		System.out.println("上月初:"+getFirstOfLastMonth(time1));
		System.out.println("去年同期月初:"+getFirstOfLastYearMonth(time1));
		System.out.println("去年上月月初:"+getFirstOfLastYearLastMonth(time1));
	}
	
	
	/////////////////////////////////////////////////////////////////////////////
	
	public static void main(String[] args)
	{
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String today = "20170128";
		String target = "20150101";
		
		today = today.replaceAll("-|:|/| ","");
		target = target.replaceAll("-|:|/", "");
		
		System.out.println(today);
		

		
		try
		{
			Date time1 = null;
			time1 = df.parse(today);
			
			showTimeUtil(time1);	
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
	

}
