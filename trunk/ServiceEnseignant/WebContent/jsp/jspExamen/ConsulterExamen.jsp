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
<form method='POST' action='http://localhost:8080/ServiceEnseignant/ExamenServlet'>
<jsp:useBean id="ExamBeans" scope="request" class="beans.Examen" />
<TABLE BORDER=0>
<TR>
	<TD>Intitulé</TD>
	<TD>
	<INPUT type=text name="intitule" value=<%= ExamBeans.getLibelle() %>>
	</TD>
</TR>

<TR>
	<TD>Date de l'examen </TD>
	<TD>
	<INPUT type=date name="date" value=<%= ExamBeans.getDate().getDate().toString() %>>
	</TD>
</TR>


<TR>
	<TD>Coefficient</TD>
	<TD>
	<INPUT type=double name="coeff" value=<%= ExamBeans.getCoefficient() %>>
	</TD>
</TR>

<TR>
	<TD>Promotion</TD>
	<TD>
	<%=  %>TODO
	</TD>
</TR>

<TR>
	<TD>
	<br>
    <br>
    <br>
    <INPUT TYPE ='SUBMIT' NAME='Enregistrer les modifications' VALUE='Envoyer'>
    </form>
	</TD>
	
		<TD>
			<br>
    <br>
	<INPUT type="submit" value="Retour">
	</TD>
</TR>
</TABLE>
<center>
<table>
<tr><td>Nom</td><td>Prenom</td><td>Note</td>
</tr>
<%int i=0;
for(i=0;i<ExamBeans.getMesNotes().size();i++) %>
<tr><td><%=ExamBeans.getMesNotes().get(i).getMonEtudiant().getMonCandidat().getNom() %></td>
<td><%=ExamBeans.getMesNotes().get(i).getMonEtudiant().getMonCandidat().getPrenom() %></td>
<td><INPUT type="text" name=<%= "Note" + i %> value=<%=(ExamBeans.getMesNotes().get(i).getNote() != -1 ? ExamBeans.getMesNotes().get(i).getNote() : "") %>></td>









</table>
</center>
</form>
</body>
</html>