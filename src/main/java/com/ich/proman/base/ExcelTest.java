package com.ich.proman.base;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ExcelTest {
    private static final String EXL_2007 = "C:\\Users\\Administrator\\Pictures\\工作进度（U圈U点）\\temp.xlsx";

    public static void main(String[] args){
        try {
            test(EXL_2007);
        } catch (Exception e) {
            System.out.println("请检查EXCEL文件及格式!!");
        }
    }

    private static void test(String file) throws Exception  {
        Workbook wb = null;
        if (file.endsWith(".xlsx")) {//EXCEL2007
            wb = new XSSFWorkbook(new FileInputStream(new File(file)));
        }else if(file.endsWith(".xls")){//EXCEL97-2003
            wb = new HSSFWorkbook(new FileInputStream(new File(file)));
        }else{
            throw new Exception("");
        }

        if(wb.getNumberOfSheets() > 1){
            wb.removeSheetAt(1);
        }

        //单元格合并
        CellRangeAddress region = null;
        Sheet sheet1 = wb.getSheetAt(0);
        Sheet sheet2 = wb.createSheet(sheet1.getSheetName() + "_副本");
        for (int i = 0; i < sheet1.getNumMergedRegions(); i++) {
            region = sheet1.getMergedRegion(i);
            if ((region.getFirstColumn() >= sheet1.getFirstRowNum())
                    && (region.getLastRow() <= sheet1.getLastRowNum())) {
                sheet2.addMergedRegion(region);
            }
        }

        //复制内容
        Row rowFrom = null;
        Row rowTo = null;
        Cell cellFrom = null;
        Cell cellTo = null;
        for (int i = sheet1.getFirstRowNum(); i < sheet1.getLastRowNum(); i++) {
            rowFrom = sheet1.getRow(i);
            if (null == rowFrom){
                continue;
            }

            rowTo = sheet2.createRow(i);
            rowTo.setHeight(rowFrom.getHeight());
            for (int j = 0; j < rowFrom.getLastCellNum(); j++) {
                sheet2.setColumnWidth(j, sheet1.getColumnWidth(j));
                if(null != sheet1.getColumnStyle(j)){
                    sheet2.setDefaultColumnStyle(j, sheet1.getColumnStyle(j));
                }

                cellFrom = rowFrom.getCell(j);
                if (null == cellFrom){
                    continue;
                }

                cellTo = rowTo.createCell(j);
                cellTo.setCellStyle(cellFrom.getCellStyle());
                cellTo.setCellType(cellFrom.getCellType());

                if(Cell.CELL_TYPE_STRING == cellFrom.getCellType()){
                    cellTo.setCellValue(cellFrom.getStringCellValue());
                }else if(Cell.CELL_TYPE_NUMERIC == cellFrom.getCellType()){
                    cellTo.setCellValue(cellFrom.getNumericCellValue());
                }
            }
        }

        sheet2.setDisplayGridlines(true);//
        FileOutputStream fileOut = new FileOutputStream(file);
        wb.write(fileOut);
        fileOut.close();

        System.out.println(file + " 复制sheet成功!");
    }

    public static void copy_sheet(Workbook wb) throws Exception  {
        //单元格合并
        CellRangeAddress region = null;
        Sheet sheet1 = wb.getSheetAt(0);
        Sheet sheet2 = wb.createSheet(sheet1.getSheetName() + "_副本");
        for (int i = 0; i < sheet1.getNumMergedRegions(); i++) {
            region = sheet1.getMergedRegion(i);
            if ((region.getFirstColumn() >= sheet1.getFirstRowNum())
                    && (region.getLastRow() <= sheet1.getLastRowNum())) {
                sheet2.addMergedRegion(region);
            }
        }

        //复制内容
        Row rowFrom = null;
        Row rowTo = null;
        Cell cellFrom = null;
        Cell cellTo = null;
        for (int i = sheet1.getFirstRowNum(); i < sheet1.getLastRowNum(); i++) {
            rowFrom = sheet1.getRow(i);
            if (null == rowFrom){
                continue;
            }
            rowTo = sheet2.createRow(i);
            rowTo.setHeight(rowFrom.getHeight());
            for (int j = 0; j < rowFrom.getLastCellNum(); j++) {
                sheet2.setColumnWidth(j, sheet1.getColumnWidth(j));
                if(null != sheet1.getColumnStyle(j)){
                    sheet2.setDefaultColumnStyle(j, sheet1.getColumnStyle(j));
                }
                cellFrom = rowFrom.getCell(j);
                if (null == cellFrom){
                    continue;
                }
                cellTo = rowTo.createCell(j);
                cellTo.setCellStyle(cellFrom.getCellStyle());
                cellTo.setCellType(cellFrom.getCellType());
                if(Cell.CELL_TYPE_STRING == cellFrom.getCellType()){
                    cellTo.setCellValue(cellFrom.getStringCellValue());
                }else if(Cell.CELL_TYPE_NUMERIC == cellFrom.getCellType()){
                    cellTo.setCellValue(cellFrom.getNumericCellValue());
                }
            }
        }
        sheet2.setDisplayGridlines(true);//
    }
}
