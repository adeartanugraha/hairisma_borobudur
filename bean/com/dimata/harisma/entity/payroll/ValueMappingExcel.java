/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.entity.payroll;

import com.dimata.harisma.entity.employee.PstEmployee;
import java.util.Vector;
import java.text.NumberFormat;
import java.io.*;
import java.util.*;
import java.lang.System;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import com.dimata.util.Excel;
import com.dimata.util.Formater;
import com.dimata.util.blob.TextLoader;
import org.apache.poi.hssf.usermodel.*;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.hssf.record.*;
import org.apache.poi.hssf.model.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.*;


import com.dimata.util.*;
import com.dimata.gui.jsp.*;
import com.dimata.qdep.form.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.attendance.*;
import com.dimata.harisma.entity.log.LogSysHistory;
import com.dimata.harisma.entity.log.PstLogSysHistory;
import com.dimata.harisma.entity.payroll.*;
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dimata 007
 */
public class ValueMappingExcel {

    public String processImportExcel(String compCode, ServletConfig config, HttpServletRequest request, HttpServletResponse response) {
        String html = "";
        int NUM_HEADER = 1;
        int NUM_CELL = 0;
        String tdColor = "#FFF;";
        try {
            TextLoader uploader = new TextLoader();
            ByteArrayInputStream inStream = null;

            uploader.uploadText(config, request, response);
            Object obj = uploader.getTextFile("file");
            byte byteText[] = null;
            byteText = (byte[]) obj;
            inStream = new ByteArrayInputStream(byteText);

            POIFSFileSystem fs = new POIFSFileSystem(inStream);

            HSSFWorkbook wb = new HSSFWorkbook(fs);
            System.out.println("creating workbook");
             String jsonProccess = "";
            int numOfSheets = wb.getNumberOfSheets();
            String className = "";
            for (int i = 0; i < numOfSheets; i++) {

                int r = 0;

                HSSFSheet sheet = (HSSFSheet) wb.getSheetAt(i);
                html += "<div class=\"info\"><strong> Component name : " + compCode + "<br>";
                html += "<strong> Sheet name : " + sheet.getSheetName() + "</div>";
                if (sheet == null || sheet.getSheetName() == null || sheet.getSheetName().length() < 1) {
                    html += " NOT MATCH : Period name and sheet name";
                    continue;
                }
                if (sheet.getSheetName().equals(compCode)){
                    int rows = sheet.getPhysicalNumberOfRows();
                    jsonProccess = "{ \"COMP CODE\" : \""+compCode+ "\", \"data\" : [";
                    // loop untuk row dimulai dari numHeaderRow (0, .. numHeaderRow diabaikan) => untuk yang bukan sheet pertaman
                    int start = (i == 0) ? 0 : NUM_HEADER;
                    int countInsert = 0;
                    html += "<table class=\"tblStyle\">";
                    for (r = start; r < rows; r++) {
                        Value_Mapping valueMapping = new Value_Mapping();
                         className = valueMapping.getClass().getName();
                        valueMapping.setCompCode(compCode);
                        try {
                            HSSFRow row = sheet.getRow(r);
                            int cells = 0;
                            //if number of cell is static
                            if (NUM_CELL > 0) {
                                cells = NUM_CELL;
                            } //number of cell is dinamyc
                            else {
                                cells = row.getPhysicalNumberOfCells();
                            }

                            // ambil jumlah kolom yang sebenarnya
                            NUM_CELL = cells;
                            html += "<tr>";
                            for (int c = 0; c < cells; c++) {
                                HSSFCell cell = row.getCell((short) c);
                                String value = null;
                                if (cell != null) {
                                    switch (cell.getCellType()) {
                                        case HSSFCell.CELL_TYPE_FORMULA:
                                            value = String.valueOf(cell.getCellFormula());
                                            break;
                                        case HSSFCell.CELL_TYPE_NUMERIC:
                                            value = Formater.formatNumber(cell.getNumericCellValue(), "###");
                                            break;
                                        case HSSFCell.CELL_TYPE_STRING:
                                            value = String.valueOf(cell.getStringCellValue());
                                            break;
                                        default:
                                            value = String.valueOf(cell.getStringCellValue() != null ? cell.getStringCellValue() : "");
                                            ;
                                    }
                                }
                                if (r == 0){
                                    html += "<td style=\"background-color:#DDD;\"><strong>"+ value + "</strong></td>";
                                } else {
                                    long oidValue = 0;
                                    if (value.equals("NULL")){
                                        html += "<td style=\"background-color:"+tdColor+"\">-</td>";
                                        Date dateNow = new Date();
                                        switch(c){
                                            case 1:
                                                valueMapping.setStartdate(dateNow);
                                                break;
                                            case 2:
                                                valueMapping.setEnddate(dateNow);
                                                break;
                                            case 3:
                                                valueMapping.setCompany_id(0);
                                                break;
                                            case 4:
                                                valueMapping.setDivision_id(0);
                                                break;
                                            case 5:
                                                valueMapping.setDepartment_id(0);
                                                break;
                                            case 6:
                                                valueMapping.setSection_id(0);
                                                break;
                                            case 7:
                                                valueMapping.setLevel_id(0);
                                                break;
                                            case 8:
                                                valueMapping.setMarital_id(0);
                                                break;
                                            case 9:
                                                valueMapping.setLength_of_service(0);
                                                break;
                                            case 10:
                                                valueMapping.setEmployee_category(0);
                                                break;
                                            case 11:
                                                valueMapping.setPosition_id(0);
                                                break;
                                            case 12:
                                                valueMapping.setGrade(0);
                                                break;
                                            case 13:
                                                valueMapping.setEmployee_id(0);
                                                break;
                                            case 14:
                                                valueMapping.setSex(-1);
                                                break;
                                            case 15:
                                                valueMapping.setValue(0);
                                                break;
											case 16:
												valueMapping.setRemark(0);
                                        }
                                    } else {
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                        switch(c){
                                            case 1:
                                                Date dateStart = sdf.parse(value);
                                                html += "<td style=\"background-color:"+tdColor+"\">"+ value + "</td>";
                                                valueMapping.setStartdate(dateStart);
                                                 jsonProccess += "{Start Date:\""+value+"\",";
                                                break;
                                            case 2:
                                                Date dateEnd = sdf.parse(value);
                                                html += "<td style=\"background-color:"+tdColor+"\">"+ value + "</td>";
                                                valueMapping.setEnddate(dateEnd);
                                                jsonProccess += "End Date:\""+value+"\",";
                                                break;
                                            case 3:
                                                oidValue = getCompanyId(value);
                                                if (oidValue != 0){
                                                    html += "<td style=\"background-color:"+tdColor+"\">"+ value + "</td>";
                                                    jsonProccess += "Company:\""+value+"\",";
                                                } else {
                                                    html += "<td style=\"background-color:#fcd4dc; color:#d04761;\">"+ value + "</td>";
                                                    jsonProccess += "Company:\"\",";
                                                }
                                                valueMapping.setCompany_id(oidValue);
                                                break;
                                            case 4:
                                                oidValue = getDivisionId(value);
                                                if (oidValue != 0){
                                                    html += "<td style=\"background-color:"+tdColor+"\">"+ value + "</td>";
                                                     jsonProccess += "Division:\""+value+"\",";
                                                } else {
                                                    html += "<td style=\"background-color:#fcd4dc; color:#d04761;\">"+ value + "</td>";
                                                    jsonProccess += "Division:\"\",";
                                                }
                                                valueMapping.setDivision_id(oidValue);
                                                break;
                                            case 5:
                                                oidValue = getDepartmentId(value);
                                                if (oidValue != 0){
                                                    html += "<td style=\"background-color:"+tdColor+"\">"+ value + "</td>";
                                                    jsonProccess += "Department:\""+value+"\",";
                                                } else {
                                                    html += "<td style=\"background-color:#fcd4dc; color:#d04761;\">"+ value + "</td>";
                                                    jsonProccess += "Department:\"\",";
                                                }
                                                valueMapping.setDepartment_id(oidValue);
                                                break;
                                            case 6:
                                                oidValue = getSectionId(value);
                                                if (oidValue != 0){
                                                    html += "<td style=\"background-color:"+tdColor+"\">"+ value + "</td>";
                                                    jsonProccess += "Section:\""+value+"\",";
                                                } else {
                                                    html += "<td style=\"background-color:#fcd4dc; color:#d04761;\">"+ value + "</td>";
                                                    jsonProccess += "Section:\"\",";
                                                }
                                                valueMapping.setSection_id(oidValue);
                                                break;
                                            case 7:
                                                oidValue = getLevelId(value);
                                                if (oidValue != 0){
                                                    html += "<td style=\"background-color:"+tdColor+"\">"+ value + "</td>";
                                                    jsonProccess += "Level:\""+value+"\",";
                                                } else {
                                                    html += "<td style=\"background-color:#fcd4dc; color:#d04761;\">"+ value + "</td>";
                                                    jsonProccess += "Level:\"\",";
                                                }
                                                valueMapping.setLevel_id(oidValue);
                                                break;
                                            case 8:
                                                oidValue = getMaritalId(value);
                                                if (oidValue != 0){
                                                    html += "<td style=\"background-color:"+tdColor+"\">"+ value + "</td>";
                                                    jsonProccess += "Marital:\""+value+"\",";
                                                } else {
                                                    html += "<td style=\"background-color:#fcd4dc; color:#d04761;\">"+ value + "</td>";
                                                    jsonProccess += "Marital:\"\",";
                                                }
                                                valueMapping.setMarital_id(oidValue);
                                                break;
                                            case 9:
                                                html += "<td style=\"background-color:"+tdColor+"\">"+ value + "</td>";
                                                jsonProccess += "Length of service:\""+value+"\",";
                                                valueMapping.setLength_of_service(0);
                                                break;
                                            case 10:
                                                oidValue = getEmployeeCategoryId(value);
                                                if (oidValue != 0){
                                                    html += "<td style=\"background-color:"+tdColor+"\">"+ value + "</td>";
                                                    jsonProccess += "Employee Category:\""+value+"\",";
                                                } else {
                                                    html += "<td style=\"background-color:#fcd4dc; color:#d04761;\">"+ value + "</td>";
                                                    jsonProccess += "Employee Category:\"\",";
                                                }
                                                valueMapping.setEmployee_category(oidValue);
                                                break;
                                            case 11:
                                                oidValue = getPositionId(value);
                                                if (oidValue != 0){
                                                    html += "<td style=\"background-color:"+tdColor+"\">"+ value + "</td>";
                                                    jsonProccess += "Position:\""+value+"\",";
                                                } else {
                                                    html += "<td style=\"background-color:#fcd4dc; color:#d04761;\">"+ value + "</td>";
                                                    jsonProccess += "Position:\"\",";
                                                }
                                                valueMapping.setPosition_id(oidValue);
                                                break;
                                            case 12:
                                                oidValue = getGradeId(value);
                                                if (oidValue != 0){
                                                    html += "<td style=\"background-color:"+tdColor+"\">"+ value + "</td>";
                                                    jsonProccess += "Grade:\""+value+"\",";
                                                } else {
                                                    html += "<td style=\"background-color:#fcd4dc; color:#d04761;\">"+ value + "</td>";
                                                    jsonProccess += "Grade:\"\",";
                                                }
                                                valueMapping.setGrade(oidValue);
                                                break;
                                            case 13:
                                                oidValue = getEmployeeId(value);
                                                if (oidValue != 0){
                                                    html += "<td style=\"background-color:"+tdColor+"\">"+ value + "</td>";
                                                     jsonProccess += "Payroll NUM:\""+value+"\",";
                                                } else {
                                                    html += "<td style=\"background-color:#fcd4dc; color:#d04761;\">"+ value + "</td>";
                                                    jsonProccess += "Payroll NUM:\"\",";
                                                }
                                                valueMapping.setEmployee_id(oidValue);
                                                break;
                                            case 14:
                                                int valSex = -1;
                                                if ((value.equals("PRIA"))||(value.equals("WANITA"))){
                                                    if (value.equals("PRIA")){
                                                        html += "<td style=\"background-color:"+tdColor+"\">"+ value + "</td>";
                                                        valSex = 0;
                                                    } 
                                                    if (value.equals("WANITA")){
                                                        html += "<td style=\"background-color:"+tdColor+"\">"+ value + "</td>";
                                                        valSex = 1;
                                                    }
                                                     jsonProccess += "Sex:\""+value+"\",";
                                                } else {
                                                    html += "<td style=\"background-color:"+tdColor+"\">-</td>";
                                                    jsonProccess += "Sex:\"\",";
                                                    valSex = -1;
                                                }
                                                valueMapping.setSex(valSex);
                                                break;
                                            case 15:
                                                double nilai = Double.valueOf(value);
                                                html += "<td style=\"background-color:"+tdColor+"\">"+ value + "</td>";
                                                valueMapping.setValue(nilai);
                                                jsonProccess += "Value:\""+value+"\",";
                                                break;
                                            case 16:
                                                html += "<td style=\"background-color:"+tdColor+"\">"+ value + "</td>";
                                                valueMapping.setRemark(Integer.valueOf(value));
                                                jsonProccess += "Remark:\""+value+"\",";
                                                break;
                                            default:
                                                html += "<td style=\"background-color:"+tdColor+"\">"+ value + "</td>";
                                        }
                                    } 
                                    
                                }
                                
                            } /*End For Cols*/
                            if (r > 0){
                                if( valueMapping.getEmployee_id() != 0){
                                    countInsert++;
                                    PstValue_Mapping.insertExc(valueMapping);
                                }
                                
                            }
                            
                            html += "</tr>";
                        } catch (Exception e) {
                            System.out.println("=> Can't get data ..sheet : " + i + ", row : " + r + ", => Exception e : " + e.toString());
                        }
                        if(r>0){
                            if(jsonProccess.substring(jsonProccess.length()-1).equals(",")){
                                jsonProccess = ""+jsonProccess.subSequence(0, jsonProccess.length()-1);
                            }

                            if(r+1 < rows){
                                jsonProccess += "},";
                            }else{
                                jsonProccess += "}]}"; 
                            }
                            
                        }
                    } //end of sheet
                    html += "</table>";
                    
                   
                    int bnyakData = rows - 1;
                    html += "<div class=\"info\">Success : "+countInsert+"<br>Failed : "+(bnyakData-countInsert)+"</div>";
                    html += "<div class=\"warning\">";
                        html += "Jika ada kolom merah pada table, maka data tersebut tidak ada kaitannya dengan master data di database.";
                    html += "</div>";
                } else {
                    html += "<div class=\"info\">Not match component name and sheet name</div>";
                }                
            } //end of all sheets
            
             //add by Eri Yudi 2020-10-21
                        LogSysHistory logHist = new LogSysHistory();
                        logHist.setLogDocumentType(className); 
                        logHist.setLogUserAction("Upload Excel");
                        logHist.setLogOpenUrl("/payroll/value_mapping/value_mapping_process.jsp");
                        logHist.setLogUpdateDate(new Date());
                        logHist.setLogApplication("Hairisma");
                        logHist.setLogDetail(jsonProccess);
                        logHist.setApproverNote("");
                        logHist.setLogModule("Value Mapping");
                        PstLogSysHistory.insertExc(logHist);
                    //end add by Eri Yudi
        } catch (Exception e) {
            System.out.println("---=== Error : ReadStream ===---\n" + e);
        }
        ////////////////////////////////////////////
        return html;
    }
    
    public long getCompanyId(String company){
        long companyId = 0;
        String where = PstCompany.fieldNames[PstCompany.FLD_COMPANY]+"='"+company+"'";
        Vector listCompany = PstCompany.list(0, 1, where, "");
        if (listCompany != null && listCompany.size()>0){
            Company comp = (Company)listCompany.get(0);
            companyId = comp.getOID();
        }
        return companyId;
    }
    
    public long getDivisionId(String division){
        long divisionId = 0;
        String where = PstDivision.fieldNames[PstDivision.FLD_DIVISION]+"='"+division+"'";
        Vector listDivision = PstDivision.list(0, 1, where, "");
        if (listDivision != null && listDivision.size()>0){
            Division divi = (Division)listDivision.get(0);
            divisionId = divi.getOID();
        }
        return divisionId;
    }
    
    public long getDepartmentId(String department){
        long departmentId = 0;
        String where = PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT]+"='"+department+"'";
        departmentId = getDepartmentIdByName(where);
        return departmentId;
    }
    
    public long getSectionId(String section){
        long sectionId = 0;
        String where = PstSection.fieldNames[PstSection.FLD_SECTION]+"='"+section+"'";
        Vector listSection = PstSection.list(0, 1, where, "");
        if (listSection != null && listSection.size()>0){
            Section sect = (Section)listSection.get(0);
            sectionId = sect.getOID();
        }
        return sectionId;
    }
    
    public long getLevelId(String level){
        long levelId = 0;
        String where = PstLevel.fieldNames[PstLevel.FLD_LEVEL]+"='"+level+"'";
        Vector listLevel = PstLevel.list(0, 1, where, "");
        if (listLevel != null && listLevel.size()>0){
            Level lev = (Level)listLevel.get(0);
            levelId = lev.getOID();
        }
        return levelId;
    }
    
    public long getMaritalId(String maritalCode){
        long maritalId = 0;
        String where = PstMarital.fieldNames[PstMarital.FLD_MARITAL_CODE]+"='"+maritalCode+"'";
        Vector listMarital = PstMarital.list(0, 1, where, "");
        if (listMarital != null && listMarital.size()>0){
            Marital marital = (Marital)listMarital.get(0);
            maritalId = marital.getOID();
        }
        return maritalId;
    }
    
    public long getEmployeeCategoryId(String empCat){
        long empCatId = 0;
        String where = PstEmpCategory.fieldNames[PstEmpCategory.FLD_EMP_CATEGORY]+"='"+empCat+"'";
        Vector listEmpCat = PstEmpCategory.list(0, 1, where, "");
        if (listEmpCat != null && listEmpCat.size()>0){
            EmpCategory empCategory = (EmpCategory)listEmpCat.get(0);
            empCatId = empCategory.getOID();
        }
        return empCatId;
    }
    
    public long getPositionId(String position){
        long positionId = 0;
        String where = PstPosition.fieldNames[PstPosition.FLD_POSITION]+"='"+position+"'";
        Vector listPosition = PstPosition.list(0, 1, where, "");
        if (listPosition != null && listPosition.size()>0){
            Position pos = (Position)listPosition.get(0);
            positionId = pos.getOID();
        }
        return positionId;
    }
    
    public long getGradeId(String gradeCode){
        long gradeId = 0;
        String where = PstGradeLevel.fieldNames[PstGradeLevel.FLD_GRADE_CODE]+"='"+gradeCode+"'";
        Vector listGrade = PstGradeLevel.list(0, 1, where, "");
        if (listGrade != null && listGrade.size()>0){
            GradeLevel grade = (GradeLevel)listGrade.get(0);
            gradeId = grade.getOID();
        }
        return gradeId;
    }
    
    public long getEmployeeId(String payrollnum){
        long employeeId = 0;
        String where = PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]+"='"+payrollnum+"'";
        Vector listEmployee = PstEmployee.list(0, 1, where, "");
        if (listEmployee != null && listEmployee.size()>0){
            Employee emp = (Employee)listEmployee.get(0);
            employeeId = emp.getOID();
        }
        return employeeId;
    }
    
    public static String getEmployeeNum(long employeeId){
        String empNum = "0";
        String where = PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID]+"="+employeeId;
        Vector listEmployee = PstEmployee.list(0, 1, where, "");
        if (listEmployee != null && listEmployee.size()>0){
            Employee emp = (Employee)listEmployee.get(0);
            empNum = emp.getEmployeeNum();
        }
        return empNum;
    }
    
    public static long getDepartmentIdByName(String where) {
        long departmentId = 0;
        DBResultSet dbrs = null;
        String untukTest = "";
        try {
            String sql  = "SELECT "+PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID];
            sql += " FROM "+PstDepartment.TBL_HR_DEPARTMENT;
            sql += " WHERE "+where;

            dbrs = DBHandler.execQueryResult(sql);
            untukTest = sql;
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                departmentId = rs.getLong(PstDepartment.fieldNames[PstDepartment.FLD_DEPARTMENT_ID]);
            }
            rs.close();
            return departmentId;

        } catch (Exception e) {
            System.out.println("Exception Deopartement: " + e.toString());
            System.out.println("exc" + untukTest);
        } finally {
            DBResultSet.close(dbrs);
        }
        return departmentId;
    }
}
