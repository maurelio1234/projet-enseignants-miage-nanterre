<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="serviceEnseignant.*"%>
<%@ page import="beans.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type='text/javascript' src="calendrier.js"> </script>
<title>Fiche de voeux</title>
</head>


<body>


<table cellpadding='65'>
<tr>
<td>


	Saisissez une date au format JJ/MM/AAAA
	<table cellpadding="0" cellspacing="0">
		<tr>
			<td><input type="text" maxlength="10" id="dateDbt_"
				onfocus="view_microcal(true,document.getElementById('dateDbt_'),document.getElementById('microcal'),-1,0);"
				onblur="view_microcal(false,document.getElementById('dateDbt_'),document.getElementById('microcal'),-1,0);"
				onkeyup="this.style.color=testTypeDate(this.value)?'black':'red'">
			</td>
		
		</tr>
		<tr>
			<td><div id="microcal"
					style="visibility: hidden; position: absolute; border: 2px red dashed; background: #ffffff;"></div>
			</td>
		</tr>
	</table>
	
	
	<FORM method="post" action="ModeleAbsReg">

		<input type="hidden" maxlength="10" id="dateDbt"/>

		<!-- PARAMETRE DE L'INDISPO -->	
		<br/>Jour de la semaine : <br/>
		<input type="radio" name="jour" value="2" id="lundi"/><label for="lundi">Lundi</label><br/>
		<input type="radio" name="jour" value="3" id="Mardi"/><label for="Mardi">Mardi</label><br/>
		<input type="radio" name="jour" value="4" id="Mercredi"/><label for="Mercredi">Mercredi</label><br/>
		<input type="radio" name="jour" value="5" id="Jeudi"/><label for="Jeudi">Jeudi</label><br/>
		<input type="radio" name="jour" value="6" id="Vendredi"/><label for="Vendredi">Vendredi</label><br/>
		<br/>
		
		Durée de votre indisponibilité : </br>
		<input type="radio"	name="demiJ" value="0" id="am" /> <label for="am">matinée</label><br />
		<input type="radio" name="demiJ" value="1" id="pm" /> <label for="pm">après-midi</label><br />
		<input type="radio" name="demiJ" value="2" id="j" /> <label for="j">journée	entière</label><br /></br> 
		
		Le type d'indisponibilité :</br> 
		<input type="radio" name="type" value="1" id="hebdomadaire" /> <label for="hebdomadaire">hebdomadaire</label><br />
		<input type="radio" name="type" value="2" id="mensuel" /> <label for="mensuel">mensuel</label><br /></br>
	
			
		<label for="dateFin"> Nombres de répétitions</label><input type='text' name='nbOccu' /> <br />
		<br>
		
		Priorité de votre indisponibilité : </br> 
		<input type="radio" name="poids" value="1" id="1" /> <label for="1">incapacité totale</label><br />
		<input type="radio" name="poids" value="2" id="2" /> <label for="2">incapacité forte</label><br /> 
		<input type="radio" name="poids" value="3" id="3" /> <label	for="3">incapacité normale</label><br />
		<input type="radio"	name="poids" value="4" id="4" /> <label for="4">incapacité faible</label><br /></br> 
					
			
	<input type="submit" value="Valider" onclick="sendForm();" />

	</FORM>
</td>

<td valign='top'>


	Saisissez une date au format JJ/MM/AAAA
	<table cellpadding="0" cellspacing="0">
		<tr>
			<td><input type="text" maxlength="10" id="dateDebut_"
				onfocus="view_microcal(true,document.getElementById('dateDebut_'),document.getElementById('microcal'),-1,0);"
				onblur="view_microcal(false,document.getElementById('dateDebut_'),document.getElementById('microcal'),-1,0);"
				onkeyup="this.style.color=testTypeDate(this.value)?'black':'red'">
			</td>
			<td><input type="text" maxlength="10" id="dateFin_"
				onfocus="view_microcal(true,document.getElementById('dateFin_'),document.getElementById('microcal'),-1,0);"
				onblur="view_microcal(false,document.getElementById('dateFin_'),document.getElementById('microcal'),-1,0);"
				onkeyup="this.style.color=testTypeDate(this.value)?'black':'red'">
			</td>
		</tr>
		<tr>
			<td><div id="microcal"
					style="visibility: hidden; position: absolute; border: 2px red dashed; background: #ffffff;"></div>
			</td>
		</tr>
	</table>
	
Priorité de votre indisponibilité : </br> <input type="radio" name="poids"
			value="1" id="1" /> <label for="1">incapacité totale</label><br />
		<input type="radio" name="poids" value="2" id="2" /> <label for="2">incapacité
			forte</label><br /> <input type="radio" name="poids" value="3" id="3" /> <label
			for="3">incapacité normale</label><br /> <input type="radio"
			name="poids" value="4" id="4" /> <label for="4">incapacité
			faible</label><br /></br> 

	<FORM method="post" id="form" action="ModeleAbsUniq" >
		<input type="hidden" maxlength="10" id="dateDebut"/>
		<input type="hidden" maxlength="10" id="dateFin"/>
		<input type="button" maxlength="10" id="valider" value="Valider" onclick="sendForm();"/>
	</FORM>			
	</td>
	
	</tr>
	</table>

	</body>	
</head>
</body>


</html>