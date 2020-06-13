package com.bdqn.util;

import org.apache.poi.hssf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author
 * @Description: Excel表格导出工具类
 * @date 2019年9月12日 上午11:00:19 
 * @version V1.0
 */
public class ExcelUtil {
	/**
	 * 导出Excel表格
	 * @param sheetName 表格sheet名称
	 * @param title  标题数组
	 * @param values  内容 二维数组
	 * @param wb  HSSFWorkbook对象
	 * @return
	 */
	public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, String[][] values, HSSFWorkbook wb) {
		// 1.创建一个HSSWorkbook对象，对应一个Excel文件
		if (wb == null)
			wb = new HSSFWorkbook();
		// 2.在workbook当中添加一个sheet，对应Excel中的sheet
		HSSFSheet sheet = wb.createSheet(sheetName);
		// 3.在sheet表头添加第0行，老版本的poi对Excel的行数有限制
		HSSFRow row = sheet.createRow(0);
		// 4.创建单元格，并设置表头，设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		// 4.1创建一个居中表格
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 4.2声明列对象
		HSSFCell cell = null;
		// 4.3创建标题
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
			cell.setCellStyle(style);
		}
		// 4.4创建内容
		for (int i = 0; i < values.length; i++) {
			row = sheet.createRow(i + 1);
			for (int j = 0; j < values[i].length; j++) {
				// 将内容按顺序赋给对应的列对象
				row.createCell(j).setCellValue(values[i][j]);
			}
		}
		return wb;
	}

	/**
	 * 生成excel表格
	 * @param exportPath 存放路径
	 * @param fileName 生成文件名
	 * @param wb HSSFWorkbook对象
	 * @return 返回生成是否成功
	 */
	public static boolean createExcel(String exportPath, String fileName, HSSFWorkbook wb) {
		boolean flag = false;
		// 解析流
		FileOutputStream fos = null;
		try {
			File dirFile = new File(exportPath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			fos = new FileOutputStream(exportPath + File.separator + fileName);
			wb.write(fos);
			fos.close();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
				return flag;
			}
		}
		return flag;
	}

}