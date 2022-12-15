<%-- 
    Document   : up_fix_stock_cuti
    Created on : May 1, 2020, 1:22:37 PM
    Author     : gndiw
--%>

<%@page import="com.dimata.harisma.entity.attendance.PstLlStockTaken"%>
<%@page import="com.dimata.harisma.entity.attendance.PstAlStockTaken"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.dimata.harisma.entity.leave.SpecialUnpaidLeaveTaken"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.harisma.entity.leave.PstSpecialUnpaidLeaveTaken"%>
<%@page import="com.dimata.harisma.entity.employee.Employee"%>
<%@page import="com.dimata.harisma.entity.employee.PstEmployee"%>
<%@page import="com.dimata.util.Formater"%>
<%@page import="org.apache.poi.hssf.usermodel.HSSFCell"%>
<%@page import="org.apache.poi.hssf.usermodel.HSSFRow"%>
<%@page import="org.apache.poi.hssf.usermodel.HSSFSheet"%>
<%@page import="org.apache.poi.hssf.usermodel.HSSFWorkbook"%>
<%@page import="org.apache.poi.poifs.filesystem.POIFSFileSystem"%>
<%@page import="java.io.ByteArrayInputStream"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="com.dimata.util.blob.TextLoader"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <table>
        <%
            TextLoader uploader = new TextLoader();
            FileOutputStream fOut = null;
            ByteArrayInputStream inStream = null;
            uploader.uploadText(config, request, response);
            Object obj = uploader.getTextFile("file");
            byte byteText[] = null;
            byteText = (byte[]) obj;
            inStream = new ByteArrayInputStream(byteText);
            
            POIFSFileSystem fs = new POIFSFileSystem(inStream);

            
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            int numOfSheets = wb.getNumberOfSheets();
            for (int i=0; i<numOfSheets ; i++) {
                int r = 0;
                
                HSSFSheet sheet = (HSSFSheet)wb.getSheetAt(i);
                int rows = sheet.getPhysicalNumberOfRows();  
                for (r=1; r < rows; r++) {
                    try{ 
                        HSSFRow row = sheet.getRow(r);
                        int cells = 0;
                        cells = row.getPhysicalNumberOfCells();
                        String empNum = "";
                        double EO = 0;
                        for (int c = 0; c <= cells; c++){ 
                            HSSFCell cell  = row.getCell((short) c);
                            String value = "";
                            if(cell!=null){
                                switch (cell.getCellType())
	                        {
	                            case HSSFCell.CELL_TYPE_NUMERIC :
	                                //value = "NUMERIC value=" + cell.getNumericCellValue();
	                                EO = cell.getNumericCellValue();
	                                break;
	                            case HSSFCell.CELL_TYPE_STRING :
	                                //value = "STRING value=" + cell.getStringCellValue();
	                                empNum = String.valueOf(cell.getStringCellValue());                                        
	                            default :
                                        value = String.valueOf(cell.getStringCellValue()!=null?cell.getStringCellValue():"");
                                        ;
	                        }
                            }
                        }
                        try {
                            Employee emp = PstEmployee.getEmployeeByNum(empNum);
                                String whereUL = PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_SCHEDULED_ID]+" = 504404576546961452 "
                                        + " AND "+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]+" BETWEEN '2020-03-19 00:00:00' AND '2020-04-18 00:00:00'"
                                        + " AND "+PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_EMPLOYEE_ID]+" = "+emp.getOID();
                                Vector listUl = PstSpecialUnpaidLeaveTaken.list(0, 0, whereUL, PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_TAKEN_DATE]);
                                long leaveId = 0;
                                int listSLDelete = 0;
                                if (listUl.size()>0){
                                        for (int u = 0; u < listUl.size(); u++){
                                            SpecialUnpaidLeaveTaken sl = (SpecialUnpaidLeaveTaken) listUl.get(u);
                                            if (EO > 0){
                                                if (leaveId == 0){
                                                    leaveId = sl.getLeaveApplicationId();
                                                }
                                                Calendar calendar = Calendar.getInstance();
                                                calendar.setTime(sl.getTakenDate());
                                                for (int st = 0; st < (int) sl.getTakenQty(); st ++){
                                                    String day = ""+calendar.get(Calendar.DATE);
                                                    %><tr>
                                                        <td>
                                                            UPDATE hr_emp_schedule SET D<%=day%> = '504404576547229909' WHERE EMPLOYEE_ID = '<%=emp.getOID()%>' AND PERIOD_ID = '504404732399465276';
                                                        </td>
                                                    </tr><%
                                                    EO--;
                                                    if (EO == 0 && (st+1) < (int) sl.getTakenQty()){
                                                        %><tr>
                                                            <td>
                                                                UPDATE hr_special_unpaid_leave_taken SET TAKEN_QTY = '<%=((int) sl.getTakenQty()- (st+1))%>', TAKEN_FINNISH_DATE = '<%=Formater.formatDate(calendar.getTime(), "yyyy-MM-dd")+" 00:00:00"%>'  WHERE SPECIAL_UNPAID_LEAVE_TAKEN_ID = '<%=sl.getOID()%>';
                                                            </td>
                                                        </tr><%
                                                    } else if ((st+1) == (int) sl.getTakenQty() || (st+1) == (int) sl.getTakenQty())  {

                                                        listSLDelete++;
                                                        %><tr>
                                                            <td>
                                                                DELETE FROM hr_special_unpaid_leave_taken  WHERE SPECIAL_UNPAID_LEAVE_TAKEN_ID = '<%=sl.getOID()%>';
                                                            </td>
                                                        </tr><%

                                                        String whereCheckAl = PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_LEAVE_APPLICATION_ID]+"="+sl.getLeaveApplicationId();
                                                        String whereCheckLl = PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LEAVE_APPLICATION_ID]+"="+sl.getLeaveApplicationId();
                                                        String whereCheckSl = PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_LEAVE_APLICATION_ID]+"="+sl.getLeaveApplicationId();
                                                        Vector listAL = PstAlStockTaken.list(0, 0, whereCheckAl, "");
                                                        Vector listLl = PstLlStockTaken.list(0, 0, whereCheckLl, "");
                                                        Vector listSl = PstSpecialUnpaidLeaveTaken.list(0, 0, whereCheckSl, "");
                                                        if (listAL.isEmpty() && listLl.isEmpty() && (listSl.size() - listSLDelete) == 0){
                                                             %><tr>
                                                                    <td>
                                                                        DELETE FROM hr_leave_application  WHERE LEAVE_APPLICATION_ID = '<%=sl.getLeaveApplicationId()%>';
                                                                    </td>
                                                                </tr><%
                                                        }

                                                    } else if (leaveId != sl.getLeaveApplicationId()){
                                                        String whereCheckAl = PstAlStockTaken.fieldNames[PstAlStockTaken.FLD_LEAVE_APPLICATION_ID]+"="+sl.getLeaveApplicationId();
                                                        String whereCheckLl = PstLlStockTaken.fieldNames[PstLlStockTaken.FLD_LEAVE_APPLICATION_ID]+"="+sl.getLeaveApplicationId();
                                                        String whereCheckSl = PstSpecialUnpaidLeaveTaken.fieldNames[PstSpecialUnpaidLeaveTaken.FLD_LEAVE_APLICATION_ID]+"="+sl.getLeaveApplicationId();
                                                        Vector listAL = PstAlStockTaken.list(0, 0, whereCheckAl, "");
                                                        Vector listLl = PstLlStockTaken.list(0, 0, whereCheckLl, "");
                                                        Vector listSl = PstSpecialUnpaidLeaveTaken.list(0, 0, whereCheckSl, "");
                                                        if (listAL.isEmpty() && listLl.isEmpty() && (listSl.size() - listSLDelete) == 0){
                                                             %><tr>
                                                                    <td>
                                                                        DELETE FROM hr_leave_application  WHERE LEAVE_APPLICATION_ID = '<%=sl.getLeaveApplicationId()%>';
                                                                    </td>
                                                                </tr><%
                                                        }
                                                    }
                                                    calendar.add(Calendar.DATE, 1);
                                                }


                                            } else {
                                                break;
                                            }
                                        }
                                }
                        } catch (Exception exc){}
                        
                    } catch (Exception exc){}
                }
            }
            
        %>
        </table>
    </body>
</html>
