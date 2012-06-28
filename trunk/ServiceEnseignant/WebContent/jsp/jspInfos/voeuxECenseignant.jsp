<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="serviceEnseignant.*"%>
<%@ page import="beans.*"%>
<%@ page import="dao.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Espace enseignant</title>
</head>
<body>

	<center>
		<h3>Mes EC</h3>
		
		<jsp:useBean id="ens" scope="session" class="beans.Enseignant"/>
			
		<form name="form_voeux" method="post" action="../../ModifierVoeuxEnseignantServlet">
			<table border="1" cellspacing="15px">	

					
	             	
				<% // on charge tous les EC de l'enseignant
				
				VoeuxECDAO voeuxECdao = new VoeuxECDAO();
				
				// liste intermediaire pour les UE de l'enseignant
				List<UE> mesUE = new ArrayList<UE>();
				
				// on recupere les UE de l'enseignant dans une liste intermediaire pour eviter les doublons
				for(int i=0; i<ens.getMesServices().size(); i++){
				 					
					if(mesUE.isEmpty() || !mesUE.contains(ens.getMesServices().get(i).getMonEC().getMonUE())){
				
						System.out.println("UE n° "+ens.getMesServices().get(i).getMonEC().getMonUE().getNumeroUE());
						mesUE.add(ens.getMesServices().get(i).getMonEC().getMonUE());
					%>					
						<tr> <td colspan="2"> UE n°<%= ens.getMesServices().get(i).getMonEC().getMonUE().getNumeroUE() %> </td> </tr>	
						<tr> 
			               	<td style="padding-top: 8px;"> Nom EC </td> 
			               	<td style="padding-top: 8px;"> Voeux </td>  
			             </tr>	
					<% 					
										
						UEDAO ueDao = new UEDAO();
						UE monUE = ueDao.find(ens.getMesServices().get(i).getMonEC().getMonUE().getNumeroUE(), ens.getMesServices().get(i).getMonEC().getMonUE().getMaFormation().getNumeroFormation());
						ueDao.loadMesEC(monUE);
						
						System.out.println("  --> size ec pour cet ue : "+monUE.getMesEC().size());
						// on parcourt les EC de l'UE
						for(int j=0; j<monUE.getMesEC().size(); j++){
							
							System.out.println("  --> EC n° : "+monUE.getMesEC().get(j).getNumeroEC());
							
							// variables intermediaires
			      		 	int numForm = monUE.getMaFormation().getNumeroFormation();
			      		 	int numUE = monUE.getNumeroUE();
			      		 	int numEC = monUE.getMesEC().get(j).getNumeroEC();
			      		 	int numEns = ens.getNumeroEnseignant();
			      		 	
			      		 	// on recupere le voeux pour cet EC
			      		 	VoeuxEC voeux = null; // voeux EC de l'enseignant
			      		 	boolean choix = false; // variable intermediaire pour tester le choix de l'enseignant pour cet EC
			      		 	
			      		 	if((voeux=voeuxECdao.find(numForm, numUE, numEC, numEns)) != null){ 
			      		 		
			      		 		if(voeux.isChoixEC())
			      		 			choix = true; 
			      		 		
			      		 		System.out.println("  --> voeux de l'enseignant pour cet EC : "+choix);
			      		 	}
			      		 	%>
			      		 	<tr> 
			               	<td style="padding-top: 8px;"> <%= monUE.getMesEC().get(j).getLibelle() %> </td> 
			
			               	<td style="padding-top: 8px;">                		 
			               		 
		               		 	<input type="radio" name="choix_<%= numEC %>" value="oui" <% if(choix && (voeux!=null)){ %> checked <% } %> /> Oui
		               		 	<input type="radio" name="choix_<%= numEC %>" value="non" <% if(!choix && (voeux!=null)){ %> checked <% } %>/> Non
		               		 			               		
			               	</td>  
			
			             </tr>		
						<%
						} // fin for EC
												
					} 
						
				} // fin for UE %>             
	         </table> 
	         
	         <input type="submit" name="valider" value="Valider mes choix" /> 
         </form>
         
    </center>
            

</body>
</html>