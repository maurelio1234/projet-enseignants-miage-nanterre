<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="serviceEnseignant.*"%>
<%@ page import="beans.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Fiche de voeux</title>
</head>

<script type="text/javascript">


moisX = [ "", "Janvier", "Fevrier", "Mars", "Avril", "Mai", "Juin",
		"Juillet", "Aout", "Septembre", "Octobre", "Novembre",
		"Decembre" ];
JourM = [ "Di", "Lu", "Ma", "Me", "Je", "Ve", "Sa" ];
var fermable_microcal = true;
var select_old = null;
var startWeek = 0;//debut de la semaine 0=dim,1=lun,...
var jourPause = {
	0 : true,
	6 : true
}; //jour de pause de la semaine
var jourFeriee = {
	"1-1" : "jour an",
	"1-5" : "fête du travail",
	"8-5" : "armistice",
	"14-7" : "fête nationale",
	"15-8" : "ascencion",
	"1-11" : "armistice",
	"11-11" : "toussain",
	"25-12" : "noel"
};
//structure la date
function strucDate(dateX) {
	return {
		"pos" : dateX.getDay(),
		"jour" : dateX.getDate(),
		"mois" : dateX.getMonth() + 1,
		"annee" : dateX.getFullYear()
	};
}
var dateS = strucDate(new Date());//date Selectionné
var dnow = strucDate(new Date());//date actuelle
//retourne le ième jour du 1er du mois

function premJourMois(mois, annee) {
	return (new Date(annee, mois - 1, 1).getDay());
}

//retourne le jour max du mois

function JmaxMois(mois, annee) {
	return (new Date(annee, mois, 0).getDate());
}
/* Test une date si elle est correct...spécial killer*/

function testTypeDate(dateEntree) {
	tst = false;
	try {
		rc = dateEntree.split("/");
		nd = new Date(rc[2], (rc[1] - 1), rc[0]);
		tst = (rc[2] > 1800 && rc[2] < 2200
				&& rc[2] == nd.getFullYear()
				&& rc[1] == (nd.getMonth() + 1) && rc[0] == nd
				.getDate());
	} catch (e) {
	}
	return tst;
}


//selection de la zone avec la souris
function choix(koi, code) {
	if (code) {
		select_old = koi.style.background;
		koi.style.background = '#c0c0FF';
	} else {
		koi.style.background = select_old;
	}
}


function testTravail(oldX, xx, jj, mm, aa) {
	styleX = "font-family:Tahoma;font-size:10px;text-align:center;";
	styleX += (oldX) ? "" : "color:#e0e0e0;";
	styleX += "cursor:hand;border-right:1px #e0e0e0 solid;border-bottom:1px #e0e0e0 solid;";
	if (jourPause[xx] || jourFeriee[jj + "-" + mm] != null)
		styleX += "background:#f0f0f0;";
	if (jj == dnow.jour && mm == dnow.mois && aa == dnow.annee)
		styleX += "border:2px red solid;";
	return styleX;
}



//test si année bissextile
function bissextile(annee) {
	return (annee % 4 == 0 && annee % 100 != 0 || annee % 400 == 0);
}
//Retourne le nombre de jour depuis le 1er janvier (num de semaine)



function nbJAnnee(dateX) {
	var nb_mois = [ , 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304,
			334 ];
	j = dateX.jour;
	m = dateX.mois;
	a = dateX.annee;
	nb = nb_mois[m] + j - 1;
	if (bissextile(a) && m > 2)
		nb++;
	return nb;
}


//affiche le calendrier
function view_microcal(actif, ki, source, mxS, axS) {
	if (actif) {
		//decalage du mois su on clique sur -/+
		if (mxS != -1) {
			clearTimeout(cc);
			ki.focus();
			fermable_microcal = true;
			dateS.mois = mxS;
			dateS.annee = axS;
			if (dateS.mois < 1) {
				dateS.annee--;
				dateS.mois += 12;
			}
			if (dateS.mois > 12) {
				dateS.annee++;
				dateS.mois -= 12;
			}
		}
		//init
		Dstart = (premJourMois(dateS.mois, dateS.annee) + 7 - startWeek) % 7;
		jmaxi = JmaxMois(dateS.mois, dateS.annee);
		jmaxiAvant = JmaxMois((dateS.mois - 1), dateS.annee);
		//si on veux ajouter le numero de la semaine ...
		//idxWeek=parseInt(nbJAnnee(strucDate(new Date(dateS.mois+'-01-'+dateS.annee)))/7,10)+1;
		ymaxi = parseInt((jmaxi + Dstart + 1) / 7, 10);
		//generation du tableau
		//--entête
		htm = "<table><tr style='font-size:10px;font-family:Tahoma;text-align:center;'>";
		htm += "<td style='cursor:hand;' onclick=\"view_microcal(true,"
				+ ki.id + "," + source.id + "," + (dateS.mois - 1)
				+ "," + dateS.annee + ");\"> <<< </td>";
		htm += "<td colspan='5'> <b> " + moisX[dateS.mois]
				+ "</b>&nbsp;" + dateS.annee + "</td>";
		htm += "<td style='cursor:hand;' onclick=\"view_microcal(true,"
				+ ki.id + "," + source.id + "," + (dateS.mois + 1)
				+ "," + dateS.annee + ")\"> >>> </td></tr>";
		//--corps
		htm += "<tr>";
		//affichage des jours DLMMJVS
		for (x = 0; x < 7; x++)
			htm += "<td style='font-size:10px;font-family:Tahoma;'><b>"
					+ JourM[(x + startWeek) % 7] + "</b></td>";
		htm += "</tr>"
		//------------------------
		for (y = 0; y <= ymaxi; y++) {
			htm += "<tr>";
			for (x = 0; x < 7; x++) {
				idxP = y * 7 + x - Dstart + 1; //numero du jour
				aa = dateS.annee;
				xx = (x + startWeek) % 7;
				//jour du mois précedent
				if (idxP <= 0) {
					jj = idxP + jmaxiAvant;
					mm = dateS.mois - 1;
					if (mm == 0) {
						mm = 12;
						aa--;
					}
					htm += "<td style='"
							+ testTravail(false, xx, jj, mm, aa)
							+ "' onmouseover='choix(this,true)' onmouseout='choix(this,false)' onclick=\""
							+ (ki.id) + ".value='"
							+ ((jj < 10) ? "0" : "") + jj + "/"
							+ ((mm < 10) ? "0" : "") + mm + "/" + aa
							+ "';" + (ki.id)
							+ ".style.color='black';\">" + jj + "</td>";
				} else if (idxP > jmaxi) //jour du mois suivant
				{
					jj = idxP - jmaxi;
					mm = dateS.mois + 1;
					if (mm == 13) {
						mm = 1;
						aa++;
					}
					htm += "<td style='"
							+ testTravail(false, xx, jj, mm, aa)
							+ "' onmouseover='choix(this,true)' onmouseout='choix(this,false)' onclick=\""
							+ (ki.id) + ".value='"
							+ ((jj < 10) ? "0" : "") + jj + "/"
							+ ((mm < 10) ? "0" : "") + mm + "/" + aa
							+ "';" + (ki.id)
							+ ".style.color='black';\">" + jj + "</td>";
				} else //jour du mois en cours
				{
					jj = idxP;
					mm = dateS.mois;
					htm += "<td style='"
							+ testTravail(true, xx, jj, mm, aa)
							+ "' onmouseover='choix(this,true)' onmouseout='choix(this,false)' onclick=\""
							+ (ki.id) + ".value='"
							+ ((jj < 10) ? "0" : "") + jj + "/"
							+ ((mm < 10) ? "0" : "") + mm + "/" + aa
							+ "';" + (ki.id)
							+ ".style.color='black';\">" + jj + "</td>";
				}
			}
			htm += "</tr>"
		}//-------------------------
		htm += "</table>"
		//affiche le tableau
		source.innerHTML = htm;
		source.style.visibility = "";
	} else {
		//ferme le calendrier
		if (fermable_microcal)
			cc = setTimeout(source.id + ".style.visibility='hidden'",
					500);
	}
}

		function sendForm() {
			document.getElementById('dateDebut').value = document.getElementById('dateDebut_').value;
			document.getElementById('dateFin').value = document.getElementById('dateFin_').value;
			document.getElementById('form').submit();
		}
		</script>
<body>


<table cellpadding='65'>
<tr>
<td>
	Saisissez une date au format JJ/MM/AAAA
	<table cellpadding="0" cellspacing="0">
		<tr>
			<td><input type="text" maxlength="10" id="dateDebut_"
				onfocus="view_microcal(true,document.getElementById('dateDebut_'),document.getElementById('microc'),-1,0);"
				onblur="view_microcal(false,document.getElementById('dateDebut_'),document.getElementById('microc'),-1,0);"
				onkeyup="this.style.color=testTypeDate(this.value)?'black':'red'">
			</td>
		
		</tr>
		<tr>
			<td><div id="microc"
					style="visibility: hidden; position:absolute; border: 2px red dashed; background: #ffffff;"></div>
			</td>
		</tr>
	</table>
	
	
	<FORM method="post" action="../../ModeleAbsReg">
<input type="hidden" maxlength="10" id="dateDbt"/>

		<br>
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
				onfocus="view_microcal(true,document.getElementById('dateDebut_'),document.getElementById('micro'),-1,0);"
				onblur="view_microcal(false,document.getElementById('dateDebut_'),document.getElementById('micro'),-1,0);"
				onkeyup="this.style.color=testTypeDate(this.value)?'black':'red'">
			</td>
			<td><input type="text" maxlength="10" id="dateFin_"
				onfocus="view_microcal(true,document.getElementById('dateFin_'),document.getElementById('microcal'),-1,0);"
				onblur="view_microcal(false,document.getElementById('dateFin_'),document.getElementById('microcal'),-1,0);"
				onkeyup="this.style.color=testTypeDate(this.value)?'black':'red'">
			</td>
		</tr>
		<tr>
			<td><div id="micro"
					style="visibility: hidden; position: absolute; border: 2px red dashed; background: #ffffff;"></div>
			</td>
			<td><div id="microcal"
					style="visibility: hidden; position: absolute; border: 2px red dashed; background: #ffffff;"></div>
			</td>
		</tr>
	</table>
	


	<FORM method="post" id="form" action="../../ModeleAbsUniq" >
		<input type="hidden" maxlength="10" id="dateDebut" name="dateDebut"/>
		<input type="hidden" maxlength="10" id="dateFin"   name="dateFin"/>
		
		Priorité de votre indisponibilité : </br> 
		<input type="radio" name="poids" value="1" id="1b" /> <label for="1b">incapacité totale</label><br />
		<input type="radio" name="poids" value="2" id="2b" /> <label for="2b">incapacité forte</label><br />
		 <input type="radio" name="poids" value="3" id="3b" /> <label for="3b">incapacité normale</label><br />
		 <input type="radio" name="poids" value="4" id="4b" /> <label for="4b">incapacité faible</label><br /></br> 
		 
		<input type="button" maxlength="10" id="valider" value="Valider" onclick="sendForm();"/>
	</FORM>			
	</td>
	
	</tr>
	</table>

	</body>	
</head>
</body>


</html>