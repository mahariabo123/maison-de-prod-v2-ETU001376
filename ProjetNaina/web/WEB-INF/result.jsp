<%-- 
    Document   : result
    Created on : 27 janv. 2023, 17:20:20
    Author     : USER
--%>
<%@page import="entity.*,java.util.*,Service.*"%>
<%
    String sessionrecherche=(String)request.getAttribute("sessionrecherche");
    String message=(String)request.getAttribute("message");
    int nbPage=(int)request.getAttribute("nbPage");
    int numPage=(int)request.getAttribute("numPage");
    int ariana=(int)request.getAttribute("ariana");
    int nombrePage=(int)request.getAttribute("nombrePage");
    int nb=(int)request.getAttribute("nb");
    ArrayList<Objectinsert>ray=(ArrayList<Objectinsert>)request.getAttribute("listeObj");
    out.println(nbPage);
    out.println(numPage);
    out.println(ray.size());
    out.println("nombrePage "+nombrePage);
    TypeService t=new TypeService();
    
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
       <form action="<%= request.getContextPath() %>/list" method="GET">
            <p>recherche:<input type="text" name="recherche"></p>
            <p><input type="hidden" name="sessionrecherche" value="<%=sessionrecherche%>"></p>
            <p><input type="submit" value="inserer"></p>   
        </form>
            <p><a href="<%= request.getContextPath() %>/list">All</a></p>
        <table border="1">
            <thead>
                <tr>
                    <th>titre</th>
                    <th>description</th>
                    <th>photo</th>
                    <th>date un</th>
                    <th>date deux</th>
                    <th>type</th>
                </tr>
            </thead>
            <tbody>
                <%for (Objectinsert elem : ray) {%>
                       
                    <tr>
                    <th><%=elem.getTitre()%></th>
                    <th><%=elem.getDescription()%></th>
                    <th><img src="data:image/png;base64,<%=elem.getPhoto()%>" width="100" height="100"/></th>
                    <th><%=elem.getUn().toString()%></th>
                    <%if (elem.getDeux()!=null) {%>
                    <th><%=elem.getDeux().toString()%></th>
                    <%}else{%>
                        <th></th>
                    <%}%>
                    <th><%=elem.type()%></th>
                </tr>
                <%}%>
                

            </tbody>
        </table>
                 <%if (numPage-1!=0) {%>
                <p><a href="<%= request.getContextPath() %>/list?sessionrecherche=<%= sessionrecherche %>&numPage=<%= numPage-1 %>">precedent</a></p>
                 <%}%>
                 <%if (ariana==1) {%>
                 <p><a href="<%= request.getContextPath() %>/list?sessionrecherche=<%= sessionrecherche %>&numPage=<%= numPage+1 %>">suivant</a></p>
                     <%}%>
           
    </body>
</html>
