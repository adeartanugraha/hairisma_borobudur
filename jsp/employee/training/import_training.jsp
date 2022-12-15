<%-- 
    Document   : import_training
    Created on : Dec 21, 2021, 11:27:32 AM
    Author     : keys
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Upload File</title>
</head>
<body>
<form action="UploadFile" method="post" enctype="multipart/form-data">
Select File : <input type="file" name="filetoupload">
<br/>
<input type="submit" value="Upload File">
</form>
</body>
</html>