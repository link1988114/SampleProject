package com.sampleProject.service;

import java.awt.image.BufferedImage;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.bouncycastle.util.encoders.Base64;

import com.sampleProject.util.QRCodeUtil;

public class QrService
{
	public QrService()
	{
		
	}
	
	public void getBase64QRCode(String content, OutputStream outStream) throws Exception
	{
		byte[] temp = QRCodeUtil.getByteQRCode(content, 600, "jpg");
		outStream.write(Base64.encode(temp));	
	}
	
	public void getStreamQRCode(String content, OutputStream outputStream) throws Exception
	{
		BufferedImage bimg = QRCodeUtil.createBufferedImageQRCode(content, 600, "jpg");
		ImageIO.write(bimg, "jpeg", outputStream);
	}
	

}
