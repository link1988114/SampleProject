package com.sampleProject.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;


public class ExcelWriter
{
	private File file = null;
	@SuppressWarnings("unused")
	private String filesize;
	@SuppressWarnings("unused")
	private String path = "";
	@SuppressWarnings("unused")
	private String filenameNoSuffix = "";
	private String suffix = "";
	@SuppressWarnings("unused")
	private String title = "";
	@SuppressWarnings("unused")
	private String jsonStr = "";
	
	private CellStyle cellStyle;
	private Workbook workbook;
	private boolean isNewFile = false;

	/*
	 * 
	 * jsonstr:  {data:[
	 *                    {dataKeys[0]:"",dataKeys[1]:"",....},
	 *                    {dataKeys[0]:"",dataKeys[1]:"",....},....
	 *                  ]
	 *              }
	 * 
	 * initial------create-------writedata------close
	 * 
	 */
	
	/**write in xls or xlsx
	 * 
	 * ExcelWriter xlsW = new ExcelWriter(path, name, "xls");   //"or xlsx"
	 * xlsW.create(path,filenameNoSuffix,"xls");  //"or xlsx"
	 * xlsW.writeAllData(jsonstr, title, displayKeys, dataKeys);
	 * xlsW.close();
	 * 
	 */
	

	public ExcelWriter()
	{
		workbook = null;
	}
	
	public String create(String path, String filenameNoSuffix, String type)
	{
		String result = "";
		
		if("xlsx".equals(type))
		{
			suffix = "xlsx";
		}
		else
		{
			suffix = "xls";
		}
		
		File file = new File(path+File.separator+filenameNoSuffix+"."+suffix);
		isNewFile = true;
		if(file.exists())
		{
			file.delete();
		}
		try
		{
			file.createNewFile();
			this.file = file;
			this.path = path;
			this.filenameNoSuffix = filenameNoSuffix;
			result = "success";
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			result = "error";
		}
		
		return result;
	}
	
	
	public void open(File file)
	{
		this.file = file;
		this.path = file.getAbsolutePath();
		String[] temp = file.getName().split("\\.");
		this.suffix = temp[temp.length-1];
		this.filenameNoSuffix = file.getName().substring(0,file.getName().length()-this.suffix.length());
		this.isNewFile = false;
	}
	
	public void close()
	{
		try
		{
			this.workbook.close();
		} 
		catch (Exception e)
		{
			//e.printStackTrace();
		}
	}
	
	public String writeAllData(String jsonstr, String title, String[] displayKeys, String[] dataKeys)
	{
		if(file==null)
		{
			return "nofile";
		}
		
		int rowcount = 0;
		String result = "";
		try
		{
			if(suffix.equals("xls"))
			{
				FileOutputStream fsout = new FileOutputStream(file);	
				
				if("xlsx".equals(suffix))
				{
					workbook = new XSSFWorkbook();       //xlsx
				}
				else
				{
					workbook = new HSSFWorkbook();  //xls
				}
							
				Sheet sheet = workbook.createSheet();
				Row r = null;
				Cell c = null;
				
				CellStyle cs1 = workbook.createCellStyle();
				Font font1 = workbook.createFont();
				//DataFormat df = workbook.createDataFormat();
				font1.setFontHeightInPoints((short)12);
				cs1.setFont(font1);
				cs1.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
				//cs1.setFillPattern((short)0);
				
				workbook.setSheetName(0, "sheet1");
				
				//write in title
				if(title != null)
				{
					r = sheet.createRow(rowcount);
					r.setHeight((short)(256*1.5));
					rowcount++;
					c = r.createCell(0);
					c.setCellStyle(cs1);
					c.setCellValue(title);
				}
				
				//write in keys
				r = sheet.createRow(rowcount);
				r.setHeight((short)(256*1.5));
				rowcount++;
				for(int i=0;i<displayKeys.length;i++)
				{
					sheet.setColumnWidth(i, 256*4*displayKeys[i].getBytes().length/2);
					c = r.createCell(i);
					c.setCellStyle(cs1);
					c.setCellValue(displayKeys[i]);
				}
				
				//write in data
				JSONObject json = new JSONObject(jsonstr);
				JSONArray data = json.getJSONArray("data");
				int length = data.length();
				for(int i=0; i<length;i++)
				{
					r=sheet.createRow(i+rowcount);
					r.setHeight((short)(256*1.5));
					
					int cellNum = 0;
					JSONObject temp = data.optJSONObject(i);
					for(cellNum=0;cellNum<displayKeys.length;cellNum++)
					{
						String value = temp.optString(dataKeys[cellNum]);
						int currwidth = sheet.getColumnWidth(cellNum);
						int newwidth = 256*4*value.getBytes().length/2;
						sheet.setColumnWidth(cellNum, newwidth>currwidth?newwidth:currwidth);
						c = r.createCell(cellNum);
						c.setCellStyle(cs1);
						c.setCellValue(value);
					}
				}
				workbook.write(fsout);
				fsout.flush();
				fsout.close();

				isNewFile = false;
				result = "success";
				filesize = String.valueOf(file.length());
			}
			else if(suffix.equals("xlsx"))
			{
				writeInXlsxBulk(jsonstr, title, displayKeys, dataKeys);
			}
			else 
			{
				filesize = "-2";
				result = "error";
			}
			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			filesize = "-1";
			result = "error";
		}
		return result;
	}
	
	
	public void writeInLine(String[] data, int sheetindex)
	{	
		if(workbook == null)
		{
			openWorkbook();
		}
		
		Sheet currsheet = null;
		int lastRowIndex = 0;
		Row r = null;
		if(isNewFile)
		{
			currsheet = workbook.createSheet("sheet1");
			isNewFile = false;
			r = currsheet.createRow(0);
		}
		else 
		{
			currsheet = workbook.getSheetAt(sheetindex);
			lastRowIndex = currsheet.getLastRowNum();
			r = currsheet.createRow(lastRowIndex+1);
			//System.out.println(lastRowIndex+":"+isNewFile);
		}
		r.setHeight((short)(256*1.5));

		try
		{
			FileOutputStream fsout = new FileOutputStream(file);
			for(int i=0;i<data.length;i++)
			{
				Cell c = r.createCell(i);
				c.setCellStyle(cellStyle);
				int currwidth = currsheet.getColumnWidth(i);
				int newwidth = 256*4*data[i].getBytes().length/3;
				currsheet.setColumnWidth(i, newwidth>currwidth?newwidth:currwidth);
				c.setCellValue(data[i]);
			}
		
			workbook.write(fsout);
			fsout.flush();
			fsout.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	
	public String writeInXlsxBulk(String jsonstr, String title, String[] displayKeys, String[] dataKeys)
	{
		if(file==null)
		{
			return "error";
		}
		
		int rowcount = 0;
		String result = "";
		try
		{
			SXSSFWorkbook wb = new SXSSFWorkbook(100);
			
			Sheet sheet = wb.createSheet();
			Row r = null;
			Cell c = null;
			
			CellStyle cs1 = wb.createCellStyle();
			Font font1 = wb.createFont();
			//DataFormat df = workbook.createDataFormat();
			font1.setFontHeightInPoints((short)12);
			cs1.setFont(font1);
			cs1.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));
			//cs1.setFillPattern((short)0);
			wb.setSheetName(0, "sheet1");
			
			//write in title
			if(title != null)
			{
				r = sheet.createRow(rowcount);
				r.setHeight((short)(256*1.5));
				rowcount++;
				c = r.createCell(0);
				c.setCellStyle(cs1);
				c.setCellValue(title);
			}
			
			//write in keys
			r = sheet.createRow(rowcount);
			r.setHeight((short)(256*1.5));
			rowcount++;
			for(int i=0;i<displayKeys.length;i++)
			{
				sheet.setColumnWidth(i, 256*4*displayKeys[i].getBytes().length/2);
				c = r.createCell(i);
				c.setCellStyle(cs1);
				c.setCellValue(displayKeys[i]);
			}
			
			//write in data
			JSONObject json = new JSONObject(jsonstr);
			JSONArray data = json.getJSONArray("data");
			int length = data.length();
			for(int i=0; i<length;i++)
			{
				r=sheet.createRow(i+rowcount);
				r.setHeight((short)(256*1.5));
				
				int cellNum = 0;
				JSONObject temp = data.optJSONObject(i);
				for(cellNum=0;cellNum<displayKeys.length;cellNum++)
				{
					String value = temp.optString(dataKeys[cellNum]);
					CellUtil.createCell(r, cellNum, value);
				}
			}
			
			FileOutputStream fsout = new FileOutputStream(file);	
			wb.write(fsout);
			fsout.flush();
			fsout.close();
			wb.dispose();
			wb.close();

			isNewFile = false;
			result = "success";
			filesize = String.valueOf(file.length());
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			result = "error";
		}
		
		return result;
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void openWorkbook()
	{
		try
		{
			if(file != null)
			{
				if(isNewFile)
				{
					if(suffix.equals("xlsx"))
					{
						workbook = new XSSFWorkbook();
					}
					else
					{
						workbook = new HSSFWorkbook();
					}
				}
				else
				{
					InputStream is = new FileInputStream(file);
					if(suffix.equals("xlsx"))
					{
						workbook = new XSSFWorkbook(is);
					}
					else
					{
						workbook = new HSSFWorkbook(is);
					}
					is.close();
				}
			}
			else 
			{
				workbook = null;
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	


}







