package serviceEnseignant.Voeux;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import beans.Enseignant;
import serviceEnseignant.*;
import sun.util.calendar.BaseCalendar.Date;


public class EnsIndispo {

	private Enseignant refEnseignant;
	
	public EnsIndispo(){
		
	}
	
	public void loadBD(){
		/** Chargement du driver JDBC - Étape 1 */
		try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		}
		catch( Exception ex ) {
		System.err.println( "Erreur lors du chargement du driver" );
		System.exit(1);
		}
	}
	
	public void ajoutIndispo(String debut, String fin, int dj, int poids, int refEnseignant, int regulier){
		
		try {
			/** Connection à la base - Étape 2 */
			String url = "jdbc:oracle:thin:@miage03.dmiage.u-paris10.fr:1521:MIAGE";
			Connection cx = DriverManager.getConnection(url, "adlevoir",
					"apprentis2010pw");

			/** Création et exécution d'une requête - Étapes 3 & 4 */
			Statement st = cx.createStatement();
			PreparedStatement pst = cx
					.prepareStatement("INSERT INTO Indisponibilite VALUES(?,?,?,?)");
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			
			//si journalier
			if (regulier == 0) {
				//on remplace les ? par les valeurs
				
				java.util.Date dateD = format.parse(debut);
				pst.setDate(1,(java.sql.Date) dateD);
				
				pst.setInt(2, refEnseignant);
				pst.setInt(3, poids);
				pst.setInt(4,dj);
				
				// on insere qu'une seule fois 
				pst.executeUpdate();
			}

			//si hebdomadaire : meme traitement sql mais fait plusieurs fois
			if (regulier == 1) {

				java.util.Date dateD = format.parse(debut);
				java.util.Date date;
				for (date = dateD; !date.equals(fin);) {

					// ajouter une ligne dans la table de la base SQL
					
					pst.setDate(1,(java.sql.Date) dateD);
					
					pst.setInt(2, refEnseignant);
					pst.setInt(3, poids);
					pst.setInt(4,dj);
					
					
					GregorianCalendar calendar = new java.util.GregorianCalendar(); 
					
					calendar.setTime(dateD);
					// incrémenter la date pour avancer de 7 jours
					calendar.add(Calendar.WEEK_OF_MONTH, 7);
					
				}
			}
			
			//si mensuel : meme traitement sql mais fait plusieurs fois
			if (regulier == 2) {

				for (GregorianCalendar date = debut; !date.equals(fin);) {

					// ajouter une ligne dans la table de la base SQL
					java.util.Date dateD = format.parse(debut);
					pst.setDate(1,(java.sql.Date) dateD);
					
					pst.setInt(2, refEnseignant);
					pst.setInt(3, poids);
					pst.setInt(4,dj);

					// incrémenter la date pour avancer d'un mois
					date.add(Calendar.MONTH, 1);
				}
			}
			/** Fermetures - Étape 6 */
			pst.close();
			st.close();
			cx.close();
		} catch (SQLException ex) {
			System.err.println("Erreur lors de la cx à la base");
			System.exit(1);
		}
    }
}
