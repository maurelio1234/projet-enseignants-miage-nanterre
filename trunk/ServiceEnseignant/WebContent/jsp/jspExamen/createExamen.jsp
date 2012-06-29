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
<jsp:useBean id="ens" scope="session" class="beans.Enseignant" />
</HEAD>
<BODY BGCOLOR="#ffffff">
	<CENTER>
		<TABLE WIDTH="580" BORDER="0">
			<TR id="sectionA">
				<TD WIDTH="100%" VALIGN="TOP">
					<div align="center" class="Style1">CREATION</div>
				</TD>
			</TR>
			<TR>

				<TD VALIGN="TOP">
					<HR ALIGN=LEFT NOSHADE SIZE="1"></TD>
			</TR>
			<TR align="left">
				<TD VALIGN="TOP"><font face="arial, helvetica, sans serif"
					size=+2>Nouvel Examen</font>
					<HR ALIGN=LEFT NOSHADE SIZE="1"></TD>
			</TR>
			<TR align="left">
				<TD VALIGN="TOP">
					<TABLE WIDTH="500" BORDER="0" CELLSPACING="0" CELLPADDING="4">
						<TR>
							<TD WIDTH="100%" VALIGN="TOP"><BR CLEAR="ALL"> <FONT
								size=+1>

									<form method='POST' action='ServiceEnseignant/CreateExamenServlet'>
										<input type="hidden" value=<%=ens.getNumeroEnseignant()%> name="numEns">
										<TABLE BORDER=0>
											<TR>
												<TD>Intitulé</TD>
												<TD><INPUT type=text name="intitule"></TD>
											</TR>

											<TR>
												<TD>Date (formation jj/mm/aaa)</TD>
												<TD><INPUT type=text name="date"></TD>
											</TR>


											<TR>
												<TD>Horaire (format HH:mm)</TD>
												<TD><INPUT type=text name="heure"></TD>
											</TR>

											<TR>
												<TD>Coefficient</TD>
												<TD><INPUT type=text name="coeff"></TD>
											</TR>

											<tr>
												<td>Matière</td>
												<td><select name="matiere">
														<%
															int j = 0;
															for (j = 0; j < ens.getMesServices().size(); j++) {
														%><option value=<%=ens.getMesServices().get(j).getMonEC().getNumeroEC() + ";" + ens.getMesServices().get(j).getMonEC().getMonUE().getNumeroUE() + ";" +  ens.getMesServices().get(j).getMonEC().getMonUE().getMaFormation().getNumeroFormation()%>><%=ens.getMesServices().get(j).getMonEC().getLibelle()%>
														</option>
														<%
															}
														%>
												</select></td>
											<TR>
												<TD><INPUT TYPE='SUBMIT'
													NAME='envoyer' VALUE='Envoyer'>
													</TD>

												<TD><INPUT type="submit"
													value="Annuler"></TD>
											</TR>
										</TABLE>
									</FORM> </FONT>

								<P>&nbsp;</P></TD>
						</TR>

					</TABLE></TD>
			</TR>
			<TR>
				<TD VALIGN="TOP"><BR>
					<HR ALIGN=LEFT NOSHADE SIZE="1"></TD>
			</TR>
		</TABLE>
	</center>
	<!-- ------------------------------------- -->

</BODY>
</HTML>

