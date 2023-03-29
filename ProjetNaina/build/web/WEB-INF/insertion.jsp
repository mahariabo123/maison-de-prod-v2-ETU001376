<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="entity.*,java.util.*"%>
<%
    String ray=(String) request.getAttribute("tay");
    String roa=(String) request.getAttribute("class");
        ArrayList<Type>ra=(ArrayList<Type>)request.getAttribute("listeType");
    

%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to Spring Web MVC project</title>
    </head>
    
    <body>
        <form action="<%= request.getContextPath() %>/insertion" method="POST" enctype="multipart/form-data">
            <input name="file"  type="file" />
            <p>titre:<input type="text" name="titre"></p>
            <p>description:<input type="text" name="description"></p>
            <p>date un:<input type="datetime-local" name="un"></p>
            <p>date deux:<input type="datetime-local" name="deux"></p>
            <p>date de creation:<input type="datetime-local" name="datecreation"></p>
            <p>date de publication:<input type="datetime-local" name="datepublication"></p>
            <p><input type="hidden" name="class" value="<%=roa%>"></p>
            <select name="idtype">
             <%for (Type elem : ra) {%>
             <option value="<%=elem.getIdtype()%>"><%=elem.getIntitule()%></option>
            
            <%}%>
                
            </select>
               
            
         
            <p><input type="submit" value="inserer"></p>  
        </form>
            <p><a href="<%= request.getContextPath() %>/list">list</a></p>
    </body>
</html>

