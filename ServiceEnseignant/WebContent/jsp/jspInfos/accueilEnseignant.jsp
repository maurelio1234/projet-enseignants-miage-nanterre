<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="serviceEnseignant.*"%>
<%@ page import="beans.*"%>
<%@ page import="java.text.SimpleDateFormat" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Espace enseignant</title>
</head>
<body>
	<center>
            <h3>Bienvenue dans l'espace enseignants</h3>
            
            <table border="0" cellspacing="15px">
                 <tr> 
                   	<td align="right" style="padding-top: 8px;"> 
                   		<a href="/ServiceEnseignant/jsp/jspInfos/modifInfosEnseignant.jsp">Modifier mes informations</a> 
                   	</td> 

                   	<td align="right" style="padding-top: 8px;"> 
                   		<a href="/ServiceEnseignant/jsp/jspInfos/modifMDPenseignant.jsp">Modifier mon mot de passe</a> 
                   	</td>  

                   	<td align="right" style="padding-top: 8px;"> 
                   		<a href="/ServiceEnseignant/jsp/jspInfos/voeuxECenseignant.jsp">Consulter mes EC</a> 
                   	</td>  
                   	
                   	<td align="right" style="padding-top: 8px;"> 
                   		<a href="/ServiceEnseignant/jsp/jspExamen/ListeExamen.jsp">Consulter mes Examens</a> 
                   	</td> 
                   	
                   	<td align="right" style="padding-top: 8px;"> 
                   		<a href="/ServiceEnseignant/jsp/jspVoeux/index.jsp">Ajouter une indisponibilitÚ</a> 
                   	</td> 
                   	<td align="right" style="padding-top: 8px;"> 
                   		<a href="/ServiceEnseignant/ModeleAffIndispo">Afficher mes indisponibilitÚs</a> 
                   	</td> 
                 </tr> 
             </table> 
             
             
			<jsp:useBean id="ens" scope="session" class="beans.Enseignant"/>
			 <fieldset style="width: 480px; background-color:#FAFAFA;">
				<legend><b> <%= ens.getPrenom() %> <%= ens.getNom() %> :</b> </legend>
	             <table border =0>
	                    <tr align="left"> 

			<% if(request.getAttribute("msg")!= null) { %>
            	<br/> <b> <%= request.getAttribute("msg") %> </b> <br/>
            <% } %>
            
             <table border=0 width="300px" cellpadding="5px">
                   <tr align="left"> 
                   	<td style="padding-top: 8px; width:100px;"> N░ enseignant : </td> 
                   	<td> <%= ens.getNumeroEnseignant() %></td>
                   </tr>
                   
                   <tr align="left"> 
                   	<td style="padding-top: 8px;"> Login : </td> 
                   	<td> <%= ens.getLogin() %> </td>
                   </tr>
               	
               	<tr align="left">    	
                   	<td> Nom * : </td>
                   	<td>
                   	<%= ens.getNom() %>
                   	</td> 
                	</tr>
                	
                	<tr align="left">    	
                   	<td> Pr&eacute;nom * : </td>
                   	<td>
                   	<%= ens.getPrenom() %>
                   	</td> 
                 	</tr>  	
                   	
                   	<%  // gestion de l'affichage de la date
                   		SimpleDateFormat dateF = new SimpleDateFormat("dd/MM/yyyy");
    					String date = dateF.format(ens.getDateNaissance().getTime());    					
    				%>
    				
                   <tr align="left">  	
                   	<td> N&eacute;(e) le * : </td>
                   	<td> <%= date %> </td> 
                 	</tr> 
                 	
              	  <tr align="left">   	
                   	<td> Adresse * : </td>
                   	<td>
                   	<%= ens.getAdresse() %>
                   	</td> 
                 	</tr>
                  
                 <tr align="left"> 
                   	<td> T&eacute;l&eacute;phone * : </td>
                   	<td>
                   	<%= ens.getTelephone() %>
                   	</td>
                  </tr>
                                      
             </table>     
         </fieldset>
		</legend>
			
</center>
</body>
</html>