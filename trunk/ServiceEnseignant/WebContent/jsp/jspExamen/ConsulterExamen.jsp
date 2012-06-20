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
<form method='POST' action='../../UpdateExamenServlet'>
<jsp:useBean id="ExamBeans" scope="request" class="beans.Examen" />
<input type="hidden" name = "num_exam" value=<%= ExamBeans.getNumeroExamen() %>>
<TABLE BORDER=0>
<TR>
	<TD>Intitule</TD>
	<TD>
	<INPUT type=text name="intitule" value=<%= ExamBeans.getLibelle() %>>
	</TD>
</TR>

<TR>
	<TD>Date de l'examen </TD>
	<TD>
	<INPUT type=text name="date" value="Test">
	</TD>
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
	TODO
	</TD>
</TR>

<TR>
	<TD>
    <INPUT TYPE ='SUBMIT' NAME='Enregistrer les modifications' VALUE='Enregistrer les modifications'>
	</TD>
	
	<TD>
	<INPUT type="submit" value="Retour">
	</TD>
</TR>
</TABLE>
<center>
<table>
<tr>
	<td>Nom</td>
	<td>Prenom</td>
	<td>Note</td>
	<td>Present</td>
	<td>Absent</td>
	<td>Excus�</td>
</tr>
<%int i=0;
for(i=0;i<ExamBeans.getMesNotes().size();i++) {%>

<tr>
	<% System.out.println(ExamBeans.getMesNotes().get(i).getMonEtudiant().getMonCandidat().getNom()); %>
	<td><%=ExamBeans.getMesNotes().get(i).getMonEtudiant().getMonCandidat().getNom() %></td>
	<td><%=ExamBeans.getMesNotes().get(i).getMonEtudiant().getMonCandidat().getPrenom() %></td>
	<td><INPUT type="text" name=<%= "Note" + i %> value=<%=ExamBeans.getMesNotes().get(i).getNote()%>></td>	
	<td><INPUT TYPE="radio" NAME=<%= "choix" + i %> VALUE="1" checked></td>
	<td><INPUT TYPE="radio" NAME=<%= "choix" + i %> VALUE="2" <% if(ExamBeans.getMesNotes().get(i).getNote()==-2) { %> checked <%} %>></td> 
	<td><INPUT TYPE="radio" NAME=<%= "choix" + i %> VALUE="3" <% if(ExamBeans.getMesNotes().get(i).getNote()==-3) { %> checked <%} %>></td>
	<td><input type="hidden" name = <%= "RefEtudiant" + i %> value = <%=ExamBeans.getMesNotes().get(i).getMonEtudiant() %>></td>
	<td><input type="hidden" name = <%= "RefExamen" + i %> value = <%=ExamBeans.getMesNotes().get(i).getMonExamen() %>></td>
	</tr>
<% } %>

</table>
</center>
</form>
</body>
</html>