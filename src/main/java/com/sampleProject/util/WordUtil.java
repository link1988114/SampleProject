package com.sampleProject.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTInd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTabStop;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTabJc;



public class WordUtil 
{
	private String tempLocalPath = "";
	private XWPFDocument xwpfDocument = null;
	private FileInputStream inStream = null;
	private OutputStream outStream = null;
	
	public WordUtil() 
	{
		
	}
	
	public WordUtil(String tempLocalPath)
	{
		this.tempLocalPath = tempLocalPath;
	}
	
	public void setTempLocalPath(String value)
	{
		this.tempLocalPath = value;
	}
	
	public void init()
	{
		try 
		{
			inStream = new FileInputStream(new File(tempLocalPath));
			xwpfDocument = new XWPFDocument(inStream);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			inStream = null;
			xwpfDocument = null;
		}
	}
	
	public boolean export(List<Map<String, String>> params, String orgFullName, String logoFilePath, int tableIndex, Map<String,String> textMap)
	{
		boolean result = false;
		try 
		{
			createWaterMark(xwpfDocument, "测试的水印");
			createHeader(xwpfDocument, orgFullName, logoFilePath);
			replaceText(xwpfDocument, textMap);
			insertValueToTable(xwpfDocument, params, tableIndex);
			
			result = true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			result = false;
		}
		return result;
	}


	//////////////////////////////////////////////////////////////////////////
	
	
	private void createWaterMark(XWPFDocument doc, String waterMarkStr)
	{
		XWPFHeaderFooterPolicy policy = doc.getHeaderFooterPolicy();
		policy.createWatermark(waterMarkStr);
	}
	
    private void replaceText(XWPFDocument xwpfDocument,Map<String,String> textMap)
    {
        List<XWPFParagraph> paras=xwpfDocument.getParagraphs();
        Set<String> keySet=textMap.keySet();
        for (XWPFParagraph para : paras) 
        {
//        	String str=para.getText();
//            System.out.println("打印获取到的段落的每一行数据++++++++>>>>>>>"+para.getText());
//            System.out.println("========================>>>>>>"+para.getParagraphText());

            List<XWPFRun> list=para.getRuns();
            for(XWPFRun run:list)
            {
                for(String key:keySet)
                {
                    if(key.equals(run.text()))
                    {
                        run.setText(textMap.get(key),0);
                    }
                }
            }
        }
    }
    
    private void insertValueToTable(XWPFDocument xwpfDocument, List<Map<String, String>> params, int tableIndex)
    {
    	try 
    	{
			List<XWPFTable> tableList = xwpfDocument.getTables();
			if(tableList.size()<=tableIndex)
			{
				System.out.println("table out of index");
			}
			else
			{
				XWPFTable table = tableList.get(tableIndex);
				List<XWPFTableRow> rows = table.getRows();
				if(rows.size()<2)
				{
					System.out.println("table less then 2 rows");
				}
				else
				{
					XWPFTableRow tmpRow = rows.get(1);
					List<XWPFTableCell> tmpCells = null;
					List<XWPFTableCell> cells = null;
					XWPFTableCell tmpCell = null;
					tmpCells = tmpRow.getTableCells();
					
					String cellText = null;
					String cellTextKey = null;

					for(int i=0, len=params.size(); i<len; i++)
					{
						Map<String, String> map = params.get(i);
						XWPFTableRow row = table.createRow();
						row.setHeight(tmpRow.getHeight());
						cells = row.getTableCells();
						for(int k=0, klen = cells.size(); k<klen; k++)
						{
							tmpCell = tmpCells.get(k);
							XWPFTableCell cell = cells.get(k);
							cellText = tmpCell.getText();
							if(cellText != null && !cellText.equals(""))
							{
								cellTextKey = cellText.replace("$", "").replace("{", "").replace("}", "");
								if(map.containsKey(cellTextKey))
								{
									setCellText(tmpCell, cell, map.get(cellTextKey));
								}
							}
						}
					}
					table.removeRow(1);
				}
			}
		} 
    	catch (Exception e) 
    	{
			e.printStackTrace();
		}
    }
    
    private void setCellText(XWPFTableCell tmpCell, XWPFTableCell cell, String text)
    {
    	CTTc cttc2 = tmpCell.getCTTc();
    	CTTcPr ctPr2 = cttc2.getTcPr();
    	CTTc cttc = cell.getCTTc();
    	CTTcPr ctPr = cttc.addNewTcPr();
    	if(ctPr2.getTcW() != null)
    	{
    		ctPr.addNewTcW().setW(ctPr2.getTcW().getW());
    	}
    	if(ctPr2.getVAlign() != null)
    	{
    		ctPr.addNewVAlign().setVal(ctPr2.getVAlign().getVal());
    	}
    	if(cttc2.getPList().size()>0)
    	{
    		CTP ctp = cttc2.getPList().get(0);
    		if(ctp.getPPr() != null)
    		{
    			if(ctp.getPPr().getJc() != null)
    			{
    				cttc.getPList().get(0).addNewPPr().addNewJc().setVal(ctp.getPPr().getJc().getVal());
    			}
    		}
    	}
    	if(ctPr2.getTcBorders() != null)
    	{
    		ctPr.setTcBorders(ctPr2.getTcBorders());
    	}
    	
    	XWPFParagraph tmpP = tmpCell.getParagraphs().get(0);
    	XWPFParagraph cellP = cell.getParagraphs().get(0);
    	XWPFRun tmpR = null;
    	if(tmpP.getRuns() != null && tmpP.getRuns().size()>0)
    	{
    		tmpR = tmpP.getRuns().get(0);
    	}
    	XWPFRun cellR = cellP.createRun();
    	cellR.setText(text);
    	
    	if(tmpR != null)
    	{
    		if(!cellR.isBold())
    		{
    			cellR.setBold(tmpR.isBold());
    		}
    		cellR.setItalic(tmpR.isItalic());
    		cellR.setUnderline(tmpR.getUnderline());
    		cellR.setColor(tmpR.getColor());
    		cellR.setTextPosition(tmpR.getTextPosition());
    		if(tmpR.getFontSize() != -1)
    		{
    			cellR.setFontSize(tmpR.getFontSize());
    		}
    		if(tmpR.getFontFamily() != null)
    		{
    			cellR.setFontFamily(tmpR.getFontFamily());
    		}
    		if(tmpR.getCTR() != null)
    		{
    			if(tmpR.getCTR().isSetRPr())
    			{
    				CTRPr tmpRPr = tmpR.getCTR().getRPr();
    				if(tmpRPr.isSetRFonts())
    				{
    					CTFonts tmpFonts = tmpRPr.getRFonts();
    					CTRPr cellRPr = cellR.getCTR().isSetRPr() ? cellR.getCTR().getRPr() : cellR.getCTR().addNewRPr();
    					CTFonts cellFonts = cellRPr.isSetRFonts() ? cellRPr.getRFonts() : cellRPr.addNewRFonts();
    					cellFonts.setAscii(tmpFonts.getAscii());
    					cellFonts.setAsciiTheme(tmpFonts.getAsciiTheme());
    					cellFonts.setCs(tmpFonts.getCs());
    					cellFonts.setCstheme(tmpFonts.getCstheme());
    					cellFonts.setEastAsia(tmpFonts.getEastAsia());
    					cellFonts.setEastAsiaTheme(tmpFonts.getEastAsiaTheme());
    					cellFonts.setHAnsi(tmpFonts.getHAnsi());
    					cellFonts.setHAnsiTheme(tmpFonts.getHAnsiTheme());
    				}
    			}
    		}
    	}
    	
    	cellP.setAlignment(tmpP.getAlignment());
    	cellP.setVerticalAlignment(tmpP.getVerticalAlignment());
    	cellP.setBorderBetween(tmpP.getBorderBetween());
    	cellP.setBorderBottom(tmpP.getBorderBottom());
    	cellP.setBorderLeft(tmpP.getBorderLeft());
    	cellP.setBorderRight(tmpP.getBorderRight());
    	cellP.setBorderTop(tmpP.getBorderTop());
    	cellP.setPageBreak(tmpP.isPageBreak());	
    	if(tmpP.getCTP() != null)
    	{
    		if(tmpP.getCTP().getPPr() != null)
    		{
    			CTPPr tmppPr = tmpP.getCTP().getPPr();
    			CTPPr cellPPr = cellP.getCTP().getPPr() != null ? cellP.getCTP().getPPr() : cellP.getCTP().addNewPPr();
    			CTSpacing tmpSpacing = tmppPr.getSpacing();
    			if(tmpSpacing != null)
    			{
    				CTSpacing cellSpacing = cellPPr.getSpacing() != null ? cellPPr.getSpacing() : cellPPr.addNewSpacing();
    				if(tmpSpacing.getAfter() != null)
    				{
    					cellSpacing.setAfter(tmpSpacing.getAfter());
    				}
    				if(tmpSpacing.getAfterAutospacing() != null)
    				{
    					cellSpacing.setAfterAutospacing(tmpSpacing.getAfterAutospacing());
    				}
    				if(tmpSpacing.getAfterLines() != null)
    				{
    					cellSpacing.setAfterLines(tmpSpacing.getAfterLines());
    				}
    				if(tmpSpacing.getBefore() != null)
    				{
    					cellSpacing.setBefore(tmpSpacing.getBefore());
    				}
    				if(tmpSpacing.getBeforeAutospacing() != null)
    				{
    					cellSpacing.setBeforeAutospacing(tmpSpacing.getBeforeAutospacing());
    				}
    				if(tmpSpacing.getBeforeLines() != null)
    				{
    					cellSpacing.setBeforeLines(tmpSpacing.getBeforeLines());
    				}
    				if(tmpSpacing.getLine() != null)
    				{
    					cellSpacing.setLine(tmpSpacing.getLine());
    				}
    				if(tmpSpacing.getLineRule() != null)
    				{
    					cellSpacing.setLineRule(tmpSpacing.getLineRule());
    				}
    			}
    			
    			
    			CTInd tmpInd = tmppPr.getInd();
    			if(tmpInd != null)
    			{
    				CTInd cellInd = cellPPr.getInd() != null ? cellPPr.getInd() : cellPPr.addNewInd();
    				if(tmpInd.getFirstLine() != null)
    				{
    					cellInd.setFirstLine(tmpInd.getFirstLine());
    				}
    				if(tmpInd.getFirstLineChars() != null)
    				{
    					cellInd.setFirstLineChars(tmpInd.getFirstLineChars());
    				}
    				if(tmpInd.getHanging() != null)
    				{
    					cellInd.setHanging(tmpInd.getHanging());
    				}
    				if(tmpInd.getHangingChars() != null)
    				{
    					cellInd.setHangingChars(tmpInd.getHangingChars());
    				}
    				if(tmpInd.getLeft() != null)
    				{
    					cellInd.setLeft(tmpInd.getLeft());
    				}
    				if(tmpInd.getLeftChars() != null)
    				{
    					cellInd.setLeftChars(tmpInd.getLeftChars());
    				}
    				if(tmpInd.getRight() != null)
    				{
    					cellInd.setRight(tmpInd.getRight());
    				}
    				if(tmpInd.getRightChars() != null)
    				{
    					cellInd.setRightChars(tmpInd.getRightChars());
    				}
    			}
    			
    		}
    	}
    }
    
    @SuppressWarnings("unused")
	private void createHeader(XWPFDocument doc, String orgFullName)
    {
    	try 
    	{
			CTSectPr sectPr = doc.getDocument().getBody().addNewSectPr();
			XWPFHeaderFooterPolicy hfPolicy = new XWPFHeaderFooterPolicy(doc, sectPr);
			XWPFHeader header = hfPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT);
			
			XWPFParagraph paragraph = header.getParagraphArray(0);
			paragraph.setBorderBottom(Borders.THICK);
			
			if(orgFullName != null && !orgFullName.equals(""))
			{
				XWPFRun run = paragraph.createRun();
				run.setText(orgFullName);
			}
			
		} 
    	catch (Exception e) 
    	{
			e.printStackTrace();
		}
    }
    
    private void createHeader(XWPFDocument doc, String orgFullName, String imgFilePath)
    {
    	try 
    	{
			CTSectPr sectPr = doc.getDocument().getBody().addNewSectPr();
			XWPFHeaderFooterPolicy hfPolicy = new XWPFHeaderFooterPolicy(doc, sectPr);
			XWPFHeader header = hfPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT);
			
			XWPFParagraph paragraph = header.getParagraphArray(0);
			paragraph.setAlignment(ParagraphAlignment.BOTH);
			paragraph.setBorderBottom(Borders.THICK);
			
			XWPFRun run = paragraph.createRun();
			setXWPFRunStyle(run, "新宋体",10);
			
			if(orgFullName != null && !orgFullName.equals(""))
			{
				run = paragraph.createRun();
				run.setText(orgFullName);
				setXWPFRunStyle(run, "新宋体", 10);
			}
			run.addTab();
			
			CTTabStop tabStop = paragraph.getCTP().getPPr().addNewTabs().addNewTab();
			tabStop.setVal(STTabJc.CENTER);
			int twipsPerInch = 1450;
			tabStop.setPos(BigInteger.valueOf(6*twipsPerInch));
			
			if(imgFilePath != null && !imgFilePath.equals(""))
			{
				File file = new File(imgFilePath);
				InputStream is = new FileInputStream(file);
				XWPFPicture pic = run.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG, imgFilePath, Units.toEMU(40), Units.toEMU(40));
				
				String blipID = "";
				for(XWPFPictureData picdata:header.getAllPackagePictures())
				{
					blipID = header.getRelationId(picdata);
				}
				pic.getCTPicture().getBlipFill().getBlip().setEmbed(blipID);
				run.addTab();
				is.close();
			}
		} 
    	catch (Exception e) 
    	{
			e.printStackTrace();
		}
    }
    
    
    
    private void setXWPFRunStyle(XWPFRun r1, String font, int fontSize)
    {
    	r1.setFontSize(fontSize);
    	CTRPr rpr = r1.getCTR().isSetRPr() ? r1.getCTR().getRPr() : r1.getCTR().addNewRPr();
    	CTFonts fonts = rpr.isSetRFonts() ? rpr.getRFonts() : rpr.addNewRFonts();
    	fonts.setAscii(font);
    	fonts.setEastAsia(font);
    	fonts.setHAnsi(font);
    }
    
    private boolean generate(String outDocPath)
    {
    	boolean result = false;
    	try 
    	{
			outStream = new FileOutputStream(outDocPath);
			xwpfDocument.write(outStream);
			outStream.close();
			inStream.close();
			result = true;
		} 
    	catch (Exception e) 
    	{
			e.printStackTrace();
			result = false;
		}
    	return result;
    }
    
    
    ///////////////////////////////////////////////////////////////
    
    public static void main(String[] args) throws Exception
    {
    	List<Map<String, String>> list = new ArrayList<Map<String,String>>();
    	for(int i=0; i<10; i++)
    	{
    		Map<String, String> map = new HashMap<String, String>();
    		map.put("index", "2018"+i);
    		map.put("fileName", "第一列数据"+i);
    		map.put("creationDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    		map.put("supervisionOpinions", "意见"+i);
    		map.put("comment", "备注"+i);
    		list.add(map);
    	}
    	
    	Map<String, String> textMap = new HashMap<String, String>();
    	textMap.put("companyName", "测试的公司名");
    	textMap.put("folderName", "测试的目录名");
    	
    	String filePath = "d:\\test.docx";
    	String outPath = "d:\\template.docx";
    	
    	String orgFullName = "测试的公司名";
    	String imgFile = "d:\\logo.jpg";
    	WordUtil wordUtil = new WordUtil();
    	wordUtil.setTempLocalPath(filePath);
    	wordUtil.init();
    	wordUtil.export(list, orgFullName, imgFile, 0, textMap);
    	wordUtil.generate(outPath);
    	
    }
    

}


















