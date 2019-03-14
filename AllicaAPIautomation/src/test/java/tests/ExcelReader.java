
package tests;

import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

public class ExcelReader {

	public static Map<String, Map<String, String>> setMapData() throws Exception {

		String path = System.getProperty("user.dir")+"\\src\\test\\resources\\APIdata.xlsx";

		FileInputStream fis = new FileInputStream(path);

		Workbook workbook = new XSSFWorkbook(fis);

		Sheet sheet = workbook.getSheetAt(0);
		//Sheet sheet = workbook.getSheetAt(1);

		int lastRow = sheet.getLastRowNum();

		Map<String, Map<String, String>> excelFileMap = new HashMap<String, Map<String, String>>();

		Map<String, String> dataMap = new HashMap<String, String>();

		// Looping over entire row
		for (int i = 0; i <= lastRow; i++) {

			Row row = sheet.getRow(i);

			// Desired Cell as Value
			Cell valueCell_httpmethod = row.getCell(2);
			Cell valueCell_baseuri = row.getCell(3);
			Cell valueCell_queryparam = row.getCell(4);
			Cell valueCell_requestbody = row.getCell(6);
			Cell valueCell_extract = row.getCell(7);
			
			// 0th Cell as Key
			Cell keyCell = row.getCell(0);

			String value = valueCell_httpmethod.getStringCellValue().trim() + "~"+valueCell_baseuri.getStringCellValue().trim() + "~"
					+ valueCell_queryparam.getStringCellValue().trim() +"~"+valueCell_requestbody+ "~" +valueCell_extract;
			String key = keyCell.getStringCellValue().trim();

			// Putting key & value in dataMap
			dataMap.put(key, value);

			// Putting dataMap to excelFileMap
			excelFileMap.put("DataSheet", dataMap);

		}

		// Returning excelFileMap
		return excelFileMap;

	}

	// Method to retrieve value
	public static String[] getMapData(String key) throws Exception {

		Map<String, String> m = setMapData().get("DataSheet");
		String value = m.get(key);
		String data[] = value.split("~");

		return data;

	}

}
