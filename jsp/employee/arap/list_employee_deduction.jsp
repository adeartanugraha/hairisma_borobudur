<%-- 
    Document   : hutang kariawan
    Created on : April 17, 2015, 3:56:51 PM
    Author     : Priska
--%>
<%@page import="com.dimata.harisma.entity.report.ArApAging"%>
<%@page import="com.dimata.harisma.entity.arap.ArApEmpDeduction"%>
<%@page import="com.dimata.harisma.entity.payroll.PayPeriod"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayPeriod"%>
<%@page import="com.dimata.harisma.form.arap.FrmArApPayment"%>
<%@page import="com.dimata.harisma.entity.arap.ArApPayment"%>
<%@page import="java.text.DecimalFormatSymbols"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.dimata.harisma.entity.arap.PstArApItem"%>
<%@page import="com.dimata.harisma.entity.arap.ArApItem"%>
<%@page import="com.dimata.harisma.entity.payroll.CurrencyType"%>
<%@page import="com.dimata.harisma.entity.payroll.PstCurrencyType"%>
<%@page import="com.dimata.harisma.entity.payroll.PayComponent"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPayComponent"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.harisma.entity.arap.ArApMain"%>
<%@page import="com.dimata.harisma.entity.arap.PstArApMain"%>
<%@page import="com.dimata.harisma.form.arap.FrmArApMain"%>
<%@page import="com.dimata.gui.jsp.ControlLine"%>
<%@page import="com.dimata.harisma.form.arap.CtrlArApMain"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.system.entity.PstSystemProperty"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.harisma.entity.admin.AppObjInfo"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.harisma.entity.attendance.I_Atendance"%>
<%
            /*
             * Page Name  		:  arApMain.jsp
             * Created on 		:  [date] [time] AM/PM
             *
             * @author  		: Priska_20150417
             * @version  		: -
             */

            /*******************************************************************
             * Page Description 	: [project description ... ]
             * Imput Parameters 	: [input parameter ...]
             * Output 			: [output ...]
             *******************************************************************/
%>
<%@ page language = "java" %>
<!-- package java -->
<%@ page import = "java.util.*" %>
<!-- package dimata -->
<%@ page import = "com.dimata.util.*" %>
<!-- package qdep -->
<%@ page import = "com.dimata.gui.jsp.*" %>
<%@ page import = "com.dimata.qdep.form.*" %>
<!--package harisma -->
<%@ page import = "com.dimata.harisma.entity.employee.*" %>
<%@ page import = "com.dimata.harisma.form.employee.*" %>
<%@ page import = "com.dimata.harisma.form.locker.*" %>
<%@ page import = "com.dimata.harisma.entity.admin.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.*" %>
<%@ page import = "com.dimata.harisma.entity.masterdata.location.Location" %>
<%@ page import = "com.dimata.harisma.entity.locker.*" %>
<%@ page import = "com.dimata.harisma.form.search.*" %>
<%@ page import = "com.dimata.harisma.session.employee.SessEmployeePicture" %>
<%@page import = "com.dimata.harisma.form.masterdata.FrmKecamatan" %>

<%@ include file = "../../main/javainit.jsp" %>
<% int appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_EMPLOYEE, AppObjInfo.G2_DATABANK, AppObjInfo.OBJ_DATABANK);%>
<%@ include file = "../../main/checkuser.jsp" %>

<%!    public String drawList(Vector objectClass) {
        ControlList ctrlist = new ControlList();
        ctrlist.setAreaWidth("80%");
        ctrlist.setListStyle("listgen");
        ctrlist.setTitleStyle("listgentitle");
        ctrlist.setCellStyle("listgensell");
        ctrlist.setHeaderStyle("listgentitle");
        ctrlist.addHeader("No", "10%");
        ctrlist.addHeader("Emp Number", "20%");
        ctrlist.addHeader("Name", "20%");
        ctrlist.addHeader("Department", "10%");
        ctrlist.addHeader("Amount", "10%");
        ctrlist.addHeader("Description", "50%");

        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        Vector lstLinkData = ctrlist.getLinkData();
        ctrlist.setLinkPrefix("javascript:cmdEdit('");
        ctrlist.setLinkSufix("')");
        ctrlist.reset();
        int index = -1;

         DecimalFormat df = (DecimalFormat) DecimalFormat.getCurrencyInstance();
                        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
                        dfs.setCurrencySymbol("");
                        dfs.setMonetaryDecimalSeparator(',');
                        dfs.setGroupingSeparator('.');
                        df.setDecimalFormatSymbols(dfs);
        for (int i = 0; i < objectClass.size(); i++) {
            ArApEmpDeduction arApEmpDeduction = (ArApEmpDeduction) objectClass.get(i);
            Vector rowx = new Vector();
            
            rowx.add(""+(i+1));
            rowx.add(""+arApEmpDeduction.getEmpNum());
            rowx.add(""+arApEmpDeduction.getEmpName());
            rowx.add(""+arApEmpDeduction.getDepartment());
            rowx.add(arApEmpDeduction.getAmount()!=0?"Rp. " + df.format(arApEmpDeduction.getAmount()) : "-");
            rowx.add(arApEmpDeduction.getDescription());
            
            lstData.add(rowx);
            lstLinkData.add(String.valueOf(arApEmpDeduction.getArapMainId()));
        }
        return ctrlist.draw(index);
    }

%>


<%
            int iCommand = FRMQueryString.requestCommand(request);
            int start = FRMQueryString.requestInt(request, "start");
            int prevCommand = FRMQueryString.requestInt(request, "prev_command");
            long oidArapMain = FRMQueryString.requestLong(request, "hidden_arapMain_id");
            
            long periodeId = FRMQueryString.requestLong(request, "periodId");
            long companyId = FRMQueryString.requestLong(request, FrmArApMain.fieldNames[FrmArApMain.FRM_COMPANY_ID]);
            long deductionComp = FRMQueryString.requestLong(request, FrmArApMain.fieldNames[FrmArApMain.FRM_COMPONENT_DEDUCTION_ID]);
            
            I_Atendance attdConfig = null;
    try {
        attdConfig = (I_Atendance) (Class.forName(PstSystemProperty.getValueByName("ATTENDANCE_CONFIG")).newInstance());
    } catch (Exception e) {
        System.out.println("Exception : " + e.getMessage());
        System.out.println("Please contact your system administration to setup system property: ATTENDANCE_CONFIG ");
    }
            /*variable declaration*/
            int recordToGet = 50;
            String msgString = "";
            int iErrCode = FRMMessage.NONE;
            PayPeriod payPeriod2 = new PayPeriod();
            try{
                payPeriod2 = PstPayPeriod.fetchExc(periodeId);
            } catch (Exception e){
                System.out.printf("period null");
            }
            
            String whereClause = "  hai.`arap_item_status` = 0 AND he.COMPANY_ID = " + companyId ;
            String whereDeduct = "  he.COMPANY_ID = " + companyId ;
            if (deductionComp > 0 ){
                whereClause = whereClause + " AND ham.`component_deduction_id` = " + deductionComp ;
            }
            if (periodeId > 0){
            whereClause = whereClause + " AND (hai.DUE_DATE BETWEEN \"" + Formater.formatDate(payPeriod2.getStartDate(), "yyyy-MM-dd HH:mm:ss") + "\" AND \"" + Formater.formatDate(payPeriod2.getEndDate(), "yyyy-MM-dd 23:59:59") + "\" )";
            //whereDeduct = whereDeduct + " AND (hai.DUE_DATE BETWEEN \"" + Formater.formatDate(payPeriod2.getStartDate(), "yyyy-MM-dd HH:mm:ss") + "\" AND \"" + Formater.formatDate(payPeriod2.getEndDate(), "yyyy-MM-dd 23:59:59") + "\" )";
            
            }
            String orderClause = "";

            CtrlArApMain ctrlArApMain = new CtrlArApMain(request);
            ControlLine ctrLine = new ControlLine();
            Vector listArApMain = new Vector(1, 1);
            /*switch statement */
            iErrCode = ctrlArApMain.action(iCommand, oidArapMain);
            /* end switch*/
            FrmArApMain frmArApMain = ctrlArApMain.getForm();
            /*count list All Position*/
            
            if (deductionComp > 0){
            listArApMain = PstArApMain.listDeductionPayroll(0, 0, whereClause, "");
                       }
            String whereDeduc = PstArApMain.listDeductionPayrollDistinct(0, 0, whereDeduct, "") ;
            if (whereDeduc.length() > 2){
             whereDeduc = whereDeduc.substring(0, (whereDeduc.length()-2));
               
            }
            int vectSize = listArApMain.size();

            ArApMain arApMain = ctrlArApMain.getArApMain();
            msgString = ctrlArApMain.getMessage();
            
            long exportpayroll = 0 ;
            if (iCommand == Command.POST){
                exportpayroll = PstArApMain.ExportPayroll(listArApMain, periodeId, companyId);
            }
            
            if ((iCommand == Command.SAVE) && (iErrCode == FRMMessage.NONE)) {
                oidArapMain = arApMain.getOID();
            }

            if ((iCommand == Command.FIRST || iCommand == Command.PREV)
                    || (iCommand == Command.NEXT || iCommand == Command.LAST)) {
                start = ctrlArApMain.actionList(iCommand, start, vectSize, recordToGet);
            }
            
            if (listArApMain.size() < 1 && start > 0) {
                if (vectSize - recordToGet > recordToGet) {
                    start = start - recordToGet;   //go to Command.PREV
                } else {
                    start = 0;
                    iCommand = Command.FIRST;
                    prevCommand = Command.FIRST; //go to Command.FIRST
                }
                listArApMain = PstArApMain.listDeductionPayroll(0, 0, whereClause, "");
            }

            session.putValue("listItem", listArApMain);
            

%>
<html><!-- #BeginTemplate "/Templates/main.dwt" -->
    <head>
        <!-- #BeginEditable "doctitle" -->
        <title>HARISMA - ArApMain</title>
        <script language="JavaScript">


            function cmdAdd(){
                document.frmArApMain.hidden_arapMain_id.value="0";
                document.frmArApMain.command.value="<%=Command.ADD%>";
                document.frmArApMain.prev_command.value="<%=prevCommand%>";
                document.frmArApMain.action="arap_main.jsp";
                document.frmArApMain.submit();
            }

            function cmdAsk(oidArapMain){
                document.frmArApMain.hidden_arapMain_id.value=oidArapMain;
                document.frmArApMain.command.value="<%=Command.ASK%>";
                document.frmArApMain.prev_command.value="<%=prevCommand%>";
                document.frmArApMain.action="arap_main.jsp";
                document.frmArApMain.submit();
            }

            function cmdConfirmDelete(oidArapMain){
                document.frmArApMain.hidden_arapMain_id.value=oidArapMain;
                document.frmArApMain.command.value="<%=Command.DELETE%>";
                document.frmArApMain.prev_command.value="<%=prevCommand%>";
                document.frmArApMain.action="arap_main_list.jsp";
                document.frmArApMain.submit();
            }
            function cmdSave(){
                document.frmArApMain.command.value="<%=Command.SAVE%>";
                document.frmArApMain.prev_command.value="<%=prevCommand%>";
                document.frmArApMain.action="arap_main.jsp";
                document.frmArApMain.submit();
            }
            function cmdPost(){
                document.frmArApPayment.command.value="<%=Command.POST%>";
                document.frmArApPayment.prev_command.value="<%=prevCommand%>";
                document.frmArApPayment.action="arap_main.jsp";
                document.frmArApPayment.submit();
            }

            function cmdEdit(oidArapMain){
                document.frmArApMain.hidden_arapMain_id.value=oidArapMain;
                document.frmArApMain.command.value="<%=Command.EDIT%>";
                document.frmArApMain.prev_command.value="<%=prevCommand%>";
                document.frmArApMain.action="arap_main.jsp";
                document.frmArApMain.submit();
            }

            function cmdPost(oidArapMain){
                document.frmArApMain.hidden_arapMain_id.value=oidArapMain;
                document.frmArApMain.command.value="<%=Command.POST%>";
                document.frmArApMain.prev_command.value="<%=prevCommand%>";
                document.frmArApMain.action="list_employee_deduction.jsp";
                document.frmArApMain.submit();
            }


            function cmdCancel(oidArapMain){
                document.frmArApMain.hidden_arapMain_id.value=oidArapMain;
                document.frmArApMain.command.value="<%=Command.EDIT%>";
                document.frmArApMain.prev_command.value="<%=prevCommand%>";
                document.frmArApMain.action="arap_main.jsp";
                document.frmArApMain.submit();
            }

            function cmdBack(){
                document.frmArApMain.command.value="<%=Command.BACK%>";
                document.frmArApMain.action="arap_main_list.jsp";
                document.frmArApMain.submit();
            }

            function cmdListFirst(){
                document.frmArApMain.command.value="<%=Command.FIRST%>";
                document.frmArApMain.prev_command.value="<%=Command.FIRST%>";
                document.frmArApMain.action="arap_main.jsp";
                document.frmArApMain.submit();
            }

            function cmdListPrev(){
                document.frmArApMain.command.value="<%=Command.PREV%>";
                document.frmArApMain.prev_command.value="<%=Command.PREV%>";
                document.frmArApMain.action="arap_main.jsp";
                document.frmArApMain.submit();
            }

            function cmdListNext(){
                document.frmArApMain.command.value="<%=Command.NEXT%>";
                document.frmArApMain.prev_command.value="<%=Command.NEXT%>";
                document.frmArApMain.action="arap_main.jsp";
                document.frmArApMain.submit();
            }

            function cmdListLast(){
                document.frmArApMain.command.value="<%=Command.LAST%>";
                document.frmArApMain.prev_command.value="<%=Command.LAST%>";
                document.frmArApMain.action="arap_main.jsp";
                document.frmArApMain.submit();
            }
            
            function cmdUpdateCDDS(){
                document.frmArApMain.command.value="<%=String.valueOf(Command.GOTO)%>";
                document.frmArApMain.action="list_employee_deduction.jsp";
                document.frmArApMain.submit();
            }
            
            function cmdSearch(){
                document.frmArApMain.command.value="<%=Command.LIST%>";
                document.frmArApMain.prev_command.value="<%=Command.LIST%>";
                document.frmArApMain.action="list_employee_deduction.jsp";
                document.frmArApMain.submit();
            }

        function cmdExportExcel(){
                 
                var linkPage = "<%=approot%>/employee/arap/Arap_Excel/listArap_xls.jsp";    
                var newWin = window.open(linkPage,"attdReportDaily","height=700,width=990,status=yes,toolbar=yes,menubar=no,resizable=yes,scrollbars=yes,location=yes"); 			
                 newWin.focus();
 
            }

            function fnTrapKD(){
                //alert(event.keyCode);
                switch(event.keyCode) {
                    case <%=LIST_PREV%>:
                            cmdListPrev();
                        break;
                    case <%=LIST_NEXT%>:
                            cmdListNext();
                        break;
                    case <%=LIST_FIRST%>:
                            cmdListFirst();
                        break;
                    case <%=LIST_LAST%>:
                            cmdListLast();
                        break;
                    default:
                        break;
                    }
                }

                //-------------- script control line -------------------
                function MM_swapImgRestore() { //v3.0
                    var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
                }

                function MM_preloadImages() { //v3.0
                    var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
                        var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
                            if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
                    }

                    function MM_findObj(n, d) { //v4.0
                        var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
                            d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
                        if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
                        for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
                        if(!x && document.getElementById) x=document.getElementById(n); return x;
                    }

                    function MM_swapImage() { //v3.0
                        var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
                            if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
                    }

        </script>
        <!-- #EndEditable -->
        <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
        <!-- #BeginEditable "styles" -->
        <link rel="stylesheet" href="../../styles/main.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "stylestab" -->
        <link rel="stylesheet" href="../../styles/tab.css" type="text/css">
        <!-- #EndEditable -->
        <!-- #BeginEditable "headerscript" -->
        <SCRIPT language=JavaScript>
                    function hideObjectForEmployee(){
                    }

                    function hideObjectForLockers(){
                    }

                    function hideObjectForCanteen(){
                    }

                    function hideObjectForClinic(){
                    }

                    function hideObjectForMasterdata(){
                    }

                    function cmdSearchEmp(){

                            window.open("<%=approot%>/employee/search/SearchMasterFlow.jsp?formName=frmArApMain&empPathId=<%=frmArApMain.fieldNames[frmArApMain.FRM_FIELD_EMPLOYEE_ID]%>", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
                    }
                    cmdSearchEmpPay()
                    function cmdSearchEmpPay(){

                            window.open("<%=approot%>/employee/search/SearchMasterFlow.jsp?formName=FrmArApPayment&empPathId=<%=FrmArApPayment.fieldNames[FrmArApPayment.FRM_FIELD_EMPLOYEE_ID]%>", null, "height=550,width=600, status=yes,toolbar=no,menubar=no,location=no, scrollbars=yes");       
                    }
        </SCRIPT>
        <!-- #EndEditable -->
    </head>

    <body <%=noBack%> bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('<%=approot%>/images/BtnNewOn.jpg')">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" bgcolor="#F9FCFF" >
             <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%> 
           <%@include file="../../styletemplate/template_header.jsp" %>
            <%}else{%>
            <tr>
                <td ID="TOPTITLE" background="<%=approot%>/images/HRIS_HeaderBg3.jpg" width="100%" height="54">
                    <!-- #BeginEditable "header" -->
                    <%@ include file = "../../main/header.jsp" %>
                    <!-- #EndEditable -->
                </td>
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
                                        <td height="20">
                                            <font color="#FF6600" face="Arial"><strong>
                                                    <!-- #BeginEditable "contenttitle" -->
                                                    Employee Deduction &gt; ArApMain<!-- #EndEditable -->
                                                </strong></font>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr>
                                                    <td  style="background-color:<%=bgColorContent%>; "> 
                                                        <table width="100%" border="0" cellspacing="1" cellpadding="1" >
                                                            <tr>
                                                                <td valign="top">
                                                                    <table style="border:1px solid <%=garisContent%>" width="100%" border="0" cellspacing="1" cellpadding="1" class="tablecolor">
                                                                        <tr>
                                                                            <td valign="top">
                                                                                <!-- #BeginEditable "content" -->
                                                                                <form name="frmArApMain" method ="post" action="">
                                                                                    <input type="hidden" name="command" value="<%=iCommand%>">
                                                                                    <input type="hidden" name="vectSize" value="<%=vectSize%>">
                                                                                    <input type="hidden" name="start" value="<%=start%>">
                                                                                    <input type="hidden" name="prev_command" value="<%=prevCommand%>">
                                                                                    <input type="hidden" name="hidden_arapMain_id" value="<%=oidArapMain%>">
                                                                                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                                                       
                                                                                        <tr>
                                                                                            <td>&nbsp;
                                                                                            </td>
                                                                                        </tr>
                                                                                        <tr align="left" valign="top">
                                                                                            <td height="8" valign="middle" colspan="3">
                                                                                                <table width="100%" border="0" cellspacing="2" cellpadding="2">
                                                                                                    <tr>
                                                                                                        <td class="listtitle"><%=oidArapMain == 0 ? "Add" : "Edit"%> ArApMain</td>
                                                                                                    </tr>
                                                                                                    <tr>
                                                                                                        <td height="100%">
                                                                                                            <table border="0" cellspacing="2" cellpadding="2" width="50%">
                                                                                                                <tr align="left" valign="top">
                                                                                                                    <td valign="top" width="17%">&nbsp;</td>
                                                                                                                    <td width="83%" class="comment">*)entry required </td>
                                                                                                                </tr>
                                                                                                                
                                                                                                                <tr align="left">
                                                                                                                                
                                                                                                                                <td  width="31%"  valign="top">
                                                                                                                                         <%
                                                                                                                                    Vector comp_value = new Vector(1, 1);
                                                                                                                                    Vector comp_key = new Vector(1, 1);
                                                                                                                                    Vector listComp = PstCompany.list(0, 0, "", " COMPANY ");
                                                                                                                                    //comp_value.add(""+0);
                                                                                                                                    //comp_key.add("select");
                                                                                                                                    for (int i = 0; i < listComp.size(); i++) {
                                                                                                                                        Company div = (Company) listComp.get(i);
                                                                                                                                        comp_key.add(div.getCompany());
                                                                                                                                        comp_value.add(String.valueOf(div.getOID()));
                                                                                                                                    }
                                                                                                                                    
                                                                                                                                    %>
                                                                                                                                    <!-- Ari_20110903
                                                                                                                                        Menambah Link menuju employee_mutation.jsp { -->
                                                                                                                                    <%= ControlCombo.draw(frmArApMain.fieldNames[frmArApMain.FRM_COMPANY_ID], "formElemen", null, "" + companyId, comp_value, comp_key,"onChange=\"javascript:cmdUpdateCDDS()\"")%>
                                                                                                                   
                                                                                                                                    </td>
                                                                                                                                    
                                                                                                                </tr>
                                                                                                                
                                                                                                                <tr align="left">
                                                                                                                                
                                                                                                                                <td  width="31%"  valign="top">
                                                                                                                                        <%
                                                                                                                            Vector periodValue = new Vector(1, 1);
                                                                                                                            Vector periodKey = new Vector(1, 1);
                                                                                                                            // salkey.add(" ALL DEPARTMET");
                                                                                                                            //deptValue.add("0");
                                                                                                                            Vector listPeriod = PstPayPeriod.list(0, 0, "", "START_DATE DESC");
                                                                                                                            //   Vector listPeriod = PstPeriod.list(0, 0, "", "START_DATE DESC");
                                                                                                                            for (int r = 0; r < listPeriod.size(); r++) {
                                                                                                                                PayPeriod payPeriod = (PayPeriod) listPeriod.get(r);
                                                                                                                                //  Period period = (Period) listPeriod.get(r);
                                                                                                                                periodValue.add("" + payPeriod.getOID());
                                                                                                                                periodKey.add(payPeriod.getPeriod());
                                                                                                                            }
                                                                                                                                        %> <%=ControlCombo.draw("periodId", null, "" + periodeId, periodValue, periodKey, "onChange=\"javascript:cmdUpdateCDDS()\"")%>
                                                                                                                                                
                                                                                                                                    </td>
                                                                                                                                    
                                                                                                                </tr>
                                                                                                                                                                                         <tr align="left">
                                                                                                                                
                                                                                                                                <td  width="31%"  valign="top">
                                                                                                                                   <%
                                                                                                                                        Vector CompDecd_value = new Vector(1, 1);
                                                                                                                                        Vector CompDecd_key = new Vector(1, 1);
                                                                                                                                        
                                                                                                                                        String whereClauseDeduction ="";
                                                                                                                                              whereClauseDeduction  = " pc."+PstPayComponent.fieldNames[PstPayComponent.FLD_COMP_TYPE] + " =  " + PstPayComponent.TYPE_DEDUCTION ;
                                                                                                                                     if (periodeId > 0){
                                                                                                                                        whereClauseDeduction = whereClauseDeduction + " AND pc.COMPONENT_ID IN  (" + whereDeduc+" ) " ; 
                                                                                                                                        whereClauseDeduction = whereClauseDeduction + " AND ham.`arap_main_id` = hai.`arap_main_id` AND hai.DUE_DATE < " + "\"" + Formater.formatDate(payPeriod2.getEndDate(), "yyyy-MM-dd HH:mm:ss") + "\"";
                                                                                                                                     }
                                                                                                                                        Vector listCompDecd = PstPayComponent.listInMain(0, 0, whereClauseDeduction, "");
                                                                                                                                        for (int i = 0; i < listCompDecd.size(); i++) {
                                                                                                                                            PayComponent CompDecd = (PayComponent) listCompDecd.get(i);
                                                                                                                                            CompDecd_key.add(CompDecd.getCompName());
                                                                                                                                            CompDecd_value.add(String.valueOf(CompDecd.getOID()));
                                                                                                                                        }
                                                                                                                                     
                                                                                                                                    %>
                                                                                                                                    <%=  ControlCombo.draw(FrmArApMain.fieldNames[FrmArApMain.FRM_COMPONENT_DEDUCTION_ID], "formElemen", null, "" + deductionComp, CompDecd_value, CompDecd_key, "onChange=\"javascript:cmdUpdateCDDS()\"") %> * <%= frmArApMain.getErrorMsg(frmArApMain.FRM_COMPONENT_DEDUCTION_ID) %>
                                                                                                                                             
                                                                                                                                    </td>
                                                                                                                                    
                                                                                                                </tr>
                                                                                                                
                                                                                                                
                                                                                                                
                                                                                                            </table >
                                                                                                        </td>
                                                                                                    </tr>
                                                                                                               <tr>
                                                                                                                    <td width="24"><a href="javascript:cmdSearch()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1003','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1003" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" ></a><a href="javascript:cmdSearch()" class="command" style="text-decoration:none">SEARCH</a></td>
                                                                                                                    
                                                                                                                </tr>
                                                                                                            <% if (listArApMain.size() > 0) { %>
                                                                                                           
                                                                                                           
                                                                                                        <td>List <%= payPeriod2.getPeriod() %></td>
 
                                                                                                        <tr align="left" valign="top">
                                                                                                            <td height="22" valign="middle" colspan="3">
                                                                                                                <%= drawList(listArApMain)%>
                                                                                                            </td>
                                                                                                        </tr>
                                                                                                        <tr>
                                                                                                                    <td width="24"><a href="javascript:cmdPost()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1003','','<%=approot%>/images/BtnNewOn.jpg',1)"><img name="Image1003" border="0" src="<%=approot%>/images/BtnNew.jpg" width="24" height="24" ></a><a href="javascript:cmdPost()" class="command" style="text-decoration:none">Export Payroll</a></td>
                                                                                                                    
                                                                                                        </tr>
                                                                                                        <tr> 
                                                                                                            <td width="16"><a href="javascript:cmdExportExcel()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image10','','<%=approot%>/images/excel.png',1)" id="aSearch"><img name="Image10" border="0" src="<%=approot%>/images/excel.png" width="24" height="24" alt="Export Excel"></a>
                                                                                                            <img src="<%=approot%>/images/spacer.gif" width="4" height="1"><a href="javascript:cmdExportExcel()">Export Excel Rincian</a></td>
                                                                                                            
                                                                                                        </tr>
                                                                                                        <% } %>
                                                                                                        <tr>
                                                                                                    </tr>
                                                                                                </table>
                                                                                            </td>
                                                                                        </tr>
                                                                                    </table>
                                                                                </form>
                                                                                                        
                                                                                                        
                                                                                                        
                                                                                                        
                                                                                                        
                                                                                                        
                                                                                                        
                                                                                                        
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
                    </table>
                </td>
            </tr>
             <%if(headerStyle && !verTemplate.equalsIgnoreCase("0")){%>
            <tr>
                            <td valign="bottom">
                               <!-- untuk footer -->
                                <%@include file="../../footer.jsp" %>
                            </td>
                            
            </tr>
            <%}else{%>
            <tr> 
                <td colspan="2" height="20" > <!-- #BeginEditable "footer" --> 
      <%@ include file = "../../main/footer.jsp" %>
                <!-- #EndEditable --> </td>
            </tr>
            <%}%>
        </table>
    </body>
    <!-- #BeginEditable "script" -->
    <script language="JavaScript">
                //var oBody = document.body;
                //var oSuccess = oBody.attachEvent('onkeydown',fnTrapKD);
    </script>
    <!-- #EndEditable -->
    <!-- #EndTemplate --></html>

