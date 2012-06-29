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
<center>
			<% if(request.getAttribute("msg")!= null) { %>
            	<br/> <b> <%= request.getAttribute("msg") %> </b> <br/>
            <% } %>
</center>

<center>
<h3>Consultation des Examens</h3>
 <fieldset style="width: 480px; background-color:#FAFAFA;">
<legend><b> Informations :</b> </legend>
	             <table border =0>
	                    <tr align="left"> 
<tr>
	<td><input type=hidden name="num_exam" value = <%= ExamBeans.getNumeroExamen() %>></td>
	<td><input type=hidden name="nb_notes" value = <%= ExamBeans.getMesNotes().size() %>></td>
</tr>
<TR>
	<TD>Promotion</TD>
	<TD>
	<%= ExamBeans.getMonEC().getMonUE().getMaFormation().getGrade() + ExamBeans.getMonEC().getMonUE().getMaFormation().getNiveau() + " " + ExamBeans.getMonEC().getMonUE().getMaFormation().getType() %>
	</TD>
</TR>

<tr>
	<td> Matière </td>
	<td><%= ExamBeans.getMonEC().getLibelle() %></td>
</tr>
<TR>
	<TD>Intitule</TD>
	<TD>
	<INPUT type=text name="libelle" value=<%= ExamBeans.getLibelle() %>>
	</TD>
</TR>

<TR>
	<TD>Date de l'examen </TD>
	<TD><INPUT type=text name="date" value= <%= ExamBeans.getDate().toString() %>> </TD>
</TR>
<TR>
	<td> à </TD>
	<td> <input type=text name="heure" value=<%= ExamBeans.getHoraire() %>> </td>
</TR>


<TR>
	<TD>Coefficient</TD>
	<TD>
	<INPUT type=text name="coeff" value=<%= ExamBeans.getCoefficient() %>>
	</TD>
</TR>


<TR>
	<TD>
    <INPUT TYPE ='SUBMIT' NAME='Update' VALUE='Enregistrer les modifications'>
	</TD>
	
	<TD>
	
                   	<td align="right" style="padding-top: 8px;"> 
                   		<a href="/ServiceEnseignant/jsp/jspExamen/ListeExamen.jsp">Retour</a> 
                   	</td> 
	
	</TD>
</TR>
</TABLE>
			</fieldset>
		</legend>
</center>

<br>
<center>
 <fieldset style="width: 480px; background-color:#FAFAFA;">
<legend> <b> Liste des Etudiants : </b></legend>
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
	<td align = left><%=ExamBeans.getMesNotes().get(i).getMonEtudiant().getMonCandidat().getNom() %></td>
	<td align = left><%=ExamBeans.getMesNotes().get(i).getMonEtudiant().getMonCandidat().getPrenom() %></td>
	<td><INPUT type="text" name=<%= "Note" + i %>
	<% switch((int)ExamBeans.getMesNotes().get(i).getNote()){
	case -1 : %> value = "" <% break; 
	case -2 : %> disabled="disabled" value = "0" <% break; 
	case -3 : %> disabled="disabled" value = "N/A" <% break;
	default : %> value = <%= ExamBeans.getMesNotes().get(i).getNote()%> <% break;
	}
	%>></td>
	<td><INPUT TYPE="radio" onclick="UpdateRadio(<%=i %>)" NAME=<%= "choix" + i %> VALUE="1" checked></td>
	<td><INPUT TYPE="radio" onclick="UpdateRadio(<%=i %>)" NAME=<%= "choix" + i %> VALUE="2" <% if(ExamBeans.getMesNotes().get(i).getNote()==-2) { %> checked <%} %>></td> 
	<td><INPUT TYPE="radio" onclick="UpdateRadio(<%=i %>)" NAME=<%= "choix" + i %> VALUE="3" <% if(ExamBeans.getMesNotes().get(i).getNote()==-3) { %> checked <%} %>></td>
	<td><input type="hidden" name = <%= "RefEtudiant" + i %> value = <%=ExamBeans.getMesNotes().get(i).getMonEtudiant().getNumeroEtudiant() %>></td>

	</tr>
<% } %>
</table>
			</fieldset>
		</legend>
</center>
</form>
</body>
<script type="text/javascript">
	function UpdateRadio(i) {
		if (document.getElementsByName("choix" + i)[0].checked == true) {
			document.getElementsByName("Note" + i)[0].disabled = false;
			document.getElementsByName("Note" + i)[0].value = "";
		} else if((document.getElementsByName("choix" + i)[1].checked == true)){
			document.getElementsByName("Note" + i)[0].disabled = true;
			document.getElementsByName("Note" + i)[0].value = 0;
		} else {
			document.getElementsByName("Note" + i)[0].disabled = true;
			document.getElementsByName("Note" + i)[0].value = "N/A";			
		}
	};

</script>
</html>