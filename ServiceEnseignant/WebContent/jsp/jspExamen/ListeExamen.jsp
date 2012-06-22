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

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript">
	function open_win() {
		window
				.open(
						"popup.jsp",
						"_blank",
						"toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, left=00, top=0, width=605, height=360")
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
						<td><SELECT name="formationChoix" size="1">
								<%-- 	<%int i=0; --%>
								<!-- 	for(i=0;i<ensBeans.getMesServices().size();i++){ %> -->
								<%-- 	<OPTION VALUE="formationChoix"><%= ensBeans.getMesServices().get(i).getMonEC().getMaFormation().getNiveau() %>  --%>
								<%-- 	<%= ensBeans.getMesServices().get(i).getMonEC().getMaFormation().getLibelle() %> --%>
								<%-- 	<%= ensBeans.getMesServices().get(i).getMonEC().getMaFormation().getType() %> --%>
								<!-- 	</OPTION> -->
								<%-- 	<%} %> --%>
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
						<td style="padding-top: 8px;">Ann�e de la promotion :</td>
						<td><SELECT name="annee" size="1">
						</SELECT></td>
					</tr>

					<tr>
						
						<td colspan="2" align="right" style="padding-top: 8px;">
						<input type="button" onclick="open_win()" name="NewExamen" value="Cr�er un examen" />
						<input type="submit" name="valider" value="Valider" />
						</td>
						
							
					</tr>

				</table>

			</form>
	</center>
</body>
</html>