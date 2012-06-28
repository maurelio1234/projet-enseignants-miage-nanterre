<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="serviceEnseignant.*"%>
<%@ page import="beans.Enseignant"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Espace enseignant</title>
</head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript">
	function open_win() {
		window
				.open(
						"popup.jsp",
						"_blank",
						"toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, left=00, top=0, width=605, height=360")
	}
	
	/**Retourne la valeur du select selectId*/
	function getSelectValue(selectId)
	{
		/**On récupère l'élement html <select>*/
		var selectElmt = document.getElementById(selectId);
		/**
		selectElmt.options correspond au tableau des balises <option> du select
		selectElmt.selectedIndex correspond à l'index du tableau options qui est actuellement sélectionné
		*/
		return selectElmt.options[selectElmt.selectedIndex].value;
	}
	
	function afficherExam(choixUE, choixFormation){
		var choixUE2 = getSelectValue(choixUE);
		var choixFormation2 = getSelectValue(choixFormation);
		var liste = new Array;
		var k, j;
		
		for(k=0; k<ens.getMesExamens().size(); k++){
			for(j=0;j<ens.getMesServices().size();j++){
				if(ens.getMesServices().get(j).getMonEC().getMonUE().equals(choixUE2) && ens.getMesServices().get(j).getMonEC().getMonUE().getMaFormation().getNumeroFormation().equals(choixFormation2)){
						liste.push( ens.getMesServices().get(j).getMonEnseignant().getMesExamens(k));					
				}
			}	
		}
		return list;
	}
	
</script>



<body>
	<center>
		<h3>Consultation des Examens</h3>

		<jsp:useBean id="ens" scope="session" class="beans.Enseignant" />
		<%=ens.getPrenom()%>
		<%=ens.getNom()%>

		<form name="ListeExamen" method="post" action="../../ServiceEnseignant/ListeExamenServlet">

				<table border=1>
					<tr align="left">
						<td style="padding-top: 8px;">Formation :</td>
						<td><SELECT id="formationChoix" size="1"> 
									<%int i;
 									for(i=0;i<ens.getMesServices().size();i++){ %>
 									<OPTION VALUE="formationChoix1" visibility = 'hidden' ><%= ens.getMesServices().get(i).getMonEC().getMonUE().getMaFormation().getNumeroFormation() %> </Option>
									<OPTION VALUE="formationChoix2"><%= ens.getMesServices().get(i).getMonEC().getMonUE().getMaFormation().getGrade() %>
									<%= ens.getMesServices().get(i).getMonEC().getMonUE().getMaFormation().getNiveau() %> 
									<%= ens.getMesServices().get(i).getMonEC().getMonUE().getMaFormation().getType() %> </OPTION>
									<%} %>

						</SELECT></td>
					</tr>

					<tr align="left">
						<td style="padding-top: 8px;">UE :</td>
						<td><SELECT id="UEChoix" size="1">
								<%-- 	<%int j=0; --%>
								<!-- 	for(j=0;j<ensBeans.getMesServices().size();j++){ %> -->
								<%-- 	<OPTION VALUE="UEChoix"><%= ensBeans.getMesServices().get(j).getMonEC().getMonUE().getNumeroUE() %> --%>
								<%-- 	<%} %> --%>
						</SELECT></td>
					</tr>


					<tr align="left">
						<td style="padding-top: 8px;">Année de la promotion :</td>
						<td><SELECT name="annee" size="1">
						</SELECT></td>
					</tr>

					<tr>
						
						<td colspan="2" align="right" style="padding-top: 8px;">
						<input type="button" onclick="open_win()" name="NewExamen" value="Créer un examen" />
						<input type="submit" name="valider" value="Valider" />
						</td>
						
							
					</tr>

				</table>
				
				<br>
				<br>
					
								
				<table border=1>
					<tr align="left">
						<td style="padding-top: 8px;">Formation :</td>
						<td><SELECT name="formationChoix10" size="1"> 
									<%int j=0;
									for(j=0;i<ens.getMesServices().size();j++){ %>
									<OPTION VALUE="formationChoix10"><%= ens.getMesServices().get(j).getMonEC().getMonUE().getMaFormation().getLibelle() %>
									<%= ens.getMesServices().get(j).getMonEC().getMonUE().getMaFormation().getGrade() %>
									</OPTION>
									<%} %>
						</SELECT></td>
					</tr>

					<tr align="left">
						<td style="padding-top: 8px;">UE :</td>
						<td><SELECT name="UEChoix" size="1">
								<%-- 	<%int j=0; --%>
								<!-- 	for(j=0;j<ensBeans.getMesServices().size();j++){ %> -->
								<%-- 	<OPTION VALUE="UEChoix"><%= ensBeans.getMesServices().get(j).getMonEC().getMonUE().getNumeroUE() %> --%>
								<%-- 	<%} %> --%>
						</SELECT></td>
					</tr>


					<tr align="left">
						<td style="padding-top: 8px;">Année de la promotion :</td>
						<td><SELECT name="annee" size="1">
						</SELECT></td>
					</tr>

					<tr>
						
						<td colspan="2" align="right" style="padding-top: 8px;">
						<input type="button" onclick="open_win()" name="NewExamen" value="Créer un examen" />
						<input type="submit" name="valider" value="Valider" />
						</td>
						
							
					</tr>

				</table>
				

			</form>
	</center>
</body>
</html>