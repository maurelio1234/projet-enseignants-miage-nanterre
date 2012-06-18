<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="serviceEnseignant.*"%>
<%@ page import="beans.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<center>
            <h3>Bienvenue dans l'espace enseignants</h3>
            
			<jsp:useBean id="ens" scope="request" class="beans.Enseignant"/>
			Bonjour <%= ens.getPrenom() %> <%= ens.getNom() %> !

             <table border = 1>
                   <tr align="left"> 
                   	<td style="padding-top: 8px;"> N° enseignant : </td> 
                   	<td> <%= ens.getNumeroEnseignant() %></td>
                   </tr>
                   
                   <tr align="left"> 
                   	<td style="padding-top: 8px;"> Login : </td> 
                   	<td> <%= ens.getLogin() %> </td>
                   </tr>
               	
               	<tr>    	
                   	<td> Nom * : </td>
                   	<td>
                   	<%= ens.getNom() %>
                   	</td> 
                	</tr>
                	
                	<tr>   	
                   	<td> Prenom * : </td>
                   	<td>
                   	<%= ens.getPrenom() %>
                   	</td> 
                 	</tr>  	
                   	
              	  	<tr>    	
                   	<td> Adresse * : </td>
                   	<td>
                   	<%= ens.getAdresse() %>
                   	</td> 
                 	</tr>
                  
                  <tr>
                   	<td> Telephone * : </td>
                   	<td>
                   	<%= ens.getTelephone() %>
                   	</td>
                  </tr>
                  
                   <tr> 
                   	<td colspan="2" align="right" style="padding-top: 8px;"> 
                   		>> <a href="/modifInfosEnseignant.jsp">Modifier mes informations</a> 
                   	</td> 
                   </tr>
                   <tr>
                   	<td colspan="2" align="right" style="padding-top: 8px;"> 
                   		>> <a href="/modifMDPEnseignant.jsp">Modifier mon mot de passe</a> 
                   	</td>  
                   </tr>   
             </table>     

        </center>
</body>
</html>