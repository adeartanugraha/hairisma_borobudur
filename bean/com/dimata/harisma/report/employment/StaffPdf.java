/* Generated by Together */
/*
 * EmpPresencePdf.java
 * @author gedhy
 * @version 1.0
 * Created on July 13, 2002, 09:10 PM
 */

package com.dimata.harisma.report.employment;

/* package java */
import com.lowagie.text.Cell;
import com.lowagie.text.PageSize;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Element;
import com.lowagie.text.Phrase;
import com.lowagie.text.Font;
import com.lowagie.text.DocumentException;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Chunk;
import com.lowagie.text.Table;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Document;
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
import com.dimata.harisma.session.employee.*;

public class StaffPdf extends HttpServlet {

    /* declaration constant */
    public static Color blackColor = new Color(0,0,0);
    public static Color whiteColor = new Color(255,255,255);
    public static Color titleColor = new Color(200,200,200);
    public static Color summaryColor = new Color(240,240,240);
    public static String formatDate  = "MMM dd, yyyy";
    public static String formatNumber = "#,###";

    /* setting some fonts in the color chosen by the user */
    public static Font fontHeader = new Font(Font.TIMES_NEW_ROMAN, 12, Font.BOLD, blackColor);
    public static Font fontTitle = new Font(Font.TIMES_NEW_ROMAN, 10, Font.NORMAL, blackColor);
    public static Font fontContent = new Font(Font.TIMES_NEW_ROMAN, 10, Font.NORMAL, blackColor);

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

        /* setting some constant */
        String currText[] = {"(IRD)","(US$)"};

        /* creating the document object */
        Document document = new Document(PageSize.A4, 40, 40, 108, 108);

		/* creating an OutputStream */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {

            /* creating an instance of the writer */
            PdfWriter writer = PdfWriter.getInstance(document, baos);

            /* get data from session */
            String year = FRMQueryString.requestString(request, "year");
            String month = FRMQueryString.requestString(request, "month");
            long recapYear = FRMQueryString.requestLong(request, "recapYear");

            Calendar cal = new GregorianCalendar(Integer.parseInt(year), Integer.parseInt(month)-1, 1);
            int maxDate = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
            Date dt = new Date(Integer.parseInt(year) - 1900, Integer.parseInt(month)-1, maxDate);

            cal.add(GregorianCalendar.MONTH, -1);
            int maxDatePrev = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
            Date dtPrev = new Date(cal.get(GregorianCalendar.YEAR) - 1900, cal.get(GregorianCalendar.MONTH), maxDatePrev);

            System.out.println("\tyear-month : " + year + "-" + month);
            Vector listStaffRecapitulationMonthly = new Vector(1, 1);
            SessEmployee sessEmployee = new SessEmployee();
            listStaffRecapitulationMonthly = sessEmployee.getStaffRecapitulationMonthly(Integer.parseInt(month), Integer.parseInt(year));

            //out.print("size = " + listStaffRecapitulationMonthly.size());
            Vector vDept = (Vector) listStaffRecapitulationMonthly.get(0);
            Vector vCountCurr = (Vector) listStaffRecapitulationMonthly.get(1);
            Vector vCountPrev = (Vector) listStaffRecapitulationMonthly.get(2);
              
            /* adding a Header of page, i.e. : title, align and etc */
            HeaderFooter header = new HeaderFooter(new Phrase("STAFF RECAPITULATION\nAs of " + Formater.formatDate(dt, "dd MMMM yyyy"), fontHeader), false);
            header.setAlignment(Element.ALIGN_CENTER);
            header.setBorder(Rectangle.BOTTOM);
            header.setBorderColor(blackColor);
            document.setHeader(header);

            /* opening the document, needed for add something into document */
            document.open();


            for (int i = 0; i < vDept.size(); i++) { 
                int iPrev = Integer.parseInt(String.valueOf(vCountPrev.get(i)));
                int iCurr = Integer.parseInt(String.valueOf(vCountCurr.get(i)));
                int iDiscrepancy = iCurr - iPrev;
                
                /* create header */
                Table tableEmpPresence = getTableHeader(String.valueOf(vDept.get(i)).toUpperCase(), dt, dtPrev);

                /* generate employee attendance report's data */
                Cell cellEmpData = new Cell("");

                cellEmpData = new Cell(new Chunk("Permanent",fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk(String.valueOf(vCountPrev.get(i)),fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk(String.valueOf(vCountCurr.get(i)),fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk(String.valueOf(iDiscrepancy),fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk("",fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk("",fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                //---------------------------------------------------------
                cellEmpData = new Cell(new Chunk("Contract",fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk("",fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk("",fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk("",fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk("",fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk("",fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                //---------------------------------------------------------
                cellEmpData = new Cell(new Chunk("Apprenticeship",fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk("",fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk("",fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk("",fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk("",fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk("",fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                //---------------------------------------------------------
                cellEmpData = new Cell(new Chunk("Daily workers",fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk("",fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk("",fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk("",fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk("",fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk("",fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                //------------------------------------------
                    
                cellEmpData = new Cell(new Chunk("Total",fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk(String.valueOf(vCountPrev.get(i)),fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk(String.valueOf(vCountCurr.get(i)),fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk(String.valueOf(iDiscrepancy),fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk("",fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk("",fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                    
                    /*
                    if (!writer.fitsPage(tableEmpPresence)) {
                        tableEmpPresence.deleteLastRow();
                        i--;
                        document.add(tableEmpPresence);
                        document.newPage();
				        tableEmpPresence = getTableHeader(String.valueOf(vDept.get(i)).toUpperCase(), dt, dtPrev);//getTableHeader();
                    }
                    */
                document.add(tableEmpPresence);
            }


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
    * this method used to create header table
    */
    public static Table getTableHeader(String strDepartment, Date dt, Date dtPrev) throws BadElementException, DocumentException {

           Table tableEmpPresence = new Table(6);
           tableEmpPresence.setCellpadding(1);
           tableEmpPresence.setCellspacing(1);
           tableEmpPresence.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
	       int widthHeader[] = {15, 15, 15, 12, 15, 20};
    	   tableEmpPresence.setWidths(widthHeader);
           tableEmpPresence.setWidth(100);

           Cell cellEmpPresence = new Cell(new Chunk(String.valueOf(strDepartment),fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setColspan(6);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);

           cellEmpPresence = new Cell(new Chunk(" Status",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);
           cellEmpPresence = new Cell(new Chunk(String.valueOf(Formater.formatDate(dtPrev, "dd-MMM-yyyy")),fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);
           cellEmpPresence = new Cell(new Chunk(String.valueOf(Formater.formatDate(dt, "dd-MMM-yyyy")),fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);
           cellEmpPresence = new Cell(new Chunk(" Discrepancy",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);
           cellEmpPresence = new Cell(new Chunk(" Staff Resignation",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);
           cellEmpPresence = new Cell(new Chunk(" Month to Date Turnover",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);

           return tableEmpPresence;
    }

}
