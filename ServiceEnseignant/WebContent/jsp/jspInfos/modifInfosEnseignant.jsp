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
            <h3>Modification des informations enseignant</h3>
            
			<jsp:useBean id="ens" scope="session" class="beans.Enseignant"/>
			<%= ens.getPrenom() %> <%= ens.getNom() %>

	
            <form name="form_modifInfos" method="post" action="../../ModifierInfosEnseignantServlet">

			<fieldset style="width: 400px; background-color:#FAFAFA;">
				<legend> Mes informations </legend>
			
				<table border = 1>
	                    <tr align="left"> 
	                    	<td style="padding-top: 8px;"> N° enseignant : </td> 
	                    	<td> <%= ens.getNumeroEnseignant() %> </td>
	                    </tr>
	                    
	                    <tr align="left"> 
	                    	<td style="padding-top: 8px;"> Login : </td> 
	                    	<td> <%= ens.getLogin() %> </td>
	                    </tr>
	                	
	                	<tr>    	
	                    	<td> Nom * : </td>
	                    	<td>
	                    		<input type="text" name="nom" value="<%= ens.getNom() %>" /> 
	                    	</td> 
	                 	</tr>
	                 	
	                 	<tr>   	
	                    	<td> Prenom * : </td>
	                    	<td>
	                    		<input type="text" name="prenom" value="<%= ens.getPrenom() %>" /> 
	                    	</td> 
	                  	</tr>  	
	                    
	                    <tr>   	
		                   	<td> Date de naissance (jj/mm/aaaa) * : </td>
		                   	<td>
		                   	<%  // gestion de l'affichage de la date
	                   			SimpleDateFormat dateF = new SimpleDateFormat("dd/MM/yyyy");
	    						String date = dateF.format(ens.getDateNaissance().getTime());    					
	    					%>
		                   		<input type="text" name="dateNaiss" value="<%= date %>" />
		                   	</td> 
	                 	</tr> 
	                 		
	               	  	<tr>    	
	                    	<td> Adresse * : </td>
	                    	<td>
	                    		<input type="text" name="adresse" value="<%= ens.getAdresse() %>" /> 
	                    	</td> 
	                  	</tr>
	                   
	                   <tr>
	                    	<td> Telephone * : </td>
	                    	<td>
	                    		<input type="text" name="telephone" value="<%= ens.getTelephone() %>" /> 
	                    	</td> 
	                   </tr>
	                   
	                    <tr> 
	                    	<td colspan="2" align="right" style="padding-top: 8px;"> 
	                    		<input type="submit" name="valider" value="Valider" /> 
	                    	</td> 
	                    </tr>
	   
	              </table>     
			
			</fieldset>
             

            </form>
        </center>
</body>
</html>