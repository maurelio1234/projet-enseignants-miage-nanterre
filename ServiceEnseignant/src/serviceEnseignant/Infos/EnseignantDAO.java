package serviceEnseignant.Infos;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.GregorianCalendar;

import beans.Enseignant;



public class EnseignantDAO {

	private Enseignant ens;
	
	public EnseignantDAO(){
		
		this.ens = new Enseignant();
		this.chargerDriver();
	}
	
	/**
	 * methode qui recupere les informations de la base de donnees 
	 * @param numEns
	 */
	public void recupererInfos(int numEns) {
		
		try {
			
			java.sql.Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@miage03.dmiage.u-paris10.fr:1521:MIAGE","maletell","matthieu");
			
			Statement st =  conn.createStatement();
			
			ResultSet resultat =  st.executeQuery("select * from ENSEIGNANT e "+
													"where e.NO_ENSEIGNANT = '"+ numEns +"'"); 
			
			while(resultat.next()){
				this.ens.setNom(resultat.getString("NOM_ENSEIGNANT"));
				this.ens.setPrenom(resultat.getString("PRENOM_ENSEIGNANT"));
				this.ens.setAdresse(resultat.getString("ADRESSE_ENSEIGNANT"));
				this.ens.setTelephone(resultat.getString("TELEPHONE_ENSEIGNANT"));
				this.ens.setLogin(resultat.getString("LOGIN_ENSEIGNANT"));
				this.ens.setPassword(resultat.getString("PWD_ENSEIGNANT"));
				
				// cast du string en gregorian calendar
				/**SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				Date dateNais = (Date) format.parse(resultat.getString("DATE_NAISSANCE_ENSEIGNANT"));
				GregorianCalendar gcDate = new GregorianCalendar();
				gcDate.setTime(dateNais);*/
				
				GregorianCalendar gcDate = ConvertirDate(resultat.getString("DATE_NAISSANCE_ENSEIGNANT"));
												
				//SimpleDateFormat dateF = new SimpleDateFormat("dd/MM/yyyy");
				//System.out.println("date : "+dateF.format(gcDate.getTime()));
				
				this.ens.setDateNaissance(gcDate);
			}
			
			resultat.close();
			st.close();
			conn.close();
			
		}
		catch (Exception e){
			//System.out.println("Erreur de connexion a la base de donnee ");
			System.exit(1);
		}
	}
	
	/**
	 * methode qui verifie si le login et le mdp existe dans la table ensignant
	 * @param login
	 * @param mdp
	 * @return un boolean si l'enseignant existe
	 */
	
	public boolean verifierLoginMdp(String login, String mdp){
		boolean testConn = false;
		//System.out.println("VERIFIE LOGIN MDP");
		try {
			
			java.sql.Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@miage03.dmiage.u-paris10.fr:1521:MIAGE","maletell","matthieu");
			//System.out.println("je suis connecté");		
			Statement st =  conn.createStatement();
			
			ResultSet resultat =  st.executeQuery("select * from ENSEIGNANT e "+
													"where e.LOGIN_ENSEIGNANT = '"+ login +"' and e.PWD_ENSEIGNANT = '"+ mdp+ "'"); 
			
			//System.out.println("j'ai fai ma requete");	
			while(resultat.next()){
				this.ens.setNumeroEnseignant(resultat.getInt("NO_ENSEIGNANT"));
				this.ens.setLogin(resultat.getString("LOGIN_ENSEIGNANT"));
				this.ens.setPassword(resultat.getString("PWD_ENSEIGNANT"));
				testConn = true;
				//System.out.println("LOGIN ET MDP OK");
			}
			
			//System.out.println("fin requete");	
			resultat.close();
			st.close();
			conn.close();
			
		}
		catch (Exception e){
			System.out.println("Erreur de connexion a la base de donnee ");
			System.exit(1);
		}
		
		return testConn;
	}
	
	/**
	 * methode qui enregistre les modifications de l'enseignant
	 * @param numEns
	 * @param nom
	 * @param prenom
	 * @param adresse
	 * @param telephone
	 * @param dateNaissance
	 * @return un boolean si la methode a fait un enregistrement dans la table enseignant
	 */
	public boolean enregistrerInfos(String nom, String prenom, String adresse, String telephone, String dateNaissance) {
		boolean testEnreg = false;
		
		//SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		//java.sql.Date dateNais = (java.sql.Date) format.parse(dateNaissance);
		
		//System.out.println("dans enregistrerInfos de DAO");
		
		try {
			
			java.sql.Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@miage03.dmiage.u-paris10.fr:1521:MIAGE","maletell","matthieu");
			
			//System.out.println("je suis connecté a la bdd");
			
			PreparedStatement pst =  conn.prepareStatement("UPDATE ENSEIGNANT "+
														"SET NOM_ENSEIGNANT = ?, PRENOM_ENSEIGNANT = ?, ADRESSE_ENSEIGNANT = ?, TELEPHONE_ENSEIGNANT = ?, DATE_NAISSANCE_ENSEIGNANT = ? " +
														"WHERE NO_ENSEIGNANT = ?");
			
			//System.out.println("je prepare la requete update");
			
			pst.setString(1, nom);
			pst.setString(2, prenom);
			pst.setString(3, adresse);
			pst.setString(4, telephone);
			pst.setString(5, dateNaissance);
			pst.setInt(6, this.ens.getNumeroEnseignant());
			
			int result = pst.executeUpdate();
			//retourne le nombre de lignes mises a jour
			
			//System.out.println("j'execute la requete");
			
			if(result>0) {
				testEnreg = true;
				//System.out.println("la requete a ete execute ac succes");
			}
			
			pst.close();
			conn.close();
			
		}
		catch (Exception e){
			System.out.println("Erreur de connexion a la base de donnee ");
			System.exit(1);
		}
		
		this.recupererInfos(this.ens.getNumeroEnseignant());
		return testEnreg;
		
	}
	
	public boolean verifAncienMDP(String ancienMDP){
		boolean ok = false;
		/*
		try {
			
			java.sql.Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@miage03.dmiage.u-paris10.fr:1521:MIAGE","maletell","matthieu");
			
			Statement st =  conn.createStatement();
			
			ResultSet resultat =  st.executeQuery("select * from ENSEIGNANT e "+
													"where e.NO_ENSEIGNANT ="+ numEns +" and e.PWD_ENSEIGNANT = '"+ ancienMDP+ "'"); 
			
			while(resultat.next()){				
				ok = true;
			}
			
			resultat.close();
			st.close();
			conn.close();
			
		}
		catch (Exception e){
			//System.out.println("Erreur de connexion a la base de donnee ");
			System.exit(1);
		}
		*/
		
		//System.out.println("je suis dans la méthode verifier ancien mdp");
		
		if(this.ens.getPassword().equals(ancienMDP))
			ok = true;
		
		return ok;
	}
	
	public boolean enregistrerMDP(String ancienMDP, String nouveauMDP){
		
		//System.out.println("je suis dans la méthode enregistrer nouveau mdp");
		
		boolean modifOk = false;
		
		//si l'ancien mdp est correct pour le numero enseignant, on peut proceder a la modification 
		if(this.verifAncienMDP(ancienMDP)){
			
			try {
				
				java.sql.Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@miage03.dmiage.u-paris10.fr:1521:MIAGE","maletell","matthieu");
				
				conn.setAutoCommit(false);
				
				//System.out.println("je suis connectée dans bdd");
				
				PreparedStatement pst =  conn.prepareStatement("UPDATE ENSEIGNANT "+
															"SET PWD_ENSEIGNANT = ? " +
															"WHERE NO_ENSEIGNANT = "+this.getEns().getNumeroEnseignant());
				
				pst.setString(1, nouveauMDP);
				//pst.setInt(2, this.getEns().getNumeroEnseignant());
				
				int result = pst.executeUpdate();
				//retourne le nombre de lignes mises a jour
				
				conn.commit();
				
				/*System.out.println("j'execute la requete");
				System.out.println("UPDATE ENSEIGNANT "+
						"SET PWD_ENSEIGNANT = '" +nouveauMDP+
						"' WHERE NO_ENSEIGNANT = "+this.getEns().getNumeroEnseignant());
				
				System.out.println("nb lignes mis a jour : "+result);
				*/
				
				if(result>0) {
					modifOk = true;
					//System.out.println("succes de la requete update mdp");
				}
				
				pst.close();
				conn.close();
				
			}
			catch (Exception e){
				System.out.println("Erreur de connexion a la base de donnee ");
				System.exit(1);
			}
			
		}
		
		this.recupererInfos(this.getEns().getNumeroEnseignant());
		return modifOk;	
	}
	
	public GregorianCalendar ConvertirDate(String date) throws Exception {
        String delims = "[/]";
        String[] tokens = date.split(delims);
        GregorianCalendar gDate = null;
        if (tokens.length == 3) {
            int jour = Integer.parseInt(tokens[0]);
            int mois = Integer.parseInt(tokens[1]) -1;
            int annee = Integer.parseInt(tokens[2]);
            try {
                gDate = new GregorianCalendar(annee, mois, jour);
                gDate.setLenient(false);
            } catch (Exception e) {
                System.out.println("Le Format de la date n'est pas valide");
                return null;
            }
        }
        return gDate;
    }
	
	public void setEns(Enseignant ens){
		this.ens = ens;
	}
	
	public Enseignant getEns(){
		return this.ens;
	}
	
	// méthode statique qui charge le driver passé en paramètre
	public static void chargerDriver(){
		
		try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }
        catch (Exception ex){
            System.err.println("Erreur lors du chargement du driver");
            System.exit(1);
        }       
	}
}
