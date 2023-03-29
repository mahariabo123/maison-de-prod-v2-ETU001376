<%-- 
    Document   : index
    Created on : 29 janv. 2023, 09:03:37
    Author     : USER
--%>
<%@page import="entity.*,java.util.*,Service.*"%>
<%
    ArrayList<Date>ray=(ArrayList<Date>)request.getAttribute("listDate");
    HashMap<Date,ArrayList<Sousplanning>> map=(HashMap<Date,ArrayList<Sousplanning>>)request.getAttribute("map");
    //out.println(ray.size());
    int idplanning=(int)request.getAttribute("idplanning");
    
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Document</title>
  <style>
    table a:link {
	color: #666;
	font-weight: bold;
	text-decoration:none;
}
table a:visited {
	color: #999999;
	font-weight:bold;
	text-decoration:none;
}
table a:active,
table a:hover {
	color: #bd5a35;
	text-decoration:underline;
}
table {
	font-family:Arial, Helvetica, sans-serif;
	color:#666;
	font-size:12px;
	text-shadow: 1px 1px 0px #fff;
	background:#eaebec;
	margin:20px;
	border:#ccc 1px solid;

	-moz-border-radius:3px;
	-webkit-border-radius:3px;
	border-radius:3px;

	-moz-box-shadow: 0 1px 2px #d1d1d1;
	-webkit-box-shadow: 0 1px 2px #d1d1d1;
	box-shadow: 0 1px 2px #d1d1d1;
}
table th {
	padding:21px 25px 22px 25px;
	border-top:1px solid #fafafa;
	border-bottom:1px solid #e0e0e0;

	background: #ededed;
	background: -webkit-gradient(linear, left top, left bottom, from(#ededed), to(#ebebeb));
	background: -moz-linear-gradient(top,  #ededed,  #ebebeb);
}
table th:first-child {
	text-align: left;
	padding-left:20px;
}
table tr:first-child th:first-child {
	-moz-border-radius-topleft:3px;
	-webkit-border-top-left-radius:3px;
	border-top-left-radius:3px;
}
table tr:first-child th:last-child {
	-moz-border-radius-topright:3px;
	-webkit-border-top-right-radius:3px;
	border-top-right-radius:3px;
}
table tr {
	text-align: center;
	padding-left:20px;
}
table td:first-child {
	text-align: left;
	padding-left:20px;
	border-left: 0;
}
table td {
	padding:18px;
	border-top: 1px solid #ffffff;
	border-bottom:1px solid #e0e0e0;
	border-left: 1px solid #e0e0e0;

	background: #fafafa;
	background: -webkit-gradient(linear, left top, left bottom, from(#fbfbfb), to(#fafafa));
	background: -moz-linear-gradient(top,  #fbfbfb,  #fafafa);
}
table tr.even td {
	background: #f6f6f6;
	background: -webkit-gradient(linear, left top, left bottom, from(#f8f8f8), to(#f6f6f6));
	background: -moz-linear-gradient(top,  #f8f8f8,  #f6f6f6);
}
table tr:last-child td {
	border-bottom:0;
}
table tr:last-child td:first-child {
	-moz-border-radius-bottomleft:3px;
	-webkit-border-bottom-left-radius:3px;
	border-bottom-left-radius:3px;
}
table tr:last-child td:last-child {
	-moz-border-radius-bottomright:3px;
	-webkit-border-bottom-right-radius:3px;
	border-bottom-right-radius:3px;
}
table tr:hover td {
	background: #f2f2f2;
	background: -webkit-gradient(linear, left top, left bottom, from(#f2f2f2), to(#f0f0f0));
	background: -moz-linear-gradient(top,  #f2f2f2,  #f0f0f0);	
}
  </style>
</head>
<body>
  <table cellspacing='0'> <!-- cellspacing='0' is important, must stay -->

    <!-- Table Header -->
    <thead>
      <tr>
        <th>jour</th>
        <th>scene</th>
      </tr>
    </thead>
    <!-- Table Header -->
  
    <!-- Table Body -->
    <tbody>
  
      <%for(int i=0;i<ray.size();i++) {%>
               
                <tr>
                    <td><%=ray.get(i).toString()%></td>
                    <td>
                        <%ArrayList<Sousplanning>list=map.get(ray.get(i));%>
                        <%for(int j=0;j<list.size();j++) {%>
                        <p>numero:<%=list.get(j).getScene().getNumero()%> <a href="<%= request.getContextPath() %>/details?idscene=<%=list.get(j).getScene().getIdscene()%>">details</a> <a href="<%= request.getContextPath() %>/deletesousplanning?idsousplanning=<%=list.get(j).getIdsousplanning()%>&idplanning=<%=idplanning%>">remove</a> plateau :<%=list.get(j).getScene().plateau()%> dure:<%=list.get(j).getScene().duree()%></p></br>
                        <%}%>
                        <p><a href="<%= request.getContextPath() %>/here?idplanning=<%=idplanning%>&date=<%=ray.get(i).toString()%>">add scene here</a></p>
                    </td>
                </tr>
      <%}%>
  
      
  
    </tbody>
    <!-- Table Body -->
  
  </table>
  <p><a href="<%= request.getContextPath() %>/annulerplanning?idplanning=<%=idplanning%>">annuler planning</a></p>
  <p><a href="<%= request.getContextPath() %>/validerplanning?idplanning=<%=idplanning%>">valider planning</a></p>
</body>
</html>

