/*
 * MonthlySicknessPdf.java
 *
 * Created on June 17, 2004, 6:29 PM
 */

package com.dimata.harisma.report.sickness;

/* package java */
import com.lowagie.text.Cell;
import com.lowagie.text.PageSize;
import com.lowagie.text.Font;
import com.lowagie.text.DocumentException;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Element;
import com.lowagie.text.Chunk;
import com.lowagie.text.Table;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import java.util.*;
import java.text.*;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/* package servlet */
import javax.servlet.*;
import javax.servlet.http.*;

/* package lowagie */
import com.lowagie.text.pdf.PdfWriter;

/* package qdep */
import com.dimata.util.*;
import com.dimata.qdep.form.*;

/* package harisma */
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.entity.admin.*;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.form.search.*;
import com.dimata.harisma.session.attendance.*;

/**
 *
 * @author  gedhy
 */
public class MonthlySicknessPdf extends HttpServlet {
    
    /* declaration constant */
    public static Color blackColor = new Color(0,0,0);
    public static Color whiteColor = new Color(255,255,255);
    public static Color titleColor = new Color(200,200,200);
    public static Color summaryColor = new Color(240,240,240);
    public static String formatDate  = "MMM dd, yyyy";
    public static String formatNumber = "#,###";

    /* setting some fonts in the color chosen by the user */
    public static Font fontHeader = new Font(Font.TIMES_NEW_ROMAN, 10, Font.BOLD, blackColor);
    public static Font fontTitle = new Font(Font.TIMES_NEW_ROMAN, 9, Font.NORMAL, blackColor);
    public static Font fontContent = new Font(Font.TIMES_NEW_ROMAN, 8, Font.NORMAL, blackColor);

    /** Initializes the servlet
    */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    /** Handles the HTTP <code>GET</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** Destroys the servlet
    */
    public void destroy() {
    }

    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {


        /* creating the document object */
        Document document = new Document(PageSize.A4.rotate(), 30, 30, 50, 50);

        /* creating an OutputStream */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {

            /* creating an instance of the writer */
            PdfWriter writer = PdfWriter.getInstance(document, baos);

            /* get data from session */
            HttpSession sessEmpPresence = request.getSession(true);
            Vector vctMonthlyAbsence = null;
            try{
	        vctMonthlyAbsence = (Vector)sessEmpPresence.getValue("SICKNESS_MONTHLY");
            }catch(Exception e){
                System.out.println("Exc : "+e.toString());
            }
            //System.out.println("vctMonthlyAbsence  "+vctMonthlyAbsence.size());
            //Date presence = new Date();
            String presence = "";
            String departmentName="";
            Department department = new Department();
            Vector listPresence = new Vector(1,1);
            int intDateOfMonth = 0;
            int startDatePeriod = 0;
            String footerNote = "";
                      String empNumb="";
             String fullName="";
             long oidDepartment=0;
              long oidSection=0;
              Vector listStruktur = new Vector(1,1);
            if(vctMonthlyAbsence != null && vctMonthlyAbsence.size()>0){
                try{
	            	presence = (String) vctMonthlyAbsence.get(0);
	               /* long oidDepartment = Long.parseLong((String)vctMonthlyAbsence.get(1));
                        if(oidDepartment!=0){
                            department = (Department)PstDepartment.fetchExc(oidDepartment);
                            departmentName= department.getDepartment();
                        }
                        else{
                            departmentName = " ALL DEPARTMENT ";
                        }*/
                         if(vctMonthlyAbsence.get(0) !=null){
                         oidDepartment = Long.parseLong((String)vctMonthlyAbsence.get(1));
                       // if(oidDepartment !=0){
	               // department = (Department)PstDepartment.fetchExc(oidDepartment);
                        //}
                      } 
                        if(vctMonthlyAbsence.get(8) !=null){
                          oidSection = Long.parseLong((String)vctMonthlyAbsence.get(8));
                        //if(oidSection !=0){
                            //section = (com.dimata.harisma.entity.masterdata.Section)PstSection.fetchExc(oidSection);
                        //}
                      }
	                listPresence = (Vector)vctMonthlyAbsence.get(2);
                        intDateOfMonth = Integer.parseInt(String.valueOf(vctMonthlyAbsence.get(3)));
                        startDatePeriod = Integer.parseInt(String.valueOf(vctMonthlyAbsence.get(4)));
                        footerNote = (String) vctMonthlyAbsence.get(5);
                         if(vctMonthlyAbsence.get(6)!=null)
                        empNumb = (String)vctMonthlyAbsence.get(6);
                        if(vctMonthlyAbsence.get(7)!=null)
                        fullName = (String)vctMonthlyAbsence.get(7);
                       if((empNumb !=null && empNumb.length() > 0) 
                                 || fullName !=null && fullName.length()>0
                                 || oidDepartment !=0
                                 || oidSection !=0){
                            listStruktur = PstDepartment.listStruktur(empNumb.trim(), fullName.trim(),oidDepartment,oidSection);
                         }
                }
                catch(Exception e)
                {
                 	System.out.println("exc on get List vctMonthlyAbsence : "+e.toString());
                }
            }               
            
            /* adding a Header of page, i.e. : title, align and etc */
            
             String departement = "";
          String company = "";
          String division = "";
          String section ="";
           if(listStruktur !=null && listStruktur.size()>0){
                for(int d=0; d < listStruktur.size(); d++){
                     StrukturEmployee strukturEmployee = (StrukturEmployee)listStruktur.get(d);
                    company = strukturEmployee.getCompany();
                    division = strukturEmployee.getDivision();
                    departement = strukturEmployee.getDepartment();
                    section = strukturEmployee.getSection();
                }
            }
            if(departement ==null || departement.length() <= 0 ){
               departement="-";
            }
             if(section ==null || section.length() <= 0 ){
               section="-";
            }
              if(company ==null || company.length() <= 0 ){
               company="-";
            }
            if(division ==null || division.length() <= 0 ){
               division="-";
            }
             String strReportTitle = 
                                    "SICKNESS REPORT PER : " + presence.toUpperCase() +
                                    "\nCOMPANY \t\t\t\t\t\t\t\t\t: "+ company.toUpperCase()+
                                    "\nDEPARTMENT \t: "+ departement.toUpperCase()+
                                    "\nDIVISION \t\t\t\t\t\t\t\t\t\t\t: "+ division.toUpperCase()+
                                    "\nSECTION \t\t\t\t\t\t\t\t\t\t\t : "+ section.toUpperCase();
                                    // "\nSECTION : \n"+sec;
            /* adding a Header of page, i.e. : title, align and etc */
            HeaderFooter header = new HeaderFooter(new Phrase(strReportTitle, fontHeader), false);

            header.setAlignment(Element.ALIGN_LEFT);
            header.setBorder(Rectangle.BOTTOM);
            header.setBorderColor(blackColor);
            document.setHeader(header); 
            
            
            
            //footer
           HeaderFooter footer = new HeaderFooter(new Phrase(""+footerNote.toUpperCase(), fontContent), false);
            // HeaderFooter headerleft = new HeaderFooter(new Phrase("LEVEL : " + levName.toUpperCase(), fontHeader), false);
            footer.setAlignment(Element.ALIGN_LEFT);
            footer.setBorder(Rectangle.TOP);
            footer.setBorderColor(blackColor);
            document.setFooter(footer);
             
            /* opening the document, needed for add something into document */
            document.open();

            /* create header */            
            Table tableEmpPresence = createHeaderDetailDinamis(intDateOfMonth, startDatePeriod, fontContent);

            /* generate employee attendance report's data */
            Cell cellEmpData = new Cell("");
             
            if(listPresence!=null && listPresence.size()>0)
            {
                for (int i = 0; i < listPresence.size(); i++) 
                {          
                    Vector itemAbsence = (Vector)listPresence.get(i);

                    // no, payroll and name
                    cellEmpData = new Cell(new Chunk(String.valueOf(itemAbsence.get(0)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                    cellEmpData = new Cell(new Chunk(String.valueOf(itemAbsence.get(1)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                    cellEmpData = new Cell(new Chunk(String.valueOf(itemAbsence.get(2)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);          
                        
                    // list of schedule and duration, dimulai dari index ke 3
                    for(int isch=3; isch<itemAbsence.size()-1; isch++)
                    {
                        cellEmpData = new Cell(new Chunk(String.valueOf(itemAbsence.get(isch)),fontContent));
                        cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                        cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                        cellEmpData.setBackgroundColor(whiteColor);
                        cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                        tableEmpPresence.addCell(cellEmpData);          
                    }

                    // total
                    cellEmpData = new Cell(new Chunk(String.valueOf(itemAbsence.get(itemAbsence.size()-1)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_RIGHT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);                                
  
                    if (!writer.fitsPage(tableEmpPresence)) {
                        tableEmpPresence.deleteLastRow();
                        i--;
                        document.add(tableEmpPresence);
                        document.newPage();                        
                        tableEmpPresence = createHeaderDetailDinamis(intDateOfMonth, startDatePeriod, fontContent);
                    }
	        }
            }

            document.add(tableEmpPresence);
        }
        catch(DocumentException de) {
            System.err.println(de.getMessage());
            de.printStackTrace();
        }

        /* closing the document */
        document.close();

        /* we have written the pdfstream to a ByteArrayOutputStream, now going to write this outputStream to the ServletOutputStream
         * after we have set the contentlength
         */
        response.setContentType("application/pdf");
        response.setContentLength(baos.size());
        ServletOutputStream out = response.getOutputStream();
        baos.writeTo(out);
        out.flush();
    }
    
    /** 
     * create header of report
     * @param intDateOfMonth
     * @param font1
     * @return
     * @created by Edhy
     */    
    public static Table createHeaderDetail(int intDateOfMonth, Font font1)
    {        
        Table datatable = null;  
        try
        {                        
            int maxColumn = 4 + intDateOfMonth;                        
            float headerwidths[] = new float[maxColumn];
            headerwidths[0] = 2;
            headerwidths[1] = 5;
            headerwidths[2] = 14;
            
            int index = 3;                        
            for(int j=0; j<intDateOfMonth; j++)
            {                    
                headerwidths[index] = 2.5f;  
                index++;                    
            }
            headerwidths[index] = 3;
            

            // create table
            datatable = new Table(maxColumn);
            datatable.setPadding(1);
            datatable.setSpacing(1);
            datatable.setWidths(headerwidths);
            datatable.setWidth(100);
            datatable.setDefaultRowspan(2);
            datatable.setDefaultVerticalAlignment(Cell.ALIGN_MIDDLE);
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.setBackgroundColor(summaryColor);                
            
            // data no urut, payroll number and employee
            datatable.addCell(new Phrase("No", font1));
            datatable.addCell(new Phrase("Payroll", font1));
            datatable.addCell(new Phrase("Employee", font1));
            
            
            datatable.setDefaultColspan(intDateOfMonth);
            datatable.setDefaultRowspan(1);            
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.addCell(new Phrase("Sickness Period", font1));                        
            
            // data total night shift
            datatable.setDefaultRowspan(2);
            datatable.setDefaultColspan(1);
            datatable.setBackgroundColor(summaryColor);                
            datatable.setDefaultVerticalAlignment(Cell.ALIGN_MIDDLE);
            datatable.addCell(new Phrase("Total", font1));            

            // detail data every date of month
            
            for(int itDetail=0; itDetail<intDateOfMonth; itDetail++)
            {
                datatable.setDefaultColspan(1);
                datatable.setDefaultRowspan(1);
                datatable.setBackgroundColor(summaryColor);                
                datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
                datatable.addCell(new Phrase(""+(itDetail+1), font1));                
            }

            // this is the end of the table header
            datatable.endHeaders();
        }
        catch(Exception e)
        {
            System.out.println("Exception on createHeaderDetail : "+e.toString());
        }
        return datatable;
    }
    
    
    /** 
     * create header of report ( untuk periode dinamis)
     * @param intDateOfMonth
     * @param font1
     * @param startDatePeriod
     * @return
     * @created by Yunny
     */    
    public static Table createHeaderDetailDinamis(int intDateOfMonth, int startDatePeriod, Font font1)
    {        
        Table datatable = null;  
        try
        {                        
            int maxColumn = 4 + intDateOfMonth;                        
            float headerwidths[] = new float[maxColumn];
            headerwidths[0] = 2;
            headerwidths[1] = 5;
            headerwidths[2] = 14;
            
            int index = 3;                        
            for(int j=0; j<intDateOfMonth; j++)
            {                    
                headerwidths[index] = 2.5f;  
                index++;                    
            }
            headerwidths[index] = 3;
            

            // create table
            datatable = new Table(maxColumn);
            datatable.setPadding(1);
            datatable.setSpacing(1);
            datatable.setWidths(headerwidths);
            datatable.setWidth(100);
            datatable.setDefaultRowspan(2);
            datatable.setDefaultVerticalAlignment(Cell.ALIGN_MIDDLE);
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.setBackgroundColor(summaryColor);                
            
            // data no urut, payroll number and employee
            datatable.addCell(new Phrase("No", font1));
            datatable.addCell(new Phrase("Payroll", font1));
            datatable.addCell(new Phrase("Employee", font1));
            
            
            datatable.setDefaultColspan(intDateOfMonth);
            datatable.setDefaultRowspan(1);            
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.addCell(new Phrase("Sickness Period", font1));                        
            
            // data total night shift
            datatable.setDefaultRowspan(2);
            datatable.setDefaultColspan(1);
            datatable.setBackgroundColor(summaryColor);                
            datatable.setDefaultVerticalAlignment(Cell.ALIGN_MIDDLE);
            datatable.addCell(new Phrase("Total", font1));            

            // detail data every date of month
            
             // untuk tanggal pertama dalam period yang dipilih
            datatable.setDefaultColspan(1);
            datatable.setDefaultRowspan(1);
            datatable.setBackgroundColor(summaryColor);                
            datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
            datatable.addCell(new Phrase(""+(startDatePeriod), font1)); 
            
            for(int itDetail=0; itDetail<intDateOfMonth-1; itDetail++)
            {
                if(startDatePeriod==intDateOfMonth){
                    startDatePeriod = 1;
                    datatable.setDefaultColspan(1);
                    datatable.setDefaultRowspan(1);
                    datatable.setBackgroundColor(summaryColor);                
                    datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
                    datatable.addCell(new Phrase(""+(startDatePeriod), font1)); 
                }
                else {
                    startDatePeriod = startDatePeriod + 1;
                    datatable.setDefaultColspan(1);
                    datatable.setDefaultRowspan(1);
                    datatable.setBackgroundColor(summaryColor);                
                    datatable.setDefaultHorizontalAlignment(Cell.ALIGN_CENTER);
                    datatable.addCell(new Phrase(""+(startDatePeriod), font1)); 
                    
                }               
            }

            // this is the end of the table header
            datatable.endHeaders();
        }
        catch(Exception e)
        {
            System.out.println("Exception on createHeaderDetail : "+e.toString());
        }
        return datatable;
    }
    
}
