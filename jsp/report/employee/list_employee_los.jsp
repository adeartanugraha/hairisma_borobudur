<%-- 
    Document   : list_employee_los
    Created on : Aug 25, 2018, 8:53:08 AM
    Author     : dimata005
--%>

<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.harisma.entity.payroll.PayPeriod"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayPeriod"%>
<%@ include file = "../../main/javainit.jsp" %>
<% int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_MASTERDATA, AppObjInfo.G2_MD_COMPANY, AppObjInfo.OBJ_PUBLIC_HOLIDAY); %>
<%@ include file = "../../main/checkuser.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    boolean isSecretaryLogin = (positionType >= PstPosition.LEVEL_SECRETARY) ? true : false;
    long hrdDepartmentOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_HRD_DEPARTMENT)));
    boolean isHRDLogin = hrdDepartmentOid == departmentOid ? true : false;
    long edpSectionOid = Long.parseLong(String.valueOf(PstSystemProperty.getPropertyLongbyName(OID_EDP_SECTION)));
    boolean isEdpLogin = edpSectionOid == sectionOfLoginUser.getOID() ? true : false;
    boolean isGeneralManager = positionType == PstPosition.LEVEL_GENERAL_MANAGER ? true : false;
%>
<%
    int iCommand = FRMQueryString.requestCommand(request);
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
    
    
%>
<!DOCTYPE html>
<html>
    <head>
        <script language="JavaScript">
            function cmdXLS(){
                document.frmlistlos.command.value="<%=String.valueOf(Command.LIST)%>";
                document.frmlistlos.action="<%=approot%>/EmployeeCustXLS";
                document.frmlistlos.submit();
            }
            function cmdPDF(){
                document.frmlistlos.command.value="<%=String.valueOf(Command.LIST)%>";
                document.frmlistlos.action="<%=approot%>/EmployeeCustPDF";
                document.frmlistlos.submit();
            }
            function cmdUpdateDiv(){
                document.frmlistlos.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmlistlos.action="list_employee_los.jsp";
                document.frmlistlos.submit();
            }
            function cmdUpdateDep(){
                document.frmlistlos.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmlistlos.action="list_employee_los.jsp";
                document.frmlistlos.submit();
            }
            function cmdUpdatePos(){
                document.frmlistlos.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmlistlos.action="list_employee_los.jsp";
                document.frmlistlos.submit();
            }
        </script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>List Employee Mutation</title>
        <!-- #EndEditable -->
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <!-- #BeginEditable "styles" -->
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <!-- #EndEditable --> <!-- #BeginEditable "stylestab" -->
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
    </head>
<body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
        <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%> 
        <%@include file="../../styletemplate/template_header.jsp" %>
        <%} else {%>
        <tr>
          <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54">
                <!-- #BeginEditable "header" -->
                <%@ include file = "../../main/header.jsp" %>
                <!-- #EndEditable --> </td>
        </tr>
        <tr>
            <td  bgcolor="#9BC1FF" height="15" ID="MAINMENU" valign="middle"> <!-- #BeginEditable "menumain" -->
                <%@ include file = "../../main/mnmain.jsp" %>
                <!-- #EndEditable --> </td>
        </tr>
        <tr>
            <td  bgcolor="#9BC1FF" height="10" valign="middle">
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td align="left"><img src="<%=approot%>/images/harismaMenuLeft1.jpg" width="8" height="8"></td>
                        <td align="center" background="<%=approot%>/images/harismaMenuLine1.jpg" width="100%"><img src="<%=approot%>/images/harismaMenuLine1.jpg" width="8" height="8"></td>
                        <td align="right"><img src="<%=approot%>/images/harismaMenuRight1.jpg" width="8" height="8"></td>
                    </tr>
                </table>
            </td>
        </tr>
        <%}%>
        <tr>
          <td width="88%" valign="top" align="left">
             <table width="100%" border="0" cellspacing="3" cellpadding="2">
                <tr>
                   <td width="100%">
                      <table width="100%" border="0" cellspacing="0" cellpadding="0">
                         <tr>
                            <td height="20"> <font color="#FF6600" face="Arial"><strong> <!-- #BeginEditable "contenttitle" -->
                                   List Employee Mutation <!-- #EndEditable -->
                                </strong></font> </td>
                                </tr>
                                <tr>
                                  <td>
                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td  style="background-color:<%=bgColorContent%>; ">
                                          <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                            <tr>
                                              <td valign="top">
                                                <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tabbg">
                                                  <tr>
                                                    <td valign="top"> <!-- #BeginEditable "content" -->
                                                      <form name="frmlistlos" method ="post" action="">
                                                        <input type="hidden" name="command" value="<%=iCommand%>">
                                                           <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                              <tr align="left" valign="top">
                                                                <td height="8"  colspan="3">
                                                                    <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                        <tr> 
                                                                            <td width="1%" align="right" nowrap> <div align=left>Company</div>  </td>
                                                                            <td width="30%" nowrap="nowrap">:
                                                                                <%
                                                                                    Vector comp_value = new Vector(1, 1);
                                                                                    Vector comp_key = new Vector(1, 1);
                                                                                    String whereCompany="";
                                                                                    if (!(isHRDLogin || isEdpLogin || isGeneralManager)){
                                                                                        whereCompany = PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]+"='"+ emplx.getCompanyId()+"'";
                                                                                    } else{
                                                                                        comp_value.add("0");
                                                                                        comp_key.add("select ...");                                            
                                                                                    }
                                                                                    Vector listComp = PstCompany.list(0, 0, whereCompany, " COMPANY ");
                                                                                    for (int i = 0; i < listComp.size(); i++) {
                                                                                            Company comp = (Company) listComp.get(i);
                                                                                            comp_key.add(comp.getCompany());
                                                                                            comp_value.add(String.valueOf(comp.getOID()));
                                                                                    }
                                                                                    
                                                                                    if(companyId==0){
                                                                                       //srcOvertime = new SrcOvertime();
                                                                                       oidDivision = 0;
                                                                                       departmentName=0;
                                                                                       sectionName=0;
                                                                                    }
                                                                                %>
                                                                                <%= ControlCombo.draw("companyId", "formElemen", null, "" + companyId, comp_value, comp_key, "onChange=\"javascript:cmdUpdateDiv()\"")%> 
                                                                            </td>
                                                                        </tr>
                                                                        <tr> 
                                                                            <td width="1%" align="right" nowrap> <div align=left>Division</div></td>
                                                                            <td width="30%" nowrap="nowrap">:
                                                                                <%
                                                                                    Vector div_value = new Vector(1, 1);
                                                                                    Vector div_key = new Vector(1, 1);
                                                                                    String whereDivision ="";
                                                                                    if (!(isHRDLogin || isEdpLogin || isGeneralManager)){
                                                                                        whereDivision = PstDivision.fieldNames[PstDivision.FLD_DIVISION_ID]+"='"+ emplx.getDivisionId()+"'";
                                                                                        oidDivision = emplx.getDivisionId();
                                                                                    } else{
                                                                                        div_value.add("0");
                                                                                        div_key.add("select ...");                                            
                                                                                    }
                                                                                    Vector listDiv = PstDivision.list(0, 0, whereDivision, " DIVISION ");
                                                                                    for (int i = 0; i < listDiv.size(); i++) {
                                                                                            Division div = (Division) listDiv.get(i);
                                                                                            div_key.add(div.getDivision());
                                                                                            div_value.add(String.valueOf(div.getOID()));
                                                                                    }
                                                                                    //update by satrya 2013-08-13
                                                                                    //jika user memilih select kembali
                                                                                    if(oidDivision==0){
                                                                                        departmentName =0;
                                                                                        sectionName=0;  
                                                                                    }
                                                                                %>
                                                                                <%= ControlCombo.draw("oidDivision", "formElemen", null, "" + oidDivision, div_value, div_key, "onChange=\"javascript:cmdUpdateDep()\"")%>  
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td width="6%" align="right" nowrap><div align="left">Department </div></td>
                                                                            <td width="30%" nowrap="nowrap">:
                                                                                <%  
                                                                                    Vector dept_value = new Vector(1, 1);
                                                                                    Vector dept_key = new Vector(1, 1);
                                                                                    Vector listDept = new Vector(1, 1);
                                                                                    String whereDept ="";
                                                                                    if (isHRDLogin || isEdpLogin || isGeneralManager){
                                                                                        whereDept = PstDepartment.TBL_HR_DEPARTMENT+"."+PstDepartment.fieldNames[PstDepartment.FLD_DIVISION_ID] + "=" + oidDivision;
                                                                                        dept_value.add("0");
                                                                                        dept_key.add("select ...");                        
                                                                                    } else {
                                                                                        dept_value.add("0");
                                                                                        dept_key.add("select ...");
                                                                                    }
                                                                                    listDept = PstDepartment.list(0, 0, whereDept, "DEPARTMENT");
                                                                                    for (int i = 0; i < listDept.size(); i++) {
                                                                                        Department dept = (Department) listDept.get(i);
                                                                                        dept_key.add(dept.getDepartment());
                                                                                        dept_value.add(String.valueOf(dept.getOID()));
                                                                                    }


                                                                                    //update by satrya 2013-08-13
                                                                                    //jika user memilih select kembali
                                                                                    if(departmentName==0){
                                                                                        sectionName=0; 
                                                                                    }
                                                                                %>

                                                                                    <%= ControlCombo.draw("department", "formElemen", null, "" + departmentName, dept_value, dept_key, "onChange=\"javascript:cmdUpdatePos()\"")%>
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td width="6%" align="right" nowrap><div align="left">Section </div></td>
                                                                            <td width="30%" nowrap="nowrap">:
                                                                                <%  
                                                                                    Vector sec_value = new Vector(1, 1);
                                                                                    Vector sec_key = new Vector(1, 1);
                                                                                    sec_value.add("0");
                                                                                    sec_key.add("select ...");
                                                                                    //Vector listSec = PstSection.list(0, 0, "", " DEPARTMENT_ID, SECTION ");
                                                                                    String strWhereSec = PstSection.fieldNames[PstSection.FLD_DEPARTMENT_ID] + "=" + departmentName;
                                                                                    Vector listSec = PstSection.list(0, 0, strWhereSec, " SECTION ");
                                                                                    for (int i = 0; i < listSec.size(); i++) {
                                                                                            Section sec = (Section) listSec.get(i);
                                                                                            sec_key.add(sec.getSection());
                                                                                            sec_value.add(String.valueOf(sec.getOID()));
                                                                                    }
                                                                                %>

                                                                                    <%= ControlCombo.draw("section", "formElemen", null, "" + sectionName, sec_value, sec_key, " onkeydown=\"javascript:fnTrapKD()\"")%> 
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td width="6%" align="right" nowrap><div align="left">LOS </div></td>
                                                                            <td width="30%" nowrap="nowrap">:
                                                                                <%  
                                                                                    //month
                                                                                    Vector month_keyVal = new Vector(1, 1);
                                                                                    for (int s = 0; s < 13; s++) {
                                                                                        month_keyVal.add(""+s);
                                                                                    }

                                                                                    //year
                                                                                    Vector year_keyVal = new Vector(1, 1);
                                                                                    for (int s = 0; s < 100; s++) {
                                                                                        year_keyVal.add(""+s);
                                                                                    }
                                                                                %>

                                                                                <%= ControlCombo.draw("losYearFrom", "formElemen", null, "" + losYearFrom , year_keyVal, year_keyVal, "")%> year &nbsp;
                                                                                <%= ControlCombo.draw("losMonthFrom", "formElemen", null, "" + losMonthFrom , month_keyVal, month_keyVal, "")%> month &nbsp;
                                                                                to &nbsp;
                                                                                <%= ControlCombo.draw("losYearTo", "formElemen", null, "" + losYearTo , year_keyVal, year_keyVal, "")%> year &nbsp;
                                                                                <%= ControlCombo.draw("losMonthTo", "formElemen", null, "" + losMonthTo , month_keyVal, month_keyVal, "")%> month &nbsp;
                                                                            </td>
                                                                        </tr>
                                                                        <tr> 
                                                                            <td height="13" width="0%">&nbsp;</td>
                                                                            <td width="39%"><a href="javascript:cmdXLS()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a>
                                                                           <img src="<%=approot%>/images/spacer.gif" width="6" height="1">
                                                                          <a href="javascript:cmdXLS()">Export Excel</a>&nbsp;
                                                                            <a href="javascript:cmdPDF()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/BtnSearchOn.jpg',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/BtnSearch.jpg" width="24" height="24" alt="Search Employee"></a>
                                                                           <img src="<%=approot%>/images/spacer.gif" width="6" height="1">
                                                                          <a href="javascript:cmdPDF()">Export PDF</a></td>  
                                                                        </tr>
                                                                    </table>
                                                                </td>
                                                              </tr>
                                                           </table>
                                                      </form>
                                                    </td>
                                                  </tr>
                                                </table>
                                                   <!-- #EndEditable --> 
                                              </td>
                                            </tr>
                                          </table>
                                        </td>
                                      </tr>
                                    </table>
                                 </td>
                              </tr>
                              <tr>
                                  <td>&nbsp; </td>
                              </tr>
                           </table>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <%if (headerStyle && !verTemplate.equalsIgnoreCase("0")) {%>
        <tr>
            <td valign="bottom">
                <!-- untuk footer -->
                <%@include file="../../footer.jsp" %>
            </td>

        </tr>
        <%} else {%>
        <tr> 
            <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
                <%@ include file = "../../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
        </tr>
        <%}%>
    </table>
</body>
</html>

