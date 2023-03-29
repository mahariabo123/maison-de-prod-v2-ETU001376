<%-- 
    Document   : result
    Created on : 27 janv. 2023, 17:20:20
    Author     : USER
--%>
<%@page import="entity.*,java.util.*,Service.*"%>
<%
    int idwatch=(int)request.getAttribute("idwatch");
    String sessionrecherche=(String)request.getAttribute("sessionrecherche");
    int nbPage=(int)request.getAttribute("nbPage");
    int numPage=(int)request.getAttribute("numPage");
    int ariana=0;
    if(request.getAttribute("ariana")!=null){
        ariana=(int)request.getAttribute("ariana");
    }
    int nombrePage=(int)request.getAttribute("nombrePage");
    ArrayList<Scene>ray=(ArrayList<Scene>)request.getAttribute("listScene");
    ArrayList<Status>status=(ArrayList<Status>)request.getAttribute("listStatus");
   
    
    
    
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" dir="ltr">
  <head>
    <meta charset="utf-8">
    <title>Fully Functional Pagination | Working With Example UI Cards - HTML, CSS & Jquery</title>
  </head>
  <style>
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@100;200;300;400;500;600;700;800;900&display=swap');

*{
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: "Poppins", sans-serif;
}

.container{
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

.card-content{
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
  margin: 30px;
}

.card{
  position: relative;
  background: #fff;
  max-width: 325px;
  width: 325px;
  height: auto;
  margin: 25px;
  box-shadow: 0 5px 25px rgb(1 1 1 / 20%);
  border-radius: 10px;
  overflow: hidden;
}

.card-image{
  max-height: 200px;
}

.card-image img{
  max-width: 100%;
  height: auto;
}

.card-info{
  position: relative;
  color: #222;
  padding: 10px 20px 20px;
}

.card-info h3{
  font-size: 1.8em;
  font-weight: 800;
  margin-bottom: 5px;
}

.card-info p{
  font-size: 1em;
  margin-bottom: 5px;
}

.pagination{
  text-align: center;
  margin: 30px 30px 60px;
  user-select: none;
}

.pagination li{
  display: inline-block;
  margin: 5px;
  box-shadow: 0 5px 25px rgb(1 1 1 / 10%);
}

.pagination li a{
  color: #fff;
  text-decoration: none;
  font-size: 1.2em;
  line-height: 45px;
}

.previous-page, .next-page{
  background: #0AB1CE;
  width: 80px;
  border-radius: 45px;
  cursor: pointer;
  transition: 0.3s ease;
}

.previous-page:hover{
  transform: translateX(-5px);
}

.next-page:hover{
  transform: translateX(5px);
}

.current-page, .dots{
  background: #ccc;
  width: 45px;
  border-radius: 50%;
  cursor: pointer;
}

.active{
  background: #0AB1CE;
}

.disable{
  background: #ccc;
}

header
{
     background: red;
     height: 200px;
     width: 100%;
     margin: 0;
     padding: 10px;
}
</style>
  <body>
      
      <div style="padding-left:40%;padding-top: 5%">
          <p><a href="<%= request.getContextPath() %>/addscene?idwatch=<%=idwatch %>">add scene</a></p>
          <p>recherche</p>
          <form action="<%= request.getContextPath() %>/list" method="GET">
              <p><select name="recherche">
                      <%for (Status elem : status) {%>
                      <option value="<%=elem.getIdstatus()%>"><%=elem.getIntitule()%></option>
                      <%}%>
                  </select>
            </p>
            <p><input type="hidden" name="sessionrecherche" value="<%=sessionrecherche%>"></p>
            <p><input type="hidden" name="idwatch" value="<%=idwatch%>"></p>
            <input type="submit" value="rechercher">  
        </form>
      </div>
      </div>
    <div class="container">
      <div class="card-content" >
          <%for (Scene elem : ray) {%>
        <div class="card">
            
          <div class="card-info">
            
               <h3>titre:<%=elem.getIntitule()%></p>
            
            
            <p>plateau:<%=elem.plateau()%></p>
            <p>auteur:<%=elem.auteur()%></p>
            <p>numero:<%=elem.getNumero()%></p>
            <p>dure:<%=elem.duree()%></p>
            <%if (elem.getPlannification()!=null) {%>
            <p>date planning:<%=elem.getPlannification().toString()%></p>
            <p><a href="<%= request.getContextPath() %>/pdf-test?idscene=<%= elem.getIdscene()%>">envoyez par mail</a></p>
            <%}else{%>
            <p>non plannifie</p>
            <%}%>
            <p>status:<%=elem.status()%></p>
            <p><a href="<%= request.getContextPath() %>/list2?idscene=<%= elem.getIdscene()%>">consulter les action</a></p>
            <p><a href="<%= request.getContextPath() %>/modifscene?idscene=<%=elem.getIdscene()%>">modifier</a></p>
                    
        </div>
          
      </div>
        <%}%>
  
          
      </div>
       <div class="pagination">
          <li class="page-item previous-page disable">
               <%if (numPage-1!=0) {%>
                <a href="<%= request.getContextPath() %>/list?sessionrecherche=<%= sessionrecherche %>&numPage=<%= numPage-1 %>&idwatch=<%= idwatch%>">precedent</a>
                 <%}%>
          </li>
          <%for (int i=1;i<nombrePage+1;i++) {%>
          <li class="page-item current-page active">
              <a class="page-link" href="<%= request.getContextPath() %>/list?sessionrecherche=<%= sessionrecherche %>&numPage=<%= i %>&idwatch=<%= idwatch%>"><%= i %></a>
          </li>
           <%}%>
           <li class="page-item previous-page disable">
              <%if (ariana==1) {%>
                 <p><a href="<%= request.getContextPath() %>/list?sessionrecherche=<%= sessionrecherche %>&numPage=<%= numPage+1 %>&idwatch=<%= idwatch%>">suivant</a></p>
              <%}%>
          </li>
          
          
        </div>
    </div>

  </body>
</html>
