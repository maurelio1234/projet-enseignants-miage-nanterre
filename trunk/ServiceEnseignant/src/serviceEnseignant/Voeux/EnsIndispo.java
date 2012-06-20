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
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import beans.*;
import serviceEnseignant.*;


public class EnsIndispo {

	
	public EnsIndispo(){
		
	}
	
	public void loadBD(){
		/** Chargement du driver JDBC - �tape 1 */
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}
		catch( Exception ex ) {
			System.err.println( "Erreur lors du chargement du driver" );
			System.exit(1);
		}
	}
	
	private Set<Date> getIndispo(Date debut, int regulier, int nbO, int j) {
		Set<Date> ret = new HashSet<Date>();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	
		GregorianCalendar calendar = new java.util.GregorianCalendar(); 
		calendar.setTime(debut);
		
		System.out.println("je suis dans le getindispo et regulier : " + regulier);
		
		if (regulier == 1) { //HEBDO
			///
		} else if (regulier == 2){ // MENS
			
			System.out.println("je suis mensuellement dans le getindispo");
			int semaine = calendar.get(Calendar.WEEK_OF_MONTH);
			int mois = calendar.get(Calendar.MONTH);
			int annee = calendar.get(Calendar.YEAR);
			for(int i=0;i<nbO;++i) {
				Date date = getDate(semaine, j, annee, mois+i);
				ret.add(date);
				
				System.out.println("je suis mensuellement dans le getindispo BOUCLE FOR");
			}
		}
		return ret;
	}
	
	private Date getDate(int semaine, int j, int annee, int mois) {
		GregorianCalendar calendar = new java.util.GregorianCalendar(annee, mois-1, 1);
		while(semaine > 0) {
			System.out.println("je suis dans le WHILE du getDate");
			if (calendar.get(GregorianCalendar.DAY_OF_WEEK) == j) {				
				semaine --;
			}
			calendar.add(GregorianCalendar.DAY_OF_MONTH, 1);
		}
		
		java.util.Date d = calendar.getTime();
		
		return d;
	}
		
	public void ajoutIndispoReg(String debut, /*String fin,*/ int dj, int poids, Enseignant ensei, int regulier, int nbO, int j) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date deb = format.parse(debut);
		
		System.out.println("je suis dans ajoutREG" + regulier);
		
		Set<Date> collectionIndispo = new HashSet<Date>();
		collectionIndispo=getIndispo(deb, regulier, nbO, j);
		
		for(Date ind : collectionIndispo) {
			System.out.println("je suis dans le DEBUT DU FOR de ajoutIndispoREG");
			ajoutIndispoSimple(ind, poids, ensei, dj); //ajouter juste une ligne dans la table indispo
			System.out.println("je suis dans le FOR de ajoutIndispoREG");
		}
	}
	
	public void ajoutIndispoSimple(Date debut,int poids, Enseignant ensei, int dj){
		try {
			System.out.println("je suis dans ajoutSIMPLE" + debut);
			/** Connection � la base - �tape 2 */
			this.loadBD();

			Connection cx = DriverManager.getConnection("jdbc:oracle:thin:@miage03.dmiage.u-paris10.fr:1521:MIAGE","maletell","matthieu");
			cx.setAutoCommit(false);

			/** Cr�ation et ex�cution d'une requ�te - �tapes 3 & 4 */
			Statement st = cx.createStatement();
			PreparedStatement pst = cx.prepareStatement("INSERT INTO Indisponibilite VALUES(?,?,?,?)");
			
//			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		//	java.util.Date dateD = format.parse(debut);
			GregorianCalendar calendar = new java.util.GregorianCalendar(); 
			calendar.setTime(debut);
			
			java.sql.Date sqlDate = new java.sql.Date(calendar.getTimeInMillis());

			pst.setDate(1,sqlDate);
			pst.setInt(2, ensei.getNumeroEnseignant());
			pst.setInt(3, poids);
			pst.setInt(4,dj);
			
			pst.executeUpdate();
			System.out.println("je suis apres le executeUpdate du ajoutIndispoSIMPLE");
			cx.commit();
			
			pst.close();
			st.close();
			cx.close();
		}catch (SQLException ex) {
			ex.printStackTrace();
			System.err.println("Erreur lors de la cx � la base");
			System.exit(1);
		}
	}	
//		try {
//			System.out.println("coucou!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//			/** Connection � la base - �tape 2 */
//			this.loadBD();
//
//			Connection cx = DriverManager.getConnection("jdbc:oracle:thin:@miage03.dmiage.u-paris10.fr:1521:MIAGE","maletell","matthieu");
//			System.out.println("typeeeeeeeeee 222522222222 " + regulier);
//cx.setAutoCommit(false);
//
//			/** Cr�ation et ex�cution d'une requ�te - �tapes 3 & 4 */
//			Statement st = cx.createStatement();
//			PreparedStatement pst = cx
//					.prepareStatement("INSERT INTO Indisponibilite VALUES(?,?,?,?)");
//			
//			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//			java.util.Date dateD = format.parse(debut);
//			GregorianCalendar calendar = new java.util.GregorianCalendar(); 
//			calendar.setTime(dateD);
//			
//			GregorianCalendar calendar2 = new java.util.GregorianCalendar();
//			
//			System.out.println("type : "+ regulier);
//				
//			//si hebdomadaire : meme traitement sql mais fait plusieurs fois
//			if (regulier == 1) {
//				for(int i=0; i<nbO; i++){
//					//ici, on ajoute une indispo autant de fois que demand� par le prof
//					
//					if(calendar.get(Calendar.DAY_OF_WEEK)==j){
//						//si le jour correspond bien � la date entr�e
//						
//						//on transforme le calendar en date pour l'ajouter � la table
//						//java.util.Date d = calendar.getTime();
//						java.sql.Date sqlDate = new java.sql.Date(calendar.getTimeInMillis());
//						System.out.println("hi");
//						pst.setDate(1,sqlDate);
//
//						
//						pst.setInt(2, ensei.getNumeroEnseignant());
//						pst.setInt(3, poids);
//						pst.setInt(4,dj);
//						System.out.println("test ap SET de hebdo");
//						pst.executeUpdate();
//						
//						cx.commit();
//					System.out.println("test ap executeUpdate de hebdo");
//					
//						//pour passer � la semaine suivante, on r�-utilise la date au format calendar
//						calendar.add(Calendar.WEEK_OF_MONTH, 1);
//						
//						
//						
//					}
//				}
//				
//			}
//			
//			//si mensuel : meme traitement sql mais fait plusieurs fois
//			if (regulier == 2) {
//
//				for(int i=0; i<nbO; i++){
//					//ici, on ajoute une indispo autant de fois que demand� par le prof
//					
//					if(calendar.get(Calendar.DAY_OF_WEEK)==j){
//						//si le jour correspond bien � la date entr�e
//						
//						//on transforme le calendar en date pour l'ajouter � la table
//						java.sql.Date sqlDate = new java.sql.Date(calendar.getTimeInMillis());
//						pst.setDate(1,sqlDate);
//						
//						pst.setInt(2, ensei.getNumeroEnseignant());
//						pst.setInt(3, poids);
//						pst.setInt(4,dj);
//						System.out.println("test ap SET de mens");
//						pst.executeUpdate();
//						cx.commit();
//						System.out.println("test ap executeUpdate de mens");
//	// VERIFIER QUE AVANCE BIEN D'UN LUNDI A UN AUTRE : CF PB DES MOIS DE 28/29/30/31 JOURS
//						
//						
//						calendar2 = calendar;
//						
//						//pour passer au mois suivant, on r�-utilise la date au format calendar
//						calendar.add(Calendar.MONTH, 1);
//						
//						
//						while(calendar.get(Calendar.DAY_OF_WEEK)!= j ){
//							
//							System.out.println("Je SUIS DANS CETTE SATANEE BOUCLE DE ****TUUUUUUUUUUUUUT*******");
//							
//							if(calendar.get(Calendar.DAY_OF_WEEK)>j){
//								calendar.add(Calendar.DAY_OF_MONTH, -1);
//							}
//							if(calendar.get(Calendar.DAY_OF_WEEK)<j){
//								calendar.add(Calendar.DAY_OF_MONTH, 1);
//							}
//						}
//						
//						System.out.println(calendar);
//						System.out.println("mois avant : "+ calendar + " mois apr : " + calendar);
//						
//						//calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar2.get(Calendar.WEEK_OF_MONTH));
//						System.out.println("\n\n nouvelle date : " + calendar);
//					}
//				}
//			}
//			/** Fermetures - �tape 6 */
//		
//		System.out.println("teeeeeeeeeest");
//			pst.close();
//			st.close();
//			cx.close();
//		} catch (SQLException ex) {
//			ex.printStackTrace();
//			System.err.println("Erreur lors de la cx � la base");
//			System.exit(1);
//		}
//    }
	
	
	
public void ajoutIndispoUniq(String debut, String fin, int poids, int refEnseignant) throws ParseException{
		
		try {
			/** Connection � la base - �tape 2 */
			String url = "jdbc:oracle:thin:@miage03.dmiage.u-paris10.fr:1521:MIAGE";
			Connection cx = DriverManager.getConnection(url, "maletell",
					"matthieu");

			/** Cr�ation et ex�cution d'une requ�te - �tapes 3 & 4 */
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
				pst.setInt(4,2);//par defaut, pour une periode, c'est la journee enti�re
			}
			
			/** Fermetures - �tape 6 */
			pst.close();
			st.close();
			cx.close();
		} catch (SQLException ex) {
			System.err.println("Erreur lors de la cx � la base");
			System.exit(1);
		}
    }

	public void afficherIndispo(Enseignant ensei) throws SQLException{
		
		/** Connection � la base - �tape 2 */
		String url = "jdbc:oracle:thin:@miage03.dmiage.u-paris10.fr:1521:MIAGE";
		Connection cx = DriverManager.getConnection(url, "maletell",
				"matthieu");

		/** Cr�ation et ex�cution d'une requ�te - �tapes 3 & 4 */
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
