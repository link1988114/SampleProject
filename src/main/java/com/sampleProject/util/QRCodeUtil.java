package com.sampleProject.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;



/**
 * 
 * 	QRCodeUtil.createQRCode(outStream, content, 600, "jpg");
	byte[] temp = QRCodeUtil.getByteQRCode(content, 600, "png");
	outStream.write(Base64.encode(temp));
 * 
 * 
 * @author 123
 *
 */



public class QRCodeUtil
{
	public static boolean createBarCode(File destFile, String content, int width, int height, String imgFormat)
	{
		boolean flag = false;
		try
		{
			BitMatrix bitMatrix = makeBarCodeBitMatrix(content, width, height);
			MatrixToImageWriter.writeToFile(bitMatrix, imgFormat, destFile);
			flag = true;
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	public static boolean createBarCode(OutputStream outStream, String content, int width, int height, String imgFormat)
	{
		boolean flag = false;
		try
		{
			BitMatrix bitMatrix = makeBarCodeBitMatrix(content, width, height);
			MatrixToImageWriter.writeToStream(bitMatrix, imgFormat, outStream);
			flag = true;
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	public static byte[] getByteBarCode(String content, int width, int height, String imgFormat)
	{
		byte[] result = null;
		try
		{
			BitMatrix bitMatrix = makeBarCodeBitMatrix(content, width, height);
	        ByteArrayOutputStream byteImg = new ByteArrayOutputStream();
	        MatrixToImageWriter.writeToStream(bitMatrix, imgFormat, byteImg);
	        result = byteImg.toByteArray();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			result = null;
		}
		return result;
	}
	
	public static byte[] getByteBarCodeWithText(String content, int width, int height, String imgFormat)
	{
		byte[] result = null;
		try
		{
			BitMatrix bitMatrix = makeBarCodeBitMatrix(content, width, height);
			BufferedImage img = MatrixToImageWriter.toBufferedImage(bitMatrix);
			BufferedImage newImg = insertWords(img, content);
			
	        ByteArrayOutputStream byteImg = new ByteArrayOutputStream();
	        ImageIO.write(newImg, imgFormat, byteImg);
	        result = byteImg.toByteArray();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			result = null;
		}
		return result;
	}
	
	
	
	public static boolean createQRCode(File destFile, String content, int qrSize, String imgFormat) throws Exception
	{
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, qrSize, qrSize, hints);
        MatrixToImageWriter.writeToFile(bitMatrix, imgFormat, destFile);
        return true;
	}
	
	public static boolean createQRCode(OutputStream outStream, String content, int qrSize, String imgFormat) throws Exception
	{
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, qrSize, qrSize, hints);
        MatrixToImageWriter.writeToStream(bitMatrix, imgFormat, outStream);
        return true;
	}
	
	public static byte[] getByteQRCode(String content, int qrSize, String imgFormat) throws Exception
	{
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, qrSize, qrSize, hints);
        ByteArrayOutputStream byteImg = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, imgFormat, byteImg);
        return byteImg.toByteArray();
	}
	
	public static BufferedImage createBufferedImageQRCode(String content, int qrSize, String imgFormat) throws Exception
	{
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, qrSize, qrSize, hints);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
	}
	
	public void createImageFile(String content, int qrsize, String imgFormat, File file) throws Exception
	{
		BufferedImage img = createBufferedImageQRCode(content, qrsize, imgFormat);
		ImageIO.write(img, imgFormat, file);
	}
	
	
	/**
     * 随机生成指定长度的验证码
     *
     * @param length 验证码长度
     * @return 生成的验证码
     */
	public static String generateNumCode(int length) 
    {
        String val = "";
        String charStr = "char";
        String numStr = "num";
        Random random = new Random();

        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) 
        {
            String charOrNum = random.nextInt(2) % 2 == 0 ? charStr : numStr;
            //输出字母还是数字
            if (charStr.equalsIgnoreCase(charOrNum)) 
            {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } 
            else if (numStr.equalsIgnoreCase(charOrNum)) 
            {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }
	
	
	
    
    /**
     * 解析指定路径下的二维码图片
     *
     * @param filePath 二维码图片路径
     * @return
     */
    public static String readQRCode(String filePath) throws Exception 
    {
        String content = "";
        
        File file = new File(filePath);
        BufferedImage image = ImageIO.read(file);
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        Binarizer binarizer = new HybridBinarizer(source);
        BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
        Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
        hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
        MultiFormatReader formatReader = new MultiFormatReader();
        Result result = formatReader.decode(binaryBitmap, hints);
        //System.out.println("result:" + result.toString());
        //System.out.println("resultFormat:" + result.getBarcodeFormat());
        //System.out.println("resultText:" + result.getText());
        
        content = result.getText();
        return content;
    }
    
    public static String readQRCode(InputStream inStream) throws Exception 
    {
        String content = "";
        
        BufferedImage image = ImageIO.read(inStream);
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        Binarizer binarizer = new HybridBinarizer(source);
        BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
        Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
        hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
        MultiFormatReader formatReader = new MultiFormatReader();
        Result result = formatReader.decode(binaryBitmap, hints);
        //System.out.println("result:" + result.toString());
        //System.out.println("resultFormat:" + result.getBarcodeFormat());
        //System.out.println("resultText:" + result.getText());
        
        content = result.getText();
        return content;
    }
    
    public static String readBarCode(String filePath)
    {
    	String content = "";
    	try
		{
    		BufferedImage image = ImageIO.read(new File(filePath));
			if (image == null) 
			{
				System.out.println("no image");
				content = "";
			}
			else 
			{
				LuminanceSource source = new BufferedImageLuminanceSource(image);
				BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
				content = new MultiFormatReader().decode(bitmap, null).getText();
			}
		} 
    	catch (Exception e)
		{
			e.printStackTrace();
			content = "";
		}
    	return content;
    }
    
    public static String readBarCode(InputStream inStream)
    {
    	String content = "";
    	try
		{
    		BufferedImage image = ImageIO.read(inStream);
			if (image == null) 
			{
				System.out.println("no image");
				content = "";
			}
			else 
			{
				LuminanceSource source = new BufferedImageLuminanceSource(image);
				BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
				content = new MultiFormatReader().decode(bitmap, null).getText();
			}
		} 
    	catch (Exception e)
		{
			e.printStackTrace();
			content = "";
		}
    	return content;
    }
    
    
    
    ///////////////////////////////////////////////////////////////////////
    
    private static BitMatrix makeBarCodeBitMatrix(String content, int width, int height)
    {
    	BitMatrix bitMatrix = null;
		try
		{
			int codeWidth = 
					3 + // start guard
					(7 * 6) + // left bars
					5 + // middle guard
					(7 * 6) + // right bars
					3; // end guard
			codeWidth = Math.max(codeWidth, width);
			bitMatrix = new MultiFormatWriter().encode(content,BarcodeFormat.CODE_128, codeWidth, height, null);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			bitMatrix = null;
		}
		return bitMatrix;
    }
    
    
    private static BufferedImage insertWords(BufferedImage image, String words)
    {
    	if(words != null && !words.equals(""))
    	{
    		BufferedImage outImage = new BufferedImage(image.getWidth(), image.getHeight()+20, image.getType());
    		Graphics2D g2d = outImage.createGraphics();
            
	        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
	        Stroke s = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
	        g2d.setStroke(s);
    		
    		g2d.setColor(Color.WHITE);
            g2d.fillRect(0,0,600,600);
            g2d.setColor(Color.BLACK);
            
            g2d.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
            Color color=new Color(0, 0, 0);
            g2d.setColor(color);
            g2d.setFont(new Font("微软雅黑", Font.PLAIN, 18));
            int strWidth = g2d.getFontMetrics().stringWidth(words);
            int wordStartX=(image.getWidth() - strWidth) / 2;
            int wordStartY=image.getHeight()+20;

            g2d.drawString(words, wordStartX, wordStartY);
            g2d.dispose();
            outImage.flush();
            return outImage;
    	}
    	else 
    	{
    		return null;
    	}
    }
    
    
    
}
