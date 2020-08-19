package com.sampleProject.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.aspectj.weaver.ast.Var;

public class ProjectUtil
{
	public static String defaultPassword = Encrypt.Md5_32("111111");
	
	public static String regExp_scienceNumber = "-?[0-9]\\.[0-9]+(E|e)(\\+|-)[0-9]+";
	
	
	
	public static String identifyNo15To18(String identifyNo15)
	{
		String retId = "";  
        String id17 = "";  
        int sum = 0;  
        int y = 0;  
        // 定义数组存放加权因子（weight factor）  
        int[] wf = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };  
        // 定义数组存放校验码（check code）  
        String[] cc = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };  
        if (identifyNo15.length() != 15) {  
            return identifyNo15;  
        }  
        // 加上两位年19  
        id17 = identifyNo15.substring(0, 6) + "19" + identifyNo15.substring(6);  
        // 十七位数字本体码加权求和  
        for (int i = 0; i < 17; i++) {  
            sum = sum + Integer.valueOf(id17.substring(i, i + 1)) * wf[i];  
        }  
        // 计算模  
        y = sum % 11;  
        // 通过模得到对应的校验码 cc[y]  
        retId = id17 + cc[y];  
        return retId;
	}
	
	
	public static String getNumRndCode(int length)
	{
		String result = "";
		for(int i=0; i<length; i++)
		{
			String item = new Random().nextInt(10)+"";
			result += item;
		}
		return result;
	}
	
	public static List<String> getUniqueNumRndCode(long counts, int codeLength)
	{
		if(codeLength<1)
		{
			codeLength = 1;
		}
		long start = (long)Math.pow(10, codeLength-1);
		long end = (long)Math.pow(10, codeLength)-1;
		
		if(codeLength==1) 
		{
			start = 0;
		}
		
		List<String> serial = new ArrayList<String>();
		for(long i=start; i<=end; i++)
		{
			serial.add(i+"");
		}
		
		List<String> output = new ArrayList<String>();
		long max = counts>serial.size() ? serial.size() : counts;
		for(long i=0; i<max; i++)
		{
			int index = (int)Math.floor(Math.random()*serial.size());
			String pick = serial.get(index);
			serial.remove(index);
			output.add(pick);
		}
		
		return output;
	}
	
	
	public static String scienceToNumberStr(String scienceNumber)
	{
		String output = "";
		
		String temp[] = scienceNumber.split("E|e");
		char head = temp[0].charAt(0);
		char indexFlag = temp[1].charAt(0);
		if(indexFlag !='-' && indexFlag !='+')
		{
			temp[1] = "+"+temp[1];
			indexFlag = '+';
		}
		String numBody = temp[0].substring(1);
		String index = temp[1].substring(1);
		
		if(indexFlag == '-')
		{
			for(int i=0; i<Integer.parseInt(index); i++)
			{
				numBody = "0"+numBody;
			}
			output = numBody.replace(".", "");
			output = output.substring(0,1)+"."+output.substring(1,output.length());
			output = head + output;
		}
		else 
		{
			String[] numBodyTemp = numBody.split("\\.");
			int currentLen = numBodyTemp[1].length();
			int indexLen = Integer.parseInt(index);
			if(currentLen > indexLen)
			{
				numBody = numBodyTemp[0]+numBodyTemp[1].substring(0,indexLen)+"."+numBodyTemp[1].substring(indexLen,numBodyTemp[1].length());
				output = head + numBody;
			}
			else 
			{
				numBody = numBody.replace(".", "");
				for(int i=0; i<(indexLen - currentLen); i++)
				{
					numBody = numBody+"0";
				}
				output = head + numBody;
			}
		}
		return output;
	}

}
