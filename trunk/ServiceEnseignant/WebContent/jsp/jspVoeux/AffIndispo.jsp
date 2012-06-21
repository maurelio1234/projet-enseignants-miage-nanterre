<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="serviceEnseignant.*"%>
<%@ page import="beans.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Mes Indisponibilités</title>
</head>
<body>

<jsp:useBean id="EnseiBean" scope="request" class="beans.Enseignant" />
<% int i=0; 
	int mois=0;
	String[] tabMois={"Janvier", "Fevrier","Mars","Avril","Mai","Juin", "Juillet",
			"Août","Septembre","Octobre","Novembre","Décembre"};
	java.util.Date date =null;%>
<table>
	<tr>
		<th>Date</th>
		<th>Durée</th>
		<th>Priorité</th>
	</tr>
	
	<%for(int m = 0; m<12; m++){%>
		<%=tabMois[m]%>
	<%for(i=0;i<EnseiBean.getMesIndisponibilites().size();i++){
		Calendar dateDbt = EnseiBean.getMesIndisponibilites().get(i).getDateIndisponibilite().getDateDuJour();
		
		
	if(dateDbt.get(Calendar.MONTH)==m){
	date = dateDbt.getTime();
	%>
	<tr>
		<td><%=date%> </td>
		<td><%=EnseiBean.getMesIndisponibilites().get(i).getDemiJournee()%></td>
		<td><%=EnseiBean.getMesIndisponibilites().get(i).getPoids()%> </td>
	</tr>

	<%
	//retransf en calendar
	dateDbt.setTime(date);
	}
	}
	} %>
</table>


<a href="index.jsp">Modifier une indisponibilité</a>
<a href="index.jsp">Entrer de nouvelles indisponibilités</a>

</body>
</html>