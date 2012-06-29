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
	
	
/***
 * FONCTIONS APPELANT LES DAO 
 */	
	/**
	 * Pour récupérer toutes les dates
	 * (et calculer le nombre de jours à ajouter à la première occurrence d'une indispo :
	 * si c'est hebdomadaire, on ajoute 7 jours, si c'est mensuel on cherche le numéro 
	 * qui correspond au jour et au même numéro de semaine)
	 *  
	 * @param debut
	 * @param regulier
	 * @param nbO
	 * @param j
	 * @return une collection (SET) de dates
	 */
	private Set<Date> getIndispo(Date debut, int regulier, int nbO, int j) {
		Set<Date> ret = new HashSet<Date>();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	
		GregorianCalendar calendar = new java.util.GregorianCalendar(); 
		calendar.setTime(debut);
		
		if (regulier == 1) { //HEBDO
			
			java.util.Date d;
			for(int i=0; i<nbO; i++){
				//ici, on ajoute une indispo autant de fois que demandé par le prof
				
				if(calendar.get(Calendar.DAY_OF_WEEK)==j){
					d= calendar.getTime();
					ret.add(d);
					}
				
				calendar.add(Calendar.WEEK_OF_MONTH, 1);
			}
			
			
		} else if (regulier == 2){ // MENS
			
			int semaine = calendar.get(Calendar.WEEK_OF_MONTH);
			int mois = calendar.get(Calendar.MONTH) + 1;
			int annee = calendar.get(Calendar.YEAR);
						
			for(int i=0;i<nbO;++i) {
				Date date = getDate(semaine, j, annee, mois+i);
				ret.add(date);
			}
		}
		return ret;
	}
	
	/**
	 * Fonction permettant de retourner une date afin
	 * de trouver l'occurrence suivante d'une indisponibilité mensuelle
	 * 
	 * @param semaine
	 * @param j
	 * @param annee
	 * @param mois
	 * @return la date associé au même jour et au même numéro de semaine (passés en param)
	 */
	private Date getDate(int semaine, int j, int annee, int mois) {
		GregorianCalendar calendar = new java.util.GregorianCalendar(annee, mois-1, 1);
		
		while(semaine > 0) {
			if (calendar.get(GregorianCalendar.DAY_OF_WEEK) == j) {				
				semaine --;
			}
			
			if(semaine != 0)
				calendar.add(GregorianCalendar.DAY_OF_MONTH, 1);
			
		}
		
		java.util.Date d = calendar.getTime();		
		return d;
	}
	
	/**
	 * Fonction qui récupère la collection de Date et qui fait appel à la fonction
	 * ajoutSimpleDAO qui ajoute une indisponibilite dans la BD
	 * @param debut
	 * @param dj
	 * @param poids
	 * @param ensei
	 * @param regulier
	 * @param nbO
	 * @param j
	 * @throws ParseException
	 * @throws SQLException
	 */
	public void ajoutIndispoReg(String debut, /*String fin,*/ int dj, int poids, Enseignant ensei, int regulier, int nbO, int j) throws ParseException, SQLException{
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date deb = format.parse(debut);
				
		Set<Date> collectionIndispo = new HashSet<Date>();
		collectionIndispo=getIndispo(deb, regulier, nbO, j);
		
		for(Date ind : collectionIndispo) {
			ajoutIndispoSimpleDAO(ind, poids, ensei, dj); //ajouter juste une ligne dans la table indispo
		}
	}
	
	/**
	 * Fonction qui supprime une indisponibilité dans la BD
	 * Elle permet de créer des instances DAO (indispoDAO, EnseiDAO, JoursDAO)
	 * et les Beans associés.
	 * Fonction appelée par ajoutIndispoReg et faisant appel à la méthode delete de IndisponibiliteDAO
	 * @param date
	 * @param refEnseignant
	 */
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
	

	/**
	 * AJOUT PERIODE REGULIERE INDISPOS : créer des instances DAO (indispoDAO, EnseiDAO, JoursDAO)
	 * et les Beans associés.
	 * Fait appel à la fonction create de IndisponibiliteDAO
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
			
			i.setDateIndisponibilite(j);
			i.setDemiJournee(dj);
			i.setPoids(poids);
			i.setMonEnseignant(ensei);
			IndisponibiliteDAO idao = new IndisponibiliteDAO();
			idao.create(i);
					
	}	
	
	/**
	 * AJOUT PERIODE UNIQUE INDISPOS : créer des instances DAO (indispoDAO, EnseiDAO, JoursDAO)
	 * et les Beans associés.
	 * Fait appel à la fonction create de IndisponibiliteDAO
	 * MEME FONCTIONNEMENT QUE LA METHODE PRECEDENTE MAIS POUR UNE SEULE INDISPO
	 * @param debut
	 * @param fin
	 * @param poids
	 * @param refEnseignant
	 * @throws ParseException
	 */
	public void ajoutIndispoUniqDAO(String debut, String fin, int poids,
			int refEnseignant, int duree) throws ParseException {

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date dateD = format.parse(debut);
		java.util.Date dateF = format.parse(fin);

		/*
		 * pour qu'on s'arrete au jour de fin INCLUS il faut transformer la
		 * dateF en calendar pour ajouter 1 JOUR
		 */
		GregorianCalendar cal = new java.util.GregorianCalendar();
		cal.setTime(dateF);
		/*
		 * On retransforme en date pour la boucle
		 */
		java.util.Date f = cal.getTime();

		/*
		 * Maintenant on boucle tant qu'on n'est pas la date de fin et on ajoute
		 * dans la base
		 */

		EnseignantDAO ensdao = new EnseignantDAO();
		Enseignant ens = ensdao.find(refEnseignant);

		java.util.Date date = new java.util.Date();
		for (date = dateD; date.before(f) || date.equals(f);) {

			GregorianCalendar calendar = new java.util.GregorianCalendar();
			calendar.setTime(date);

			JoursDAO jdao = new JoursDAO();
			Jours j = jdao.find(calendar);

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
	
	/**
	 * FONCIONS NON UTILISEES (AVANT DAO)
	 */

	public void SuppIndispo(String date, int refEnseignant) throws SQLException {

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

			request.executeUpdate("DELETE FROM Indisponibilite WHERE NO_ENSEIGNANT = "
					+ refEnseignant
					+ " AND DATE_INDISPO = "
					+ DAO.dateFromJavaToOracle(cal));

			cx.commit();
			request.close();
			cx.close();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
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
						
			java.util.Date date= new java.util.Date();
			for (date = dateD; date.before(f) || date.equals(f);) {
				calendar.setTime(date);
				
				sqlDate = new java.sql.Date(calendar.getTimeInMillis());
				pst.setDate(1,sqlDate);
				
				pst.setInt(2, refEnseignant);
				pst.setInt(3, poids);
				pst.setInt(4,duree);
								
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
				
		ResultSet rs = st.executeQuery("SELECT * FROM Indisponibilite WHERE NO_ENSEIGNANT ="+ ensei.getNumeroEnseignant() + " ORDER BY DATE_INDISPO");
				
		List<Indisponibilite> listInd = new ArrayList<Indisponibilite>();
		
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
			
			listInd.add(i);
		}		
	}
	
	public void ajoutIndispoSimple(Date debut,int poids, Enseignant ensei, int dj) throws SQLException, SQLIntegrityConstraintViolationException{
		
		PreparedStatement pst=null;
		
		Connection cx=null;
			
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
			cx.commit();
			
			pst.close();
					
			cx.close();
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
}
