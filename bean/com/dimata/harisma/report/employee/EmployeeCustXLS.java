/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.harisma.report.employee;

import com.dimata.harisma.entity.employee.EmpAward;
import com.dimata.harisma.entity.employee.EmpEducation;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmpAward;
import com.dimata.harisma.entity.employee.PstEmpEducation;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.Department;
import com.dimata.harisma.entity.masterdata.Marital;
import com.dimata.harisma.entity.masterdata.Position;
import com.dimata.harisma.entity.masterdata.PstDepartment;
import com.dimata.harisma.entity.masterdata.PstMarital;
import com.dimata.harisma.entity.masterdata.PstPosition;
import com.dimata.harisma.entity.masterdata.PstReligion;
import com.dimata.harisma.entity.masterdata.Religion;
import com.dimata.harisma.session.employee.SessEmployeePicture;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.util.DateCalc;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Vector;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFRegionUtil;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;

/**
 *
 * @author dimata005
 */
public class EmployeeCustXLS extends HttpServlet {

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

        HSSFWorkbook wb = new HSSFWorkbook();
        DataFormat format = wb.createDataFormat();
        HSSFSheet sheet = wb.createSheet("Book 1");
        
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
        
        Vector<String> whereCollect = new Vector<String>();
        
        if (companyId != 0){
            String whereClauseEmp = " "+PstEmployee.fieldNames[PstEmployee.FLD_COMPANY_ID]+"="+companyId;
            whereCollect.add(whereClauseEmp);
        }
        if (oidDivision != 0){
            String whereClauseEmp = " "+PstEmployee.fieldNames[PstEmployee.FLD_DIVISION_ID]+"="+oidDivision;
            whereCollect.add(whereClauseEmp);
        }
        if (oidDepartment != 0){
            String whereClauseEmp = " "+PstEmployee.fieldNames[PstEmployee.FLD_DEPARTMENT_ID]+"="+oidDepartment;
            whereCollect.add(whereClauseEmp);
        }
        if (oidSection != 0){
            String whereClauseEmp = " "+PstEmployee.fieldNames[PstEmployee.FLD_SECTION_ID]+"="+oidSection;
            whereCollect.add(whereClauseEmp);
        }
        
        String whereClauseEmp = PstEmployee.fieldNames[PstEmployee.FLD_COMMENCING_DATE]+" BETWEEN "
                            + "(SELECT DATE_FORMAT(DATE_ADD(NOW(), INTERVAL - ("+losYearTo+"."+losMonthTo+"*365) DAY),'%Y-%m-%d')) AND "
                            + "(SELECT DATE_FORMAT(DATE_ADD(NOW(), INTERVAL - ("+losYearFrom+"."+losMonthFrom+"*365) DAY),'%Y-%m-%d'))"
                            + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 0";;
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
        
        Vector listEmployee = PstEmployee.list(0, 0, whereClauseEmp, "");
        
        
        String[] tableHeader = {
            "NO", "FOTO", "NAMA / NOMER HANDPHONE", "JENIS KELAMIN / STATUS", 
            "TGL LAHIR / USIA", "SHIO / ELEMEN", 
            "AGAMA / PENDIDKAN TERAKHIR", "TGL MASUK/ MASA KERJA", 
            "JABATAN TERAKHIR / DEPARTEMEN", "KEGIATAN / KETERANGAN"
        };
        //100 is 0.25 width
        sheet.setColumnWidth(0, 1500);
        sheet.setColumnWidth(1, 4700);  
        sheet.setColumnWidth(2, 5700);
        sheet.setColumnWidth(3, 4300);
        sheet.setColumnWidth(4, 4300);
        sheet.setColumnWidth(5, 3200);
        sheet.setColumnWidth(6, 5750);
        sheet.setColumnWidth(7, 4400);
        sheet.setColumnWidth(8, 9500);
        sheet.setColumnWidth(9, 12000);
        
        
        
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
        
        for (int k = 0; k < tableHeader.length; k++) {
            cell = row.createCell((short) k);
            cell.setCellValue(tableHeader[k]);
            cell.setCellStyle(styleHeader);
        }
        
        int rowMergeStart = 4;
        HSSFPatriarch patriarch=sheet.createDrawingPatriarch();
        for (int i = 0;i < listEmployee.size(); i++){
            Employee emp = (Employee) listEmployee.get(i);
            
            Vector listAward = PstEmpAward.list(0, 0, PstEmpAward.fieldNames[PstEmpAward.FLD_EMPLOYEE_ID]+"="+emp.getOID(), "");
            String strKegiatan = "";
            String strDesc = "";
            if (listAward.size()>0){
                for (int x=0; x < listAward.size();x++){
                    EmpAward empAward = (EmpAward) listAward.get(x);
                    strKegiatan += empAward.getTitle() + " \n ";
                    strDesc += empAward.getAwardDescription() + " \n ";
                }
            }
            row = sheet.createRow((short) rowMergeStart);
            cell = row.createCell((short) 0);
            cell.setCellValue(i+1);
            cell.setCellStyle(style1);
            
            cell = row.createCell((short) 2);
            cell.setCellValue(emp.getFullName());
            cell.setCellStyle(style1);
            
            /*Jenis Kelamin*/
            cell = row.createCell((short) 3);
            cell.setCellValue((emp.getSex()==0? "Laki-Laki" : "Perempuan"));
            cell.setCellStyle(style1);
            /*end Jenis Kelamin*/
            
            /*birth date */
            cell = row.createCell((short) 4);
            cell.setCellValue(emp.getBirthDate());
            cell.setCellStyle(styleDate);
            /*end birth date*/
            
            /*shio*/
            cell = row.createCell((short) 5);
            cell.setCellValue(emp.getShio());
            cell.setCellStyle(styleDate);
            /*end shio*/
            
            /*religion*/
            String religion = "";
            try {
                Religion rel = PstReligion.fetchExc(emp.getReligionId());
                religion = rel.getReligion();
            } catch (Exception exc){}
            
            cell = row.createCell((short) 6);
            cell.setCellValue(religion);
            cell.setCellStyle(style1);
            /*end religion*/
            
            /*comm date */
            cell = row.createCell((short) 7);
            cell.setCellValue(emp.getCommencingDate());
            cell.setCellStyle(styleDate);
            /*end Comm date*/
            
            /*position*/
            String position = "";
            try {
                Position pos = PstPosition.fetchExc(emp.getPositionId());
                position = pos.getPosition();
            } catch (Exception exc){}
            cell = row.createCell((short) 8);
            cell.setCellValue(position);
            cell.setCellStyle(style1);
            /*end position*/
            
            /*kegiatan*/
            cell = row.createCell((short) 9);
            cell.setCellValue(strKegiatan);
            cell.setCellStyle(styleWrap);
            /*end kegiatan*/
            
            /* image */
            
            int index = getPicIndex(wb, emp, request);
            HSSFClientAnchor anchor = new HSSFClientAnchor(20,10,1010,225,(short)1,(rowMergeStart),(short)1,(rowMergeStart+1));
            anchor.setAnchorType(1);
            patriarch.createPicture(anchor, index); 
            
            row.setHeight((short) 1500);
            
            row = sheet.createRow((short) rowMergeStart+1);
            
            cell = row.createCell((short) 0);
            cell.setCellValue("");
            cell.setCellStyle(style1);
            
            cell = row.createCell((short) 1);
            cell.setCellValue("");
            cell.setCellStyle(style1);
            
            /*phone*/
            cell = row.createCell((short) 2);
            cell.setCellValue(emp.getPhone());
            cell.setCellStyle(style1);
            /*end phone*/
            
            /*marital*/
            String marital = "";
            try {
                Marital mar = PstMarital.fetchExc(emp.getMaritalId());
                marital = mar.getMaritalStatus();
            } catch (Exception exc){}
            cell = row.createCell((short) 3);
            cell.setCellValue(marital);
            cell.setCellStyle(style1);
            /*end marital*/
            
            /*age*/
            long dayDiffAge = DateCalc.dayDifference(emp.getBirthDate(), new Date());
            cell = row.createCell((short) 4);
            cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
            cell.setCellFormula(dayDiffAge+"/365");
            cell.setCellStyle(styleDouble);
            /*end age*/
            
            /*elemen*/
            cell = row.createCell((short) 5);
            cell.setCellValue(emp.getElemen());
            cell.setCellStyle(style1);
            /*end elemen*/
            
            /*education*/
            Vector listEducation = PstEmpEducation.list(0, 0, PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMPLOYEE_ID]+"="+emp.getOID(), "");
            String edu = "";
            if (listEducation.size()>0){
                EmpEducation empEdu = (EmpEducation) listEducation.get(0);
                edu = empEdu.getEducationDesc();
            }
            cell = row.createCell((short) 6);
            cell.setCellValue(edu);
            cell.setCellStyle(style1);
            /*end education*/
            
            /*los*/
            long dayDiffLos = DateCalc.dayDifference(emp.getCommencingDate(), new Date());
            cell = row.createCell((short) 7);
            cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
            cell.setCellFormula(dayDiffLos+"/365");
            cell.setCellStyle(styleDouble);
            /*end los*/
            
            /*los*/
            String dept = "";
            try {
                Department depart = PstDepartment.fetchExc(emp.getDepartmentId());
                dept = depart.getDepartment();
            } catch (Exception exc){}
            cell = row.createCell((short) 8);
            cell.setCellValue(dept);
            cell.setCellStyle(style1);
            /*end los*/
            
            /*ket*/
            cell = row.createCell((short) 9);
            cell.setCellValue(strDesc);
            cell.setCellStyle(styleWrap);
            /*end ket*/
            row.setHeight((short) 1500);
            
            org.apache.poi.ss.util.CellRangeAddress cellRangeAddress = new org.apache.poi.ss.util.CellRangeAddress(rowMergeStart,rowMergeStart+1,1,1);
            sheet.addMergedRegion(new CellRangeAddress(rowMergeStart,rowMergeStart+1,0,0));
            
            sheet.addMergedRegion(new CellRangeAddress(rowMergeStart,rowMergeStart+1,1,1));
            HSSFRegionUtil.setBorderBottom(CellStyle.BORDER_THIN, cellRangeAddress, sheet, wb);
            HSSFRegionUtil.setBorderLeft(CellStyle.BORDER_THIN, cellRangeAddress, sheet, wb);
            HSSFRegionUtil.setBorderRight(CellStyle.BORDER_THIN, cellRangeAddress, sheet, wb);
            HSSFRegionUtil.setBorderTop(CellStyle.BORDER_THIN, cellRangeAddress, sheet, wb);
            
            rowMergeStart = rowMergeStart + 2;
            
        }
        
        sheet.createFreezePane(3,4);
        
        ServletOutputStream sos = response.getOutputStream();
        wb.write(sos);
        sos.close();
    }
    
    public int getPicIndex(HSSFWorkbook wb, Employee employee, HttpServletRequest request){
        int index = -1;
        try {
            /*byte[] picData = null;
            File pic = new File( "D:\\Gunadi\\Windows 10\\development\\Hairisma\\HairismaBPD\\jsp\\imgcache\\no-img.jpg" );
            long length = pic.length(  );
            picData = new byte[ ( int ) length ];
            FileInputStream picIn = new FileInputStream( pic );
            picIn.read( picData );*/
            String pictPath = "";
            try {
                SessEmployeePicture sessEmployeePicture = new SessEmployeePicture();
                pictPath = sessEmployeePicture.fetchImageEmployee(employee.getOID());

            } catch (Exception e) {
                System.out.println("err." + e.toString());
            }
            ServletContext servletContext = getServletContext();
            String contextPath = servletContext.getRealPath("/");
            if (pictPath != null && pictPath.length() > 0) {
                pictPath = contextPath + "/" + pictPath;
            } else {
                pictPath = contextPath + "/imgcache/no-img.jpg";
            }
            
            InputStream my_banner_image = new FileInputStream(pictPath);
            /* Convert Image to byte array */
            byte[] bytes = IOUtils.toByteArray(my_banner_image);
            index = wb.addPicture( bytes, HSSFWorkbook.PICTURE_TYPE_JPEG );
        } catch (IOException e) {
            e.printStackTrace();
        }  catch (Exception e) {
            e.printStackTrace();
        } 
        return index;
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
