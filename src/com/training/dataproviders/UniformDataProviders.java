package com.training.dataproviders;

import java.io.File;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.training.bean.LoginBean;
import com.training.dao.ELearningDAO;
import com.training.readexcel.ApachePOIExcelRead;
import com.training.readexcel.ApachePOIExcelRead2;
import com.training.readexcel.ReadExcel;

public class UniformDataProviders {

	private String fileName = "uniform-testdata" + File.separator + "TestData.xlsx";

	@DataProvider(name = "return-product")
	public Object[][] returnProduct() {
		fileName = "uniform-testdata" + File.separator + "TestData.xlsx";
		String sheetName = "Return Product";

		List<List<Object>> retVal = ApachePOIExcelRead2.getExcelContent(fileName, sheetName);
		System.out.println("Size : " + retVal.size());

		Object[][] result = new Object[retVal.size()][retVal.size()];
		int count = 0;

		for (List<Object> temp : retVal) {
			if (temp != null) {
				Object[] obj = new Object[4];
				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));

				obj[0] = temp.get(0);
				obj[1] = temp.get(1);
				obj[2] = temp.get(2);
				obj[3] = temp.get(3);

				result[count++] = obj;
			}

		}
		return result;
	}
	
	@DataProvider(name = "adminAddProduct")
	public Object[][] adminAddProduct() {
		fileName = "uniform-testdata" + File.separator + "TestData.xlsx";
		String sheetName = "Admin Add Product";

		List<List<Object>> retVal = ApachePOIExcelRead2.getExcelContent(fileName, sheetName);
		System.out.println("Size : " + retVal.size());

		Object[][] result = new Object[retVal.size()][retVal.size()];
		int count = 0;

		for (List<Object> temp : retVal) {
			if (temp != null) {
				Object[] obj = new Object[4];
				System.out.println(temp.get(0));
				System.out.println(temp.get(1));
				System.out.println(temp.get(2));
				System.out.println(temp.get(3));

				obj[0] = temp.get(0);
				obj[1] = temp.get(1);
				obj[2] = temp.get(2);
				obj[3] = temp.get(3);

				result[count++] = obj;
			}

		}
		return result;
	}
}
