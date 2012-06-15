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
	Bonjour
	<jsp:useBean id="ExamBeans" scope="request" class="beans.Examen" />
	<%= ExamBeans.getLibelle() %>
	<%= ExamBeans.getCoefficient() %>
	<%= ExamBeans.getHoraire() %>
	FIN
</body>
</html>