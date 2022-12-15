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
import com.dimata.qdep.db.DBHandler;
import com.dimata.qdep.db.DBResultSet;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.util.DateCalc;
import com.dimata.util.Formater;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.*;
import java.sql.ResultSet;
import java.util.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author dimata005
 */
public class EmployeeCustPDF extends HttpServlet {

    public static Color blackColor = new Color(0,0,0);
    
    
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
        
        Document document = new Document(PageSize.A3.rotate(), 80f, 30f, 30f, 30f); 
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
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
                            + " AND " + PstEmployee.fieldNames[PstEmployee.FLD_RESIGNED] + " = 0";
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
        
        try {
            
            PdfWriter pdfWriter = PdfWriter.getInstance(document, baos);
            document.open();
            
            String[] tableHeader = {
                "NO", "FOTO", "NAMA / NOMER HANDPHONE", "JENIS KELAMIN / STATUS", 
                "TGL LAHIR / USIA", "SHIO / ELEMEN", 
                "AGAMA / PENDIDKAN TERAKHIR", "TGL MASUK/ MASA KERJA", 
                "JABATAN TERAKHIR / DEPARTEMEN", "KEGIATAN / KETERANGAN"
            };
            
            document.add(createDetail(tableHeader, listEmployee, pdfWriter));  
            document.close();
        
            response.setContentType("application/pdf");
            response.setContentLength(baos.size());
            ServletOutputStream out = response.getOutputStream();  
            baos.writeTo(out);
            out.flush();
            out.close();
        } catch (Exception exc){}
        
        
       
    }
    
    
    public PdfPTable createDetail(String[] strTitle, Vector listEmployee, PdfWriter pdfWriter)
    {        
        PdfPTable datatable = null;
        try
        {
            ServletContext servletContext = getServletContext();
            String contextPath = servletContext.getRealPath("/");
            BaseFont baseCalibriBold = BaseFont.createFont(contextPath+"/styles/fonts/calibrib.ttf", BaseFont.CP1250, BaseFont.NOT_EMBEDDED);
            Font calibri20 = new Font(baseCalibriBold, 14);
            Font calibri16 = new Font(baseCalibriBold, 14);
            
            // create table
            datatable = new PdfPTable(10);
            int[] headerwidths={2,4,5,4,4,3,5,4,10,8};
            datatable.setWidths(headerwidths);
            datatable.setWidthPercentage(100);
            datatable.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            PdfPCell cell;
            cell = new PdfPCell(new Phrase("HOTEL BOROBUDUR JAKARTA",calibri20));
            cell.setColspan(10);
            cell.setBorder(0);
            datatable.addCell(cell);
            
            cell = new PdfPCell(new Phrase(""));
            cell.setColspan(10);
            cell.setBorder(0);
            cell.setFixedHeight(30);
            datatable.addCell(cell);
            
            
            for (int k = 0; k < strTitle.length; k++) {
                cell = new PdfPCell(new Phrase(strTitle[k],calibri16));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                datatable.addCell(cell);
            }
            for (int i = 0; i < listEmployee.size(); i++) {
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
                
                String pictPath = "";
                try {
                    SessEmployeePicture sessEmployeePicture = new SessEmployeePicture();
                    pictPath = sessEmployeePicture.fetchImageEmployee(emp.getOID());

                } catch (Exception e) {
                    System.out.println("err." + e.toString());
                }
                if (pictPath != null && pictPath.length() > 0) {
                    pictPath = contextPath + "/" + pictPath;
                } else {
                    pictPath = contextPath + "/imgcache/no-img.jpg";
                }

                Image img = Image.getInstance(pictPath);
                float width = img.getScaledWidth();
                float height = img.getScaledHeight();
                float ratio = height/width;
                float leftreduce = 0f;
                float rightreduce = 0f;
                if (ratio < 1.4f){
                    float y = height/7;
                    float shouldWidth = y*5;
                    float over = width - shouldWidth;
                    leftreduce = Math.abs(over/2);
                    rightreduce = Math.abs(over/2);
                }
                Image cropImage = cropImage(pdfWriter, img, leftreduce, rightreduce, 0, 0);
                cropImage.scaleAbsolute(85f, 120f);
                
                
                datatable.getDefaultCell().setFixedHeight(130f);
                datatable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                datatable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
                datatable.addCell(""+(i+1));
                PdfPCell cellImage = new PdfPCell();
                cellImage.addElement(cropImage);
                datatable.addCell(cellImage);

                PdfPTable col3 = new PdfPTable(1);
                col3.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
                col3.getDefaultCell().setFixedHeight(65f);
                col3.addCell(emp.getFullName());
                col3.addCell(emp.getPhone());
                PdfPCell cellCol3 = new PdfPCell(col3);
                datatable.addCell(cellCol3);

                PdfPTable col4 = new PdfPTable(1);
                col4.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
                col4.getDefaultCell().setFixedHeight(65f);
                col4.addCell((emp.getSex()==0? "Laki-Laki" : "Perempuan"));
                String marital = "";
                try {
                    Marital mar = PstMarital.fetchExc(emp.getMaritalId());
                    marital = mar.getMaritalStatus();
                } catch (Exception exc){}
                col4.addCell(marital);
                PdfPCell cellCol4 = new PdfPCell(col4);
                datatable.addCell(cellCol4);

                PdfPTable col5 = new PdfPTable(1);
                col5.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
                col5.getDefaultCell().setFixedHeight(65f);
                col5.addCell(Formater.formatDate(emp.getBirthDate(), "dd-MMM-yy"));
                long dayDiffAge = DateCalc.dayDifference(emp.getBirthDate(), new Date());
                double age = getLosValue(dayDiffAge+"/365");
                col5.addCell(""+age);
                PdfPCell cellcol5 = new PdfPCell(col5);
                datatable.addCell(cellcol5);

                PdfPTable col6 = new PdfPTable(1);
                col6.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
                col6.getDefaultCell().setFixedHeight(65f);
                col6.addCell(emp.getShio());
                col6.addCell(emp.getElemen());
                PdfPCell cellcol6 = new PdfPCell(col6);
                datatable.addCell(cellcol6);

                PdfPTable col7 = new PdfPTable(1);
                col7.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
                col7.getDefaultCell().setFixedHeight(65f);
                String religion = "";
                try {
                    Religion rel = PstReligion.fetchExc(emp.getReligionId());
                    religion = rel.getReligion();
                } catch (Exception exc){}
                col7.addCell(religion);
                Vector listEducation = PstEmpEducation.list(0, 0, PstEmpEducation.fieldNames[PstEmpEducation.FLD_EMPLOYEE_ID]+"="+emp.getOID(), "");
                String edu = "";
                if (listEducation.size()>0){
                    EmpEducation empEdu = (EmpEducation) listEducation.get(0);
                    edu = empEdu.getEducationDesc();
                }
                col7.addCell(edu);
                PdfPCell cellcol7 = new PdfPCell(col7);
                datatable.addCell(cellcol7);

                PdfPTable col8 = new PdfPTable(1);
                col8.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
                col8.getDefaultCell().setFixedHeight(65f);
                col8.addCell(Formater.formatDate(emp.getCommencingDate(), "dd-MMM-yy"));
                long dayDiffLos = DateCalc.dayDifference(emp.getCommencingDate(), new Date());
                double los = getLosValue(dayDiffLos+"/365");
                col8.addCell(""+los);
                PdfPCell cellcol8 = new PdfPCell(col8);
                datatable.addCell(cellcol8);

                PdfPTable col9 = new PdfPTable(1);
                col9.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
                col9.getDefaultCell().setFixedHeight(65f);
                String position = "";
                try {
                    Position pos = PstPosition.fetchExc(emp.getPositionId());
                    position = pos.getPosition();
                } catch (Exception exc){}
                col9.addCell(position);
                String dept = "";
                try {
                    Department depart = PstDepartment.fetchExc(emp.getDepartmentId());
                    dept = depart.getDepartment();
                } catch (Exception exc){}
                col9.addCell(dept);
                PdfPCell cellcol9 = new PdfPCell(col9);
                datatable.addCell(cellcol9);

                PdfPTable col10 = new PdfPTable(1);
                col10.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
                col10.getDefaultCell().setFixedHeight(65f);
                col10.addCell(strKegiatan);
                col10.addCell(strDesc);
                PdfPCell cellcol10 = new PdfPCell(col10);
                datatable.addCell(cellcol10);
            }
            
            datatable.setHeaderRows(3);
            // this is the end of the table header
        }
        catch(Exception e)       
        {
            System.out.println("Exc createHeaderDetail : " + e.toString());
        }
        return datatable;
    }
    
    public static double getLosValue(String formula) {
        DBResultSet dbrs = null;
        double los = 0;
        try {
            String sql = "SELECT ROUND(" + formula + ",1)";
            /*String sql = "SELECT COMP_VALUE FROM " + TBL_PAY_SLIP_COMP+
            " WHERE "+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_PAY_SLIP_ID]+"="+paySlipId+
            " AND "+PstPaySlipComp.fieldNames[PstPaySlipComp.FLD_COMP_CODE]+" LIKE '%"+compCode.trim()+"%'";*/
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            // System.out.println("SQL getCompFormValue"+sql);
            while (rs.next()) {
                los = rs.getDouble(1);
            }

            rs.close();
            return los;
        } catch (Exception e) {
            return 0;            
        } finally {
            DBResultSet.close(dbrs);
        }
    }
    
    public Image cropImage(PdfWriter writer, Image image, float leftReduction, float rightReduction, float topReduction, float bottomReduction) throws DocumentException {
        float width = image.getScaledWidth();
        float height = image.getScaledHeight();
        PdfTemplate template = writer.getDirectContent().createTemplate(
                width - leftReduction - rightReduction,
                height - topReduction - bottomReduction);
        template.addImage(image,
                width, 0, 0,
                height, -leftReduction, -bottomReduction);
        return Image.getInstance(template);
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
