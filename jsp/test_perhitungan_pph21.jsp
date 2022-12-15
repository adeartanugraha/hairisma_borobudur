<%-- 
    Document   : test_perhitungan_pph21
    Created on : Jan 21, 2022, 4:00:01 PM
    Author     : keys
--%>

<%@page import="com.dimata.util.Formater"%>
<%@page import="com.dimata.harisma.session.payroll.TaxCalculator"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.harisma.report.payroll.TaxDetail"%>
<%@page import="com.dimata.harisma.session.payroll.Pajak"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String FORMAT_DECIMAL = "###,###.##";
    String npwp = FRMQueryString.requestString(request, "npwp");
    double pkp = FRMQueryString.requestDouble(request, "pkp");
    double pph21 = 0;
    Pajak pajak = new Pajak();
    pajak.setNamaPemotong(npwp);
    pph21 = TaxCalculator.hitungTarifPPH21(pkp, pajak, npwp);
%>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Test for perhitungan pph21</title>
    </head>
    <body>
        <h3>Hasil PPH21 : <%=Formater.formatNumber( pph21, FORMAT_DECIMAL)%></h3>
        <form>
            <label>NPWP</label>
            <input type="text" value="1234444" name="npwp">
            <label>Jumlah PKP</label>
            <input type="text" value="0" name="pkp">
            <input type="submit" value="Submit">
        </form>
    </body>
</html>
