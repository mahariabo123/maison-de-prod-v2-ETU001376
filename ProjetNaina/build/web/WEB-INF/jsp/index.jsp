<%-- 
    Document   : index
    Created on : 29 janv. 2023, 09:03:37
    Author     : USER
--%>
<%@page import="entity.*,java.util.*,Service.*"%>
<%
    ArrayList<Watch>ray=(ArrayList<Watch>)request.getAttribute("listWatch");
    
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
       <%for (Watch elem : ray) {%>
            <p>titre :<%=elem.getIntitule()%></p>
            <p><a href="<%= request.getContextPath() %>/list?idwatch=<%= elem.getIdwatch()%>">consulter les scenes</a></p>
            <p><a href="<%= request.getContextPath() %>/avantplanning?idwatch=<%= elem.getIdwatch()%>">plannifier</a></p>
       <%}%>
    </body>
</html>
