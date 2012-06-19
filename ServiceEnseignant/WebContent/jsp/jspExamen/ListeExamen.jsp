<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import = "beans.*" %>  

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>BONJOUR</title>
</head>
<body>
		<jsp:useBean id="ensBeans" scope="request" class="beans.Enseignant" />
		
	Bonjour <%= ensBeans.getPrenom()%>
	   <%= ensBeans.getNom()%>
	<br>

	Selectionner les informations suivantes : <br>
	
		Promotion : 
	 <SELECT name="promo" size="1">
	<%int i=0;
	for(i=0;i<ensBeans.getMesServices().size();i++){ %>
	<OPTION><%= ensBeans.getMesServices().get(i).getMonEC().getMaFormation() %>
	<%} %>
	</SELECT>
	
	
	Promotion : 
	 <SELECT name="promo" size="1">
	<%int j=0;
	for(j=0;i<ensBeans.getMesServices().size();i++){ %>
	<OPTION><%= ensBeans.getMesServices().get(i).getMonEC().getMaFormation() %>
	<%} %>
	</SELECT>


	    
</body>
</html>