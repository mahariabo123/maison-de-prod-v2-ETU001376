<%-- 
    Document   : avantplanning
    Created on : 20 mars 2023, 09:25:08
    Author     : USER
--%>
<%@page import="entity.*,java.util.*,Service.*"%>
<%
    ArrayList<Scene>ray=(ArrayList<Scene>)request.getAttribute("listScene");
%>
<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://fonts.googleapis.com/css?family=Roboto:400,700,900&display=swap" rel="stylesheet">

    <style>
       body {
  margin: 0;
  background-color: hsl(0, 0%, 98%);
  color: #333;
  font: 100% / normal sans-serif;
}

main {
  margin: 0 auto;
  padding: 4rem 0;
  width: 90%;
  max-width: 60rem;
}

form {
  box-sizing: border-box;
  padding: 2rem;
  border-radius: 1rem;
  background-color: hsl(0, 0%, 100%);
  border: 4px solid hsl(0, 0%, 90%);
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 2rem;
}

.full-width {
  grid-column: span 2;
}
    </style>

    <title>Contact Form #10</title>
  </head>
  <body>
  

     <main>
  <form action="<%= request.getContextPath() %>/planning" method="GET">
    <div>
      <label for="debut">date debut:</label>
      <input type="date" name="debut">
    </div>
    <div>
      <label for="fin">date fin:</label>
      <input type="date" name="fin">
    </div>
    <%for (Scene elem : ray) {%>
    <div>
      
        <p><input type="checkbox" name="idscene" value="<%=elem.getIdscene()%>">
            <p>titre:<%=elem.getIntitule()%></p>
            <p>plateau:<%=elem.plateau()%></p>
            <p>numero:<%=elem.getNumero()%></p>
            <p>dure:<%=elem.duree()%></p>
        </p>
        
    </div>
        <%}%>
        <p><input type="hidden" name="idwatch" value="<%=((String)request.getParameter("idwatch"))%>"></p>
    <div class="full-width">
      <button type="submit">Send Response</button>
      <button type="reset">Clear Form</button>
    </div>
  </form>
</main>

  </body>
</html>