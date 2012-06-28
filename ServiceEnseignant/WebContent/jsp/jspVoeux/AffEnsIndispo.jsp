<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
       <%@ page import="serviceEnseignant.*"%>
<%@ page import="beans.*"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Afficher les indisponibilités de l'enseignant</title>
</head>
<body>
<jsp:useBean id="EnseiBean" scope="request" class="beans.Enseignant" />
Voici l'enseignant que vous avaez demandé : 
<%=EnseiBean.getNom() %>
<%=EnseiBean.getPrenom() %>
<table border="3" bordercolor='blue' cellpadding='15'>
	<tr>
		<th>Mois</th>
		<th>Date</th>
		<th>Demi journée</th>
		<th>Priorité</th>
	</tr>
	

<% int i=0; 
	int mois=0;
	String[] tabMois={"Janvier", "Fevrier","Mars","Avril","Mai","Juin", "Juillet",
			"Août","Septembre","Octobre","Novembre","Décembre"};
	String[] Jours={"Dimanche","Lundi","Mardi","Mercredi","Jeudi","Vendredi","Samedi"};
	String[] dj={"Matin","Après-Midi","Journée entière"};
	String[] prio={"Incapacité totale","Incapacité forte","Incapacité normale","Incapacité faible"};
	java.util.Date date =null;%>

	
	<%for(int m = 0; m<12; m++){%>
		<tr> <td><%=tabMois[m]%></td>
		<td></td>
		<td></td>
		<td></td>
		</tr>
	<%for(i=0;i<EnseiBean.getMesIndisponibilites().size();i++){
		Calendar dateDbt = EnseiBean.getMesIndisponibilites().get(i).getDateIndisponibilite().getDateDuJour();
		
		
	if(dateDbt.get(Calendar.MONTH)==m){
		
		date = dateDbt.getTime();
		
		SimpleDateFormat dateF = new SimpleDateFormat("dd/MM/yyyy");
		String dated= dateF.format(date);
	%><tr>
		<td></td>
		<td><br><%=Jours[date.getDay()]%> <%=dated%> </td>
		<td><%=dj[EnseiBean.getMesIndisponibilites().get(i).getDemiJournee()]%></td>
		<td><%=prio[EnseiBean.getMesIndisponibilites().get(i).getPoids()-1]%> </td>
		
	</tr>

	<%
	//retransf en calendar
	dateDbt.setTime(date);
	}
	}
	} %>

</table>

</body>
</html>