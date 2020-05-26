package com.bdqn.util;

import com.bdqn.entity.JXinQian;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ImportXinQianExcel {

    //总行数
    private int totalRows = 0;
    //总条数
    private int totalCells = 0;
    //错误信息接收器
    private String errorMsg;
    //构造方法
    public ImportXinQianExcel(){}
    //获取总行数
    public int getTotalRows()  { return totalRows;}
    //获取总列数
    public int getTotalCells() {  return totalCells;}
    //获取错误信息
    public String getErrorInfo() { return errorMsg; }

    /**
     * 验证EXCEL文件
     * @param filePath
     * @return
     */
    public boolean validateExcel(String filePath){
        if (filePath == null || !(WDWUtil.isExcel2003(filePath) || WDWUtil.isExcel2007(filePath))){
            errorMsg = "文件名不是excel格式";
            return false;
        }
        return true;
    }

    /**
     * 读EXCEL文件，获取客户信息集合
     * @param fileName
     * @return
     */
    public List<JXinQian> getExcelInfo(String fileName, MultipartFile Mfile){
        //把spring文件上传的MultipartFile转换成CommonsMultipartFile类型
        CommonsMultipartFile cf= (CommonsMultipartFile)Mfile; //获取本地存储路径
        File file = new File("C:\\java\\fileupload");
        //创建一个目录 （它的路径名由当前 File 对象指定，包括任一必须的父路径。）
        if (!file.exists()) {
            file.mkdirs();
        }
        //新建一个文件
//        File file1 = new File("C:\\fileupload" + new Date().getTime() + ".xlsx");E:\java
        File file1 = new File("C:\\java\\fileupload" + File.separator + fileName);


        //将上传的文件写入新建的文件中
        try {
            cf.getFileItem().write(file1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //初始化客户信息的集合
        List<JXinQian> xinQianList=new ArrayList<JXinQian>();
        //初始化输入流
        InputStream is = null;
        try{
            //验证文件名是否合格
            if(!validateExcel(fileName)){
                return null;
            }
            //根据文件名判断文件是2003版本还是2007版本
            boolean isExcel2003 = true;
            if(WDWUtil.isExcel2007(fileName)){
                isExcel2003 = false;
            }
            //根据新建的文件实例化输入流
            is = new FileInputStream(file1);
            //根据excel里面的内容读取客户信息
            xinQianList = getExcelInfo(is, isExcel2003);
            is.close();
        }catch(Exception e){
            e.printStackTrace();
        } finally{
            if(is !=null)
            {
                try{
                    is.close();
                }catch(IOException e){
                    is = null;
                    e.printStackTrace();
                }
            }
        }
        file1.delete();
        return xinQianList;
    }
    /**
     * 根据excel里面的内容读取客户信息
     * @param is 输入流
     * @param isExcel2003 excel是2003还是2007版本
     * @return
     * @throws IOException
     */
    public  List<JXinQian> getExcelInfo(InputStream is,boolean isExcel2003){
        List<JXinQian> jXinQianList=null;
        try{
            /** 根据版本选择创建Workbook的方式 */
            Workbook wb = null;
            //当excel是2003时
            if(isExcel2003){
                wb = new HSSFWorkbook(is);
            }
            else{//当excel是2007时
                wb = new XSSFWorkbook(is);
            }
            //读取Excel里面客户的信息
            jXinQianList=readExcelValue(wb);
        }
        catch (IOException e)  {
            e.printStackTrace();
        }
        return jXinQianList;
    }
    /**
     * 读取Excel里面客户的信息
     * @param wb
     * @return
     */
    private List<JXinQian> readExcelValue(Workbook wb){
        //得到第一个shell
        Sheet sheet=wb.getSheetAt(0);
        //得到Excel的行数
        this.totalRows=sheet.getPhysicalNumberOfRows();
        //得到Excel的列数(前提是有行数)
        if(totalRows>=1 && sheet.getRow(0) != null){
            this.totalCells=sheet.getRow(0).getPhysicalNumberOfCells();
        }
//        System.out.println("行totalRows>>>>"+totalRows);
//        System.out.println("列totalCells>>>>"+totalCells);
        List<JXinQian> customerList=new ArrayList<JXinQian>();
        JXinQian xinQian;
        //循环Excel行数,从第二行开始。标题不入库
        for(int r=1;r<totalRows;r++){
            Row row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            xinQian = new JXinQian();
            //循环Excel的列
            for(int c = 0; c <this.totalCells; c++){

                Cell cell = row.getCell(c);
                if ("".equals(cell)){
                    continue;
                }

                if (null != cell ){

                    if (sheet.getRow(0).getCell(c).toString().equals("销售")){
//                        System.out.println("销售>>>>>"+cell.toString());
//                        System.out.println("销售>>>>>"+stringFormat(cell.toString()));
                        xinQian.setCreatname(stringFormat(cell.toString()));
                    }
                    if (sheet.getRow(0).getCell(c).toString().equals("录单时间")){
//                        System.out.println("录单时间>>>>>"+cell.getDateCellValue());
                        xinQian.setCreatdate(cell.getDateCellValue());
                    }
                    if (sheet.getRow(0).getCell(c).toString().equals("经理组")){
//                        System.out.println("经理组>>>>>"+cell.toString());
                        xinQian.setManager_group(stringFormat(cell.toString()));
                    }
                    if (sheet.getRow(0).getCell(c).toString().equals("区域")){
//                        System.out.println("区域>>>>>"+cell.toString());
                        xinQian.setArea(stringFormat(cell.toString()));
                    }
                    if (sheet.getRow(0).getCell(c).toString().equals("ID")){
//                        System.out.println("区域>>>>>"+cell.toString());
                        xinQian.setXinqianid(stringFormat(cell.toString()));
                    }
                    if (sheet.getRow(0).getCell(c).toString().equals("公司名称")){
//                        System.out.println("公司名称>>>>>"+cell.toString());
                        xinQian.setCanme(stringFormat(cell.toString()));
                    }
                    if (sheet.getRow(0).getCell(c).toString().equals("订单行号")){
//                        System.out.println("订单行号>>>>>"+cell.toString());
                        xinQian.setBianhao_customer(stringFormat(cell.toString()));
                    }
                    if (sheet.getRow(0).getCell(c).toString().equals("金额")){
//                        System.out.println("金额>>>>>"+new BigDecimal(stringFormat(cell.toString())));
                        xinQian.setWprice(new BigDecimal(stringFormat(cell.toString())));
                    }
                    if (sheet.getRow(0).getCell(c).toString().equals("网销宝")){
//                        System.out.println("网销宝>>>>>"+new BigDecimal(stringFormat(cell.toString())));
//                        xinQian.setWprice(new BigDecimal(stringFormat(cell.toString() == "" || cell.toString() == null ? "0" : cell.toString())));
                        String content =stringFormat(cell.toString());
                        xinQian.setWxb( !"".equals(content) ? 1 : 0  );
                    }
                    if (sheet.getRow(0).getCell(c).toString().equals("行业")){
//                        System.out.println("行业>>>>>"+cell.toString());
                        xinQian.setCtype(stringFormat(cell.toString()));
                    }
                    if (sheet.getRow(0).getCell(c).toString().equals("到单方式")){
//                        System.out.println("到单方式>>>>>"+cell.toString());
                        xinQian.setTo_order_type(stringFormat(cell.toString()));
                    }
                    if (sheet.getRow(0).getCell(c).toString().equals("联系人")){
//                        System.out.println("联系人>>>>>"+cell.toString());
                        xinQian.setContact_person(stringFormat(cell.toString()));
                    }
                    if (sheet.getRow(0).getCell(c).toString().equals("手机")){
//                        System.out.println("手机1>>>>>"+cell.toString().getClass().toString());
//                        System.out.println("手机2>>>>>"+cell.toString().replace(String.valueOf((char)160),""));
//                        System.out.println("手机4>>>>>"+Double.valueOf(stringFormat(cell.toString().replace(String.valueOf((char)160),""))));
//                        System.out.println("手机3>>>>>"+Double.valueOf(stringFormat(cell.toString())));
                        xinQian.setPhone(stringFormat(cell.toString()));
//                        xinQian.setPhone(phoneFormat(Double.valueOf(stringFormat(cell.toString().replace(String.valueOf((char)160),"")))));
                    }
                    if (sheet.getRow(0).getCell(c).toString().equals("到款方式")){
//                        System.out.println("到款方式>>>>>"+cell.toString());
                        xinQian.setTo_money_type(stringFormat(cell.toString()));
                    }
                    if (sheet.getRow(0).getCell(c).toString().equals("开通日期")){
//                        System.out.println("开通日期>>>>>"+cell.getDateCellValue());
                        xinQian.setOpentime(cell.getDateCellValue());
                    }
                    if (sheet.getRow(0).getCell(c).toString().equals("实际地址")){
//                        System.out.println("实际地址>>>>>"+cell.toString());
                        xinQian.setAddress(stringFormat(cell.toString()));
                    }
                    if (sheet.getRow(0).getCell(c).toString().equals("单量")){
//                        System.out.println("单量>>>>>"+cell.toString());
                        xinQian.setOrdercount(Integer.valueOf(doubleFormat(cell.toString())));
                    }
                    if (sheet.getRow(0).getCell(c).toString().equals("是否赠送首保")){
//                        System.out.println("是否赠送首保>>>>>"+cell.toString());
//                        xinQian.setIstp("是" .equals(stringFormat(cell.toString()))? 1 : 0 );
                        String content =stringFormat(cell.toString());
                        xinQian.setIsfirstinsurance(content);
                    }
                    //2020-03-28
                    String titleName = sheet.getRow(0).getCell(c).toString();
                    if("续费日期".equals(titleName)){
                        xinQian.setRenewtime(cell.getDateCellValue());
                    }
                    if("是否退款".equals(titleName)){
                        xinQian.setIsrefund("否".equals(stringFormat(cell.toString()))  ? 0 : 1 );
                    }
                }
            }
            //添加知识
            customerList.add(xinQian);
        }
        return customerList;
    }

    public void dateFormat(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String timeFormat = sdf.format(date);
        System.out.println(timeFormat);
    }

    public String stringFormat(String s){
        return s.replaceAll("\\s*","");
    }

    public String doubleFormat(String s){
        return s.replaceAll("\\s*","").substring(0, s.indexOf("."));
    }

    public String phoneFormat(double s){
        DecimalFormat df = new DecimalFormat("#");
        return df.format(s);
    }



}
