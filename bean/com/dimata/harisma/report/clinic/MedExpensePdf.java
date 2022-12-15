/* Generated by Together */


package com.dimata.harisma.report.clinic;

import com.lowagie.text.Cell;
import com.lowagie.text.PageSize;
import com.lowagie.text.DocumentException;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Element;
import com.lowagie.text.Chunk;
import com.lowagie.text.Table;
import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import java.sql.*;
import java.awt.Color;
import java.awt.Point;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Date;

import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.html.HtmlWriter;
import com.lowagie.text.Font;
import com.dimata.util.*;
import com.dimata.harisma.entity.search.*;
import com.dimata.harisma.form.search.*;
import com.dimata.harisma.entity.clinic.*;
import com.dimata.harisma.entity.masterdata.*;
import com.dimata.harisma.entity.employee.*;
import com.dimata.harisma.session.clinic.*;

public class MedExpensePdf extends HttpServlet
{
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    
    /** Destroys the servlet.
     */
    public void destroy() {
        
    }


 	// setting the color values
     public static Color border = new Color(0x00, 0x00, 0x00);
     
     // setting some fonts in the color chosen by the user
     public static Font fontMainHeader = new Font(Font.TIMES_NEW_ROMAN, 10, Font.UNDERLINE, border);
     public static Font fontHeader = new Font(Font.TIMES_NEW_ROMAN, 10, Font.NORMAL, border);
     public static Font fontType = new Font(Font.TIMES_NEW_ROMAN, 9, Font.BOLD, border);
     public static Font fontLsContent = new Font(Font.TIMES_NEW_ROMAN, 9);
     public static Font fontLsHeader = new Font(Font.TIMES_NEW_ROMAN, 9);
     public static Font fontNotAvb = new Font(Font.TIMES_NEW_ROMAN, 12, Font.BOLDITALIC, border);


    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {

        Color bgColor = new Color(200,200,200);
        Rectangle rectangle = new  Rectangle( 20, 20, 20, 20);
        rectangle.rotate();
        //Document document = new Document(rectangle);
        Document document = new Document(PageSize.A4, 30, 30, 50, 50);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try{

        //step2.2: creating an instance of the writer
        PdfWriter writer = PdfWriter.getInstance(document, baos);
         
        // step 3.1: adding some metadata to the document
        document.addSubject("This is a subject.");
        document.addSubject("This is a subject two.");

        document.open();

     	/* get data from session */
        Vector listAll = new Vector();
        HttpSession sess = request.getSession(true);
        try{
         	listAll = (Vector)sess.getValue("MED_EXPENSE");
        }catch(Exception e){
            System.out.println("Exc : "+e.toString());
            listAll = new Vector();
        }

        Hashtable listData = new Hashtable(1,1);
        Vector listExpense = new Vector(1,1);
        int start = 0;
        int person = 0;
        if((listAll != null) && (listAll.size()==2)){
              listData = (Hashtable)listAll.get(0);
              person = Integer.parseInt(""+listAll.get(1));
              Iterator iter = listData.keySet().iterator();
              String key = "";
              if(iter.hasNext())
                key = (String)iter.next();
              listExpense = (Vector)listData.get(key);

              Table list = getHeader(key);

              long expTypeId = 0;														
		   	  double sumAmount = 0;
			  double sumDiscRp = 0;
			  double sumTotal = 0;
		      int sumPerson = 0;
			  double allAmount = 0;
			  double allDiscRp = 0;
			  double allTotal = 0;
			  int allPerson = 0;

              int minus = 0;
              Cell lisContent = new Cell();
	          for(int ls = 0;ls< listExpense.size();ls++)
	          {
	            Vector temp = (Vector)listExpense.get(ls);
				MedicalType medicalType = (MedicalType)temp.get(0);
                MedExpenseType medExpenseType = (MedExpenseType)temp.get(1);
                MedicalRecord expRec = (MedicalRecord)temp.get(2);
                Vector persons = (Vector)temp.get(3);
                int pers = Integer.parseInt(""+persons.get(0));

                if(expTypeId != medicalType.getMedExpenseTypeId()){
					expTypeId = medicalType.getMedExpenseTypeId();
					if(sumTotal != 0){

			            lisContent = new Cell(new Chunk(" SUB TOTAL", fontLsContent));
			            lisContent.setHorizontalAlignment(Element.ALIGN_LEFT);
			       		lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        lisContent.setColspan(2);
			            list.addCell(lisContent);  //col 1-2
	
			            lisContent = new Cell(new Chunk(Formater.formatNumber(sumAmount,"#,###.#"), fontLsContent));
			            lisContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
			            lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
			            list.addCell(lisContent);  //col 3

	
			            lisContent = new Cell(new Chunk(Formater.formatNumber(sumDiscRp,"#,###.#"), fontLsContent));
			            lisContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
			         	lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        lisContent.setColspan(2);
			            list.addCell(lisContent);  //col 5

						lisContent = new Cell(new Chunk(Formater.formatNumber(sumTotal,"#,###.#"), fontLsContent));
			            lisContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
			      		lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
			            list.addCell(lisContent);  //col 6

			            lisContent = new Cell(new Chunk(String.valueOf(sumPerson), fontLsContent));
			            lisContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
			      		lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
			            list.addCell(lisContent);  //col 7

                        allAmount = allAmount + sumAmount;
						allDiscRp = allDiscRp + sumDiscRp;
						allTotal = allTotal + sumTotal;
						allPerson = allPerson + sumPerson;
						sumAmount = 0;
						sumDiscRp = 0;
						sumTotal = 0;
						sumPerson = 0;
                    }

                    if(ls != 0 && minus != 0){
	                    lisContent = new Cell(new Chunk("", fontType));
			            lisContent.setHorizontalAlignment(Element.ALIGN_LEFT);
			      		lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
	                    lisContent.setBorder(Rectangle.TOP);
	                    lisContent.setColspan(7);
			            list.addCell(lisContent);  //col 1-7
                    }

                    lisContent = new Cell(new Chunk(" "+medExpenseType.getType().toUpperCase(), fontType));
		            lisContent.setHorizontalAlignment(Element.ALIGN_LEFT);
		      		lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    lisContent.setBorder(Rectangle.BOTTOM);
                    lisContent.setColspan(7);
		            list.addCell(lisContent);  //col 1-7

                    minus = 0;
                }

                lisContent = new Cell(new Chunk(medicalType.getTypeCode(), fontLsContent));
	            lisContent.setHorizontalAlignment(Element.ALIGN_CENTER);
	       		lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lisContent.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
	            list.addCell(lisContent);  //col 1

                lisContent = new Cell(new Chunk(medicalType.getTypeName(), fontLsContent));
	            lisContent.setHorizontalAlignment(Element.ALIGN_LEFT);
	       		lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lisContent.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
	            list.addCell(lisContent);  //col 2

	            lisContent = new Cell(new Chunk(Formater.formatNumber(expRec.getAmount(),"#,###.#"), fontLsContent));
	            lisContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
	            lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lisContent.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
	            list.addCell(lisContent);  //col 3


                double disc = (expRec.getDiscountInRp()*100)/ expRec.getAmount();

	            //lisContent = new Cell(new Chunk(Formater.formatNumber(expRec.getDiscountInPercent(),"#,###.0"), fontLsContent));
                lisContent = new Cell(new Chunk(Formater.formatNumber(disc,"#,###.0")+"%", fontLsContent));
	            lisContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
	            lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lisContent.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
	            list.addCell(lisContent);  //col 4

	            lisContent = new Cell(new Chunk(Formater.formatNumber(expRec.getDiscountInRp(),"#,###.#"), fontLsContent));
	            lisContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
	         	lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lisContent.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
	            list.addCell(lisContent);  //col 5

				lisContent = new Cell(new Chunk(Formater.formatNumber(expRec.getTotal(),"#,###.#"), fontLsContent));
	            lisContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
	      		lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lisContent.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
	            list.addCell(lisContent);  //col 6

	            lisContent = new Cell(new Chunk(String.valueOf(pers), fontLsContent));
	            lisContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
	      		lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
                lisContent.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
	            list.addCell(lisContent);  //col 7


                sumAmount = sumAmount + expRec.getAmount();
				sumDiscRp = sumDiscRp + expRec.getDiscountInRp();
				sumTotal = sumTotal + expRec.getTotal();
				sumPerson = sumPerson + pers;

                minus = minus + 1;

                if (!writer.fitsPage(list)) {
                    for(int i=0;i<=minus;i++){
                    	list.deleteLastRow();
                    }
                    ls = ls-minus;
                    expTypeId = 0;
                    minus = 0;

                    sumAmount = 0;
					sumDiscRp = 0;
					sumTotal = 0;
					sumPerson = 0;

                    document.add(list);
                    document.newPage();
                    list = getHeader(key);
                }


              }//end for

	          allAmount = allAmount + sumAmount;
			  allDiscRp = allDiscRp + sumDiscRp;
			  allTotal = allTotal + sumTotal;
			  allPerson = allPerson + sumPerson;


              lisContent = new Cell(new Chunk(" SUB TOTAL", fontLsContent));
              lisContent.setHorizontalAlignment(Element.ALIGN_LEFT);
       		  lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
              lisContent.setColspan(2);
              list.addCell(lisContent);  //col 1-2

              lisContent = new Cell(new Chunk(Formater.formatNumber(sumAmount,"#,###.#"), fontLsContent));
              lisContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
              lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
              list.addCell(lisContent);  //col 3

              lisContent = new Cell(new Chunk(Formater.formatNumber(sumDiscRp,"#,###.#"), fontLsContent));
              lisContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
         	  lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
              lisContent.setColspan(2);
              list.addCell(lisContent);  //col 4-5

			  lisContent = new Cell(new Chunk(Formater.formatNumber(sumTotal,"#,###.#"), fontLsContent));
              lisContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
      		  lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
              list.addCell(lisContent);  //col 6

              lisContent = new Cell(new Chunk(String.valueOf(sumPerson), fontLsContent));
              lisContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
      		  lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
              list.addCell(lisContent);  //col 7

              lisContent = new Cell(new Chunk("", fontLsContent));
              lisContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
      		  lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
              lisContent.setBorder(Rectangle.TOP);
              lisContent.setColspan(7);
              list.addCell(lisContent);  //col 1-7

              lisContent = new Cell(new Chunk(" GRAND TOTAL", fontLsContent));
              lisContent.setHorizontalAlignment(Element.ALIGN_LEFT);
       		  lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
              lisContent.setColspan(2);
              list.addCell(lisContent);  //col 1-2

              lisContent = new Cell(new Chunk(Formater.formatNumber(allAmount,"#,###.#"), fontLsContent));
              lisContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
              lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
              list.addCell(lisContent);  //col 3

              lisContent = new Cell(new Chunk("", fontLsContent));
              lisContent.setHorizontalAlignment(Element.ALIGN_LEFT);
              lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
              lisContent.setColspan(2);
              list.addCell(lisContent);  //col 4-5

			  lisContent = new Cell(new Chunk(Formater.formatNumber(allTotal,"#,###.#"), fontLsContent));
              lisContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
      		  lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
              list.addCell(lisContent);  //col 6

              lisContent = new Cell(new Chunk(String.valueOf(allPerson), fontLsContent));
              lisContent.setHorizontalAlignment(Element.ALIGN_RIGHT);
      		  lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
              list.addCell(lisContent);  //col 7

              lisContent = new Cell(new Chunk("", fontLsContent));
              lisContent.setHorizontalAlignment(Element.ALIGN_LEFT);
      		  lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
              lisContent.setBorder(Rectangle.TOP);
              lisContent.setColspan(7);
              list.addCell(lisContent);  //col 1-7
              /*
              lisContent = new Cell(new Chunk("", fontLsContent));
              lisContent.setHorizontalAlignment(Element.ALIGN_LEFT);
      		  lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
              lisContent.setBorderColor(new Color(255,255,255));
              list.addCell(lisContent);  //col 1

              lisContent = new Cell(new Chunk("Total Staff "+person+" persons", fontHeader));
              lisContent.setHorizontalAlignment(Element.ALIGN_LEFT);
      		  lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
              lisContent.setBorderColor(new Color(255,255,255));
              lisContent.setColspan(6);
              list.addCell(lisContent);  //col 2-7 */
              /*
              lisContent = new Cell(new Chunk("", fontLsContent));
              lisContent.setHorizontalAlignment(Element.ALIGN_LEFT);
      		  lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
              lisContent.setBorderColor(new Color(255,255,255));
              list.addCell(lisContent);  //col 1

            //  double average = allTotal/(new Double(person)).doubleValue();
              //String tmpTot = Formater.formatNumber(allTotal,"###");
             // allTotal = Float.parseFloat(tmpTot);
              lisContent = new Cell(new Chunk("Average medical expenses per employee "+Formater.formatNumber(allTotal,"#,###.#") + " / "+person+" = "+Formater.formatNumber((allTotal/person),"#,###.00"), fontHeader));
              lisContent.setHorizontalAlignment(Element.ALIGN_LEFT);
      		  lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
              lisContent.setBorderColor(new Color(255,255,255));
              lisContent.setColspan(6);
              list.addCell(lisContent);  //col 2-7

            /*  lisContent = new Cell(new Chunk("", fontLsContent));
              lisContent.setHorizontalAlignment(Element.ALIGN_LEFT);
      		  lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
              lisContent.setBorderColor(new Color(255,255,255));
              list.addCell(lisContent);  //col 1  */
              /*
              lisContent = new Cell(new Chunk("Melia Bali Hotel", fontHeader));
              lisContent.setHorizontalAlignment(Element.ALIGN_CENTER);
      		  lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
              lisContent.setBorderColor(new Color(255,255,255));
              lisContent.setColspan(2);
              list.addCell(lisContent);  //col 2

              lisContent = new Cell(new Chunk("", fontHeader));
              lisContent.setHorizontalAlignment(Element.ALIGN_CENTER);
      		  lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
              lisContent.setBorderColor(new Color(255,255,255));
              lisContent.setColspan(5);
              list.addCell(lisContent);  //col 3-7

              lisContent = new Cell(new Chunk("", fontLsContent));
              lisContent.setHorizontalAlignment(Element.ALIGN_LEFT);
      		  lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
              lisContent.setBorderColor(new Color(255,255,255));
              lisContent.setColspan(7);
              lisContent.setRowspan(2);
              list.addCell(lisContent);  //col 1-7

             /* lisContent = new Cell(new Chunk("", fontLsContent));
              lisContent.setHorizontalAlignment(Element.ALIGN_LEFT);
      		  lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
              lisContent.setBorderColor(new Color(255,255,255));
              list.addCell(lisContent);  //col 1   */
              /*
              lisContent = new Cell(new Chunk("House Doctor", fontHeader));
              lisContent.setHorizontalAlignment(Element.ALIGN_CENTER);
      		  lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
              lisContent.setBorderColor(new Color(255,255,255));
              lisContent.setColspan(2);
              list.addCell(lisContent);  //col 2

              lisContent = new Cell(new Chunk("", fontHeader));
              lisContent.setHorizontalAlignment(Element.ALIGN_CENTER);
      		  lisContent.setVerticalAlignment(Element.ALIGN_MIDDLE);
              lisContent.setBorderColor(new Color(255,255,255));
              lisContent.setColspan(5);
              list.addCell(lisContent);  //col 3-7
              */


              document.add(list);

         }else{

              int headerInt[] = {100};
              Table tbl = new Table(1);
              tbl.setBorderColor(new Color(255,255,255));
              tbl.setWidth(100);
	          tbl.setWidths(headerInt);
	          tbl.setBorderWidth(1);
	          tbl.setCellpadding(1);
	          tbl.setCellspacing(1);
	
              Cell hcell = new Cell(new Chunk("No Recapitulation Of Medical Expense available", fontNotAvb));
              hcell.setBorderColor(new Color(255,255,255));
              tbl.addCell(hcell);

              document.add(tbl);
          }

         }catch(Exception e){
                System.out.println("Exception e : "+e.toString());
         }

        // step 5: closing the document
        document.close();
        
        // we have written the pdfstream to a ByteArrayOutputStream,
        // now we are going to write this outputStream to the ServletOutputStream
        // after we have set the contentlength (see http://www.lowagie.com/iText/faq.html#msie)
        response.setContentType("application/pdf");
        response.setContentLength(baos.size());
        ServletOutputStream out = response.getOutputStream();
        baos.writeTo(out);
        out.flush();

    }

    /* this method make table header */
    private static Table getHeader(String titleName) throws BadElementException, DocumentException {

		int ctnInt[] = {10,35,15,10,15,15,10};
        Table list = new Table(7);
        list.setBorderColor(new Color(255,255,255));
        list.setWidth(100);
        list.setWidths(ctnInt);
        list.setBorderWidth(1);
        list.setPadding(1);
        list.setSpacing(1);

        Cell hcell = new Cell(new Chunk("RECAPITULATION OF MEDICAL EXPENSES", fontMainHeader));
        hcell.setBorderColor(new Color(255,255,255));
        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
        hcell.setColspan(7);
        list.addCell(hcell);

        hcell = new Cell(new Chunk("PERIODE  : "+titleName, fontHeader));
        hcell.setBorderColor(new Color(255,255,255));
        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
        hcell.setColspan(7);
        list.addCell(hcell);
        /*
        hcell = new Cell(new Chunk("", fontHeader));
        hcell.setBorderColor(new Color(255,255,255));
        hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
        hcell.setColspan(7);
        list.addCell(hcell);
        */
        hcell = new Cell(new Chunk("Report Date : "+Formater.formatDate(new Date(),"dd/MM/yyyy"), fontLsContent));
        hcell.setBorderColor(new Color(255,255,255));
        hcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        hcell.setColspan(7);
        list.addCell(hcell);

        Cell lisHeader = new Cell(new Chunk("DESCRIPTION", fontLsHeader));
        lisHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        lisHeader.setVerticalAlignment(Element.ALIGN_MIDDLE);
        lisHeader.setBackgroundColor(new Color(255,255,255));
        lisHeader.setColspan(2);
        list.addCell(lisHeader);  //col 1-2

        lisHeader = new Cell(new Chunk("AMOUNT", fontLsHeader));
        lisHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        lisHeader.setVerticalAlignment(Element.ALIGN_MIDDLE);
        lisHeader.setBackgroundColor(new Color(255,255,255));
        list.addCell(lisHeader);  //col 3

  		lisHeader = new Cell(new Chunk("DISCOUNT", fontLsHeader));
        lisHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        lisHeader.setVerticalAlignment(Element.ALIGN_MIDDLE);
        lisHeader.setBackgroundColor(new Color(255,255,255));
        lisHeader.setColspan(2);
        list.addCell(lisHeader);  //col 4 - 5

        lisHeader = new Cell(new Chunk("TOTAL", fontLsHeader));
        lisHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        lisHeader.setVerticalAlignment(Element.ALIGN_MIDDLE);
        lisHeader.setBackgroundColor(new Color(255,255,255));
        list.addCell(lisHeader);  //col 6

        lisHeader = new Cell(new Chunk("PERSON", fontLsHeader));
        lisHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
        lisHeader.setVerticalAlignment(Element.ALIGN_MIDDLE);
        lisHeader.setBackgroundColor(new Color(255,255,255));
        list.addCell(lisHeader);  //col 7

        return list;
    }


    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        processRequest(request, response);
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
       processRequest(request, response);
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
}

