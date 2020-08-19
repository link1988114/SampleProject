package com.sampleProject.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import sun.misc.BASE64Decoder;

@SuppressWarnings("restriction")
public class TesseractUtil
{
	private static String tessData = "D:\\apache-tomcat-7.0.68\\webapps\\zyxf\\tessdata";
	//private static String tessData = "D:\\Develop\\Eclipse\\zyxf\\tessdata";
	private static String languagePack = "eng";
	
	
	
	public static String base64StrOCR(String base64Str)
    {
		String ocrResult = "-";
		try
		{
			base64Str = base64Str.replace("data:image/jpeg;base64,", "");
			BASE64Decoder decoder = new BASE64Decoder();
	    	byte[] bArr = decoder.decodeBuffer(base64Str);
	    	for(byte b : bArr)
	    	{
	    		if(b<0)
	    		{
	    			b+=256;
	    		}
	    	}
	    	ITesseract instance = new Tesseract();
            //如果未将tessdata放在根目录下需要指定绝对路径
            instance.setDatapath(tessData);
            // 我们需要指定识别语种
            instance.setLanguage(languagePack);
            // 指定识别图片
            ByteArrayInputStream in = new ByteArrayInputStream(bArr);
        	BufferedImage image = ImageIO.read(in);
        	
            //File imgDir = new File("test_chinese_07.PNG");
            //long startTime = System.currentTimeMillis();
        	ImageIO.scanForPlugins();
            ocrResult = instance.doOCR(image);
            
            in.close();
            instance = null;
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
    	//System.out.println(ocrResult);
        return ocrResult;
    }
	
	
	public static String imgFileOCR(File imgFile)
	{
		String ocrResult = "-";
		try
		{
			InputStream in = new FileInputStream(imgFile);
	    	ITesseract instance = new Tesseract();
            //如果未将tessdata放在根目录下需要指定绝对路径
            instance.setDatapath(tessData);
            // 我们需要指定识别语种
            instance.setLanguage(languagePack);
            // 指定识别图片
        	BufferedImage image = ImageIO.read(in);
        	
            //File imgDir = new File("test_chinese_07.PNG");
            //long startTime = System.currentTimeMillis();
            ImageIO.scanForPlugins();
        	ocrResult = instance.doOCR(image);
            
            in.close();
            instance = null;
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
    	//System.out.println(ocrResult);
        return ocrResult;
	}
	

}
