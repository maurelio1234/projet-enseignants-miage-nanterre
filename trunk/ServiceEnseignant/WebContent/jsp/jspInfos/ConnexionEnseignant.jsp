<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<center>
            <h3>Connexion à l'espace enseignants</h3>
            <form name="form_login" method="post" action="../../serviceEnseignant.Infos/ConnexionEnseignantServlet">

                <table border="0">
                    <tr align="left"> <td style="padding-top: 8px;"> Login : </td> </tr>
                    <tr> <td> <input type="text" name="login" value=""> </td> </tr>
                   
                    <tr align="left"> <td style="padding-top: 8px;"> Mot de passe : </td> </tr>
                    <tr> <td> <input type="text" name="mdp" value=""> </td> </tr>
                   
                    <tr> <td colspan="2" align="right" style="padding-top: 8px;"> <input type="submit" name="bt_OK" value="OK"> </td> </tr>   
                </table>       

            </form>
     </center>


</body>
</html>