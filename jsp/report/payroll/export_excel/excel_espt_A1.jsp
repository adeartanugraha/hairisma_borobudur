<%-- 
    Document   : excel_espt_A1
    Created on : Aug 19, 2019, 9:11:50 AM
    Author     : IanRizky
--%>

<%@page import="com.dimata.harisma.entity.payroll.PayPeriod"%>
<%@page import="com.dimata.harisma.entity.payroll.PstPaySlip"%>
<%@page import="com.dimata.harisma.session.payroll.Pajak"%>
<%@page import="com.dimata.system.entity.PstSystemProperty"%>
<%@page import="com.dimata.harisma.session.payroll.SessESPT"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page contentType="application/x-msexcel" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<%
	response.setHeader("Content-Disposition","attachment; filename=espet_A1_detail.xls ");
	
	int iCommand = FRMQueryString.requestCommand(request);
    long periodId = FRMQueryString.requestLong(request, "inp_period_id");
    long companyId = FRMQueryString.requestLong(request, "company_id");
    long divisionId = FRMQueryString.requestLong(request, "division_id");
    long departmentId = FRMQueryString.requestLong(request, "department_id");
    long sectionId = FRMQueryString.requestLong(request, "inp_section_id");
    long payrollGroupId = FRMQueryString.requestLong(request, "payrollGroupId");
    long search = 0 ;
    try{ search = FRMQueryString.requestLong(request, "search");} catch (Exception e){}
    Vector listEspt = new Vector();
	
	String html = "";
    if (iCommand == Command.LIST){
		String inSalBrutto =  PstSystemProperty.getValueByName("PAYROLL_ESPT_SALARY_BRUTTO_COMP") ;//'TI','ALW28', 'ALW02';         
        inSalBrutto="'"+inSalBrutto.replaceAll(",", "','")+"'";
        String inPPHBrutto =  PstSystemProperty.getValueByName("PAYROLL_ESPT_PPH_COMP");
        inPPHBrutto="'"+inPPHBrutto.replaceAll(",", "','")+"'";
        // String kodePajak =  PstSystemProperty.getValueByName("PAYROLL_ESPT_KODE_PAJAK") ;
        Pajak pajak = new Pajak();
		html = SessESPT.getListEsptA1MonthString(pajak, periodId, divisionId, departmentId, sectionId, inSalBrutto, inPPHBrutto, payrollGroupId);
	}
	
	Vector periodList = PstPaySlip.getYearPeriodListToThisPeriod(periodId);
	
%>
<html>
    <head>
        <style type="text/css">
            #menu_utama {color: #0066CC; font-weight: bold; padding: 5px 14px; border-left: 1px solid #0066CC; font-size: 14px; background-color: #F7F7F7;}
            #mn_utama {color: #FF6600; padding: 5px 14px; border-left: 1px solid #999; font-size: 14px; background-color: #F5F5F5;}
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
            #tdForm {
                padding: 5px;
            }
            .tblStyle {border-collapse: collapse;font-size: 11px;}
            .tblStyle td {padding: 3px 5px; border: 1px solid #CCC;}
            .title_tbl {font-weight: bold;background-color: #DDD; color: #575757;}
        </style>
    </head>
    <body>
        <table width="100%" cellspacing="0" border="1">
			<tbody>
				<tr>
					<td style="text-align: center" rowspan="2">No.</td>
					<td style="text-align: center" rowspan="2">Emp Num</td>
					<td style="text-align: center" rowspan="2">Unit Kerja</td>
					<td style="text-align: center" rowspan="2">Mulai Kerja</td>
					<td style="text-align: center" rowspan="2">Berhenti Kerja</td>
					<td style="text-align: center" rowspan="2">Masa Pajak</td>
					<td style="text-align: center" rowspan="2">Tahun</td>
					<td style="text-align: center" rowspan="2">Pembetulan</td>
					<td style="text-align: center" rowspan="2">Nomor Bukti Potong</td>
					<td style="text-align: center" rowspan="2">Masa Perolehan Awal </td>
					<td style="text-align: center" rowspan="2">Masa Perolehan Akhir</td>
					<td style="text-align: center" rowspan="2">NPWP</td>
					<td style="text-align: center" rowspan="2">NIK</td>
					<td style="text-align: center" rowspan="2">Nama</td>
					<td style="text-align: center" rowspan="2">Alamat</td>
					<td style="text-align: center" rowspan="2">Jenis Kelamin</td>
					<td style="text-align: center" rowspan="2">Status PTKP</td>
					<td style="text-align: center" rowspan="2">Jumlah Tanggungan</td>
					<td style="text-align: center" rowspan="2">Nama Jabatan</td>
					<td style="text-align: center" rowspan="2">WP Luar Negeri</td>
					<td style="text-align: center" rowspan="2">Kode Negara</td>
					<td style="text-align: center" rowspan="2">Kode Pajak</td>
					<td style="text-align: center" colspan="<%=periodList.size()+1%>">Jumlah 1</td>
					<td style="text-align: center" colspan="<%=periodList.size()+1%>">Jumlah 2</td>
					<td style="text-align: center" colspan="<%=periodList.size()+1%>">Jumlah 3</td>
					<td style="text-align: center" colspan="<%=periodList.size()+1%>">Jumlah 4</td>
					<td style="text-align: center" colspan="<%=periodList.size()+1%>">Jumlah 5</td>
					<td style="text-align: center" colspan="<%=periodList.size()+1%>">Jumlah 6</td>
					<td style="text-align: center" colspan="<%=periodList.size()+1%>">Jumlah 7</td>
					<td style="text-align: center" colspan="<%=periodList.size()+1%>">Jumlah 8</td>
					<td style="text-align: center" rowspan="2">Jumlah 9</td>
					<td style="text-align: center" colspan="<%=periodList.size()+1%>">Jumlah 10</td>
					<td style="text-align: center" rowspan="2">Jumlah 11</td>
					<td style="text-align: center" rowspan="2">Jumlah 12</td>
					<td style="text-align: center" rowspan="2">Jumlah 13</td>
					<td style="text-align: center" rowspan="2">Jumlah 14</td>
					<td style="text-align: center" rowspan="2">Jumlah 15</td>
					<td style="text-align: center" rowspan="2">Jumlah 16</td>
					<td style="text-align: center" rowspan="2">Jumlah 17</td>
					<td style="text-align: center" rowspan="2">Jumlah 18</td>
					<td style="text-align: center" rowspan="2">Jumlah 19</td>
					<td style="text-align: center" colspan="<%=periodList.size()+1%>">Jumlah 20</td>
					<td style="text-align: center" rowspan="2">Status Pindah</td>
					<td style="text-align: center" rowspan="2">NPWP Pemotong</td>
					<td style="text-align: center" rowspan="2">Nama Pemotong</td>
					<td style="text-align: center" rowspan="2">Tanggal Bukti Potong</td>
				</tr>
				<tr>
					<%
					for (int idx = 0; idx < periodList.size(); idx++) {
						PayPeriod tmpPayPeriod = (PayPeriod) periodList.get(idx);
						%><td><%=tmpPayPeriod.getPeriod()%></td><%
					}
						%><td>Total</td><%
					%>
					<%
					for (int idx = 0; idx < periodList.size(); idx++) {
						PayPeriod tmpPayPeriod = (PayPeriod) periodList.get(idx);
						%><td><%=tmpPayPeriod.getPeriod()%></td><%
					}
						%><td>Total</td><%
					%>
					<%
					for (int idx = 0; idx < periodList.size(); idx++) {
						PayPeriod tmpPayPeriod = (PayPeriod) periodList.get(idx);
						%><td><%=tmpPayPeriod.getPeriod()%></td><%
					}
						%><td>Total</td><%
					%>
					<%
					for (int idx = 0; idx < periodList.size(); idx++) {
						PayPeriod tmpPayPeriod = (PayPeriod) periodList.get(idx);
						%><td><%=tmpPayPeriod.getPeriod()%></td><%
					}
						%><td>Total</td><%
					%>
					<%
					for (int idx = 0; idx < periodList.size(); idx++) {
						PayPeriod tmpPayPeriod = (PayPeriod) periodList.get(idx);
						%><td><%=tmpPayPeriod.getPeriod()%></td><%
					}
						%><td>Total</td><%
					%>
					<%
					for (int idx = 0; idx < periodList.size(); idx++) {
						PayPeriod tmpPayPeriod = (PayPeriod) periodList.get(idx);
						%><td><%=tmpPayPeriod.getPeriod()%></td><%
					}
						%><td>Total</td><%
					%>
					<%
					for (int idx = 0; idx < periodList.size(); idx++) {
						PayPeriod tmpPayPeriod = (PayPeriod) periodList.get(idx);
						%><td><%=tmpPayPeriod.getPeriod()%></td><%
					}
						%><td>Total</td><%
					%>
					<%
					for (int idx = 0; idx < periodList.size(); idx++) {
						PayPeriod tmpPayPeriod = (PayPeriod) periodList.get(idx);
						%><td><%=tmpPayPeriod.getPeriod()%></td><%
					}
						%><td>Total</td><%
					%>	
					<%
					for (int idx = 0; idx < periodList.size(); idx++) {
						PayPeriod tmpPayPeriod = (PayPeriod) periodList.get(idx);
						%><td><%=tmpPayPeriod.getPeriod()%></td><%
					}
						%><td>Total</td><%
					%>
					<%
					for (int idx = 0; idx < periodList.size(); idx++) {
						PayPeriod tmpPayPeriod = (PayPeriod) periodList.get(idx);
						%><td class="title_tbl" width=""><%=tmpPayPeriod.getPeriod()%></td><%
					}
						%><td class="title_tbl" width="">Total</td><%
					%>
				</tr>
				<%=html%>
		</table>
    </body>
</html>
