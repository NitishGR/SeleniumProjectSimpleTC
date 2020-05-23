package com.training.readexcel;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * @author Naveen
 * @see this class will take the records from excel sheet, and return it as list
 *      of list of object, and can be generic, can given any records until it
 *      exists. Test it with main method provided, and the path is hard coded,
 *      participatns are asked to refractor this path in the property file and
 *      access.
 */
public class ApachePOIExcelRead2 {
	public static List<List<Object>> getExcelContent(String fileName, String sheetName) {
		List<List<Object>> list = new ArrayList<List<Object>>();

		try {
			System.out.println("File Path " + fileName);
			System.out.println("SheetName: " + sheetName);
			FileInputStream file = new FileInputStream(new File(fileName));

			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheet(sheetName);

			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();

				// skipping first record
				List<Object> tempList = new ArrayList<Object>();
				if (row.getRowNum() == 0) {
					continue;
				}
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					// Check the cell type and format accordingly
					switch (cell.getCellType()) {

					case Cell.CELL_TYPE_NUMERIC:

						if (((Double) cell.getNumericCellValue()).toString() != null) {
							tempList.add(NumberToTextConverter.toText(cell.getNumericCellValue()));
						}
						break;
					case Cell.CELL_TYPE_STRING:
						if (cell.getStringCellValue() != null) {
							tempList.add(cell.getStringCellValue());
						}
						break;
					}
				}
				list.add(tempList);
			}

			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

//	public static void main(String[] args) {
//		String fileName = "C:/Selenium/TestData.xlsx";
//		String sheetName = "Sheet1";
//		System.out.println("ApachePOI main method");
//		for (List<Object> temp : getExcelContent(fileName, sheetName)) {
//			System.out.println(temp.get(0) + " ::"+ temp.get(1));
//		}
//
//	}
}