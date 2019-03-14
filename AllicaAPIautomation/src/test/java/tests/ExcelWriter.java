package tests;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ExcelWriter {

	public static void LogResponse(int TestCaseId, String responseBody, int statusCode, String statusLine,
			long timeInMilliSec , String extract) throws Exception {
		String extractedData[] = { responseBody, String.valueOf(statusCode), statusLine,
				Long.toString(timeInMilliSec) , extract };
		String path = "";
		if (TestCaseId == 1)
			path = System.getProperty("user.dir") + "\\src\\test\\resources\\APIdata.xlsx";
		else
			path = System.getProperty("user.dir") + "\\src\\test\\java\\logs\\APIdataLog.xlsx";

		FileInputStream fis = new FileInputStream(path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet resultSheet = workbook.getSheet("ExperienceAPI");
		Row resultRow = resultSheet.getRow(TestCaseId);
		for (int i = 0; i < extractedData.length; i++) {
			Cell resultCell = resultRow.createCell(i + 8);
			resultCell.setCellValue(extractedData[i]);
			resultSheet.autoSizeColumn(i + 8);

			/*----------------------------- CENTER , BORDER ----------------*/
			XSSFCellStyle style2 = workbook.createCellStyle();

			style2.setBorderTop(BorderStyle.DOUBLE);
			style2.setTopBorderColor(IndexedColors.BLACK.getIndex());
			style2.setBorderBottom(BorderStyle.MEDIUM_DASHED);
			style2.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			style2.setBorderRight(BorderStyle.MEDIUM_DASHED);
			style2.setRightBorderColor(IndexedColors.BLACK.getIndex());
			style2.setBorderLeft(BorderStyle.MEDIUM_DASHED);
			style2.setLeftBorderColor(IndexedColors.BLACK.getIndex());
			style2.setWrapText(true);
			style2.setAlignment(HorizontalAlignment.CENTER);
			style2.setVerticalAlignment(VerticalAlignment.CENTER);

			resultCell.setCellStyle(style2);
		}

		FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir") + "\\src\\test\\java\\logs\\APIdataLog.xlsx");

				workbook.write(fos);
		// fos.close();

	}

}