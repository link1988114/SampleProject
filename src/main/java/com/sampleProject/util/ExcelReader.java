package com.sampleProject.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.sampleProject.util.ProjectUtil;




public class ExcelReader
{
	//1   read   xls
	//initial------open-------readdata------close
	
	
	//2  read xlsx
	/**
	 *File outputfile = new File("D:\\data2.txt");
	 *PrintStream ps = new PrintStream(outputfile);
	 *ExcelReader xlsx2csv = new ExcelReader(ps,11);
	 *String flag = xlsx2csv.openFile("D:\\ssl\\test2223.xlsx");
	 *xlsx2csv.processOneSheet(0);
	 * 
	 */
	
	
	//3   read   xls  or  xlsx
	/**
	 * File file = new File("path");
	 * ExcelReader xlsUtil = new ExcelReader(file);
	 * xlsUtil.open();
	 * xlsUtil.getSheetDataGrid(sheetIndex, startLine);
	 * xlsUtil.close();
	**/
	
	
	public int sheetCount = 0;
	
	private File file = null;
	private String filename = "";
	private String path = "";
	private String suffix = "";
	private Workbook wb;
	//private FileInputStream is = null;
	
	//low cost mode
	private OPCPackage xlsxPackage = null;
	private int minColumns;
	private PrintStream output = null;
	private StringBuilder builder = new StringBuilder();
	
	
	public ExcelReader(File file)
	{	
		this.file = file;
		this.filename = file.getName();
		this.path = file.getAbsolutePath();
		
		String[] temp = filename.split("\\.");
		this.suffix = temp[temp.length-1].toLowerCase().trim();
	}
	
	public ExcelReader(PrintStream output, int minColumns)
	{
		this.output = output;
		this.minColumns = minColumns;
	}
	
	
	public String open()
	{
		String result = "";
	
		//System.out.println("opening...");
		
		InputStream is = null;
		try
		{
			is = new FileInputStream(file);
			
			if("xlsx".equals(suffix))
			{
				wb = new XSSFWorkbook(is);
				sheetCount = wb.getNumberOfSheets();
				result = "success";
			}
			else if("xls".equals(suffix))
			{
				wb = new HSSFWorkbook(is);
				sheetCount = wb.getNumberOfSheets();
				result = "success";
			}
			else
			{
				result = "errorType";
			}
			is.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			result = "error";
		}
		
		return result;
	}
	
	public String getCellByCrood(int rowNum,int colNum)
	{
		if(wb==null)
		{
			return null;
		}
		else 
		{
			Sheet sheet  = wb.getSheetAt(0);
			Row row = sheet.getRow(rowNum);			
			Cell cell = row.getCell(colNum);
			return getCellValue(cell);
		}
	}
	
	public List<String[]> getSheetDataGrid(int sheetIndex,int startline)
	{
		List<String[]> result = new ArrayList<String[]>();
		if(startline<0)
		{
			startline=0;
		}
		minColumns = startline;
		
		if(suffix.equals("xls"))
		{
			int maxlength = 0;
			int maxsize = 0;	
				
			if(wb == null)
			{
				return null;
			}
			Sheet sheet = wb.getSheetAt(sheetIndex);
			if(sheet.getSheetName() == null || sheet.getSheetName().equals(""))  //sheet name restrict
			{
				return null;
			}
			else
			{
				//get data in first line size
				Row row = sheet.getRow(startline);
				maxlength = row.getLastCellNum();
				maxsize = sheet.getLastRowNum()+1;
				for(int i=startline;i<maxsize;i++)
				{
					String[] line = new String[maxlength];
					for(int j=0;j<maxlength;j++)
					{
						line[j] = getCellByCrood(i, j);
					}
					result.add(line);
				}
			}
		}
		else if(suffix.equals("xlsx"))
		{
			try
			{
				result = getXlsxGrid(sheetIndex);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			result = null;
		}
		
		return result;
	}
	
	public List<ArrayList<String>> getSheetData(int sheetIndex)
	{
		List<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		
		if(wb == null)
		{
			return null;
		}
		
		Sheet sheet = wb.getSheetAt(sheetIndex);
		if(sheet.getSheetName()==null || sheet.getSheetName().equals(""))   //if(!sheet.getSheetName().contains(""))
		{
			return null;
		}
		else
		{
			Iterator<Row> iterRow = sheet.rowIterator();
			while(iterRow.hasNext())
			{
				ArrayList<String> temp = new ArrayList<String>();
				Row row = iterRow.next();
				Iterator<Cell> iterCell = row.cellIterator();
				while(iterCell.hasNext())
				{
					Cell cell = iterCell.next();
					temp.add(getCellValue(cell));
				}
				result.add(temp);
			}
			return result;
		}
	}
	
	
	public String[][] getSheetData2(int sheetIndex)
	{
		List<ArrayList<String>> temp = getSheetData(sheetIndex);
		if(temp==null)
		{
			return null;
		}
		else 
		{
			int max = 0;
			for(ArrayList<String> line : temp)
			{
				if(line.size()>max)
				{
					max=line.size();
				}
			}
			String[][] re = new String[temp.size()][max];
			for(int i=0; i<temp.size(); i++)
			{
				String[] t = new String[temp.get(i).size()];
				re[i] = temp.get(i).toArray(t);
			}
			return re;	
		}
	}
	
	
	public String getSheetName(int index)
	{
		if(wb == null)
		{
			return "notfound";
		}
		else
		{
			return wb.getSheetName(index);
		}
	}
	
	public void close()
	{
		try
		{
			if(this.wb != null)
			{
				wb.close();
			}	
		}
		catch(Exception e)
		{}
		try 
		{
			if(this.xlsxPackage != null)
			{
				xlsxPackage.close();
			}
		}
		catch(Exception e) 
		{}
		try 
		{
			if(this.output != null)
			{
				output.close();
			}
		}
		catch(Exception e) 
		{}
	}
	
	
	//////////////////////////////////////////////////////////////////////////////
	
	public void outputdata(int sheetindex)
	{
		List<ArrayList<String>> data0 = getSheetData(sheetindex);
		for(int i=0;i<data0.size();i++)
		{
			for(int j=0;j<data0.get(i).size();j++)
			{
				System.out.print(data0.get(i).get(j)+"|space|");
			}
			System.out.println();
		}
	}
	
	
	public void outputGridData(int sheetindex)
	{
		List<String[]> data = getSheetDataGrid(sheetindex,1);
		for(int i=0;i<data.size();i++)
		{
			for(int j=0;j<data.get(i).length;j++)
			{
				System.out.print(data.get(i)[j]+"|");
			}
			System.out.println();
		}
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////
	
	@SuppressWarnings("deprecation")
	private String getCellValue(Cell cell)
	{
		String result ="";
		
		if(cell==null)
		{
			result = "";
			return result;
		}
		else
		{
			int type = cell.getCellType();
			switch (type)
			{
				case HSSFCell.CELL_TYPE_BLANK:
					result = "";
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN:
					result = String.valueOf(cell.getBooleanCellValue());
					break;
				case HSSFCell.CELL_TYPE_ERROR:
					result = "";
					break;
				case HSSFCell.CELL_TYPE_FORMULA:
					result = "";
					break;
				case HSSFCell.CELL_TYPE_NUMERIC:
					result = new DecimalFormat("#0.00").format(cell.getNumericCellValue());
					break;
				case HSSFCell.CELL_TYPE_STRING:
					result = cell.getStringCellValue();
					break;
				default:
					result = "";
					break;
			}
			result = result.trim();
		}
		
		return result;
	}
	
	
	///////////////////////////////////////////////////////////////////////
	// low cost mode
	
	private class SheetToCSV implements SheetContentsHandler
	{
		private boolean firstCellOfRow;
		private int currentRow = -1;
		private int currentCol = -1;
		
		private char isolationSign = '|';
		
		private void addContent(CharSequence content)
		{
			//output.append(content);
			builder.append(content);
		}
		
		private void addContent(char content)
		{
			//output.append(content);
			builder.append(content);
		}
		
		private void outputMissingRows(int number)
		{
			for(int i=0; i<number; i++)
			{
				for(int j=0; j<minColumns; j++)
				{
					addContent(isolationSign);
				}
				addContent('\n');
			}
		}
		
		public void startRow(int rowNum)
		{
			//fill the missing data
			outputMissingRows(rowNum-currentRow-1);
			
			//form the row
			firstCellOfRow = true;
			currentRow = rowNum;
			currentCol = -1;
		}
		
		public void endRow(int rowNum)
		{
			for(int i=currentCol; i<minColumns; i++)
			{
				addContent(isolationSign);
			}
			addContent('\n');
		}
		
		public void cell(String cellReference, String formattedValue, XSSFComment comment)
		{
			if(firstCellOfRow)
			{
				firstCellOfRow = false;
			}
			else 
			{
				addContent(isolationSign);
			}
			
			if(cellReference == null)
			{
				cellReference = new CellAddress(currentRow,	currentCol).formatAsString();
			}
			
			int thisCol = (new CellReference(cellReference)).getCol();
			int missedCols = thisCol - currentCol -1;
			for(int i=0; i<missedCols; i++)
			{
				addContent(isolationSign);
			}
			currentCol = thisCol;
			
			//cell type
			try
			{
				if(formattedValue.matches(ProjectUtil.regExp_scienceNumber))
				{
					formattedValue = ProjectUtil.scienceToNumberStr(formattedValue);
				}
				else 
				{
					if(formattedValue.split("\\.")[1].length()>9)
					{
						formattedValue = new DecimalFormat("#0.00").format(Double.parseDouble(formattedValue));
					}	
				}
			} 
			catch (Exception e)
			{}
			finally 
			{
				//addContent('"');
				addContent(formattedValue);
				//addContent('"');
			}
		}
		
		
		public void headerFooter(String arg0, boolean arg1, String arg2)
		{
		}
	}
	
	
	public String openFile(String fileWithPath) throws Exception
	{
		String result = "error";
		
		if(!fileWithPath.toLowerCase().endsWith("xlsx"))
		{
			result = "not xlsx file";
		}
		else 
		{
			File xlsxFile = new File(fileWithPath); //new File(args[0]);
	        if (!xlsxFile.exists()) 
	        {
	            System.err.println("Not found or not a file: " + xlsxFile.getPath());
	            result = "notfound";
	        }
	        else 
	        {
	        	xlsxPackage = OPCPackage.open(xlsxFile.getPath(), PackageAccess.READ);
				result = "success";
			}
		}
		return result;
	}
	
	public List<String[]> getXlsxGrid(int sheetIndex) throws Exception
	{
		String rawData = processOneSheet(sheetIndex);
		String[] temp = rawData.split("\\\n");
		List<String[]> list = new ArrayList<String[]>();
		
		for(String line : temp)
		{
			String[] item = line.split("\\|");
			list.add(item);
		}
		return list;
	}
	
	//process one sheet
	public String processOneSheet(int sheetIndex) throws Exception
	{
		String result = "error";
		
		if(xlsxPackage==null)
		{
			String re = openFile(this.path);
			if(!re.equals("success"))
			{
				System.out.println("file not found");
				return "error";
			}
		}
			
		ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(this.xlsxPackage);
		XSSFReader xssfReader = new XSSFReader(this.xlsxPackage);
		StylesTable styles = xssfReader.getStylesTable();
		XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
		
		while(iter.hasNext())
		{
			sheetIndex--;
			try
			{
				InputStream stream = iter.next();
				if(sheetIndex<0)
				{
					//String sheetName = iter.getSheetName();
					//this.output.println();
					//this.output.println(sheetName+"[index="+index+"]:");
					processSheet(styles, strings, new SheetToCSV(), stream);
					
					/*
					DBExceute db = new DBExceute();
					String sql = "load data local infile 'd:/data2.txt' replace into table test333 character set utf8 fields terminated by '|' ";
					System.out.println(builder.toString());
					
					InputStream is = new ByteArrayInputStream(builder.toString().getBytes());
					
					
					db.loadInByStream(sql, is);
					
					is.close();
					*/
					//System.out.println(builder.toString());
					result = builder.toString();
					break;
				}
			} 
			catch (Exception e)
			{
			}
		}
		return result;
	}
	
	
	//process all sheet
	public void process() throws Exception
	{
		if(xlsxPackage==null)
		{
			System.out.println("file not found");
			return;
		}
		
		ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(this.xlsxPackage);
		XSSFReader xssfReader = new XSSFReader(this.xlsxPackage);
		StylesTable styles = xssfReader.getStylesTable();
		XSSFReader.SheetIterator iter = (XSSFReader.SheetIterator) xssfReader.getSheetsData();
		//int index = 0;
		while(iter.hasNext())
		{
			try
			{
				InputStream stream = iter.next();
				//String sheetName = iter.getSheetName();
				//this.output.println();
				//this.output.println(sheetName+"[index="+index+"]:");
				processSheet(styles, strings, new SheetToCSV(), stream);
			} 
			catch (Exception e)
			{
				output.close();
			}
			//++index;
		}
		output.close();
	}
	
	
	//read with style
	public void processSheet(StylesTable styles, ReadOnlySharedStringsTable strings, SheetContentsHandler sheetHandler, InputStream sheetInputStream)
	{
		DataFormatter formatter = new DataFormatter();
		InputSource sheetSource = new InputSource(sheetInputStream);

		try
		{
			XMLReader sheetParser = SAXHelper.newXMLReader();
			ContentHandler handler = new XSSFSheetXMLHandler(null, null, strings, sheetHandler, formatter, false);
			sheetParser.setContentHandler(handler);
			sheetParser.parse(sheetSource);
		} 
		catch (Exception e)
		{
			throw new RuntimeException("SAX parser appears to be broken");
		}
	}
	

	
	
	

	

}
