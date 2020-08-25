package com.sampleProject.service;



import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.sampleProject.model.MsgModel;
import com.sampleProject.util.FileDownload;
import com.sampleProject.util.FileUpload;

public class FileService extends BaseService
{
	public String doFileUpload(HttpServletRequest req, String path, String newFileName)
	{
		MsgModel msg = new MsgModel();
		List<String> fileList = FileUpload.springUpload(req, path, newFileName);
		if ((fileList != null) && (fileList.size() > 0))
		{
			msg.setResult("success");
			msg.setMsg(ListToJsonstr(fileList));
		}
		else
		{
			msg.setMsg("上传出错");
		}
		return msg.toString();
	}
  
  
	public void doFileDownload(HttpServletRequest req, HttpServletResponse resp, String path, String fileName, boolean deleteAfterComplete) throws Exception
	{
		String[] temp = fileName.split("\\.");
		String suffix = temp[(temp.length - 1)];
		String filename = fileName.substring(0, fileName.length() - suffix.length() - 1);
		FileDownload.Download(req, resp, "application/octet-stream", path, filename, suffix, deleteAfterComplete);
	}
  
  
//////////////////////////////////////////////////////////////////////////
  
//  public String processDataUpload(String path, String filename) throws Exception
//  {
//	  MsgModel msg = new MsgModel();
//	  
//	  DescriptionDB descriptionDB = new DescriptionDB();
//	  List<DescriptionModel> desList = descriptionDB.getAllList();
//
//	  //System.out.println(desList.toString());
//	  
//	  String currCataName = "";
//	  String currCataKey = "";
//	  int currCataID = -1;
//	  
//	  int stationIDIndex = -1;
//
//	  JSONObject cataJson = new JSONObject();	  
//	  for(int i=0; i<desList.size(); i++)
//	  {
//		  DescriptionModel item = desList.get(i);
//		  if(item.getDes_type().equals("table"))
//		  {
//			  currCataID = item.getDes_id();
//			  currCataName = item.getDes_name();
//			  currCataKey = item.getDes_keyname();
//		  }
//		  else
//		  {
//			  if(!item.getDes_keyname().equals("station_id"))
//			  {
//				  JSONObject temp = new JSONObject();
//				  temp.put("des_keyname", item.getDes_keyname());
//				  temp.put("cataName", currCataKey);
//				  cataJson.put(item.getDes_name(), temp);  
//			  }
//		  }
//	  }
//	  
//	  File file = new File(path+File.separator+filename);
//	  ExcelReader xlsx = new ExcelReader(file);
//	  if(xlsx.openFile(path+File.separator+filename).equals("success"))
//	  {
//		  List<String[]> list = xlsx.getXlsxGrid(0);
//		  JSONObject sqlJson = new JSONObject();
//		  for(int i=0; i<list.size(); i++)
//		  {
//			  String[] line = list.get(i);
//			  if(i==0)
//			  {
//				  sqlJson = createSqlJson(cataJson, line);
//			  }
//			  else
//			  {
//				  List<SqlModel> sqlList = createSqlStr(sqlJson, line);
//				  if (sqlList.size() > 0)
//				  {
//					  DescriptionDB desDB = new DescriptionDB();
//					  desDB.updateData(sqlList);
//					  msg.setResult("success");
//					  msg.setMsg("导入成功");
//				  }
//				  else
//				  {
//					  msg.setMsg("导入出错请重试");
//				  }
//			  }
//		  }
//			
//	  }
//	  	  
//	  return msg.toString();
//  }
  
    
}
