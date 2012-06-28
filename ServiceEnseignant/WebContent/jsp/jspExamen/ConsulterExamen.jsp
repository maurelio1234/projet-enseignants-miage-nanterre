<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import = "beans.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Consulter Examen</title>
</head>
<body>
<form method='POST' action='UpdateExamenServlet'>
<jsp:useBean id="ExamBeans" scope="request" class="beans.Examen" />
<input type="hidden" name = "num_exam" value=<%= ExamBeans.getNumeroExamen() %>>
<TABLE BORDER=0>
<tr>
	<td><input type=hidden name="num_exam" value = <%= ExamBeans.getNumeroExamen() %>></td>
	<td><input type=hidden name="nb_notes" value = <%= ExamBeans.getMesNotes().size() %>></td>
</tr>
<TR>
	<TD>Intitule</TD>
	<TD>
	<INPUT type=text name="libelle" value=<%= ExamBeans.getLibelle() %>>
	</TD>
</TR>

<TR>
	<TD>Date de l'examen </TD>
	<TD>
	<INPUT type=text name="date" value="Test">
	</TD>
	<td> à <input type=text name="heure" value=<%= ExamBeans.getHoraire() %>>
</TR>


<TR>
	<TD>Coefficient</TD>
	<TD>
	<INPUT type=text name="coeff" value=<%= ExamBeans.getCoefficient() %>>
	</TD>
</TR>

<TR>
	<TD>Promotion</TD>
	<TD>
	<%= "MIAGE" %>
	</TD>
</TR>

<tr>
	<td> Matière </td>
	<td><%= ExamBeans.getMonEC().getLibelle() %></td>
</tr>
<TR>
	<TD>
    <INPUT TYPE ='SUBMIT' NAME='Update' VALUE='Enregistrer les modifications'>
	</TD>
	
	<TD>
	<INPUT type="submit" value="Retour">
	</TD>
</TR>
</TABLE>

<table>
<tr>
	<td>Nom</td>
	<td>Prenom</td>
	<td>Note</td>
	<td>Present</td>
	<td>Absent</td>
	<td>Excusé</td>
</tr>
<%int i=0;
double n;
for(i=0;i<ExamBeans.getMesNotes().size();i++) {%>
<% n = ExamBeans.getMesNotes().get(i).getNote(); %>
<tr>
	<% System.out.println(ExamBeans.getMesNotes().get(i).getMonEtudiant().getMonCandidat().getNom()); %>
	<td><%=ExamBeans.getMesNotes().get(i).getMonEtudiant().getMonCandidat().getNom() %></td>
	<td><%=ExamBeans.getMesNotes().get(i).getMonEtudiant().getMonCandidat().getPrenom() %></td>
	<td><INPUT type="text" name=<%= "Note" + i %> value=<%=ExamBeans.getMesNotes().get(i).getNote()%>></td>	
	<td><INPUT TYPE="radio" NAME=<%= "choix" + i %> VALUE="1" checked></td>
	<td><INPUT TYPE="radio" NAME=<%= "choix" + i %> VALUE="2" <% if(ExamBeans.getMesNotes().get(i).getNote()==-2) { %> checked <%} %>></td> 
	<td><INPUT TYPE="radio" NAME=<%= "choix" + i %> VALUE="3" <% if(ExamBeans.getMesNotes().get(i).getNote()==-3) { %> checked <%} %>></td>
	<td><input type="hidden" name = <%= "RefEtudiant" + i %> value = <%=ExamBeans.getMesNotes().get(i).getMonEtudiant().getNumeroEtudiant() %>></td>

	</tr>
<% } %>

</table>

</form>
</body>
</html>