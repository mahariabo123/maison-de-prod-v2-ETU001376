<%-- 
    Document   : index
    Created on : 29 janv. 2023, 09:03:37
    Author     : USER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <a href="<%= request.getContextPath() %>/boIndex.jsp">backoffice</a>
        <a href="<%= request.getContextPath() %>/list">frontoffice</a>
    </body>
</html>
