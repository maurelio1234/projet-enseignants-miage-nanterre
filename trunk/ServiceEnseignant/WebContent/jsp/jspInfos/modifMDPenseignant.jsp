<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="serviceEnseignant.*"%>
<%@ page import="beans.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Espace enseignant</title>
</head>
<body>
	<center>
            <h3>Modification du mot de passe</h3>
            
			<jsp:useBean id="ens" scope="session" class="beans.Enseignant"/>
			<%= ens.getPrenom() %> <%= ens.getNom() %>
							
			<% if(request.getAttribute("erreur")!= null) { %>
            	<br/> <font color="#DC143C"> <b> <%= request.getAttribute("erreur") %> </b> </font> <br/>
            	<form name="form_modifMDP" method="post" action="../../ServiceEnseignant/ModifierMDPEnseignantServlet">
            <% } 
            else { %>           
                <form name="form_modifMDP" method="post" action="../../ModifierMDPEnseignantServlet">
            <% } %>
          			
             <table border = 1>
                    <tr align="left"> 
                    	<td style="padding-top: 8px;"> Ancien mot de passe : </td> 
                    	<td> <input type="text" name="ancienMDP" value="" />  </td>                    	
                    </tr>
                    
                    <tr align="left"> 
                    	<td style="padding-top: 8px;"> Nouveau mot de passe : </td> 
                    	<td> <input type="text" name="nouveauMDP1" value="" />  </td>                    	
                    </tr>
                    
                    <tr align="left"> 
                    	<td style="padding-top: 8px;"> Confirmez le nouveau mot de passe : </td> 
                    	<td> <input type="text" name="nouveauMDP2" value="" />  </td>                    	
                    </tr>
                                       
                    <tr> 
                    	<td colspan="2" align="right" style="padding-top: 8px;"> 
                    		<input type="submit" name="valider" value="Valider" /> 
                    	</td> 
                    </tr>
   
              </table>     

            </form>
        </center>
</body>
</html>