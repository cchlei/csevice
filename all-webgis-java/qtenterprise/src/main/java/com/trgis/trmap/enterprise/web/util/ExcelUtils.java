package com.trgis.trmap.enterprise.web.util;

import org.apache.poi.ss.usermodel.Cell;

public class ExcelUtils {
	public static String getStringVal(Cell cell){
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue()?"TRUE":"FALSE";
		case Cell.CELL_TYPE_FORMULA:
			return cell.getCellFormula();
		case Cell.CELL_TYPE_NUMERIC: //数字格式
			cell.setCellType(Cell.CELL_TYPE_STRING);
			return cell.getStringCellValue();
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		default:
			return "";
		}
	}
}
