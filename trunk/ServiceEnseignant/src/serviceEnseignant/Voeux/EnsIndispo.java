package serviceEnseignant.Voeux;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import beans.*;
import serviceEnseignant.*;
import sun.util.calendar.BaseCalendar.Date;


public class EnsIndispo {

	
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
	
	public void ajoutIndispoReg(String debut, /*String fin,*/ int dj, int poids, Enseignant ensei, int regulier, int nbO, int j) throws ParseException{
		
		try {
			/** Connection à la base - Étape 2 */
			String url = "jdbc:oracle:thin:@miage03.dmiage.u-paris10.fr:1521:MIAGE";
			Connection cx = DriverManager.getConnection(url, "maletell",
					"matthieu");

			/** Création et exécution d'une requête - Étapes 3 & 4 */
			Statement st = cx.createStatement();
			PreparedStatement pst = cx
					.prepareStatement("INSERT INTO Indisponibilite VALUES(?,?,?,?)");
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date dateD = format.parse(debut);
			GregorianCalendar calendar = new java.util.GregorianCalendar(); 
			calendar.setTime(dateD);
				
			//si hebdomadaire : meme traitement sql mais fait plusieurs fois
			if (regulier == 1) {
				for(int i=0; i<nbO; i++){
					//ici, on ajoute une indispo autant de fois que demandé par le prof
					
					if(calendar.get(Calendar.DAY_OF_WEEK)==j){
						//si le jour correspond bien à la date entrée
						
						//on transforme le calendar en date pour l'ajouter à la table
						java.util.Date d = calendar.getTime();
						pst.setDate(1,(java.sql.Date) d);
						
						pst.setInt(2, ensei.getNumeroEnseignant());
						pst.setInt(3, poids);
						pst.setInt(4,dj);
					
						//pour passer à la semaine suivante, on ré-utilise la date au format calendar
						calendar.add(Calendar.WEEK_OF_MONTH, 1);
					}
				}
			}
			
			//si mensuel : meme traitement sql mais fait plusieurs fois
			if (regulier == 2) {

				for(int i=0; i<nbO; i++){
					//ici, on ajoute une indispo autant de fois que demandé par le prof
					
					if(calendar.get(Calendar.DAY_OF_WEEK)==j){
						//si le jour correspond bien à la date entrée
						
						//on transforme le calendar en date pour l'ajouter à la table
						java.util.Date d = calendar.getTime();
						pst.setDate(1,(java.sql.Date) d);
						
						pst.setInt(2, ensei.getNumeroEnseignant());
						pst.setInt(3, poids);
						pst.setInt(4,dj);
					
	// VERIFIER QUE AVANCE BIEN D'UN LUNDI A UN AUTRE : CF PB DES MOIS DE 28/29/30/31 JOURS
						
						//pour passer au mois suivant, on ré-utilise la date au format calendar
						calendar.add(Calendar.MONTH, 1);
						System.out.println("mois avant : "+ d + " mois apr : " + calendar);
					}
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
	
	
	
public void ajoutIndispoUniq(String debut, String fin, int poids, int refEnseignant) throws ParseException{
		
		try {
			/** Connection à la base - Étape 2 */
			String url = "jdbc:oracle:thin:@miage03.dmiage.u-paris10.fr:1521:MIAGE";
			Connection cx = DriverManager.getConnection(url, "maletell",
					"matthieu");

			/** Création et exécution d'une requête - Étapes 3 & 4 */
			Statement st = cx.createStatement();
			PreparedStatement pst = cx.prepareStatement("INSERT INTO Indisponibilite VALUES(?,?,?,?)");
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date dateD = format.parse(debut);
			java.util.Date dateF = format.parse(fin);
			
			/* 
			 * pour qu'on s'arrete au jour de fin INCLUS
			 * il faut transformer la dateF en calendar pour ajouter 1 JOUR 
			 */
			GregorianCalendar cal = new java.util.GregorianCalendar(); 
			cal.setTime(dateF);
			cal.add(Calendar.DAY_OF_WEEK, 1);
			/*
			 * On retransforme en date pour la boucle
			 */
			java.util.Date f = cal.getTime();
			
			/*
			 * Maintenant on boucle tant qu'on n'est pas
			 * la date de fin
			 * et on ajoute dans la base
			 */
			java.util.Date date= new java.util.Date();
			for (date = dateD; !date.equals(f);) {
				pst.setDate(1,(java.sql.Date) date);
				
				pst.setInt(2, refEnseignant);
				pst.setInt(3, poids);
				pst.setInt(4,2);//par defaut, pour une periode, c'est la journee entière
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

	public void afficherIndispo(Enseignant ensei) throws SQLException{
		
		/** Connection à la base - Étape 2 */
		String url = "jdbc:oracle:thin:@miage03.dmiage.u-paris10.fr:1521:MIAGE";
		Connection cx = DriverManager.getConnection(url, "maletell",
				"matthieu");

		/** Création et exécution d'une requête - Étapes 3 & 4 */
		Statement st = cx.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM Indisponibilite  WHERE NO_ENSEIGNANT ="+ ensei.getNumeroEnseignant() + "ORDER BY DATE_INDISPO");
		
		Indisponibilite i = new Indisponibilite();
		Jours j = new Jours();
		GregorianCalendar calendar = new java.util.GregorianCalendar(); 
		
		while(rs.next()){
			calendar.setTime(rs.getDate(2));
			j.setDate(calendar);
			i.setDateIndisponibilite(j);
			ensei.getMesIndispos().add(i);
		}
		
		
	}
}
