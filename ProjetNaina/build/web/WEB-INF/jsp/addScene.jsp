<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="entity.*,java.util.*"%>
<%
    int idwatch=(int) request.getAttribute("idwatch");
    String roa=(String) request.getAttribute("class");
    String redirect=(String) request.getAttribute("redirect");
    ArrayList<Plateau>ra=(ArrayList<Plateau>)request.getAttribute("listPlateau");
    ArrayList<Auteur>au=(ArrayList<Auteur>)request.getAttribute("listAuteur");
    

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
  <form action="<%= request.getContextPath() %>/insert" method="GET">
    <div>
      <label for="intitule">intitule</label>
      <input name="intitule" type="text" />
    </div>
    <div>
      <label for="numero">numero</label>
      <input name="numero" type="text" />
    </div>
    <div>
      <label for="comp">plateau</label>
      <select name="idplateau">
             <%for (Plateau elem : ra) {%>
             <option value="<%=elem.getIdplateau()%>"><%=elem.getIntitule()%></option>
            
            <%}%>
                
            </select>
    </div>
    <div>
      <label for="comp">auteur</label>
      <select name="idauteur">
             <%for (Auteur elem : au) {%>
             <option value="<%=elem.getIdauteur()%>"><%=elem.getPrenom()%></option>
            
            <%}%>
                
            </select>
    </div>
    <div class="full-width">
      <button type="submit">Send Response</button>
      <button type="reset">Clear Form</button>
    </div>
    <p><input type="hidden" name="idstatus" value="<%=1%>"></p>
    <p><input type="hidden" name="class" value="<%=roa%>"></p>
    <p><input type="hidden" name="idwatch" value="<%=idwatch%>"></p>
    <p><input type="hidden" name="redirect" value="<%=redirect%>"></p>
  </form>
</main>

  </body>
</html>

