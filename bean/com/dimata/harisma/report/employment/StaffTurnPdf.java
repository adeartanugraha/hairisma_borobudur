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
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.session.employee.*;


public class StaffTurnPdf extends HttpServlet {

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
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);

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
            SessEmployee sessEmployee = new SessEmployee();
              
            /* adding a Header of page, i.e. : title, align and etc */
            HeaderFooter header = new HeaderFooter(new Phrase("DETAIL INFORMATION\nTURNOVER REPORT\nAs of " + Formater.formatDate(dt, "dd MMMM yyyy"), fontHeader), false);
            header.setAlignment(Element.ALIGN_CENTER);
            header.setBorder(Rectangle.BOTTOM);
            header.setBorderColor(blackColor);
            document.setHeader(header);

            /* opening the document, needed for add something into document */
            document.open();

            Vector listStaffNewHire = new Vector(1, 1);
            listStaffNewHire = sessEmployee.getStaffNewHire(Integer.parseInt(month), Integer.parseInt(year));

            System.out.println("\tlistStaffNewHire.size() = " + listStaffNewHire.size());

            /* create header */
            Table tableEmpPresence = getTableHeader();

            for (int i = 0; i < listStaffNewHire.size(); i++) {
                Vector temp = (Vector)listStaffNewHire.get(i);
                Employee employee = (Employee)temp.get(0);
                Position position = (Position)temp.get(1);
                

                /* generate employee attendance report's data */
                Cell cellEmpData = new Cell("");

                cellEmpData = new Cell(new Chunk(String.valueOf(i + 1),fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk(String.valueOf(employee.getFullName()),fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk(String.valueOf(position.getPosition()),fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData.setBackgroundColor(whiteColor);
                cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                cellEmpData = new Cell(new Chunk(String.valueOf(Formater.formatDate(employee.getCommencingDate(), "dd-MMM-yyyy")),fontContent));
                cellEmpData.setHorizontalAlignment(Cell.ALIGN_CENTER);
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
            }
            
            document.add(tableEmpPresence);

            /* create header for resign */
            Table tableEmpPresenceResign = getTableHeaderResign();
            
            Vector listStaffResigned = new Vector(1, 1);
            listStaffResigned = sessEmployee.getStaffResigned(Integer.parseInt(month), Integer.parseInt(year));

            System.out.println("\tlistStaffResigned.size() = " + listStaffResigned.size());
            
            for (int i = 0; i < listStaffResigned.size(); i++) {
                Vector temp = (Vector)listStaffResigned.get(i);
                Employee employee = (Employee)temp.get(0);
                Position position = (Position)temp.get(1);
                ResignedReason res = (ResignedReason)temp.get(2);
                

                /* generate employee attendance report's data */
                Cell cellEmpData2 = new Cell("");

                cellEmpData2 = new Cell(new Chunk(String.valueOf(i + 1),fontContent));
                cellEmpData2.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellEmpData2.setBackgroundColor(whiteColor);
                cellEmpData2.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresenceResign.addCell(cellEmpData2);
                cellEmpData2 = new Cell(new Chunk(String.valueOf(employee.getFullName()),fontContent));
                cellEmpData2.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData2.setBackgroundColor(whiteColor);
                cellEmpData2.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresenceResign.addCell(cellEmpData2);
                cellEmpData2 = new Cell(new Chunk(String.valueOf(position.getPosition()),fontContent));
                cellEmpData2.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData2.setBackgroundColor(whiteColor);
                cellEmpData2.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresenceResign.addCell(cellEmpData2);
                cellEmpData2 = new Cell(new Chunk(String.valueOf(Formater.formatDate(employee.getCommencingDate(), "dd-MMM-yyyy")),fontContent));
                cellEmpData2.setHorizontalAlignment(Cell.ALIGN_CENTER);
                cellEmpData2.setBackgroundColor(whiteColor);
                cellEmpData2.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresenceResign.addCell(cellEmpData2);
                cellEmpData2 = new Cell(new Chunk("",fontContent));
                cellEmpData2.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData2.setBackgroundColor(whiteColor);
                cellEmpData2.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresenceResign.addCell(cellEmpData2);
                cellEmpData2 = new Cell(new Chunk(String.valueOf(res.getResignedReason()),fontContent));
                cellEmpData2.setHorizontalAlignment(Cell.ALIGN_LEFT);
                cellEmpData2.setBackgroundColor(whiteColor);
                cellEmpData2.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresenceResign.addCell(cellEmpData2);
                    
                    /*
                    if (!writer.fitsPage(tableEmpPresence)) {
                        tableEmpPresence.deleteLastRow();
                        i--;
                        document.add(tableEmpPresence);
                        document.newPage();
				        tableEmpPresence = getTableHeader(String.valueOf(vDept.get(i)).toUpperCase(), dt, dtPrev);//getTableHeader();
                    }
                    */
            }
            
            document.add(tableEmpPresenceResign);

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
    public static Table getTableHeader() throws BadElementException, DocumentException {

           Table tableEmpPresence = new Table(6);
           tableEmpPresence.setCellpadding(1);
           tableEmpPresence.setCellspacing(1);
           tableEmpPresence.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
	       int widthHeader[] = {5, 20, 20, 12, 15, 10};
    	   tableEmpPresence.setWidths(widthHeader);
           tableEmpPresence.setWidth(100);

           Cell cellEmpPresence = new Cell(new Chunk(" NEW HIRE",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setColspan(6);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);

           cellEmpPresence = new Cell(new Chunk(" NO.",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);
           cellEmpPresence = new Cell(new Chunk(" NAME",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);
           cellEmpPresence = new Cell(new Chunk(" POSITION",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);
           cellEmpPresence = new Cell(new Chunk(" COMM. DATE",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);
           cellEmpPresence = new Cell(new Chunk(" EFFECTIVE DATE",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);
           cellEmpPresence = new Cell(new Chunk(" REMARKS",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);

           return tableEmpPresence;
    }

    /**
    * this method used to create header table
    */
    public static Table getTableHeaderResign() throws BadElementException, DocumentException {

           Table tableEmpPresence = new Table(6);
           tableEmpPresence.setCellpadding(1);
           tableEmpPresence.setCellspacing(1);
           tableEmpPresence.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
	       int widthHeader[] = {5, 20, 20, 12, 15, 10};
    	   tableEmpPresence.setWidths(widthHeader);
           tableEmpPresence.setWidth(100);

           Cell cellEmpPresence = new Cell(new Chunk(" RESIGN",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setColspan(6);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);

           cellEmpPresence = new Cell(new Chunk(" NO.",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);
           cellEmpPresence = new Cell(new Chunk(" NAME",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);
           cellEmpPresence = new Cell(new Chunk(" POSITION",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);
           cellEmpPresence = new Cell(new Chunk(" COMM. DATE",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);
           cellEmpPresence = new Cell(new Chunk(" EFFECTIVE DATE",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);
           cellEmpPresence = new Cell(new Chunk(" REMARKS",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);

           return tableEmpPresence;
    }
}

