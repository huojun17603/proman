package com.ich.proman.project.service.impl;

import com.ich.admin.dao.EmployeeMapper;
import com.ich.admin.dto.EmployeeQueryDto;
import com.ich.admin.dto.LocalEmployee;
import com.ich.admin.service.LocalEmployeeService;
import com.ich.core.base.IDUtils;
import com.ich.core.base.ObjectHelper;
import com.ich.core.file.ExcelUtil;
import com.ich.core.file.FileUtil;
import com.ich.core.http.entity.HttpResponse;
import com.ich.core.http.other.CustomException;
import com.ich.proman.base.ExcelTest;
import com.ich.proman.base.POIUtils;
import com.ich.proman.base.ProjectQuery;
import com.ich.proman.project.controller.TerminalController;
import com.ich.proman.project.mapper.ProCatalogMapper;
import com.ich.proman.project.mapper.ProModularMapper;
import com.ich.proman.project.mapper.ProTaskMapper;
import com.ich.proman.project.pojo.ProCatalog;
import com.ich.proman.project.pojo.ProModular;
import com.ich.proman.project.pojo.ProTask;
import com.ich.proman.project.pojo.Project;
import com.ich.proman.project.service.ProTaskEXLSService;
import com.ich.proman.project.service.ProjectCoreService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ProTaskEXLSServiceImpl implements ProTaskEXLSService {

    @Autowired
    private ProjectCoreService projectCoreService;
    @Autowired
    private ProCatalogMapper catalogMapper;
    @Autowired
    private ProTaskMapper taskMapper;
    @Autowired
    private ProModularMapper modularMapper;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private LocalEmployeeService localEmployeeService;

    public static String DEFAULT_DATE_PATTERN="yyyy/MM/dd";//默认日期格式

    public static String DEFAULT_TEMPALE="/temp.xlsx";//默认日期格式

    @Override
    public void executedown(String projectid, String catalogids, String terminals, HttpServletResponse response) {
        try {
            InputStream in = this.getClass().getResourceAsStream(DEFAULT_TEMPALE);
            XSSFWorkbook workbook = new XSSFWorkbook(in);
//            CellStyle titleStyle = workbook_temp.getCellStyleAt((short) 0);
//            XSSFSheet sheet_tmp = workbook_temp.getSheetAt(0);

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            Project project = projectCoreService.findById(projectid);
            String[] catalogid_arr = catalogids.split(",");
            String[] terminal_arr = terminals.split(",");
//            SXSSFWorkbook workbook = new SXSSFWorkbook(1000);//缓存
//            workbook.setCompressTempFiles(true);


            // 列头样式（居中）
            CellStyle dataCStyle = workbook.createCellStyle();
            dataCStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            dataCStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            dataCStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            dataCStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            dataCStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            dataCStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            dataCStyle.setFillBackgroundColor(HSSFColor.WHITE.index);
            dataCStyle.setFillForegroundColor(HSSFColor.WHITE.index);
            Font headerFont = workbook.createFont();
            headerFont.setFontName("宋体");
            headerFont.setFontHeightInPoints((short) 11);
            dataCStyle.setFont(headerFont);

            // 列头样式（靠左）
            CellStyle dataLStyle = workbook.createCellStyle();
            dataLStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            dataLStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            dataLStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            dataLStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            dataLStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            dataLStyle.setFillBackgroundColor(HSSFColor.WHITE.index);
            dataLStyle.setFillForegroundColor(HSSFColor.WHITE.index);
            dataLStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            dataLStyle.setFont(headerFont);

            int sheetNum = 0;
            for(String catalogid : catalogid_arr){
                ProCatalog catalog = catalogMapper.selectById(catalogid);
                for(String terminal : terminal_arr){
                    Map<String,Object> paramMap = new HashMap<String,Object>();
                    paramMap.put("projectid",projectid);
                    paramMap.put("terminal", terminal);
                    paramMap.put("catalogid", catalogid);
                    paramMap.put("order", "2");
                    List<Map<String,Object>> list = taskMapper.selectListByQuery(paramMap);
                    if(ObjectHelper.isNotEmpty(list)){
                        //第一行
                        String catalog_title = catalog.getTitle();
                        ExcelTest.copy_sheet(workbook);
                        XSSFSheet sheet = workbook.getSheetAt(sheetNum + 1);
                        workbook.setSheetName(sheetNum + 1, catalog_title+"("+terminal+")");
                        sheetNum ++;

                        XSSFRow titleRow = sheet.getRow(0);
//                        titleRow.setRowStyle(sheet_tmp.getRow(0).getRowStyle());
                        titleRow.getCell(0).setCellValue("项目进度表（"+project.getTitle()+"）");
//                        titleRow.getCell(0).setCellStyle(sheet_tmp.getRow(0).getCell(0).getCellStyle());
                        //第二行
                        XSSFRow topRow = sheet.getRow(1);
                        String top_val[] = new String[]{"目录",catalog.getTitle(),"终端",terminal,"","","","",""};
                        for(int i=0;i<9;i++) {
                            topRow.getCell(i).setCellValue(top_val[i]);
                        }
                        //第三行
                        XSSFRow headerRow = sheet.getRow(2);
                        String header_val[] = new String[]{"模块","编码","标题","描述","人员","工时（小时）","开始时间","实际完成时间","当前状态"};
                        for(int i=0;i<9;i++)
                        {
                            headerRow.getCell(i).setCellValue(header_val[i]);
                        }
                        //开始内容
                        int rowIndex = 3;
                        for (int i = 0; i < list.size(); i++){
                            Map<String,Object> taskMap = list.get(i);
                            XSSFRow dataRow = sheet.createRow(rowIndex + i);
                            dataRow.setHeight((short) 465);
                            Object modularname = taskMap.get("modularname");
                            Object code = taskMap.get("code");
                            Object title = taskMap.get("title");
                            Object content = taskMap.get("content");
                            Object power = taskMap.get("power");
                            Object receivename = taskMap.get("receivename");
                            Object receivetime = taskMap.get("receivetime");
                            Object completetime = taskMap.get("completetime");
                            Integer status = (Integer)taskMap.get("status");
                            String statusVal = "待完成";
                            if(status == 3) statusVal = "完成";
                            if(status == 4) statusVal = "废弃";
                            Object data_val[] = new Object[]{modularname,code,title,content,receivename,power,receivetime,completetime,statusVal};
                            for (int x = 0; x < data_val.length; x++) {
                                XSSFCell newCell = dataRow.createCell(x);
                                Object o =  data_val[x];
                                String cellValue = "";
                                if(o==null) cellValue = "";
                                else if(o instanceof Date) cellValue = new SimpleDateFormat(DEFAULT_DATE_PATTERN).format(o);
                                else if(o instanceof Float || o instanceof Double)
                                    cellValue= new BigDecimal(o.toString()).setScale(2,BigDecimal.ROUND_HALF_UP).toString();
                                else cellValue = o.toString();
                                newCell.setCellValue(cellValue);
                                if(x==3){
                                    newCell.setCellStyle(dataLStyle);
                                }else {
                                    newCell.setCellStyle(dataCStyle);
                                }
                            }
                        }
                    }
                }
            }
            workbook.removeSheetAt(0);
            workbook.write(os);
            //输出到页面
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            // 设置response参数，可以打开下载页面
            response.reset();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename="+ new String((project.getTitle()+"("+ project.getVersion() +")" + ".xlsx").getBytes(), "iso-8859-1"));
            response.setContentLength(content.length);
            ServletOutputStream outputStream = response.getOutputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            BufferedOutputStream bos = new BufferedOutputStream(outputStream);
            byte[] buff = new byte[8192];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);

            }
            bis.close();
            bos.close();
            outputStream.flush();
            outputStream.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public HttpResponse executeUpload(String projectid, MultipartFile multipartFile) {
        try {
            LocalEmployee employee = localEmployeeService.findLocalEmployee();
            String fileName = multipartFile.getOriginalFilename();//文件名称
            String ext = FileUtil.getFileExt(fileName).substring(1);
            String catalog = "";
            String catalogid = "";
            String terminal = "";
            if (ext.equals("xls")) {
                POIFSFileSystem fileSystem = new POIFSFileSystem(multipartFile.getInputStream());
                HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);
                for(int i = 0; i < workbook.getNumberOfSheets(); i++){
                    HSSFSheet sheet = workbook.getSheetAt(i);
                    for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {//从第二行开始
                        HSSFRow row = sheet.getRow(j);
                        if(j==1){
                            catalog = row.getCell(1).toString();
                            terminal = row.getCell(3).toString();
                            //验证内容
                            if(ObjectHelper.isEmpty(catalog)) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的目录信息");
                            ProCatalog catalogEntity = catalogMapper.selectByPTitle(projectid,catalog);
                            if(ObjectHelper.isEmpty(catalogEntity)){
                                return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的目录信息");
                            }
                            catalogid = catalogEntity.getId();
                            if(!TerminalController.vryTerminals(terminal)){
                                return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的终端信息");
                            }

                        }else if(j>=3 && row != null){//从第四行开始，进入内容
                            String modularname = row.getCell(0).getStringCellValue();
                            if(ObjectHelper.isEmpty(modularname)) break;
                            List<ProModular> modulars = modularMapper.selectByPName(projectid,catalogid,modularname);
                            if(ObjectHelper.isEmpty(modulars))  throw new CustomException(HttpResponse.HTTP_ERROR,"无效的模块信息在第"+(i+1)+"页-第"+(j+1)+"行");
                            String username = row.getCell(4).getStringCellValue();
                            if(ObjectHelper.isEmpty(username))  throw new CustomException(HttpResponse.HTTP_ERROR,"无效的人员信息在第"+(i+1)+"页-第"+(j+1)+"行");
                            Map<Object,Object> param = new HashMap<>();
                            EmployeeQueryDto employeeQueryDto = new EmployeeQueryDto();
                            employeeQueryDto.setName(username);
                            param.put("dto", employeeQueryDto);
                            List<EmployeeQueryDto> employees = employeeMapper.queryEmployee(param);
                            if(ObjectHelper.isEmpty(employees))  throw new CustomException(HttpResponse.HTTP_ERROR,"无效的人员信息在第"+(i+1)+"页-第"+(j+1)+"行");
                            ProModular modular = modulars.get(0);
                            EmployeeQueryDto employeeDto = employees.get(0);
                            ProTask task = new ProTask();
                            task.setProjectid(projectid);
                            task.setCatalogid(catalogid);
                            task.setModularid(modular.getId());
                            task.setUsername(employee.getEmployeeName());
                            task.setUserid(employee.getEmployeeId());
                            task.setTerminal(terminal);
                            String code = row.getCell(1).getStringCellValue();
                            if(ObjectHelper.isEmpty(code))  throw new CustomException(HttpResponse.HTTP_ERROR,"不允许存在空编码，第"+(i+1)+"页-第"+(j+1)+"行");
                            String title = row.getCell(2).getStringCellValue();
                            if(ObjectHelper.isEmpty(title))  throw new CustomException(HttpResponse.HTTP_ERROR,"不允许存在空标题，第"+(i+1)+"页-第"+(j+1)+"行");
                            String content = row.getCell(3).getStringCellValue();
                            if(ObjectHelper.isEmpty(content))  throw new CustomException(HttpResponse.HTTP_ERROR,"不允许存在空内容，第"+(i+1)+"页-第"+(j+1)+"行");
                            int power = (int)row.getCell(5).getNumericCellValue();
                            if(ObjectHelper.isEmpty(power)||power==0)  throw new CustomException(HttpResponse.HTTP_ERROR,"不允许存在空工时，第"+(i+1)+"页-第"+(j+1)+"行");
                            task.setCode(code);
                            task.setTitle(title);
                            task.setReceivename(employeeDto.getName());
                            task.setReceiveid(employeeDto.getId());
                            task.setContent(content);
                            task.setPower(power);
                            if(row.getCell(6).toString().equals("/")||ObjectHelper.isEmpty(row.getCell(6).toString())){
                                task.setReceivetime(null);
                            }else{
                                task.setReceivetime(row.getCell(6).getDateCellValue());
                            }
                            if(row.getCell(7).toString().equals("/")||ObjectHelper.isEmpty(row.getCell(7).toString())){
                                task.setCompletetime(null);
                            }else{
                                task.setCompletetime(row.getCell(7).getDateCellValue());
                            }
                            Integer status = findStatus(row.getCell(8).getStringCellValue());
                            if(ObjectHelper.isEmpty(status)||status==0)  throw new CustomException(HttpResponse.HTTP_ERROR,"错误的状态信息，第"+(i+1)+"页-第"+(j+1)+"行");
                            task.setStatus(status);
                            HttpResponse response = write(task);
                            if(response.getStatus()==HttpResponse.HTTP_ERROR){
                                throw new CustomException(HttpResponse.HTTP_ERROR,response.getMsg() + "第"+(i+1)+"页-第"+(j+1)+"行");
                            }
                        }
                    }
                }
            } else if (ext.equals("xlsx")) {
                XSSFWorkbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
                for(int i = 0; i < workbook.getNumberOfSheets(); i++){
                    XSSFSheet sheet = workbook.getSheetAt(i);
                    for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {//从第二行开始
                        XSSFRow row = sheet.getRow(j);
                        if(j==1){
                            catalog = row.getCell(1).toString();
                            terminal = row.getCell(3).toString();
                            //验证内容
                            if(ObjectHelper.isEmpty(catalog)) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的目录信息");
                            ProCatalog catalogEntity = catalogMapper.selectByPTitle(projectid,catalog);
                            if(ObjectHelper.isEmpty(catalogEntity)){
                                return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的目录信息");
                            }
                            catalogid = catalogEntity.getId();
                            if(!TerminalController.vryTerminals(terminal)){
                                return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的终端信息");
                            }

                        }else if(j>=3 && row != null){//从第四行开始，进入内容
                            String modularname = row.getCell(0).getStringCellValue();
                            if(ObjectHelper.isEmpty(modularname)) break;
                            List<ProModular> modulars = modularMapper.selectByPName(projectid,catalogid,modularname);
                            if(ObjectHelper.isEmpty(modulars))  throw new CustomException(HttpResponse.HTTP_ERROR,"无效的模块信息在第"+(i+1)+"页-第"+(j+1)+"行");
                            String username = row.getCell(4).getStringCellValue();
                            if(ObjectHelper.isEmpty(username))  throw new CustomException(HttpResponse.HTTP_ERROR,"无效的人员信息在第"+(i+1)+"页-第"+(j+1)+"行");
                            Map<Object,Object> param = new HashMap<>();
                            EmployeeQueryDto employeeQueryDto = new EmployeeQueryDto();
                            employeeQueryDto.setName(username);
                            param.put("dto", employeeQueryDto);
                            List<EmployeeQueryDto> employees = employeeMapper.queryEmployee(param);
                            if(ObjectHelper.isEmpty(employees))  throw new CustomException(HttpResponse.HTTP_ERROR,"无效的人员信息在第"+(i+1)+"页-第"+(j+1)+"行");
                            ProModular modular = modulars.get(0);
                            EmployeeQueryDto employeeDto = employees.get(0);
                            ProTask task = new ProTask();
                            task.setProjectid(projectid);
                            task.setCatalogid(catalogid);
                            task.setModularid(modular.getId());
                            task.setUsername(employee.getEmployeeName());
                            task.setUserid(employee.getEmployeeId());
                            task.setTerminal(terminal);
                            String code = row.getCell(1).getStringCellValue();
                            if(ObjectHelper.isEmpty(code))  throw new CustomException(HttpResponse.HTTP_ERROR,"不允许存在空编码，第"+(i+1)+"页-第"+(j+1)+"行");
                            String title = row.getCell(2).getStringCellValue();
                            if(ObjectHelper.isEmpty(title))  throw new CustomException(HttpResponse.HTTP_ERROR,"不允许存在空标题，第"+(i+1)+"页-第"+(j+1)+"行");
                            String content = row.getCell(3).getStringCellValue();
                            if(ObjectHelper.isEmpty(content))  throw new CustomException(HttpResponse.HTTP_ERROR,"不允许存在空内容，第"+(i+1)+"页-第"+(j+1)+"行");
                            int power = (int)row.getCell(5).getNumericCellValue();
                            if(ObjectHelper.isEmpty(power)||power==0)  throw new CustomException(HttpResponse.HTTP_ERROR,"不允许存在空工时，第"+(i+1)+"页-第"+(j+1)+"行");
                            task.setCode(code);
                            task.setTitle(title);
                            task.setReceivename(employeeDto.getName());
                            task.setReceiveid(employeeDto.getId());
                            task.setContent(content);
                            task.setPower(power);
                            if(row.getCell(6).toString().equals("/")||ObjectHelper.isEmpty(row.getCell(6).toString())){

                                task.setReceivetime(null);
                            }else{
                                System.out.println(row.getCell(6).toString());
                                task.setReceivetime(row.getCell(6).getDateCellValue());
                            }
                            if(row.getCell(7).toString().equals("/")||ObjectHelper.isEmpty(row.getCell(7).toString())){
                                task.setCompletetime(null);
                            }else{

                                task.setCompletetime(row.getCell(7).getDateCellValue());
                            }
                            Integer status = findStatus(row.getCell(8).getStringCellValue());
                            if(ObjectHelper.isEmpty(status)||status==0)  throw new CustomException(HttpResponse.HTTP_ERROR,"错误的状态信息，第"+(i+1)+"页-第"+(j+1)+"行");
                            task.setStatus(status);
                            HttpResponse response = write(task);
                            if(response.getStatus()==HttpResponse.HTTP_ERROR){
                                throw new CustomException(HttpResponse.HTTP_ERROR,response.getMsg() + "第"+(i+1)+"页-第"+(j+1)+"行");
                            }
                        }
                    }
                }
            } else {
                return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的文档！");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new CustomException(HttpResponse.HTTP_ERROR,e.getMessage());
        }
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }



    private HttpResponse write(ProTask task){
        ProTask entity = taskMapper.selectByPCode(task.getProjectid(),task.getCode());
        if(ObjectHelper.isNotEmpty(entity)){//如果存在
            task.setId(entity.getId());
            //验证信息一致性，防止冲突
            if(!entity.getCatalogid().equals(task.getCatalogid())
                    ||!entity.getModularid().equals(task.getModularid())
                    ||!entity.getTerminal().equals(task.getTerminal()))
                return new HttpResponse(HttpResponse.HTTP_ERROR,"已存在的编码的基础信息与当前不一致！");
            if(entity.getStatus()==3||entity.getStatus()==4){//如果已完成或废除则不允许操作
                return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
            }
            if(task.getStatus()==2){//允许修改，标题、内容、人员、工时、开始、结束
                taskMapper.updateXBase(task);
            }
            if(task.getStatus()==3){
                if(ObjectHelper.isEmpty(task.getReceivetime())
                        ||ObjectHelper.isEmpty(task.getCompletetime()))
                    return new HttpResponse(HttpResponse.HTTP_ERROR,"已完成的进度必须存在开始和结束时间！");
                taskMapper.updateXBase(task);
            }
            if(task.getStatus()==4){//废弃则，不会存在修改行为
                taskMapper.delete(entity.getId());
            }
        }else{
            task.setId(IDUtils.createUUId());
            task.setCreatetime(new Date());
            if(task.getStatus()==3){
                if(ObjectHelper.isEmpty(task.getReceivetime())
                        ||ObjectHelper.isEmpty(task.getCompletetime()))
                    return new HttpResponse(HttpResponse.HTTP_ERROR,"已完成的进度必须存在开始和结束时间！");
                taskMapper.insert(task);
            }else{
                taskMapper.insert(task);
            }
        }
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    private Integer findStatus(String status) {
        if(status.equals("待完成")){
            return 2;
        }else if(status.equals("完成")){
            return 3;
        }else if(status.equals("废弃")){
            return 4;
        }else {
            return 0;
        }
    }


}
