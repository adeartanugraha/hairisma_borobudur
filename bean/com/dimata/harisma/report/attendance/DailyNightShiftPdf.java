/*
 * DailyNightShiftPdf.java
 *
 * Created on June 1, 2004, 5:58 PM
 */

package com.dimata.harisma.report.attendance;

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
import com.dimata.harisma.entity.admin.*;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.form.search.*;
import com.dimata.harisma.session.attendance.*;


/**
 *
 * @author  gedhy
 */
public class DailyNightShiftPdf extends HttpServlet {
    
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
        Document document = new Document(PageSize.A4, 30, 30, 50, 50);

        /* creating an OutputStream */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {

            /* creating an instance of the writer */
            PdfWriter writer = PdfWriter.getInstance(document, baos);

            /* get data from session */
            HttpSession sessEmpPresence = request.getSession(true);
            Vector vctDailyPresence = null;
            try{
	        vctDailyPresence = (Vector)sessEmpPresence.getValue("NIGHT_SHIFT_DAILY");
            }catch(Exception e){
                System.out.println("Exc : "+e.toString());
            }

            Date presence = new Date();
            Department department = new Department();
            Vector listPresence = new Vector(1,1);
            if(vctDailyPresence != null && vctDailyPresence.size()==3){
                try{
	            	presence = (Date) vctDailyPresence.get(0);
	                long oidDepartment = Long.parseLong((String)vctDailyPresence.get(1));
	                department = (Department)PstDepartment.fetchExc(oidDepartment);
	                listPresence = (Vector)vctDailyPresence.get(2);
                }catch(Exception e){
                 	System.out.println("get List : "+e.toString());
                }
            }
             
            /* adding a Header of page, i.e. : title, align and etc */
            HeaderFooter header = new HeaderFooter(new Phrase("NIGHT SHIFT REPORT PER " + (Formater.formatDate(presence,"MMMM dd, yyyy").toUpperCase()) +
                				  "\nDEPARTMENT : "+ department.getDepartment().toUpperCase(), fontHeader), false);

            header.setAlignment(Element.ALIGN_LEFT);  
            header.setBorder(Rectangle.BOTTOM);
            header.setBorderColor(blackColor);
            document.setHeader(header);
             
            /* opening the document, needed for add something into document */
            document.open();


            /* create header */
            Table tableEmpPresence = getTableHeader();

            /* generate employee attendance report's data */
            Cell cellEmpData = new Cell("");
             
            if(listPresence!=null && listPresence.size()>0)
            {
                for (int i = 0; i < listPresence.size(); i++) 
                {                    
                    Vector itemNightShift = (Vector)listPresence.get(i);
                     
                    cellEmpData = new Cell(new Chunk(String.valueOf(itemNightShift.get(0)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                    cellEmpData = new Cell(new Chunk(String.valueOf(itemNightShift.get(1)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
                    

                    /* -------------- schedule --------------*/
                    cellEmpData = new Cell(new Chunk(String.valueOf(itemNightShift.get(2)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                    cellEmpData = new Cell(new Chunk(String.valueOf(itemNightShift.get(3)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                    cellEmpData = new Cell(new Chunk(String.valueOf(itemNightShift.get(4)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

					
                    
                    /* -------------- actual --------------*/                    
                    cellEmpData = new Cell(new Chunk(String.valueOf(itemNightShift.get(5)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                    cellEmpData = new Cell(new Chunk(String.valueOf(itemNightShift.get(6)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);
				
                    cellEmpData = new Cell(new Chunk(String.valueOf(itemNightShift.get(7)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                    
                    /* -------------- difference --------------*/    
                    cellEmpData = new Cell(new Chunk(String.valueOf(itemNightShift.get(8)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                    cellEmpData = new Cell(new Chunk(String.valueOf(itemNightShift.get(9)),fontContent));
                    cellEmpData.setHorizontalAlignment(Cell.ALIGN_LEFT);
                    cellEmpData.setVerticalAlignment(Cell.ALIGN_MIDDLE);
                    cellEmpData.setBackgroundColor(whiteColor);
                    cellEmpData.setBorder(Rectangle.LEFT | Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
                    tableEmpPresence.addCell(cellEmpData);

                    if (!writer.fitsPage(tableEmpPresence)) {
                        tableEmpPresence.deleteLastRow();
                        i--;
                        document.add(tableEmpPresence);
                        document.newPage();
                        tableEmpPresence = getTableHeader();
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
    * this method used to create header table
    */
    public static Table getTableHeader() throws BadElementException, DocumentException {

           Table tableEmpPresence = new Table(10);
           tableEmpPresence.setCellpadding(1);
           tableEmpPresence.setCellspacing(1);
           tableEmpPresence.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
	       int widthHeader[] = {5, 25, 8, 8, 10, 8, 10, 10, 8, 8};
    	   tableEmpPresence.setWidths(widthHeader);
           tableEmpPresence.setWidth(100);

           Cell cellEmpPresence = new Cell(new Chunk(" NO.",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_LEFT);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setRowspan(2);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);

           cellEmpPresence = new Cell(new Chunk(" EMPLOYEE",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_LEFT);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setRowspan(2);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);

           cellEmpPresence = new Cell(new Chunk(" SCHEDULE",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setColspan(3);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);

           cellEmpPresence = new Cell(new Chunk(" ACTUAL",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setColspan(3);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);

           cellEmpPresence = new Cell(new Chunk(" DIFFERENCE",fontContent));
           cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_CENTER);
           cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
           cellEmpPresence.setColspan(2);
           cellEmpPresence.setRowspan(1);
           cellEmpPresence.setBackgroundColor(summaryColor);
         	tableEmpPresence.addCell(cellEmpPresence);


            cellEmpPresence = new Cell(new Chunk(" SYMBOL",fontContent));
            cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellEmpPresence.setColspan(1);
            cellEmpPresence.setRowspan(1);
            cellEmpPresence.setBackgroundColor(summaryColor);
          	tableEmpPresence.addCell(cellEmpPresence);

            cellEmpPresence = new Cell(new Chunk(" TIME IN",fontContent));
            cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellEmpPresence.setColspan(1);
            cellEmpPresence.setRowspan(1);
            cellEmpPresence.setBackgroundColor(summaryColor);
          	tableEmpPresence.addCell(cellEmpPresence);

            cellEmpPresence = new Cell(new Chunk(" TIME OUT",fontContent));
            cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellEmpPresence.setColspan(1);
            cellEmpPresence.setRowspan(1);
            cellEmpPresence.setBackgroundColor(summaryColor);
          	tableEmpPresence.addCell(cellEmpPresence);



            cellEmpPresence = new Cell(new Chunk(" TIME IN",fontContent));
            cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellEmpPresence.setColspan(1);
            cellEmpPresence.setRowspan(1);
            cellEmpPresence.setBackgroundColor(summaryColor);
          	tableEmpPresence.addCell(cellEmpPresence);

            cellEmpPresence = new Cell(new Chunk(" DURATION",fontContent));
            cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellEmpPresence.setColspan(1);
            cellEmpPresence.setRowspan(1);
            cellEmpPresence.setBackgroundColor(summaryColor);
          	tableEmpPresence.addCell(cellEmpPresence);

            cellEmpPresence = new Cell(new Chunk(" TIME OUT",fontContent));
            cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellEmpPresence.setColspan(1);
            cellEmpPresence.setRowspan(1);
            cellEmpPresence.setBackgroundColor(summaryColor);
          	tableEmpPresence.addCell(cellEmpPresence);



            cellEmpPresence = new Cell(new Chunk(" IN",fontContent));
            cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellEmpPresence.setColspan(1);
            cellEmpPresence.setRowspan(1);
            cellEmpPresence.setBackgroundColor(summaryColor);
          	tableEmpPresence.addCell(cellEmpPresence);

            cellEmpPresence = new Cell(new Chunk(" OUT",fontContent));
            cellEmpPresence.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cellEmpPresence.setHorizontalAlignment(Cell.ALIGN_LEFT);
            cellEmpPresence.setColspan(1);
            cellEmpPresence.setRowspan(1);
            cellEmpPresence.setBackgroundColor(summaryColor);
          	tableEmpPresence.addCell(cellEmpPresence);

            return tableEmpPresence;
    }
    
}
