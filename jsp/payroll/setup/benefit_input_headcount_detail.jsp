<%-- 
    Document   : benefit_input_headcount_detail
    Created on : 21-Sep-2017, 10:36:06
    Author     : Gunadi
--%>
<%@page import="com.dimata.harisma.entity.payroll.PstBenefitPeriod"%>
<%@ include file = "../../main/javainit.jsp" %>
<%@page contentType="application/vnd.ms-excel" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    response.setHeader("Content-Disposition","attachment; filename=headcount_detail.xls ");
    long dataBenefitConfigId = FRMQueryString.requestLong(request, "config_id");
    long dataPeriodId = FRMQueryString.requestLong(request, "period_id");
    
    Vector listEmployee = PstBenefitPeriod.getEmployeeList(dataBenefitConfigId, dataPeriodId);
    Vector listPrevEmployee = PstBenefitPeriod.getPreviousEmployeeList(dataBenefitConfigId, dataPeriodId);
    Vector listNewEmployee = PstBenefitPeriod.getNewEmployeeList(dataBenefitConfigId, dataPeriodId);
    Vector listResignedEmployee = PstBenefitPeriod.getResignedEmployeeList(dataBenefitConfigId, dataPeriodId);
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HARISMA - Headcount Detail</title>
        <!-- #EndEditable --> 
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
        <!-- #BeginEditable "styles" --> 
        <link rel="stylesheet" href="<%=approot%>/styles/main.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "stylestab" --> 
        <link rel="stylesheet" href="<%=approot%>/styles/tab.css" type="text/css">
        <!-- #EndEditable -->
        <link rel="stylesheet" href="<%=approot%>/styles/calendar.css" type="text/css">
        <style type="text/css">
            #menu_utama {color: #0066CC; font-weight: bold; padding: 5px 14px; border-left: 1px solid #0066CC; font-size: 14px; background-color: #F7F7F7;}
            #mn_utama {color: #FF6600; padding: 5px 14px; border-left: 1px solid #999; font-size: 14px; background-color: #F5F5F5;}
    
            #tbl0 {padding: 7px;}
            
            #input {padding: 3px; border: 1px solid #CCC; margin: 0px;}
            #btn {
              background: #3498db;
              border: 1px solid #0066CC;
              border-radius: 3px;
              font-family: Arial;
              color: #ffffff;
              font-size: 12px;
              padding: 3px 9px 3px 9px;
            }

            #btn:hover {
              background: #3cb0fd;
              border: 1px solid #3498db;
            }
            #btn1 {
              background: #f27979;
              border: 1px solid #d74e4e;
              border-radius: 3px;
              font-family: Arial;
              color: #ffffff;
              font-size: 12px;
              padding: 3px 9px 3px 9px;
            }

            #btn1:hover {
              background: #d22a2a;
              border: 1px solid #c31b1b;
            }
            .tblStyle {border-collapse: collapse;font-size: 11px;}
            .tblStyle td {padding: 3px 5px; border: 1px solid #CCC;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
            a {text-decoration: none; font-weight: bold; color: #0066CC;}
            a:hover {color: red;}
            
            #titleTd {background-color: #a0d5fb; color: #FFF; padding: 3px 5px; border-left: 1px solid #0066CC;}
            #listPos {background-color: #FFF; border: 1px solid #CCC; padding: 3px;cursor: pointer;margin: 3px 0px;} 
            #position_input { margin: 3px; padding: 5px 7px; color: #FF6600;}
            #payroll_input { margin: 3px; padding: 5px 7px; color: #FF6600;}
            #division_input { margin: 3px; padding: 5px 7px; color: #FF6600;}
            #confirm {background-color: #fad9d9;border: 1px solid #da8383; color: #bf3c3c; padding: 14px 21px;border-radius: 5px;}
            #caption {
                font-size: 12px;
                color: #474747;
                font-weight: bold;
                padding: 2px 0px 3px 0px;
            }
            #divinput {
                margin-bottom: 5px;
                padding-bottom: 5px;
            }
            
        </style>
    </head>
    <table border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td valign="top">
                <div id="caption">Last Month Head Count</div>
                <table border ="1" cellspacing="1" cellpadding="1">
                    <tr>
                        <td>No</td>
                        <td>Payroll Number</td>
                    </tr>
                <%
                    if (listPrevEmployee.size() > 0 && listPrevEmployee != null){
                        for (int i = 0; i < listPrevEmployee.size(); i++){
                            Employee employee = (Employee) listPrevEmployee.get(i);
                %>
                            <tr>
                                <td><%=(i+1)%></td>
                                <td><%=employee.getEmployeeNum()%></td>
                            </tr>
                <%
                        }
                    }
                %>
                </table>
            </td>
            <td></td>
            <td valign="top">
                <div id="caption">This Month Head Count</div>
                <table border ="1" cellspacing="1" cellpadding="1">
                    <tr>
                        <td>No</td>
                        <td>Payroll Number</td>
                    </tr>
                <%
                    if (listEmployee.size() > 0 && listEmployee != null){
                        for (int i = 0; i < listEmployee.size(); i++){
                            Employee employee = (Employee) listEmployee.get(i);
                %>
                            <tr>
                                <td><%=(i+1)%></td>
                                <td><%=employee.getEmployeeNum()%></td>
                            </tr>
                <%
                        }
                    }
                %>
                </table>
            </td>
            <td></td>
            <td valign="top">
                <div id="caption">New This Month</div>
                <table border ="1" cellspacing="1" cellpadding="1">
                    <tr>
                        <td>No</td>
                        <td>Payroll Number</td>
                    </tr>
                <%
                    if (listNewEmployee.size() > 0 && listNewEmployee != null){
                        for (int i = 0; i < listNewEmployee.size(); i++){
                            Employee employee = (Employee) listNewEmployee.get(i);
                %>
                            <tr>
                                <td><%=(i+1)%></td>
                                <td><%=employee.getEmployeeNum()%></td>
                            </tr>
                <%
                        }
                    }
                %>
                </table>
            </td>
            <td></td>
            <td valign="top">
                <div id="caption">Resigned From Last Month</div>
                <table border ="1" cellspacing="1" cellpadding="1">
                    <tr>
                        <td>No</td>
                        <td>Payroll Number</td>
                    </tr>
                <%
                    if (listResignedEmployee.size() > 0 && listResignedEmployee != null){
                        for (int i = 0; i < listResignedEmployee.size(); i++){
                            Employee employee = (Employee) listResignedEmployee.get(i);
                %>
                            <tr>
                                <td><%=(i+1)%></td>
                                <td><%=employee.getEmployeeNum()%></td>
                            </tr>
                <%
                        }
                    }
                %>
                </table>
            </td>

        </tr>
    </table>
                                  
    </body>
</html>
