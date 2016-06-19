package com.wacajou.common.file.xls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLSXFile {
	private File myFile;

	public XLSXFile(File file) {
		this.myFile = file;
	}

	public List<String> Read() throws IOException {
		FileInputStream fis = new FileInputStream(myFile); // Finds the workbook
															// instance for XLSX
															// file
		List<String> retour = new ArrayList<String>();
		XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
		// Return first sheet from the XLSX workbook
		XSSFSheet mySheet = myWorkBook.getSheetAt(0);
		// Get iterator to all the rows in current sheet
		Iterator<Row> rowIterator = mySheet.iterator();
		// Traversing over each row of XLSX file
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			// For each row, iterate through each columns
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					retour.add(cell.getStringCellValue());
					break;
				case Cell.CELL_TYPE_NUMERIC:
					retour.add(""+cell.getNumericCellValue());
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					retour.add(""+cell.getBooleanCellValue());;
					break;
				default:
				}
			}
		}
		return retour;
	}

	public void Write(Map<String, Object[]> newData) throws IOException {
		FileInputStream fis = new FileInputStream(myFile); // Finds the workbook
		// instance for XLSX
		// file
		XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
		// Return first sheet from the XLSX workbook
		XSSFSheet mySheet = myWorkBook.getSheetAt(0);
		Set<String> newRows = newData.keySet();
		int rownum = mySheet.getLastRowNum();
		for (String key : newRows) {
			Row row = mySheet.createRow(rownum++);
			Object[] objArr = newData.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof String) {
					cell.setCellValue((String) obj);
				} else if (obj instanceof Boolean) {
					cell.setCellValue((Boolean) obj);
				} else if (obj instanceof Date) {
					cell.setCellValue((Date) obj);
				} else if (obj instanceof Double) {
					cell.setCellValue((Double) obj);
				}
			}
		}
		// open an OutputStream to save written data into Excel file
		FileOutputStream os = new FileOutputStream(myFile);
		myWorkBook.write(os);
		System.out.println("Writing on Excel file Finished ...");
		// Close workbook, OutputStream and Excel file to prevent leak
		os.close();
		myWorkBook.close();
		fis.close();
	}
}
