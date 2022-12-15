/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.report.employee;

import com.dimata.harisma.entity.employee.EmpCustomField;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.FamilyMember;
import com.dimata.harisma.entity.employee.PstCareerPath;
import com.dimata.harisma.entity.employee.PstEmpCustomField;
import com.dimata.harisma.entity.employee.PstEmpEducation;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.employee.PstFamilyMember;
import com.dimata.harisma.entity.masterdata.CustomFieldMaster;
import com.dimata.harisma.entity.masterdata.EmployeeCompetency;
import com.dimata.harisma.entity.masterdata.PstCompany;
import com.dimata.harisma.entity.masterdata.PstCompetency;
import com.dimata.harisma.entity.masterdata.PstCustomFieldMaster;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstDivision;
import com.dimata.harisma.entity.masterdata.PstEducation;
import com.dimata.harisma.entity.masterdata.PstEmpCategory;
import com.dimata.harisma.entity.masterdata.PstFamRelation;
import com.dimata.harisma.entity.masterdata.PstLanguage;
import com.dimata.harisma.entity.masterdata.PstLevel;
import com.dimata.harisma.entity.masterdata.PstMarital;
import com.dimata.harisma.entity.masterdata.PstPayrollGroup;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.harisma.entity.masterdata.PstRace;
import com.dimata.harisma.entity.masterdata.PstReligion;
import com.dimata.harisma.entity.masterdata.PstResignedReason;
import com.dimata.harisma.entity.masterdata.PstSection;
import com.dimata.harisma.entity.payroll.PstPayEmpLevel;
import com.dimata.harisma.entity.search.SrcSpecialEmployee;
import com.dimata.harisma.form.search.FrmSrcSpecialEmployeeQuery;
import com.dimata.harisma.session.employee.SearchSpecialQuery;
import com.dimata.harisma.session.employee.SessEmployeeXls;
import static com.dimata.harisma.session.employee.SessSpecialEmployee.DAYS_IN_A_MONTH;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.util.Formater;
import com.dimata.util.LogicParser;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author gndiw
 */
public class EmployeXlsNew extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/x-msexcel");
        response.setHeader("Content-Disposition","attachment; filename=" + "employee_data.xls" ); 
        HSSFWorkbook wb = new HSSFWorkbook();
        DataFormat format = wb.createDataFormat();
        HSSFSheet sheet = wb.createSheet("Employee Data");
        
        HSSFFont fontHeader = wb.createFont();
        fontHeader.setFontHeightInPoints((short)16);
        fontHeader.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        fontHeader.setFontName("Calibri");
        
        HSSFFont font14 = wb.createFont();
        font14.setFontHeightInPoints((short)14);
        font14.setFontName("Calibri");
        
        HSSFFont font20 = wb.createFont();
        font20.setFontHeightInPoints((short)20);
        font20.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font20.setFontName("Calibri");
        
        HSSFCellStyle styleHeader = wb.createCellStyle();
        styleHeader.setFont(fontHeader);
        styleHeader.setWrapText(true);
        styleHeader.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleHeader.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleHeader.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleHeader.setBorderTop(HSSFCellStyle.BORDER_THIN);
        styleHeader.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styleHeader.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        
        HSSFCellStyle styleTitle = wb.createCellStyle();
        styleTitle.setFont(font20);
        styleTitle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        
        
        HSSFCellStyle style1 = wb.createCellStyle();
        style1.setFont(font14);
        style1.setWrapText(true);
        style1.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        
        HSSFCellStyle styleDate = wb.createCellStyle();
        styleDate.setDataFormat(format.getFormat("d-mmm-yy"));
        styleDate.setFont(font14);
        styleDate.setWrapText(true);
        styleDate.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        styleDate.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleDate.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleDate.setBorderTop(HSSFCellStyle.BORDER_THIN);
        styleDate.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styleDate.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        
        HSSFCellStyle styleDouble = wb.createCellStyle();
        styleDouble.setDataFormat(format.getFormat("0.0"));
        styleDouble.setFont(font14);
        styleDouble.setWrapText(true);
        styleDouble.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        styleDouble.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleDouble.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleDouble.setBorderTop(HSSFCellStyle.BORDER_THIN);
        styleDouble.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styleDouble.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        
        HSSFCellStyle styleWrap = wb.createCellStyle();
        styleWrap.setFont(font14);
        styleWrap.setWrapText(true);
        styleWrap.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        styleWrap.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleWrap.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleWrap.setBorderTop(HSSFCellStyle.BORDER_THIN);
        styleWrap.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styleWrap.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleWrap.setWrapText(true);
        
        long companyId = FRMQueryString.requestLong(request, "companyId");
        long oidDivision = FRMQueryString.requestLong(request, "oidDivision");
        long oidDepartment = 0;
        long oidSection = 0;

        oidDepartment = FRMQueryString.requestLong(request,"oidDepartment");
        oidSection = FRMQueryString.requestLong(request,"oidSection");
        long departmentName = FRMQueryString.requestLong(request, "department");
        long sectionName = FRMQueryString.requestLong(request, "section");
        int losYearFrom = FRMQueryString.requestInt(request, "losYearFrom");
        int losMonthFrom = FRMQueryString.requestInt(request, "losMonthFrom");
        int losYearTo = FRMQueryString.requestInt(request, "losYearTo");
        int losMonthTo = FRMQueryString.requestInt(request, "losMonthTo");
        
        if (losYearTo == 0){
            losYearTo = 100;
        }
        
        if (losMonthTo == 0){
            losMonthTo = 100;
        }
        
        Vector<String> whereCollect = new Vector<String>();
        
        SearchSpecialQuery searchSpecialQuery = new SearchSpecialQuery();
        FrmSrcSpecialEmployeeQuery frmSrcSpecialEmployeeQuery = new FrmSrcSpecialEmployeeQuery(request, searchSpecialQuery);
        frmSrcSpecialEmployeeQuery.requestEntityObject(searchSpecialQuery);
        
        if (companyId != 0){
            String whereClauseEmp = " "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]+"="+companyId;
            whereCollect.add(whereClauseEmp);
        }
        if (oidDivision != 0){
            String whereClauseEmp = " "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+"="+oidDivision;
            whereCollect.add(whereClauseEmp);
        }
        if (oidDepartment != 0){
            String whereClauseEmp = " "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+"="+oidDepartment;
            whereCollect.add(whereClauseEmp);
        }
        if (oidSection != 0){
            String whereClauseEmp = " "+PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+"="+oidSection;
            whereCollect.add(whereClauseEmp);
        }
        
        String whereClauseEmp = PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]+" BETWEEN "
                            + "(SELECT DATE_FORMAT(DATE_ADD(NOW(), INTERVAL - ("+losYearTo+"."+losMonthTo+"*365) DAY),'%Y-%m-%d')) AND "
                            + "(SELECT DATE_FORMAT(DATE_ADD(NOW(), INTERVAL - ("+losYearFrom+"."+losMonthFrom+"*365) DAY),'%Y-%m-%d'))"
                            + " AND " + PstEmployee.TBL_HR_EMPLOYEE+"."+PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 0";;
        whereCollect.add(whereClauseEmp);
        
        if (whereCollect != null && whereCollect.size()>0){
            whereClauseEmp = "";
            for (int i=0; i<whereCollect.size(); i++){
                String where = (String)whereCollect.get(i);
                whereClauseEmp += where;
                if (i < (whereCollect.size()-1)){
                     whereClauseEmp += " AND ";
                }
            }
        }
        
        whereClauseEmp = getWhereEmployee(searchSpecialQuery);
        Vector listEmployee = PstEmployee.list(0, 0, whereClauseEmp, "");
        
        ArrayList<String> tableHeader = new ArrayList<String>();
        tableHeader.add("PAYROLL");tableHeader.add("FULL NAME");
        tableHeader.add("TEMPORARY ADDRESS");tableHeader.add("PERMANENT ADDRESS");
        tableHeader.add("TELEPHONE");tableHeader.add("HP");tableHeader.add("EMERGENCY PHONE");
        tableHeader.add("EMERGENCY PERSON NAME");tableHeader.add("GENDER");tableHeader.add("PLACE OF BIRTH");
        tableHeader.add("DATE OF BIRTH");tableHeader.add("AGE");tableHeader.add("SHIO");
        tableHeader.add("ELEMENT");tableHeader.add("RELIGION");tableHeader.add("MARITAL STATUS");
        tableHeader.add("TAX MARITAL STATUS");tableHeader.add("BLOOD TYPE");tableHeader.add("RACE");
        tableHeader.add("BARCODE NUM");tableHeader.add("ID CARD NO");tableHeader.add("ID CARD TYPE");
        tableHeader.add("ID CARD VALID");tableHeader.add("NO KK");tableHeader.add("EMAIL");
        tableHeader.add("PAYROLL GROUP");tableHeader.add("IQ");tableHeader.add("EQ");
        tableHeader.add("NO REKENING");tableHeader.add("NAMA REKENING");tableHeader.add("NAMA BANK");
        tableHeader.add("NPWP");tableHeader.add("FATHER NAME");tableHeader.add("MOTHER NAME");
        tableHeader.add("PARENTS ADDRESS");tableHeader.add("COMPANY");tableHeader.add("DIVISION");
        tableHeader.add("DEPARTMENT");tableHeader.add("SECTION");tableHeader.add("EMPLOYEE CATEGORY");
        tableHeader.add("LEVEL");tableHeader.add("POSITION");tableHeader.add("W. A. COMPANY");
        tableHeader.add("W. A. DIVISION");tableHeader.add("W. A. DEPARTMENT");tableHeader.add("W. A. SECTION");
        tableHeader.add("W. A. POSITION");/*tableHeader.add("W. A. PROVIDER");*/tableHeader.add("COMMENCING DATE");
        tableHeader.add("LENGTH OF SERVICES");tableHeader.add("BPJS TK NUMBER");tableHeader.add("BPJS TK DATE");
        tableHeader.add("BPJS KESEHATAN NO");tableHeader.add("BPJS KESEHATAN DATE");tableHeader.add("MEMBER BPJS TK");
        tableHeader.add("MEMBER BPJS KESEHATAN");
        
        Vector listCustom = PstCustomFieldMaster.list(0, 0, PstCustomFieldMaster.fieldNames[PstCustomFieldMaster.FLD_FIELD_NAME]+" != ''", "");
        for(int c=0; c<listCustom.size(); c++){
            CustomFieldMaster custom = (CustomFieldMaster)listCustom.get(c);
            tableHeader.add(custom.getFieldName());
        }
        
        
//        int maxFamily = SessEmployeeXls.getMaxFamilyMember(whereClauseEmp);
//        HashMap<Long, Vector> mapFamily = SessEmployeeXls.getListEmployee(whereClauseEmp);
//        for (int i = 0; i < maxFamily; i++){
//            tableHeader.add("FAMILY FULL NAME "+(i+1));tableHeader.add("FAMILY SEX "+(i+1));
//            tableHeader.add("FAMILY RELATION "+(i+1));tableHeader.add("FAMILY DATE OF BIRTH "+(i+1));
//            tableHeader.add("FAMILY JOB "+(i+1));tableHeader.add("FAMILY ADDRESS "+(i+1));
//            tableHeader.add("FAMILY EDUCATION "+(i+1));tableHeader.add("FAMILY RELIGION "+(i+1));
//        }
//        
//        int maxComp = SessEmployeeXls.getMaxCompetency(whereClauseEmp);
//        HashMap<Long, Vector> mapComp = SessEmployeeXls.getListCompetency(whereClauseEmp);
//        for (int i = 0; i < maxComp; i++){
//            tableHeader.add("COMPETENCIES "+(i+1));tableHeader.add("LEVEL VALUE "+(i+1));
//            tableHeader.add("DATE OF ACHIEVE "+(i+1));tableHeader.add("SPECIAL ACHIEVEMENT "+(i+1));
//        }
        
        HSSFRow row = sheet.createRow((short) 0);
        HSSFCell cell = row.createCell((short) 0);
        cell.setCellValue("HOTEL BOROBUDUR JAKARTA");
        
        cell.setCellStyle(styleTitle);
        row.setHeight((short) 500);
        
        row = sheet.createRow((short) 1);
        cell = row.createCell((short) 0);
        cell.setCellValue("");
        cell.setCellStyle(styleTitle);
        row.setHeight((short) 400);
        
        row = sheet.createRow((short) 2);
        cell = row.createCell((short) 0);
        cell.setCellValue("");
        cell.setCellStyle(styleTitle);
        row.setHeight((short) 400);
            
        row = sheet.createRow((short) 3);
        cell = row.createCell((short) 0);
        row.setHeight((short) 1500);
        
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,4));
        System.out.println(""+tableHeader.size());
        for (int k = 0; k < tableHeader.size(); k++) {
            cell = row.createCell((short) k);
            cell.setCellValue(tableHeader.get(k));
            cell.setCellStyle(styleHeader);
        }
        
        sheet.createFreezePane(0,4);
        sheet.setDefaultColumnWidth(30);
        
        int rowMergeStart = 4;
        for (int i=0; i < listEmployee.size();i++){
            Employee emp = (Employee) listEmployee.get(i);
            
            /*Payroll*/
            int col = 0;
            row = sheet.createRow((short) rowMergeStart);
            cell = row.createCell((short) col);
            cell.setCellValue(emp.getEmployeeNum());
            cell.setCellStyle(style1);
            col++;
            
            /* Full Name  */
            cell = row.createCell((short) col);
            cell.setCellValue(emp.getFullName());
            cell.setCellStyle(style1);
            col++;
            
            /* Temporary Address */
            cell = row.createCell((short) col);
            cell.setCellValue(emp.getAddress());
            cell.setCellStyle(style1);
            col++;
            
            /* Permanent Address */
            cell = row.createCell((short) col);
            cell.setCellValue(emp.getAddressPermanent());
            cell.setCellStyle(style1);
            col++;
            
            /* Telephone */
            cell = row.createCell((short) col);
            cell.setCellValue(emp.getPhone());
            cell.setCellStyle(style1);
            col++;
            
            /* Hp */
            cell = row.createCell((short) col);
            cell.setCellValue(emp.getHandphone());
            cell.setCellStyle(style1);
            col++;
            
            /* Emergency Phone */
            cell = row.createCell((short) col);
            cell.setCellValue(emp.getPhoneEmg());
            cell.setCellStyle(style1);
            col++;
            
            /* Emergency Person Name */
            cell = row.createCell((short) col);
            cell.setCellValue(emp.getNameEmg());
            cell.setCellStyle(style1);
            col++;
            
            /*Gender*/
            cell = row.createCell((short) col);
            cell.setCellValue((emp.getSex()==0? "Laki-Laki" : "Perempuan"));
            cell.setCellStyle(style1);
            col++;
            
            /*Place of Birth*/
            cell = row.createCell((short) col);
            cell.setCellValue(emp.getBirthPlace());
            cell.setCellStyle(style1);
            col++;
            
            /*Date of Birth*/
            cell = row.createCell((short) col);
            cell.setCellValue(emp.getBirthDate());
            cell.setCellStyle(style1);
            col++;
            
            /*Age*/
            cell = row.createCell((short) col);
            cell.setCellValue(0);
            cell.setCellStyle(style1);
            col++;
            
            /*Shio*/
            cell = row.createCell((short) col);
            cell.setCellValue(emp.getShio());
            cell.setCellStyle(style1);
            col++;
            
            /*Element*/
            cell = row.createCell((short) col);
            cell.setCellValue(emp.getElemen());
            cell.setCellStyle(style1);
            col++;
            
            /*Religion*/
            cell = row.createCell((short) col);
            cell.setCellValue(getReligion(emp.getReligionId()));
            cell.setCellStyle(style1);
            col++;
            
            /*Marital Status*/
            cell = row.createCell((short) col);
            cell.setCellValue(getStatusMarital(emp.getMaritalId()));
            cell.setCellStyle(style1);
            col++;
            
            /*Tax Marital Status*/
            cell = row.createCell((short) col);
            cell.setCellValue(getStatusMarital(emp.getTaxMaritalId()));
            cell.setCellStyle(style1);
            col++;
            
            /*Blood Type*/
            cell = row.createCell((short) col);
            cell.setCellValue(emp.getBloodType());
            cell.setCellStyle(style1);
            col++;
            
            /*Race*/
            cell = row.createCell((short) col);
            cell.setCellValue(getRace(emp.getRace()));
            cell.setCellStyle(style1);
            col++;
            
            /*Barcode Num*/
            cell = row.createCell((short) col);
            cell.setCellValue(emp.getBarcodeNumber());
            cell.setCellStyle(style1);
            col++;
            
            /*ID Card No*/
            cell = row.createCell((short) col);
            cell.setCellValue(emp.getIndentCardNr());
            cell.setCellStyle(style1);
            col++;
                        
            /*ID Card Type*/
            cell = row.createCell((short) col);
            cell.setCellValue(emp.getIdcardtype());
            cell.setCellStyle(style1);
            col++;
            
            /*ID Card Valid*/
            cell = row.createCell((short) col);
            cell.setCellValue(""+emp.getIndentCardValidTo());
            cell.setCellStyle(style1);
            col++;
            
            /*No KK*/
            cell = row.createCell((short) col);
            cell.setCellValue(emp.getNoKK());
            cell.setCellStyle(style1);
            col++;
            
            /*Email*/
            cell = row.createCell((short) col);
            cell.setCellValue(emp.getEmailAddress());
            cell.setCellStyle(style1);
            col++;
            
            /*Payroll Group*/
            cell = row.createCell((short) col);
            cell.setCellValue(getPayrollGroup(emp.getPayrollGroup()));
            cell.setCellStyle(style1);
            col++;
            
            /*IQ*/
            cell = row.createCell((short) col);
            cell.setCellValue(emp.getIq());
            cell.setCellStyle(style1);
            col++;
            
            /*EQ*/
            cell = row.createCell((short) col);
            cell.setCellValue(emp.getEq());
            cell.setCellStyle(style1);
            col++;
            
            /*No Rekening*/
            cell = row.createCell((short) col);
            cell.setCellValue(emp.getNoRekening());
            cell.setCellStyle(style1);
            col++;
            
            /*Nama Rekening*/
            cell = row.createCell((short) col);
            cell.setCellValue(emp.getNamaRekening());
            cell.setCellStyle(style1);
            col++;
            
            /*Nama Bank*/
            cell = row.createCell((short) col);
            cell.setCellValue(emp.getNamaBank());
            cell.setCellStyle(style1);
            col++;
            
            /*NPWP*/
            cell = row.createCell((short) col);
            cell.setCellValue(emp.getNpwp());
            cell.setCellStyle(style1);
            col++;
            
            /*Father Name*/
            cell = row.createCell((short) col);
            cell.setCellValue(emp.getFather());
            cell.setCellStyle(style1);
            col++;
            
            /*Mother Name*/
            cell = row.createCell((short) col);
            cell.setCellValue(emp.getMother());
            cell.setCellStyle(style1);
            col++;
            
            /*Parents Address*/
            cell = row.createCell((short) col);
            cell.setCellValue(emp.getParentsAddress());
            cell.setCellStyle(style1);
            col++;
            
            /*Company*/
            cell = row.createCell((short) col);
            cell.setCellValue(getCompany(companyId));
            cell.setCellStyle(style1);
            col++;
            
            /*Division*/
            cell = row.createCell((short) col);
            cell.setCellValue(getDivision(emp.getDivisionId()));
            cell.setCellStyle(style1);
            col++;
            
            /*Department*/
            cell = row.createCell((short) col);
            cell.setCellValue(getDepartment(emp.getDepartmentId()));
            cell.setCellStyle(style1);
            col++;
            
            /*Section*/
            cell = row.createCell((short) col);
            cell.setCellValue(getSection(emp.getSectionId()));
            cell.setCellStyle(style1);
            col++;
            
            /*Employee Category*/
            cell = row.createCell((short) col);
            cell.setCellValue(getEmpCategory(emp.getEmpCategoryId()));
            cell.setCellStyle(style1);
            col++;
            
            /*Level*/
            cell = row.createCell((short) col);
            cell.setCellValue(getLevel(emp.getLevelId()));
            cell.setCellStyle(style1);
            col++;
            
            /*Position*/
            cell = row.createCell((short) col);
            cell.setCellValue(getPosition(emp.getPositionId()));
            cell.setCellStyle(style1);
            col++;
            
            /*W. A. Company*/
            cell = row.createCell((short) col);
            cell.setCellValue(getCompany(emp.getWorkassigncompanyId()));
            cell.setCellStyle(style1);
            col++;
            
            /*W. A. Division*/
            cell = row.createCell((short) col);
            cell.setCellValue(getDivision(emp.getWorkassigndivisionId()));
            cell.setCellStyle(style1);
            col++;
            
            /*W. A. Department*/
            cell = row.createCell((short) col);
            cell.setCellValue(getDepartment(emp.getWorkassigndepartmentId()));
            cell.setCellStyle(style1);
            col++;
            
            /*W. A. Section*/
            cell = row.createCell((short) col);
            cell.setCellValue(getSection(emp.getWorkassignsectionId()));
            cell.setCellStyle(style1);
            col++;
            
            /*W. A. Position*/
            cell = row.createCell((short) col);
            cell.setCellValue(getPosition(emp.getWorkassignpositionId()));
            cell.setCellStyle(style1);
            col++;
            
            /*Commencing Date*/
            cell = row.createCell((short) col);
            cell.setCellValue(emp.getCommencingDate());
            cell.setCellStyle(style1);
            col++;
            
            /*Length of Services*/
            cell = row.createCell((short) col);
            cell.setCellValue(0);
            cell.setCellStyle(style1);
            col++;
            
            /*BPJS TK Number*/
            cell = row.createCell((short) col);
            cell.setCellValue(emp.getAstekNum());
            cell.setCellStyle(style1);
            col++;
            
            /*BPJS TK Date*/
            cell = row.createCell((short) col);
            cell.setCellValue(""+emp.getAstekDate());
            cell.setCellStyle(style1);
            col++;
            
            /*BPJS Kesehatan No*/
            cell = row.createCell((short) col);
            cell.setCellValue(emp.getBpjs_no());
            cell.setCellStyle(style1);
            col++;
            
            /*BPJS Kesehatan Date*/
            cell = row.createCell((short) col);
            cell.setCellValue(""+emp.getBpjs_date());
            cell.setCellStyle(style1);
            col++;
            
            /*Member BPJS TK*/
            cell = row.createCell((short) col);
            cell.setCellValue((emp.getMemOfBpjsKetenagaKerjaan()==0? "Tidak" : "Ya"));
            cell.setCellStyle(style1);
            col++;
            
            /*Member BPJS Kesehatan*/
            cell = row.createCell((short) col);
            cell.setCellValue((emp.getMemOfBpjsKesahatan()==0? "Tidak" : "Ya"));
            cell.setCellStyle(style1);
            col++;
            
            /*Custom Field*/
            for(int c=0; c<listCustom.size(); c++){
                CustomFieldMaster custom = (CustomFieldMaster)listCustom.get(c);
                String whereEmpCustom = "CUSTOM_FIELD_ID="+custom.getOID()+" AND EMPLOYEE_ID="+emp.getOID();
                Vector listEmpCustom = PstEmpCustomField.list(0, 0, whereEmpCustom, "");
                String valueInput = "-";
                if (listEmpCustom != null && listEmpCustom.size()>0){
                    for(int e=0; e<listEmpCustom.size(); e++){
                        EmpCustomField empCust = (EmpCustomField)listEmpCustom.get(e);
                        switch(custom.getFieldType()){
                            case 0: valueInput = empCust.getDataText(); break;
                            case 1: valueInput = ""+empCust.getDataNumber(); break;
                            case 2: valueInput = ""+empCust.getDataNumber(); break;
                            case 3: valueInput = ""+empCust.getDataDate(); break;
                            case 4: valueInput = ""+empCust.getDataDate(); break;
                        }
                    }
                }
                cell = row.createCell((short) col);
                cell.setCellValue(valueInput);
                cell.setCellStyle(style1);
                col++;
            }
//            Vector listFam = (Vector) mapFamily.get(emp.getOID());
//            col = addFamily(cell,style1,listFam,maxFamily,row,col);
//            Vector listComp = (Vector) mapComp.get(emp.getOID());
//            col = addCompetencty(cell, style1, listComp, maxComp, row, col);
            rowMergeStart++;
            row.setHeight((short) 1500);
        }
        
        
        
        ServletOutputStream sos = response.getOutputStream();
        wb.write(sos);
        sos.close();
    }
    
    private static int addFamily(HSSFCell cell, HSSFCellStyle style1, 
            Vector listFam, int maxFamily, HSSFRow row, int col){
            int cnt = 0;
            
            if (listFam != null){
                for (int eFam = 0; eFam < listFam.size(); eFam++ ){
                    cnt++;
                    FamilyMember familyMember = (FamilyMember) listFam.get(eFam);

                    /*Full Name*/
                    cell = row.createCell((short) col);
                    cell.setCellValue(familyMember.getFullName());
                    cell.setCellStyle(style1);
                    col++;

                    /*Sex*/
                    cell = row.createCell((short) col);
                    cell.setCellValue((familyMember.getSex()==0? "Laki-Laki" : "Perempuan"));
                    cell.setCellStyle(style1);
                    col++;

                    /*Relationship*/
                    cell = row.createCell((short) col);
                    cell.setCellValue(getFamilyRelation(familyMember.getRelationship()));
                    cell.setCellStyle(style1);
                    col++;

                    /*Date of Birth*/
                    cell = row.createCell((short) col);
                    cell.setCellValue(""+familyMember.getBirthDate());
                    cell.setCellStyle(style1);
                    col++;

                    /*Job*/
                    cell = row.createCell((short) col);
                    cell.setCellValue(""+familyMember.getJob());
                    cell.setCellStyle(style1);
                    col++;

                    /*Address*/
                    cell = row.createCell((short) col);
                    cell.setCellValue(""+familyMember.getAddress());
                    cell.setCellStyle(style1);
                    col++;

                    /*Education*/
                    cell = row.createCell((short) col);
                    cell.setCellValue(getEducation(familyMember.getEducationId()));
                    cell.setCellStyle(style1);
                    col++;

                    /*Religion*/
                    cell = row.createCell((short) col);
                    cell.setCellValue(getReligion(familyMember.getReligionId()));
                    cell.setCellStyle(style1);
                    col++;

                }
            }
            
            for (int c = cnt; c < maxFamily; c++){
                /*Full Name*/
                cell = row.createCell((short) col);
                cell.setCellValue("");
                cell.setCellStyle(style1);
                col++;

                /*Sex*/
                cell = row.createCell((short) col);
                cell.setCellValue("");
                cell.setCellStyle(style1);
                col++;

                /*Relationship*/
                cell = row.createCell((short) col);
                cell.setCellValue("");
                cell.setCellStyle(style1);
                col++;

                /*Date of Birth*/
                cell = row.createCell((short) col);
                cell.setCellValue("");
                cell.setCellStyle(style1);
                col++;

                /*Job*/
                cell = row.createCell((short) col);
                cell.setCellValue("");
                cell.setCellStyle(style1);
                col++;

                /*Address*/
                cell = row.createCell((short) col);
                cell.setCellValue("");
                cell.setCellStyle(style1);
                col++;

                /*Education*/
                cell = row.createCell((short) col);
                cell.setCellValue("");
                cell.setCellStyle(style1);
                col++;

                /*Religion*/
                cell = row.createCell((short) col);
                cell.setCellValue("");
                cell.setCellStyle(style1);
                col++;
            }
        return col;
    }
    
    private static int addCompetencty(HSSFCell cell, HSSFCellStyle style1, 
            Vector listComp, int maxComp, HSSFRow row, int col){
            int cnt = 0;
            
            if (listComp != null){
                for (int eComp = 0; eComp < listComp.size(); eComp++ ){
                    cnt++;
                    EmployeeCompetency empCompetency = (EmployeeCompetency) listComp.get(eComp);

                    /*Competencies*/
                    cell = row.createCell((short) col);
                    cell.setCellValue(getCompetency(empCompetency.getCompetencyId()));
                    cell.setCellStyle(style1);
                    col++;

                    /*Level Value*/
                    cell = row.createCell((short) col);
                    cell.setCellValue(empCompetency.getLevelValue());
                    cell.setCellStyle(style1);
                    col++;

                    /*Date of Achieve*/
                    cell = row.createCell((short) col);
                    cell.setCellValue(""+empCompetency.getDateOfAchvmt());
                    cell.setCellStyle(style1);
                    col++;

                    /*Special Achievement*/
                    cell = row.createCell((short) col);
                    cell.setCellValue(""+empCompetency.getSpecialAchievement());
                    cell.setCellStyle(style1);
                    col++;

                }
            }
            
            for (int c = cnt; c < maxComp; c++){
                /*Competencies*/
                cell = row.createCell((short) col);
                cell.setCellValue("");
                cell.setCellStyle(style1);
                col++;

                /*Level Value*/
                cell = row.createCell((short) col);
                cell.setCellValue("");
                cell.setCellStyle(style1);
                col++;

                /*Date of Achieve*/
                cell = row.createCell((short) col);
                cell.setCellValue("");
                cell.setCellStyle(style1);
                col++;

                /*Special Achievement*/
                cell = row.createCell((short) col);
                cell.setCellValue("");
                cell.setCellStyle(style1);
                col++;
            }
        return col;
    }
    
    private static Vector logicParser(String text) {
        Vector vector = LogicParser.textSentence(text);
        for (int i = 0; i < vector.size(); i++) {
            String code = (String) vector.get(i);
            if (((vector.get(vector.size() - 1)).equals(LogicParser.SIGN))
                    && ((vector.get(vector.size() - 1)).equals(LogicParser.ENGLISH))) {
                vector.remove(vector.size() - 1);
            }
        }

        return vector;
    }
    
    private String getWhereEmployee(SearchSpecialQuery searchSpecialQuery){
        String[] educationId = searchSpecialQuery.getArrEducationId(0);
        String[] languageId = searchSpecialQuery.getArrLanguageId(0);
        String whereClause = "(1=1)";
        if (searchSpecialQuery.getBirthMonth() > 0) {
            whereClause = whereClause + " AND MONTH("+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]
                    + ") = '" + (searchSpecialQuery.getBirthMonth()) + "'  ";
        }
        if ((searchSpecialQuery.getFullNameEmp() != null) && (searchSpecialQuery.getFullNameEmp().length() > 0)) {
            Vector vectName = logicParser(searchSpecialQuery.getFullNameEmp());
            if (vectName != null && vectName.size() > 0) {
                whereClause = whereClause + " AND (";
                for (int i = 0; i < vectName.size(); i++) {
                    String str = (String) vectName.get(i);
                    if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                        whereClause = whereClause + " "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]
                                + " LIKE '%" + str.trim() + "%' ";
                    } else {
                        whereClause = whereClause + str.trim();
                    }
                }
                whereClause = whereClause + ") ";
            }
        }

        if ((searchSpecialQuery.getAddrsEmployee() != null) && (searchSpecialQuery.getAddrsEmployee().length() > 0)) {
            Vector vectAddr = logicParser(searchSpecialQuery.getAddrsEmployee());
            if (vectAddr != null && vectAddr.size() > 0) {
                whereClause = whereClause + " AND (";
                for (int i = 0; i < vectAddr.size(); i++) {
                    String str = (String) vectAddr.get(i);
                    if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                        whereClause = whereClause + " "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]
                                + " LIKE '%" + str.trim() + "%' ";
                    } else {
                        whereClause = whereClause + str.trim();
                    }
                }
                whereClause = whereClause + ") ";
            }
        }

        if ((searchSpecialQuery.getEmpnumber() != null) && (searchSpecialQuery.getEmpnumber().length() > 0)) {
            Vector vectNum = logicParser(searchSpecialQuery.getEmpnumber());
            if (vectNum != null && vectNum.size() > 0) {
                whereClause = whereClause + " AND (";
                for (int i = 0; i < vectNum.size(); i++) {
                    String str = (String) vectNum.get(i);
                    if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                        whereClause = whereClause + " "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_NUM]
                                + " LIKE '%" + str.trim() + "%' ";
                    } else {
                        whereClause = whereClause + str.trim();
                    }
                }
                whereClause = whereClause + ") ";
            }
        }

        if ((searchSpecialQuery.getAddrsEmployee() != null) && (searchSpecialQuery.getAddrsEmployee().length() > 0)) {
            Vector vectName = logicParser(searchSpecialQuery.getAddrsEmployee());
            if (vectName != null && vectName.size() > 0) {
                whereClause = whereClause + " AND (";
                for (int i = 0; i < vectName.size(); i++) {
                    String str = (String) vectName.get(i);
                    if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                        whereClause = whereClause + " "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS]
                                + " LIKE '%" + str.trim() + "%' ";
                    } else {
                        whereClause = whereClause + str.trim();
                    }
                }
                whereClause = whereClause + ") ";
            }
        }

        if ((searchSpecialQuery.getAddressPermanent() != null) && (searchSpecialQuery.getAddressPermanent().length() > 0)) {
            Vector vectName = logicParser(searchSpecialQuery.getAddressPermanent());
            if (vectName != null && vectName.size() > 0) {
                whereClause = whereClause + " AND (";
                for (int i = 0; i < vectName.size(); i++) {
                    String str = (String) vectName.get(i);
                    if (!LogicParser.isInSign(str) && !LogicParser.isInLogEnglish(str)) {
                        whereClause = whereClause + " "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_ADDRESS_PERMANENT]
                                + " LIKE '%" + str.trim() + "%' ";
                    } else {
                        whereClause = whereClause + str.trim();
                    }
                }
                whereClause = whereClause + ") ";
            }
        }

            //update by satrya 2013-08-15
        //
        String[] CountyId = searchSpecialQuery.getArrCountryId(0);
        //String[] maritalId = (String[]) vparam.get(7);
        if (CountyId != null && !(CountyId[0].equals("0"))) {
            whereClause += " AND (";
            for (int i = 0; i < CountyId.length; i++) {
                whereClause = whereClause + " "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_ADDR_COUNTRY_ID]
                        + " = " + CountyId[i] + " OR ";
                if (i == (CountyId.length - 1)) {
                    whereClause = whereClause.substring(0, whereClause.length() - 4);
                }
            }
            whereClause += " )";
        }

        String[] provinsiId = searchSpecialQuery.getArrProvinsiId(0);
        //String[] maritalId = (String[]) vparam.get(7);
        if (provinsiId != null && !(provinsiId[0].equals("0"))) {
            whereClause += " AND (";
            for (int i = 0; i < provinsiId.length; i++) {
                whereClause = whereClause + " "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_ADDR_PROVINCE_ID]
                        + " = " + provinsiId[i] + " OR ";
                if (i == (provinsiId.length - 1)) {
                    whereClause = whereClause.substring(0, whereClause.length() - 4);
                }
            }
            whereClause += " )";
        }
        String[] kabupatenId = searchSpecialQuery.getArrKabupatenId(0);
        //String[] maritalId = (String[]) vparam.get(7);
        if (kabupatenId != null && !(kabupatenId[0].equals("0"))) {
            whereClause += " AND (";
            for (int i = 0; i < kabupatenId.length; i++) {
                whereClause = whereClause + " "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_ADDR_REGENCY_ID]
                        + " = " + kabupatenId[i] + " OR ";
                if (i == (kabupatenId.length - 1)) {
                    whereClause = whereClause.substring(0, whereClause.length() - 4);
                }
            }
            whereClause += " )";
        }

        String[] kecamatanId = searchSpecialQuery.getArrKecamatanId(0);
        //String[] maritalId = (String[]) vparam.get(7);
        if (kecamatanId != null && !(kecamatanId[0].equals("0"))) {
            whereClause += " AND (";
            for (int i = 0; i < kecamatanId.length; i++) {
                whereClause = whereClause + " "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_ADDR_SUBREGENCY_ID]
                        + " = " + kecamatanId[i] + " OR ";
                if (i == (kecamatanId.length - 1)) {
                    whereClause = whereClause.substring(0, whereClause.length() - 4);
                }
            }
            whereClause += " )";
        }

        String[] districtId = searchSpecialQuery.getArrDistrictId(0);
        //String[] maritalId = (String[]) vparam.get(7);
        if (districtId != null && !(districtId[0].equals("0"))) {
            whereClause += " AND (";
            for (int i = 0; i < districtId.length; i++) {
                whereClause = whereClause + " "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_ADDR_DISTRICT_ID]
                        + " = " + districtId[i] + " OR ";
                if (i == (districtId.length - 1)) {
                    whereClause = whereClause.substring(0, whereClause.length() - 4);
                }
            }
            whereClause += " )";
        }

        String[] districtIdPermanent = searchSpecialQuery.getArrDistrictIdPermanent(0);
        //String[] maritalId = (String[]) vparam.get(7);
        if (districtIdPermanent != null && !(districtIdPermanent[0].equals("0"))) {
            whereClause += " AND (";
            for (int i = 0; i < districtIdPermanent.length; i++) {
                whereClause = whereClause + " "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_ADDR_PMNT_DISTRICT_ID]
                        + " = " + districtIdPermanent[i] + " OR ";
                if (i == (districtIdPermanent.length - 1)) {
                    whereClause = whereClause.substring(0, whereClause.length() - 4);
                }
            }
            whereClause += " )";
        }

        //
        String[] CountyIdPermanent = searchSpecialQuery.getArrCountryIdPermanent(0);
        //String[] maritalId = (String[]) vparam.get(7);
        if (CountyIdPermanent != null && !(CountyIdPermanent[0].equals("0"))) {
            whereClause += " AND (";
            for (int i = 0; i < CountyIdPermanent.length; i++) {
                whereClause = whereClause + " "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_ADDR_PMNT_COUNTRY_ID]
                        + " = " + CountyIdPermanent[i] + " OR ";
                if (i == (CountyIdPermanent.length - 1)) {
                    whereClause = whereClause.substring(0, whereClause.length() - 4);
                }
            }
            whereClause += " )";
        }

        String[] provinsiIdPermanent = searchSpecialQuery.getArrProvinsiIdPermanent(0);
        //String[] maritalId = (String[]) vparam.get(7);
        if (provinsiIdPermanent != null && !(provinsiIdPermanent[0].equals("0"))) {
            whereClause += " AND (";
            for (int i = 0; i < provinsiIdPermanent.length; i++) {
                whereClause = whereClause + " "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_ADDR_PMNT_PROVINCE_ID]
                        + " = " + provinsiIdPermanent[i] + " OR ";
                if (i == (provinsiIdPermanent.length - 1)) {
                    whereClause = whereClause.substring(0, whereClause.length() - 4);
                }
            }
            whereClause += " )";
        }
        String[] kabupatenIdPermanent = searchSpecialQuery.getArrKabupatenIdPermanent(0);
        //String[] maritalId = (String[]) vparam.get(7);
        if (kabupatenIdPermanent != null && !(kabupatenIdPermanent[0].equals("0"))) {
            whereClause += " AND (";
            for (int i = 0; i < kabupatenIdPermanent.length; i++) {
                whereClause = whereClause + " "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_ADDR_PMNT_REGENCY_ID]
                        + " = " + kabupatenIdPermanent[i] + " OR ";
                if (i == (kabupatenIdPermanent.length - 1)) {
                    whereClause = whereClause.substring(0, whereClause.length() - 4);
                }
            }
            whereClause += " )";
        }

        String[] kecamatanIdPermanent = searchSpecialQuery.getArrKecamatanIdPermanent(0);
        //String[] maritalId = (String[]) vparam.get(7);
        if (kecamatanIdPermanent != null && !(kecamatanIdPermanent[0].equals("0"))) {
            whereClause += " AND (";
            for (int i = 0; i < kecamatanIdPermanent.length; i++) {
                whereClause = whereClause + " "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_ADDR_PMNT_SUBREGENCY_ID]
                        + " = " + kecamatanIdPermanent[i] + " OR ";
                if (i == (kecamatanIdPermanent.length - 1)) {
                    whereClause = whereClause.substring(0, whereClause.length() - 4);
                }
            }
            whereClause += " )";
        }
            //

        if (searchSpecialQuery.getArrDivision(0) != null) {
            String[] divisionId = searchSpecialQuery.getArrDivision(0);
            if (!(divisionId != null && (divisionId[0].equals("0")))) {
                whereClause += " AND (";
                for (int i = 0; i < divisionId.length; i++) {
                    whereClause = whereClause + " "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]
                            + " = " + divisionId[i] + " OR ";
                    if (i == (divisionId.length - 1)) {
                        whereClause = whereClause.substring(0, whereClause.length() - 4);
                    }
                }
                whereClause += " )";
            }

        }

        if ((searchSpecialQuery.getBirthYearFrom() != 0) || (searchSpecialQuery.getBirthYearTo() != 0)) {
            if (searchSpecialQuery.getBirthYearFrom() != 0 && searchSpecialQuery.getBirthYearTo() == 0) {
                whereClause = whereClause + " AND YEAR("+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]
                        + ") = '" + (searchSpecialQuery.getBirthMonth()) + "'  ";

            } else if (searchSpecialQuery.getBirthYearTo() != 0 && searchSpecialQuery.getBirthYearFrom() == 0) {
                whereClause = whereClause + " AND YEAR("+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]
                        + ") = '" + (searchSpecialQuery.getBirthYearTo()) + "'  ";
            } else {
                whereClause = whereClause + " AND YEAR("+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE] + ") BETWEEN '"
                        + searchSpecialQuery.getBirthYearFrom() + "' AND '"
                        + searchSpecialQuery.getBirthYearTo() + "'  ";

            }
        }

        //update by satrya 2013-11-13
        if ((searchSpecialQuery.getDtCarrierWorkStart() != null && searchSpecialQuery.getDtCarrierWorkEnd() != null)) {

            whereClause = whereClause + " AND \"" + Formater.formatDate(searchSpecialQuery.getDtCarrierWorkEnd(), "yyyy-MM-dd") + "\" > CRR."
                    + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_FROM] + " AND CRR."
                    + PstCareerPath.fieldNames[PstCareerPath.FLD_WORK_TO] + " > \"" + Formater.formatDate(searchSpecialQuery.getDtCarrierWorkStart(), "yyyy-MM-dd") + "\"";

        }
        if (searchSpecialQuery.getCarrierCategoryEmp() != 0) {
            whereClause = whereClause + " AND CRR." + PstCareerPath.fieldNames[PstCareerPath.FLD_EMP_CATEGORY_ID] + "=" + searchSpecialQuery.getCarrierCategoryEmp();
        }

        if ((searchSpecialQuery.getDtBirthFrom() != null) || (searchSpecialQuery.getDtBirthTo() != null)) {
            if (searchSpecialQuery.getDtBirthFrom() != null && searchSpecialQuery.getDtBirthTo() == null) {
                whereClause = whereClause + " AND "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE] + " BETWEEN '"
                        + Formater.formatDate(searchSpecialQuery.getDtBirthFrom(), "yyyy-MM-dd") + "' AND '"
                        + Formater.formatDate(searchSpecialQuery.getDtBirthFrom(), "yyyy-MM-dd") + "'  ";
            } else if (searchSpecialQuery.getDtBirthTo() != null && searchSpecialQuery.getDtBirthFrom() == null) {
                whereClause = whereClause + " AND "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE] + " BETWEEN '"
                        + Formater.formatDate(searchSpecialQuery.getDtBirthTo(), "yyyy-MM-dd") + "' AND '"
                        + Formater.formatDate(searchSpecialQuery.getDtBirthTo(), "yyyy-MM-dd") + "'  ";
            } else {
                whereClause = whereClause + " AND "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE] + " BETWEEN '"
                        + Formater.formatDate(searchSpecialQuery.getDtBirthFrom(), "yyyy-MM-dd") + "' AND '"
                        + Formater.formatDate(searchSpecialQuery.getDtBirthTo(), "yyyy-MM-dd") + "'  ";
            }
        }

//            if (searchSpecialQuery.getMonthBirth()!=0) {
//                 whereClause = whereClause + " MONTH("+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]
//                            + ") = '" + (searchSpecialQuery.getMonthBirth()) + "' AND ";
//               
//            }
//            if (!searchSpecialQuery.isBirthdayChecked()) {
//                System.out.println("Bithday checked : " + searchSpecialQuery.isBirthdayChecked());
//               // System.out.println("Bithday : " + searchSpecialQuery.getBirthday());
//                System.out.println("Bithmon : " + searchSpecialQuery.getBirthMonth());
//            }
        if (searchSpecialQuery.getSalaryLevel() != null && searchSpecialQuery.getSalaryLevel().length() > 0) {
            whereClause = whereClause + " AND LEV." + PstPayEmpLevel.fieldNames[PstPayEmpLevel.FLD_LEVEL_CODE]
                    + " = '" + searchSpecialQuery.getSalaryLevel() + "'  ";
        }

        if (searchSpecialQuery.getArrEmpCategory(0) != null) {
            String[] empCategory = searchSpecialQuery.getArrEmpCategory(0);
            if (!(empCategory != null && (empCategory[0].equals("0")))) {
                whereClause += " AND (";
                for (int i = 0; i < empCategory.length; i++) {
                    whereClause = whereClause + " "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_EMP_CATEGORY_ID]
                            + " = " + empCategory[i] + " OR ";
                    if (i == (empCategory.length - 1)) {
                        whereClause = whereClause.substring(0, whereClause.length() - 4);
                    }
                }
                whereClause += " )";
            }

        }
        //update by satrya 2013-10-17
        boolean noresign = true;
        if (/*searchSpecialQuery.getDtPeriodStart()!=null &&*/searchSpecialQuery.getDtPeriodEnd() != null) {
            noresign = false;
            whereClause = whereClause + " AND "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    /*+ " BETWEEN \""+Formater.formatDate(searchSpecialQuery.getDtPeriodStart(), "yyyy-MM-dd") + "\""
                     +" AND \""+Formater.formatDate(searchSpecialQuery.getDtPeriodEnd(), "yyyy-MM-dd")+"\"  "*/
                    + " <= \" " + Formater.formatDate(searchSpecialQuery.getDtPeriodEnd(), "yyyy-MM-dd") + "\" "
                    + " AND (("+PstEmployee.TBL_HR_EMPLOYEE+"."
                    + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=" + PstEmployee.NO_RESIGN + ") OR  "
                    + " ("+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + "=" + PstEmployee.NO_RESIGN
                    + " AND "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE] + ">=\"" + Formater.formatDate(searchSpecialQuery.getDtPeriodEnd(), "yyyy-MM-dd") + "\"))";
        }
        if (noresign && (searchSpecialQuery.getiResigned() == 1) && (searchSpecialQuery.getStartResign() != null) && (searchSpecialQuery.getEndResign() != null)) {
            whereClause = whereClause + " AND "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE] + " BETWEEN '"
                    + Formater.formatDate(searchSpecialQuery.getStartResign(), "yyyy-MM-dd") + "' AND '"
                    + Formater.formatDate(searchSpecialQuery.getEndResign(), "yyyy-MM-dd") + "'  ";
        }

        // parameter : DEPARTMENT = 0
        if (searchSpecialQuery.getArrDepartmentId(0) != null) {
            //if((String[]) vparam.get(1)!=null){
            String[] departmentId = searchSpecialQuery.getArrDepartmentId(0);
            //String[] departmentId = (String[]) vparam.get(1);
            if (departmentId != null && !((departmentId[0].equals("0")))) {
                whereClause += " AND (";
                for (int i = 0; i < departmentId.length; i++) {
                    whereClause = whereClause + " "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]
                            + " = " + departmentId[i] + " OR ";
                    if (i == (departmentId.length - 1)) {
                        whereClause = whereClause.substring(0, whereClause.length() - 4);
                    }
                }
                whereClause += " )";
            }
        }
        // parameter : POSITION = 2
        String[] positionId = searchSpecialQuery.getArrPositionId(0);
        //String[] positionId = (String[]) vparam.get(2);
        if (positionId != null && !((positionId[0].equals("0")))) {
            whereClause += " AND (";
            for (int i = 0; i < positionId.length; i++) {
                whereClause = whereClause + " "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_POSITION_ID]
                        + " = " + positionId[i] + " OR ";
                if (i == (positionId.length - 1)) {
                    whereClause = whereClause.substring(0, whereClause.length() - 4);
                }
            }
            whereClause += " )";
        }

        // parameter : LOCATION = 2
        String[] locationId = searchSpecialQuery.getArrLocationId(0);
        //String[] locationId = (String[]) vparam.get(2);
        if (locationId != null && !((locationId[0].equals("0")))) {
            whereClause += " AND (";
            for (int i = 0; i < locationId.length; i++) {
                whereClause = whereClause + " "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_LOCATION_ID]
                        + " = " + locationId[i] + " OR ";
                if (i == (locationId.length - 1)) {
                    whereClause = whereClause.substring(0, whereClause.length() - 4);
                }
            }
            whereClause += " )";
        }
        if (searchSpecialQuery.getArrPayrollGroupId(0) != null) {
            String[] payrollGId = searchSpecialQuery.getArrPayrollGroupId(0);
            if (!(payrollGId != null && (payrollGId[0].equals("0")))) {
                whereClause += " AND (";
                for (int i = 0; i < payrollGId.length; i++) {
                    whereClause = whereClause + " "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_PAYROLL_GROUP]
                            + " = " + payrollGId[i] + " OR ";
                    if (i == (payrollGId.length - 1)) {
                        whereClause = whereClause.substring(0, whereClause.length() - 4);
                    }
                }
                whereClause += " )";
            }

        }
        // parameter : GRADE = 2
        String[] gradeId = searchSpecialQuery.getArrGradeId(0);
        //String[] gradeId = (String[]) vparam.get(2);
        if (gradeId != null && !((gradeId[0].equals("0")))) {
            whereClause += " AND (";
            for (int i = 0; i < gradeId.length; i++) {
                whereClause = whereClause + " "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_GRADE_LEVEL_ID]
                        + " = " + gradeId[i] + " OR ";
                if (i == (gradeId.length - 1)) {
                    whereClause = whereClause.substring(0, whereClause.length() - 4);
                }
            }
            whereClause += " )";
        }

        String[] companyId = searchSpecialQuery.getArrCompany(0);
        //String[] positionId = (String[]) vparam.get(2);
        if (companyId != null && !((companyId[0].equals("0")))) {
            whereClause += " AND (";
            for (int i = 0; i < companyId.length; i++) {
                whereClause = whereClause + " "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]
                        + " = " + companyId[i] + " OR ";
                if (i == (companyId.length - 1)) {
                    whereClause = whereClause.substring(0, whereClause.length() - 4);
                }
            }
            whereClause += " )";
        }

        // parameter : SECTION = 3
        String[] sectionId = searchSpecialQuery.getArrSectionId(0);
        //String[] sectionId = (String[]) vparam.get(3);
        if (sectionId != null && !((sectionId[0].equals("0")))) {
            whereClause += " AND (";
            for (int i = 0; i < sectionId.length; i++) {
                whereClause = whereClause + " "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]
                        + " = " + sectionId[i] + " OR ";
                if (i == (sectionId.length - 1)) {
                    whereClause = whereClause.substring(0, whereClause.length() - 4);
                }
            }
            whereClause += " )";
        }

        // parameter : LEVEL = 4
        String[] levelId = searchSpecialQuery.getArrLevelId(0);
        //String[] levelId = (String[]) vparam.get(4);
        if (levelId != null && !((levelId[0].equals("0")))) {
            whereClause += " AND (";
            for (int i = 0; i < levelId.length; i++) {
                whereClause = whereClause + " "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_LEVEL_ID]
                        + " = " + levelId[i] + " OR ";
                if (i == (levelId.length - 1)) {
                    whereClause = whereClause.substring(0, whereClause.length() - 4);
                }
            }
            whereClause += " )";
        }

            // parameter : EDUCATION = 5
        //String[] educationId = (String[]) vparam.get(4);
        if (educationId != null) {
            if (educationId != null && !((educationId[0].equals("0")))) {
                whereClause += " AND (";
                for (int i = 0; i < educationId.length; i++) {
                    whereClause = whereClause + " EMPEDU." + PstEmpEducation.fieldNames[PstEmpEducation.FLD_EDUCATION_ID]
                            + " = '" + educationId[i] + "' OR ";
                    if (i == (educationId.length - 1)) {
                        whereClause = whereClause.substring(0, whereClause.length() - 4);
                    }
                }
                whereClause += " )";
            }
        }
        // parameter : RELIGION = 6
        String[] religionId = searchSpecialQuery.getArrReligionId(0);
            //String[] religionId = (String[]) vparam.get(6);

        if (religionId != null && !(religionId[0].equals("0"))) {
            whereClause += " AND (";
            for (int i = 0; i < religionId.length; i++) {
                whereClause = whereClause + " "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_RELIGION_ID]
                        + " = " + religionId[i] + " OR ";
                if (i == (religionId.length - 1)) {
                    whereClause = whereClause.substring(0, whereClause.length() - 4);
                }
            }
            whereClause += " )";
        }

        // parameter : MARITAL = 7
        String[] maritalId = searchSpecialQuery.getArrMaritalId(0);
        //String[] maritalId = (String[]) vparam.get(7);
        if (maritalId != null && !(maritalId[0].equals("0"))) {
            whereClause += " AND (";
            for (int i = 0; i < maritalId.length; i++) {
                whereClause = whereClause + " "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_MARITAL_ID]
                        + " = " + maritalId[i] + " OR ";
                if (i == (maritalId.length - 1)) {
                    whereClause = whereClause.substring(0, whereClause.length() - 4);
                }
            }
            whereClause += " )";
        }

        // parameter : BLOOD = 8
        if (searchSpecialQuery.getArrBlood(0) != null) {
            //if((String[]) vparam.get(8)!=null){
            String[] bloodId = searchSpecialQuery.getArrBlood(0);
            if (bloodId != null && !((bloodId[0].equals("0")))) {
                whereClause += " AND (";
                for (int i = 0; i < bloodId.length; i++) {
                    whereClause = whereClause + " "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_BLOOD_TYPE]
                            + " = '" + bloodId[i] + "' OR ";
                    if (i == (bloodId.length - 1)) {
                        whereClause = whereClause.substring(0, whereClause.length() - 4);
                    }
                }
                whereClause += " )";
            }
        }
        // parameter : RACE = 24
        String[] raceId = searchSpecialQuery.getArrRaceId(0);
        //String[] raceId = (String[]) vparam.get(24);
        if (raceId != null && !((raceId[0].equals("0")))) {
            whereClause += " AND (";
            for (int i = 0; i < raceId.length; i++) {
                whereClause = whereClause + " "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_RACE]
                        + " = " + raceId[i] + " OR ";
                if (i == (raceId.length - 1)) {
                    whereClause = whereClause.substring(0, whereClause.length() - 4);
                }
            }
            whereClause += " )";
        }

        // parameter : LANGUAGE = 9
        if (languageId != null && !(languageId[0].equals("0"))) {
            whereClause += " AND (";
            for (int i = 0; i < languageId.length; i++) {
                whereClause = whereClause + " EMPLANG." + PstLanguage.fieldNames[PstLanguage.FLD_LANGUAGE_ID]
                        + " = " + languageId[i] + " OR ";
                if (i == (languageId.length - 1)) {
                    whereClause = whereClause.substring(0, whereClause.length() - 4);
                }
            }
            whereClause += " )";
        }

        // parameter : SEX = 10
        int sex = searchSpecialQuery.getiSex();
        //Integer.parseInt(vparam.get(10).toString());
        if (sex < 2) {
            whereClause += " AND "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_SEX]
                    + " = " + sex;
        }

        // parameter : RESIGNED = 11
        int resigned = searchSpecialQuery.getiResigned();
        //Integer.parseInt(vparam.get(11).toString());
        java.util.Date newD = new java.util.Date();
        if (resigned < 2) {
            if (resigned == 0) {
                whereClause += " AND ("+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                        + " = " + resigned + " OR "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED_DATE] + ">=\"" + Formater.formatDate(newD, "yyyy-MM-dd") + "\")";
            } else {
                whereClause += " AND "+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED]
                        + " = " + resigned;
            }

        }

            //--- COMMENCING_DATE ----------------------------------------------
        // parameter : COMMENCING_DATE = 12, 13, 14
        int radiocommdate = searchSpecialQuery.getRadiocommdate();//Integer.parseInt(vparam.get(12).toString());
        if (radiocommdate > 0) {
//                java.util.Date commdatefrom;
//                java.util.Date commdateto;
            java.util.Date commdatefrom = null;
            java.util.Date commdateto = null;
            if (searchSpecialQuery.getCommdatefrom(0) != null) {//if (vparam.get(13) != null) {
                commdatefrom = searchSpecialQuery.getCommdatefrom(0);//(java.util.Date) vparam.get(13);
            }
            if (searchSpecialQuery.getCommdateto(0) != null) {//if (vparam.get(14) != null) {
                commdateto = searchSpecialQuery.getCommdateto(0);//(java.util.Date) vparam.get(14);
            }
            if ((commdatefrom != null) && (commdateto != null)) {
                whereClause = whereClause + " AND ("+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + " BETWEEN '"
                        + Formater.formatDate((java.util.Date) commdatefrom, "yyyy-MM-dd") + "' AND '"
                        + Formater.formatDate((java.util.Date) commdateto, "yyyy-MM-dd") + "') ";
            } else if ((commdatefrom != null) && (commdateto == null)) {
                whereClause = whereClause + " AND ("+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + " > '"
                        + Formater.formatDate((java.util.Date) commdatefrom, "yyyy-MM-dd") + "')";
            } else if ((commdatefrom == null) && (commdateto != null)) {
                whereClause = whereClause + " AND ("+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE] + " < '"
                        + Formater.formatDate((java.util.Date) commdateto, "yyyy-MM-dd") + "')";
            }
        }

            //--- END_CONTRACT ----------------------------------------------
        // parameter : END_CONTRACT = 12, 13, 14
        int radioendcontract = searchSpecialQuery.getRadioendcontract();//Integer.parseInt(vparam.get(12).toString());
        if (radioendcontract > 0) {
            java.util.Date endcontractfrom = null;
            java.util.Date endcontractto = null;
            if (searchSpecialQuery.getEndcontractfrom(0) != null) {//if (vparam.get(13) != null) {
                endcontractfrom = searchSpecialQuery.getEndcontractfrom(0);//(java.util.Date) vparam.get(13);
            }
            if (searchSpecialQuery.getEndcontractto(0) != null) {//if (vparam.get(14) != null) {
                endcontractto = searchSpecialQuery.getEndcontractto(0);//(java.util.Date) vparam.get(14);
            }
            if ((endcontractfrom != null) && (endcontractto != null)) {
                whereClause = whereClause + " AND ("+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_END_CONTRACT] + " BETWEEN '"
                        + Formater.formatDate((java.util.Date) endcontractfrom, "yyyy-MM-dd") + "' AND '"
                        + Formater.formatDate((java.util.Date) endcontractto, "yyyy-MM-dd") + "') ";
            } else if ((endcontractfrom != null) && (endcontractto == null)) {
                whereClause = whereClause + " AND ("+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_END_CONTRACT] + " > '"
                        + Formater.formatDate((java.util.Date) endcontractfrom, "yyyy-MM-dd") + "')";
            } else if ((endcontractfrom == null) && (endcontractto != null)) {
                whereClause = whereClause + " AND ("+PstEmployee.TBL_HR_EMPLOYEE+"." + PstEmployee.fieldNames[PstEmployee.FLD_END_CONTRACT] + " < '"
                        + Formater.formatDate((java.util.Date) endcontractto, "yyyy-MM-dd") + "')";
            }
        }

            //--- WORK ---------------------------------------------------------
        // parameter : WORKYEARFROM = 15
        int workyearfrom = searchSpecialQuery.getWorkyearfrom(0) != null && searchSpecialQuery.getWorkyearfrom(0).length() > 0 ? Integer.parseInt(searchSpecialQuery.getWorkyearfrom(0)) : 0;

        // parameter : WORKMONTHFROM = 16
        int workmonthfrom = searchSpecialQuery.getWorkmonthfrom(0) != null && searchSpecialQuery.getWorkmonthfrom(0).length() > 0 ? Integer.parseInt(searchSpecialQuery.getWorkmonthfrom(0)) : 0;
//            try { workmonthfrom = Integer.parseInt(vparam.get(16).toString()); }
//            catch (Exception e) {}

        long workfrom = Math.round(((12d * workyearfrom) + workmonthfrom) * DAYS_IN_A_MONTH);

        // parameter : WORKYEARTO = 17
        int workyearto = searchSpecialQuery.getWorkyearto(0) != null && searchSpecialQuery.getWorkyearto(0).length() > 0 ? Integer.parseInt(searchSpecialQuery.getWorkyearto(0)) : 0;
        /*try { workyearto= Integer.parseInt(vparam.get(17).toString()); }
         catch (Exception e) {}*/

        // parameter : WORKMONTHTO = 18
        int workmonthto = searchSpecialQuery.getWorkmonthto(0) != null && searchSpecialQuery.getWorkmonthto(0).length() > 0 ? Integer.parseInt(searchSpecialQuery.getWorkmonthto(0)) : 0;
//            try { workmonthto = Integer.parseInt(vparam.get(18).toString()); }
//            catch (Exception e) {}

        long workto = Math.round(((12d * workyearto) + workmonthto) * DAYS_IN_A_MONTH);

        if ((workfrom > 0) && (workto > 0) && (workfrom <= workto)) {
            whereClause += " AND ((TO_DAYS(NOW()) - TO_DAYS("+PstEmployee.TBL_HR_EMPLOYEE+"."
                    + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ") >= " + workfrom + " ) AND (TO_DAYS(NOW()) - TO_DAYS("+PstEmployee.TBL_HR_EMPLOYEE+"."
                    + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ") <= " + workto + " ))";
        } else if (workfrom > 0) {
            whereClause += " AND (TO_DAYS(NOW()) - TO_DAYS("+PstEmployee.TBL_HR_EMPLOYEE+"."
                    + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ") >= " + workfrom + ") ";
        } else if (workto > 0) {
            whereClause += " AND (TO_DAYS(NOW()) - TO_DAYS("+PstEmployee.TBL_HR_EMPLOYEE+"."
                    + PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]
                    + ") <= " + workto + ") ";
        }

            //--- AGE ----------------------------------------------------------
        // parameter : AGEYEARFROM = 19
        int ageyearfrom = searchSpecialQuery.getAgeyearfrom(0) != null && searchSpecialQuery.getAgeyearfrom(0).length() > 0 ? Integer.parseInt(searchSpecialQuery.getAgeyearfrom(0)) : 0;
//            try { ageyearfrom = Integer.parseInt(vparam.get(19).toString()); }
//            catch (Exception e) {}

        // parameter : AGEMONTHFROM = 20
        int agemonthfrom = searchSpecialQuery.getAgemonthfrom(0) != null && searchSpecialQuery.getAgemonthfrom(0).length() > 0 ? Integer.parseInt(searchSpecialQuery.getAgemonthfrom(0)) : 0;
//            try { agemonthfrom = Integer.parseInt(vparam.get(20).toString()); }
//            catch (Exception e) {}

        long agefrom = Math.round(((12d * ageyearfrom) + agemonthfrom) * DAYS_IN_A_MONTH);

        // parameter : AGEYEARTO = 21
        int ageyearto = searchSpecialQuery.getAgeyearto(0) != null && searchSpecialQuery.getAgeyearto(0).length() > 0 ? Integer.parseInt(searchSpecialQuery.getAgeyearto(0)) : 0;
//            try { ageyearto= Integer.parseInt(vparam.get(21).toString()); }
//            catch (Exception e) {}

        // parameter : AGEMONTHTO = 22
        int agemonthto = searchSpecialQuery.getAgemonthto(0) != null && searchSpecialQuery.getAgemonthto(0).length() > 0 ? Integer.parseInt(searchSpecialQuery.getAgemonthto(0)) : 0;
//            try { agemonthto = Integer.parseInt(vparam.get(22).toString()); }
//            catch (Exception e) {}

        long ageto = Math.round(((12d * ageyearto) + agemonthto) * DAYS_IN_A_MONTH);

        if ((agefrom > 0) && (ageto > 0) && (agefrom <= ageto)) {
            whereClause += " AND ((TO_DAYS(NOW()) - TO_DAYS("+PstEmployee.TBL_HR_EMPLOYEE+"."
                    + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]
                    + ") >= " + agefrom + " ) AND (TO_DAYS(NOW()) - TO_DAYS("+PstEmployee.TBL_HR_EMPLOYEE+"."
                    + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]
                    + ") <= " + ageto + " ))";
        } else if (agefrom > 0) {
            whereClause += " AND (TO_DAYS(NOW()) - TO_DAYS("+PstEmployee.TBL_HR_EMPLOYEE+"."
                    + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]
                    + ") >= " + agefrom + ") ";
        } else if (ageto > 0) {
            whereClause += " AND (TO_DAYS(NOW()) - TO_DAYS("+PstEmployee.TBL_HR_EMPLOYEE+"."
                    + PstEmployee.fieldNames[PstEmployee.FLD_BIRTH_DATE]
                    + ") <= " + ageto + ") ";
        }

        return whereClause;
    }
    
    private static String getReligion(long religionId){
        String data = "-";
        try {
            data = PstReligion.fetchExc(religionId).getReligion();
        } catch (Exception exc){}
        return data;
    }
    
    private static String getStatusMarital(long maritalId){
        String data = "-";
        try {
            data = PstMarital.fetchExc(maritalId).getMaritalStatus();
        } catch (Exception exc){}
        return data;
    }
    
    private static String getRace(long raceId){
        String data = "-";
        try {
            data = PstRace.fetchExc(raceId).getRaceName();
        } catch (Exception exc){}
        return data;
    }

    private static String getPayrollGroup(long payrollGroupId){
        String data = "-";
        try {
            data = PstPayrollGroup.fetchExc(payrollGroupId).getPayrollGroupName();
        } catch (Exception exc){}
        return data;
    }
    
    private static String getCompany(long companyId){
        String data = "-";
        try {
            data = PstCompany.fetchExc(companyId).getCompany();
        } catch (Exception exc){}
        return data;
    }
    
    private static String getDivision(long divisionId){
        String data = "-";
        try {
            data = PstDivision.fetchExc(divisionId).getDivision();
        } catch (Exception exc){}
        return data;
    }
    
    private static String getDepartment(long departmentId){
        String data = "-";
        try {
            data = PstDepartment.fetchExc(departmentId).getDepartment();
        } catch (Exception exc){}
        return data;
    }
    
    private static String getSection(long sectionId){
        String data = "-";
        try {
            data = PstSection.fetchExc(sectionId).getSection();
        } catch (Exception exc){}
        return data;
    }
    
    private static String getEmpCategory(long empCategoryId){
        String data = "-";
        try {
            data = PstEmpCategory.fetchExc(empCategoryId).getEmpCategory();
        } catch (Exception exc){}
        return data;
    }
    
    private static String getLevel(long levelId){
        String data = "-";
        try {
            data = PstLevel.fetchExc(levelId).getLevel();
        } catch (Exception exc){}
        return data;
    }
    
    private static String getPosition(long positionId){
        String data = "-";
        try {
            data = PstPosition.fetchExc(positionId).getPosition();
        } catch (Exception exc){}
        return data;
    }
    
    private static String getFamilyRelation(String relationId){
        String data = "-";
        try {
            data = PstFamRelation.fetchExc(Long.valueOf(relationId)).getFamRelation();
        } catch (Exception exc){}
        return data;
    }
    
    private static String getEducation(long educationId){
        String data = "-";
        try {
            data = PstEducation.fetchExc(educationId).getEducation();
        } catch (Exception exc){}
        return data;
    }
    
    private static String getCompetency(long competencyId){
        String data = "-";
        try {
            data = PstCompetency.fetchExc(competencyId).getCompetencyName();
        } catch (Exception exc){}
        return data;
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
