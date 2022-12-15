<%-- 
    Document   : fix_stock
    Created on : May 1, 2020, 2:32:15 PM
    Author     : gndiw
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form name="form1" method="post" action="up_fix_stock_cuti.jsp" enctype="multipart/form-data">
            <table width="60%" border="0" cellspacing="2" cellpadding="2">
              
              <tr> 
                <td width="5%">&nbsp;</td>
                <td width="9%" nowrap>Upload File </td>
                <td width="86%"> 
                  <input type="file" name="file" size="40">
                  <input type="submit" name="Submit" value="Submit">
                </td>
              </tr>
            </table>
          </form>
    </body>
</html>
