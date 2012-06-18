<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<HEAD>
<TITLE>Saisie nouvel examen</TITLE>
<style type="text/css">
<!--
.Style1 {
	font-size: 18px;
	font-family: Verdana, Arial, Helvetica, sans-serif;
	font-weight: bold;
}
-->
</style>


</HEAD>
<BODY BGCOLOR="#ffffff">
<CENTER>
<TABLE WIDTH="580" BORDER="0">
<TR id="sectionA">
<TD WIDTH="100%" VALIGN="TOP"> <div align="center" class="Style1">CREATION</div></TD>
</TR>
<TR>

<TD VALIGN="TOP">
<HR ALIGN=LEFT NOSHADE SIZE="1">
</TD>
</TR>
<TR align="left">
<TD VALIGN="TOP">
<font face="arial, helvetica, sans serif" size=+2>Nouvel Examen</font>
<HR ALIGN=LEFT NOSHADE SIZE="1">
</TD>
</TR>
<TR align="left">
<TD VALIGN="TOP">
<TABLE WIDTH="500" BORDER="0" CELLSPACING="0" CELLPADDING="4">
<TR>
<TD WIDTH="100%" VALIGN="TOP">
<BR CLEAR="ALL">


<FONT size=+1>

<form method='POST' action='http://localhost:8080/ProjetInfo2b/Servlet'>

<TABLE BORDER=0>
<TR>
	<TD>Intitulé</TD>
	<TD>
	<INPUT type=text name="intitule">
	</TD>
</TR>

<TR>
	<TD>Date en format (jj/mm/aaaa) </TD>
	<TD>
	<INPUT type=date name="date">
	</TD>
</TR>


<TR>
	<TD>Coefficient</TD>
	<TD>
	<INPUT type=double name="coeff">
	</TD>
</TR>

<TR>
	<TD>Promotion</TD>
	<TD>
	<SELECT name="promo">
		<OPTION VALUE="M1App2013">M1 App 2013</OPTION>
		<OPTION VALUE="M1Cla2013">M1 Cla 2013</OPTION>
		<OPTION VALUE="L3App2014">L3 App 2014</OPTION>
		<OPTION VALUE="L3Cla2014">L3 Cla 2014</OPTION>
	</SELECT>
	</TD>
</TR>

<TR>
	<TD>
	<br>
    <br>
    <br>
    <INPUT TYPE ='SUBMIT' NAME='envoyer' VALUE='Envoyer'>
    </form>
	</TD>
	
		<TD>
			<br>
    <br>
	<INPUT type="submit" value="Annuler">
	</TD>
</TR>
</TABLE>
</FORM>

</FONT>

<P>&nbsp;</P>

</TD>
</TR>

</TABLE>
</TD>
</TR>
<TR>
<TD VALIGN="TOP">
<BR>
<HR ALIGN=LEFT NOSHADE SIZE="1">
</TD>
</TR>
</TABLE>
</center>
<!-- ------------------------------------- -->

</BODY>
</HTML>

