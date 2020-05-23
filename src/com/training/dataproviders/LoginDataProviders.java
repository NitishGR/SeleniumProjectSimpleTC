package com.training.dataproviders;

import java.io.File;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.training.bean.LoginBean;
import com.training.dao.ELearningDAO;
import com.training.readexcel.ApachePOIExcelRead;
import com.training.readexcel.ApachePOIExcelRead2;
import com.training.readexcel.ReadExcel;

public class LoginDataProviders {

	@DataProvider(name = "db-inputs")
	public Object [][] getDBData() {

		List<LoginBean> list = new ELearningDAO().getLogins(); 
		
		Object[][] result = new Object[list.size()][]; 
		int count = 0; 
		for(LoginBean temp : list){
			Object[]  obj = new Object[2]; 
			obj[0] = temp.getUserName(); 
			obj[1] = temp.getPassword(); 
			
			result[count ++] = obj; 
		}
		
		
		return result;
	}
	
	@DataProvider(name = "excel-inputs")
	public Object[][] getExcelData(){
		String fileName ="C:/Users/Naveen/Desktop/Testing.xlsx"; 
		return new ApachePOIExcelRead().getExcelContent(fileName); 
	}
	
	@DataProvider(name = "excel-inputs2")
	public Object[][] getExcelData2(){
		// String fileName ="C:/Selenium/TestData.xlsx"; 
		String fileName ="uniform-testdata"+File.separator+"TestData.xlsx"; 
		String sheetName ="Sheet1" ;
		
		List<List<Object>> retVal  = ApachePOIExcelRead2.getExcelContent(fileName, sheetName);
		System.out.println("Size : "+retVal.size());
		
		Object[][] result = new Object[retVal.size()][retVal.size()];
		int count = 0;
		
		for(List<Object> temp:retVal) {
			if(temp!=null) {
				Object[] obj = new Object[2] ;
				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				
				
				obj[0] = temp.get(0);
				obj[1] = temp.get(1);
				
				result[count++]=obj;
			}
			
		}
 		return result; 
	}
	
	@DataProvider(name = "excel-inputs3")
	public Object[][] getExcelData3(){
		String fileName ="."+File.separator+"uniform-testdata"+File.separator+"/TestData.xlsx"; 
		String sheetName ="Sheet2" ;
		
		List<List<Object>> retVal  = ApachePOIExcelRead2.getExcelContent(fileName, sheetName);
		System.out.println("Size : "+retVal.size());
		System.out.println(retVal);
		Object[][] result = new Object[retVal.size()][retVal.size()];
		int count = 0;
		System.out.println(result);
		for(List<Object> temp:retVal) {
			if(temp!=null) {
				Object[] obj = new Object[3] ;
				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));

				obj[0] = temp.get(0);
				obj[1] = temp.get(1);
				obj[2] = temp.get(2);

				
				result[count++]=obj;
			}
			
		}
 		return result; 
	}
	
	@DataProvider(name = "xls-inputs")
	public Object[][] getXLSData(){
		// ensure you will have the title as first line in the file 
		return new ReadExcel().getExcelData("C:/Users/Naveen/Desktop/Testing.xls", "Sheet1"); 
	}
}
