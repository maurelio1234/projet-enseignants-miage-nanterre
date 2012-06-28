package serviceEnseignant.Voeux;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dao.DAO;
import beans.*;

import serviceEnseignant.DAO.IndisponibiliteDAO;
import serviceEnseignant.DAO.JoursDAO;
import serviceEnseignant.DAO.EnseignantDAO;



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
	
	private Set<Date> getIndispo(Date debut, int regulier, int nbO, int j) {
		Set<Date> ret = new HashSet<Date>();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	
		GregorianCalendar calendar = new java.util.GregorianCalendar(); 
		calendar.setTime(debut);
		System.out.println("essaiiii : " + debut);
		
		System.out.println("je suis dans le getindispo et regulier : " + regulier);
		
		if (regulier == 1) { //HEBDO
			
			java.util.Date d;
			for(int i=0; i<nbO; i++){
				//ici, on ajoute une indispo autant de fois que demandé par le prof
				System.out.println("\n je suis dans le début FOR HEBDO");
				
				if(calendar.get(Calendar.DAY_OF_WEEK)==j){
					d= calendar.getTime();
					ret.add(d);
					System.out.println("\n je suis dans le IF HEBDO");
					}
				
				calendar.add(Calendar.WEEK_OF_MONTH, 1);
				System.out.println("\n je suis dans le fin FOR HEBDO");
			}
			
			
			
		} else if (regulier == 2){ // MENS
			
			System.out.println("je suis mensuellement dans le getindispo");
			int semaine = calendar.get(Calendar.WEEK_OF_MONTH);
			int mois = calendar.get(Calendar.MONTH) + 1;
			int annee = calendar.get(Calendar.YEAR);
			
			System.out.println("Sem : " + semaine + " mois : "+mois);
			
			for(int i=0;i<nbO;++i) {
				Date date = getDate(semaine, j, annee, mois+i);
				ret.add(date);
				
				System.out.println("je suis mensuellement dans le getindispo BOUCLE FOR " + date);
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
			
			if(semaine != 0)
				calendar.add(GregorianCalendar.DAY_OF_MONTH, 1);
			
		}
		
		java.util.Date d = calendar.getTime();
		System.out.println("\n dateeeeee " + d);
		
		return d;
	}
		
	
	
	
	public void ajoutIndispoReg(String debut, /*String fin,*/ int dj, int poids, Enseignant ensei, int regulier, int nbO, int j) throws ParseException, SQLException{
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date deb = format.parse(debut);
		
		System.out.println("je suis dans ajoutREG" + regulier);
		
		Set<Date> collectionIndispo = new HashSet<Date>();
		collectionIndispo=getIndispo(deb, regulier, nbO, j);
		
		for(Date ind : collectionIndispo) {
			System.out.println("je suis dans le DEBUT DU FOR de ajoutIndispoREG");
			ajoutIndispoSimpleDAO(ind, poids, ensei, dj); //ajouter juste une ligne dans la table indispo
			System.out.println("je suis dans le FOR de ajoutIndispoREG");
		}
	}
	
	
	
	public void ajoutIndispoSimple(Date debut,int poids, Enseignant ensei, int dj) throws SQLException, SQLIntegrityConstraintViolationException{
		
		
		PreparedStatement pst=null;
		
		
		Connection cx=null;
		
		//try {
			
			/** Connection à la base - Étape 2 */
			this.loadBD();

			cx = DriverManager.getConnection("jdbc:oracle:thin:@miage03.dmiage.u-paris10.fr:1521:MIAGE","maletell","matthieu");
			cx.setAutoCommit(false);

			/** Création et exécution d'une requête - Étapes 3 & 4 */
			
			 pst = cx.prepareStatement("INSERT INTO Indisponibilite VALUES(?,?,?,?)");
			
			 
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
		
			cx.close();
		//}catch (SQLException ex) {
			pst.close();
			
			cx.close();
			
			//System.exit(1);
		//}
	}	
//		try {
//			System.out.println("coucou!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//			/** Connection à la base - Étape 2 */
//			this.loadBD();
//
//			Connection cx = DriverManager.getConnection("jdbc:oracle:thin:@miage03.dmiage.u-paris10.fr:1521:MIAGE","maletell","matthieu");
//			System.out.println("typeeeeeeeeee 222522222222 " + regulier);
//cx.setAutoCommit(false);
//
//			/** Création et exécution d'une requête - Étapes 3 & 4 */
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
//					//ici, on ajoute une indispo autant de fois que demandé par le prof
//					
//					if(calendar.get(Calendar.DAY_OF_WEEK)==j){
//						//si le jour correspond bien à la date entrée
//						
//						//on transforme le calendar en date pour l'ajouter à la table
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
//						//pour passer à la semaine suivante, on ré-utilise la date au format calendar
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
//					//ici, on ajoute une indispo autant de fois que demandé par le prof
//					
//					if(calendar.get(Calendar.DAY_OF_WEEK)==j){
//						//si le jour correspond bien à la date entrée
//						
//						//on transforme le calendar en date pour l'ajouter à la table
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
//						//pour passer au mois suivant, on ré-utilise la date au format calendar
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
//			/** Fermetures - Étape 6 */
//		
//		System.out.println("teeeeeeeeeest");
//			pst.close();
//			st.close();
//			cx.close();
//		} catch (SQLException ex) {
//			ex.printStackTrace();
//			System.err.println("Erreur lors de la cx à la base");
//			System.exit(1);
//		}
//    }
	
	
	
public void ajoutIndispoUniq(String debut, String fin, int poids, int refEnseignant, int duree) throws ParseException{
		
		try {
			
			System.out.println("\nDEBUT : " + debut);
			/** Connection à la base - Étape 2 */
			String url = "jdbc:oracle:thin:@miage03.dmiage.u-paris10.fr:1521:MIAGE";
			Connection cx = DriverManager.getConnection(url, "maletell",
					"matthieu");
			cx.setAutoCommit(false);

			/** Création et exécution d'une requête - Étapes 3 & 4 */
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
			
			
			GregorianCalendar calendar = new java.util.GregorianCalendar(); 
			java.sql.Date sqlDate;
			/*
			 * Maintenant on boucle tant qu'on n'est pas
			 * la date de fin
			 * et on ajoute dans la base
			 */
			
			System.out.println("AVANT BOUCLE FOR");
			
			java.util.Date date= new java.util.Date();
			for (date = dateD; date.before(f) || date.equals(f);) {
				calendar.setTime(date);
				
				sqlDate = new java.sql.Date(calendar.getTimeInMillis());
				pst.setDate(1,sqlDate);
				
				pst.setInt(2, refEnseignant);
				pst.setInt(3, poids);
				pst.setInt(4,duree);
				
				System.out.println("DANS BOUCLE FOR " + date);
				
				pst.executeUpdate();
				
				cx.commit();
				
				GregorianCalendar calendarBis = new java.util.GregorianCalendar(); 
				calendarBis.setTime(date);
				calendarBis.add(Calendar.DAY_OF_MONTH, 1);
				date = calendarBis.getTime();
			}
			
			/** Fermetures - Étape 6 */
			pst.close();
			cx.close();
			
			System.out.println("APRES BOUCLE FOR");
			
		} catch (SQLException ex) {
			ex.printStackTrace();
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
		
		System.out.println("Num ensei : " + ensei.getNumeroEnseignant());
		
		ResultSet rs = st.executeQuery("SELECT * FROM Indisponibilite WHERE NO_ENSEIGNANT ="+ ensei.getNumeroEnseignant() + " ORDER BY DATE_INDISPO");
				
		List<Indisponibilite> listInd = new ArrayList<Indisponibilite>();
		int b=0;
		while(rs.next()){
			GregorianCalendar calendar = new java.util.GregorianCalendar(); 
			calendar.setTime(rs.getDate(1));

			Indisponibilite i = new Indisponibilite();
			Jours j = new Jours();
			
			j.setDateDuJour(calendar);
			
			i.setDateIndisponibilite(j);
			i.setDemiJournee(rs.getInt(4));
			i.setPoids(rs.getInt(3));
			System.out.println(rs.getDate(1));
			ensei.getMesIndisponibilites().add(i);
			ensei.setMesIndisponibilites(ensei.getMesIndisponibilites());
			
			System.out.println(" i vaut : " + i.getDateIndisponibilite().getDateDuJour().get(Calendar.DAY_OF_MONTH));
			listInd.add(i);

			
		}
		//ensei.setMesIndisponibilites(listInd);
		
	}
	
public void SuppIndispo(String date, int refEnseignant) throws SQLException{
		
		try {
			
			
			/** Connection à la base - Étape 2 */
			String url = "jdbc:oracle:thin:@miage03.dmiage.u-paris10.fr:1521:MIAGE";
			Connection cx = DriverManager.getConnection(url, "maletell",
					"matthieu");
			Statement request = cx.createStatement();
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date dateD = format.parse(date);
		    GregorianCalendar cal = new java.util.GregorianCalendar(); 
			cal.setTime(dateD);
			java.sql.Date sqldate = new java.sql.Date(cal.getTimeInMillis());
			System.out.println("date : " + dateD + " sql date : " + sqldate);
			
			System.out.println("DELETE FROM Indisponibilite WHERE NO_ENSEIGNANT = " + refEnseignant 
					+ " AND DATE_INDISPO = " + DAO.dateFromJavaToOracle(cal));
			System.out.println(request.executeUpdate("DELETE FROM Indisponibilite WHERE NO_ENSEIGNANT = " + refEnseignant 
					+ " AND DATE_INDISPO = " + DAO.dateFromJavaToOracle(cal)));
		
		cx.commit();
		request.close();
		cx.close();
		
		
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
	
	/*
	public void SuppIndispoDAO(String date, int refEnseignant){
		
		try {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date dte = format.parse(date);
			GregorianCalendar gregcal = new GregorianCalendar();
			gregcal.setTime(dte);
			EnseignantDAO ensdao = new EnseignantDAO();
			Enseignant ens =ensdao.find(refEnseignant);
			IndisponibiliteDAO inddao= new IndisponibiliteDAO();
			JoursDAO jdao= new JoursDAO();
			Jours jrs = jdao.find(gregcal);
			Indisponibilite ind = new Indisponibilite();
			ind.setDateIndisponibilite(jrs);
			ind.setMonEnseignant(ens);
			
			inddao.delete(ind);
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	*/
	
	
	
	/***
	 * FONCTIONS APPELANT LES DAO 
	 */
	
	/**
	 * AJOUT PERIODE REGULIERE INDISPOS
	 * @param debut
	 * @param poids
	 * @param ensei
	 * @param dj
	 * @throws SQLException
	 */

	public void ajoutIndispoSimpleDAO(Date debut,int poids, Enseignant ensei, int dj) throws SQLException{
					
			GregorianCalendar calendar = new java.util.GregorianCalendar(); 
			calendar.setTime(debut);
			
			/**
			 * APPEL AU DAO
			 */
			serviceEnseignant.DAO.JoursDAO jdao = new serviceEnseignant.DAO.JoursDAO();
			Jours j = jdao.find(calendar);
			Indisponibilite i = new Indisponibilite();
			System.out.println("jours " + j.getDateDuJour());
			i.setDateIndisponibilite(j);
			i.setDemiJournee(dj);
			i.setPoids(poids);
			i.setMonEnseignant(ensei);
			IndisponibiliteDAO idao = new IndisponibiliteDAO();
			idao.create(i);
					
	}	
	
	/**
	 * AJOUT PERIODE UNIQUE INDISPOS
	 * @param debut
	 * @param fin
	 * @param poids
	 * @param refEnseignant
	 * @throws ParseException
	 */
public void ajoutIndispoUniqDAO(String debut, String fin, int poids, int refEnseignant, int duree) throws ParseException{
		
		
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date dateD = format.parse(debut);
			java.util.Date dateF = format.parse(fin);

			/* 
			 * pour qu'on s'arrete au jour de fin INCLUS
			 * il faut transformer la dateF en calendar pour ajouter 1 JOUR 
			 */
		GregorianCalendar cal = new java.util.GregorianCalendar(); 
			cal.setTime(dateF);
			/*cal.add(Calendar.DAY_OF_WEEK, 1);*/
			/*
			 * On retransforme en date pour la boucle
			 */
	java.util.Date f = cal.getTime();
			
			
			
			java.sql.Date sqlDate;
			/*
			 * Maintenant on boucle tant qu'on n'est pas
			 * la date de fin
			 * et on ajoute dans la base
			 */
			
			EnseignantDAO ensdao = new EnseignantDAO();
			Enseignant ens = ensdao.find(refEnseignant);
			
			java.util.Date date= new java.util.Date();
			for (date = dateD; date.before(f) || date.equals(f);) {
				
				GregorianCalendar calendar = new java.util.GregorianCalendar(); 
				calendar.setTime(date);
				
				
				
				JoursDAO jdao = new JoursDAO();
				Jours j = jdao.find(calendar);
				
				System.out.println("jours " + j.getDateDuJour());
				
				Indisponibilite i = new Indisponibilite();
				i.setDateIndisponibilite(j);
				i.setDemiJournee(duree);
				i.setPoids(poids);
				i.setMonEnseignant(ens);
				IndisponibiliteDAO idao = new IndisponibiliteDAO();
				idao.create(i);				
				
				
				GregorianCalendar calendarBis = new java.util.GregorianCalendar(); 
				calendarBis.setTime(date);
				calendarBis.add(Calendar.DAY_OF_MONTH, 1);
				date = calendarBis.getTime();
			}
			
		
    }
    
   
    	
/**
 * FIN APPEL AU DAO
 */
	
}
